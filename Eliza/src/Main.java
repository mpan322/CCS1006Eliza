import java.io.File;
import java.util.List;

import Parser.ScriptParser;
import ScriptDataStructure.Script;
import ScriptIO.ScriptFileIO;
import ScriptIO.ScriptPathException;

public class Main {

    public static void main(String[] args) {

        try {

            ScriptFileIO scriptIO = new ScriptFileIO("./Eliza/src/scripts/script.xml");
            File scriptFile = scriptIO.getScript();
            ScriptParser parser = new ScriptParser(scriptFile);
            // Script script = parser.parseScript();

            // script.print(0);

        } catch (ScriptPathException e) {

            e.printStackTrace();

        }

    }

}
