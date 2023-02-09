package Parser;

import org.w3c.dom.Node;

/**
 * Enum of representations of the XML tags in the script
 */
public enum ScriptXMLTags {

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

    private ScriptXMLTags(String tag, String... attrs) {

        this.TAG = tag;

    }

    /**
     * @return the tag of this tag type (ex. keyword)
     */
    public String getTag() {

        return this.TAG;

    }

    /**
     * @param tagName the name to check
     * @return whether or not the inputted name matches this tag
     */
    public boolean isType(String tagName) {

        return this.TAG.equals(tagName);

    }

    /**
     * @param node a XML node
     * @return whether the node is this tag type
     */
    public boolean isType(Node node) {

        return this.isType(node.getNodeName());

    }

}
