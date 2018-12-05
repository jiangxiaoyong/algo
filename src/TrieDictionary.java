import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrieDictionary {
    class TrieNode {
        Map<Character, TrieNode> children;
        boolean isEnd;
        public TrieNode() {
            children = new HashMap<>();
            this.isEnd = false;
        }
    }
    class Trie {
        TrieNode root;
        Trie() {
            this.root = new TrieNode();
        }

        void insert(String input) {
            TrieNode cur = root;
            for (char ch : input.toCharArray()) {
                if (!cur.children.containsKey(ch)) {
                    cur.children.put(ch, new TrieNode());
                }
                cur = cur.children.get(ch);
            }
            cur.isEnd = true;
        }
    }

    public List<String> solution(char[][] array, String[] words) {
        Trie trie = new Trie();
        for (String str : words) {
            trie.insert(str);
        }

        List<String> result = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                DFS(trie.root, i, j, array, sb, result);
            }
        }
        return result;
    }

    public void DFS(TrieNode trieNode, int row, int col, char[][] array, StringBuilder sb, List<String> result) {
        if (row >= array.length || row < 0 || col >= array[0].length || col < 0 || array[row][col] == '#') {
            return;
        }

        if (trieNode.isEnd) {
            result.add(sb.toString());
            trieNode.isEnd = false;
        }

        char ch = array[row][col];
        TrieNode node = trieNode.children.get(ch);
        if (node == null) {
            return;
        }

        sb.append(array[row][col]);
        array[row][col] = '#';

        DFS(node, row + 1, col, array, sb, result);
        DFS(node, row - 1, col, array, sb, result);
        DFS(node, row, col + 1, array, sb, result);
        DFS(node, row, col - 1, array, sb, result);

        array[row][col] = ch;
        sb.deleteCharAt(sb.length() - 1);
    }

    public static void main(String[] args) {
        TrieDictionary main = new TrieDictionary();
        char[][] array = new char[][] {
                {'a', 'p', 'p', 'd'},
                {'p', 'p', 'l', 'g'},
                {'o', 'g', 'e', 'l'},
                {'d', 'k', 'l', 'm'}
        };
        String[] words = {"at", "app", "apple", "cat", "cap", "cathy", "dog"};
        System.out.println( main.solution(array, words));

    }
}
