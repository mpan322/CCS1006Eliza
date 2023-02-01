package ScriptDataStructure;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public class Script extends HashMap<String, Keyword> {
    
    private String welcomeMsg;
    private String goodbyeMsg;

    private Collection<String> quitKeywords;

    // needs to be a set as it will equal the keyset of keywordMap
    private LinkedHashSet<String> keywords;

    private Substituter preSubstituter;
    private Substituter postSubstituter;

}
