@SuppressWarnings("unchecked")
public class QuaternaryHeapsort {

    /**
     * Sorts the input array, in-place, using a quaternary heap sort.
     *
     * Time Complexity: O(n log n) because performing the downheap requires
     * O(log n) time and the bottom up construction of the heap loops O(n) times
     * which means that the overall time complexity is O(n * log n).
     *
     * Memory Complexity: O(1) because downheap has O(1) memory complexity and
     * removeMax modifies the input array in-place so extra memory does not need
     * to be allocated.
     *
     * @param input to be sorted (modified in place)
     */
    public static <T extends Comparable<T>> void quaternaryHeapsort(T[] input) {
        // TODO: implement question 1 here
        for (int i = input.length / 4 - 1; i >= 0; i--) {
            quaternaryDownheap(input, i, input.length);
        }
        int size = input.length;
        for (int i = input.length; i > 0; i--) {
            size = removeMax(input, size);
        }
    }

    /**
     * Performs a downheap from the element in the given position on the given max heap array.
     *
     * A downheap should restore the heap order by swapping downwards as necessary.
     * The array should be modified in place.
     * 
     * You should only consider elements in the input array from index 0 to index (size - 1)
     * as part of the heap (i.e. pretend the input array stops after the inputted size).
     *
     * Time Complexity: O(log n) because the heap has a height of O(log n) and
     * so O(log n) swaps will be required to restore the heap order.
     *
     * Memory Complexity: O(1) because a constant amount of memory is required
     * to temporarily store the children and the indexes.
     *
     * @param input array representing a quaternary max heap.
     * @param start position in the array to start the downheap from.
     * @param size the size of the heap in the input array, starting from index 0
     */
    public static <T extends Comparable<T>> void quaternaryDownheap(T[] input, int start, int size) {
        // TODO: implement question 1 here
        int j = start;
        while (hasChildren(j, size)) {
            int largestChild = 0;
            T[] children = getChildren(input, j, size);
            for (int i = 1; i < children.length && children[i] != null; i++) {
                if (children[largestChild].compareTo(children[i]) < 0) {
                    largestChild = i;
                }
            }
            int largestChildIndex = 4 * j + (largestChild + 1);
            if (input[largestChildIndex].compareTo(input[j]) <= 0) {
                break;
            }
            swap(input, j, largestChildIndex);
            j = largestChildIndex;
        }
    }

    /**
     * Gets the index of the left child of the parent.
     *
     * Time Complexity: O(1) because multiplication and addition are primitive
     * operations.
     *
     * Memory Complexity: O(1) because a constant amount of memory is required.
     *
     * @param parent the index of the parent.
     * @return the index of the left child of the parent.
     */
    private static <T extends Comparable<T>> int getLeftChildIndex(int parent) {
        return parent * 4 + 1;
    }

    /**
     * Checks if the parent has a child.
     *
     * Time Complexity: O(1) because checking the condition and computing the
     * child index requires constant time.
     *
     * Memory Complexity: O(1) because a constant amount of memory is required.
     *
     * @param parent the index of the parent.
     * @param size the size of the heap in the input array
     * @return true if the parent has a child, else false.
     */
    private static <T extends Comparable<T>> boolean hasChildren(int parent,
            int size) {
        return getLeftChildIndex(parent) < size;
    }

    /**
     * Gets the children of the parent.
     *
     * Time Complexity: O(1) because the loop will always get up to four
     * children and this is independent of the size of the input array.
     *
     * Memory Complexity: O(1) because the memory allocated for storing four
     * children.
     *
     * @param input array representing a quaternary max heap.
     * @param parent the index of the parent.
     * @param size the size of the heap in the input array
     * @return the children of the parent.
     */
    private static <T extends Comparable<T>> T[] getChildren(T[] input,
            int parent, int size) {
        T[] children = (T[]) new Comparable[4];
        for (int i = 1; i < 5; i++) {
            int child = parent * 4 + i;
            if (child < size) {
                children[i - 1] = input[child];
            }
        }
        return children;
    }

    /**
     * Swaps the elements at index i and j in the input array.
     *
     * Time Complexity: O(1) because the size of the input does not impact the
     * time to complete the function.
     *
     * Memory Complexity: O(1) because a constant amount of memory is required
     * for the tmp variable.
     *
     * @param input array representing a quaternary max heap.
     * @param i the index of the first element to swap.
     * @param j the index of the second element to swap.
     */
    private static <T extends Comparable<T>> void swap(T[] input, int i,
            int j) {
        T tmp = input[i];
        input[i] = input[j];
        input[j] = tmp;
    }

    /**
     * Removes the maximum element of the heap and gets the size of the heap
     * after removing the maximum element.
     *
     * Time Complexity: O(log n) because it needs to restore the heap order with
     * quaternaryDownheap.
     *
     * Memory Complexity: O(1) because a constant amount of memory is required.
     *
     * @param input array representing a quaternary max heap.
     * @param size the size of the heap in the input array.
     * @return the size of the heap after removing the max element.
     */
    private static <T extends Comparable<T>> int removeMax(T[] input,
            int size) {
        swap(input, 0, size - 1);
        quaternaryDownheap(input, 0, --size);
        return size;
    }
}
