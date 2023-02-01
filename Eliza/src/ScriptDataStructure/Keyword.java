package ScriptDataStructure;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Keyword extends ArrayList<Decomposition> implements ScriptDataStructureElement {

    public Keyword(Node keyword) {

        NodeList decompositionRules = keyword.getChildNodes();

        for (int i = 0; i < decompositionRules.getLength(); i++) {
            
            Node rule = decompositionRules.item(i);
            this.add(new Decomposition(rule));

        }

    }

    @Override
    public void makeNewElement(Node node) {
        // TODO Auto-generated method stub
        
    }
    
}
