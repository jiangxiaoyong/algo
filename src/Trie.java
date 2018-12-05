import sun.util.resources.cldr.zh.CalendarData_zh_Hans_HK;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiaoyongjiang on 6/21/17.
 */
public class Trie {
    public class Node {
        HashMap<Character, Node> map;
        boolean isEnd;
        public Node() {
            this.isEnd = false;
            this.map = new HashMap<>();
        }
    }
    Node root;
    /** Initialize your data structure here. */
    public Trie() {
        root = new Node();
    }

    /** Inserts a word into the trie. */
    public void insert(String word) {
        Node cur = root;
        char[] array = word.toCharArray();
        for (char ch : array) {
            if (!cur.map.containsKey(ch)) {
                cur.map.put(ch, new Node());
            }
            cur = cur.map.get(ch);
        }
        cur.isEnd = true;
    }

    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        Node cur = root;
        char[] array = word.toCharArray();
        for (char ch : array) {
            if (!cur.map.containsKey(ch)) {
                return false;
            }
            cur = cur.map.get(ch);
        }
        return cur.isEnd;
    }

    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        Node cur = root;
        char[] array = prefix.toCharArray();
        for (char ch : array) {
            if (!cur.map.containsKey(ch)) {
                return false;
            }
            cur = cur.map.get(ch);
        }
        return true;
    }

    public boolean delete(String word) {
        Node cur = root;
        char[] array = word.toCharArray();
        for (char ch : array) {
            if (!cur.map.containsKey(ch)) {
                return false;
            }
            cur = cur.map.get(ch);
        }
        cur.isEnd = false;
        return true;
    }
}
