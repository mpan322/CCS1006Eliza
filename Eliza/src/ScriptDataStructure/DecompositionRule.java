package ScriptDataStructure;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DecompositionRule extends ArrayList<ReassemblyElement> implements ScriptDataStructureElement{

    private final String PATTERN;

    public DecompositionRule(Node decompositionRule) {

        Node formatAttr = decompositionRule.getAttributes().getNamedItem("pattern");
        this.PATTERN = formatAttr.getNodeValue();
        
        NodeList reassemblyRules = decompositionRule.getChildNodes();

        for (int i = 0; i < reassemblyRules.getLength(); i++) {
            
            Node reassemblyRule = reassemblyRules.item(i);
            this.add(new ReassemblyElement(reassemblyRule));

        }

    }

    @Override
    public void makeNewElement(Node node) {
        // TODO Auto-generated method stub
        
    }

}
