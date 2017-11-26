package au.edu.uq.itee.comp3506.assn2.collections;

import au.edu.uq.itee.comp3506.assn2.nodes.TreeNode;

/**
 * A AVL Tree data structure which maps keys to sets of values.
 *
 * An AVL is a self balancing binary search tree which has efficient lookup
 * times and insertion times to allow for fast query processing.
 *
 * Memory Efficiency: O(n) where n is the amount of key/value pairs that are
 * stored in the AVL tree. Linear memory efficiency is achieved as
 * each value stored in the tree at a given key will increase the number of
 * entries in that keys node by constant complexity.
 *
 * @author Brae Webb <s4435400@student.uq.edu.au>
 *
 * @param <K> The type of the tree keys.
 * @param <V> The type of the tree values.
 */
public class AVLTree<K extends Comparable<? super K>, V> implements Tree<K, V> {
    // The root node of the AVL tree
    private TreeNode<K, V> root;

    /**
     * Construct an empty AVL tree which contains no values.
     *
     * Runtime Efficiency: O(1) as the assignment operation is constant
     * efficiency.
     */
    public AVLTree() {
        root = null;
    }

    /**
     * Inserts a new record of key mapping to some value.
     *
     * Runtime Efficiency: O(log(n)) where n is the number of nodes stored in
     * the AVL tree. This is because the internal method insertBelow has
     * O(log(n)) efficiency.
     *
     * @param key The key value of the new entry.
     * @param value A value to map to the key.
     */
    public void insert(K key, V value) {
        root = insertBelow(key, value, root);
    }

    /**
     * Inserts a new record of key mapping to some value below a given starting
     * node.
     *
     * Runtime Efficiency: O(log(n)) where n is the number of nodes stored in
     * the AVL tree.
     * This is because the worst case will be finding the
     * appropriate location to store the record at the bottom of the tree.
     * In this worst case the height of the tree has been traversed.
     * As the tree is a balanced binary tree the maximum height of the tree is log(n).
     * Thus in the worst case log(n) records will be traversed.
     *
     * @param key The key value of the new entry.
     * @param value A value to map to the key.
     * @param start The node to add records below.
     * @return The new starting node if the tree was rotated.
     */
    private TreeNode<K, V> insertBelow(K key, V value, TreeNode<K, V> start) {
        TreeNode<K, V> node = start;

        if (node == null) {
            node = new TreeNode<>(key);
            node.addValue(value);
        } else if (key.compareTo(node.getKey()) < 0) {
            node.setLeft(insertBelow(key, value, start.getLeft()));

            if (height(node.getLeft()) - height(node.getRight()) == 2) {
                if (key.compareTo(node.getLeft().getKey()) < 0) {
                    node = leftRotate(node);
                } else {
                    node = leftDoubleRotate(node);
                }
            }
        } else if (key.compareTo(node.getKey()) > 0) {
            node.setRight(insertBelow(key, value, node.getRight()));

            if (height(node.getRight()) - height(node.getLeft()) == 2) {
                if (key.compareTo(node.getRight().getKey()) > 0) {
                    node = rightRotate(node);
                } else {
                    node = rightDoubleRotate(node);
                }
            }
        } else {
            node.addValue(value);
        }

        node.setHeight(calculateHeight(node));
        return node;
    }

    /**
     * Find all the values mapped to a given key.
     *
     * Runtime Efficiency: O(log(n)) where n is the number of nodes stored in
     * the AVL tree. This is because the internal method find has O(log(n))
     * runtime efficiency.
     *
     * @param key The key to find values for.
     * @return A list of all the values mapped to a given key.
     */
    public List<V> find(K key) {
        return find(key, root);
    }

    /**
     * Find all the values mapped to a given key below a given node.
     *
     * Runtime Efficiency: O(log(n)) where n is the number of nodes stored in
     * the AVL tree. This is because the worst case would be that the node was
     * found at the bottom or the tree or not at all. If not found then it will
     * have traversed to the bottom of the tree to discover this.
     * As this is a balanced binary tree the height of the tree will be log(n).
     * Thus the worst case running time is O(log(n)).
     *
     * @param key The key to find values for.
     * @param node The node to look below for values.
     * @return A list of all the values mapped to a given key.
     */
    private List<V> find(K key, TreeNode<K, V> node) {
        if (node == null) {
            return new LinkedList<>();
        } else if (key.compareTo(node.getKey()) < 0) {
            return find(key, node.getLeft());
        } else if (key.compareTo(node.getKey()) > 0) {
            return find(key, node.getRight());
        }
        return node.getValues();
    }

    /**
     * Replace the first value of a key's value collection with the given value.
     *
     * Runtime Efficiency: O(log(n)) where n is the number of nodes in this
     * AVL tree. This is because the find and insert operations are below
     * O(log(n)) causing the runtime efficiency of this method to be O(log(n)).
     *
     * @param key The key of the value to replace.
     * @param value The value to replace with.
     */
    public void replace(K key, V value) {
        List<V> values = find(key);
        if (values == null) {
            insert(key, value);
        } else {
            values.replace(value);
        }
    }

    /**
     * Find all the values between a lower key and an upper key.
     *
     * Runtime Efficiency: O(log(n) + k) where n is the number of nodes in the
     * AVL tree and k is the number of nodes found by the range search.
     * This is because the internal method range is O(log(n) + k).
     *
     * @param lower The lower key value.
     * @param upper The upper key value.
     * @return All the values found between the lower and upper key values.
     */
    public List<V> range(K lower, K upper) {
        if (root == null) {
            return new LinkedList<>();
        }
        return range(lower, upper, root);
    }

    /**
     * Find all the values between a lower key and an upper key and below a
     * given node.
     *
     * Runtime Efficiency: O(log(n) + k) where n is the number of nodes in the
     * AVL tree and k is the number of nodes found by the range search.
     * This is because the worst case is can either be the amount of nodes
     * traversed to find all the values or the amount of nodes traversed to find
     * the initial starting node.
     *
     * @param lower The lower key value.
     * @param upper The upper key value.
     * @param node The node to search below.
     * @return All the values found between the lower and upper key values
     * and below a given node.
     */
    private List<V> range(K lower, K upper, TreeNode<K, V> node) {

        List<V> list = new LinkedList<>();

        if (node == null) {
            return list;
        }

        if (node.getLeft() != null) {
            if (node.getKey().compareTo(lower) >= 0) {
                list.addAll(range(lower, upper, node.getLeft()));
            }
        }

        if (node.getKey().compareTo(lower) >= 0 && node.getKey().compareTo(upper) <= 0) {
            list.addAll(node.getValues());
        }

        if (node.getRight() != null) {
            if (node.getKey().compareTo(upper) <= 0) {
                list.addAll(range(lower, upper, node.getRight()));
            }
        }

        return list;
    }

    /**
     * Retrieve all the nodes stored in the AVL Tree.
     *
     * Runtime Efficiency: O(n) where n is the number of nodes in the AVL tree.
     * This is because the internal method getNodes has O(n) efficiency.
     *
     * @return All the nodes in the tree.
     */
    public List<TreeNode<K, V>> getNodes() {
        return getNodes(root);
    }

    /**
     * Retrieve all the nodes stored in the AVL Tree below a given node.
     *
     * Runtime Efficiency: O(n) where n is the number of nodes in the AVL tree.
     * This is because the each node in the AVL tree needs to be traversed to
     * find all the nodes.
     *
     * @param root The node to look below.
     * @return All the nodes in the tree.
     */
    private List<TreeNode<K, V>> getNodes(TreeNode<K, V> root) {
        List<TreeNode<K, V>> list = new LinkedList<>();

        if (root == null) {
            return list;
        }
        list.add(root);

        if (root.getLeft() != null) {
            list.addAll(getNodes(root.getLeft()));
        }
        if (root.getRight() != null) {
            list.addAll(getNodes(root.getRight()));
        }

        return list;
    }

    /**
     * Retrieve the string representation of the AVL tree.
     *
     * Runtime Efficiency: O(n) where n is the number of nodes in the tree.
     * This is because the TreeNode's toString method is O(n).
     *
     * @return The string representation of the AVL tree.
     */
    public String toString() {
        return root.toString();
    }

    private int height(TreeNode<K, V> node) {
        if (node == null) {
            return -1;
        }
        return node.getHeight();
    }

    private int calculateHeight(TreeNode<K, V> node) {
        if (node == null) {
            return -1;
        }
        if (height(node.getLeft()) > height(node.getRight())) {
            return height(node.getLeft()) + 1;
        } else {
            return height(node.getRight()) + 1;
        }
    }

    private TreeNode<K, V> leftRotate(TreeNode<K, V> node) {
        TreeNode<K, V> left = node.getLeft();

        node.setLeft(left.getRight());
        left.setRight(node);

        node.setHeight(calculateHeight(node));
        left.setHeight(calculateHeight(node));

        return left;
    }

    private TreeNode<K, V> leftDoubleRotate(TreeNode<K, V> node) {
        node.setLeft(rightRotate(node.getLeft()));
        return leftRotate(node);
    }

    private TreeNode<K, V> rightRotate(TreeNode<K, V> node) {
        TreeNode<K, V> right = node.getRight();

        node.setRight(right.getLeft());
        right.setLeft(node);

        node.setHeight(calculateHeight(node));
        right.setHeight(calculateHeight(right));

        return right;
    }

    private TreeNode<K, V> rightDoubleRotate(TreeNode<K, V> node) {
        node.setRight(leftRotate(node.getRight()));
        return rightRotate(node);
    }
}

