package Wednesday.Sunday.DynamicProgramming;

public class Main {






















    static public int distinctSubsequences(String s, String t) {
        int[][] m = new int[s.length() + 1][t.length() + 1];
        m[0][0] = 1;

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


/*
    Class String/Character  Matching
    The Matching does not need to be from two sequences
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
