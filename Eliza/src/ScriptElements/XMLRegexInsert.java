package ScriptElements;

/**
 * Common regex groups that can be inserted dinto the script when wanted using
 * the given identifier
 */
public enum XMLRegexInsert {

    ANY_GROUP("ANY", "(.*)"), // matches anything (does not match from start of line)
    ANY_START_GROUP("ANY_START", "(?:^)(.*)"), // matches anything from the start of the line to any

    NEXT_WORD_GROUP("NEXT_WORD", "\\\\b((?:\\\\w+))\\\\b"), // selects the next word in a line
    QUESTION_WORDS("Q_WORDS", "(?:(?:who)|(?:when)|(?:what)|(?:where)|(?:why)|(?:how))"), // matches all question words

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
