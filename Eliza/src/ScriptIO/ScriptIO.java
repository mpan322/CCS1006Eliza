package ScriptIO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOError;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import javax.swing.text.html.CSS;

public class ScriptIO {

    public ScriptIO(String relativePath) {

        try {

            Path scriptPath = this.resolveFilePath(relativePath);
            String rawText = this.parseScriptFile(scriptPath);

        } catch (ScriptIOException e) {
            // TODO: handle exception
        }

    }

    private Path resolveFilePath(String relativePath) throws ScriptPathException {

        Path scriptPath = null;
        try {
            
            Path absolutePath = Paths.get(".").toAbsolutePath();
            scriptPath = absolutePath.resolve(relativePath).toAbsolutePath();

        } catch (IOError e) {
            // TODO: handle exception
        } catch (SecurityException e) {

        }

        return scriptPath;

    }

    private String parseScriptFile(Path scriptPath) throws ScriptReadingException {

        List<String> rawText = null;
        File file = new File(scriptPath.toString());

        try (FileReader reader = new FileReader(file)) {

            ScriptParser parser = new ScriptParser(reader);

        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        } 

        return "";

    }

}
