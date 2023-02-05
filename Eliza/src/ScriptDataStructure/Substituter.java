package ScriptDataStructure;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Substituter extends HashMap<String, String> implements ScriptElement{

    private static final String INPUT_ATTR = "input";
    private static final String REPLACE_ATTR = "replace";


    @Override
    public String generateOutput(String input) {

        this.forEach((String value, String replacement) -> {

            Pattern pattern = Pattern.compile(value);
            Matcher matcher = pattern.matcher(input);

            matcher.replaceAll(replacement);

        });

        return input;

    }

    @Override
    public void print(int indentDepth) {

        String indent = this.makeIndent(indentDepth);
        this.forEach((value, replace) -> {

            System.out.println(indent + "SUBTITUTION: " + value + " " + replace);

        });

    }

}
