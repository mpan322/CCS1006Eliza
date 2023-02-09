package Parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.w3c.dom.Node;

import ScriptDataStructure.DecompositionRule;
import ScriptDataStructure.Keyword;
import ScriptDataStructure.ReassemblyRule;
import ScriptDataStructure.Script;
import ScriptDataStructure.Substituter;

/**
 * ScriptParser
 */
public class ScriptParser extends XMLParser implements ScriptParserInterface {

    private static final Predicate<? super Node> DEFAULT_FILTER = (
            Node node) -> node.getNodeName() != ScriptXMLTags.DEFAULT.getTag();

    public ScriptParser(File scriptFile) {

        super(scriptFile);

    }

    @Override
    public Script parseScript() {

        List<String> quitKeywords = this.parseQuitKeywords();

        List<Keyword> keywords = this.parseKeywords();
        Keyword defaultKeyword = this.getDefaultKeyword();

        String goodbyeMessage = this.getTextFromATag(ScriptXMLTags.GOODBYE_MSG.getTag());
        String welcomeMessage = this.getTextFromATag(ScriptXMLTags.WELOCME_MSG.getTag());

        Substituter postSub = this.parseGlobalPostSubstitution();
        Substituter preSub = this.parsePreSubstitution();

        return new Script(defaultKeyword, keywords, preSub, postSub, welcomeMessage, goodbyeMessage, quitKeywords);

    }

    /**
     * Parses the quit keywords
     * 
     * @return the list of quit keywords
     */
    private List<String> parseQuitKeywords() {

        return this.streamByTagName(ScriptXMLTags.QUIT_KEYWORD.getTag())
                .map(this::getNodeText)
                .toList();

    }

    /**
     * Parses the presubstions
     * 
     * @return a substituter which will substitute using the presubstitutions
     */
    private Substituter parsePreSubstitution() {

        return this.streamByTagName(ScriptXMLTags.PRE_SUBSTITUTION.getTag())
                .limit(1)
                .map(this::parseSubstituter)
                .toList().get(0);

    }

    /**
     * Parses the global post substitutions
     * 
     * @return parser which will substitute using the gloabl post substitutions
     */
    private Substituter parseGlobalPostSubstitution() {

        String scriptTag = ScriptXMLTags.SCRIPT.getTag();

        // only keeps tags with script as their parent
        Predicate<Node> hasScriptParent = (Node node) -> node.getParentNode().getNodeName().equals(scriptTag);

        return this.streamByTagName(ScriptXMLTags.POST_SUBSTITUTION.getTag())
                .filter(hasScriptParent)
                .limit(1)
                .map(this::parseSubstituter)
                .toList().get(0);

    }

    /**
     * Sorts the keywords by priority in descending order
     * 
     * @param keywords list keywords to be sorted
     * @return the sorted list
     */
    private List<Keyword> sortKeywordsByPriority(List<Keyword> keywords) {

        List<Keyword> sorted = new ArrayList<>();

        for (Keyword keyword : keywords) {

            int priority = keyword.getPriority();
            int i = 0;
            int currPriority = Integer.MAX_VALUE;

            // keep increasing i until the end of the list is reached
            // or the keywords priority exceeds that of the (i-1)th element
            while (currPriority > priority && i < keywords.size()) {

                currPriority = keywords.get(i).getPriority();

                if (currPriority > priority) {

                    i++;

                }

            }

            sorted.add(i, keyword);

        }

        return sorted;

    }

    /**
     * Parses the keywords
     * 
     * @return a list of keywords (objects)
     */
    private List<Keyword> parseKeywords() {

        List<Keyword> keywords = this.streamByTagName("keyword")
                .map(this::parseKeyword)
                .toList();

        keywords = this.sortKeywordsByPriority(keywords);

        return keywords;

    }

    /**
     * Gets the text from the first tag a specifc type found
     * 
     * @param tag the tag type
     * @return the text is contains
     */
    private String getTextFromATag(String tag) {

        return this.streamByTagName(tag)
                .limit(1) // limit to first one found (avoid malformed)
                .map(this::getNodeText)
                .toList().get(0);

    }

    private Keyword getDefaultKeyword() {

        return this.streamByTagName(ScriptXMLTags.KEYWORDS.getTag())
                .map(this::streamChildren)
                .flatMap(stream -> stream) // join together all the streams
                .filter(ScriptParser.DEFAULT_FILTER.negate()) // only include tags named default
                .limit(1) // only include the first found (avoid malformation errors)
                .map(this::parseKeyword) 
                .toList().get(0);

    }

    private Keyword parseKeyword(Node keyword) {

        String word = "";
        String priorityString = "-1";
        if (keyword.hasAttributes()) {

            word = this.getAttribute(keyword, "word");
            priorityString = this.getAttribute(keyword, "priority");

        }

        Integer priority = Integer.parseInt(priorityString);

        // parse non default decomposition rules
        List<DecompositionRule> decompositionRules = this.streamChildren(keyword)
                .filter(DEFAULT_FILTER)
                .filter(XMLParser.NON_TAG_FILTER)
                .map(this::parseDecompositionRule)
                .toList();

        
        // parse default decomposition rules
        DecompositionRule defaultDecomposition = this.streamChildren(keyword)
                .filter(DEFAULT_FILTER.negate())
                .limit(1) // limit to 1 to avoid malformed
                .map(this::parseDecompositionRule)
                .toList().get(0);

        return new Keyword(word, priority, decompositionRules, defaultDecomposition);

    }

    private DecompositionRule parseDecompositionRule(Node decompositionNode) {

        String pattern = "";
        if (decompositionNode.hasAttributes()) {

            // get pattern
            pattern = this.getAttribute(decompositionNode, "pattern");

        }

        // parse reassembly rules associated with this decomposition rule
        List<ReassemblyRule> reassemblyRules = this.streamChildren(decompositionNode)
                .filter(XMLParser.NON_TAG_FILTER)
                .map(this::parseReassemblyRule)
                .toList();

        return new DecompositionRule(pattern, reassemblyRules);

    }

    private ReassemblyRule parseReassemblyRule(Node reassemblyNode) {

        // get the format
        String format = this.getAttribute(reassemblyNode, "format");

        Substituter substituter = Substituter.EMPTY;
        if (reassemblyNode.hasChildNodes()) {

            // parse the substituter
            this.streamChildren(reassemblyNode)
                    .limit(1) // limit to 1 to avoid malformed
                    .map(this::parseSubstituter)
                    .toList().get(0);

        }

        return new ReassemblyRule(format, substituter);

    }

    private Substituter parseSubstituter(Node substituterNode) {

        // anonymous functions for getting the input and replace attributes
        Function<? super Node, String> getInput = (Node node) -> this.getAttribute(node, "input");
        Function<? super Node, String> getReplace = (Node node) -> this.getAttribute(node, "replace");

        // get the inputs and replacements
        Map<String, String> substitutions = this.streamChildren(substituterNode)
                .filter(XMLParser.NON_TAG_FILTER)
                .collect(Collectors.toMap(getInput, getReplace));

        Substituter substituter = new Substituter(substitutions);
        
        return substituter;

    }

}