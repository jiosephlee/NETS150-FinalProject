import java.util.*;

/**
 * @param <V>   {@inheritDoc}
 * @param <Key> {@inheritDoc}
 */
public class PriorityQueue<Key extends Comparable<Key>, V> implements BinaryMinHeap<Key, V> {
    /**
     * {@inheritDoc}
     */

    ArrayList<BinaryMinHeap.Entry<Key, V>> minHeap = new ArrayList<BinaryMinHeap.Entry<Key, V>>();
    HashMap<V, Integer> hm = new HashMap<V, Integer>();

    @Override
    public int size() {
        return minHeap.size();
    }

    @Override
    public boolean isEmpty() {
        return (minHeap.isEmpty());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsValue(V value) {
        return (hm.get(value) != null);
    }

    void minHeapify(ArrayList<BinaryMinHeap.Entry<Key, V>> a, int i) {
        int l = ((i + 1) * 2) - 1;
        int r = (i + 1) * 2;
        int smallest;
        if (l < a.size() && a.get(l).key.compareTo(a.get(i).key) < 0) {
            smallest = l;
        } else {
            smallest = i;
        }
        if (r < a.size() && a.get(r).key.compareTo(a.get(smallest).key) < 0) {
            smallest = r;
        }
        if (smallest != i) {
            BinaryMinHeap.Entry<Key, V> temp = new BinaryMinHeap.Entry<Key, V>(a.get(i).key,
                    a.get(i).value);
            hm.put(a.get(i).value, smallest);
            hm.put(a.get(smallest).value, i);
            a.set(i, a.get(smallest));
            a.set(smallest, temp);
            minHeapify(a, smallest);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(Key key, V value) {
        if (key == null || this.containsValue(value)) {
            throw new IllegalArgumentException();
        }
        BinaryMinHeap.Entry<Key, V> newEntry = new BinaryMinHeap.Entry<Key, V>(key, value);
        minHeap.add(newEntry);
        hm.put(value, minHeap.size() - 1);
        decreaseKey(value, key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void decreaseKey(V value, Key newKey) {
        if (!hm.containsKey(value)) {
            throw new NoSuchElementException();
        }
        if (newKey == null || newKey.compareTo(minHeap.get(hm.get(value)).key) > 0) {
            throw new IllegalArgumentException();
        }
        BinaryMinHeap.Entry<Key, V> newEntry = new BinaryMinHeap.Entry<Key, V>(newKey, value);
        minHeap.set(hm.get(value), newEntry);
        int current = hm.get(value);
        if (current == 0) {
            minHeap.set(current, newEntry);
        }
        while (current > 0) {
            int parent = 0;
            if (current % 2 == 0) {
                parent = (current - 1) / 2;
            }
            else {
                parent = current /2;
            }
            if (minHeap.get(current).key.compareTo(minHeap.get(parent).key) > 0) {
                break;
            }
            else {
                BinaryMinHeap.Entry<Key, V> temp = new BinaryMinHeap.Entry<Key, V>(
                        minHeap.get(parent).key, minHeap.get(parent).value);
                hm.put(minHeap.get(current).value, parent);
                hm.put(minHeap.get(parent).value, current);
                minHeap.set(parent, minHeap.get(current));
                minHeap.set(current, temp);
                current = parent;
            }
        }
        // progress the indices by 1
        // while current > 0
        // calculate the parent
        // if statement to see if parent is less than equal to child
        // if so, break
    }
/*
 *while (hm.get(value) > 0) {
            int ind = hm.get(value);
            if (ind % 2 == 0) {
                if (minHeap.get(hm.get(value) / 2).key.compareTo(minHeap.get(hm.get(value)).key) > 0) {
                    break;
                }
                BinaryMinHeap.Entry<Key, V> temp = new BinaryMinHeap.Entry<Key, V>(minHeap.get(ind /
                        2).key,
                        minHeap.get(ind / 2).value);
                hm.put(minHeap.get(ind - 1).value, ((ind -1 ) / 2));
                hm.put(minHeap.get((ind -1 )/ 2).value, (ind -1 ));
                minHeap.set((ind -1 ) / 2, minHeap.get((ind -1 )));
                minHeap.set((ind -1 ), temp);
            }
            else {
                if (minHeap.get(hm.get(value) / 2).key.compareTo(minHeap.get(hm.get(value)).key) > 0) {
                    break;
                }
                BinaryMinHeap.Entry<Key, V> temp = new BinaryMinHeap.Entry<Key, V>(minHeap.get(ind /
                        2).key,
                        minHeap.get(ind / 2).value);
                hm.put(minHeap.get(ind).value, (ind / 2));
                hm.put(minHeap.get(ind / 2).value, ind);
                minHeap.set(ind / 2, minHeap.get(ind));
                minHeap.set(ind, temp);
            }
        }
 */
    /**
     * {@inheritDoc}
     */
    @Override
    public Entry<Key, V> peek() {
        if (minHeap.isEmpty()) {
            throw new NoSuchElementException();
        }
        return minHeap.get(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Entry<Key, V> extractMin() {
        if (minHeap.isEmpty()) {
            throw new NoSuchElementException();
        }
        Entry<Key, V> min = new BinaryMinHeap.Entry<Key, V>(this.peek().key, this.peek().value);
        if (minHeap.size() > 1) {
            minHeap.set(0, minHeap.remove(minHeap.size() - 1));
            hm.put(minHeap.get(0).value, 0);
            hm.remove(min.value);
            minHeapify(minHeap, 0);
        } else {
            minHeap.clear();
        }
        return min;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<V> values() {
        return hm.keySet();
    }

}