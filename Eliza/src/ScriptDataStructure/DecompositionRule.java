package ScriptDataStructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DecompositionRule implements ScriptElement {

    private final String RAW_PATTERN;
    private final Pattern PATTERN;
    private final List<ReassemblyRule> REASSEMBLY_RULES = new ArrayList<>();

    public DecompositionRule(String pattern, List<ReassemblyRule> reassemblyRules) {

        this.RAW_PATTERN = pattern;

        String regexPattern = this.parseRegexInsertIdentifiers(pattern);
        regexPattern = ".*" + regexPattern + ".*";
        // allow extra spaces 
        regexPattern = regexPattern.replaceAll("\s", "\\\\s+");
        regexPattern = regexPattern.toLowerCase();

        this.PATTERN = Pattern.compile(regexPattern);

        this.REASSEMBLY_RULES.addAll(reassemblyRules);

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

        Matcher matcher = this.PATTERN.matcher(input);
        return matcher.matches();

    }

    @Override
    public String generateOutput(String input) {

        ReassemblyRule reassemblyRule = this.chooseReassemblyRule();
        String reassemblyFormat = reassemblyRule.getFormat();
        
        // make the output a copy of the reassembly format so it does not modify the format
        String output = reassemblyFormat + "";

        Matcher decompositionMatcher = this.PATTERN.matcher(input);
        decompositionMatcher.matches();
        
        int groupCount = decompositionMatcher.groupCount();
        for (int i = 0; i < groupCount; i++) {
            
            String group = decompositionMatcher.group(i + 1);
            output = output.replaceAll(i + "", group);

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
