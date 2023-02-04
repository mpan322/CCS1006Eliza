package ScriptDataStructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.NodeList;

public class DecompositionRule extends ArrayList<ReassemblyRule> implements ScriptElement {

    private final Pattern PATTERN;

    public DecompositionRule(String pattern) {

        this.PATTERN = this.parsePattern(pattern);

    }

    private Pattern parsePattern(String pattern) {

        // make groups for all *s in the text to match anything
        pattern = pattern.replaceAll("*", "(.*)");

        // allow for extra accidental spaces
        pattern = pattern.replaceAll("\s", "\s+");

        return Pattern.compile(pattern);

    }

    public ReassemblyRule chooseReassemblyRule() {

        Random rand = new Random();
        int randInt = rand.nextInt(this.size());
        return this.get(randInt);

    }

    public List<String> getTextGroups(String input) {

        Matcher matcher = this.PATTERN.matcher(input);

        // get the text from where all the *'s would be
        List<String> asteriskGroups = new ArrayList<>();
        int groupCount = matcher.groupCount();
        for (int i = 0; i < groupCount; i++) {

            asteriskGroups.add(matcher.group(i));

        }

        return asteriskGroups;

    }

    @Override
    public String generateOutput(String input) {

        // the decomposition rule does not modify the string
        return input;

    }

    @Override
    public void print() {
        // TODO Auto-generated method stub

    }

}
