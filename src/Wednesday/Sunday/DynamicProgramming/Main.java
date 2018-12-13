package Wednesday.Sunday.DynamicProgramming;

public class Main {


















    static public int maxCoins(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int n = nums.length;
        int[] arr = new int[n + 2];
        int[][] coins = new int[n + 2][n + 2];
        for (int i = 0; i < n; i++) {
            arr[i + 1] = nums[i];
        }

        arr[0] = 1;
        arr[n + 1] = 1;

        for (int len = 1; len <= n; len++) {
            for (int i = 1; i <= n - len + 1; i++) {
                int j = i + len - 1;
                for (int k = i; k <= j; k++) {
                    coins[i][j] = Math.max(coins[i][j], coins[i][k - 1] + coins[k + 1][j] + arr[k] * arr[i - 1] * arr[j + 1]);
                }
            }
        }
        return coins[1][n];
    }

    public static void main(String[] args) {
        int res = Main.maxCoins(new int[] {3,1,5,8});
        System.out.println(res);
    }
}

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
