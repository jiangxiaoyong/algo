import java.util.Arrays;

/**
 * Created by xiaoyongjiang on 6/19/17.
 */
public class MyHashMap<K, V> {
    public static class Node<K,V> {
        K key;
        V value;
        Node<K, V> next;
        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }

    public static final int DEFAULT_CAPACITY = 16;
    public static final Float DEFAULT_LOAD_FACTOR = 0.75f;

    private float loadFactor;
    private int size;
    private Node<K, V>[] array;

    public MyHashMap(int size, float loadFactor) {
        this.size = size;
        this.loadFactor = loadFactor;
    }

    public MyHashMap() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public synchronized int size() {
        return size;
    }

    public synchronized boolean isEmpty() {
        return size == 0;
    }

    public synchronized void clear() {
        Arrays.fill(array, null);
        size = 0;
    }

    private int hash(K key) {
        if (key == null) {
            return 0;
        }
        return key.hashCode() & 0x7FFFFFFF;
    }

    private int getIndex(K key) {
        return hash(key) % array.length;
    }

    private boolean equalsValue(V v1, V v2) {
        if (v1 == null && v2 == null) {
            return true;
        }

        if (v1 == null || v2 == null) {
            return false;
        }

        return v1.equals(v2);
    }

    private boolean equalsKey(K k1, K k2) {
        if (k1 == null && k2 == null) {
            return true;
        }

        if (k1 == null || k2 == null) {
            return false;
        }

        return k1.equals(k2);
    }

    public synchronized boolean containsValue(V value) {
        if (isEmpty()) {
            return false;
        }

        for (Node<K, V> node : array) {
            while (node != null) {
                if (equalsValue(node.value, value)) {
                    return true;
                }
                node = node.next;
            }
        }
        return false;
    }



    public synchronized boolean containsKey(K key) {
        if (key == null) {
            return false;
        }

        Node<K, V> node = array[getIndex(key)];
        while (node != null) {
            if (equalsKey(node.key, key)) {
                return true;
            }
            node = node.next;
        }
        return false;
    }

    public synchronized V get(K key) {
        if (key == null) {
            return null;
        }

        Node<K, V> node = array[getIndex(key)];
        while (node != null) {
            if (equalsKey(node.key, key)) {
                return node.value;
            }
            node = node.next;
        }
        return null;
    }

    public synchronized V put(K key, V value) {
        int index = getIndex(key);
        Node<K, V> node = array[index];
        if (node != null) {  // same hashed key already stored in map
            while (node.next != null) {
                if (equalsKey(node.key, key)) { //update existing key, value
                    V result = node.value;
                    node.value = value;
                    return result;
                }
                node = node.next;
            }
            node.next = new Node(key, value); //append new node to end of linked list
        } else {
            Node<K, V> newNode = new Node(key, value); // add new node to empty slot
            array[index] = newNode;
            size++;
        }

        if (needResize()) {
            reHashing();
        }

        return null;
    }

    private boolean needResize() {
        return (size + 0.0f) / array.length >= loadFactor;
    }

    private void reHashing() {}

    public synchronized V remove(K key) {
        Node<K, V> node = array[getIndex(key)];
        Node<K, V> prev = null;

        while (node != null) {
            if (equalsKey(node.key, key)) {
                if (prev == null) {
                    array[getIndex(key)] = node.next;
                    size--;
                    return node.value;
                } else {
                    prev.next = node.next;
                    node.next = null;
                    size--;
                    return node.value;
                }
            }
            prev = node;
            node = node.next;
        }
        return null;
    }
}
