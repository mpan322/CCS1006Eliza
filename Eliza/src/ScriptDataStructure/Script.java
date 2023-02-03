package ScriptDataStructure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

public class Script {

    private String welcomeMessage;
    private String goodbyeMessage;

    private Collection<String> quitKeywords;

    private Substituter preSubstituter;
    private Substituter postSubstituter;

    private List<Keyword> keywords = new ArrayList<>();


    // Setters
    public void setWelcomeMessage(String welcomeMessage) {

        this.welcomeMessage = welcomeMessage;

    }

    public void setGoodbyeMessage(String goodbyeMessage) {

        this.goodbyeMessage = goodbyeMessage;

    }

    public void setPresubstituter(Substituter preSubstituter) {

        this.preSubstituter = preSubstituter;

    }

    public void setGlobalPostSubstituter(Substituter postSubstituter) {

        this.postSubstituter = postSubstituter;

    }

    public String getGoodbyeMessage() {

        return this.goodbyeMessage;

    }

    public String getWelcomeMessage() {

        return this.welcomeMessage;

    }

    public void addQuitKeyword(String quitKeyword) {

        this.quitKeywords.add(quitKeyword);

    }

    public void addKeyword(Keyword keyword) {

        int priority = keyword.getPriority();
        int i = 0;
        Keyword curr = new Keyword(null, 0);
        do {

            curr = this.keywords.get(i);
            i++;

        } while (curr.getPriority() < priority);

        this.keywords.add(i, keyword);

    }

    public boolean isQuit(String input) {

        return this.quitKeywords.contains(input);

    }

    public String generateOutput(String input) {

        Keyword keyword = this.findBestKeyword(input);
        DecompositionRule decompositionRule = keyword.chooseDecompositionRule();
        ReassemblyRule reassemblyRule = decompositionRule.chooseReassemblyRule();

        return null;

    }

    private Keyword findBestKeyword(String input) {

        for (Keyword keyword : keywords) {

            if (keyword.containsKeyword(input)) {

                return keyword;

            }

        }

        return null;

    }

}
