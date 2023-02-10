package ScriptDataStructure;

abstract class ScriptElement {

    protected String replaceRegexInsertIdentifiers(String input){

        String output = "" + input;
        for (XMLRegexInsert insert :  XMLRegexInsert.values()) {
            
            String regex = insert.getRegex();
            String identifier = insert.getIdentifier();
            output = output.replaceAll("(?:[$]" + identifier + "[$])", regex);

        }

        return output;
        
    }

    /**
     * Prints the script elements info
     */
    protected abstract void print(int indentDepth);

    protected String makeIndent(int depth) {

        String indent = "";
        for (int i = 0; i < depth; i++) {

            indent += "  ";

        }

        return indent;

    }

}
