package ScriptDataStructure;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Substituter extends HashMap<String, String> implements ScriptElement {

    private static final String INPUT_ATTR = "input";
    private static final String REPLACE_ATTR = "replace";

    public static final Substituter EMPTY = new Substituter();

    @Override
    public String generateOutput(String input) {

        String output = input + "";

        for (Entry<String, String> entry : this.entrySet()) {

            String value = entry.getKey();
            String replacement = entry.getValue();
            output = output.replaceAll("\\b" + value + "\\b", "__" + replacement + "__");

        }

        return output;

    }

    @Override
    public void print(int indentDepth) {

        String indent = this.makeIndent(indentDepth);
        this.forEach((value, replace) -> {

            System.out.println(indent + "SUBTITUTION: " + value + " " + replace);

        });

    }

    @Override
    public String put(String key, String value) {

        String regexKey = "\\b" + key + "\\b";
        return super.put(regexKey, value);

    }

}
