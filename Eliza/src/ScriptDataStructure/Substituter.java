package ScriptDataStructure;

import java.util.HashMap;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Substituter extends HashMap<String, String> {
    
    private static final String INPUT_ATTR = "input";
    private static final String REPLACE_ATTR = "replace";

    public Substituter(Node substituter) {

        NodeList substitutionRulesNodes = substituter.getChildNodes();

        for (int i = 0; i < substitutionRulesNodes.getLength(); i++) {
            
            Node subsRule = substitutionRulesNodes.item(i);
            this.parseSubsRule(subsRule);

        }
        
    }

    private void parseSubsRule(Node subsRule) {

        NamedNodeMap attrs = subsRule.getAttributes();
        Node inputAttrNode = attrs.getNamedItem(Substituter.INPUT_ATTR);
        Node replaceAttrNode = attrs.getNamedItem(Substituter.REPLACE_ATTR);

        String inputWord = inputAttrNode.getNodeValue();
        String replaceWord = replaceAttrNode.getNodeValue();

        this.put(inputWord, replaceWord);

    }

}
