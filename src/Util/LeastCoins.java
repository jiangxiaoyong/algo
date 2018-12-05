package Util;

public class LeastCoins {
    /**
     *
     *
     Coins Combination
     {2, 3, 6} - coins you can use.
     11 - target coins.
     least number of coins to sum up to the target.

     m[i] represent least number of coins to sum up to i

     base case: m[0] = 0
     induction rule:
     m[i] = min(m[j]) + 1  if i - coin[i]  = 0

     target == 1    m[1] = 0

     target == 2    m[2] = 1
     2 - coin[0] = 0 = m[0] = 0

     target == 3    m[3] = min(m[0], m[1]) + 1 = 1
     3 - coin[0] = 3 - 2 = 1 = m[1] = 0
     3 - coin[1] = 3 - 3 = 0 = m[0] = 0

     target == 4    m[4] = m[2] + 1 = 2
     4 - coin[0] = 4 - 2 = m[2]
     4 - coin[1] = 4 - 3 = m[1]

     target == 5    m[5] = min(m[2], m[3]) + 1 = 2
     5 - coin[0] = 5 - 2 = m[3] = 1
     5 - cont[1] = 5 - 3 = m[2] = 1
     5 - coin[2] = 5 - 6 < 0

     target == 6     m[6] = min(m[0], m[3], m[4]) + 1 = 1
     6 - coin[0] = 6 - 2 = 4 = m[4] = 2
     6 - coin[1] = 6 - 3 = 3 = m[3] = 1
     6 - coin[2] = 6 - 6 = 0 = m[0] = 0

     */

    public int solution(int[] coins, int target) {

        int[] m = new int[target + 1];

        for (int i = 1; i <= target; i++) {
            int curMin = Integer.MAX_VALUE;
            for (int j = 0; j < coins.length; j++) {
                if (i - coins[j] >= 0) {
                    curMin = Math.min(curMin, m[i - coins[j]] + 1);
                }
            }
            m[i] = curMin == Integer.MAX_VALUE ? 0 : curMin;
        }
        return m[target];
    }
}
