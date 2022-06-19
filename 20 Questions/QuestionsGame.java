// Nathan Lee
// TA: Yafqa Khan
// The QuestionsGame class represents a tree of yes/no
// questions and answers for a game of 20 questions.

import java.util.*;
import java.io.*;

public class QuestionsGame {

    // The tree representing the yes/no questions and answers.
    private QuestionNode overallRoot;

    // The scanner which reads input and output.
    private Scanner console;

    // Represents the values of the question tree and constructs new question trees.
    private static class QuestionNode {
        // The current value of the tree.
        public String data;

        // The left sub-value of the tree.
        public QuestionNode left;

        // The right sub-value of the tree.
        public QuestionNode right;

        // Constructs a new tree with the given data  
        // with null values as both of the sub-trees.
        public QuestionNode(String data) {
            this(data, null, null);
        }

        // Constructs a new tree with the given data and the given left as the
        // left sub-tree and the given right as the right sub-tree of the tree.
        public QuestionNode(String data, QuestionNode left, QuestionNode right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }

    // Initializes a new QuestionsGame tree with 
    // the single value "computer" in the tree.
    public QuestionsGame() {
        overallRoot = new QuestionNode("computer");
        console = new Scanner(System.in);
    }

    // Replaces the current question tree with a new tree 
    // representing the information in the given input 
    // file. Assumes the file is in the standard format.
    public void read(Scanner input) {
        overallRoot = newTree(input);
    }

    // Returns a new question tree created with the information 
    // in the given input file in the standard format.
    private QuestionNode newTree(Scanner input) {
        if (input.nextLine().equals("A:")) {
            return new QuestionNode(input.nextLine());
        } else {
            return new QuestionNode(input.nextLine(), newTree(input), newTree(input));
        }
    }

    // Stores the current question tree in the given output file. 
    // The output file can later be used to play another game 
    // with the computer using the questions sent to the output 
    // file. The written output file is in the standard format.
    public void write(PrintStream output) {
        write(output, overallRoot);
    }

    // Sends the data in the given overallRoot to the given 
    // output file writing the data in the standard format.
    private void write(PrintStream output, QuestionNode overallRoot) {
        if (overallRoot.left == null && overallRoot.right == null) {
            output.println("A:");
            output.println(overallRoot.data); 
        } else {
            output.println("Q:");
            output.println(overallRoot.data);    
            write(output, overallRoot.left);
            write(output, overallRoot.right);   
        }
    }

    // Plays one game with the player using the current question tree.
    // Asks yes/no questions until reaching an answer to guess. Prints 
    // a message saying the computer wins if the guess is correct. Otherwise
    // asks the user the correct object, a question to distinguish the 
    // guess and the correct answer, the answer to the question, and then 
    // updates the question tree to include the new question and answer.
    public void askQuestions() {
        overallRoot = askQuestions(overallRoot);
    }

    // Asks the player yes/no questions in the given overallRoot 
    // until the computer reaches a guess. Informs the player the 
    // computer won if the guess is correct. Otherwise, asks the 
    // player the object they were thinking of, a yes/no question to 
    // distinguish the object from the computer's guess, and the yes/no
    // answer to the question, and then updates the question tree 
    // with the new question and answer returning the new question tree.
    private QuestionNode askQuestions(QuestionNode overallRoot) {
        if (overallRoot.left == null || overallRoot.right == null) {
            if (yesTo("Would your object happen to be " + overallRoot.data + "?")) {
                System.out.println("Great, I got it right!");
            } else {
                System.out.print("What is the name of your object? ");
                String name = console.nextLine();
                System.out.println("Please give me a yes/no question that");
                System.out.println("distinguishes between your object");
                System.out.print("and mine--> ");
                String question = console.nextLine();
                if (yesTo("And what is the answer for your object?")) {
                    overallRoot = new QuestionNode(question, new QuestionNode(name), overallRoot);
                } else {
                    overallRoot = new QuestionNode(question, overallRoot, new QuestionNode(name));
                }
            }
        } else {
            if (yesTo(overallRoot.data)) {
                overallRoot.left = askQuestions(overallRoot.left);
            } else {
                overallRoot.right = askQuestions(overallRoot.right);
            }
        }
        return overallRoot;
    }

    // Do not modify this method in any way
    // post: asks the user a question, forcing an answer of "y" or "n";
    //       returns true if the answer was yes, returns false otherwise
    private boolean yesTo(String prompt) {
        System.out.print(prompt + " (y/n)? ");
        String response = console.nextLine().trim().toLowerCase();
        while (!response.equals("y") && !response.equals("n")) {
            System.out.println("Please answer y or n.");
            System.out.print(prompt + " (y/n)? ");
            response = console.nextLine().trim().toLowerCase();
        }
        return response.equals("y");
    }
}
