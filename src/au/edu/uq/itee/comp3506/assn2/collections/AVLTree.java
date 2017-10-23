package au.edu.uq.itee.comp3506.assn2.collections;

import au.edu.uq.itee.comp3506.assn2.nodes.TreeNode;


public class AVLTree<K extends Comparable<? super K>, V> implements Tree<K, V> {
    private TreeNode<K, V> root;

    public AVLTree() {
        root = null;
    }

    public void insert(K key, V value) {
        root = insertBelow(key, value, root);
    }

    public void replace(K key, V value) {
        List<V> values = find(key);
        if (values == null) {
            insert(key, value);
        } else {
            values.replace(value);
        }
    }

    public List<V> find(K key) {
        return find(key, root);
    }

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

    public String toString() {
        return root.toString();
    }

    // TODO: REMOVE THIS PLZ
    public boolean plz() {
        return isNotFucked(root);
    }

    public boolean isNotFucked(TreeNode<K, V> node) {
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

    public int getDepth(TreeNode<K, V> node) {
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

    public List<V> range(K lower, K upper) {
        // TODO: Handle this
//        if (lower.hashCode() > upper.hashCode())
//            throw new IllegalArgumentException("lower > upper");

        if (root == null) {
            return new LinkedList<>();
        }
        return range(lower, upper, root);
    }

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

    public List<TreeNode<K, V>> getNodes() {
        return getNodes(root);
    }

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
}

