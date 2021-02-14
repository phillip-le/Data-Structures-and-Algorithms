## A collection of data structures and algorithms
### BinaryTreeComparator
Compares two binary trees with the given root nodes.
Two nodes are compared by their left childs, their values, then their right childs, in that order. A null is less than a non-null, and equal to another null.

Time Complexity: O(n) because in the worst case all nodes need to be compared.

Memory Complexity: O(log n) because the tree has a height of O(log n) and in the worst case, all of the nodes down one subtree need to be stored on the call stack.

* param tree1 root of the first binary tree, may be null.

* param tree2 root of the second binary tree, may be null.

* return -1, 0, +1 if tree1 is less than, equal to, or greater than tree2, respectively.

### LinkedMultiHashSet
LinkedMultiHashSet is an implementation of a multiset (see MultiSet), using a hashtable as the internal data structure, with predictable iteration order based on the insertion order of elements. The iteration order is maintained like a doubly linked list, with each entry having pointers to the next and previous entry in the iteration order.

Its iterator orders elements according to when the first occurrence of the element was added. When the multiset contains multiple instances of an element, those instances are consecutive in the iteration order. If all occurrences of an element are removed, after which that element is added to the multiset, the element will appear at the end of the iteration.

The internal hashtable array is doubled in size after an add that would cause it to be at full capacity. The internal capacity never decreases.

Collision handling for elements with the same hashcode (i.e. with hashCode()) is done using linear probing.
### QuaternaryHeapsort
Sorts the input array, in-place, using a quaternary heap sort.

Time Complexity: O(n log n) because performing the downheap requires O(log n) time and the bottom up construction of the heap loops O(n) times which means that the overall time complexity is O(n log n).

Memory Complexity: O(1) because downheap has O(1) memory complexity and removeMax modifies the input array in-place so extra memory does not need to be allocated.

### StrongHeap
Determines whether the binary tree with the given root node is a "strong binary heap".
* A strong binary heap is a binary tree which is:
    - a complete binary tree, AND
    - its values satisfy the strong heap property.

Time Complexity: O(n) because all nodes need to be checked to ensure that the binary tree is complete.

Memory Complexity: O(n) because the number of nodes that need to be added to the queue is proportional to the number of nodes in the binary tree.

* param root root of a binary tree, cannot be null.

* return true if the tree is a strong heap, otherwise false.
### SortingAlgorithms
A list of classical sorting algorithms including Quicksort, Merge Sort, Insertion Sort and Selection Sort. Each sorting algorithm accepts a boolean which specifies whether the output is in ascending or descending order. 

Quicksort is implemented recursively and in-place and has an expected Time Complexity of O(n log n).

Merge Sort is implemented recursively and in-place and has an expected Time Complexity of O(n log n).