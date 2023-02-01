package Parser;

import java.util.List;

/**
 * Identifier of the line type in ELiza scripts
 */
public enum ScriptXMLIdentifiers {

    WELCOME_MSG("welcomeMsg"),
    GOODBYE_MSG("goodbyeMsg"),
    PRE_SUBSTITUTION("preSub"),
    POST_SUBSTITUTION("postSub"),
    SUBSTITUTION("preSub"),
    REASSEMBLY_RULE("reassembly"),
    DECOMPOSITION_RULE("decomposition"),
    KEYWORD("keyword"),
    QUIT_KEYWORDS("quitKeywords"),
    QUIT_KEYWORD("quitKeyword"),
    NONE(null);


    private final String TAG;
    private final List<String> ATTRIBUTES;
    private ScriptXMLIdentifiers(String identifier, String ... attributes) {

        this.TAG = identifier;
        this.ATTRIBUTES = List.of(attributes);
        
    }

    public String getTag() {

        return this.TAG;

    }

    public List<String> getAttributes() {

        return this.ATTRIBUTES;

    }

    /**
     * @param input line to check
     * @return whether the line type identifier is the found
     */
    public boolean matches(String input) {
        
        String[] splitInput = input.split(":", 1);
        String potentialIdentfier = splitInput[0];

        return this.TAG.equals(potentialIdentfier);
    
    }

}
