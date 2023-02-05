package ScriptDataStructure;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ReassemblyRule implements ScriptElement {

    private Substituter postSubstituter;
    private final String FORMAT;

    // potentially change the name from pattern to something else
    public ReassemblyRule(String format) {

        this.FORMAT = format;

    }

    public void setSubstituter(Substituter substituter) {

        this.postSubstituter = substituter;

    }

    @Override
    public String generateOutput(String input) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void print(int indentDepth) {

        String indent = this.makeIndent(indentDepth);
        System.out.println(indent + "REASSEMBLY: " + this.FORMAT);

        if (this.postSubstituter != null) {

            this.postSubstituter.print(indentDepth + 1);
        
        }
        
    }

}
