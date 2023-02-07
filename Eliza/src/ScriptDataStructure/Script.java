package ScriptDataStructure;

import java.security.Key;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

public class Script implements ScriptElement {

    private final String WELCOME_MESSAGE;
    private final String GOODBYE_MESSAGE;
 
    private final Collection<String> QUIT_KEYWORDS;
    private final Substituter PRE_SUBSTITUTER;
    private final Substituter POST_SUBSTITUTER;

    private final List<Keyword> KEYWORDS;
    private final Keyword DEFAULT_KEYWORD;

    public Script(Keyword defaultKeyword, List<Keyword> keywords, Substituter preSub, Substituter postSub, String welcomeMessage, String goodbyeMessage, List<String> quitKeywords) {

        this.QUIT_KEYWORDS = quitKeywords;
        this.KEYWORDS = keywords;
        this.PRE_SUBSTITUTER = preSub;
        this.POST_SUBSTITUTER = postSub;
        this.WELCOME_MESSAGE = welcomeMessage;
        this.GOODBYE_MESSAGE = goodbyeMessage;
        this.DEFAULT_KEYWORD = defaultKeyword;

    }

    public boolean isQuit(String input) {

        return this.QUIT_KEYWORDS.contains(input);

    }

    public String generateOutput(String input) {

        // global pre substitution
        String output = this.PRE_SUBSTITUTER.generateOutput(input);

        Keyword keyword = this.findBestKeyword(input);
        System.out.println(keyword.getKeyword());

        return output;

    }

    /**
     * Finds the higest priority keyword in the input
     * 
     * @param input the input scentence
     * @return the best keyword, or if none match then the default keyword
     */
    private Keyword findBestKeyword(String input) {

        for (Keyword keyword : KEYWORDS) {

            if (keyword.containsKeyword(input)) {

                return keyword;

            }

        }

        return this.DEFAULT_KEYWORD;

    }

    @Override
    public void print(int indentDepth) {

        System.out.println("WELCOME: " + this.WELCOME_MESSAGE);
        System.out.println("GOODBYE: " + this.GOODBYE_MESSAGE);

        String indent = this.makeIndent(indentDepth);
        String quitKeywordMesasage = String.join(", ", this.QUIT_KEYWORDS);
        System.out.println(indent + "QUIT-KEYWORDS: " + quitKeywordMesasage + "\n");

        this.KEYWORDS.forEach((keyword) -> {

            keyword.print(indentDepth);
            System.out.println();

        });

        System.out.println(indent + "POST-SUBSTITUTION: ");
        this.POST_SUBSTITUTER.print(indentDepth + 1);

        System.out.println(indent + "PRE-SUBSTITUTION: ");
        this.PRE_SUBSTITUTER.print(indentDepth + 1);

    }

}
