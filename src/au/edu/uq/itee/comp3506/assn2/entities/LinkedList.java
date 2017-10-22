package au.edu.uq.itee.comp3506.assn2.entities;

import java.lang.reflect.Array;
import java.util.Collection;

/**
 * A linked list of items stored in sequential order.
 * Maintains a cursor to one element in the sequential list and provides methods for
 * moving that cursor and manipulating the list at the current cursor location.
 *
 * @author Brae Webb <s4435400@student.uq.edu.au>
 *
 * @param <T> The type of element held in the linked list.
 */
public class LinkedList<T> {

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
    public void addToEnd(T item){
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
     * Add an item before the cursor of the linked list.
     * If the list is empty then the item becomes the only item in the list.
     * The cursor is set to the location of the newly inserted item.
     *
     * Runtime efficiency: O(1)
     * Determined as the LinkedNode constructor, isEmpty, getBefore and getAfter
     * methods all run in O(1) so the worst case runtime is O(1)
     *
     * @param item The item to be inserted into the list.
     */
    public void insert(T item){
        LinkedNode<T> node = new LinkedNode<>(item);

        size++;

        if (isEmpty()) {
            cursor = node;
            first = cursor;
            last = cursor;
            return;
        }

        if (cursor.getBefore() == null) {
            node.setAfter(cursor);
            cursor.setBefore(node);
            cursor = node;
            first = cursor;
            return;
        }

        LinkedNode<T> before = cursor.getBefore();

        node.setBefore(before);
        node.setAfter(cursor);

        cursor.setBefore(node);
        before.setAfter(node);

        cursor = node;
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
     * Set the cursor to point to the previous item in the sequential linked list.
     * Returns the previous item in the list or null if the list is empty.
     *
     * Runtime efficiency: O(1)
     * Determined as isEmpty, getBefore and getValue are O(1) efficiency so the worst
     * case runtime can only be O(1).
     *
     * @return The item at the previous position of the list or null if the list is empty.
     */
    public T getPrevious(){
        if (isEmpty()) {
            return null;
        }
        LinkedNode<T> before = cursor.getBefore();
        if (before != null) {
            cursor = before;
        }
        return cursor.getValue();
    }

    /**
     * Iterates through the linked list starting at the first value until the
     * value of item is found in the list or the end of the list is reached.
     * The cursor points to the item where the value was found or the last
     * element in the list if it was not found.
     *
     * Runtime efficiency: O(n)
     * Determined as getFirst, getValue, equals and getNext run in constant
     * runtime complexity but the code is looped through a maximum of n times
     * (if the value is never found) where n is the length of the linked list.
     *
     * @param item The item to be found.
     * @return true iff the value was found in the list.
     */
    public boolean find(T item){
        if (getFirst() != null) {
            do {
                if (cursor.getValue().equals(item)) {
                    return true;
                }
                getNext();
            } while (!isLast());
        }
        return false;
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

    public int size() {
        return this.size;
    }

    public T[] toArray(Class<T> typeClass) {
        T[] list = (T[]) Array.newInstance(typeClass, size);
        int index = 0;

        list[index++] = getFirst();

        while (!isLast()) {
            list[index++] = getNext();
        }

        return list;
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
    public boolean isLast(){
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

        getFirst();
        if (cursor == null) {
            return "";
        }
        String string = "[" + cursor.getValue().toString();
        while (!isLast()) {
            getNext();
            string += ", " + cursor.getValue().toString();
        }
        cursor = current;
        return string + "]";
    }

}

/**
 * I have chosen to implement this linked list as a double linked list of
 * Nodes due to it's simplicity and efficiency.
 * This design is very simplistic compared to more complicated methods
 * because majority of the information about the class is stored in another
 * LinkedNode class. This prevents overly complex and complicated array lists.
 *
 * The design is also very efficient will all methods apart from the find and
 * toString methods have a O(1) runtime efficiency.
 *
 * This was made possibly by a change in design. Originally the getFirst and
 * getLast methods iterated through the list until they pointed to the first
 * and last elements respectively. This was inefficient as the getFirst, getLast
 * methods and any methods which utilized them such as the addToEnd and find
 * methods would have a minimum complexity of O(n).
 *
 * The change which made the current efficiency possible was to keep track
 * of pointers to the first and last values and simply change to point to those
 * when the getFirst and getLast methods are called.
 *
 * Additionally this would have caused all the methods in the game which use a
 * linked list have O(n) efficiency which would slow it down significantly for
 * big game worlds.
 */


class LinkedNode<T> {

    private LinkedNode<T> before;
    private LinkedNode<T> after;
    private T value;

    LinkedNode(T value) {
        this.value = value;
        before = null;
        after = null;
    }

    void setBefore(LinkedNode<T> before) {
        this.before = before;
    }

    LinkedNode<T> getBefore() {
        return before;
    }

    void setAfter(LinkedNode<T> after) {
        this.after = after;
    }

    LinkedNode<T> getAfter() {
        return after;
    }

    T getValue() {
        return value;
    }
}