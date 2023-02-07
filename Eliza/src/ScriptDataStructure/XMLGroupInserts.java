package ScriptDataStructure;

/**
 * Common regex groups that can be inserted dinto the script when wanted using
 * the given identifier
 */
public enum XMLGroupInserts {

    EXCLUDE_PUNCTUATION_GROUP("NON_PUNC", "([^[?][.]!]*)"),
    ANY_GROUP("ANY", "(.*)"),

    ;

    private final String REGEX;
    private final String IDENTIFIER;

    private XMLGroupInserts(String identifier, String regex) {

        this.REGEX = regex;
        this.IDENTIFIER = identifier;

    }

    public String getRegex() {

        return this.REGEX;

    }

    String getIdentifier() {

        return this.IDENTIFIER;
    
    }

}
