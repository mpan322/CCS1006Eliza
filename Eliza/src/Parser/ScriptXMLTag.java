package Parser;

/**
 * Enum of representations of the XML tags in the script
 */
public enum ScriptXMLTag {

    TEXT("#text"),
    DOCUMENT("#document"),
    SCRIPT("script"),
    WELOCME_MSG("welcomeMsg"),
    GOODBYE_MSG("goodbyeMsg"),
    QUIT_KEYWORDS("quitKeywords"),
    QUIT_KEYWORD("quitKeyword"),
    KEYWORD("keyword"),
    DECOMPOSITION("decomposition"),
    REASSEMBLY("reassembly"),
    POST_SUBSTITUTION("postSub"),
    PRE_SUBSTITUTION("preSub"),
    SUBSTITUTION_RULE("subRule"),
    DEFAULT("default"),
    KEYWORDS("keywords");

    private final String TAG;

    private ScriptXMLTag(String tag) {

        this.TAG = tag;

    }

    /**
     * @return the tag of this tag type (ex. keyword)
     */
    public String getTag() {

        return this.TAG;

    }

}
