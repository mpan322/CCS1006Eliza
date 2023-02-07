package ScriptDataStructure;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReassemblyRule implements ScriptElement {

    private final Substituter POST_SUBSTITUTER;
    private final String FORMAT;
    private final boolean CONTAINS_GROUP_REPLACEMENT;

    // potentially change the name from pattern to something else
    public ReassemblyRule(String format, Substituter postSubstituter) {

        this.FORMAT = format;
        this.POST_SUBSTITUTER = postSubstituter;
        this.CONTAINS_GROUP_REPLACEMENT = this.determineIfContainsGroupReplacement(format);

    }

    private boolean determineIfContainsGroupReplacement(String format) {

        Pattern groupReplacePattern = Pattern.compile(".*[$][0-9]+.*");
        Matcher matcher = groupReplacePattern.matcher(format);
        return matcher.find();

    }

    public boolean containsGroupReplaces() {

        return this.CONTAINS_GROUP_REPLACEMENT;

    }

    public String getFormat() {

        return this.FORMAT;

    }

    @Override
    public String generateOutput(String inputText) {

        System.out.println("WRONG REASSEMBLY RULE GENERATE METHOD");
        return "";

    }

    @Override
    public void print(int indentDepth) {

        String indent = this.makeIndent(indentDepth);
        System.out.println(indent + "REASSEMBLY: " + this.FORMAT);

        if (this.POST_SUBSTITUTER != null) {

            this.POST_SUBSTITUTER.print(indentDepth + 1);

        }

    }

    public String doPostSubstitutions(String output) {

        return this.POST_SUBSTITUTER.generateOutput(output);

    }

}
