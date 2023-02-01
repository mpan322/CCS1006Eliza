package ScriptDataStructure;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ReassemblyElement {

    private Substituter postSubstituter;
    private final String FORMAT;
    
    // potentially change the name from pattern to something else
    public ReassemblyElement(Node reassemblyRule) {
        
        Node formatAttr = reassemblyRule.getAttributes().getNamedItem("format");
        this.FORMAT = formatAttr.getNodeValue();

        NodeList children = reassemblyRule.getChildNodes();
        if(children.getLength() > 0) {

            Node susbtituterNode = children.item(0);
            this.postSubstituter = new Substituter(susbtituterNode); 

        }

    }

}
