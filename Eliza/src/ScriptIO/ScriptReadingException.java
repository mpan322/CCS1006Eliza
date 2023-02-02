package ScriptIO;

public class ScriptReadingException extends ScriptIOException {

    private final String ERROR_MSG;

    public ScriptReadingException(String errorMessage) {

        this.ERROR_MSG = errorMessage;

    }

    public void printErrorMessage() {

        System.out.println(this.ERROR_MSG);

    }

}
