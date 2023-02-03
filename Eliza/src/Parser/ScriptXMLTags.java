package Parser;

import org.w3c.dom.Node;

public enum ScriptXMLTags {

    NONE(""),
    SCRIPT("script"),
    WELOCME_MSG("welcomeMsg"),
    GOODBYE_MSG("goodbyeMsg"),
    QUIT_KEYWORDS("quitKeywords"),
    QUIT_KEYWORD("quitKeyword"),
    KEYWORD("keyword"),
    DECOMPOSITION("decomposition"),
    REASSEMBLY("reassembly"),
    POST_SUBSTITUTION("postSub"),
    PRE_SUBSTITUTION("postSub"),
    SUBSTITUTION_RULE("subRule"),
    ;

    private final String TAG;

    private ScriptXMLTags(String tag, String... attrs) {

        this.TAG = tag;

    }

    public String getTag() {

        return this.TAG;

    }

    public boolean isType(String tag) {

        return this.TAG.equals(tag);

    }

    public boolean isType(Node node) {

        return this.isType(node.getNodeName());

    }

}
