package ScriptDataStructure;

import java.util.List;

public class DecompositionRule {

    private final String PATTERN;
    private final List<ReassemblyRule> REASSEMBLY_RULE;

    public DecompositionRule(String rawRule, List<ReassemblyRule> reassemblyRules) {

        String[] splitRaw = rawRule.split(":", 1);
        
        this.PATTERN = splitRaw[0];
        this.REASSEMBLY_RULE = reassemblyRules;

    }

}
