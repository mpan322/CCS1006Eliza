package ScriptElements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Keyword extends ScriptElement {

    private final int PRIORITY;
    private final String KEYWORD;
    private final Pattern KEYWORD_PATTERN;
    private final List<DecompositionRule> DECOMPOSITION_RULES = new ArrayList<>();
    private final DecompositionRule DEFAULT_DECOMPOSITION_RULE;

    public Keyword(String keyword, int priority, List<DecompositionRule> decompositionRules,
            DecompositionRule defaultDecomposition) {

        this.KEYWORD = keyword.toLowerCase();
        this.PRIORITY = priority;

        String patternString = ".*\\b" + this.KEYWORD + "\\b.*";

        // pattern will match the keyword anywhere in a sentence
        Pattern keywordPattern;
        try {

            keywordPattern = Pattern.compile(patternString);

        } catch (PatternSyntaxException e) {

            System.out.println("WARNING: Malformed regex " + patternString + " found in keyword");
            keywordPattern = Pattern.compile("/MALFORMED_REGEX_ERROR/");

        }

        this.KEYWORD_PATTERN = keywordPattern;

        this.DECOMPOSITION_RULES.addAll(decompositionRules);
        this.DEFAULT_DECOMPOSITION_RULE = defaultDecomposition;

    }

    public String getKeyword() {

        return this.KEYWORD;

    }

    public int getPriority() {

        return this.PRIORITY;

    }

    /**
     * Determines if the input contains the keyword (allows for regex keywords)
     * 
     * @param input
     * @return whether it contains the keyword
     */
    public boolean containsKeyword(String input) {

        Matcher matcher = this.KEYWORD_PATTERN.matcher(input);
        return matcher.matches();

    }

    /**
     * Finds the first decomposition rule matching the input
     * 
     * @param input
     * @return the matching decomposition rule
     */
    public DecompositionRule findDecompositionRule(String input) {

        for (DecompositionRule decompositionRule : DECOMPOSITION_RULES) {

            if (decompositionRule.matches(input)) {

                return decompositionRule;

            }

        }

        return this.DEFAULT_DECOMPOSITION_RULE;

    }

    @Override
    protected void print(int indentDepth) {

        String indent = this.makeIndent(indentDepth);
        System.out.println(indent + "KEYWORD: " + this.KEYWORD);
        this.DECOMPOSITION_RULES.forEach((decomp) -> {

            decomp.print(indentDepth + 1);

        });

        this.DEFAULT_DECOMPOSITION_RULE.print(indentDepth + 1);

    }

}
