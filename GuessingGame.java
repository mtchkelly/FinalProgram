import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Stack;

/**
 *	Contains main logic of program.
 *
 *	@author Mitchell Kelly, Elliot Lapenga, Jeff Gillis, Casey NeyWaren
 *
 *	CS1122, Fall 2022
 */
public class GuessingGame implements Game {
    Scanner sc;

    private LinkedBinaryTreeNode<String> root;

    public GuessingGame(String filename) {
        root = (LinkedBinaryTreeNode<String>) loadTree(filename);
        sc = new Scanner(System.in);
    }

    public BinaryTreeNode<String> loadTree(String filename) {
        File treeFile = new File(filename);
        Scanner input = null;
        try {
            input = new Scanner(treeFile);
        } catch (FileNotFoundException ignored) {
        }

        Stack<BinaryTreeNode<String>> stack = new Stack<>();
        stack.push(parseLine(input.nextLine()));

        while (input.hasNextLine()) {
            BinaryTreeNode<String> top = stack.peek();
            BinaryTreeNode<String> next = parseLine(input.nextLine());
            while (isValid(top)) {
                stack.pop();
                top = stack.peek();
            }
            if (top.hasLeftChild()) {
                top.setRight(next);
            } else {
                top.setLeft(next);
            }
            next.setParent(top);
            stack.push(next);
        }
        return stack.firstElement();
    }

    /*
    Returns whether a Question node is complete, aka has two children nodes
    Guess nodes always return true because they are always leaf nodes
     */
    private boolean isValid(BinaryTreeNode<String> node) {
        if (node.getClass().getName().equals("Question")) {
            return node.hasLeftChild() && node.hasRightChild();
        }
        return true;
    }

    /*
    Creates a Guess or Question node from a line of text.
     */
    private BinaryTreeNode<String> parseLine(String line) {
        BinaryTreeNode<String> node;
        String data = line.substring(2);
        if (line.startsWith("G")) {
            node = new Guess<>(data);
        } else {
            node = new Question<>(data, null, null);
        }
        return node;
    }

    public void saveTree(String filename) {
        WriteFile save = new WriteFile(filename);
        getRoot().traversePreorder(save);
        save.output.close();
    }

    private class WriteFile implements BinaryTreeNode.Visitor {
        PrintWriter output;
        public WriteFile(String fileName) {

            try {
                output = new PrintWriter(fileName);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        public void visit(BinaryTreeNode node) {
            if (node.isLeaf()) {
                output.print("G:");
            } else if (node.isParent()) {
                output.print("Q:");
            }
            output.println(node.getData());
        }
    }

    public BinaryTreeNode<String> getRoot() {
        return root;
    }

    public void play() {
        BinaryTreeNode<String> cur = getRoot();
        while (!cur.isLeaf()) {
            System.out.println(cur.getData() + " (y/n) ");
            if (sc.nextLine().equals("y")) {
                cur = cur.getRight();
            } else {
                cur = cur.getLeft();
            }
        }
        if (makeGuess((Guess<String>) cur)) {
            System.out.println("I win!");
        } else {
            System.out.println("You win!");
            insertGuess((Guess<String>) cur);
        }
    }

    private boolean makeGuess(Guess<String> g) {
        System.out.println("Are you thinking of a " + g.getData() + "? (y/n) ");
        return sc.nextLine().equals("y");
    }

    private void insertGuess(Guess<String> g) {
        Question<String> parent = (Question<String>) g.getParent();
        System.out.println("What are you thinking of? ");
        Guess<String> newGuess = new Guess<>(sc.nextLine());
        Question<String> newQuestion;
        System.out.println("What question separates a " + g.getData() + " from a " + newGuess.getData() + "? ");
        String newQ = sc.nextLine();
        System.out.println("Is " + newGuess.getData() + " correct when the answer to \"" + newQ + "\" is yes? (y/n) ");
        if (sc.nextLine().equals("y")) {
            newQuestion = new Question<>(newQ, g, newGuess);
        } else {
            newQuestion = new Question<>(newQ, newGuess, g);
        }
        newQuestion.setParent(g.getParent());
        if (g.getParent().getRight() == g) {
            g.getParent().setRight(newQuestion);
        } else {
            g.getParent().setLeft(newQuestion);
        }
    }

    public static void main(String[] args) {
        GuessingGame game = new GuessingGame(args[0]);
        Scanner sc = new Scanner(System.in);
        System.out.println("Shall we play a game? (y/n) ");
        while (sc.nextLine().equals("y")) {
            game.play();
            System.out.println("Shall we play a game? (y/n) ");
        }
        System.out.println("\nSaving tree data...");
        System.out.println("Enter file name (with .data): ");
        game.saveTree(sc.nextLine());
    }

}
