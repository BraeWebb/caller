package au.edu.uq.itee.comp3506.assn2.collections;

import au.edu.uq.itee.comp3506.assn2.nodes.TreeNode;

public interface Tree<K, V> {
    void insert(K key, V value);

    List<V> find(K key);

    List<V> range(K lower, K upper);

    List<TreeNode<K, V>> getNodes();
}
