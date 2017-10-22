package au.edu.uq.itee.comp3506.assn2.tests;

import au.edu.uq.itee.comp3506.assn2.entities.AVLTree;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * AVLTreeTest
 * Created 21/10/2017
 *
 * @author Brae Webb
 */
public class AVLTreeTest {
    @Test
    public void testInsert() {
        AVLTree<Integer, Integer> tree = new AVLTree<>();

        tree.insert(1, 20);
        tree.insert(2, 30);
        tree.insert(3, 40);
        tree.insert(4, 50);
        tree.insert(5, 10);
        tree.insert(6, 23);
        tree.insert(7, 2395);
        tree.insert(8, 485);
        tree.insert(9, 451);
        tree.insert(10, 45);
        tree.insert(11, 3454);

        assertEquals(tree.toString(), "└── 4 : 50\n" +
                "    ├── 2 : 30\n" +
                "    │   ├── 1 : 20\n" +
                "    │   └── 3 : 40\n" +
                "    └── 8 : 485\n" +
                "        ├── 6 : 23\n" +
                "        │   ├── 5 : 10\n" +
                "        │   └── 7 : 2395\n" +
                "        └── 10 : 45\n" +
                "            ├── 9 : 451\n" +
                "            └── 11 : 3454\n");

        Integer[] keys = tree.getKeys().toArray(new Integer[tree.getKeys().size()]);
        assertArrayEquals(keys, new Integer[]{4, 2, 1, 3, 8, 6, 5, 7, 10, 9, 11});

        Integer[] values = tree.getValues().toArray(new Integer[tree.getValues().size()]);
        assertArrayEquals(values, new Integer[]{50, 30, 20, 40, 485, 23, 10, 2395, 45, 451, 3454});

        tree = new AVLTree<>();

        tree.insert(23, 20);
        tree.insert(22, 30);
        tree.insert(21, 40);
        tree.insert(20, 50);
        tree.insert(19, 10);

        assertEquals(tree.toString(), "└── 22 : 30\n" +
                "    ├── 20 : 50\n" +
                "    │   ├── 19 : 10\n" +
                "    │   └── 21 : 40\n" +
                "    └── 23 : 20\n");

        keys = tree.getKeys().toArray(new Integer[tree.getKeys().size()]);
        assertArrayEquals(keys, new Integer[]{22, 20, 19, 21, 23});

        values = tree.getValues().toArray(new Integer[tree.getValues().size()]);
        assertArrayEquals(values, new Integer[]{30, 50, 10, 40, 20});
    }
}
