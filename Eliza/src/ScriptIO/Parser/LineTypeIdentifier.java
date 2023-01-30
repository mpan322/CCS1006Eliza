package ScriptIO.Parser;

/**
 * Identifier of the line type in ELiza scripts
 */
public enum LineTypeIdentifier {

    NONE(null, null),
    PRE_SUBSTITUTION("Pre-substitution", LineTypeIdentifier.NONE),
    POST_SUBSTITUTION("Post-substitution", LineTypeIdentifier.NONE),
    REASSEMBLY_RULE("Reassembly Rule", LineTypeIdentifier.POST_SUBSTITUTION),
    DECOMPOSITION_RULE("Decomposition Rule", LineTypeIdentifier.REASSEMBLY_RULE),
    KEYWORD("Keyword", LineTypeIdentifier.DECOMPOSITION_RULE);

    private final String IDENTIFIER;
    private final LineTypeIdentifier VALID_CHILD;
    private LineTypeIdentifier(String identifier, LineTypeIdentifier validChild) {

        this.IDENTIFIER = identifier;
        this.VALID_CHILD = validChild;
        
    }

    public LineTypeIdentifier getValidChildType() {
        
        return this.VALID_CHILD;

    }

    /**
     * @param input line to check
     * @return whether the line type identifier is the found
     */
    public boolean matches(String input) {
        
        String[] splitInput = input.split(":", 1);
        String potentialIdentfier = splitInput[0];

        return this.IDENTIFIER.equals(potentialIdentfier);
    
    }

}
