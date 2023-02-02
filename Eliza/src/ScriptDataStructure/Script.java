package ScriptDataStructure;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class Script {

    private WelcomeMessage welcomeMessage;
    private GoodbyeMessage goodbyeMessage;

    private Collection<QuitKeyword> quitKeywords;

    private Substituter preSubstituter;
    private Substituter postSubstituter;

    private Map<String, Keyword> keywordMap = new LinkedHashMap<>();

    // needs to be a set as it will equal the keyset of keywordMap
    // needs to be better ordered
    private LinkedHashSet<String> keywords;

    public void addKeyword(Keyword keyword) {

        String word = keyword.getKeyword();
        this.keywordMap.put(word, keyword);
        // also need to add it to some list ordered by priority

    }

    public void setWelcomeMessage(WelcomeMessage welcomeMessage) {

        this.welcomeMessage = welcomeMessage;

    }

    public void setGoodbyeMessage(GoodbyeMessage goodbyeMessage) {

        this.goodbyeMessage = goodbyeMessage;

    }

    public void addQuitKeyword(QuitKeyword quitKeyword) {

        this.quitKeywords.add(quitKeyword);

    }

}
