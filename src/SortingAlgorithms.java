public class SortingAlgorithms {
    /**
     * Sorts the given array using the selection sort algorithm.
     * This should modify the array in-place.
     *
     * @param input An array of comparable objects.
     * @param reversed If false, the array should be sorted ascending.
     *                 Otherwise, it should be sorted descending.
     * @requires input != null
     */
    static <T extends Comparable> void selectionSort(T[] input,
            boolean reversed) {
        if (reversed) {
            for (int i = 0; i < input.length; i++) {
                int max = i;
                // Search for the max element in the unsorted part of the array
                for (int j = i + 1; j < input.length; j++) {
                    if (input[j].compareTo(input[max]) > 0) {
                        max = j;
                    }
                }
                swap(input, i, max);
            }
        }
        else {
            for (int i = 0; i < input.length; i++) {
                int min = i;
                // Search for the min element in the unsorted part of the array
                for (int j = i + 1; j < input.length; j++) {
                    if (input[j].compareTo(input[min]) < 0) {
                        min = j;
                    }
                }
                swap(input, i, min);
            }
        }
    }

    /**
     * Sorts the given array using the insertion sort algorithm.
     * This should modify the array in-place.
     *
     * @param input An array of comparable objects.
     * @param reversed If false, the array should be sorted ascending.
     *                 Otherwise, it should be sorted descending.
     * @requires input != null
     */
    static <T extends Comparable> void insertionSort(T[] input,
            boolean reversed) {
        for (int i = 1; i < input.length; i++) {
            T value = input[i];
            int j = i;
            // Swap each element until it is in the correct position in the
            // sorted part of the array
            while (j > 0 && ((!reversed && input[j - 1].compareTo(value) > 0) ||
                    (reversed && input[j - 1].compareTo(value) < 0))) {
                input[j] = input[j - 1];
                j--;
            }
            input[j] = value;
        }
    }
    
    /**
     * Sorts the given array using the merge sort algorithm.
     * This should modify the array in-place.
     * 
     * @param input An array of comparable objects.
     * @param reversed If false, the array should be sorted ascending.
     *                 Otherwise, it should be sorted descending.
     * @requires input != null
     */
    static <T extends Comparable> void mergeSort(T[] input,
            boolean reversed) {
        // Create an auxiliary array to store elements during the sort
        T[] aux = (T[]) new Comparable[input.length];
        recursiveMergeSort(input, aux, 0, input.length - 1, reversed);
    }

    /**
     * Recursively calls itself, to sort the array using merge sort.
     * @param input An array of comparable objects.
     * @param aux The auxiliary array to elements during the sort
     * @param left The left index of the partition
     * @param right The right index of the partition
     * @param reversed If false, the array should be sorted ascending.
     *                 Otherwise, it should be sorted descending.
     */
    private static <T extends Comparable<T>> void recursiveMergeSort(T[] input,
            T[] aux, int left, int right, boolean reversed) {
        if (left < right) {
            int mid = (left + right) / 2;
            recursiveMergeSort(input, aux, left, mid, reversed);
            recursiveMergeSort(input, aux, mid + 1, right, reversed);
            merge(input, aux, left, mid, right, reversed);
        }
    }

    /**
     * Merges sorted partitions.
     * @param input An array of comparable objects with sorted partitions.
     * @param aux The auxiliary array to elements during the sort
     * @param left The left index of the partition.
     * @param mid The middle index of the partition.
     * @param right The right index of the partition.
     * @param reversed If false, the array should be sorted ascending.
     *                 Otherwise, it should be sorted descending.
     */
    private static <T extends Comparable<T>> void merge(T[] input,
            T[] aux, int left, int mid, int right, boolean reversed) {
        // Copy the partitions into the auxiliary array.
        for (int k = left; k <= right; k++) {
            aux[k] = input[k];
        }

        int i = left, j = mid + 1;
        // Copy the elements in the aux array to the input in sorted order.
        for (int k = left; k <= right; k++) {
            // If all elements in the left partition are already copied,
            // copy over the elements in the right partition.
            if (i > mid) {
                input[k] = aux[j++];
            // Copy over the elements in the left partition
            } else if (j > right) {
                input[k] = aux[i++];
            } else {
                // Copy over the next element in sorting order
                if (reversed) {
                    if (aux[i].compareTo(aux[j]) > 0) {
                        input[k] = aux[i++];
                    } else {
                        input[k] = aux[j++];
                    }
                } else {
                    if (aux[i].compareTo(aux[j]) < 0) {
                        input[k] = aux[i++];
                    } else {
                        input[k] = aux[j++];
                    }
                }
            }
        }
    }

    /**
     * Sorts the given array using the quick sort algorithm.
     * This should modify the array in-place.
     *
     * You should use the value at the middle of the input array(i.e. floor(n/2))
     * as the pivot at each step.
     *
     * @param input An array of comparable objects.
     * @param reversed If false, the array should be sorted ascending.
     *                 Otherwise, it should be sorted descending.
     * @requires input != null
     */
    static <T extends Comparable> void quickSort(T[] input, boolean reversed) {
        inPlaceQuickSort(input, 0, input.length - 1, reversed);
    }

    /**
     * Sorts the given array with quick sort.
     * @param input An array of comparable objects.
     * @param left The left index of the partition.
     * @param right The right index of the partition.
     * @param reversed If false, the array should be sorted ascending.
     *                 Otherwise, it should be sorted descending.
     */
    private static <T extends Comparable<T>> void inPlaceQuickSort(T[] input,
            int left, int right, boolean reversed) {
        if (left >= right) {
            return;
        }
        // Swap the median element with the rightmost element
        int pivotIndex = (right + left) / 2;
        T pivot = input[pivotIndex];
        swap(input, pivotIndex, right);

        int i = left, j = right - 1;
        while (i <= j) {
            if (reversed) {
                // Scan until reaching value <= pivot (or left marker)
                while (i <= j && input[i].compareTo(pivot) > 0) {
                    i++;
                }
                // Scan until reaching value >= pivot (or right marker)
                while (i <= j && input[j].compareTo(pivot) < 0) {
                    j--;
                }
            } else {
                // Scan until reaching value >= pivot (or right marker)
                while (i <= j && input[i].compareTo(pivot) < 0) {
                    i++;
                }
                // Scan until reaching value <= pivot (or left marker)
                while (i <= j && input[j].compareTo(pivot) > 0) {
                    j--;
                }
            }
            if (i <= j) {
                swap(input, i++, j--);
            }
        }
        // Swap pivot into its correct position
        swap(input, i, right);
        inPlaceQuickSort(input, left, i - 1, reversed);
        inPlaceQuickSort(input, i + 1, right, reversed);
    }

    /**
     * Swaps the elements at the given indexes.
     * @param input An array that has elements to be swapped.
     * @param i The index of one of the elements to be swapped.
     * @param j The index of one of the elements to be swapped.
     */
    private static <T extends Comparable<T>> void swap(T[] input, int i,
            int j) {
        T temp = input[i];
        input[i] = input[j];
        input[j] = temp;
    }
}
