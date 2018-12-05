import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaoyongjiang on 6/21/17.
 */
public class WordDictionary {
    public class Node {
        Map<Character, Node> map;
        boolean isEnd;
        public Node() {
            this.map = new HashMap<>();
            this.isEnd = false;
        }
    }
    Node root;
    /** Initialize your data structure here. */
    public WordDictionary() {
        root = new Node();
    }

    /** Adds a word into the data structure. */
    public void addWord(String word) {
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

    /** Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter. */
    public boolean search(String word) {
        Node cur = root;
        char[] array = word.toCharArray();
        boolean[] find = new boolean[1];
        DFS(array, 0, cur, find);
        return find[0];
    }

    public void DFS(char[] array, int index, Node cur, boolean[] find) {
        if (index == array.length) {
            if (cur.isEnd) {
                find[0] = true;
            }
            return;
        }

        if (array[index] == '.') {
            for (Map.Entry<Character, Node> entry : cur.map.entrySet()) {
                DFS(array, index + 1, entry.getValue(), find);
            }
        } else {
            if (cur.map.containsKey(array[index])) {
                DFS(array, index + 1, cur.map.get(array[index]), find);
            }
        }
    }

    public List<String> findAllMatchWildCard(String target) {
        List<String> result = new ArrayList<>();
        char[] array = target.toCharArray();
        StringBuilder sb = new StringBuilder();
        Node cur = root;
        findAllMatchWildCardDFS(array, cur, result, 0, sb);
        return  result;
    }

    public void findAllMatchWildCardDFS(char[] array, Node cur, List<String> result, int index, StringBuilder sb) {
        if (index == array.length) {
            result.add(new String(sb));
            return;
        }

        if (array[index] == '.') {
            for (Map.Entry<Character, Node> entry : cur.map.entrySet()) {
                sb.append(entry.getKey());
                findAllMatchWildCardDFS(array, entry.getValue(), result, index + 1, sb);
                sb.deleteCharAt(sb.length() -1);
            }
        } else {
            if (cur.map.containsKey(array[index])) {
                sb.append(array[index]);
                findAllMatchWildCardDFS(array, cur.map.get(array[index]), result, index + 1, sb);
                sb.deleteCharAt(sb.length() - 1);
            }
        }

    }

    public List<String> findAllWithPrefix(String prefix) {
        StringBuilder sb = new StringBuilder();
        List<String> result = new ArrayList<>();
        char[] array = prefix.toCharArray();
        Node cur = root;
        for (char ch : array) {
            if (!cur.map.containsKey(ch)) {
                return result;
            }
            sb.append(ch);
            cur = cur.map.get(ch);
        }
        findAllWithPrefixDFS(cur, sb, result);
        return result;
    }

    public void findAllWithPrefixDFS(Node cur, StringBuilder sb, List<String> result) {
        if (cur.isEnd) {
            result.add(new String(sb));
        }

        for (Map.Entry<Character, Node> entry : cur.map.entrySet()) {
            sb.append(entry.getKey());
            findAllWithPrefixDFS(entry.getValue(), sb, result);
            sb.deleteCharAt(sb.length() - 1);
        }
    }


}
