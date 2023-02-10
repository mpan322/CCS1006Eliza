package Tests;


import java.io.IOException;

import org.w3c.dom.Document;

import Parser.MalformedScriptException;
import Parser.ScriptParser;
import ScriptElements.Script;

public class KeywordPriorityTest {

    public static void test() throws MalformedScriptException, IOException {
        
        Document doc = ScriptIO.ScriptFileIO.getXMLDocument("./Eliza/src/Tests/testXML/keywordPriorityCheck.xml", "./Eliza/src/scripts/script.xsd");
        ScriptParser parser = new ScriptParser(doc);
        Script script = parser.parseScript();
        script.print();
        

    }

}
