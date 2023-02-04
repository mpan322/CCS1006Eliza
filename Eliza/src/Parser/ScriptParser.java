package Parser;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ScriptDataStructure.DecompositionRule;
import ScriptDataStructure.Keyword;
import ScriptDataStructure.ReassemblyRule;
import ScriptDataStructure.Script;
import ScriptDataStructure.Substituter;

/**
 * ScriptParser
 */
public class ScriptParser {

    private final Document SCRIPT_XML;
    private final Script PARSED_SCRIPT;

    public ScriptParser(File scriptFile) {

        this.SCRIPT_XML = this.getXMLDocument(scriptFile);
        this.PARSED_SCRIPT = new Script();

    }

    /**
     * Gets all nodes in the XML DOM of a given tag
     * 
     * @param tag the tag type to get
     * @return all nodes of that tag type
     */
    private NodeList getTagNodes(String tag) {

        NodeList nodes = this.SCRIPT_XML.getElementsByTagName(tag);
        return nodes;

    }

    public void parseScript() {

        this.parseKeywords();
        this.parseWelcomeMessage();
        this.parseGoodbyeMessages();
        this.parseQuitKeywords();
        this.parseGlobalPostSubstitution();
        this.parsePreSubstitution();

    }

    private interface NodeParse {

        public void parse(Node nodes);

    }

    /**
     * Parses a list of nodes using a given anonymous function, apply it to each
     * node
     * 
     * @param nodes       the nodes to parse
     * @param parsingFunc the anonymous function to apply
     */
    private void parseNodes(NodeList nodes, NodeParse parsingFunc) {

        for (int i = 0; i < nodes.getLength(); i++) {

            Node node = nodes.item(i);
            parsingFunc.parse(node);

        }

    }

    /**
     * Parses all nodes of a given tag applying the given anonymous function to each
     * node
     * 
     * @param tag         the tag of the nodes
     * @param parsingFunc the anonymous function to apply
     */
    private void parseNodes(ScriptXMLTags tag, NodeParse parsingFunc) {

        String tagString = tag.getTag();
        NodeList nodes = this.getTagNodes(tagString);
        this.parseNodes(nodes, parsingFunc);

    }

    /**
     * Parses all children of a given node applying the given anonymous function to
     * each child
     * 
     * @param parent      the parent node whose children are beign parsed
     * @param parsingFunc the anonymous function to apply
     */
    private void parseChildren(Node parent, NodeParse parsingFunc) {

        NodeList children = parent.getChildNodes();
        this.parseNodes(children, parsingFunc);

    }

    private void parseGlobalPostSubstitution() {

        NodeList postSubstitutionNodes = this.SCRIPT_XML.getElementsByTagName(ScriptXMLTags.POST_SUBSTITUTION.getTag());
        this.parseNodes(postSubstitutionNodes, (Node node) -> {

            Node parent = node.getParentNode();
            if (ScriptXMLTags.SCRIPT.isType(parent)) {

                Substituter postSubtitution = this.parseSubstituter(node);
                this.PARSED_SCRIPT.setGlobalPostSubstituter(postSubtitution);

            }

        });

    }

    private void parsePreSubstitution() {

        NodeList preSubstitutionNodes = this.SCRIPT_XML.getElementsByTagName(ScriptXMLTags.PRE_SUBSTITUTION.getTag());
        this.parseNodes(preSubstitutionNodes, (Node node) -> {

            Substituter preSubtitution = this.parseSubstituter(node);
            this.PARSED_SCRIPT.setGlobalPostSubstituter(preSubtitution);

        });

    }

    /**
     * Parses the goodbye messages in the script adding them to the PARSED_SCRIPT
     */
    private void parseGoodbyeMessages() {

        this.parseNodes(ScriptXMLTags.GOODBYE_MSG, (Node node) -> {

            String goodbyeMessage = node.getNodeValue();
            this.PARSED_SCRIPT.setGoodbyeMessage(goodbyeMessage);

        });

    }

    /**
     * Parses the quit keywords in the script adding them to the PARSED_SCRIPT
     */
    private void parseQuitKeywords() {

        this.parseNodes(ScriptXMLTags.QUIT_KEYWORD, (Node node) -> {

            String quitKeyword = node.getNodeValue();
            this.PARSED_SCRIPT.addQuitKeyword(quitKeyword);

        });

    }

    /**
     * Parses the welcome messages in the script adding them to the PARSED_SCRIPT
     */
    private void parseWelcomeMessage() {

        this.parseNodes(ScriptXMLTags.WELOCME_MSG, (Node node) -> {

            String welcomeMessage = node.getNodeValue();
            this.PARSED_SCRIPT.setWelcomeMessage(welcomeMessage);

        });

    }

    /**
     * Parses the keywords in the script adding them to the PARSED_SCRIPT
     */
    private void parseKeywords() {

        this.parseNodes(ScriptXMLTags.KEYWORD, (Node node) -> {

            // get the keyword and priority
            Integer priority = Integer.parseInt(this.getAttributeValue(node, "priority"));
            String word = this.getAttributeValue(node, "word");

            // create new keyword and add it to script
            Keyword keyword = new Keyword(word, priority);
            this.PARSED_SCRIPT.addKeyword(keyword);

            // parse / add all decomposition rules to the keyword
            NodeList decompositionRules = node.getChildNodes();
            this.parseNodes(decompositionRules, (Node decompNode) -> {

                DecompositionRule decompositionRule = this.parseDecompositionRule(decompNode);
                keyword.add(decompositionRule);

            });

        });

    }

    /**
     * Parses a decomposition rule into a java representation
     * 
     * @param decompositionNode the node representing the decomposition rule
     * @return the parsed rule
     */
    private DecompositionRule parseDecompositionRule(Node decompositionNode) {

        String pattern = this.getAttributeValue(decompositionNode, "pattern");
        DecompositionRule decompositionRule = new DecompositionRule(pattern);

        this.parseChildren(decompositionNode, (Node reassemblyNode) -> {

            ReassemblyRule reassemblyElement = this.parseReassemblyRule(reassemblyNode);
            decompositionRule.add(reassemblyElement);

        });

        return decompositionRule;

    }

    /**
     * Parses a reassembly rule into a java representation
     * 
     * @param reassmblyNode the node representing the reassembly rule
     * @return the parsed rule
     */
    private ReassemblyRule parseReassemblyRule(Node reassmblyNode) {

        String format = this.getAttributeValue(reassmblyNode, "format");
        ReassemblyRule reassemblyRule = new ReassemblyRule(format);

        this.parseChildren(reassmblyNode, (Node node) -> {

            Substituter postSubs = this.parseSubstituter(node);
            reassemblyRule.setSubstituter(postSubs);

        });

        return reassemblyRule;
    }

    /**
     * Gets the values of the given attributes of the inputted node.
     * The returned values will be in the same order they were passed into the
     * method.
     * 
     * @param node           the node whose attributes are being gotten
     * @param attributeNames the names of attributes whose values are being gotten
     * @return a list of the values ordered in the same way as the attributes were
     *         inputted.
     */
    private String getAttributeValue(Node node, String attributeName) {

        NamedNodeMap attrs = node.getAttributes();

        Node attrNode = attrs.getNamedItem(attributeName);
        String value = attrNode.getNodeValue();

        return value;

    }

    private Substituter parseSubstituter(Node postSubstitutionNode) {

        Substituter substituter = new Substituter();

        this.parseChildren(postSubstitutionNode, (Node subsRuleNode) -> {

            NamedNodeMap attrs = subsRuleNode.getAttributes();
            Node inputAttrNode = attrs.getNamedItem("input");
            Node replaceAttrNode = attrs.getNamedItem("replace");

            String inputWord = inputAttrNode.getNodeValue();
            String replaceWord = replaceAttrNode.getNodeValue();

            substituter.put(inputWord, replaceWord);

        });

        return substituter;

    }

    private Document getXMLDocument(File file) {

        Document doc = null;

        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(file);

        } catch (ParserConfigurationException e) {

            System.out.println("getXMLDocument ParserConfiguration Exception");

        } catch (IOException e) {

            System.out.println("getXMLDocument IOException");

        } catch (FactoryConfigurationError e) {

        } catch (SAXException e) {

        } catch (IllegalArgumentException e) {

        }

        return doc;

    }

}