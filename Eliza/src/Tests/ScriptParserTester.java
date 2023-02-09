package Tests;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import Parser.ScriptParser;
import ScriptIO.ScriptFileIO;

public class ScriptParserTester {
    
    public static void main(String[] args) throws MalformedURLException, SAXException, IOException, ParserConfigurationException {
        

        Document scriptDocument = ScriptFileIO.getXMLDocument("./Eliza/src/Tests/parserTest.xml");
        ScriptParser parser = new ScriptParser(scriptDocument);
        parser.print();

    }

}
