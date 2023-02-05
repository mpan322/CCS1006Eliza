package ScriptDataStructure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

public class Script implements ScriptElement {

    private String welcomeMessage;
    private String goodbyeMessage;

    private Collection<String> quitKeywords;

    private Substituter preSubstituter;
    private Substituter postSubstituter;

    private List<Keyword> keywords = new ArrayList<>();
    private static final Keyword DEFAULT_KEYWORD = new Keyword("//DEFAULT//", -1);

    public Script() {

        this.quitKeywords = new ArrayList<>();

    }

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

    public void sayGoodbyeMessage() {

        System.out.println(this.goodbyeMessage);

    }

    public void sayWelcomeMessage() {

        System.out.println(this.welcomeMessage);

    }

    public void addQuitKeyword(String quitKeyword) {

        this.quitKeywords.add(quitKeyword);

    }

    public void addKeyword(Keyword keyword) {

        int priority = keyword.getPriority();
        int i = 0;

        int currPriority = Integer.MAX_VALUE;
        while (currPriority > priority && i < keywords.size()) {

            currPriority = this.keywords.get(i).getPriority();

            if (currPriority > priority) {

                i++;

            }

        }

        this.keywords.add(i, keyword);

    }

    public boolean isQuit(String input) {

        return this.quitKeywords.contains(input);

    }

    public String generateOutput(String input) {

        // global pre substitution
        this.preSubstituter.generateOutput(input);

        // do main parsing step based on the keyword (decompositon, reassembly, etc)
        Keyword keyword = this.findBestKeyword(input);
        keyword.generateOutput(input);

        // global post substitution
        this.postSubstituter.generateOutput(input);

        return input;

    }

    /**
     * Finds the higest priority keyword in the input
     * 
     * @param input the input scentence
     * @return the best keyword, or if none match then the default keyword
     */
    private Keyword findBestKeyword(String input) {

        for (Keyword keyword : keywords) {

            if (keyword.containsKeyword(input)) {

                return keyword;

            }

        }

        return Script.DEFAULT_KEYWORD;

    }

    @Override
    public void print(int indentDepth) {

        System.out.println("WELCOME: " + this.welcomeMessage);
        System.out.println("GOODBYE: " + this.goodbyeMessage);

        String indent = this.makeIndent(indentDepth);
        String quitKeywordMesasage = String.join(", ", this.quitKeywords);
        System.out.println(indent + "QUIT-KEYWORDS: " + quitKeywordMesasage + "\n");

        this.keywords.forEach((keyword) -> {

            keyword.print(indentDepth);
            System.out.println();

        });

        System.out.println(indent + "POST-SUBSTITUTION: ");
        this.postSubstituter.print(indentDepth + 1);

        System.out.println(indent + "PRE-SUBSTITUTION: ");
        this.preSubstituter.print(indentDepth + 1);

    }

}
