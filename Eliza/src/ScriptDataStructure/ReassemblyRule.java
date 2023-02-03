package ScriptDataStructure;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ReassemblyRule {

    private Substituter postSubstituter;
    private final String FORMAT;
    
    // potentially change the name from pattern to something else
    public ReassemblyRule(String format) {
        
        this.FORMAT = format;

    }

    public void setSubstituter(Substituter substituter) {
        
        this.postSubstituter = substituter;

    }

}
