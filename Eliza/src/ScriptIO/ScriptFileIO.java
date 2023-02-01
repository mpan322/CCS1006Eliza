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

    public ScriptFileIO(String relativePath) {

        this.FILE_PATH = this.resolveFilePath(relativePath);

    }

    public File getScript() {

        return new File(this.FILE_PATH.toString());

    }

    private Path resolveFilePath(String relativePath) {

        Path scriptPath = null;
        try {

            Path absolutePath = Paths.get(".").toAbsolutePath();
            scriptPath = absolutePath.resolve(relativePath).toAbsolutePath();

        } catch (IOError e) {

            e.printStackTrace();

        } catch (SecurityException e) {

            e.printStackTrace();

        }

        return scriptPath;

    }

}
