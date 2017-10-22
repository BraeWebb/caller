package au.edu.uq.itee.comp3506.assn2.entities;

import java.util.Iterator;

/**
 * Tree
 * Created 21/10/2017
 *
 * @author Brae Webb
 */
public interface Tree<K, V> {
    void insert(K key, V value);

    void remove(K key);

    V find(K key);
}
