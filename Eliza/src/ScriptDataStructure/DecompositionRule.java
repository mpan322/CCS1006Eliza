package ScriptDataStructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DecompositionRule extends ArrayList<ReassemblyRule> {

    private final String PATTERN;

    public DecompositionRule(String pattern) {

        this.PATTERN = pattern;

    }

    public ReassemblyRule chooseReassemblyRule() {

        Random rand = new Random();
        int randInt = rand.nextInt(this.size());
        return this.get(randInt);
    
    }

}
