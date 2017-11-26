package au.edu.uq.itee.comp3506.assn2.nodes;

/**
 * A linked node which contains holds a value and maintains a reference to
 * the linked nodes before and after itself in a linked list.
 *
 * Holding the references to the nodes before and after allows the list to
 * be traversed.
 *
 * Memory Efficiency: O(1) as each instance of the class holds two node
 * references and a T value.
 *
 * @author Brae Webb <s4435400@student.uq.edu.au>
 *
 * @param <T> The value stored in a linked node.
 */
public class LinkedNode<T> {
    // The linked node immediately before this node in a linked list
    private LinkedNode<T> before;
    // The linked node immediately after this node in a linked list
    private LinkedNode<T> after;
    // The value held by this node
    private T value;

    /**
     * Constructs a linked node that contains the given value. The node starts
     * with a null before and after node meaning that it is the only item
     * in a linked list.
     *
     * Runtime Efficiency: O(1) as the method consists of three constant time
     * assignment operations.
     *
     * @param value The value to be held by this linked node.
     */
    public LinkedNode(T value) {
        this.value = value;
        before = null;
        after = null;
    }

    /**
     * Sets the node immediately before the current node.
     *
     * Runtime Efficiency: O(1) as the method consists of one constant time
     * assignment operation.
     *
     * @param before The node immediately before this node in a linked list.
     */
    public void setBefore(LinkedNode<T> before) {
        this.before = before;
    }

    /**
     * Returns the node immediately before this node.
     *
     * Runtime Efficiency: O(1) as the method consists of one constant time
     * return operation.
     *
     * @return The node immediately before this node in a linked list.
     */
    public LinkedNode<T> getBefore() {
        return before;
    }

    /**
     * Sets the node immediately after the current node.
     *
     * Runtime Efficiency: O(1) as the method consists of one constant time
     * assignment operation.
     *
     * @param after The node immediately after this node in a linked list.
     */
    public void setAfter(LinkedNode<T> after) {
        this.after = after;
    }

    /**
     * Returns the node immediately after this node.
     *
     * Runtime Efficiency: O(1) as the method consists of one constant time
     * return operation.
     *
     * @return The node immediately after this node in a linked list.
     */
    public LinkedNode<T> getAfter() {
        return after;
    }

    /**
     * Returns the value held by this linked node.
     *
     * Runtime Efficiency: O(1) as the method consists of one constant time
     * return operation.
     *
     * @return The value currently held by this linked node.
     */
    public T getValue() {
        return value;
    }
}
