package au.edu.uq.itee.comp3506.assn2.nodes;

import au.edu.uq.itee.comp3506.assn2.collections.LinkedList;
import au.edu.uq.itee.comp3506.assn2.collections.List;

/**
 * A tree node which contains holds a mapping of a key to a set of values
 * and maintains a reference to the left and right tree node children.
 *
 * Holding the references to the nodes left and right children allows the
 * tree to be traversed.
 *
 * Memory Efficiency: O(n) where n is the number of values assigned to the node.
 *      As each tree node holds a number of constant memory variables and a
 *      linked list which has O(n) memory efficiency.
 *
 * @author Brae Webb <s4435400@student.uq.edu.au>
 *
 * @param <K> The key of the tree node.
 * @param <V> The type of values mapped to the key.
 */
public class TreeNode<K, V> {
    // The key of the tree node
    private K key;
    // The values stored in the node
    private LinkedList<V> values;

    // The relative height of the node
    private int height;

    // The left child of the node or null if there is no left child
    private TreeNode<K, V> left = null;
    // The right child of the node or null if there is no right child
    private TreeNode<K, V> right = null;

    /**
     * Constructs a tree node with the given key which maps to an empty set
     * of values.
     *
     * Initial height of the tree is negative one and it has no left or right
     * children.
     *
     * Runtime Efficiency: O(1) as construction of a linked list is O(1) and
     * assignment of variables is constant efficiency.
     *
     * @param key The tree nodes key.
     */
    public TreeNode (K key) {
        this.key = key;
        this.values = new LinkedList<>();
        this.height = -1;
    }

    /**
     * Map a new value to the tree node.
     *
     * Runtime Efficiency: O(1) as the only operation - linked list add - runs
     * with a constant runtime efficiency.
     *
     * @param value The value to add to the tree node.
     */
    public void addValue(V value) {
        values.add(value);
    }

    /**
     * Retrieve the key of the current tree node.
     *
     * Runtime Efficiency: O(1) as the only operation is a constant time return
     * operation.
     *
     * @return The key of the tree node.
     */
    public K getKey() {
        return key;
    }

    /**
     * Retrieve a collection of all the values mapped to this tree node.
     *
     * Runtime Efficiency: O(1) as the only operation is a constant time return
     * operation.
     *
     * @return A collection of all the values mapped to this tree node.
     */
    public List<V> getValues() {
        return values;
    }

    /**
     * Set the new relative height of the tree node.
     *
     * Runtime Efficiency: O(1) as the only operation is a constant time
     * assignment operation.
     *
     * @param height The new relative height of the tree node.
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Retrieve the relative height of the tree node.
     *
     * Runtime Efficiency: O(1) as the only operation is a constant time return
     * operation.
     *
     * @return The relative height of the tree node.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Set the new left child of the tree node.
     *
     * Runtime Efficiency: O(1) as the only operation is a constant time
     * assignment operation.
     *
     * @param left The new left child of the tree node.
     */
    public void setLeft(TreeNode<K, V> left) {
        this.left = left;
    }

    /**
     * Retrieve the left child of the tree node.
     *
     * Runtime Efficiency: O(1) as the only operation is a constant time return
     * operation.
     *
     * @return The left child of the tree node.
     */
    public TreeNode<K, V> getLeft() {
        return this.left;
    }

    /**
     * Set the new right child of the tree node.
     *
     * Runtime Efficiency: O(1) as the only operation is a constant time
     * assignment operation.
     *
     * @param right The new right child of the tree node.
     */
    public void setRight(TreeNode<K, V> right) {
        this.right = right;
    }

    /**
     * Retrieve the right child of the tree node.
     *
     * Runtime Efficiency: O(1) as the only operation is a constant time return
     * operation.
     *
     * @return The right child of the tree node.
     */
    public TreeNode<K, V> getRight() {
        return this.right;
    }

    /**
     * Retrieve the string representation of the tree node.
     *
     * Runtime Efficiency: O(n) where n is the number of nodes below this node
     * in the tree. As internal toString method is O(n).
     *
     * @return The string representation of this tree node.
     */
    public String toString() {
        return toString("");
    }

    /**
     * Retrieve the string representation of the tree node.
     *
     * Runtime Efficiency: O(n) where n is the number of nodes below this node
     * in the tree. As each child element on the left and right sides will be
     * recursed down to the tree leaves.
     *
     * @param prefix A string to append to the start of the tree node string.
     * @return The string representation of this tree node.
     */
    private String toString(String prefix) {
        String string = (prefix + "├── ") + getKey() + " : " + getValues() + "\n";
        if (left != null) {
            string += left.toString(prefix + "    ");
        }
        if (right != null) {
            string += right.toString(prefix + "    ");
        }
        return string;
    }
}
