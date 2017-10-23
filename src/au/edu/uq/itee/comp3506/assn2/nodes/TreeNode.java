package au.edu.uq.itee.comp3506.assn2.nodes;

import au.edu.uq.itee.comp3506.assn2.collections.LinkedList;

/**
 * TreeNode
 * Created 22/10/2017
 *
 * @author Brae Webb
 */
public class TreeNode<K, V> {
    private K key;
    private LinkedList<V> values;
    private int height;
    private TreeNode<K, V> left = null;
    private TreeNode<K, V> right = null;

    public TreeNode (K key) {
        this.key = key;
        this.values = new LinkedList<>();
        this.height = -1;
    }

    public void addValue(V value) {
        values.add(value);
    }

    public K getKey() {
        return key;
    }

    public LinkedList<V> getValues() {
        return values;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public void setLeft(TreeNode<K, V> left) {
        this.left = left;
    }

    public TreeNode<K, V> getLeft() {
        return this.left;
    }

    public void setRight(TreeNode<K, V> right) {
        this.right = right;
    }

    public TreeNode<K, V> getRight() {
        return this.right;
    }

    public String toString() {
        return toString("", true);
    }

    private String toString(String prefix, boolean isTail) {
        String string = (prefix + (isTail ? "└── " : "├── ") + getKey() + " : " + getValues()) + "\n";
        if (left != null) {
            string += left.toString(prefix + (isTail ? "    " : "│   "), false);
        }
        if (right != null) {
            string += right.toString(prefix + (isTail ?"    " : "│   "), true);
        }
        return string;
    }
}
