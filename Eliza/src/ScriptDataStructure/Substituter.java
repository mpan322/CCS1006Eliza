package ScriptDataStructure;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Substituter extends HashMap<String, String> {

    private static final String INPUT_ATTR = "input";
    private static final String REPLACE_ATTR = "replace";

    public void doSubstitutions(String input){

        this.forEach((String value, String replacement) -> {

            Pattern pattern = Pattern.compile(value);
            Matcher matcher = pattern.matcher(input);

            matcher.replaceAll(replacement);

        });

    }

}
