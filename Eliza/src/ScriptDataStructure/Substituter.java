package ScriptDataStructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Substituter extends HashMap<String, String> implements ScriptElement {

    public static final Substituter EMPTY = new Substituter();

    /**
     * Perform substitutions on a any number of strings
     * 
     * @param input the strings to act on
     * @return the strings following the substitutions
     */
    public List<String> doSubstitutions(List<String> input) {

        List<String> substitutedStrings = new ArrayList<>();

        for (String string : input) {

            String substituted = this.doSubstitutions(string);
            substitutedStrings.add(substituted);

        }

        return substitutedStrings;

    }

    /**
     * Performs post substitutions on a single string
     * 
     * @param input the string to act on
     * @return the string with the substitutions
     */
    public String doSubstitutions(String input) {

        // make a copy of the input
        String output = "" + input;

        for (Entry<String, String> entry : this.entrySet()) {

            // get the value to be substituted and its replacement
            String value = entry.getKey();
            String replacement = entry.getValue();

            // replace all instances of the value (must be its own word)
            output = output.replaceAll("\\b" + value + "\\b", replacement);

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
