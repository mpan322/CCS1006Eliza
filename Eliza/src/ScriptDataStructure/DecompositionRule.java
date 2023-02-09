package ScriptDataStructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DecompositionRule implements ScriptElement {

    private final String RAW_PATTERN;
    private String pattern;
    private final List<ReassemblyRule> REASSEMBLY_RULES = new ArrayList<>();

    public DecompositionRule(String pattern, List<ReassemblyRule> reassemblyRules) {

        this.RAW_PATTERN = pattern;

        String regexPattern = this.parseRegexInsertIdentifiers(pattern);
        this.pattern = ".*" + regexPattern + ".*";
        // allow extra spaces for typos
        this.pattern = this.pattern.replaceAll("\s", "\\\\s+");
        this.pattern = this.pattern.toLowerCase();
        this.REASSEMBLY_RULES.addAll(reassemblyRules);

    }

    public void defaultParseStep() {

        this.pattern = "^.*$";

    }

    public ReassemblyRule chooseReassemblyRule() {

        Random rand = new Random();
        int randInt = rand.nextInt(this.REASSEMBLY_RULES.size());
        return this.REASSEMBLY_RULES.get(randInt);

    }

    /**
     * Determines whether the decomposition rule's pattern matches to an inputted
     * string
     * 
     * @param input
     * @return whether or not it matches
     */
    public boolean matches(String input) {

        Pattern pattern = Pattern.compile(this.pattern);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();

    }

    @Override
    public String generateOutput(String input) {

        ReassemblyRule reassemblyRule = this.chooseReassemblyRule();
        String reassemblyFormat = reassemblyRule.getFormat();

        String output;
        if(!reassemblyRule.containsGroupReplaces()) {

            output = reassemblyFormat; 

        } else {

            output = input.replaceAll(this.pattern, reassemblyFormat);

        }

        output = reassemblyRule.doPostSubstitutions(output);

        return output;

    }

    @Override
    public void print(int indentDepth) {

        String indent = this.makeIndent(indentDepth);
        System.out.println(indent + "DECOMPOSITION: " + this.RAW_PATTERN);
        this.REASSEMBLY_RULES.forEach((reassembly) -> {

            reassembly.print(indentDepth + 1);

        });

    }

}
