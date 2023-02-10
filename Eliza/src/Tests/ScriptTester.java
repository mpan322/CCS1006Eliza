package Tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;

import Parser.MalformedScriptException;
import Parser.ScriptParser;
import ScriptDataStructure.Script;
import ScriptDataStructure.Substituter;
import ScriptIO.ScriptFileIO;

public class ScriptTester extends Tester {

    public static void test() {

        ScriptTester tester = new ScriptTester();
        tester.runTests();

    }

    public static Script getTestScript() throws MalformedScriptException, IOException {

        Document doc = ScriptFileIO.getXMLDocument("./Eliza/src/Tests/testXML/normalTestScript.xml",
                "./Eliza/src/scripts/script.xsd");
        ScriptParser parser = new ScriptParser(doc);
        return parser.parseScript();

    }

    @Test
    public static void testGoodbyeMessage() throws MalformedScriptException, IOException {

        Script script = ScriptTester.getTestScript();
        script.sayGoodbye();
        System.out.println("Expected: TEXT GOODBYE MESSAGE");

    }

    @Test
    public static void testWelcomeMessage() throws MalformedScriptException, IOException {

        Script script = ScriptTester.getTestScript();
        script.sayWelcome();
        System.out.println("Expected: TEST WELCOME MESSAGE");

    }

    @Test
    public static void testIsQuitKeyword() throws MalformedScriptException, IOException {

        Script script = ScriptTester.getTestScript();
        boolean isSuccess = script.isQuit("TEST IS QUIT 1") && script.isQuit("TEST IS QUIT 2") && script.isQuit("TEST IS QUIT 3");
        System.out.println("Is Success: " + isSuccess);

    }

    @Test 
    public static void testSubstituterBasic() {

        Map<String, String> substitutions = new HashMap<>();
        List<String> inputs = List.of("INPUT 1", "INPUT 2", "INPUT 3");
        List<String> replaces = List.of("REPLACE 1", "REPLACE 2", "REPLACE 3");

        for (int i = 0; i < inputs.size(); i++) {
            
            String input = inputs.get(i);
            String replace = replaces.get(i);
            substitutions.put(input, replace);

        }

        Substituter substituter = new Substituter(substitutions);
        List<String> outputs = substituter.doSubstitutions(inputs);
        
        boolean allEqual = true;
        for (int i = 0; i < inputs.size(); i++) {
            
            String replace = replaces.get(i);
            String output = outputs.get(i);
            allEqual = allEqual && replace.equals(output);

        }

        System.out.println("Is Success: " + allEqual);

    }

}
