package ScriptIO;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * 
 */
public class ScriptFileIO {

    public static Document getXMLDocument(String filePath) {

        Document doc = null;
        File file = new File(filePath);

        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(file);

        } catch (ParserConfigurationException e) {

            System.out.println("getXMLDocument ParserConfiguration Exception");
            e.printStackTrace();

        } catch (IOException e) {

            System.out.println("getXMLDocument IOException");
            e.printStackTrace();

        } catch (FactoryConfigurationError e) {

        } catch (SAXException e) {

        } catch (IllegalArgumentException e) {

        }

        return doc;

    }

}
