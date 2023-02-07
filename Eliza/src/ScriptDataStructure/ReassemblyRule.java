package ScriptDataStructure;

public class ReassemblyRule implements ScriptElement {

    private final Substituter POST_SUBSTITUTER;
    private final String FORMAT;

    // potentially change the name from pattern to something else
    public ReassemblyRule(String format, Substituter postSubstituter) {

        this.FORMAT = format;
        this.POST_SUBSTITUTER = postSubstituter;

    }

    @Override
    public String generateOutput(String inputText) {

        this.POST_SUBSTITUTER.generateOutput(inputText);
        return null;

    }

    @Override
    public void print(int indentDepth) {

        String indent = this.makeIndent(indentDepth);
        System.out.println(indent + "REASSEMBLY: " + this.FORMAT);

        if (this.POST_SUBSTITUTER != null) {

            this.POST_SUBSTITUTER.print(indentDepth + 1);
        
        }
        
    }

}
