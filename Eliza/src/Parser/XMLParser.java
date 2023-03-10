package Parser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Class providing utitlies for parsing XML documents
 */
public class XMLParser {

    private final Document DOC;

    // filters out nodes with names that have #'s ex. #document, #text, #comment
    protected static final Predicate<Node> NON_TAG_FILTER = (Node node) -> !node.getNodeName().contains("#");

    // filters out all text nodes
    protected static final Predicate<Node> TEXT_NODE_FILTER = (Node node) -> !node.getNodeName().equals("#text");

    /**
     * Creates a new XMLDocument parser using the XML file found at an inputted url
     * 
     * @param url
     */
    public XMLParser(Document document) {

        this.DOC = document;

    }

    /**
     * @return the document being parsed
     */
    protected Document getDoc() {

        return this.DOC;

    }

    /**
     * Gets the text from the first tag a specifc type found
     * 
     * @param tagName the tag type
     * @return the text is contains
     */
    protected String getTextFromFirstTagWithName(String tagName) {

        return this.streamByTagName(tagName)
                .limit(1) // limit to first one found (avoid malformed)
                .map(this::getNodeText)
                .toList().get(0);

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
     * Builds a stream of all elements in the document with a speciifc tag. If no
     * nodes
     * are found then null is returned
     * 
     * @param tag the tag to select
     * @return the stream of nodes
     */
    protected Stream<Node> streamByTagName(String tag) {

        NodeList nodes = this.DOC.getElementsByTagName(tag);

        if (nodes != null) {

            return this.nodeListToStream(nodes);

        } else {

            return null;

        }

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
     * @return a predicate filtering out this node type
     */
    protected Predicate<? super Node> makeNodeNameFilter(String... nodeNames) {

        return (Node node) -> {

            String tag = node.getNodeName();
            boolean isTagType = Arrays.asList(nodeNames).contains(tag);
            return !isTagType;

        };

    }

    protected String getNodeText(Node node) {

        if (node.getFirstChild() != null) {

            return this.streamChildren(node)
                    .filter(XMLParser.TEXT_NODE_FILTER.negate())
                    .map(Node::getNodeValue)
                    .reduce(String::concat)
                    .get();

        } else {

            return "";

        }

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
                    .reduce((a, b) -> a + " " + b)
                    .get();

        }

        return attrText;

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

        Node root = this.DOC.getDocumentElement();
        this.deepPrintNode(root, 0);

    }

    /**
     * Prints a node and all its children
     * 
     * @param node        the node to deep print
     * @param indentDepth the number of indents needed
     */
    private void deepPrintNode(Node node, int indentDepth) {

        String indent = "";
        for (int i = 0; i < indentDepth; i++) {

            indent += "  ";

        }

        String attrText = this.makeAttributeText(node);
        System.out.println(indent + node.getNodeName() + " " + attrText);

        this.streamChildren(node)
                .filter(XMLParser.NON_TAG_FILTER)
                .forEach((Node child) -> {

                    this.deepPrintNode(child, indentDepth + 1);

                });

    }

}
