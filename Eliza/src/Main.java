import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;

import org.w3c.dom.Document;

import Parser.ScriptParser;
import ScriptDataStructure.Script;
import ScriptIO.ScriptFileIO;

public class Main {

    public static void main(String[] args) throws Exception {

        Document scriptDocument = ScriptFileIO.getXMLDocument("./Eliza/src/scripts/8YearOldScript.xml");
        ScriptParser parser = new ScriptParser(scriptDocument);
        Script script = parser.parseScript();

        Reader reader = new FileReader(new File("./Eliza/src/Tests/test.txt"));
        BufferedReader br = new BufferedReader(reader);

        script.print();

        while (br.ready()) {

            String line = br.readLine();
            System.out.println("--" + line + "--");
            String out = script.generateOutput(line);
            System.out.println(out);
            System.out.println();

        }

    }

}
