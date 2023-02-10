package ScriptIO;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import Parser.MalformedScriptException;

/**
 * Class containing a collection of static methods for IO of an xml document
 */
public class ScriptFileIO {

    private static Validator makeSchemaValidator(String schemaPath) throws SAXException, IOException {

        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = factory.newSchema(new File(schemaPath));
        Validator validator = schema.newValidator();
        return validator;

    }

    private static void checkIsValidXMLFilePath(String filePath) throws IOException {

        if (filePath == null) {

            throw new IOException("Null filePath input");

        }

        int filePathLen = filePath.length();
        String possibleXMLExtension = filePath.substring(filePathLen - 4, filePathLen);

        if (!possibleXMLExtension.equals(".xml")) {

            throw new IOException("Non .xml file inputted");

        }

    }

    public static Document getXMLDocument(String filePath, String schemaPath)
            throws MalformedScriptException, IOException {

        Document doc = null;

        ScriptFileIO.checkIsValidXMLFilePath(filePath);

        File file = new File(filePath);

        try {

            // validate against the schema
            Validator validator = ScriptFileIO.makeSchemaValidator(schemaPath);
            validator.validate(new StreamSource(file));

            // creates a new document builder factory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            // build new XML document
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(file);

        } catch (ParserConfigurationException e) {

            System.out.println("getXMLDocument ParserConfiguration Exception");
            e.printStackTrace();

        } catch (SAXException e) {

            throw new MalformedScriptException("Malformed script, not following schema");

        }

        return doc;

    }

}
