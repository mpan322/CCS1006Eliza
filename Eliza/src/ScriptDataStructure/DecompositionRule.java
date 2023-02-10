package ScriptDataStructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class DecompositionRule extends ScriptElement {

    private final String RAW_PATTERN;
    private final Pattern PATTERN;
    private final List<ReassemblyRule> REASSEMBLY_RULES;
    private int cycleStep;

    public DecompositionRule(String patternString, List<ReassemblyRule> reassemblyRules) {

        this.RAW_PATTERN = patternString;

        // replace all insert identifeirs with their correspondign regex (ex. $$ANY$$)
        String regexPattern = super.replaceRegexInsertIdentifiers(patternString);

        // pre parsing so the pattern is more flexible (lowercase, extra spaces, and
        // any leading / trailing text)
        regexPattern = ".*" + regexPattern + ".*";
        regexPattern = regexPattern.replaceAll("\s", "\\\\s+");
        regexPattern = regexPattern.toLowerCase();

        // try to assign pattern and if malformed regex then error
        Pattern pattern;
        try {

            pattern = Pattern.compile(regexPattern);

        } catch (PatternSyntaxException e) {
            
            System.out.println("WARNING: Malformed regex " + regexPattern + " found in decomposition rule");
            pattern = Pattern.compile("/MALFORMED_REGEX_ERROR/");

        }

        this.PATTERN = pattern;
        this.REASSEMBLY_RULES = reassemblyRules;

        // assing the starting cycle step to a random reassembly rule index
        Random rand = new Random();
        this.cycleStep = rand.nextInt(this.REASSEMBLY_RULES.size());

    }

    /**
     * @return a randomly chosen reassembly rule
     */
    public ReassemblyRule chooseReassemblyRule() {

        ReassemblyRule chosenRule = this.REASSEMBLY_RULES.get(cycleStep);
        cycleStep++;
        if(cycleStep == this.REASSEMBLY_RULES.size()) {

            cycleStep = 0;

        }

        return chosenRule;

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

    /**
     * Gets all strings captured by the capture groups in the decomposition rule
     * 
     * @param input the string
     * @return the strings captured by the groups
     */
    public List<String> getCaptureGroups(String input) {

        List<String> captures = new ArrayList<>();

        Matcher decompositionMatcher = this.PATTERN.matcher(input);
        decompositionMatcher.matches();

        // store all strings captured by groups in list
        int groupCount = decompositionMatcher.groupCount();
        for (int i = 1; i <= groupCount; i++) {

            String group = decompositionMatcher.group(i);
            captures.add(group);

        }

        return captures;

    }

    @Override
    protected void print(int indentDepth) {

        String indent = this.makeIndent(indentDepth);
        System.out.println(indent + "DECOMPOSITION: " + this.RAW_PATTERN);
        this.REASSEMBLY_RULES.forEach((reassembly) -> {

            reassembly.print(indentDepth + 1);

        });

    }

}
