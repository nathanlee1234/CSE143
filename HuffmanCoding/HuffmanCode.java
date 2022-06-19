// Nathan Lee
// TA: Yafqa Khan
// The HuffmanCode class compressess data to 
// take up smaller amounts of data space.

import java.util.*;
import java.io.*;

public class HuffmanCode {

    // The model used to create the compressed data.
    private HuffmanNode root;

    // Represents the HuffmanNode model used
    // to construct the compressed data codes.
    // Implements the comparable method.
    private static class HuffmanNode implements Comparable<HuffmanNode> {
        // The ASCII value for the HuffmanNode object.
        public int data;

        // The frequency of the ASCII value for the HuffmanNode object.
        public int frequency;

        // The left sub-value of the HuffmanNode object.
        public HuffmanNode left;

        // The right sub-value of the HuffmanNode object.
        public HuffmanNode right;

        // Constructs a new HuffmanNode object with zero 
        // values for the the ASCII value and the frequency.
        public HuffmanNode() {
            this(0, 0);
        }

        // Constructs a new HuffmanNode object with the 
        // given ASCII data value and frequency.
        public HuffmanNode(int data, int frequency) {
            this(data, frequency, null, null);
        }

        // Constructs a new HuffmanNode object with the given ASCII data
        // value, frequency, and left and right HuffmanNode objects.
        public HuffmanNode(int data, int frequency, HuffmanNode left, HuffmanNode right) {
            this.data = data;
            this.frequency = frequency;
            this.left = left;
            this.right = right;
        }

        // Returns the difference between frequencies of this 
        // HuffmanNode and the given other HuffmanNode.
        public int compareTo(HuffmanNode other) {
            return this.frequency - other.frequency;
        }
    }

    // Initializes a new HuffmanCode object using the given array of 
    // frequencies representing the count of each ASCII character.
    public HuffmanCode(int[] frequencies) {
        Queue<HuffmanNode> letterFrequency = new PriorityQueue<HuffmanNode>();
        for (int i = 0; i < frequencies.length; i++) {
            if (frequencies[i] != 0) {
                letterFrequency.add(new HuffmanNode(i, frequencies[i]));
            }
        }
        while (letterFrequency.size() != 1) {
            HuffmanNode left = letterFrequency.remove();
            HuffmanNode right = letterFrequency.remove();
            letterFrequency.add(new HuffmanNode(0, left.frequency + right.frequency, left, right));
        }
        root = letterFrequency.remove();
    }

    // Initializes a new HuffmanCode object using the given 
    // constructed code from an input file. Assumes given input 
    // file is not null and is given in the standard format.
    public HuffmanCode(Scanner input) {
        while (input.hasNextLine()) {
            int asciiValue = Integer.parseInt(input.nextLine());
            String code = input.nextLine();
            root = createTree(root, asciiValue, code);
        }
    }

    // Returns the HuffmanNode tree created using the 
    // given HuffmanNode, ASCII value, and code string. 
    private HuffmanNode createTree(HuffmanNode root, int asciiValue, String code) {
        if (root == null) {
            root = new HuffmanNode();
        }
        if (code.isEmpty()) {
            root = new HuffmanNode(asciiValue, -1);
        } else if (code.startsWith("0")) {
            root.left = createTree(root.left, asciiValue, code.substring(1));
        } else {
            root.right = createTree(root.right, asciiValue, code.substring(1));
        }
        return root;
    }

    // Saves the current Huffman codes in the Huffman model 
    // to the given output stream in the standard format.
    public void save(PrintStream output) {
        save(root, "", output);
    }

    // Prints the given Huffman codes stored in the 
    // given HuffmanNode to the given output stream.
    private void save(HuffmanNode root, String code, PrintStream output) {
        if (root.right == null && root.right == null) {
            output.println(root.data);
            output.println(code);
        } else {
            save(root.left, code + 0, output);
            save(root.right, code + 1, output);
        }
    }

    // Reads a combination of codes from the given input file and writes 
    // the corresponding ASCII charaters to the given output stream. Stops 
    // reading when the given input file is empty. Assumes the given input 
    // contains a legal set of characters from the Huffman code of the tree.
    public void translate(Scanner input, PrintStream output) {
        HuffmanNode temp = root;
        while (input.hasNextByte()) {
            int number = input.nextByte();
            if (number == 0) {
                temp = temp.left;
            } else if (number == 1) {
                temp = temp.right;
            }
            if (temp.left == null && temp.right == null) {
                output.write(temp.data);Ω
                temp = root;
            }
        }
    }
}
