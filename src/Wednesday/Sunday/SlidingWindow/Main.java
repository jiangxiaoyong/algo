package Wednesday.Sunday.SlidingWindow;

import com.sun.tools.javah.Gen;
import org.omg.CORBA.INTERNAL;
import org.omg.PortableInterceptor.INACTIVE;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import sun.util.resources.sv.CurrencyNames_sv_SE;

import java.awt.*;
import java.sql.Array;
import java.sql.SQLSyntaxErrorException;
import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {


    /*



                   0 1  2 3  4  5 6
                  [1,2,-3,6,-2,-2,2]
     prefix sum [0,1,3, 0,6, 4, 2,4] target = 0
                        ^ ^  ^  ^

     sum < target
     target > sum - prev
     prev > sum - target

     012345
     143212

     A D B E * B C
     1 2 3 4 5 6 7

     A D B E B C
         h
             c
     {A:1}              time 1
     {A:1, D:2}         time 2
     {A:1, D:2, B:3}    time 3  remove head A 3- 2 = 1
     {D:2, B:3, E:4}    time 4  remove head d 4 - 2 = 2
     {B:6, E:4}         time 6  remove head E 6 - 2 = 4
     {B:6, C:7}         time 7

        [0,3,4,15,21]
         s   f


     */


    interface RateLimiter {
        public boolean canCall();
    }

    class SimpleRateLimiter implements RateLimiter {
        private int[] hits;
        public static final int WINDOW = 1000;
        private long prevMillis;
        private int total;
        private int limit;

        public SimpleRateLimiter(int limit) {
            hits = new int[WINDOW];
            this.limit = limit;
            prevMillis = System.currentTimeMillis();
            total = 0;
        }

        @Override
        public boolean canCall() {
            long curMillis = System.currentTimeMillis();
            evict(curMillis);
            if (total >= limit) {
                return false;
            }
            hits[(int) (curMillis % 300)]++;
            total++;
            prevMillis = curMillis;
            return true;
        }

        public void evict(long curMillis) {
            if (curMillis >= prevMillis + WINDOW) {
                Arrays.fill(hits,0);
                total = 0;
                return;
            }
            for (int time = (int) (prevMillis + 1); time <= curMillis; time++) {
                total -= hits[(int) (curMillis % WINDOW)];
                hits[(int) (curMillis % WINDOW)] = 0;
            }
        }
    }


/*
    a =          [3,0,-1,-3,4,2]
    prefix-sum [0,3,3, 2,-1,3,5]

    target = 2    5 -2 = 3
 */


    public static void main(String[] args) {
        Main main = new Main();
    }
}

/*
 *      class 1 Fixed size
 *
 *      to keep correct window size, we need to move head as the end of loop,
 *      and add new elements at the beginning of loop
 *
 *      Question Anagram and max value of sliding window are bit different
 *      They moving head at beginning of loop, but that still work, because the real work is after
 */

/*
    Q1.  find repeated dna seq

    Time: O(k * n)

    public List<String> findRepeatedDnaSequences(String s) {
        Set<String> set = new HashSet<>();
        List<String> res = new LinkedList<>();
        for (int i = 0; i < s.length() - 9; i++) {
            String tenChar = s.substring(i, i + 10);
            if (!set.add(tenChar)) { // see repeated string
                res.add(tenChar);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        Main main = new Main();
        System.out.println(main.findRepeatedDnaSequences("AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT"));
    }
 */

/*
    Q 3.1 Find k-largest elements in each subarray of size n
     0 1 2 3 4 5
    [1,4,3,2,1,2,] n = 3, k = 2
     1 4 3                  [4,3]
       4 3 2                [4,3]
         3,2,1              [3,2]
           2 1 1            [2,2]

    public List<List<Integer>> kLargestInEachSubArray(int[] array, int n, int k) {
        List<List<Integer>> res = new ArrayList<>();
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

        for (int i = 0; i < array.length; i++) {
            maxHeap.offer(array[i]); //window size back to normal after adding

            if (i - n + 1 >= 0) {
                int a1 = maxHeap.poll();
                int a2 = maxHeap.poll();

                res.add(Arrays.asList(a1, a2));

                maxHeap.offer(a1);
                maxHeap.offer(a2);

                //moving head
                maxHeap.remove(array[i - n + 1]); // not O(log n)
            }
        }
        return res;
    }

    public static void main(String[] args) {
        Main main = new Main();
        List<List<Integer>> res = main.kLargestInEachSubArray(new int[] {1,4,3,2,1,2}, 3, 2);
        System.out.println(res.toString());
    }
 */

/*
    Q 4.2  if there is dup and distance between than less than k, return true

     0 1 2 3 4
    [1,2,3,4,1] k = 2, false
    [1,2,1,4,2] k = 2, true

    public boolean duplicateIfDistanceWithinK(int[] array, int k) {
        Set<Integer> window = new HashSet<>();
        for (int i = 0; i < array.length; i++) {
            if (!window.add(array[i])) {
                return true;
            }

            //NOTE: remove the delta, '>=' e.g if window is 3, there the window size 2, new el added next loop
            if (i - k >= 0) {
                window.remove(array[i - k]);
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Main main = new Main();
        boolean res = main.duplicateIfDistanceWithinK(new int[] {1,2,1,4,2},  2);
        System.out.println(res);
    }
 */

/*
    follow-up 4.3 if there is dup, that distance <= k, diff value <= d

    [1,3,5,1], k =2, d = 1, return false;
    [1,10,3,20,15], k = 2, d = 2, return true;

     public boolean duplicateIfDistanceWithinKValueLessThanD(int[] array, int k, int d) {
        TreeSet<Integer> window = new TreeSet<>();
        for (int i = 0; i < array.length; i++) {
            Integer smallestLargerEqual = window.ceiling(array[i]);
            if (smallestLargerEqual != null && smallestLargerEqual - array[i] <= d) {
                return true;
            }

            Integer largestSmallerEqual = window.floor(array[i]);
            if (largestSmallerEqual != null && array[i] - largestSmallerEqual <= d) {
                return true;
            }

            //add later at here b.c [1,10,3], cur = 3, ceiling return 3, not 10
            window.add(array[i]);

            if (i - k >= 0) {
                window.remove(array[i - k]);
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Main main = new Main();
        boolean res = main.duplicateIfDistanceWithinKValueLessThanD(new int[] {1,10,3,20,15},  2, 2);
        System.out.println(res);
    }
 */

/*
 *      Class 2 2d sliding window
 */

/*
        Q5. average of sliding window

    [2,    3, 5,  7,8,   9]
    [-3.2, 0, 2, 1, 0.8, -2]
    left                  right

    sliding window: [left, right)     close left, open right

    public double[] averageSlidingWindow(int[] time, double[] value, int k) {
        double[] average = new double[time.length];
        int left = 0;
        int right = 0;
        double sum = 0;
        for (int i = 0; i < time.length; i++) {
            while (time[left] < time[i] - k) {
                sum -= value[left];
                left++;
            }

            while (right < time.length && time[right] <= time[i] + k) {
                sum += value[right];
                right++;
            }
            average[i] = sum / (right - left);
        }
        return average;
    }

    public static void main(String[] args) {
        Main main = new Main();
        double[] res = main.averageSlidingWindow(new int[] {2,3,5,7,8,9}, new double[]{-3.2, 0,2,1,0.8, -2},3);
        for (double d : res) {
            System.out.println(d);
        }
    }
 */

/*
    Q6. schedule task

    tasks: AABCB, k=2

    task: A * * A B C * B
    time: 1 2 3 4 5 6 7 8

     A D B E B C
         h
             c
     {A:1}              time 1
     {A:1, D:2}         time 2
     {A:1, D:2, B:3}    time 3  remove head A 3- 2 = 1
     {D:2, B:3, E:4}    time 4  remove head d 4 - 2 = 2
     {B:6, E:4}         time 6  remove head E 6 - 2 = 4
     {B:6, C:7}         time 7

    public int taskSchedule(char[] tasks, int k) {
        Map<Character, Integer> recentTime = new HashMap<>();
        int time = 0; // finish time of previous task
        int cur = 0;
        int head = 0;

        while (cur < tasks.length) {
            // try to add into queue
            //step 1: check the time to run task
            Integer recent = recentTime.get(tasks[cur]);
            if (recent != null) time = recent + k + 1;
            else time++;

            // step2 : move head, '<=' ensure remove the head, decrease the size by one. Next round loop add tail to recover the size
            while (head < cur && recentTime.get(tasks[head]) <= time - k) { // window of [x-k, x)
                recentTime.remove(tasks[head]);
                head++;
            }
            //step3 : update the time for the task
            //must update later, e.g {A:1} @time 4, A:1 should be delete, and update to {A:4}
            recentTime.put(tasks[cur], time);
            cur++;
        }
        return time;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int res = main.taskSchedule(new char[]{'A','B','C','D','E'}, 2);
        System.out.println(res);
    }
 */

/*
    Q7 form straight

    cards:   [1,2,3,4,5,6,7]
    count:   [1,3,4,4,4,3,1]
    s(x):     1,2,1,0,0,0,0
    n(x);     0 1 3 4 4 3 1      <-- using prefix sum

    s(x): straight starting from x,
    n(x): straight not start from x

    public boolean straight(int[] array, int k) {
        int[] countArray = new int[14];
        for (int i : array) {
            countArray[i]++;
        }

        int[] sx = new int[countArray.length];

        // mark the first non zero element
        int start = 0;
        for (int i = 1; i < countArray.length; i++) {
            if (countArray[i] != 0) {
                sx[i] = 1;
                start = i;
                break;
            }
        }

        int sum = 0;
        for (int i = start + 1; i < sx.length; i++) {
            sum += sx[i - 1];
            sx[i] = countArray[i] - sum;

            if (sx[i] < 0) { // can not form straight
                return false;
            }

            if (i - k >= 0) { //remove head
                sum -= sx[i - k + 1];
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Main main = new Main();
        boolean res = main.straight(new int[] {1,2,3,4,5,2,3,4,5,6,2,3,4,5,6,3,4,5,6,7}, 5);
        System.out.println(res);
    }
 */

/*
 *      class 3. None-fixed size sliding window
 */

/*
    Q0 return size of largest subset, that elements of the subset differ by more than target

    [4,3,15,0,21], target 5 => [4,3,0] return 3

     public int largestSubsetDiffMoreThanTarget(int[] array, int target) {
        Arrays.sort(array);
        int slow = 0;
        int fast = 0;
        int max = 0;
        while (fast < array.length) {
            while (array[fast] - array[slow] > target) {
                slow++;
            }
            max = Math.max(max, fast - slow + 1);
            fast++;
        }
        return max;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int res = main.largestSubsetDiffMoreThanTarget(new int[] {4,3,15,0,21}, 5);
        System.out.println(res);
    }
 */

/*
    Q1.  shortest subArray which sum > target

    a =         [1,3,2,2,4, 1]
                       s f
    prefix =  [0,1,4,6,8,12,13]

    public int shortestSubArraySumGreaterTarget(int[] array, int target) {
        int fast = 0;
        int slow = 0;
        int sum = 0;
        int min = Integer.MAX_VALUE;
        while (fast < array.length) {
            sum += array[fast];
            while (sum > target) {
                min = Math.min(min, fast - slow + 1); //last time sum > target
                sum -= array[slow++];
            }
            fast++;
        }
        return min;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int res = main.shortestSubArraySumGreaterTarget(new int[] {1,3,2,2,4,1}, 5);
        System.out.println(res);
    }
 */

/*
    Q 1.1 shortest sub array sum == target, array with negative

    a =          [3,0,-1,-3,4]
    prefix-sum [0,3,3, 2,-1,3]

     public int shortestSubArraySumEquLTargetWithNegative(int[] array, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1); // important
        int sum = 0;
        int min = Integer.MAX_VALUE;
        for (int j = 0; j < array.length; j++) {
            sum += array[j];
            if (map.containsKey(sum - target)) {
                min = Math.min(min, j - map.get(sum - target));
            }
            map.put(sum, j); // find right most index to ensure shortest
        }
        return min;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int res = main.shortestSubArraySumEquLTargetWithNegative(new int[] {3,0,-1,-3,4}, 4);
        System.out.println(res);
    }

 */

/*
    Q 1.1.1 largest sub array sum == target , with negative elements

    public int largestSubArraySumEquLTargetWithNegative(int[] array, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1); // important
        int sum = 0;
        int max = Integer.MIN_VALUE;
        for (int j = 0; j < array.length; j++) {
            sum += array[j];
            if (map.containsKey(sum - target)) {
                max = Math.max(max, j - map.get(sum - target));
            }
            map.putIfAbsent(sum, j); // find left most index to ensure largest
        }
        return max;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int res = main.largestSubArraySumEquLTargetWithNegative(new int[] {3,0,-1,-3,4}, 4);
        System.out.println(res);
    }
 */

/*
    1.1.2   number of sub array with sum == target

    public int largestSubArraySumEquLTargetWithNegative(int[] array, int target) {
        // Map<value, # of occurrence>
        Map<Integer, Integer> count = new HashMap<>();
        count.put(0, 1); // important
        int sum = 0;
        int result = 0;
        for (int j = 0; j < array.length; j++) {
            sum += array[j];
            result += count.getOrDefault(sum - target, 0);
            count.put(sum, count.getOrDefault(sum, 0) + 1);
        }
        return result;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int res = main.largestSubArraySumEquLTargetWithNegative(new int[] {3,0,-1,-3,4}, 4);
        System.out.println(res);
    }
 */

/*
    1.1.3 find the sub array with sum closet to target, return sum

    for each j:
        find closets prefix sum[i] to (prefix[j] - target)
            - smallestLargerOrEquals
            - largestSmallerOrEquals

     public int subArrayClosestToTarget(int[] array, int target) {
        int result = 0;
        int sum = 0;
        int min = Integer.MAX_VALUE;
        TreeSet<Integer> closet = new TreeSet<>();
        closet.add(0);
        for (int j = 0; j < array.length; j++) {
            sum += array[j];
            Integer floor = closet.floor(sum - target);
            Integer ceiling = closet.ceiling(sum - target);
            int candidate = 0;
            if (floor != null && ceiling != null) {
                candidate = Math.abs(sum - floor - target) - Math.abs(sum - ceiling - target) < 0 ?
                        sum - floor : sum- ceiling;
            } else if (floor != null) {
                candidate = sum - floor;
            } else if (ceiling != null) {
                candidate = sum - ceiling;
            }

            if (Math.abs(candidate - target) < min) {
                result = candidate;
                min = Math.abs(candidate - target);
            }
            closet.add(sum);
        }
        return result;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int res = main.subArrayClosestToTarget(new int[] {3,0,1,3,4}, 6);
        System.out.println(res);
    }
 */

/*
    1.2     find longest sub array which sum < target

                   0 1  2 3  4  5 6
                  [1,2,-3,6,-2,-2,2]
     prefix sum [0,1,3, 0,6, 4, 2,4] target = 0
                        ^ ^  ^  ^

    prev > sum - target
    3    >  2 -  0

    example analysis:

                 0 1 2 3 4 5 6 7
prefix suj      [0,1,3,0,6,4,2,4]

                       0 1 2 4
possible i pos        [0,1,3,6]
                           i

                       0 1 6 7
possible j pos        [0,1,2,4]
                           j

             max 4     index 6 -  index 2

    // this is not optimal solution
    public int longestSubArraySumLessTargetWithNegative(int[] array, int target) {
        int sum = 0;
        Map<Integer, Integer> map = new HashMap<>();
        int max = 0;
        map.put(0, -1);
        for (int j = 0; j < array.length; j++) {
            sum += array[j];
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                if (entry.getKey() > sum - target) {
                    max = Math.max(max, j - entry.getValue());
                }
            }
            map.putIfAbsent(sum, j);
        }
        return max;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int res = main.longestSubArraySumLessTargetWithNegative(new int[] {1,2,-3,6,-2,-2,2}, 0);
        System.out.println(res);
    }
 */

/*
        2. find longest substring / largest number that corresponding int number % 36 = 1

        36 = 4 * 9
        % 36 = % 4 && % 9

        x % 4 = only need to look at last 2 digits
        x % 9 = sum(all the digits of x) % 9

     public int longestSubstringModular(String str) {
        int[] max = new int[1];
        int fast = 0;
        while (fast < str.length()) {
            if (Character.isDigit(str.charAt(fast))) {
                int end = fast;
                while (end < str.length() && Character.isDigit(str.charAt(end))) end++;
                findLongestSubstring(str.substring(fast, end), max);
                fast = end;
            } else {
                fast++;
            }
        }
        return max[0];
    }

    public void findLongestSubstring(String str, int[] max) {
        //check one digit
        if (str.length() == 1 && Integer.valueOf(str) == 1) max[0] = Math.max(max[0], 1);

        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        Integer sum = 0;
        for (int i = 1; i <= str.length(); i++) {
            sum += str.substring(i-1, i) - '0';
            map.putIfAbsent(sum % 9, i - 1);
            if (map.containsKey((sum % 9) - 1)) {
                int numFor4 = Integer.valueOf(str.substring(i - 2, i));
                // check modular for 4
                if (numFor4 % 4 == 1) {
                    max[0] = Math.max(max[0], i - map.get((sum % 9) - 1));
                }
            }
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        int res = main.longestSubstringModular("agda468576698657986869");
        System.out.println(res);
    }
 */

/*
 *       class 4 Non fix sliding window
 */

/*
    Q4  word distance I

    [a,c,d,b,b,b,c,a],   s(a,b) = 2

    public int shortestWordDistance(char[] array, char a, char b) {
        int min = Integer.MAX_VALUE;
        int aPos = -1;
        int bPos = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == a) {
                if (bPos != -1) {
                    min = Math.min(min, i - bPos + 1);
                }
                aPos = i;
            }

            if (array[i] == b) {
                if (aPos != -1) {
                    min = Math.min(min, i - aPos + 1);
                }
                bPos = i;
            }
        }
        return min;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int res = main.shortestWordDistance(new char[] {'a','c','d','b','b','b','c','a'}, 'a', 'b');
        System.out.println(res);
    }
 */

/*
    4.2 word distance I

    using two pointer, find the min difference, 谁小移谁

    public int shortestWordDistanceTwoPointers(char[] array, char a, char b) {
        List<Integer> la = new ArrayList<>();
        List<Integer> lb = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            if (array[i] == a) {
                la.add(i);
            } else if (array[i] == b){
                lb.add(i);
            }
        }
        int i = 0;
        int j = 0;
        int min = Integer.MAX_VALUE;
        while (i < la.size() && j < lb.size()) {
            min = Math.min(min, Math.abs(la.get(i) - lb.get(j)) + 1);
            if (la.get(i) < lb.get(j)) {
                i++;
            } else {
                j++;
            }
        }
        return min;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int res = main.shortestWordDistanceTwoPointers(new char[] {'a','c','d','b','b','b','c','a'}, 'a', 'b');
        System.out.println(res);
    }
 */

/*
    Q5 longest substring at most k distinct characters

    public int longestSubStringAtMostKDistinctChar(String str, int k) {
        Map<Character, Integer> map = new HashMap<>();
        int max = -1;
        int slow = 0;
        int fast = 0;

        while (fast < str.length()) {
            char ch = str.charAt(fast);
            map.put(ch, map.getOrDefault(ch, 0) + 1);
            while (map.size() > k && slow < str.length()) {
                //NOTE: if the we want to find shortest, we need to update min at here

                char slowChar = str.charAt(slow);
                if (map.containsKey(slowChar)) {
                    int count = map.get(slowChar);
                    if (count == 1) {
                        map.remove(slowChar);
                    }
                    map.put(slowChar, count - 1);
                }
                slow++;
            }
            //guarantee map.size() <= k

            // NOTE: to find longest, update max at here
            max = Math.max(max, fast - slow + 1);
            fast++;
        }
        return max;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int res = main.longestSubStringAtMostKDistinctChar("aaaabbbca", 2);
        System.out.println(res);
    }

 */

/*
    Q 6.1 how many substring satisfy no duplicate chars

    //all substring within the longest string without dup are strings without dup

    public int hwoManySubStringContainingNoDupChars(String str) {
        int count = 0;
        int slow = 0;
        int fast = 0;
        Set<Character> set = new HashSet<>();
        while (fast < str.length()) {
            if (!set.add(str.charAt(fast))) {
                while (str.charAt(slow) != str.charAt(fast)) {
                    set.remove(str.charAt(slow));
                    slow++;
                }
            }
            count += (fast - slow + 1);  // same as Q6, except add count
            fast++;
        }
        return count;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int res = main.hwoManySubStringContainingNoDupChars("aaaabbbca");
        System.out.println(res);
    }

 */

/*
    Design counter class

     class Counter {
        private int[] hits;
        public static final int WINDOW = 5 * 60;
        long prevSec;
        int total;

        public Counter() {
            hits = new int[300];
            total = 0;
            prevSec = System.currentTimeMillis() / 1000;
        }

        public void record() {
            long curSec = System.currentTimeMillis() / 1000;
            evict(curSec);
            hits[(int) (curSec % 300)]++;
            total++;
            prevSec = curSec;
        }

        public long hitsLastFiveMins() {
            long curSec = System.currentTimeMillis() / 1000;
            evict(curSec);
            prevSec = curSec;
            return total;
        }

        private void evict(Long curSec) {
            if (curSec >= prevSec + WINDOW) {
                Arrays.fill(hits, 0);
                total = 0;
                return;
            }
            for (long time = prevSec + 1; time <= curSec; time++) {
                total -= hits[(int) (time % 300)];
                hits[(int)(time % 300)] = 0;
            }
        }
    }
 */

/*
    Rate limiter

    interface RateLimiter {
        public boolean canCall();
    }

    class SimpleRateLimiter implements RateLimiter {
        private int[] hits;
        public static final int WINDOW = 1000;
        private long prevMillis;
        private int total;
        private int limit;

        public SimpleRateLimiter(int limit) {
            hits = new int[WINDOW];
            this.limit = limit;
            prevMillis = System.currentTimeMillis();
            total = 0;
        }

        @Override
        public boolean canCall() {
            long curMillis = System.currentTimeMillis();
            evict(curMillis);
            if (total >= limit) {
                return false;
            }
            hits[(int) (curMillis % 300)]++;
            total++;
            prevMillis = curMillis;
            return true;
        }

        public void evict(long curMillis) {
            if (curMillis >= prevMillis + WINDOW) {
                Arrays.fill(hits,0);
                total = 0;
                return;
            }
            for (int time = (int) (prevMillis + 1); time <= curMillis; time++) {
                total -= hits[(int) (curMillis % WINDOW)];
                hits[(int) (curMillis % WINDOW)] = 0;
            }
        }
    }
 */