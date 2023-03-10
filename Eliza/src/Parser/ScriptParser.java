package Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import ScriptElements.DecompositionRule;
import ScriptElements.Keyword;
import ScriptElements.ReassemblyRule;
import ScriptElements.Script;
import ScriptElements.Substituter;

/**
 * ScriptParser
 */
public class ScriptParser extends XMLParser {

    // filters out default tags
    private static final Predicate<? super Node> DEFAULT_FILTER = (
            Node node) -> node.getNodeName() != ScriptXMLTag.DEFAULT.getTag();

    public ScriptParser(Document scriptDocument) {

        super(scriptDocument);

    }

    public Script parseScript() throws MalformedScriptException {

        List<String> quitKeywords = this.parseQuitKeywords();

        List<Keyword> keywords = this.parseKeywords();
        Keyword defaultKeyword = this.getDefaultKeyword();

        String goodbyeMessage = this.getTextFromFirstTagWithName(ScriptXMLTag.GOODBYE_MSG.getTag());
        String welcomeMessage = this.getTextFromFirstTagWithName(ScriptXMLTag.WELOCME_MSG.getTag());

        Substituter postSub = this.parseGlobalPostSubstitution();
        Substituter preSub = this.parsePreSubstitution();

        return new Script(defaultKeyword, keywords, preSub, postSub, welcomeMessage, goodbyeMessage, quitKeywords);

    }

    /**
     * Parses the quit keywords
     * 
     * @return the list of quit keywords
     * @throws MalformedScriptException
     */
    private List<String> parseQuitKeywords() throws MalformedScriptException {

        List<String> quitKeywords = this.streamByTagName(ScriptXMLTag.QUIT_KEYWORD.getTag())
                .map(this::getNodeText)
                .toList();

        // if no quit keywords found throw an exception
        if (quitKeywords.isEmpty()) {

            throw new MalformedScriptException("No Quit Keywords Found");

        }

        return quitKeywords;

    }

    /**
     * Parses the presubstion tag (only one per script)
     * 
     * @return a substituter which will substitute using the presubstitutions
     */
    private Substituter parsePreSubstitution() {

        return this.streamByTagName(ScriptXMLTag.PRE_SUBSTITUTION.getTag())
                .limit(1) // limit to 1 - avoid malformed
                .map(this::parseSubstituter)
                .toList().get(0);

    }

    /**
     * Parses the global post substitutions
     * 
     * @return parser which will substitute using the gloabl post substitutions
     */
    private Substituter parseGlobalPostSubstitution() {

        String scriptTag = ScriptXMLTag.SCRIPT.getTag();

        // only keeps tags with script as their parent
        Predicate<Node> hasScriptParent = (Node node) -> node.getParentNode().getNodeName().equals(scriptTag);

        return this.streamByTagName(ScriptXMLTag.POST_SUBSTITUTION.getTag())
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

        List<Keyword> temp = new ArrayList<>();
        temp.addAll(keywords);

        List<Keyword> output = new ArrayList<>();

        for (int i = 0; i < keywords.size(); i++) {
            
            int highestPrio = 0;
            int highestIdx = 0;

            for (int j = 0; j < temp.size(); j++) {

                Keyword currKeyword = keywords.get(j);
                int currPrio = currKeyword.getPriority();

                if(currPrio > highestPrio) {

                    highestPrio = currPrio;
                    highestIdx = j;

                }

            }

            output.add(0, temp.get(highestIdx));
            temp.remove(highestIdx);

        }

        return output;

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
     * Gets the default keyword in the script and parses it
     * 
     * @return the default keyword
     */
    private Keyword getDefaultKeyword() {

        Predicate<Node> parentIsKeywords = (Node node) -> {

            String parentName = node.getParentNode().getNodeName();
            return parentName.equals(ScriptXMLTag.KEYWORDS.getTag());

        };

        return this.streamByTagName(ScriptXMLTag.DEFAULT.getTag())
                .filter(parentIsKeywords) // parent is keywords -> must be default keyword
                .limit(1) // only include the first found (avoid malformation errors)
                .map(this::parseKeyword)
                .toList().get(0);

    }

    /**
     * Parses a keyword node into a keyword script object
     * 
     * @param keyword the keyword node
     * @return the keyword script object
     */
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

    /**
     * Parses a decomposition rule node into a decomposition rule object
     * 
     * @param decompositionNode the node
     * @return the object version
     */
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

    /**
     * Parses a reassembly rule node into a reassembly rule object
     * 
     * @param reassemblyNode the reassembly rule node
     * @return the object version
     */
    private ReassemblyRule parseReassemblyRule(Node reassemblyNode) {

        // get the format
        String format = this.getAttribute(reassemblyNode, "format");

        Substituter substituter = Substituter.EMPTY;
        if (reassemblyNode.hasChildNodes()) {

            // parse the substituter
            substituter = this.streamChildren(reassemblyNode)
                    .filter(XMLParser.TEXT_NODE_FILTER)
                    .limit(1) // limit to 1 to avoid malformed
                    .map(this::parseSubstituter)
                    .toList().get(0);

        }

        return new ReassemblyRule(format, substituter);

    }

    /**
     * Parses a node which is a substituter (pre or post) into a substituter object
     * 
     * @param substituterNode the substituter node
     * @return the object version
     */
    private Substituter parseSubstituter(Node substituterNode) {

        // anonymous functions for getting the input and replace attributes
        Function<? super Node, String> getInput = (Node node) -> this.getAttribute(node, "input");
        Function<? super Node, String> getReplace = (Node node) -> this.getAttribute(node, "replace");

        // this.streamChildren(substituterNode)
        //         .filter(XMLParser.NON_TAG_FILTER)
        //         .map(Node::getNodeName)
        //         .forEach(System.out::println);

        // get the inputs and replacements
        Map<String, String> substitutions = this.streamChildren(substituterNode)
                .filter(XMLParser.NON_TAG_FILTER)
                .collect(Collectors.toMap(getInput, getReplace));

        Substituter substituter = new Substituter(substitutions);

        return substituter;

    }

}