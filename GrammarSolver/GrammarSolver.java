// Nathan Lee
// TA: Yafqa Khan
// The Grammar Solver class generates
// random sentences when given a List
// of grammar rules which it abides by.

import java.util.*;

public class GrammarSolver {
    // The map containing all the non-terminals in
    // the given set of rules as well as each of the 
    // corresponding terminals for each non-terminal.
    private SortedMap<String, List<String[]>> grammarRules;

    // Generates a single random occurence of the 
    // given symbol and returns this occurence as a 
    // string with no trailing or leading spaces.
    private String randomGeneration(String symbol) {
        String randomOccurrence = "";
        if (!grammarRules.containsKey(symbol)) {
            return symbol;
        } else {
            List<String[]> terminals = grammarRules.get(symbol);
            int randomNumber = (int) Math.floor(Math.random() * terminals.size());
            for (int i = 0; i < terminals.get(randomNumber).length; i++) {
                randomOccurrence += randomGeneration(terminals.get(randomNumber)[i]) + " ";
            }
            return randomOccurrence.trim();
        }
    }

    // Creates a new grammar with the given list of rules 
    // separating and keeping track of the rules to allow 
    // for efficient locating of grammar parts later. Throws 
    // an IllegalArgumentException if the given list is 
    // empty of if there are two or more entries in the 
    // given list of rules for the same grammar symbol.
    public GrammarSolver(List<String> rules) {
        if (rules.isEmpty()) {
            throw new IllegalArgumentException();
        }
        grammarRules = new TreeMap<String, List<String[]>>();
        for (String statement : rules) {
            String[] split = statement.split("::=");
            if (grammarRules.containsKey(split[0])) {
                throw new IllegalArgumentException();
            }
            String[] terminals = split[1].split("\\|");
            List<String[]> terminal = new ArrayList<String[]>();
            for (int i = 0; i < terminals.length; i++) {
                terminal.add(terminals[i].trim().split("\\s+"));
            }
            grammarRules.put(split[0], terminal);
        }
    }

    // Returns true if the given symbol is a 
    // non-terminal in the grammar and false otherwise.
    public boolean grammarContains(String symbol) {
        return grammarRules.containsKey(symbol);
    }

    // Returns a string representation of each 
    // non-terminal in the grammar as a sorted, 
    // comma-separated list enclosed in brackets.
    public String getSymbols() {
        return grammarRules.keySet().toString();
    }

    // Generates the given times number of random occurences 
    // of the given symbol. Returns the random generation of 
    // words in a String[] with only one space between each 
    // terminal and no leading or trailing spaces in each string. 
    // Throws an IllegalArgumentException if the given symbol 
    // is not a non-terminal or if the given times is negative. 
    // Selects between different rules with equal probability.
    public String[] generate(String symbol, int times) {
        if (times < 0 || !grammarRules.containsKey(symbol)) {
            throw new IllegalArgumentException();
        }
        String[] randomTerminals = new String[times];
        for (int i = 0; i < times; i++) {
            randomTerminals[i] = randomGeneration(symbol);
        }
        return randomTerminals;
    }
}
