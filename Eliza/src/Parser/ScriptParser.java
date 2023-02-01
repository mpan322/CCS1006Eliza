package Parser;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.print.Doc;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * ScriptParser
 */
public class ScriptParser {

    private final Document SCRIPT_XML;

    public ScriptParser(File scriptFile) {

        this.SCRIPT_XML = this.getXMLDocument(scriptFile);

    }

    private Map<String, NodeList> getTagNodes() {

        Map<String, NodeList> tagNodes = new HashMap<>();
        for (ScriptXMLTags XMLTag : ScriptXMLTags.values()) {
            
            String tag = XMLTag.getTag();
            NodeList nodes = this.SCRIPT_XML.getElementsByTagName(tag);
            tagNodes.put(tag, nodes);

        }

        return tagNodes;

    }

    public void parseScript() {

        Map<String, NodeList> tagNodes = this.getTagNodes();

        tagNodes.forEach((tag, nodes) -> {

            

        });

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