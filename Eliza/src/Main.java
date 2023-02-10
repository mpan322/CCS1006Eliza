import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.NumberFormat.Style;
import java.util.Scanner;

import org.w3c.dom.Document;

import Parser.MalformedScriptException;
import Parser.ScriptParser;
import ScriptDataStructure.Script;
import ScriptIO.ScriptFileIO;

public class Main {

    public static void main(String[] args) {

        try {

            Document scriptDocument = ScriptFileIO.getXMLDocument("./Eliza/src/scripts/8YearOldScript.xml",
                    "./Eliza/src/scripts/script.xsd");
            ScriptParser parser = new ScriptParser(scriptDocument);
            Script script = parser.parseScript();

            // interactive eliza
            System.out.println("STARTING ELIZA ENGINE...");
            script.sayWelcome();
            Main.doChatLoop(script);
            script.sayGoodbye();

        } catch (MalformedScriptException e) {

            System.out.println("MALFORMED SCRIPT EXCEPTION: " + e.toString());

        } catch (IOException e) {

            System.out.println("IOEXCEPTION: " + e.toString());

        }

    }

    /**
     * Performs main eliza chat loop
     * @param script
     */
    private static void doChatLoop(Script script) {

        try (Scanner scanner = new Scanner(System.in)) {

            boolean quit = false;
            while (!quit) {

                String input = scanner.nextLine();
        
                // check if next keyword is quit
                if(!script.isQuit(input)) {

                    // if not quit respond
                    String output = script.generateOutput(input);
                    System.out.println(output);

                } else {

                    quit = true;

                }


            }

        } catch (Exception e) {

            System.out.println(e);

        }

    }

}
