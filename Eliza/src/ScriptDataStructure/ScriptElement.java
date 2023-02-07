package ScriptDataStructure;

public interface ScriptElement {

    /**
     * Generates an output string given an input
     * 
     * @param input the string to use to generate output
     * @return the output message
     */
    public String generateOutput(String input);

    public default String parseRegexInsertIdentifiers(String input){

        String output = "" + input;
        for (XMLGroupInserts insert :  XMLGroupInserts.values()) {
            
            String regex = insert.getRegex();
            String identifier = insert.getIdentifier();
            output = output.replaceAll("[$]" + identifier + "[$]", regex);

        }

        return output;
        
    }

    /**
     * Prints the script elements info
     */
    public void print(int indentDepth);

    default public String makeIndent(int depth) {

        String indent = "";
        for (int i = 0; i < depth; i++) {

            indent += "  ";

        }

        return indent;

    }

}
