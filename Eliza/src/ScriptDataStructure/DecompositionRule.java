package ScriptDataStructure;

import java.util.ArrayList;
import java.util.Random;

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
