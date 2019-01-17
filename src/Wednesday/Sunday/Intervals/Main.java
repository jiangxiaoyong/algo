package Wednesday.Sunday.Intervals;


import com.sun.xml.internal.ws.policy.spi.PolicyAssertionValidator;

import java.lang.reflect.Array;
import java.util.*;

public class Main {

    static class Interval {
        int start;
        int end;
        Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }












/*

----[1,2]
       ------------ [3,8]
          -----[4,7]
              ---------------[6,11]
                 ------[7,9]
                           --------------[10,14]
                                               ----------[16,18]
 */

    static public int largestSubsetNoOverlap(List<Interval> intervals) {
        Collections.sort(intervals, new Comparator<Interval>() {
            @Override
            public int compare(Interval o1, Interval o2) {
                return o1.end - o2.end;
            }
        });
        int[] m = new int[intervals.size()];

        // base case: any interval overlap with first interval
        for (int i = 0; i < intervals.size(); i++) {
            if (intervals.get(0).end >= intervals.get(i).start && intervals.get(0).end <= intervals.get(i).end) {
                m[i] = 1;
            }
        }

        int res = 0;
        for (int i = 0; i < intervals.size(); i++) {
            for (int j = 0; j < i; j++) {
                if (intervals.get(j).end <= intervals.get(i).start) {
                    m[i] = Math.max(m[i], m[j] + 1);
                }
            }
            res = Math.max(res, m[i]);
        }
        return res;
    }

    public static void main(String[] args) {
        List<Interval> l1 = new ArrayList<>();
        l1.add(new Interval(1,2));
        l1.add(new Interval(3,8));
        l1.add(new Interval(4,7));
        l1.add(new Interval(6,11));
        l1.add(new Interval(7,9));
        l1.add(new Interval(10,14));
        l1.add(new Interval(16,18));
        int res = Main.largestSubsetNoOverlap(l1);
        System.out.println(res);
    }
}

/*
    Q 1.2   Merge two lists of sorted non-overlap intervals

    [0,2] [3,5] [6,7]
                 i
    [1,4][6,9]
          j
     res: [0,5],[6,9]

     move whichever pointer is the smaller

     static public List<Interval> mergeTwoLists(List<Interval> l1, List<Interval> l2) {
        List<Interval> res = new ArrayList<>();
        if (l1 == null && l2 == null) return res;
        if (l1 == null) return l2;
        if (l2 == null) return l1;

        int i = 0;
        int j = 0;
        int prevStart = l1.get(0).start < l2.get(0).start ? l1.get(0).start : l2.get(0).start;
        int prevEnd = l1.get(0).start < l2.get(0).start ? l1.get(0).end : l2.get(0).end;

        while (i < l1.size() || j < l2.size()) {
            if ((i < l1.size() && j < l2.size() && l1.get(i).start < l2.get(j).start) || j >= l2.size()) {
                if (l1.get(i).start <= prevEnd) {
                    prevEnd = Math.max(prevEnd, l1.get(i).end);
                } else {
                    res.add(new Interval(prevStart, prevEnd));
                    prevStart = l1.get(i).start;
                    prevEnd = l1.get(i).end;
                }
                i++;
            } else {
                if (l2.get(j).start <= prevEnd) {
                    prevEnd = Math.max(prevEnd, l2.get(j).end);
                } else {
                    res.add(new Interval(prevStart, prevEnd));
                    prevStart = l2.get(j).start;
                    prevEnd = l2.get(j).end;
                }
                j++;
            }
        }
        res.add(new Interval(prevStart, prevEnd));
        return res;
    }

    public static void main(String[] args) {
        List<Interval> l1 = new ArrayList<>();
        List<Interval> l2 = new ArrayList<>();
        l1.add(new Interval(0,2));
        l1.add(new Interval(3,5));
        l1.add(new Interval(6,7));
        l2.add(new Interval(1,4));
        l2.add(new Interval(6,9));
        List<Interval> res = Main.mergeTwoLists(l1, l2);
        for (Interval i : res) {
            System.out.println(i.start);
            System.out.println(i.end);
        }
    }
 */

/*
    Q3 Intersection of tow sorted non-overlap intervals

    [0,   2] [3,   5] [6,7]
             i
       [1,      4]    [6,9]
        j
     res: [1,2],[3,4],[6,7]

    static public List<Interval> intersectTwoLists(List<Interval> l1, List<Interval> l2) {
        List<Interval> res = new ArrayList<>();
        if (l1 == null && l2 == null) return res;
        if (l1 == null) return l2;
        if (l2 == null) return l1;

        int i = 0;
        int j = 0;
        while (i < l1.size() && j < l2.size()) {
            if (l1.get(i).end <= l2.get(j).end) {
                if (l1.get(i).end > l2.get(j).start) {
                    res.add(new Interval(Math.max(l1.get(i).start, l2.get(j).start), l1.get(i).end));
                }
                i++;
            } else {
                if (l2.get(i).end > l1.get(j).start) {
                    res.add(new Interval(Math.max(l1.get(i).start, l2.get(j).start), l2.get(j).end));
                }
                j++;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        List<Interval> l1 = new ArrayList<>();
        List<Interval> l2 = new ArrayList<>();
        l1.add(new Interval(0,2));
        l1.add(new Interval(3,5));
        l1.add(new Interval(6,7));
        l2.add(new Interval(1,4));
        l2.add(new Interval(6,9));
        List<Interval> res = Main.intersectTwoLists(l1, l2);
        for (Interval i : res) {
            System.out.println(i.start);
            System.out.println(i.end);
        }
    }
 */

/*
    Q 5.1.3 determine if target interval is overlapped with any of the sorted array non-overlap intervals

    [0,2] [4,6] [7,10]
    target [5,8] -> true

         [     ) -> target interval
    [) <- answer
       [    }
           [ )
           [          )

      // binary search to find interval.start <= target.end
     static public boolean overlapInterval(List<Interval> intervals, Interval target) {
        int left = 0;
        int right = intervals.size() - 1;
        while (left < right - 1) {
            int mid = left + (right - left) / 2;
            if (target.end <= intervals.get(mid).start) {
                right = mid - 1;
            } else {
                left = mid;
            }
        }
        if (intervals.get(left).end >= target.start || intervals.get(right).end >= target.start) return true;
        return false;
    }

    public static void main(String[] args) {
        List<Interval> l1 = new ArrayList<>();
        l1.add(new Interval(0,2));
        l1.add(new Interval(4,6));
        l1.add(new Interval(7,10));
        boolean res = Main.overlapInterval(l1, new Interval(5,8));
        System.out.println(res);
    }
 */

/*
    Q6  find minimum number of intervals to cover the target interval

            ------------------------ target[5,12]
----[1,2]
       ------------ [3,8]
          ------[4,7]
              ---------------[6,11]
                 ------[7,9]
                           --------------[10,14]
                                               ----------[16,18]
     result: 3

    - sort by end
    - filter out intervals not covered by target

    dp[i] = cover [target.start, intervals[i].end], using interval[0 - i], the min # of intervals
    induction: dp[i] = min(dp[j]) + 1

    static public int minNumIntervalsCoverTarget(List<Interval> input, Interval target) {
        List<Interval> intervals = new LinkedList<>();
        Collections.sort(input, new Comparator<Interval>() {
            @Override
            public int compare(Interval o1, Interval o2) {
                return o1.end - o2.end;
            }
        });

        // filter out intervals that not covered by target
        for (Interval itv : input) {
            if (itv.end < target.start || itv.start > target.end) {
                continue;
            }
            intervals.add(itv);
        }
        int[] m = new int[intervals.size()];
        for (int i = 0; i < intervals.size(); i++) {
            if (intervals.get(i).start <= target.start) { //base case: dp[i] = 1 where interval[i].start <= target.start
                m[i] = 1;
            } else {
                int local = Integer.MAX_VALUE;
                for (int j = 0; j < i; j++) {
                    if (intervals.get(j).end >= intervals.get(i).start && intervals.get(j).end <= intervals.get(i).end) {
                        local = Math.min(local, m[j] + 1);
                    }
                }
                m[i] = local;
            }
        }
        int res = Integer.MAX_VALUE;
        //there may multiple intervals with end > target.end, find min among them
        // min(dp[x]) where interval[x].end >= target.end
        for (int i = 0; i < intervals.size(); i++) {
            if (intervals.get(i).end >= target.end) {
                res = Math.min(res, m[i]);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        List<Interval> l1 = new ArrayList<>();
        l1.add(new Interval(1,2));
        l1.add(new Interval(3,8));
        l1.add(new Interval(4,7));
        l1.add(new Interval(6,11));
        l1.add(new Interval(7,9));
        l1.add(new Interval(10,14));
        l1.add(new Interval(16,18));
        int res = Main.minNumIntervalsCoverTarget(l1, new Interval(5,12));
        System.out.println(res);
    }
 */

/*
    Q7  find the largest subset of intervals without overlap
----[1,2]
       ------------ [3,8]
          -----[4,7]
              ---------------[6,11]
                 ------[7,9]
                           --------------[10,14]
                                               ----------[16,18]

    - sort by end
    - dp[i] max # of non overlap intervals [0, i], and interval i must included
    dp[i] = max(dp[j] + 1) where j < i && not overlap with interval[i]

     static public int largestSubsetNoOverlap(List<Interval> intervals) {
        Collections.sort(intervals, new Comparator<Interval>() {
            @Override
            public int compare(Interval o1, Interval o2) {
                return o1.end - o2.end;
            }
        });
        int[] m = new int[intervals.size()];

        // base case: any interval overlap with first interval
        for (int i = 0; i < intervals.size(); i++) {
            if (intervals.get(0).end >= intervals.get(i).start && intervals.get(0).end <= intervals.get(i).end) {
                m[i] = 1;
            }
        }

        int res = 0;
        for (int i = 0; i < intervals.size(); i++) {
            for (int j = 0; j < i; j++) {
                if (intervals.get(j).end <= intervals.get(i).start) {
                    m[i] = Math.max(m[i], m[j] + 1);
                }
            }
            res = Math.max(res, m[i]);
        }
        return res;
    }

    public static void main(String[] args) {
        List<Interval> l1 = new ArrayList<>();
        l1.add(new Interval(1,2));
        l1.add(new Interval(3,8));
        l1.add(new Interval(4,7));
        l1.add(new Interval(6,11));
        l1.add(new Interval(7,9));
        l1.add(new Interval(10,14));
        l1.add(new Interval(16,18));
        int res = Main.largestSubsetNoOverlap(l1);
        System.out.println(res);
    }
 */