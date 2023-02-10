
import java.io.IOException;
import java.util.Scanner;

import org.w3c.dom.Document;

import Parser.MalformedScriptException;
import Parser.ScriptParser;
import ScriptElements.Script;
import ScriptIO.ScriptFileIO;

public class Main {

    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {

        try {

            // choose script
            int choice = Main.chooseScript();
            String choiceFile = Main.getChoicesFileName(choice);

            // setup script
            Document scriptDocument = ScriptFileIO.getXMLDocument("./scripts/" + choiceFile,
                    "./scripts/script.xsd");
            ScriptParser parser = new ScriptParser(scriptDocument);
            Script script = parser.parseScript();


            // interactive eliza
            System.out.println("STARTING ELIZA ENGINE WITH SCRIPT: " + choiceFile + "\n");
            script.sayWelcome();
            Main.doChatLoop(script);
            script.sayGoodbye();

        } catch (MalformedScriptException e) {

            System.out.println("MALFORMED SCRIPT EXCEPTION: " + e.toString());

        } catch (IOException e) {

            System.out.println("IOEXCEPTION: " + e.toString());

        } finally {

            Main.SCANNER.close();

        }

    }

    private static String getChoicesFileName(int choice) {

        switch (choice) {
            case 1:
                return "8YearOldScript.xml";

            case 2:
                return "therapistScript.xml";

            case 3:
                return "yodaScript.xml";

            // this code is unreachable in the context of the program
            default:
                return null;
        }

    }

    /**
     * Prints out the options for scripts and their corresponding numbers
     */
    private static void printScriptOptions() {

        System.out.println("Choose a script: (Enter corresponding number)");
        System.out.println("\t 1) 8 year old script");
        System.out.println("\t 2) therapist script");
        System.out.println("\t 3) yoda script");

    }

    /**
     * Repeatedly prompts the user to choose a script to use until a valid script is
     * chosen
     * 
     * @return number corresponding to the script chosen
     */
    private static int chooseScript() {

        int choice = 0;

        boolean quit = false;

        while (!quit) {

            Main.printScriptOptions();

            try {

                choice = Main.SCANNER.nextInt();
                if (choice < 1 || choice > 3) {

                    System.out.println("Error: choice of " + choice + " output of bounds");

                } else {

                    quit = true;
                    System.out.println("Choice: " + choice);

                }

            } catch (Exception e) {

                System.out.println("Invalid input, choice must be 1, 2, or 3");
                Main.SCANNER.next();

            }

        }


        return choice;

    }

    /**
     * Performs main eliza chat loop
     * 
     * @param script
     */
    private static void doChatLoop(Script script) {

        try {

            boolean quit = false;
            Main.SCANNER.next();

            while (!quit) {

                String input = Main.SCANNER.nextLine();

                // check if next keyword is quit - or end test
                if (!script.isQuit(input) && !input.equals("$END_TEST$")) {

                    // if not quit respond
                    String output = script.generateOutput(input);
                    System.out.println(output);

                } else {

                    quit = true;

                }

            }

        } catch (Exception e) {

            e.printStackTrace();
            System.out.println(e);

        }

    }

}
