package ScriptDataStructure;

import java.util.ArrayList;
import java.util.List;

public class Keyword extends ArrayList<DecompositionRule> {

    private final int PRIORITY;
    private final String KEYWORD;

    public Keyword(String keyword, int priority) {

        this.KEYWORD = keyword;
        this.PRIORITY = priority;

    }


    public String getKeyword() {

        return this.KEYWORD;
    
    }

}
