package ScriptDataStructure;

/**
 * Common regex groups that can be inserted dinto the script when wanted using
 * the given identifier
 */
public enum XMLRegexInsert {

    EXCLUDE_TERMINAL_PUNCTUATION_GROUP("NON_TERM_PUNC", "([^[?][.]!]*)"), // matches any non punctuation character
    ANY_GROUP("ANY", "(.*)"), // matches anything until $END_ANY$ or the end of the line
    NEXT_WORD_GROUP("NEXT_WORD", "((?:\\\\w+){1})"), // selects the next word in a line
    QUESTION_WORDS("Q_WORDS", "((?:who)|(?:when)|(?:what)|(?:where)|(?:why)|(?:how))"), // matches all question words

    ;

    private final String REGEX;
    private final String IDENTIFIER;

    private XMLRegexInsert(String identifier, String regex) {

        this.REGEX = regex;
        this.IDENTIFIER = identifier;

    }

    public String getRegex() {

        return this.REGEX;

    }

    public String getIdentifier() {

        return this.IDENTIFIER;

    }

}
