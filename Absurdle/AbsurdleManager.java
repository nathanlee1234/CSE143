// Nathan Lee
// TA: Yafqa Khan
// The AbsurdleManager class constructs a new game of 
// Absurdle and narrows down possible answers with 
// each guess until only one possible answer is left.

import java.util.*;

public class AbsurdleManager {
    
    // The given length of the target word.
    private int wordLength;

    // The set of words considered by the AbsurdleManager.
    private Set<String> wordSet;

    // Given a dictionary of words and a target word length, initializes
    // a new game of Absudle by establishing a set of words which 
    // contains all the words from the given dictionary of the 
    // given length, eliminating any duplicates. Throws an 
    // IllegalArgumentException if the given length is less than 1. 
    public AbsurdleManager(Collection<String> dictionary, int length) {
        wordSet = new TreeSet<>();
        if (length < 1) {
            throw new IllegalArgumentException();
        }
        for (String word: dictionary) {
            if (word.length() == length && !wordSet.contains(word)) {
                wordSet.add(word);
            }
        }
        wordLength = length;
    }

    // Returns the current set of words considered by the manager.
    public Set<String> words() {
        return wordSet;
    }

    // Params:
    //  String word -- the secret word trying to be guessed. Assumes word is made up of only
    //                 lower case letters and is the same length as guess.
    //  String guess -- the guess for the word. Assumes guess is made up of only
    //                  lower case letters and is the same length as word.
    // Exceptions:
    //   none
    // Returns:
    //   returns a string, made up of gray, yellow, or green squares, representing a
    //   standard wordle clue for the provided guess made against the provided secret word.
    public static String patternFor(String word, String guess) {
        String[] pattern = new String[word.length()];
        Map<Character, Integer> counts = new TreeMap<Character, Integer>();
        for (int i = 0; i < word.length(); i++) {
            if (!counts.containsKey(word.charAt(i))) {
                counts.put(word.charAt(i), 1);
            } else {
                counts.put(word.charAt(i), counts.get(word.charAt(i)) + 1);
            }
        }    
        for (int i = 0; i < word.length(); i++) { 
            if (word.charAt(i) == guess.charAt(i)) {
                pattern[i] = "🟩";
                counts.put(word.charAt(i), counts.get(word.charAt(i)) - 1);
            } 
        }
        for (int i = 0; i < word.length(); i++) { 
            if (counts.containsKey(guess.charAt(i)) && 
            counts.get(guess.charAt(i)) > 0 && pattern[i] != "🟩") {
                pattern[i] = "🟨";
                counts.put(guess.charAt(i), counts.get(guess.charAt(i)) - 1);
            } else if (pattern[i] != "🟩") {
                pattern[i] = "⬜";
            }
        }
        return Arrays.toString(pattern).replace(",","").
        replace("[","").replace("]","").replace(" ", "");
    }

    // Returns the pattern associated with the largest number of words for the 
    // given guess. Picks the pattern that appears first in the sorted order if 
    // multiple patterns have the same largest number of words. Also changes the
    // word set to the corresponding set of words for the returned pattern. Throws 
    // an IllegalStateException if the set of words is empty and throws and 
    // IllegalArgumentException if the guess does not have the correct length.
    public String record(String guess) {
        if (wordSet.isEmpty()) {
            throw new IllegalStateException();
        }
        if (guess.length() != wordLength) {
            throw new IllegalArgumentException();
        }
        Map<String, Set<String>> patternInventory = new TreeMap<String, Set<String>>();
        for (String word: wordSet) {
            String pattern = patternFor(word, guess);
            if (!patternInventory.containsKey(pattern)) {
                Set<String> sampleWordSet = new TreeSet<String>();
                sampleWordSet.add(word);
                patternInventory.put(pattern, sampleWordSet);
            } else {
                patternInventory.get(pattern).add(word);
            }
        }
        int maxCount = 0;
        String bestPattern = "";
        for (String pattern: patternInventory.keySet()) {
            if (patternInventory.get(pattern).size() > maxCount) {
                bestPattern = pattern;
                maxCount = patternInventory.get(pattern).size();
                wordSet = patternInventory.get(pattern);
            }
        }
        return bestPattern;
    }
}
