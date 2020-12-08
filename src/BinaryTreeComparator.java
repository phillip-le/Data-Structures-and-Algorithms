import java.util.Comparator;

/**
 * A comparator for Binary Trees.
 */
public class BinaryTreeComparator<E extends Comparable<E>> implements Comparator<BinaryTree<E>> {

    /**
     * Compares two binary trees with the given root nodes.
     *
     * Two nodes are compared by their left childs, their values, then their right childs,
     * in that order. A null is less than a non-null, and equal to another null.
     *
     * Time Complexity: O(n) because in the worst case all nodes need to be
     * compared.
     *
     * Memory Complexity: O(log n) because the tree has a height of O(log n) and
     * in the worst case, all of the nodes down one subtree need to be stored on
     * the call stack.
     *
     * @param tree1 root of the first binary tree, may be null.
     * @param tree2 root of the second binary tree, may be null.
     * @return -1, 0, +1 if tree1 is less than, equal to, or greater than tree2, respectively.
     */
    @Override
    public int compare(BinaryTree<E> tree1, BinaryTree<E> tree2) {
        // TODO: implement question 3 here
        if (tree1 == null && tree2 == null) {
            return 0;
        } else if (tree1 == null) {
            return -1;
        } else if (tree2 == null) {
            return 1;
        }
        int result = compare(tree1.getLeft(), tree2.getLeft());
        if (result == 0) {
            result = tree1.getValue().compareTo(tree2.getValue());
            if (result == 0) {
                result = compare(tree1.getRight(), tree2.getRight());
            }
        }
        return normalise(result);
    }

    /**
     * Normalises the compareResult to an integer in the range [-1, 1].
     *
     * Time Complexity: O(1) because checking the conditions and assigning the
     * result are primitive operations.
     *
     * Memory Complexity: O(1) because a constant amount of memory is allocated.
     *
     * @param compareResult the result of comparing node values.
     * @return the normalised compareResult.
     */
    private int normalise(int compareResult) {
        if (compareResult > 0) {
            return 1;
        } else if (compareResult < 0) {
            return -1;
        }
        return 0;
    }
}
