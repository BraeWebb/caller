package au.edu.uq.itee.comp3506.assn2.collections;

import au.edu.uq.itee.comp3506.assn2.nodes.LinkedNode;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A linked list of items stored in sequential order.
 * Maintains a cursor to one element in the sequential list and provides methods for
 * moving that cursor and manipulating the list at the current cursor location.
 *
 * Memory efficiency: O(n) where n is the number of nodes in the linked list.
 * This is because the number of nodes increase linearly with the number of
 * elements stored in the list and nodes have constant memory efficiency.
 *
 * @author Brae Webb <s4435400@student.uq.edu.au>
 *
 * @param <T> The type of element held in the linked list.
 */
public class LinkedList<T> implements List<T> {

    // The node the cursor is currently pointing to
    private LinkedNode<T> cursor;

    // The first and last elements of the list, improves time efficiency
    private LinkedNode<T> first;
    private LinkedNode<T> last;

    // The amount of items in the linked list
    private int size = 0;

    /**
     * Construct a new empty linked list object. Cursor points to null.
     *
     * Runtime efficiency: O(1) as there is only a single assignment
     *
     */
    public LinkedList() {
        cursor = null;
    }

    /**
     * Add an item to the end of the linked list. If the list is empty this becomes
     * the only element stored in the list.
     * The cursor is placed at the end of the list (the location of the new item).
     *
     * Runtime efficiency: O(1)
     * Determined as the LinkedNode constructor, getLast, isEmpty, setBefore and setAfter
     * methods all run in O(1) which means the worst case runtime is O(1)
     *
     * @param item The item to be added to the list.
     */
    public void add(T item){
        LinkedNode<T> node = new LinkedNode<>(item);

        size++;

        getLast();

        if (isEmpty()) {
            cursor = node;
            first = cursor;
            last = cursor;
            return;
        }

        node.setBefore(cursor);
        cursor.setAfter(node);
        cursor = node;
        last = cursor;
    }

    /**
     * Removes the item that the cursor is currently pointing to from the linked
     * list.
     * Ensures the previous item is connected to the next item in the sequence.
     * If the removed item was the last item the list becomes empty, which means
     * that the cursor is set to null.
     *
     * Runtime efficiency: O(1)
     * Determined as the isEmpty, getBefore, getAfter, setAfter and setBefore
     * methods all run in O(1) efficiency so the worst case runtime is O(1).
     *
     * @throws IndexOutOfBoundsException if the list is currently empty.
     */
    public void remove() throws IndexOutOfBoundsException{
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("Can't remove element from empty list");
        }

        size--;

        LinkedNode<T> before = cursor.getBefore();
        LinkedNode<T> after = cursor.getAfter();

        if (after == null) {
            if (before == null) {
                cursor = null;
                first = null;
                last = null;
                return;
            }
            before.setAfter(null);
            cursor = before;
            last = cursor;
            return;
        }

        if (before == null) {
            after.setBefore(null);
            cursor = after;
            first = cursor;
            return;
        }

        before.setAfter(after);
        after.setBefore(before);

        cursor = after;
    }

    /**
     * Set the cursor to point to the first item in the sequential linked list.
     * Returns the first item in the list or null if the list is empty.
     *
     * Runtime efficiency: O(1)
     * Determined as isEmpty and getValue are O(1) efficiency so the worst
     * case runtime can only be O(1).
     *
     * @return The item at the first position of the list or null if the list is empty.
     */
    public T getFirst(){
        if (isEmpty()) {
            return null;
        }
        cursor = first;
        return cursor.getValue();
    }

    /**
     * Set the cursor to point to the next item in the sequential linked list.
     * Returns the next item in the list or null if the list is empty.
     *
     * Runtime efficiency: O(1)
     * Determined as isEmpty, getAfter and getValue are O(1) efficiency so the worst
     * case runtime can only be O(1).
     *
     * @return The item at the next position of the list or null if the list is empty.
     */
    public T getNext(){
        if (isEmpty()) {
            return null;
        }
        LinkedNode<T> after = cursor.getAfter();
        if (after != null) {
            cursor = after;
        }
        return cursor.getValue();
    }

    /**
     * Set the cursor to point to the last item in the sequential linked list.
     * Returns the last item in the list or null if the list is empty.
     *
     * Runtime efficiency: O(1)
     * Determined as isEmpty and getValue are O(1) efficiency so the worst
     * case runtime can only be O(1).
     *
     * @return The item at the last position of the list or null if the list is empty.
     */
    public T getLast(){
        if (isEmpty()) {
            return null;
        }
        cursor = last;
        return cursor.getValue();
    }

    /**
     * Indicates if the list is empty or not.
     *
     * Runtime efficiency: O(1)
     * Determined as the code only consists of a return and a comparison.
     *
     * @return true iff the list is currenty empty.
     */
    public boolean isEmpty(){
        return cursor == null;
    }

    /**
     * Retrieves the size of the current linked list.
     *
     * Runtime Efficiency: O(1) as the method consists of one constant time
     * return operation.
     *
     * @return The amount of items stored in the linked list.
     */
    public int size() {
        return this.size;
    }

    /**
     * Retrieves the iterator to allow for iteration through the linked list.
     *
     * Runtime Efficiency: O(1) as the method consists of the constant time
     * operation getFirst and a return operation.
     *
     * @return An iterator for this linked list.
     */
    public Iterator<T> iterator() {
        getFirst();

        return new Iterator<T>() {
            private boolean isFirst = true;

            @Override
            public boolean hasNext() {
                return isFirst && !isEmpty() || !isEmpty() && !isLast();
            }

            @Override
            public T next() {
                if (isEmpty()) {
                    throw new NoSuchElementException();
                }
                if (isFirst) {
                    isFirst = false;
                    return getFirst();
                }
                return getNext();
            }
        };
    }

    /**
     * Replace the first element in the linked list with the given item.
     *
     * Runtime Efficiency: O(1) as the method consists of three method calls
     * which run in constant time.
     *
     * @param item The item to set as the first value.
     */
    public void replace(T item) {
        getFirst();
        remove();
        add(item);
    }

    /**
     * Add all items from another list into this linked list.
     *
     * Runtime Efficiency: O(n) where n is the number of elements in the given
     * lists. This is because the method loops for every item in the other list.
     *
     * @param list The list to add to this list.
     */
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    /**
     * Indicates if the cursor is pointing to the last element in the list.
     *
     * Runtime efficiency: O(1)
     * Determined as isEmpty has a runtime complexity of O(1) so the worst case
     * run time can only be O(1).
     *
     * @return true iff the cursor is pointing the the last element in the list.
     */
    public boolean isLast() {
        if (isEmpty()) {
            return false;
        }
        return cursor.getAfter() == null;
    }

    /**
     * Return the string representation of the linked list.
     * The linked list is represented by the values separated by '>' in sequential
     * order from left to right.
     *
     * Runtime efficiency: O(n)
     * Determined as all the method calls are O(1) but code is looped through n times.
     * Where n is the length of the linked list.
     *
     * @return The string representing this linked list.
     */
    public String toString() {
        LinkedNode<T> current = cursor;

        if (isEmpty()) {
            return "[]";
        }

        getFirst();
        String string = "[" + cursor.getValue();
        for (T value : this) {
            string += ", " + value;
        }
        cursor = current;
        return string + "]";
    }

}
