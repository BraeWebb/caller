package au.edu.uq.itee.comp3506.assn2.collections;

/**
 * List
 * Created 23/10/2017
 *
 * @author Brae Webb
 */
public interface List<T> extends Iterable<T> {
    void add(T item);

    void replace(T item);

    void addAll(List<T> items);

    boolean isEmpty();

    T getFirst();

    int size();
}
