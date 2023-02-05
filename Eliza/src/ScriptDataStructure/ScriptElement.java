package ScriptDataStructure;

public interface ScriptElement {
    
    /**
     * Generates an output string given an input
     * 
     * @param input the string to use to generate output
     * @return the output message
     */
    public String generateOutput(String input);

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
