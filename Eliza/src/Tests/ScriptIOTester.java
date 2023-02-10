package Tests;

import java.io.FileNotFoundException;
import java.io.IOException;

import Parser.MalformedScriptException;
import ScriptIO.ScriptFileIO;

public class ScriptIOTester extends Tester {

    public static void test() {

        ScriptIOTester tester = new ScriptIOTester();
        tester.runTests();

    }

    @Test
    public static void testNotFollowSchema() {

        try {

            ScriptFileIO.getXMLDocument("./Eliza/src/Tests/testXML/notFollowingSchema.xml",
                    "./Eliza/src/scripts/script.xsd");

        } catch (MalformedScriptException e) {

            System.out.println("TEST PASSED WITH DESIRED EXCEPTION: " + e.getMessage());

        } catch (Exception e) {

            System.out.println("TEST FAILED: " + e.getMessage());

        }

    }

    @Test
    public static void testNonExistantScriptFile() {

        try {

            ScriptFileIO.getXMLDocument("./Eliza/src/Tests/testXML/nonExist.xml", "./Eliza/src/scripts/script.xsd");

        } catch (FileNotFoundException e) {

            System.out.println("TEST PASSED WITH DESIRED EXCEPTION: " + e.getMessage());

        } catch (Exception e) {

            System.out.println("TEST FAILED: " + e.getMessage());

        }

    }

    @Test
    public static void testMalformedXMLFileScript() {

        try {

            ScriptFileIO.getXMLDocument("./Eliza/src/Tests/testXML/malformedXML.xml", "./Eliza/src/scripts/script.xsd");

        } catch (MalformedScriptException e) {

            System.out.println("TEST PASSED WITH DESIRED EXCEPTION: " + e.toString());

        } catch (Exception e) {

            System.out.println("TEST FAILED: " + e.toString());

        }

    }

    @Test
    public static void testNullScriptFilePath() {

        try {

            ScriptFileIO.getXMLDocument(null, "./Eliza/src/scripts/script.xsd");

        } catch (IOException e) {

            System.out.println("TEST PASSED WITH DESIRED EXCEPTION: " + e.toString());

        } catch (Exception e) {

            System.out.println("TEST FAILED: " + e.toString());

        }

    }

    @Test
    public static void testNoRootElement() {

        try {

            ScriptFileIO.getXMLDocument("./Eliza/src/Tests/testXML/malformedXML.xml", "./Eliza/src/scripts/script.xsd");

        } catch (MalformedScriptException e) {

            System.out.println("TEST PASSED WITH DESIRED EXCEPTION: " + e.toString());

        } catch (Exception e) {

            System.out.println("TEST FAILED: " + e.toString());

        }

    }

    @Test
    public static void testNonXMLScriptFile() {

        try {

            ScriptFileIO.getXMLDocument("./Eliza/src/Tests/testXML/nonXML.txt", "./Eliza/src/scripts/script.xsd");
            System.out.println("TEST FAILED: Desired error not thrown");

        } catch (IOException e) {

            System.out.println("TEST PASSED WITH DESIRED EXCEPTION: " + e.toString());

        } catch (Exception e) {

            System.out.println("TEST FAILED: " + e.toString());

        }

    }

}
