package au.edu.uq.itee.comp3506.assn2.nodes;

public class LinkedNode<T> {

    private LinkedNode<T> before;
    private LinkedNode<T> after;
    private T value;

    public LinkedNode(T value) {
        this.value = value;
        before = null;
        after = null;
    }

    public void setBefore(LinkedNode<T> before) {
        this.before = before;
    }

    public LinkedNode<T> getBefore() {
        return before;
    }

    public void setAfter(LinkedNode<T> after) {
        this.after = after;
    }

    public LinkedNode<T> getAfter() {
        return after;
    }

    public T getValue() {
        return value;
    }
}
