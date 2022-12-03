/**
 *	Interior nodes containing yes-no questions.
 *
 *	@author Mitchell Kelly, Elliot Lapenga, Jeff Gillis, Casey NeyWaren
 *
 *	CS1122, Fall 2022
 */
public class Question<E> extends LinkedBinaryTreeNode<E> {
    public Question(E question, BinaryTreeNode<E> left, BinaryTreeNode<E> right) {
        super(question);
        setLeft(left);
        setRight(right);
    }

    public String toString() {
        return "Question - " + super.toString();
    }
}
