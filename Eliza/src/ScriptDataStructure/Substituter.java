package ScriptDataStructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Substituter implements ScriptElement {

    public static final Substituter EMPTY = new Substituter(new HashMap<String, String>());
    private final Map<String, String> SUBSTITUTIONS;

    public Substituter(Map<String, String> substitutions) {

        this.SUBSTITUTIONS = substitutions;

    }

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

        for (Entry<String, String> entry : this.SUBSTITUTIONS.entrySet()) {

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
        this.SUBSTITUTIONS.forEach((value, replace) -> {

            System.out.println(indent + "SUBTITUTION: " + value + " " + replace);

        });

    }

}
