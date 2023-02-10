package ScriptElements;

import java.util.Collection;
import java.util.List;

public class Script extends ScriptElement {

    private final String WELCOME_MESSAGE;
    private final String GOODBYE_MESSAGE;

    private final Collection<String> QUIT_KEYWORDS;
    private final Substituter PRE_SUBSTITUTER;
    private final Substituter POST_SUBSTITUTER;

    private final List<Keyword> KEYWORDS;
    private final Keyword DEFAULT_KEYWORD;

    public Script(Keyword defaultKeyword, List<Keyword> keywords, Substituter preSub, Substituter postSub,
            String welcomeMessage, String goodbyeMessage, List<String> quitKeywords) {

        this.QUIT_KEYWORDS = quitKeywords;
        this.KEYWORDS = keywords;
        this.PRE_SUBSTITUTER = preSub;
        this.POST_SUBSTITUTER = postSub;
        this.WELCOME_MESSAGE = welcomeMessage;
        this.GOODBYE_MESSAGE = goodbyeMessage;
        this.DEFAULT_KEYWORD = defaultKeyword;

    }

    /**
     * Prints welcome message
     */
    public void sayWelcome() {

        System.out.println(this.WELCOME_MESSAGE);

    }

    /**
     * Prints goodbye message
     */
    public void sayGoodbye() {

        System.out.println(this.GOODBYE_MESSAGE);

    }

    /**
     * Determines if the input is a quit statement
     * 
     * @param input
     * @return whether it is a quit statement
     */
    public boolean isQuit(String input) {

        return this.QUIT_KEYWORDS.contains(input);

    }

    /**
     * Generates output message
     * 
     * @param input the users message
     * @return the generated output message
     */
    public String generateOutput(String input) {

        String output = input.toLowerCase();
        // global pre substitution
        output = this.PRE_SUBSTITUTER.doSubstitutions(output);

        // get the keyword / reassembly and decompostion rules
        Keyword keyword = this.findBestKeyword(output);
        DecompositionRule decompositionRule = keyword.findDecompositionRule(output);
        ReassemblyRule reassemblyRule = decompositionRule.chooseReassemblyRule();

        // get the capture groups and do the post substitutions on them
        List<String> captureGroups = decompositionRule.getCaptureGroups(output);
        captureGroups = reassemblyRule.getPostSubstituter().doSubstitutions(captureGroups);
        captureGroups = this.POST_SUBSTITUTER.doSubstitutions(captureGroups);

        // insert capture group into the string where needed
        output = reassemblyRule.generateOutput(captureGroups);

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

            System.out.println("keyword= " + keyword.getKeyword() + ", input= " + input);

            if (keyword.containsKeyword(input)) {

                return keyword;

            }

        }

        return this.DEFAULT_KEYWORD;

    }

    public void print() {

        this.print(0);

    }

    @Override
    protected void print(int indentDepth) {

        System.out.println("WELCOME: " + this.WELCOME_MESSAGE);
        System.out.println("GOODBYE: " + this.GOODBYE_MESSAGE);

        String indent = this.makeIndent(indentDepth);
        String quitKeywordMesasage = String.join(", ", this.QUIT_KEYWORDS);
        System.out.println(indent + "QUIT-KEYWORDS: " + quitKeywordMesasage + "\n");

        this.KEYWORDS.forEach((keyword) -> {

            keyword.print(indentDepth);
            System.out.println();

        });

        this.DEFAULT_KEYWORD.print(indentDepth);

        System.out.println(indent + "POST-SUBSTITUTION: ");
        this.POST_SUBSTITUTER.print(indentDepth + 1);

        System.out.println(indent + "PRE-SUBSTITUTION: ");
        this.PRE_SUBSTITUTER.print(indentDepth + 1);

    }

}
