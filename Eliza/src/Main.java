import java.io.File;
import java.util.List;

import Parser.ScriptParser;
import ScriptIO.ScriptFileIO;

public class Main {
    
    public static void main(String[] args) {
        
        ScriptFileIO scriptIO = new ScriptFileIO("./src/scripts/script.xml");
        File scriptFile = scriptIO.getScript();
        ScriptParser parser = new ScriptParser(scriptFile);
        parser.parseScript();

    }

}
