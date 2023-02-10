package Tests;

import org.w3c.dom.Document;

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

        ScriptParser parser = null;
        try {

            Document scriptDocument = ScriptFileIO.getXMLDocument("./Eliza/src/Tests/testXML/" + scriptName,
                    "./Eliza/src/scripts/script.xsd");
            parser = new ScriptParser(scriptDocument);

        } catch (MalformedScriptException e) {

            System.out.println("TEST INCONCLUSIVE: MALFORMED SCRIPT, DOES NOT MATCH SCHEMA " + e.toString());

        } catch (Exception e) {

            System.out.println("TEST FAILED: UNCAUGHT=" + e.toString());
            e.printStackTrace();

        }

        return parser;

    }

    @Test
    public static void TestNoTextEntry() {

        ScriptParser parser = ScriptParserTester.testScriptParser("noTextEntry.xml");

        try {

            parser.parseScript();
            System.out.println("TEST PASSED: NO EXCEPTIONS THROWN");

        } catch (MalformedScriptException e) {

            System.out.println("IDEAL ERROR THROWN - TEST PASSED: " + e.getMessage());

        }


    }

    @Test
    public static void TestMalformedRegex() {

        ScriptParser parser = ScriptParserTester.testScriptParser("malformedRegex.xml");

        try {

            parser.parseScript();
            System.out.println("TEST PASSED: NO EXCEPTIONS THROWN");

        } catch (MalformedScriptException e) {

            System.out.println("IDEAL ERROR THROWN - TEST PASSED: " + e.getMessage());

        }

    }

    @Test
    public static void TestRegexSpecialCharactersInScript() {

        ScriptParser parser = ScriptParserTester.testScriptParser("regexSpecialCharactersInScript.xml");

        try {

            parser.parseScript();
            System.out.println("TEST PASSED: NO EXCEPTIONS THROWN");

        } catch (MalformedScriptException e) {

            System.out.println("IDEAL ERROR THROWN - TEST PASSED: " + e.getMessage());

        }

    }

}
