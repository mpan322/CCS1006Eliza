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
import ScriptDataStructure.GoodbyeMessage;
import ScriptDataStructure.Keyword;
import ScriptDataStructure.QuitKeyword;
import ScriptDataStructure.Script;
import ScriptDataStructure.WelcomeMessage;

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

    private NodeList getTagNodes(String tag) {

        NodeList nodes = this.SCRIPT_XML.getElementsByTagName(tag);
        return nodes;

    }

    public void parseScript() {

        this.parseKeywords();
        this.parseWelcomeMessage();
        this.parseGoodbyeMessages();
        this.parseQuitKeywords();

    }

    private interface NodeParse {

        public void parse(Node nodes);

    }

    private void parseNodes(ScriptXMLTags tag, NodeParse func) {

        String tagString = tag.getTag();
        NodeList nodes = this.getTagNodes(tagString);
        this.parseNodes(nodes, func);

    }

    private void parseNodes(NodeList nodes, NodeParse func) {

        for (int i = 0; i < nodes.getLength(); i++) {

            Node node = nodes.item(i);
            func.parse(node);

        }

    }

    /**
     * Parses the goodbye messages in the script
     */
    private void parseGoodbyeMessages() {

        this.parseNodes(ScriptXMLTags.GOODBYE_MSG, (Node node) -> {

            String message = node.getNodeValue();
            GoodbyeMessage goodbye = new GoodbyeMessage(message);
            this.PARSED_SCRIPT.setGoodbyeMessage(goodbye);

        });

    }

    private void parseQuitKeywords() {

        this.parseNodes(ScriptXMLTags.QUIT_KEYWORD, (Node node) -> {

            String keyword = node.getNodeValue();
            QuitKeyword quitKeyword = new QuitKeyword(keyword);
            this.PARSED_SCRIPT.addQuitKeyword(quitKeyword);

        });

    }

    private void parseWelcomeMessage() {

        this.parseNodes(ScriptXMLTags.WELOCME_MSG, (Node node) -> {

            String message = node.getNodeValue();
            WelcomeMessage welcome = new WelcomeMessage(message);
            this.PARSED_SCRIPT.setWelcomeMessage(welcome);

        });

    }

    private void parseKeywords() {

        String wordAttrName = "word";
        String priorityAttrName = "priority";

        this.parseNodes(ScriptXMLTags.KEYWORD, (Node node) -> {
            
            // get the keyword and priority
            NamedNodeMap attrs = node.getAttributes();    
            Node wordAttrNode = attrs.getNamedItem(wordAttrName);
            Node priorityAttrNode = attrs.getNamedItem(priorityAttrName);

            // parse those attributes
            String wordVal = wordAttrNode.getNodeValue();
            int priorityVal = Integer.parseInt(priorityAttrNode.getNodeValue());
            
            // create new keyword and add it to script
            Keyword keyword = new Keyword(wordVal, priorityVal);
            this.PARSED_SCRIPT.addKeyword(keyword);

            // parse / add all decomposition rules to the keyword
            NodeList decompositionRules = node.getChildNodes();
            this.parseNodes(decompositionRules, (Node decompNode) -> {

                DecompositionRule decompositionRule = this.parseDecompositionRule(decompNode);
                keyword.add(decompositionRule);

            });

        });

    }

    private DecompositionRule parseDecompositionRule(Node decompositionRule) {

        return null;

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