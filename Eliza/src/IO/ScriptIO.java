package IO;

import java.io.IOError;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ScriptIO {

    public ScriptIO (String relativePath) {

        try {
            
            List<String> rawWords = this.readRawWords(relativePath);

        } catch (ScriptReadingException e) {
            // TODO: handle exception
        }

    }

    private List<String> readRawWords(String relativePath) throws ScriptReadingException{

        List<String> rawWords = null;

        try {

            Path absolutePath = Paths.get(".").toAbsolutePath();
            Path scriptPath = absolutePath.resolve(relativePath).toAbsolutePath();
            rawWords = Files.readAllLines(scriptPath);    
            
        } catch (IOException e) {

        } catch (SecurityException e) {

        } catch (InvalidPathException e) {

        } catch (IOError e) {

        }

        return rawWords;

    }

}
