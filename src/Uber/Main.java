package Uber;


import Util.Interval;
import Util.ListNode;
import Util.TreeNode;

import java.util.*;


public class Main {


    /*

             [
              ["aa","b"],
              ["a","a","b"]
             ]
                1 1 0
                0 1 1
                0 0 0
                1 1 1
                0 1 0

                  3
               /    \
              1      4
                    /
                   2

         6 3 4 5 2
         f       s

         first 3


        [2,7,9,3,1]

         rob:    12
         notrob: 10

         m[i] represent max rob money at house index i

         m[0] = nums[0]
         m[1] = Math.max(nums[0], nums[1])

         induction rule:
         m[i] = Math.max(m[i] + m[i - 2], m[i - 1])

        1 3 5
          i
        3 4
        j

                1,2,3,4,5,
                1,2,3,4,5,
                1,2,3,4,5,
                1,2,3,4,5,
                1,2,3,4,5,

     */

    public int longestIncreasingPath(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return 0;
        }
        int max = 0;
        int[][] m = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                int path = DFS(matrix, i, j, m);
                max = Math.max(max, path);
            }
        }
        return max;
    }

    public int DFS(int[][] matrix, int i, int j, int[][] m) {
        if (m[i][j] != 0) {
            return m[i][j];
        }

        int max = 1;
        if(i > 0 && matrix[i - 1][j] > matrix[i][j]){
            int len1 = 1 + DFS(matrix,  i - 1, j, m);
            max = Math.max(max, len1);
        }
        if(i <  matrix.length - 1 && matrix[i + 1][j] > matrix[i][j]){
            int len2 = 1 + DFS(matrix,  i + 1, j, m);
            max = Math.max(max, len2);
        }
        if(j > 0 && matrix[i][j - 1] > matrix[i][j]){
            int len3 = 1 + DFS(matrix, i, j - 1, m);
            max = Math.max(max, len3);
        }
        if(j < matrix[0].length - 1 && matrix[i][j + 1] > matrix[i][j]){
            int len4 = 1 + DFS(matrix,  i, j + 1, m);
            max = Math.max(max, len4);
        }

        m[i][j] = max;
        return max;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int[][] matrix = new int[][]{
                {9,9,4},
                {6,6,8},
                {2,1,1},
        };
        int res = main.longestIncreasingPath(matrix);
        System.out.print(res);
    }
}


/*
        Data infrastructure
 */

/*
    Q1 top k most frequent elements

    public int[] kMostFrequentElements(int[] nums, int k) {
        PriorityQueue<Map.Entry<Integer, Integer>> minHeap = new PriorityQueue<>(k, new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                if (o1.getValue().equals(o2.getValue())) return o2.getKey().compareTo(o1.getKey());
                return o1.getValue()> o2.getValue() ? 1 : -1;
            }
        });

        Map<Integer, Integer> map = new HashMap<>();
        for (int i: nums) {
            map.put(i, map.getOrDefault(i, 0) + 1);
        }

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (minHeap.size() < k) {
                minHeap.offer(entry);
            } else if (entry.getValue() > minHeap.peek().getValue()) {
                minHeap.poll();
                minHeap.offer(entry);
            }
        }

        int[] res = new int[k];
        int pos = k -1;
        while (!minHeap.isEmpty()) {
            res[pos--] = minHeap.poll().getKey();
        }
        return res;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int[] res = main.kMostFrequentElements(new int[]{1,1,1,2,2,2,4,4,4,3}, 3);
        for (int i : res) {
            System.out.print(i);
        }
    }
 */

/*
        Q2 find the minute with max number of active drivers

                  12----------15
                11-----13
             10--------------------------20
                                  16-----------25

    class Pair {
        boolean isOnline;
        int time;
        Pair(int time, boolean isOnline) {
            this.time = time;
            this.isOnline = isOnline;
        }
    }

    public List<Integer> minuteOfMaxActiveDriver(Interval[] intervals) {
        //Step 1: construct pair
        List<Pair> pairs = new ArrayList<>();
        for (Interval itv : intervals) {
            Pair start = new Pair(itv.start, true);
            Pair end = new Pair(itv.end, false);
            pairs.add(start);
            pairs.add(end);
        }

        //Step 2: sort list of pair
        Collections.sort(pairs, new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                if (o1.time == o2.time) return 0;
                return o1.time > o2.time ? 1 : -1;
            }
        });

        //Step 3: get max number of active user
        List<Integer> res = new ArrayList<>();
        int count = 0;
        int max = Integer.MIN_VALUE;
        for (Pair pair : pairs) {
            if (pair.isOnline) {
                count++;
            } else {
                if (count >= max) {
                    max = count;
                    res.add(pair.time); //record the end time
                }
                count--;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        Main main = new Main();
//        Interval[] intervals = new Interval[3];
//        intervals[0] = new Interval(100, 110);
//        intervals[1] = new Interval(10, 105);
//        intervals[2] = new Interval(40, 90);

        Interval[] intervals = new Interval[4];
        intervals[0] = new Interval(12,15);
        intervals[1] = new Interval(16,25);
        intervals[2] = new Interval(10, 20);
        intervals[3] = new Interval(11,13);
        List<Integer> res = main.minuteOfMaxActiveDriver(intervals);
        System.out.print(res.toString());
    }
 */

/*
    Q3 reorganize string, no adjacent dup

    public String reorganizeString(String S) {
       Map<Character, Integer> map = new HashMap<>();
       int max = 0;
       for (char ch : S.toCharArray()) {
           map.put(ch, map.getOrDefault(ch, 0) + 1);
           max = Math.max(max, map.get(ch));
       }

       if (max > (S.length() + 1) / 2) return ""; // e.g.   aaac, aabbbb

       PriorityQueue<Map.Entry<Character, Integer>> maxHeap = new PriorityQueue<>(map.size(), new Comparator<Map.Entry<Character, Integer>>() {
           @Override
           public int compare(Map.Entry<Character, Integer> o1, Map.Entry<Character, Integer> o2) {
               if (o1.getValue().equals(o2.getValue())) return 0;
               return o1.getValue() > o2.getValue() ? -1 : 1;
           }
       });

       for (Map.Entry<Character, Integer> entry : map.entrySet()) {
           maxHeap.offer(entry);
       }

       StringBuilder sb = new StringBuilder();
       while (maxHeap.size() >= 2) {
           Map.Entry<Character, Integer> en1 = maxHeap.poll();
           Map.Entry<Character, Integer> en2 = maxHeap.poll();
           sb.append(en1.getKey());
           sb.append(en2.getKey());
           int count1 = en1.getValue() - 1;
           int count2 = en2.getValue() - 1;
           if (count1 > 0) {
               // order here guarantee last letter of sb is different than en1.getKey(), e.g. aabb ->  abab
               maxHeap.offer(new AbstractMap.SimpleEntry<>(en1.getKey(), count1));
           }
           if (count2 > 0) {
               maxHeap.offer(new AbstractMap.SimpleEntry<>(en2.getKey(), count2));
           }
//           This code turns out to be superfluous, but explains what is happening
//            if (ans.length() == 0 || mc1.letter != ans.charAt(ans.length() - 1)) {
//                ans.append(mc1.letter);
//                ans.append(mc2.letter);
//            } else {
//                ans.append(mc2.letter);
//                ans.append(mc1.letter);
//            }
       }
       if (maxHeap.size() > 0) sb.append(maxHeap.poll().getKey());
       return sb.toString();
    }

    public static void main(String[] args) {
        Main main = new Main();
        String res = main.reorganizeString("aabbbb");
        System.out.print(res);
    }
 */

/*
    Q4 sliding window on 2d matrix
            1 2 3 4
            1 2 3 4
            1 2 3 4
            1 2 3 4
    //currently only works for k is odd number

    public double[][] reorganizeString(int[][] matrix, int k) {
        //construct prefix sum for vertical strip size k
        int[][] prefixSum = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                int startRow = i - k / 2 < 0 ? 0 : i - k /2; // truncate part exceed matrix size
                int endRow = i + k /2 > matrix.length -1 ? matrix.length -1 : i + k / 2; // truncate part exceed matrix size
                int sum = 0;
                for (int x = startRow; x <= endRow; x++) {
                    sum += matrix[x][j];
                }
                prefixSum[i][j] = sum;
            }
        }

        // sliding window on prefix sum from left to right size k
        double[][] ans = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < prefixSum.length; i++) {
            for (int j = 0; j < prefixSum[0].length; j++) {
                int startCol = j - k / 2 < 0 ? 0 : j - k /2;
                int endCol =  j + k /2 > prefixSum[0].length - 1 ? prefixSum[0].length - 1 : j + k / 2;
                int startRow = i - k / 2 < 0 ? 0 : i - k /2;
                int endRow = i + k /2 > matrix.length -1 ? matrix.length -1 : i + k / 2;
                int sum = 0;
                int len = endCol - startCol + 1;
                int height = endRow - startRow + 1;
                for (int x = startCol; x <= endCol; x++) {
                    sum += prefixSum[i][x];
                }
                ans[i][j] = sum / (double)(len * height);
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int[][] matrix = new int[][]{
                {1,2,3,4},
                {1,2,3,4},
                {1,2,3,4},
                {1,2,3,4}
        };
        double[][] res = main.reorganizeString(matrix, 3);
        System.out.print(res);
    }
 */

/*
 *   General
 */

/*
     LC_381 getRandom O(1) - Duplicates allowed

          0 1 2 3
          1 1 2 2
          {1:0-1}
          {2:2-3}

    static class RandomizedCollection {

        Map<Integer, Set<Integer>> map;
        List<Integer> list;
        Random rand;
        public RandomizedCollection() {
            map = new HashMap<>();
            list= new ArrayList<>();
            rand = new Random();
        }

        public boolean insert(int val) {
            boolean contain = map.containsKey(val);
            map.putIfAbsent(val, new HashSet<>());
            map.get(val).add(list.size());
            list.add(val); //last step is adding the element
            return !contain;
        }

        public boolean remove(int val) {
            if (!map.containsKey(val)) return false;
            int curPos = map.get(val).iterator().next();

            //update
            int lastOne = list.get(list.size() - 1);
            list.set(curPos, lastOne);

            //remvove
            map.get(val).remove(curPos);
            map.get(lastOne).add(curPos); //order here, important
            map.get(lastOne).remove(list.size() - 1);

            //update
            list.remove(list.size() - 1);
            if (map.get(val).size() == 0) map.remove(val);
            return true;
        }

        public int getRandom() {
            return list.get(rand.nextInt(list.size()));
        }
    }

    public static void main(String[] args) {
        Main main = new Main();

        RandomizedCollection rand = new RandomizedCollection();
        rand.insert(0);
        rand.remove(0);
        rand.insert(-1);
        rand.remove(0);
        int getRandom = rand.getRandom();
    }

 */

/*
    get square sorted array

    public int[] squareSortedArray(int[] nums) {
        int target = 0;
        int left = getClosest(nums, target);
        int right = left + 1;
        int[] ans = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            // 1. right point out of bound
            // 2. right and left not out of bound, and left is closer
            if (right >= nums.length || left >= 0 && Math.abs(nums[left] - target) < Math.abs(nums[right] - target)) {
                ans[i] = nums[left] * nums[left];
                left--;
            } else {
                ans[i] = nums[right] * nums[right];
                right++;
            }
        }
        return ans;
    }

    public int getClosest(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left < right - 1) {
            int mid = left + (right - left) / 2;
            if (target < nums[mid]) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return Math.abs(target - nums[left]) < Math.abs(target - nums[right]) ? left : right;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int[] res = main.squareSortedArray(new int[]{1,2,5});
        for (int i : res) {
            System.out.println(i);
        }
    }
 */

/*
    max sum of nodes that no two adjacent nodes
    LC 337  house robber III

    public int maxSumNoAdjacentNode(TreeNode root) {
        int[] res = helpFn(root);
        return Math.max(res[0], res[1]);
    }

    public int[] helpFn(TreeNode root) {
        if (root == null) return new int[2];
        int[] left = helpFn(root.left);
        int[] right = helpFn(root.right);

        int[] res = new int[2];
        res[0] = root.val + left[1] + right[1]; //include node
        res[1] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]); // not include node
        return res;
    }

    public static void main(String[] args) {
        Main main = new Main();
        TreeNode node = new TreeNode(1);
        node.left = new TreeNode(2);
        node.right = new TreeNode(3);
        node.left.left = new TreeNode(1);
        node.right.left = new TreeNode(4);
        node.right.right = new TreeNode(5);
        System.out.print(main.maxSumNoAdjacentNode(node));
    }
 */

/*
    sudoku valid

    public boolean isValidSudoku(String[][] board) {
        Set<String> set = new HashSet<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] != ".") {
                    String row = board[i][j] + "in row" + i;
                    String col = board[i][j] + "in col" + j;
                    String subbox= board[i][j] + "in subbox" + i / 3 + j / 3;
                    if (!set.add(row) || !set.add(col) || !set.add(subbox)) return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Main main = new Main();
        String[][] matrix = new String[][] {
                {"5","3",".",".","7",".",".",".","."},
                {"6",".",".","1","9","5",".",".","."},
                {".","9","8",".",".",".",".","6","."},
                {"8",".",".",".","6",".",".",".","3"},
                {"4",".",".","8",".","3",".",".","1"},
                {"7",".",".",".","2",".",".",".","6"},
                {".","6",".",".",".",".","2","8","."},
                {".",".",".","4","1","9",".",".","5"},
                {".",".",".",".","8",".",".","7","9"}
        };

        System.out.print(main.isValidSudoku(matrix));
    }
 */

/*
    soduku solver

    public void solveSudoku(char[][] board) {
        if (board == null || board.length == 0) return;
        solver(board);
    }

    public boolean solver(char[][] borad) {
        for (int i = 0; i < borad.length; i++) {
            for (int j = 0; j < borad[0].length; j++) {
                if (borad[i][j] == '.') {
                    for (char ch = '1'; ch <= '9'; ch++) {
                        if (isValid(borad, i, j, ch)) {
                            borad[i][j] = ch;
                            if (solver(borad)) {
                                return true;
                            } else {
                                borad[i][j] = '.';
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isValid(char[][] board, int row, int col, char ch) {
        for (int i = 0; i < 9; i++) {
            if (board[i][col] != '.' && board[i][col] == ch) return false;
            if (board[row][i] != '.' &&  board[row][i] == ch) return false;
            if (board[3 * (row / 3) + i / 3][3 * (col / 3) + i % 3] != '.' &&
            board[3 * (row / 3) + i / 3][3 * (col / 3) + i % 3] == ch) return false;
        }
        return true;
    }
 */

/*
    find all palindrome substring

              [
              ["aa","b"],
              ["a","a","b"]
             ]
                     a a b
                     /||\
               a|ab        aa|b
              /\\\
           a|a|b

     public List<List<String>> partition(String s) {
        List<List<String>> ans = new LinkedList<>();
        if (s == null) return ans;
        DFS(s, 0, new ArrayList<>(), ans);
        return ans;
    }

    public void DFS(String s, int index, List<String> cur, List<List<String>> res) {
        if (index == s.length()) {
            res.add(new ArrayList<>(cur));
            return;
        }

        for (int i = index + 1; i <= s.length(); i++) {
            String sub = s.substring(index, i);
            if (isPalindrome(sub)) {
                cur.add(sub);
                DFS(s, i,  cur, res);
                cur.remove(cur.size() - 1);
            }
        }
    }

    public boolean isPalindrome(String str) {
        int i = 0;
        int j = str.length() - 1;
        while (i < j) {
            if (str.charAt(i) != str.charAt(j)) return false;
            i++;
            j--;
        }
        return true;
    }

    public static void main(String[] args) {
        Main main = new Main();
        System.out.print(main.partition("aab"));
    }
 */

/*
    LC 200 number of islands

     public int numIslands(char[][] grid) {
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    DFS(grid, i, j);
                    count++;
                }
            }
        }
        return count;
    }

    public void DFS(char[][] grid, int i, int j) {
        if (i < 0 || i >= grid.length ||
                j < 0 || j >= grid[0].length ||
                grid[i][j] == '#' ||
                grid[i][j] == '0') {
            return;
        }

        grid[i][j] = '#';
        DFS(grid, i + 1, j);
        DFS(grid, i - 1, j);
        DFS(grid, i, j + 1);
        DFS(grid, i, j - 1);
    }
 */

/*
    LC 694  number of distinct islands

    public int numDistinctIslands(int[][] grid) {
        Set<String> set = new HashSet<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    StringBuilder islandHashCode = new StringBuilder();
                    DFS(grid, i, j, "o", islandHashCode);
                    set.add(islandHashCode.toString());
                }
            }
        }
        return set.size();
    }

    public void DFS(int[][] grid, int i, int j, String dir, StringBuilder hashCode) {
        if (i < 0 || i >= grid.length ||
        j < 0 || j >= grid[0].length || grid[i][j] == 0) return;

        hashCode.append(dir);
        grid[i][j] = 0;
        DFS(grid, i - 1, j, "u", hashCode);
        DFS(grid, i + 1, j, "d", hashCode);
        DFS(grid, i, j - 1, "l", hashCode);
        DFS(grid, i, j + 1, "r", hashCode);
        hashCode.append("b");

        // append "b" to deal with same letter string, below both are "o r d r"
                1 1 0
                0 1 1
                0 0 0
                1 1 1
                0 1 0
    }

    public static void main(String[] args) {
        Main main = new Main();
        int[][] matrix = new int[][] {
                {1,1,0},
                {0,1,1},
                {0,0,0},
                {1,1,1},
                {0,1,0},
        };
        System.out.print(main.numDistinctIslands(matrix));
    }
 */

/*
    nested interator

    public interface NestedInteger {
        public boolean isInteger();
        public Integer getInteger();
        public List<NestedInteger> getList();
    }

    public class NestedIterator implements Iterator<Integer> {

        Deque<NestedInteger> stack;

        public NestedIterator(List<NestedInteger> nestedList) {
            stack = new LinkedList<NestedInteger>();
            for (int i = nestedList.size() - 1; i>= 0; i--) { //top of stack should be first of nested integer
                stack.offerLast(nestedList.get(i));
            }
        }

        @Override
        public Integer next() {
            if (hasNext()) {
                return stack.pollLast().getInteger();
            }
            return null;
        }

        @Override
        public boolean hasNext() {
            while(!stack.isEmpty() && !stack.peekLast().isInteger()) {
                List<NestedInteger> ni = stack.pollLast().getList();
                for (int i = ni.size() - 1; i >= 0; i--) {
                    stack.offerLast(ni.get(i));
                }
            }
            return !stack.isEmpty();
        }
    }
 */

/*
    house rob

         m[i] represent max rob money at house index i

         m[0] = nums[0]
         m[1] = Math.max(nums[0], nums[1])

         induction rule:
         m[i] = Math.max(m[i] + m[i - 2], m[i - 1])

    public int rob(int[] nums) {
        if (nums.length ==0) return 0;
        if (nums.length == 1) return nums[0];

        int[] m = new int[nums.length];
        m[0] = nums[0];
        m[1] = Math.max(nums[0], nums[1]);

        int max = 0;
        for (int i = 2; i < nums.length; i++) {
            m[i] = Math.max(nums[i] + m[i - 2], m[i - 1]);
            max = Math.max(max, m[i]);
        }
        return max;
    }
    public static void main(String[] args) {
        Main main = new Main();
        System.out.print(main.rob(new int[]{2,7,9,3,1}));
    }
 */

/*
    kth smallest element in BST

    public int kthSmallest(TreeNode root, int k) {
        List<Integer> list = new ArrayList<>();
        DFS(root, list, k);
        return list.get(k - 1);
    }

    public void DFS(TreeNode root, List<Integer> list, int k) {
        if (root == null) return;
        DFS(root.left, list, k);
        list.add(root.val);
        if (list.size() == k) return;
        DFS(root.right, list, k);
    }

    public static void main(String[] args) {
        Main main = new Main();
        TreeNode node = new TreeNode(3);
        node.left = new TreeNode(1);
        node.right = new TreeNode(4);
        node.left.right = new TreeNode(2);
        System.out.print(main.kthSmallest(node, 1));
    }
 */