package ScriptDataStructure;

import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Keyword extends ArrayList<DecompositionRule> {

    private final int PRIORITY;
    private final String KEYWORD;
    private final Pattern KEYWORD_PATTERN;

    public Keyword(String keyword, int priority) {

        this.KEYWORD = keyword;
        this.PRIORITY = priority;
        this.KEYWORD_PATTERN = Pattern.compile("\\b" + priority + "\\b");

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


    public DecompositionRule chooseDecompositionRule() {

        return null;

    }


}
