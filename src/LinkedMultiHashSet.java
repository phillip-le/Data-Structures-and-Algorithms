import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * LinkedMultiHashSet is an implementation of a (@see MultiSet), using a hashtable as the internal
 * data structure, and with predictable iteration order based on the insertion order
 * of elements.
 * 
 * Its iterator orders elements according to when the first occurrence of the element 
 * was added. When the multiset contains multiple instances of an element, those instances 
 * are consecutive in the iteration order. If all occurrences of an element are removed, 
 * after which that element is added to the multiset, the element will appear at the end of the 
 * iteration.
 * 
 * The internal hashtable array should be doubled in size after an add that would cause it to be
 * at full capacity. The internal capacity should never decrease.
 * 
 * Collision handling for elements with the same hashcode (i.e. with hashCode()) should be done
 * using linear probing, as described in lectures.
 * 
 * @param <T> type of elements in the set
 */
@SuppressWarnings("unchecked")
public class LinkedMultiHashSet<T> implements MultiSet<T>, Iterable<T> {

    // TODO: implement question 4 in this file

    // Stores key value pairs containing the element and number of occurrences
    private static class Entry<T> {

        // Stores the key
        private final T key;

        // Stores the number of duplicates of the key
        private int value;

        // Stores a reference to the next entry that was inserted
        private Entry<T> nextEntry;

        // Stores a reference to the previous entry that was inserted
        private Entry<T> prevEntry;

        // Creates an Entry
        private Entry(T key, int value, Entry<T> prevEntry) {
            this.key = key;
            this.value = value;
            this.nextEntry = null;
            this.prevEntry = prevEntry;
        }
    }

    // Represents an entry that has been removed
    private final Entry<T> DEFUNCT = new Entry<>(null, -1, null);

    // Stores the entries of the set
    private Entry<T>[] entries;

    // Stores the capacity of the set
    private int capacity;

    // Stores total number of occurrences of all keys
    private int size;

    // Stores the number of distinct elements in the set
    private int distinctCount;

    // Stores the entry that was first inserted
    private Entry<T> firstEntry;

    // Stores the entry that was last inserted
    private Entry<T> lastEntry;

    /**
     * Constructs a LinkedMultiHashSet with a capacity of initialCapacity.
     *
     * Memory Complexity: O(n) where n is the initialCapacity because memory
     * is allocated based on the max capacity of the array.
     *
     * @param initialCapacity the initial capacity of the LinkedMultiHashSet.
     */
    public LinkedMultiHashSet(int initialCapacity) {
        capacity = initialCapacity;
        size = 0;
        distinctCount = 0;
        entries = new Entry[capacity];
        firstEntry = null;
        lastEntry = null;
    }

    /**
     * Time Complexity: O(n) if all slots are full because then all slots would
     * need to be searched.
     *
     * Memory Complexity: O(1) because a constant amount of memory is allocated
     * for the indexes.
     */
    @Override
    public void add(T element) {
        insert(element, 1);
    }

    /**
     * Time Complexity: O(n) if all slots are full because then all slots would
     * need to be searched.
     *
     * Memory Complexity: O(1) because a constant amount of memory is allocated
     * for the indexes.
     */
    @Override
    public void add(T element, int count) {
        insert(element, count);
    }

    /**
     * Time Complexity: O(n) if all slots are full because then all slots would
     * need to be searched before concluding that the element is not in the set.
     *
     * Memory Complexity: O(1) because a constant amount of memory is allocated
     * for the indexes.
     */
    @Override
    public boolean contains(T element) {
        int slot = findSlot(element);
        return slot != -1 && !isAvailable(entries[slot]) &&
                entries[slot].value > 0;
    }

    /**
     * Time Complexity: O(n) if all slots are full because then all slots would
     * need to be searched before concluding that the element is not in the set.
     *
     * Memory Complexity: O(1) because a constant amount of memory is allocated
     * for the indexes.
     */
    @Override
    public int count(T element) {
        return contains(element) ? entries[findSlot(element)].value : 0;
    }

    /**
     * Time Complexity: O(n) if all slots are full because then all slots would
     * need to be searched before concluding that the element is not in the set.
     *
     * Memory Complexity: O(1) because a constant amount of memory is allocated
     * for the indexes.
     */
    @Override
    public void remove(T element) throws NoSuchElementException {
        discard(element, 1);
    }

    /**
     * Time Complexity: O(n) if all slots are full because then all slots would
     * need to be searched before concluding that the element is not in the set.
     *
     * Memory Complexity: O(1) because a constant amount of memory is allocated
     * for the indexes.
     */
    @Override
    public void remove(T element, int count) throws NoSuchElementException {
        discard(element, count);
    }

    /**
     * Time Complexity: O(1) because returning a value is a primitive operation.
     *
     * Memory complexity: O(1) because a constant amount of memory is allocated.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Time Complexity: O(1) because returning a value is a primitive operation.
     *
     * Memory complexity: O(1) because a constant amount of memory is allocated.
     */
    @Override
    public int internalCapacity() {
        return capacity;
    }

    /**
     * Time Complexity: O(1) because returning a value is a primitive operation.
     *
     * Memory complexity: O(1) because a constant amount of memory is allocated.
     */
    @Override
    public int distinctCount() {
        return distinctCount;
    }

    /**
     * Time Complexity: O(1) because assigning the private variables are
     * primitive operations.
     *
     * Memory Complexity: O(1) because the memory allocated to storing the
     * private variables is fixed.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            // Stores the number of keys remaining to iterate over
            private int keysRemaining = distinctCount;

            // Stores the number of remaining occurrences of the current key to
            // iterate over
            private int dupesLeft = firstEntry != null ? firstEntry.value : 0;

            // Stores the current entry
            private Entry<T> currentEntry = firstEntry;

            /**
             * Time Complexity: O(1) because checking a condition is a primitive
             * operation.
             *
             * Memory Complexity: O(1) because a constant amount of memory is
             * allocated.
             */
            @Override
            public boolean hasNext() {
                return keysRemaining != 0;
            }

            /**
             * Time Complexity: O(1) because accessing the key of an entry,
             * checking conditions and assigning the current entry are all
             * primitive operations.
             *
             * Memory Complexity: O(1) because a constant amount of memory is
             * allocated for the result.
             */
            @Override
            public T next() {
                if (dupesLeft == 0) {
                    currentEntry = currentEntry.nextEntry;
                    dupesLeft = currentEntry.value;
                }
                T result = currentEntry.key;
                if (dupesLeft-- == 1) {
                    keysRemaining--;
                }
                return result;
            }
        };
    }

    /**
     * Normalises the given hashcode to the range [0, capacity - 1].
     *
     * Time Complexity: O(1) because abs and % are primitive functions.
     *
     * Memory Complexity: O(1) because a constant amount of memory is allocated.
     *
     * @param hashcode the hashcode to normalise.
     * @return the normalised hashcode.
     */
    private int normaliseHash(int hashcode) {
        return Math.abs(hashcode) % capacity;
    }

    /**
     * Time Complexity: O(1) because checking conditions are primitive
     * operations.
     *
     * Memory Complexity: O(1) because a constant amount of memory is allocated.
     *
     * Checks if the slot is available to insert a new entry.
     * @param entry entry to check if a new entry can be inserted.
     * @return true if slot is available, else false.
     */
    private boolean isAvailable(Entry<T> entry) {
        return entry == null || entry.equals(DEFUNCT);
    }

    /**
     * Finds the next free slot for the given element or the slot where the
     * element already exists in the set.
     *
     * Time Complexity: O(n) if all slots are full because then all slots would
     * need to be searched.
     *
     * Memory Complexity: O(1) because a constant amount of memory is allocated
     * for the indexes.
     *
     * @param element the element to find a slot for.
     * @return the slot for the element.
     */
    private int findSlot(T element) {
        int h = normaliseHash(element.hashCode());
        int i = h;
        do {
            if (isAvailable(entries[i]) || entries[i].key.equals(element)) {
                return i;
            }
            i = (i + 1) % capacity;
        } while (i != h);
        return -1;
    }

    /**
     * Inserts the given element into the set with its count.
     *
     * Time Complexity: O(n) if all slots are full because then all slots would
     * need to be searched.
     *
     * Memory Complexity: O(1) because a constant amount of memory is allocated
     * for the indexes.
     *
     * @param element the element to insert.
     * @param count the number of occurrences of the element to add.
     */
    private void insert(T element, int count) {
        int defunctSlot = -1, slot = -1;
        int h = normaliseHash(element.hashCode());
        int i = h;
        do {
            if (entries[i] == null) {
                slot = i;
                break;
            } else if (defunctSlot == -1 && entries[i].equals(DEFUNCT)) {
                defunctSlot = i;
            } else if (entries[i].key.equals(element)) {
                slot = i;
                break;
            }
            i = (i + 1) % capacity;
        } while (i != h);

        if (slot == -1 && defunctSlot >= 0) {
            slot = defunctSlot;
        }
        // Create new entry
        if (isAvailable(entries[slot])) {
            entries[slot] = new Entry<>(element, 0, lastEntry);
            distinctCount++;
            if (lastEntry != null) {
                lastEntry.nextEntry = entries[slot];
            } else {
                firstEntry = entries[slot];
            }
            lastEntry = entries[slot];
        }
        entries[slot].value += count;
        size += count;

        if (distinctCount == capacity) {
            resize();
        // Replace DEFUNCT entry with updated entry
        } else if (defunctSlot >= 0 && slot != defunctSlot) {
            entries[defunctSlot] = entries[slot];
            entries[slot] = null;
        }
    }

    /**
     * Discards element from the set based on the count given.
     *
     * Time Complexity: O(n) if all slots are full because then all slots would
     * need to be searched before concluding that the element is not in the set.
     *
     * Memory Complexity: O(1) because a constant amount of memory is allocated
     * for the indexes.
     *
     * @param element the element to discard.
     * @param count the number of occurrences to discard.
     * @throws NoSuchElementException if the element is not in the set or if the
     * count > the number of occurrences of the element.
     */
    private void discard(T element, int count) throws NoSuchElementException {
        int slot = -1, h = normaliseHash(element.hashCode());
        int i = h;
        do {
            if (!isAvailable(entries[i]) && entries[i].key.equals(element)) {
                slot = i;
                break;
            }
            i = (i + 1) % capacity;
        } while (i != h);

        if (slot == -1 || count > entries[slot].value) {
            throw new NoSuchElementException();
        }

        size -= count;
        entries[slot].value -= count;
        if (entries[slot].value == 0) {
            if (distinctCount == 1) {
                firstEntry = null;
            } else {
                firstEntry = firstEntry.nextEntry;
            }
            if (entries[slot].prevEntry != null) {
                entries[slot].prevEntry.nextEntry = entries[slot].nextEntry;
            }
            if (entries[slot].nextEntry != null) {
                entries[slot].nextEntry.prevEntry = entries[slot].prevEntry;
            }
            entries[slot] = DEFUNCT;
            distinctCount--;
        }
    }

    /**
     * Doubles the size of the array.
     *
     * Time Complexity: O(n) because where n is the size of the new array
     * because each element of the new array is initialised to null.
     *
     * Memory Complexity: O(n) because memory is allocated based on the max size
     * of the array.
     */
    private void resize() {
        capacity *= 2;
        Entry<T>[] oldEntries = entries;
        entries = new Entry[capacity];
        for (int i = 0; i < capacity / 2; i++) {
            entries[findSlot(oldEntries[i].key)] = oldEntries[i];
        }
    }
}