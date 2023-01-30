package ScriptIO.Parser;

import java.io.File;
import java.util.Scanner;

/**
 * ScriptParser
 */
public class ScriptParser {

    private final File FILE;
    private LineTypeIdentifier lineTypeDepth = LineTypeIdentifier.NONE;

    public ScriptParser(File scriptFile) {

        this.FILE = scriptFile;

    }

    public void parseScript() {

        try (Scanner scanner = new Scanner(this.FILE)) {

            while (scanner.hasNextLine()) {

                String line = scanner.nextLine();

            }

        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    public LineTypeIdentifier determineLineType(String line) throws ScriptSchemaError {

        for (LineTypeIdentifier type : LineTypeIdentifier.values()) {
            
            if(type.matches(line)) {

                return type;

            }

        }

        throw new ScriptSchemaError();
        
    }

}