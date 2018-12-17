package Wednesday.Sunday.DynamicProgramming;

import java.util.*;

public class Main {




















/*

    xxx
    xxx


    0: left
    1: up
    2: down
 */

    static public int eatingPizza(int[] array) {
        int n = array.length;
        int[][] dp = new int[n][n];

        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {
                if (i == j) {
                    dp[i][j] = array[i];
                } else if (i + 1 == j) {
                    dp[i][j] = Math.max(array[i], array[j]);
                } else {
                    int pickLeft = 0;
                    if (array[i + 1] < array[j]) {
                        pickLeft = array[i] + dp[i + 1][j - 1];
                    } else {
                        pickLeft = array[i] + dp[i + 2][j];
                    }

                    int pickRight = 0;
                    if (array[i] < array[j - 1]) {
                        pickRight = array[j] + dp[i][j - 2];
                    } else {
                        pickRight = array[j] + dp[i + 1][j - 1];
                    }
                    dp[i][j] = Math.max(pickLeft, pickRight);
                }
            }
        }
        return dp[0][n - 1];
    }

    public static void main(String[] args) {
        int res = Main.eatingPizza(new int[] {5,3,1,4});
        System.out.println(res);
    }

}


/**
 *  Typical dp problem
 */
/*
    Q1  distinct sub-sequence

    s = rabbbit
    t = rabbit
    return 3

    rabbbit
    ^^^ ^^^
    rabbbit
    ^^ ^^^^
    rabbbit
    ^^^^ ^^

    dp[i][j] means number of distinct subsequence from s[0 - i-1] matching to t[0 - j-1]

    dp[i][0] = 1  i>=0  (empty t)
    dp[0][j] = 0 j >= 1  empty s, non empty t

    static public int distinctSubsequences(String s, String t) {
        int[][] m = new int[s.length() + 1][t.length() + 1]; //need to consider empty string, so + 1 length
        m[0][0] = 1; //empty s matching empty t

        for (int i = 1; i <= s.length(); i++) {
            m[i][0] = 1;
            for (int j = 1; j <= t.length(); j++) {
                m[i][j] = m[i - 1][j];
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    m[i][j] += m[i - 1][j - 1];
                }
            }
        }
        return m[s.length()][t.length()];
    }

    public static void main(String[] args) {
        int res = Main.distinctSubsequences("rabbbit", "rabbit");
        System.out.println(res);
    }
 */

/**
 *  Reconstructing the path
 */

/*
    Q1  construct the any of path fo longest ascending sub sequence

                [2,1,3,3,5]
                 0 1 2 3 4
longest[i] =     1 1 2 2 3
prev[i] =       -1-1 1 1 3

    static public List<Integer> longestIncreasingSubsequence(int[] array) {
        int[] m = new int[array.length];
        int[] prev = new int[array.length];
        Arrays.fill(prev, -1);
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < i; j++) {
                if (array[j] < array[i]) {
                    if (m[j] + 1 > m[i]) {
                        prev[i] = j;
                        m[i] = m[j];
                    }
                }
            }
            m[i]++;
        }

        //find the index i with largest dp[i], this index is the longest ascending sub sub seq
        int index = 0;
        int max = 0;
        for (int i = 0; i < m.length; i++) {
            if (m[i] > max) {
                max = m[i];
                index = i;
            }
        }

        //build path from the largest index i back ward
        // prev[4] = 3
        // prev[3] = 1
        // prev[1] = -1
        List<Integer> res = new ArrayList<>();
        int jump = index;
        while (jump != -1) {
            res.add(array[jump]);
            jump = prev[jump];
        }
        return res;
    }

    public static void main(String[] args) {
        List<Integer> res = Main.longestIncreasingSubsequence(new int[] {2,1,3,3,5});
        System.out.println(res);
    }
 */


/**
 *    class 1d sequence 拆分 组合
 */

/*
    Q1.1 largest subarray sum of circular array

    [1,2,-3,4,5]
     x x __ x x

     case1: largest sub array sum of non- circular array
     case2: must includes array[0] and array[n - 1]
        - smallest sub array su of non-circular array
        - remaining art sum is sum - smallest

    static public int largestSubarraySumCircularArray(int[] array) {
        // largest subarray of non-circular array
        int nonCircular = 0;
        int cur = 0;
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            cur = Math.max(array[i], cur + array[i]);
            nonCircular = Math.max(nonCircular, cur);
            sum += array[i];
        }

        // largest subarray of circular array
        int midBubble = Integer.MAX_VALUE;
        cur = array[0];
        for (int i = 1; i < array.length; i++) {
            cur = Math.min(array[i], cur + array[i]);
            midBubble = Math.min(midBubble, cur);
        }
        int circular = sum - midBubble;
        return Math.max(nonCircular, circular);
    }

    public static void main(String[] args) {
        int res = Main.largestSubarraySumCircularArray(new int[] {2,1,-3,3,5});
        System.out.println(res);
    }
 */

/*
    Q1.3    largest sum of sub array with at most k elements

             0 1  2 3 4
            [1,2,-3,4,5]
prefix    [0,1,3,0, 4,9]
           0 1 2 3  4 5

   => min value of size k sliding window

    static public int largestSubArraySumAtMostKElements(int[] array, int k) {
        int[] prefix = new int[array.length + 1];
        prefix[1] = array[0];
        for (int i = 1; i < array.length; i++) {
            prefix[i + 1] = prefix[i] + array[i];
        }

        int max = 0;
        TreeSet<Integer> set = new TreeSet<>();
        for (int j = 0; j < prefix.length; j++) {
            if (j - k >= 0) {
                int curMin = set.first();
                max = Math.max(max, prefix[j] - curMin);
            }
            set.add(prefix[j]);
            if (set.size() > k) {
                set.remove(prefix[j - k]);
            }
        }
        return max;
    }

    public static void main(String[] args) {
        int res = Main.largestSubArraySumAtMostKElements(new int[] {2,1,-3,3,5}, 2);
        System.out.println(res);
    }
 */

/*
    Q2   paint colour, at most two adjacent pieces can be painted

    [black, white]

    [0,0,1]
    [0,1,0]
    [0,1,1]
    [1,0,0]
    [1,0,1]
    [1,1,0]

    dp[i][b] = dp[i - 2][w] + dp[i - 1][w] => current black, i - 2 must white
                 i - 1 is b     i - 1 is w

    dp[i][w] = dp[i - 2][b] + dp[i - 1][b] => current white, i - 2 must black
                i - 1 is w        i - 1 b

    induction rule can be reduced to:
    dp[i]: how many color can get, if we finish 0 - i, ith element as a certain color
    dp[i] = dp[i - 1] + dp[i - 2]   regardless current color
        - i - 1 if has same color as i
        - i - 1 if not the same color as i

    static public int paintColour(int n) {
        int[][] dp = new int[n][2];
        dp[0][0] = 1; dp[0][1] = 1;
        dp[1][0] = 2; dp[1][1] = 2;
        for (int i = 2; i < n; i++) {
            dp[i][0] = dp[i - 2][1] + dp[i - 1][1];
            dp[i][1] = dp[i - 2][0] + dp[i - 1][0];
        }
        return dp[n - 1][0] + dp[n - 1][1];
    }

    public static void main(String[] args) {
        int res = Main.paintColour(3);
        System.out.println(res);
    }
 */

/*
    Q2.1    up to k different color

    dp[i] = (k - 1) * dp[i - 1] + (k - 1) * dp[i - 2]
    result: k * dp[n - 1]

    static public int paintColourKColor(int n, int k) {
        int[] dp = new int[n];
        dp[0] = 1;
        dp[1] = k;
        for (int i = 2; i < n; i++) {
            dp[i] = (k - 1) * (dp[i - 1] + dp[i - 2]);
        }
        return k * dp[n - 1];
    }

    public static void main(String[] args) {
        int res = Main.paintColourKColor(2, 3);
        System.out.println(res);
    }
 */

/*
    Q2.2    paint colour at most k colour and x adjacent same colour

    dp[i] = (k - 1) * (dp[i - 1] + dp[i - 2] + ... + dp[i -x])

     static public int paintColourKColourXsame(int n, int k, int x) {
        int[] dp = new int[n];
        dp[0] = 1;
        dp[1] = k;
        for (int i = 2; i < n; i++) {
            for (int j = 1; j <= x; j++) {
                if (i - j >= 0) {
                    dp[i] += (k - 1) * dp[i - j];
                }
            }
        }
        return k * dp[n - 1];
    }

    public static void main(String[] args) {
        int res = Main.paintColourKColourXsame(2, 2, 2);
        System.out.println(res);
    }
 */

/*
    Q4 split array into m non-empty sub array, find minimize the largest sum among these m sub array

    [7,2,5,10,8]
    [7,| 2,5,10,8]  7,25
    [7,2,|5,10,8]   9,23
    [7,2,5,|10,8]   max(14,18) = 18
    [7,2,5,10,|8]   24,8


    static public int paintColourKColourXsame(int[] array, int m) {
        int n = array.length;
        int[][] dp = new int[n][m + 1];

        //base case dp[x][1] = sum of sub array [0,x]
        dp[0][1] = array[0];
        for (int i = 1; i < n; i++) {
            dp[i][1] = dp[i - 1][1] + array[i];
        }

        for (int j = 2; j <= m; j++) {
            for (int i = 0; i < n; i++) {
                int localMin = Integer.MAX_VALUE;
                for (int x = 1; x <= i; x++) {
                    localMin = Math.min(localMin, Math.max(dp[x - 1][j - 1], sum(array, x, i)));
                }
                dp[i][j] = localMin;
            }
        }
        return dp[n - 1][m];
    }

    static public int sum(int[] array, int i, int j) {
        int sum = 0;
        for (int x = i; x <= j; x++) {
            sum += array[x];
        }
        return sum;
    }

    public static void main(String[] args) {
        int res = Main.paintColourKColourXsame(new int[] {7,2,5,10,8}, 2);
        System.out.println(res);
    }
 */

/**
 *  class   Array problem
 */

/*
    Q 5.1 longest sub array flipping at most m 0
    by sliding window

    static public int longestSubarrayFlippingAtMostMZero(int[] array, int m) {
        int zeroCount = 0;
        int slow = 0;
        int fast = 0;
        int len = 0;
        while (fast < array.length) {
            if (array[fast] == 0) {
                zeroCount++;
                while (zeroCount > m) {
                    if (array[slow] == 0) zeroCount--;
                    slow++;
                }
            }
            len = Math.max(len, fast - slow + 1);
            fast++;
        }
        return len;
    }

    public static void main(String[] args) {
        int res = Main.longestSubarrayFlippingAtMostMZero(new int[] {0,1,1,0,1,1,1,0,0,1}, 2);
        System.out.println(res);
    }
 */

/*
    Q6 last byte is one or two byte

    build dp from end to begin, right -> left

    static public boolean lastByteIsOneOrTwoByte(int[] array) {
        int n  = array.length;
        boolean[] dp = new boolean[n];
        dp[n - 1] = true;
        dp[n - 2] = array[n - 2] == 0;
        for (int i = n - 3; i >= 0; i--) {
            if (array[i] == 0) {
                dp[i] = dp[i + 1];
            } else if (array[i] == 1) {
                dp[i] = dp[i + 2];
            }
        }
        return dp[0];
    }

    public static void main(String[] args) {
        boolean res = Main.lastByteIsOneOrTwoByte(new int[] {0,1,0,0,1,0});
        System.out.println(res);
    }
 */

/**
 *  class interval 正向 反向
 */

/*
    Q1 array of positive integer, insert + * (), get max evaluate value

    [1, 0.5, 2, 2] return 7
    (1 + 0.5 + 2) * 2 = 7

    static public double maxValuePlusMultiplyParenthesis(double[] array) {
        int n = array.length;
        double [][] dp = new double[n][n];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {
                if (i == j) {
                    dp[i][j] = array[i];
                } else {
                    for (int k = i; k < j; k++) {
                        dp[i][j] = Math.max(dp[i][k] * dp[k + 1][j], dp[i][k] + dp[k + 1][j]);
                    }
                }
            }
        }
        return dp[0][n - 1];
    }

    public static void main(String[] args) {
        double res = Main.maxValuePlusMultiplyParenthesis(new double[] {1,0.5,2,2});
        System.out.println(res);
    }
 */

/**
 *      class Optimum stratage
 */

/*
    Q eating Pizza

    m[i][j] represents largest sum when size i and size j pizza at each of end

    base case:
      m[i][i] = input[i]
      m[i][i+1] = max(input[i], input[i+1])

    induction rule:
      m[i][j] = max{
        case 1 pick left:
            input[i] + m[i+1][j-1]   if input[i+1] < input[j]
            input[i] + m[i+2][j]     if input[i+1] > input[j]
        case 2 pick right:
            input[j] + m[i][j-2]     if input[i] < input[j-1]
            input[j] + m[i+1][j-1]   if input[i] > input[j-1]
      }

        j 0 1 2 3
      i   3 2 5 4
      0 3 3 3 8
      1 2   3 5 7
      2 5     5 5
      3 4       4

    pick left: m[1][3] = 2 + 4 = 6
    pick right: m[1][3] = 4 + 3 = 7

    pick left:  m[0][2] = 3 + 3 = 6
    pick right: m[0][2] = 5 + 3 = 8

    pick left:  m[0][3] = 3 + 5 = 8
    pick right: m[0][3] = 4 + 2 = 6

    static public int eatingPizza(int[] array) {
        int n = array.length;
        int[][] dp = new int[n][n];

        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {
                if (i == j) {
                    dp[i][j] = array[i];
                } else if (i + 1 == j) {
                    dp[i][j] = Math.max(array[i], array[j]);
                } else {
                    int pickLeft = 0;
                    if (array[i + 1] < array[j]) {
                        pickLeft = array[i] + dp[i + 1][j - 1];
                    } else {
                        pickLeft = array[i] + dp[i + 2][j];
                    }

                    int pickRight = 0;
                    if (array[i] < array[j - 1]) {
                        pickRight = array[j] + dp[i][j - 2];
                    } else {
                        pickRight = array[j] + dp[i + 1][j - 1];
                    }
                    dp[i][j] = Math.max(pickLeft, pickRight);
                }
            }
        }
        return dp[0][n - 1];
    }

    public static void main(String[] args) {
        int res = Main.eatingPizza(new int[] {5,3,1,4});
        System.out.println(res);
    }
 */

/**
 *     Class String/Character  Matching
 *     The Matching does not need to be from two sequences
 */

/*
    Q1. Longest palindromic substring,
    e.g. BBABCBCAB, output should be 3 as "BAB" or "BCB"

    induction rule:
        dp[i][j] = s[i] == s[j] && dp[i + 1][j - 1]
    result: the largest (j - i + 1) for (i, j) where dp[i][j] is true

    static public int longestPalindromicSubstring(String s) {
        int n = s.length();
        boolean[][] m = new boolean[n][n];
        int max = 0;
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {
                if (i == j) { //base case dp[i][i] == true, odd number
                    m[i][j] = true;
                    max = Math.max(max, 1);
                } else if (i + 1 == j && s.charAt(i) == s.charAt(j)) { //base case dp[i][i + 1] = s[i] == s[i + 1], even number
                    m[i][j] = true;
                    max = Math.max(j - i + 1, max);
                } else if (s.charAt(i) == s.charAt(j) && m[i + 1][j - 1]) {
                    m[i][j] = true;
                    max = Math.max(j - i + 1, max);
                }
            }
        }
        return max;
    }

    public static void main(String[] args) {
        int res = Main.longestPalindromicSubstring("BBABCBCAB");
        System.out.println(res);
    }
 */

/*
    Q2. Longest palindromic subsequence
    e.g BBABCBCAB, should be 7 as "BABCBAB"

    induction rule: if s[i] == s[j]: 2 + dp[i + 1][j - 1]
                    else max(dp[i + 1][j], dp[i][j - 1])

    static public int longestPalindromicSubsequence(String s) {
        int n = s.length();
        int[][] m = new int[n][n];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {
                if (i == j) { // base case
                    m[i][j] = 1;
                } else if (i + 1 == j) {
                    m[i][j] = s.charAt(i) == s.charAt(j) ? 2 : 1; // base case
                } else if (s.charAt(i) == s.charAt(j)) {
                    m[i][j] = 2 + m[i + 1][j - 1];
                } else {
                    m[i][j] = Math.max(m[i + 1][j], m[i][j - 1]);
                }
            }
        }
        return m[0][n - 1];
    }

    public static void main(String[] args) {
        int res = Main.longestPalindromicSubsequence("BBABCBCAB");
        System.out.println(res);
    }
 */

/*
    Q3. Least number of insertion make palindrome

    "abca" -> "abcba" insert 1
    "abcdc" -> "abcdcba" insert 2

    static public int leastNumberOfInsertionPalindrome(String s) {
        int n = s.length();
        int[][] m = new int[n][n];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    m[i][j] = m[i + 1][j - 1];
                } else {
                    m[i][j] = Math.min(m[i + 1][j], m[i][j - 1]) + 1;
                }
            }
        }
        return m[0][n - 1];
    }

    public static void main(String[] args) {
        int res = Main.leastNumberOfInsertionPalindrome("abcb");
        System.out.println(res);
    }
 */

/*
    Q3 Decode way II
     * can be matched to any of 1-9
    "1*" -> [11], [12], [13].. [1,1],[1,2],[1,3].. return 9 + 9 = 18

    induction rule:
    length 1:
        if s[i] != * && s[i] > 0, dp[i] += dp[i - 1]
        if s[i] == *, dp[i] = 9 * dp[i - 1]

    length 2:
        if s[i - 1, i] == **, dp[i] += 26 * dp[i - 1]
        if s[i -1 , i] == *X
            if x = 0123456, dp[i] += 2 * dp[i - 2]
            if x = 789, dp[i] += dp[i - 2]
        if s[i -1, i] == X*
            if x = 1, dp[i] += 9 * dp[i - 2]
            if x = 2, dp[i] += 6 * dp[i - 2]
        if s[i - 1, i] >= 10 && <= 26, dp[i] += dp[i - 2]

    static public int numDecodings(String s) {
        int n = s.length();
        int[] m = new int[n + 1];
        if (s.charAt(0) == '0') return 0;
        m[0] = 1;
        m[1] = s.charAt(0) == '*' ? 9 : 1;

        for (int i = 2; i <= n; i++) {
            char first = s.charAt(i - 2);
            char second = s.charAt(i - 1);

            // length 1, dp[i - 1]
            if (second == '*') {
                m[i] += 9 * m[i - 1];
            } else if (second > '0') {
                m[i] += m[i - 1];
            }

            //length 2, dp[i - 2]
            if (first == '*') {
                if (second == '*') {
                    m[i] += 26 * m[i - 2];
                } else if (second <= '6') {
                    m[i] += 2 * m[i - 2];
                } else {
                    m[i] += m[i - 2];
                }
            } else if (first == '1' || first == '2') {
                if (first == '1' && second == '*') {
                    m[i] += 9 * m[i - 2];
                } else if (first == '2' && second == '*') {
                    m[i] += 6 * m[i - 2];
                } else if ((first - '0') * 10 + (second - '0') <= 26) {
                    m[i] += m[i - 2];
                }
            }
        }
        return m[n];
    }

    public static void main(String[] args) {
        int res = Main.numDecodings("1*");
        System.out.println(res);
    }
 */


/**
 *  class about Matrix
 */


/*
    Q1

    How many squares in the matches shape

    __.__.
   |     |
    __.__.
   |  |  |
    __.__.

    static class Match {
        boolean hasUp;
        boolean hasLeft;
        int longestFromUp;
        int longestFromLeft;
        public Match(int longestFromUp, int longestFromLeft, boolean hasUp, boolean hasLeft) {
            this.hasUp = hasUp;
            this.hasLeft = hasLeft;
            this.longestFromUp = longestFromUp;
            this.longestFromLeft = longestFromLeft;
        }
    }


    static int countMatchSquares(Match[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int count = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j].longestFromUp = matrix[i][j].hasUp ? matrix[i - 1][j].longestFromUp + 1 : 0;
                matrix[i][j].longestFromLeft = matrix[i][j].hasLeft ? matrix[i][j - 1].longestFromLeft + 1 : 0;
                int maxLen = Math.min(matrix[i][j].longestFromUp, matrix[i][j].longestFromLeft);
                for (int k = 1; k <= maxLen; k++) {
                    if (matrix[i - k][j].longestFromLeft >= k && matrix[i][j - k].longestFromUp >= k) {
                        //  right top point, top edge             bottom right point, left edge
                        count++;
                    }
                }
            }
        }

        return count;
    }


    public static void main(String[] args) {
        Match[][] match = new Match[3][3];

        Match match00 = new Match(0,0, false, false);
        Match match01 = new Match(0, 1, false, true);
        Match match02 = new Match(0, 2, false, true);

        Match match10 = new Match(1, 0, true, false);
        Match match11 = new Match(0, 1, false, true);
        Match match12 = new Match(1, 2, true, true);

        Match match20 = new Match(2, 0, true, false);
        Match match21 = new Match(1, 1, true, true);
        Match match22 = new Match(2,2, true, true);

        match[0][0] = match00;
        match[0][1] = match01;
        match[0][2] = match02;

        match[1][0] = match10;
        match[1][1] = match11;
        match[1][2] = match12;

        match[2][0] = match20;
        match[2][1] = match21;
        match[2][2] = match22;

        int res = Main.countMatchSquares(match);
        System.out.println(res);
    }
 */

/*
    Q6 largest rectangle

    1 1 1 1 1
    0 1 1 1 1
    0 1 0 1 1
    1 1 1 1 0
    0 1 1 1 1
    return 8

    static int largestRectangle(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int max = 0;
        for (int i = 0; i < rows; i++) {
            int[] flatRow = new int[matrix[0].length];
            for (int j = i; j < rows; j++) {
                add(flatRow, matrix[j]);
                int longestRepeatingNum = getLongestRepeatingNum(j - i + 1, flatRow);
                max = Math.max(max, (j - i + 1) * longestRepeatingNum);
            }
        }
        return max;
    }

    static public void add(int[] flatRow, int[] array) {
        for (int i = 0; i < array.length; i++) {
            flatRow[i] += array[i];
        }
    }

    static public int getLongestRepeatingNum(int num, int[] array) {
        int counter = 0;
        int max = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == num) {
                counter++;
                max = Math.max(max, counter);
            } else {
                counter = 0;
            }
        }
        return max;
    }

    public static void main(String[] args) {
        int[][] matrix = new int[][] {
                {1,1,1,1,1},
                {0,1,1,1,1},
                {0,1,0,1,1},
                {1,1,1,1,0},
                {0,1,1,1,1}
        };
        int res = Main.largestRectangle(matrix);
        System.out.println(res);
    }
 */

/**
 *  Robots matrix moving problems
 */

/*
    Q2.1  ways from leftmost to rightmost column, each step
      y
    x y
      y

    dp[i][j] = dp[i][j - 1] + dp[i + 1][j - 1] + dp[i - 1][j - 1]

    static public int waysLeftMostToRightMostColumn(int rows, int cols) {
        int[][] dp = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            dp[i][0] = 1;
        }

        for (int j = 1; j < cols; j++) {
            for (int i = 0; i < rows; i++) {
                if (i == 0) {
                    dp[i][j] = dp[i][j - 1] + dp[i + 1][j - 1];
                } else if (i == rows - 1) {
                    dp[i][j] = dp[i - 1][j - 1] + dp[i][j - 1];
                } else {
                    dp[i][j] = dp[i][j - 1] + dp[i + 1][j - 1] + dp[i - 1][j - 1];
                }
            }
        }

        int res = 0;
        for (int i = 0; i < rows; i++) {
            res += dp[i][cols - 1];
        }
        return res;
     }

    public static void main(String[] args) {
        int res = Main.waysLeftMostToRightMostColumn(2, 3);
        System.out.println(res);
    }
 */
/*
    Q2.1.4  what if there are k certain point must go through

    x x 1 x
    x x x x
    x x 1 x

    mark all the points of that column to obstacles except the given point

    static public int waysLeftMostToRightMostColumnMustPassSomePoints(int rows, int cols, int[][] points) {
        int[][] dp = new int[rows][cols];

        //change entire column to obstacle except the point
        for (int[] point : points) {
            for (int i = 0; i < rows; i++) {
                if (i != point[0]) {
                    dp[i][point[1]] = 1; //1 means obstacle
                }
            }
        }

        for (int i = 0; i < rows; i++) {
            dp[i][0] = 1;
        }

        for (int j = 1; j < cols; j++) {
            for (int i = 0; i < rows; i++) {
                if (dp[i][j] == 1) { //obstacle, dp[i][j] = 0
                    dp[i][j] = 0;
                } else if (i == 0) {
                    dp[i][j] = dp[i + 1][j - 1] + dp[i][j - 1];
                } else if (i == rows - 1) {
                    dp[i][j] = dp[i - 1][j - 1] + dp[i][j - 1];
                } else {
                    dp[i][j] = dp[i][j - 1] + dp[i + 1][j - 1] + dp[i - 1][j - 1];
                }
            }
        }

        int res = 0;
        for (int i = 0; i < rows; i++) {
            res += dp[i][cols - 1];
        }
        return res;
     }

    public static void main(String[] args) {
        int res = Main.waysLeftMostToRightMostColumnMustPassSomePoints(2, 3, new int[][]{{0,1}});
        System.out.println(res);
    }
 */
/*
    Q3  ways from left top to right bottom, path 两点拐点之间距离 > 1

    dp[i][j][k] = # of ways reaching (i,j) form (0,0), (i,j) as corner and following direction(up/left)

    // sum of all pints < i - 2, and only related to left path, b.c. each(i,j) as point corner based on state definition
    dp[i][j][up] = sum(dp[x][j][left]) 0 <= x <= i - 2  //up point related to left path, as corner itself
    dp[i][j][left] = sum(dp[i][y][up] 0 <= y <= j- 2

    static public int waysLeftTopToRightBottomTurnDistanceGreateThanOne(int rows, int cols) {
        int[][][] dp = new int[rows][cols][2];
        // up matrix, base case
        for (int i = 0; i < rows; i++) {
            if (i == 1) continue;
            dp[i][0][0] = 1;
        }

        //left matrix, base case
        for (int j = 0; j < cols; j++) {
            if (j == 1) continue;
            dp[0][j][1] = 1;
        }

        for (int i = 2; i < rows; i++) {
            for (int j = 2; j < cols; j++) {
                for (int k = 0; k <= i - 2; k++) {
                    dp[i][j][0] += dp[k][j][1];
                }

                for (int k = 0; k <= j - 2; k++) {
                    dp[i][j][1] += dp[i][k][0];
                }
            }
        }

        return dp[rows-1][cols-1][0] + dp[rows-1][cols-1][1];
    }

    public static void main(String[] args) {
        int res = Main.waysLeftTopToRightBottomTurnDistanceGreateThanOne(3, 3);
        System.out.println(res);
    }
 */

/*
    Q4.1    ways moving from top left to right bottom, up down left, each cell only be visited once

    0: left
    1: up
    2: down

    static public int waysMoveUpDownRightEachCellAppearedAtMostOnce(int rows, int cols) {
        int[][][] dp = new int[rows][cols][3];
        //base case for up direction, 0 column should be 1
        for (int i = 0; i < rows; i++) {
            dp[i][0][1] = 1;
        }

        for (int j = 1; j < cols; j++) {
            // left direction
            for (int i = 0; i < rows; i++) {
                dp[i][j][0] = dp[i][j - 1][0] + dp[i][j - 1][1] + dp[i][j - 1][2];
            }

            // up direction
            for (int i = 1; i < rows; i++) {
                dp[i][j][1] = dp[i - 1][j][1] + dp[i - 1][j][0];
            }

            for (int i = rows - 2; i >= 0; i--) {
                dp[i][j][2] = dp[i + 1][j][2] + dp[i + 1][j][0];
            }
        }

        return dp[rows - 1][cols - 1][0] + dp[rows - 1][cols - 1][1];
    }

    public static void main(String[] args) {
        int res = Main.waysMoveUpDownRightEachCellAppearedAtMostOnce(3, 3);
        System.out.println(res);
    }
 */
/*
    Q homework

    minimum number of obstacle to be removed from top left to bottom right

    static public int robotMoveMinObstaclesRemoved(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][][] dp = new int[rows][cols][3];
        //base case for up direction, 0 column should be 1
        dp[0][0][0] = matrix[0][0];
        dp[0][0][1] = matrix[0][0];
        dp[0][0][2] = matrix[0][0];

        //base column 0
        for (int i = 1; i < rows; i++) {
            dp[i][0][0] = Integer.MAX_VALUE;
            dp[i][0][1] = dp[i - 1][0][1] + matrix[i][0];
            dp[i][0][2] = Integer.MAX_VALUE;
        }

        //base case for left direction, 0 row should be 1
        for (int j = 1; j < cols; j++) {
            dp[0][j][1] = Integer.MAX_VALUE;
            dp[0][j][0] = dp[0][j - 1][0] + matrix[0][j];
            dp[0][j][2] = Integer.MAX_VALUE;
        }

        //base case, last row, from bottom
        for (int j = 0; j < cols; j++) {
            dp[rows - 1][j][2] = Integer.MAX_VALUE;
        }

        for (int j = 1; j < cols; j++) {
            // left direction
            for (int i = 1; i < rows; i++) {
                dp[i][j][0] = Math.min(dp[i][j - 1][0], Math.min( dp[i][j - 1][1], dp[i][j - 1][2])) + matrix[i][j];
            }

            // up direction
            for (int i = 1; i < rows; i++) {
                dp[i][j][1] = Math.min(dp[i - 1][j][1], dp[i - 1][j][0]) + matrix[i][j];
            }

            for (int i = rows - 2; i >= 0; i--) {
                dp[i][j][2] = Math.min(dp[i + 1][j][2], dp[i + 1][j][0]) + matrix[i][j];
            }
        }

        return Math.min(dp[rows - 1][cols - 1][0], dp[rows - 1][cols - 1][1]) + matrix[rows - 1][cols - 1];
    }

    public static void main(String[] args) {
        int[][] matrix = new int[][] {
                {0,0,1,1,0,0},
                {0,0,1,1,1,1},
                {0,0,1,1,0,0},
                {0,0,1,1,0,0}
        };
        int res = Main.robotMoveMinObstaclesRemoved(matrix);
        System.out.println(res);
    }
 */
/*
    Q5 number of ways form top left to bottom right, can move up down left right by waking k steps
    dp[i][j][k]: # of ways reaching (i,j) form (0,0) by exactly k steps

    static public int robotMoveMinObstaclesRemoved(int rows, int cols, int k) {
        int[][][] dp = new int[rows][cols][k+1];
        dp[0][0][0] = 1;
        for (int x = 1; x <= k; x++) {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (i - 1 >= 0) {
                        dp[i][j][x] += dp[i - 1][j][x - 1];
                    }
                    if (i + 1 < rows) {
                        dp[i][j][x] += dp[i + 1][j][x - 1];
                    }
                    if (j - 1 >= 0) {
                        dp[i][j][x] += dp[i][j - 1][x - 1];
                    }
                    if (j + 1 < cols) {
                        dp[i][j][x] += dp[i][j + 1][x - 1];
                    }
                }
            }
        }
        return dp[rows -1][cols -1][k];
    }

    public static void main(String[] args) {
        int res = Main.robotMoveMinObstaclesRemoved(2, 3, 3);
        System.out.println(res);
    }
 */