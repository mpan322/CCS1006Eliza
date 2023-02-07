package ScriptDataStructure;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Keyword implements ScriptElement {

    private final int PRIORITY;
    private final String KEYWORD;
    private final Pattern KEYWORD_PATTERN;
    private final List<DecompositionRule> DECOMPOSITION_RULES = new ArrayList<>();
    private final DecompositionRule DEFAULT_DECOMPOSITION_RULE;

    public Keyword(String keyword, int priority, List<DecompositionRule> decompositionRules, DecompositionRule defaultDecomposition) {

        this.KEYWORD = keyword;
        this.PRIORITY = priority;

        // pattern will match the keyword anywhere in a sentence
        this.KEYWORD_PATTERN = Pattern.compile(".*\\b" + keyword + "\\b.*");
        this.DECOMPOSITION_RULES.addAll(decompositionRules);
        this.DEFAULT_DECOMPOSITION_RULE = defaultDecomposition;

    }


    public String getKeyword() {

        return this.KEYWORD;
    
    }

    public int getPriority() {

        return this.PRIORITY;

    }

    public boolean containsKeyword(String input) {

        Matcher matcher = this.KEYWORD_PATTERN.matcher(input);
        return matcher.matches();

    }


    private DecompositionRule chooseDecompositionRule(String input) {

        for (DecompositionRule decompositionRule : DECOMPOSITION_RULES) {
            
            if(decompositionRule.matches(input)) {

                return decompositionRule;

            }

        }

        return this.DEFAULT_DECOMPOSITION_RULE;

    }


    @Override
    public String generateOutput(String input) {

        DecompositionRule decompositionRule = this.chooseDecompositionRule(input);
        String output = decompositionRule.generateOutput(input);
        return output;

    }


    @Override
    public void print(int indentDepth) {

        String indent = this.makeIndent(indentDepth);
        System.out.println(indent + "KEYWORD: " + this.KEYWORD);
        this.DECOMPOSITION_RULES.forEach((decomp) -> {

            decomp.print(indentDepth + 1);

        });

        this.DEFAULT_DECOMPOSITION_RULE.print(indentDepth + 1);
        
    }


}
