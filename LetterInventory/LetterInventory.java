// Nathan Lee
// TA: Yafqa Khan
// The LetterInventory class keeps track of an 
// inventory of letters in the english alphabet.

public class LetterInventory {

    // The list that represents the letter inventory. 
    private int[] elementData;

    // The number of characters in the letter inventory.
    private int size;

    // The number of letters in the alphabet.
    public static final int ALPHABET = 26;

    // Constructs a new empty letter inventory.
    public LetterInventory() {
        elementData = new int[ALPHABET];
    }

    // Creates an inventory of the number of each
    // letter in the given data. Sets the size
    // to the number of letters in the given data.
    // Ignores all non alphabetic characters and
    // treats lowercase and uppercase letters the same.
    public LetterInventory(String data) {
        elementData = new int[ALPHABET];
        for (int i = 0; i < data.length(); i++) {
            char character = Character.toLowerCase(data.charAt(i));
            if (character - 'a' >= 0 && character - 'a' <= ALPHABET) {
                elementData[character - 'a']++;
                size++;
            }
        }
    }

    // Returns the number of the given letter, ignoring 
    // case, in the letter inventory ignoring case.  
    // Throws an IllegalArgumentException if the 
    // letter given is not in the standard alphabet.
    public int get(char letter) {
        letter = Character.toLowerCase(letter);
        if (letter - 'a' < 0 || letter - 'a' >= ALPHABET) {
            throw new IllegalArgumentException();
        }
        return elementData[Character.toLowerCase(letter) - 'a'];
    }

    // Sets the number of the given letter, ignoring  
    // case, to the given value in the letter inventory 
    // ignoring case. Alters the value of the size 
    // to the new total number of letters in the letter 
    // inventory. Throws an IllegalArgumentException 
    // if the letter given is not in the standard 
    // alphabet or if the given value is negative.
    public void set(char letter, int value) {
        letter = Character.toLowerCase(letter);
        if (letter - 'a' < 0 || letter - 'a' >= ALPHABET || value < 0) {
            throw new IllegalArgumentException();
        }
        size -= elementData[Character.toLowerCase(letter) - 'a'] - value;
        elementData[Character.toLowerCase(letter) - 'a'] = value;
    }

    // Returns the sum of all counts in the letter inventory.
    public int size() {
        return size;
    }

    // Returns true if the letter inventory is empty.
    public boolean isEmpty() {
       return size == 0;
    }

    // Returns a String representation of the inventory with
    // all the letters in lowercase surrounded by brackets.
    public String toString() {
        String letters = "[";
        for (int i = 0; i < ALPHABET; i++) {
            for (int j = 0; j < elementData[i]; j++) {
                letters += (char) (i + 'a');
            }
        }
        return letters + "]";
    }

    // Constructs and returns a new LetterInventory object 
    // that represents the sum of this LetterInventory and
    // the given other LetterInventory with the counts 
    // for each letter added together. Returns the sum of
    // this LetterInventory and the given other LetterInventory.
    public LetterInventory add(LetterInventory other) {
        LetterInventory sum = new LetterInventory();
        for (int i = 0; i < ALPHABET; i++) {
            sum.set((char) ('a' + i), elementData[i] + other.elementData[i]);
        }
        return sum;
    }

    // Constructs and returns a new LetterInventory object that 
    // represents the difference of this LetterInventory and the 
    // given other LetterInventory. Returns the difference of this 
    // LetterInventory and the given other LetterInventory. Returns 
    // null if any resulting subtraction count would be negative.
    public LetterInventory subtract(LetterInventory other) {
        LetterInventory difference = new LetterInventory();
        for (int i = 0; i < ALPHABET; i++) {
            int value = elementData[i] - other.elementData[i];
            if (value < 0) {
                return null;
            }
            difference.set((char) ('a' + i), value);
        }
        return difference;
    }
}
