package au.edu.uq.itee.comp3506.assn2.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * AVLTree
 * Created 21/10/2017
 *
 * @author Brae Webb
 */
public class AVLTree<K, V> implements Tree<K, V> {
    private Node<K, V> root;

    public AVLTree() {
        root = null;
    }

    public void insert(K key, V value) {
        root = insertBelow(key, value, root);
    }

    public void remove(K key) {

    }

    public V find(K key) {
        return find(key, root);
    }

    private V find(K key, Node<K, V> node) {
        if (node == null) {
            return null;
        } else if (key.hashCode() < node.getKey().hashCode()) {
            return find(key, node.getLeft());
        } else if (key.hashCode() > node.getKey().hashCode()) {
            return find(key, node.getRight());
        }
        return node.getValue();
    }

    private int height(Node<K, V> node) {
        if (node == null) {
            return -1;
        }
        return node.getHeight();
    }

    private int calculateHeight(Node<K, V> node) {
        if (node == null) {
            return -1;
        }
        if (height(node.getLeft()) > height(node.getRight())) {
            return height(node.getLeft()) + 1;
        } else {
            return height(node.getRight()) + 1;
        }
    }

    private Node<K, V> insertBelow(K key, V value, Node<K, V> start) {
        Node<K, V> node = start;

        if (node == null) {
            node = new Node<>(key, value);
        } else if (key.hashCode() < node.getKey().hashCode()) {
            node.setLeft(insertBelow(key, value, start.getLeft()));

            if (height(node.getLeft()) - height(node.getRight()) == 2) {
                if (key.hashCode() < node.getLeft().getKey().hashCode()) {
                    node = leftRotate(node);
                } else {
                    node = leftDoubleRotate(node);
                }
            }
        } else if (key.hashCode() > node.getKey().hashCode()) {
            node.setRight(insertBelow(key, value, node.getRight()));

            if (height(node.getRight()) - height(node.getLeft()) == 2) {
                if (key.hashCode() > node.getRight().getKey().hashCode()) {
                    node = rightRotate(node);
                } else {
                    node = rightDoubleRotate(node);
                }
            }
        }

        node.setHeight(calculateHeight(node));
        return node;
    }

    private Node<K, V> leftRotate(Node<K, V> node) {
        Node<K, V> left = node.getLeft();

        node.setLeft(left.getRight());
        left.setRight(node);

        node.setHeight(calculateHeight(node));
        left.setHeight(calculateHeight(node));

        return left;
    }

    private Node<K, V> leftDoubleRotate(Node<K, V> node) {
        node.setLeft(rightRotate(node.getLeft()));
        return leftRotate(node);
    }

    private Node<K, V> rightRotate(Node<K, V> node) {
        Node<K, V> right = node.getRight();

        node.setRight(right.getLeft());
        right.setLeft(node);

        node.setHeight(calculateHeight(node));
        right.setHeight(calculateHeight(right));

        return right;
    }

    private Node<K, V> rightDoubleRotate(Node<K, V> node) {
        node.setRight(leftRotate(node.getRight()));
        return rightRotate(node);
    }

    public String toString() {
        return root.toString();
    }

    public boolean plz() {
        return isNotFucked(root);
    }

    public boolean isNotFucked(Node<K, V> node) {
        boolean left = true;
        boolean right = true;

        int leftHeight = 0;
        int rightHeight = 0;

        if (node.getRight() != null) {
            right = isNotFucked(node.getRight());
            rightHeight = getDepth(node.getRight());
        }
        if (node.getLeft() != null) {
            left = isNotFucked(node.getLeft());
            leftHeight = getDepth(node.getLeft());
        }

        return left && right && Math.abs(leftHeight - rightHeight) < 3;
    }

    public int getDepth(Node<K, V> node) {
        int leftHeight = 0;
        int rightHeight = 0;

        if (node.getRight() != null) {
            rightHeight = getDepth(node.getRight());
        }
        if (node.getLeft() != null) {
            leftHeight = getDepth(node.getLeft());
        }

        return Math.max(rightHeight, leftHeight) + 1;
    }



//    private String toString(LinkedNode<K, V> node) {
//        if (node == null) {
//            return "";
//        }
//        List<LinkedNode<K, V>> queue = new ArrayList<>();
//        queue.add(node);
//        LinkedNode<K, V> current;
//        LinkedNode<K, V> previous = node;
//        String string = "";
//        while (!queue.isEmpty()) {
//            current = queue.remove(0);
//            if (current == null) {
//                continue;
//            }
//
//            if (current.getHeight() != previous.getHeight()) {
//                string += "\n";
//            }
//
//            string += new String(new char[current.getHeight()*5]).replace("\0", " ");
//            string += current.getValue().toString() + " ";
//            queue.add(current.getLeft());
//            queue.add(current.getRight());
//            previous = current;
//        }
//
////        if (node.getLeft() != null) {
////            string += "Left: " + node.getLeft().getValue().toString() + " ";
////        }
////        if (node.getRight() != null) {
////            string += "Right: " + node.getRight().getValue().toString() + " ";
////        }
////        //string += "\n";
////        string += toString(node.getLeft());
////        string += toString(node.getRight());
//        return string;
//    }

    public List<Node<K, V>> getNodes() {
        return getNodes(root);
    }

    public List<Node<K, V>> getNodes(Node<K, V> root) {
        ArrayList<Node<K, V>> list = new ArrayList<>();

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

    public List<V> getValues() {
        return getValues(root);
    }

    public List<V> getValues(Node<K, V> root) {
        ArrayList<V> list = new ArrayList<>();

        if (root == null) {
            return list;
        }
        list.add(root.getValue());

        if (root.getLeft() != null) {
            list.addAll(getValues(root.getLeft()));
        }
        if (root.getRight() != null) {
            list.addAll(getValues(root.getRight()));
        }

        return list;
    }

    public List<K> getKeys() {
        return getKeys(root);
    }

    public List<K> getKeys(Node<K, V> root) {
        ArrayList<K> list = new ArrayList<>();

        if (root == null) {
            return list;
        }
        list.add(root.getKey());

        if (root.getLeft() != null) {
            list.addAll(getKeys(root.getLeft()));
        }
        if (root.getRight() != null) {
            list.addAll(getKeys(root.getRight()));
        }

        return list;
    }
}

class Node<K, V> {

    private K key;
    private V value;
    private int height;
    private Node<K, V> left = null;
    private Node<K, V> right = null;

    Node (K key, V value) {
        this.key = key;
        this.value = value;
        this.height = -1;
    }

    K getKey() {
        return key;
    }

    V getValue() {
        return value;
    }

    void setHeight(int height) {
        this.height = height;
    }

    int getHeight() {
        return height;
    }

    void setLeft(Node<K, V> left) {
        this.left = left;
    }

    Node<K, V> getLeft() {
        return this.left;
    }

    void setRight(Node<K, V> right) {
        this.right = right;
    }

    Node<K, V> getRight() {
        return this.right;
    }

    public String toString() {
        return toString("", true);
    }

    private String toString(String prefix, boolean isTail) {
        String string = (prefix + (isTail ? "└── " : "├── ") + getKey() + " : " + getValue()) + "\n";
        if (left != null) {
            string += left.toString(prefix + (isTail ? "    " : "│   "), false);
        }
        if (right != null) {
            string += right.toString(prefix + (isTail ?"    " : "│   "), true);
        }
        return string;
    }
}