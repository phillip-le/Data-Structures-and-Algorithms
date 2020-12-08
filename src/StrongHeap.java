import java.util.ArrayDeque;
import java.util.Deque;

public class StrongHeap {

    // Stores key value pairs
    private static class Entry<K, V> {

        // Stores the key
        private K key;

        // Stores the value
        private V value;

        // Creates an Entry
        private Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     * Determines whether the binary tree with the given root node is
     * a "strong binary heap", as described in the assignment task sheet.
     *
     * A strong binary heap is a binary tree which is:
     *  - a complete binary tree, AND
     *  - its values satisfy the strong heap property.
     *
     * Time Complexity: O(n) because all nodes need to be checked to ensure that
     * the binary tree is complete.
     *
     * Memory Complexity: O(n) because the number of nodes that need to be added
     * to the queue is proportional to the number of nodes in the binary tree.
     *
     * @param root root of a binary tree, cannot be null.
     * @return true if the tree is a strong heap, otherwise false.
     */
    public static boolean isStrongHeap(BinaryTree<Integer> root) {
        // TODO: implement question 2
        if (!isMaxHeap(root)) {
            return false;
        }
        Deque<BinaryTree<Integer>> dq = new ArrayDeque<>();
        dq.add(root);
        while (!dq.isEmpty()) {
            BinaryTree<Integer> node = dq.remove();
            if (node.isLeaf()) {
                return true;
            }
            if (!hasGrandchildren(node)) {
                continue;
            }
            int nodeValue = node.getValue();
            BinaryTree<Integer> lc = node.getLeft();
            BinaryTree<Integer> rc = node.getRight();
            if (breaksStrongheapProperty(lc.getLeft(), lc, nodeValue) ||
                    breaksStrongheapProperty(lc.getRight(), lc, nodeValue) ||
                    breaksStrongheapProperty(rc.getLeft(), rc, nodeValue) ||
                    breaksStrongheapProperty(rc.getRight(), rc, nodeValue)) {
                return false;
            }
            dq.add(lc);
            dq.add(rc);
        }
        return true;
    }

    /**
     * Checks if the given binary tree is a max heap.
     *
     * Time Complexity: O(n) because every node in the tree must be visited.
     *
     * Memory Complexity: O(n) if all nodes other than the root node are stored
     * in the second level.
     *
     * @param root the root of the binary tree, cannot be null.
     * @return true if the binary tree is a max heap, else false.
     */
    private static boolean isMaxHeap(BinaryTree<Integer> root) {
        Deque<Entry<BinaryTree<Integer>, Integer>> dq = new ArrayDeque<>();
        dq.add(new Entry<>(root, 1));
        int size = 0;
        Entry<BinaryTree<Integer>, Integer> entry = dq.element();
        while (!dq.isEmpty()) {
            entry = dq.pop();
            size++;
            BinaryTree<Integer> node = entry.key;
            BinaryTree<Integer> lc = node.getLeft(), rc = node.getRight();
            if (lc == null && rc != null) {
                return false;
            }
            if (lc != null) {
                if (lc.getValue() >= node.getValue()) {
                    return false;
                }
                dq.add(new Entry<>(lc, entry.value * 2));
            }
            if (rc != null) {
                if (rc.getValue() >= node.getValue()) {
                    return false;
                }
                dq.add(new Entry<>(rc, entry.value * 2 + 1));
            }
        }
        return entry.value == size;
    }

    /**
     * Checks if the complete binary tree root has grandchildren.
     *
     * Time Complexity: O(1) because accessing the child and grandchild of the
     * root and checking a condition are primitive operations.
     *
     * Memory Complexity: O(1) because a constant amount of memory is allocated.
     *
     * @param root the root of a complete binary tree, cannot be null.
     * @return true if root has grandchildren, else false.
     */
    private static boolean hasGrandchildren(BinaryTree<Integer> root) {
        return root.getLeft() != null && root.getLeft().getLeft() != null;
    }

    /**
     * Checks if the child and parent nodes violate the second strong heap
     * property which is that the sum of the child and parent values must be
     * less than the grandparent's value.
     * @param child the child node of the parent to test
     * @param parent the node of the heap to test
     * @param grandparentValue the value of the grandparent
     * @return true if the strong property is violated, else false.
     */
    private static boolean breaksStrongheapProperty(BinaryTree<Integer> child,
            BinaryTree<Integer> parent, int grandparentValue) {
        return child != null &&
                parent.getValue() + child.getValue() >= grandparentValue;
    }
}
