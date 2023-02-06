package Parser;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ScriptDataStructure.Script;

/**
 * Class providing utitlies for parsing XML documents
 */
public class XMLParser {

    private final Document DOC;


    /**
     * Creates a new XMLDocument parser using the XML file found at an inputted url
     * 
     * @param url
     */
    public XMLParser(File scriptFile) {

        this.DOC = this.getXMLDocument(scriptFile);

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
            e.printStackTrace();

        } catch (FactoryConfigurationError e) {

        } catch (SAXException e) {

        } catch (IllegalArgumentException e) {

        }

        return doc;

    }

    /**
     * Converts a node list to a stream of nodes
     * 
     * @param nodes the node list
     * @return the stream of nodes in the node list
     */
    protected Stream<Node> nodeListToStream(NodeList nodes) {

        Stream.Builder<Node> builder = Stream.builder();
        for (int i = 0; i < nodes.getLength(); i++) {

            Node node = nodes.item(i);
            builder.add(node);

        }

        return builder.build();

    }

    /**
     * Builds a stream of all elements in the document with a speciifc tag
     * 
     * @param tag the tag to select
     * @return the stream of nodes
     */
    protected Stream<Node> streamByTagName(String tag) {

        NodeList nodes = this.DOC.getElementsByTagName(tag);
        return this.nodeListToStream(nodes);

    }

    /**
     * Builds a stream entry nodes in a node map
     * 
     * @param nodeMap the node map
     * @return the stream of entry nodes
     */
    protected Stream<Node> streamNamedNodeMap(NamedNodeMap nodeMap) {

        Stream.Builder<Node> builder = Stream.builder();
        if (nodeMap != null) {
            for (int i = 0; i < nodeMap.getLength(); i++) {

                Node attr = nodeMap.item(i);
                builder.add(attr);

            }
        }

        return builder.build();

    }

    /**
     * Builds a stream containing all the children of a node
     * 
     * @param node the node
     * @return the stream of children
     */
    protected Stream<Node> streamChildren(Node node) {

        NodeList children = node.getChildNodes();
        return this.nodeListToStream(children);

    }

    /**
     * Gets a node's attribute by name
     * 
     * @param node          the node
     * @param attributeName the name
     * @return the corresponding value
     */
    protected String getAttribute(Node node, String attributeName) {

        NamedNodeMap attributes = node.getAttributes();
        Node attrNode = attributes.getNamedItem(attributeName);
        return attrNode.getNodeValue();

    }

    /**
     * Makes a predicate which filters out all nodes with a given name (tag)
     * 
     * @param nodeName the node name to fileter
     * @param include  whether to include or exclude this node tag type
     * @return a predicate filtering out this node type
     */
    protected Predicate<? super Node> makeNodeNameFilter(boolean include, String... nodeNames) {

        return (Node node) -> {

            String tag = node.getNodeName();
            boolean isTagType = Arrays.asList(nodeNames).contains(tag);

            // only keep in the stream if not including and not node name
            // or if including and does have the node name
            return !(include ^ isTagType);

        };

    }

    protected String getNodeText(Node node) {

        return this.streamChildren(node)
                .filter(this.makeNodeNameFilter(true, "#text"))
                .map(Node::getNodeValue)
                .reduce((a, b) -> {
                    return a + b;
                })
                .get();

    }

    /**
     * @return the document being parsed
     */
    protected Document getDoc() {

        return this.DOC;

    }

    /**
     * Prints out a representation the XML document being parsed
     * 
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     * @throws MalformedURLException
     */
    public void print() throws MalformedURLException, SAXException, IOException, ParserConfigurationException {

        this.deepPrintNode(this.DOC, 0);

    }

    /**
     * Generates text to be printed as a representation of a nodes attributes
     * 
     * @param node
     * @return the text to print
     */
    private String makeAttributeText(Node node) {

        NamedNodeMap attrs = node.getAttributes();
        String attrText = "";

        if (attrs != null && attrs.getLength() != 0) {

            Stream<Node> attrStream = this.streamNamedNodeMap(attrs);

            attrText = attrStream
                    .map(Node::toString)
                    .reduce((a, b) -> {

                        return a + " " + b;

                    })
                    .get();

        }

        return attrText;

    }

    /**
     * Prints a node and all its children
     * 
     * @param node        the node to deep print
     * @param indentDepth the number of indents needed
     */
    protected void deepPrintNode(Node node, int indentDepth) {

        String indent = "";
        for (int i = 0; i < indentDepth; i++) {

            indent += "  ";

        }

        String attrText = this.makeAttributeText(node);
        System.out.println(indent + node.getNodeName() + " " + attrText);

        this.streamChildren(node)
                .filter(this.makeNodeNameFilter(false, "#text"))
                .forEach((Node child) -> {

                    this.deepPrintNode(child, indentDepth + 1);

                });

    }

}
