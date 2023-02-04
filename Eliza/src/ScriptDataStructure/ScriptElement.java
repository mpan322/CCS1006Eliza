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
    public void print();

}
