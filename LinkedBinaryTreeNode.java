import java.util.ArrayList;

/**
 *	Linked version of binary tree nodes.
 *
 *	@author Mitchell Kelly, Elliot Lapenga, Jeff Gillis, Casey NeyWaren
 *
 *	CS1122, Fall 2022
 */
public class LinkedBinaryTreeNode<E> implements BinaryTreeNode<E> {

    private E data;
    private BinaryTreeNode<E> left;
    private BinaryTreeNode<E> right;
    private BinaryTreeNode<E> parent;

    public LinkedBinaryTreeNode() {
        this(null);
    }

    public LinkedBinaryTreeNode(E data) {
        this.data = data;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public BinaryTreeNode<E> getRoot() {
        if (getParent() == null) {
            return this;
        }
        return getParent().getRoot();
    }

    public BinaryTreeNode<E> getParent() {
        return parent;
    }

    public BinaryTreeNode<E> getLeft() {
        return left;
    }

    public void setLeft(BinaryTreeNode<E> child) {
        left = child;

    }

    public BinaryTreeNode<E> getRight() {
        return right;
    }

    public void setRight(BinaryTreeNode<E> child) {
        right = child;
    }

    public boolean isParent() {
        return left != null || right != null;
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }

    public boolean hasLeftChild() {
        return left != null;
    }

    public boolean hasRightChild() {
        return right != null;
    }

    public int getDepth() {
        if (getRoot() == this) {
            return 0;
        }
        return 1 + getParent().getDepth();
    }

    public int getHeight() {
        if (getRoot() != this) {
            return getRoot().getHeight();
        }
        return -1;
    }

    public int size() {
        if (isLeaf()) {
            return 1;
        }
        return 1 + getLeft().size() + getRight().size();
    }

    public void removeFromParent() {
        try {
            if (getParent().getLeft() == this) {
                getParent().setLeft(null);
            } else if (getParent().getRight() == this) {
                getParent().setRight(null);
            }
        } catch (NullPointerException e) {
            System.err.println("Does not have parent node");
        }
    }

    public ArrayList<BinaryTreeNode<E>> pathTo(BinaryTreeNode<E> descendant) {
        return null;
    }

    public ArrayList<BinaryTreeNode<E>> pathFrom(BinaryTreeNode<E> ancestor) {
        return null;
    }

    public void traversePreorder(Visitor visitor) {
        visitor.visit(this);
        if (getLeft() != null) {
            getLeft().traversePreorder(visitor);
        }
        if (getRight() != null) {
            getRight().traversePreorder(visitor);
        }
    }

    public void traversePostorder(Visitor visitor) {
        if (getLeft() != null) {
            getLeft().traversePreorder(visitor);
        }
        if (getRight() != null) {
            getRight().traversePreorder(visitor);
        }
        visitor.visit(this);
    }

    public void traverseInorder(Visitor visitor) {
        if (getLeft() != null) {
            getLeft().traversePreorder(visitor);
        }
        visitor.visit(this);
        if (getRight() != null) {
            getRight().traversePreorder(visitor);
        }
    }
}
