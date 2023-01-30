package ScriptIO.Parser;

/**
 * Identifier of the line type in ELiza scripts
 */
public enum LineTypeIdentifier {

    NONE(null),
    KEYWORD("Keyword"),
    DECOMPOSITION_RULE("Decomposition Rule"),
    REASSEMBLY_RULE("Reassembly Rule"),
    PRE_SUBSTITUTION("Pre-substitution"),
    POST_SUBSTITUTION("Post-substitution");

    private final String IDENTIFIER;
    private LineTypeIdentifier(String identifier) {

        this.IDENTIFIER = identifier;
        
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
