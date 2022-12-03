import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

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

    public void setParent(BinaryTreeNode<E> parent) {
        this.parent = parent;
    }

    public BinaryTreeNode<E> getLeft() {
        return left;
    }

    public void setLeft(BinaryTreeNode<E> child) {
        left = child;
        if (child != null) {
            child.setParent(this);
        }
    }

    public BinaryTreeNode<E> getRight() {
        return right;
    }

    public void setRight(BinaryTreeNode<E> child) {
        right = child;
        if (child != null) {
            child.setParent(this);
        }
    }

    public boolean isParent() {
        return !isLeaf();
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
        if (getRoot() == this && isLeaf())
            return 0;
        return heightHelper(getRoot());
    }

    private int heightHelper(BinaryTreeNode<E> root) {
        Queue<BinaryTreeNode<E>> nodes = new LinkedList<>();
        nodes.add(root);
        nodes.add(null);
        int height = -1;
        while (!nodes.isEmpty()) {
            BinaryTreeNode<E> node = nodes.poll();
            if (node == null) {
                if (!nodes.isEmpty()) {
                    nodes.add(null);
                }
                height++;
            } else {
                if (node.hasLeftChild()) {
                    nodes.add(node.getLeft());
                }
                if (node.hasRightChild()) {
                    nodes.add(node.getRight());
                }
            }
        }
        return height;
    }

    public int size() {
        if (isLeaf()) {
            return 1;
        }
        return 1 + getLeft().size() + getRight().size();
    }

    public void removeFromParent() {
        if (getParent() == null) {
            return;
        }
        if (getParent().getLeft() == this) {
            getParent().setLeft(null);
        } else if (getParent().getRight() == this) {
            getParent().setRight(null);
        }
        setParent(null);
    }

    public ArrayList<BinaryTreeNode<E>> pathTo(BinaryTreeNode<E> descendant) {
        ArrayList<BinaryTreeNode<E>> path = new ArrayList<>();
        BinaryTreeNode<E> cur = descendant;
        while (cur != this) {
            if (getRoot() == cur) {
                return new ArrayList<>();
            }
            path.add(0, cur);
            cur = cur.getParent();
        }
        path.add(0, cur);
        return path;
    }

    public ArrayList<BinaryTreeNode<E>> pathFrom(BinaryTreeNode<E> ancestor) {
        ArrayList<BinaryTreeNode<E>> path = new ArrayList<>();
        BinaryTreeNode<E> cur = this;
        while (cur != ancestor) {
            if (getRoot() == cur) {
                return new ArrayList<>();
            }
            path.add(cur);
            cur = cur.getParent();
        }
        path.add(cur);
        return path;
    }

    public void traversePreorder(Visitor visitor) {
        visitor.visit(this);
        if (hasLeftChild()) {
            getLeft().traversePreorder(visitor);
        }
        if (hasRightChild()) {
            getRight().traversePreorder(visitor);
        }
    }

    public void traversePostorder(Visitor visitor) {
        if (hasLeftChild()) {
            getLeft().traversePostorder(visitor);
        }
        if (hasRightChild()) {
            getRight().traversePostorder(visitor);
        }
        visitor.visit(this);
    }

    public void traverseInorder(Visitor visitor) {
        if (hasLeftChild()) {
            getLeft().traverseInorder(visitor);
        }
        visitor.visit(this);
        if (hasRightChild()) {
            getRight().traverseInorder(visitor);
        }
    }
}
