package Tests;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.List;
import java.util.function.Predicate;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import Parser.MalformedScriptException;
import Parser.ScriptParser;
import ScriptIO.ScriptFileIO;

public class ScriptParserTester extends Tester {

    public static void test() {

        ScriptParserTester tester = new ScriptParserTester();
        tester.runTests();

    }

    /**
     * Make a script parser given a file name
     * 
     * @param scriptName
     * @return a script parser for that script
     */
    public static ScriptParser testScriptParser(String scriptName) {

        Document scriptDocument = ScriptFileIO.getXMLDocument("./Eliza/src/Tests/testXML/" + scriptName);
        ScriptParser parser = new ScriptParser(scriptDocument);

        try {

            parser.parseScript();
            System.out.println("TEST PASSED: No Exceptions Thrown");

        } catch (MalformedScriptException e) {

            System.out.println("TEST PASSED: THROWS " + e.toString());

        } catch (Exception e) {

            System.out.println("TEST FAILED: UNCAUGHT=" + e.toString());
            e.printStackTrace();

        }

        return parser;

    }

    @Test
    public static void TestNoTextEntry() {

        ScriptParserTester.testScriptParser("noTextEntry.xml");

    }

    @Test
    public static void TestNonScriptXSD() {

        ScriptParserTester.testScriptParser("nonScriptXSD.xml");

    }

}
