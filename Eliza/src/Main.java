import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import Parser.ScriptParser;
import ScriptDataStructure.Script;
import ScriptIO.ScriptFileIO;
import ScriptIO.ScriptPathException;

public class Main {

    public static void main(String[] args) throws Exception {

        try {

            ScriptFileIO scriptIO = new ScriptFileIO("./Eliza/src/scripts/8YearOldScript.xml");
            File scriptFile = scriptIO.getScript();
            ScriptParser parser = new ScriptParser(scriptFile);
            Script script = parser.parseScript();

            Reader reader = new FileReader(new File("Eliza/src/Tests/test.txt"));
            BufferedReader br = new BufferedReader(reader);
            while (br.ready()) {

                String line = br.readLine();
                System.out.println("--" + line + "--");
                String out = script.generateOutput(line);
                System.out.println(out);
                System.out.println();

            }

        } catch (ScriptPathException e) {

            e.printStackTrace();

        }

    }

}
