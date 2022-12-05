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

    private Question<String> root;

    public GuessingGame(String filename) {
        loadTree(filename);
    }

    public BinaryTreeNode<String> loadTree(String filename) {
        File treeFile = new File(filename);
        Scanner input = null;
        try {
            input = new Scanner(treeFile);
        } catch (FileNotFoundException ignored) {}

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
        return null;
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
        }
        else {
            node = new Question<>(data, null, null);
        }
        return node;
    }

    public void saveTree(String filename) {
        WriteFile save = new WriteFile(filename);
        getRoot().traversePreorder(save);
    }

    private record WriteFile(String fileName) implements BinaryTreeNode.Visitor {
        public void visit(BinaryTreeNode node) {
                File treeFile = new File(fileName);
                PrintWriter output = null;
                try {
                    output = new PrintWriter(treeFile);
                } catch (FileNotFoundException ignored) {}

                if (node.isLeaf()) {
                    output.print("G:");
                } else if (node.isParent()){
                    output.print("Q:");
                }

                output.println(node.getData());
            }
        }

    public BinaryTreeNode<String> getRoot() {
        return root;
    }

    public void play() {

    }

    public static void main(String[] args) {
        GuessingGame game = new GuessingGame(args[0]);
        Scanner sc = new Scanner(System.in);
        do {
            game.play();
            System.out.println("Shall we play a game?");
        } while (sc.nextLine().equals("y"));
    }

}
