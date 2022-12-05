import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

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

        while (input.hasNextLine()) {
            String line = input.nextLine();
        }
        return null;
    }

    public void saveTree(String filename) {
        WriteFile visitor = new WriteFile(filename);
        getRoot().traversePreorder(visitor);
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
