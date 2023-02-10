package ScriptDataStructure;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReassemblyRule extends ScriptElement {

    private final Substituter POST_SUBSTITUTER;
    private final String FORMAT;
    private final boolean CONTAINS_GROUP_REPLACEMENT;

    // potentially change the name from pattern to something else
    public ReassemblyRule(String format, Substituter postSubstituter) {

        this.FORMAT = format;
        this.POST_SUBSTITUTER = postSubstituter;
        this.CONTAINS_GROUP_REPLACEMENT = this.determineIfContainsGroupReplacement(format);

    }

    /**
     * @return whether or not the reassembly rule contains group replacements (ex.
     *         $1)
     */
    public boolean containsGroupReplaces() {

        return this.CONTAINS_GROUP_REPLACEMENT;

    }

    /**
     * Determines whether or not the format contains the a group replacement
     * @param format
     * @return whether or not one is containted
     */
    private boolean determineIfContainsGroupReplacement(String format) {

        Pattern groupReplacePattern = Pattern.compile(".*[$][0-9]+.*");
        Matcher matcher = groupReplacePattern.matcher(format);
        return matcher.find();

    }

    /**
     * Replaces all group replace identifiers (ex. $1) with their associated capture
     * group (ex. group 1)
     * 
     * @param groupCaptures the text captured by each capture group
     * 
     * @return the ressembled output
     */
    public String generateOutput(List<String> groupCaptures) {

        // make a copy of the format to not edit it
        String output = this.FORMAT + "";

        for (int i = 0; i < groupCaptures.size(); i++) {

            // get the ith capture group and replace its identifier
            // (identifiers are indexed from 1 not 0 unlike the list)
            String capture = groupCaptures.get(i);
            output = output.replaceAll("[$]" + (i + 1), capture);

        }

        return output;

    }

    @Override
    protected void print(int indentDepth) {

        String indent = this.makeIndent(indentDepth);
        System.out.println(indent + "REASSEMBLY: " + this.FORMAT);

        if (this.POST_SUBSTITUTER != null) {

            this.POST_SUBSTITUTER.print(indentDepth + 1);

        }

    }

    public Substituter getPostSubstituter() {

        return this.POST_SUBSTITUTER;

    }

}
