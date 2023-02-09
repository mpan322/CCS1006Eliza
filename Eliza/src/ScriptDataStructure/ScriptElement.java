package ScriptDataStructure;

public interface ScriptElement {

    public default String parseRegexInsertIdentifiers(String input){

        String output = "" + input;
        for (XMLGroupInserts insert :  XMLGroupInserts.values()) {
            
            String regex = insert.getRegex();
            String identifier = insert.getIdentifier();
            output = output.replaceAll("(?:[$]" + identifier + "[$])", regex);

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
