package ScriptIO;

import java.io.File;
import java.io.IOError;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 
 */
public class ScriptFileIO {

    private final Path FILE_PATH;

    public ScriptFileIO(String relativePath) throws ScriptPathException {

        this.FILE_PATH = this.resolveFilePath(relativePath);

    }

    public File getScript() {

        return new File(this.FILE_PATH.toString());

    }

    private Path resolveFilePath(String relativePath) throws ScriptPathException {

        Path scriptPath = null;
        try {

            Path absolutePath = Paths.get("").toAbsolutePath();
            scriptPath = absolutePath.resolve(relativePath).toAbsolutePath();

        } catch (IOError e) {

            e.printStackTrace();
            throw new ScriptPathException("Exception: IOError trying to make path to file at " + relativePath + " relative to the working directory");

        } catch (SecurityException e) {

            e.printStackTrace();
            throw new ScriptPathException("Exception: Secutiry exception trying to make path to file at " + relativePath + " relative to the working directory");

        } 

        return scriptPath;

    }

}
