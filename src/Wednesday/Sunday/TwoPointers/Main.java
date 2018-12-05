package Wednesday.Sunday.TwoPointers;

import sun.tools.tree.InlineNewInstanceExpression;

import java.util.*;

public class Main {





    /*
        [1,4,5,8]
         i
        [1,2,9]
         j
        [3,6]
         k

         max =3 , min = 1, diff = 2

        [1,4,5,8]
           i
        [1,2,9]
         j
        [3,6]
         k

         max =4 , min = 1, diff = 3

         [1,4,5,8]
            i
         [1,2,9]
            j
         [3,6]
          k

         max =4 , min = 2, diff = 2



         [1,4,5,8]
            i
         [1,2,9]
              j
         [3,6]
          k

         max =9 , min = 3, diff = 6



         [1,4,5,8]
            i
         [1,2,9]
              j
         [3,6]
            k

         max =9 , min = 4, diff = 5



         [1,4,5,8]
              i
         [1,2,9]
              j
         [3,6]
            k

         max =9 , min = 5, diff = 4

         [1,4,5,8]
                i
         [1,2,9]
              j
         [3,6]
            k

         max =9 , min = 6, diff = 3

        [1,2,3,4]

     */

    public int minSumOfAbsoluteDiff(int[] a1, int[] a2, int[] a3) {
        int i = 0;
        int j = 0;
        int k = 0;
        int min = Integer.MAX_VALUE;
        int max = 0;
        int diff = Integer.MAX_VALUE;
        while (i < a1.length && j < a2.length && k < a3.length) {
            max = Math.max(a1[i], Math.max(a2[j], a3[k]));
            min = Math.min(a1[i], Math.min(a2[j], a3[k]));
            diff = Math.min(diff, Math.abs(max - min));
            if (a1[i] < a2[j] && a1[i] < a3[k]) {
                i++;
            } else if (a2[j] < a1[i] && a2[j] < a3[k]) {
                j++;
            }
        }
        return diff;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int res = main.minSumOfAbsoluteDiff(new int[] {1,4,5,8}, new int[]{1,2,9}, new int[] {3,6});
        System.out.println(res);
    }
}

/*
    0. count pair sum to target, no dup
    [1,2,3,4,5]

    public int countPairToTargetNoDup(int[] array, int target) {
        int i = 0;
        int j = array.length - 1;
        int count = 0;
        while (i < j) {
            int sum = array[i] + array[j];
            if (sum == target) {
                count++;
                i++;
                j--;
            } else if (sum < target) {
                i++;
            } else {
                j--;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int res = main.countPairToTargetNoDup(new int[] {1,2,3,4,5}, 6);
        System.out.println(res);
    }
 */

/*
    1. with duplicate, but don't count duplicate pairs
    [1,1,3,3]

    public int countPairToTargetDup(int[] array, int target) {
        int i = 0;
        int j = array.length - 1;
        int count = 0;
        while (i < j) {
            int sum = array[i] + array[j];
            if (sum == target) {
                count++;
                while (i + 1 < j && array[i] == array[i + 1]) {
                    i++;
                }
                i++;
            } else if (sum < target) {
                i++;
            } else {
                j--;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int res = main.countPairToTargetDup(new int[] {1,1,3,3}, 4);
        System.out.println(res);
    }
 */

/*
    2. count all pairs sum to target, with duplicate elements

    [1,1,2,2,2,3,3]

    public int countAllPairToTargetDup(int[] array, int target) {
        int i = 0;
        int j = array.length - 1;
        int count = 0;
        while (i < j) {
            int sum = array[i] + array[j];
            if (sum == target) {
                //1. handle array[i] = array[j]
                if (array[i] == array[j]) {
                    count += (j - i + 1) * (j - i)/2;
                    break;
                }

                //2. handle array[i] != array[j]
                //mode i to next distince position, count dup elements
                int count1 = 1;
                while (i + 1 < j && array[i] == array[i + 1]) {
                    i++;
                    count1++;
                }

                // mode j to next distinct position, count dup elements
                int count2 = 1;
                while (j -1 > i && array[j] == array[j - 1]) {
                    count2++;
                    j--;
                }

                count += count1 * count2;
                i++;
                j--;
            } else if (sum < target) {
                i++;
            } else {
                j--;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int res = main.countAllPairToTargetDup(new int[] {1,1,2,2,2,3,3}, 4);
        System.out.println(res);
    }
 */

/*
    2.1 Unsorted array #of pares sum to target
    [1,3,1,3] target 4
    index pare [0,1][0,3][2,3][2,3]

    public int countPairsUnsorted(int[] array, int target) {
        Map<Integer, Integer> map = new HashMap<>();

        int count = 0;
        for (int i = 0; i < array.length; i++) {
            count += map.getOrDefault(target - array[i], 0);
            map.put(array[i], map.getOrDefault(array[i], 0) + 1);
        }
        return count;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int res = main.countPairsUnsorted(new int[] {1,3,1,3}, 4);
        System.out.println(res);
    }
 */

/*
    Q 2.1 find if we can construct triangle from input array

    This question equal to:
    find if there is any index tuple, such that i < j < k and array[i] + array[j] > array[k]

    public boolean constructTriangle(int[] array) {
        for (int k = array.length -1; k >= 0; k--) {
            int i = k - 2;
            int j = k - 1;
            if (array[i] + array[j] > array[k]) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Main main = new Main();
        boolean res = main.constructTriangle(new int[] {1,2,3,4});
        System.out.println(res);
    }
 */

/*
    Q 2.2 How many triangles can we get form the array, no dup elements

    [1,2,3,4,5]
      i    j k

    public int howManyTriangleFromArray(int[] array) {
        int count = 0;
        for (int k = array.length - 1; k >= 2; k--) {
            int i = 0;
            int j = k - 1;
            while (i < j) {
                int sum = array[i] + array[j];
                if (sum <= array[k]) {
                    i++;
                } else {
                    count += (j - i); //remaining elements [i, j) all satisfy
                    j--;
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int res = main.howManyTriangleFromArray(new int[] {1,2,3,4});
        System.out.println(res);
    }
 */

/*
    Q3.1  2-diff how many pairs diff = target, no dup

        [1,2,3,5]
         i
               j

    public int howManyPairsDiffEqualTargetSortedArray(int[] array, int target) {
        int i = 0;
        int j = 1;
        int count = 0;
        while (j < array.length) {
            if (array[j] - array[i] == target) {
                count++;
                j++;
            } else if (array[j] - array[i] > target) {
                i++;
            } else {
                j++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int res = main.howManyPairsDiffEqualTargetSortedArray(new int[] {1,2,3,5}, 2);
        System.out.println(res);
    }
 */

/*
    Q 3.1 2-diff how many pairs diff > target, no dup

         0 1 2 3
        [1,2,3,5]
         i
           j

    => find the left most i, array[j] - array[i] <= target

    public int howManyPairsDiffLargerThanTargetSortedArray(int[] array, int target) {
        int i = 0;
        int j = 1;
        int count = 0;
        while (j < array.length) {
            if (array[j] - array[i] <= target) {
                count += i;
                j++;
            } else {
                i++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int res = main.howManyPairsDiffLargerThanTargetSortedArray(new int[] {1,2,3,5}, 2);
        System.out.println(res);
    }
 */

/*
    3.2 2-diff, how many pair diff == target, unsorted, dup

         0 1 2 3
        [1,3,1,3]
               j
        [0,1][0,3][1,2][2,3]

    for each j, do we have i < j such that array[j] - array[j] == target or -target

    public int howManyPairsDiffEqualTargetUnSorted(int[] array, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        int count = 0;
        for (int j = 0; j < array.length; j++) {
            count += map.getOrDefault(array[j] - target, 0);
            count += map.getOrDefault(array[j] + target, 0);
            map.put(array[j], map.getOrDefault(array[j], 0) + 1);
        }
        return count;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int res = main.howManyPairsDiffEqualTargetUnSorted(new int[] {1,3,1,3}, 2);
        System.out.println(res);
    }
 */

/*
    3.2 2-diff, how many pair diff == target, sorted, dup

    [1,1,3,3]

    public int howManyPairsDiffEqualTargetSorted(int[] array, int target) {
        int i = 0;
        int j = 1;
        int count = 0;
        while (j < array.length) {
             // stop at the leftmost element of the group of identical elements
            if (array[j] - array[i] == target) {
                int count1 = 1;
                while (i + 1 < j && array[i] == array[i + 1]) {
                    i++;
                    count1++;
                }

                int count2 = 1;
                while (j + 1 < array.length && array[j] == array[j + 1]) {
                    j++;
                    count2++;
                }

                count += count1 * count2;
                j++;
            } else if (array[j] - array[i] < target) {
                j++;
            } else {
                i++;
            }
        }
        return count;
    }
 */

/*
    Q4. Given sorted array, find number of subsets satisfying
    max(subset) - min(subset) <= target, where target >= 0

         0 1 2 3 4
        [1,3,5,6,8]
                 max (j)
             i
       if max = 8
           # of subsets {5,6}


    public int howManySubsetsDiffMaxMinLessThenTarget(int[] array, int target) {
        int i = 0;
        int j = 0;
        int count = 0;
        while (j < array.length) {
            if (array[j] - array[i] > target) {
                i++;
            } else {
                // 2^(right - left) - 1 number of subsets exclude max value, e.g 8, subset(5,6), 8 must present
                count += (1 << (j - i)) - 1;
                j++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int res = main.howManySubsetsDiffMaxMinLessThenTarget(new int[] {1,3,5,6,8}, 4);
        System.out.println(res);
    }
 */

/*
    Extend 2 pointer to k pointers

    Q 1.1  3 sorted array   a[i] + b[i] + c[i] = target

        a1  [1,3,5,6,8]
             i
        a2  [1,2,3,4,5]
                     j
        a3  [1,2,3]
                 k

     public boolean sumIn3SortedArrayToTarget(int[] a1, int[] a2, int[] a3, int target) {
        for (int k = a3.length - 1; k >= 0; k--) {
            int sum = target - a3[k];
            int i = 0;
            int j = a2.length - 1;
            while (i < a1.length && j >= 0) {
                if (a1[i] + a2[j] == sum) {
                    return true;
                } else if (a1[i] + a2[j] < sum) {
                    i++;
                } else {
                    j--;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Main main = new Main();
        boolean res = main.sumIn3SortedArrayToTarget(new int[] {1,3,5,6,8}, new int[]{1,2,3,4,5}, new int[] {1,2,3}, 7);
        System.out.println(res);
    }
 */

/*
    谁小移谁

    Q1 Two sorted array, get intersection/ union / difference

        a1  [1,2,3]
             i
        a2  [2,4]
             j
     intersection: [2]
     union: [1,2,3,4]
     diff:  [1,3,4]

    public List<List<Integer>> intersectionUnionDiffTwoSortedArray(int[] a1, int[] a2) {
        List<Integer> intersection = new ArrayList<>();
        List<Integer> union = new ArrayList<>();
        List<Integer> diff = new ArrayList<>();
        int i = 0;
        int j = 0;

        while (i < a1.length && j < a2.length) {
            if (a1[i] == a2[j]) {
                intersection.add(a1[i]);
                union.add(a1[i]);
                i++;
                j++;
            } else if (a1[i] < a2[j]) {
                union.add(a1[i]);
                diff.add(a1[1]);
                i++;
            } else {
                union.add(a2[j]);
                diff.add(a2[j]);
                j++;
            }
        }
        while (i < a1.length) {
            union.add(a1[i]);
            diff.add(a1[i]);
            i++;
        }

        while (j < a2.length) {
            union.add(a2[j]);
            diff.add(a2[j]);
            j++;
        }

        List<List<Integer>> result = new ArrayList<>();
        result.add(union);
        result.add(diff);
        result.add(intersection);
        return result;
    }

    public static void main(String[] args) {
        Main main = new Main();
        List<List<Integer>> res = main.intersectionUnionDiffTwoSortedArray(new int[] {1,2,3}, new int[]{2,4});
        System.out.println(res.toString());
    }
 */

/*
    Q2 3 sorted array, x from A, y from B, z from C, what is min of |x - y| + |y- z| + |z - x|

    assume x(min) < y < z(max)
    |x - y| + |y- z| + |z - x| = y - x + z - y + z - x = 2 * (z- x) = 2 *(max(x,yz), min(x,y,z))
    <=> what is min range of (x, y, z) max(x,y,z) - min(x,y,z) is min

        [1,4,5,8]
         i
        [1,2,9]
         j
        [3,6]
         k

         max =3 , min = 1, diff = 2

        [1,4,5,8]
           i
        [1,2,9]
         j
        [3,6]
         k

         max =4 , min = 1, diff = 3

         [1,4,5,8]
            i
         [1,2,9]
            j
         [3,6]
          k

         max =4 , min = 2, diff = 2



         [1,4,5,8]
            i
         [1,2,9]
              j
         [3,6]
          k

         max =9 , min = 3, diff = 6



         [1,4,5,8]
            i
         [1,2,9]
              j
         [3,6]
            k

         max =9 , min = 4, diff = 5



         [1,4,5,8]
              i
         [1,2,9]
              j
         [3,6]
            k

         max =9 , min = 5, diff = 4

         [1,4,5,8]
                i
         [1,2,9]
              j
         [3,6]
            k

         max =9 , min = 6, diff = 3

    public int minSumOfAbsoluteDiff(int[] a1, int[] a2, int[] a3) {
        int i = 0;
        int j = 0;
        int k = 0;
        int range = Integer.MAX_VALUE;
        while (i < a1.length && j < a2.length && k < a3.length) {
            int curMax = Math.max(a1[i], Math.max(a2[j], a3[k]));
            int curMin = Math.min(a1[i], Math.min(a2[j], a3[k]));
            range = Math.min(range, curMax - curMin);
            if (a1[i] <= a2[j] && a1[i] <= a3[k]) {
                i++;
            } else if (a2[j] <= a1[i] && a2[j] <= a3[k]) {
                j++;
            } else {
                k++;
            }
        }
        return range;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int res = main.minSumOfAbsoluteDiff(new int[] {1,4,5,8}, new int[]{1,2,9}, new int[] {3,6});
        System.out.println(res);
    }
 */

/*
    Q 2.1 what about k sorted arrays

     class Element {
        int row;
        int col;
        int val;
        Element(int row, int col, int val) {
            this.row = row;
            this.col = col;
            this.val = val;
        }
    }

    public int minSumOfAbsoluteDiff(int[][] arrays) {
        PriorityQueue<Element> minHeap = new PriorityQueue<>(arrays.length, new Comparator<Element>() {
            @Override
            public int compare(Element o1, Element o2) {
                if (o1.val == o2.val) return 0;
                return o1.val > o2.val ? 1 : -1;
            }
        });

        int curMax = 0;
        for (int i = 0; i < arrays.length; i++) {
            minHeap.offer(new Element(i,0, arrays[i][0]));
            curMax = Math.max(curMax, arrays[i][0]);
        }

        int result = Integer.MAX_VALUE;
        while (true) {
            Element el = minHeap.poll();
            int curMin = el.val;
            result = Math.min(result, curMax - curMin);

            if (el.col + 1 < arrays[el.row].length) {
                curMax = Math.max(curMax, arrays[el.row][el.col + 1]); // cur max only be updated by right hand of polled el
                minHeap.offer(new Element(el.row, el.col + 1, arrays[el.row][el.col + 1]));
            }

            if (el.col == arrays[el.row].length - 1) {
                break;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int[][] matrix = new int[][] {
                {1,4,5,8},
                {1,2,9},
                {3,6}
        };
        int res = main.minSumOfAbsoluteDiff(matrix);
        System.out.println(res);
    }
 */