/**
 *	Leaf nodes containing guesses.
 *
 *	@author Mitchell Kelly, Elliot Lapenga, Jeff Gillis, Casey NeyWaren
 *
 *	CS1122, Fall 2022
 */
public class Guess<E> extends LinkedBinaryTreeNode<E> {
    public Guess(E guess) {
        super(guess);
    }

    public void setLeft(BinaryTreeNode<E> child) {
        System.err.println("Guess node can not be parent node");
    }

    public void setRight(BinaryTreeNode<E> child) {
        System.err.println("Guess node can not be parent node");
    }

    public String toString() {
        return "Guess - " + super.toString();
    }
}
