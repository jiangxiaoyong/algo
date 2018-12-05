package Wednesday.Sunday.MissingDuplicateCycle;



public class Main {



    /*

    swap from index 0
             0  1  2  3
            [1, 3, 2, 0]
            [3, 1, 2, 0]
            [0, 1, 2, 3]

            0->1
            1->3
            3->0


    swap from index 0:
             0 1 2 3 4
            [1,3,4,0,2]
            [3,1,4,0,2]
            [0,1,4,3,2]

             0 1 2 3 4
            [1,3,4,0,2]
            0 -> 1 -> 3 -> 0

            [1,2,4,4,-3,0]

             0 1 2 3
            [2,3,3,3]

            [1,2,3,2]

        [1,2,3,3,4]
    */


    public static void main(String[] args) {
        Main main = new Main();
    }
}

/*
    0. Missing number

        Given array: [1,4,0,3]

        correct mapping: [0,1,2,3]
        #elements <= x    1 2 3 4

        search range: [0,1,2,3,4]
        f(x) :         1 2 2 3 4
        diff :         1 1 0 0 0
        find leftmost element that number elements smaller equal to it is 0

        Time: O(nlogn), space O(1)

    public int missingNumber(int[] array) {
        int left = 0;
        int right = array.length;

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (numberElementsSmallerEqual(array, mid) > mid) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }

    public int numberElementsSmallerEqual(int[] array, int num) {
        int count = 0;
        for (int i : array) {
            if (i <= num) count++;
        }
        return count;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int res = main.missingNumber(new int[]{1,4,0,3});
        System.out.print(res);
    }
 */

/*
    1. Cyclic swap

    public int[] cyclicSwap(int[] array) {
        for (int i = 0; i < array.length; i++) {
            while (array[i] != i + 1) {
                swap(array, i, array[i] - 1);
            }
        }
        return array;
    }

    public void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int[] res = main.cyclicSwap(new int[]{5,2,3,1,4});
        for (int i : res) {
            System.out.println(i);
        }
    }
 */

/*
    2. Find smallest missing positive integer, possible duplicates

             [6,-2,-1, 0, 1, 4]
              1  2  3  4  5  6
        index 0  1  2  3  4  5

                        [6,-2,-1, 0, 1, 4]
   after cyclic swap:    1        4     6



    public int smallestMissing(int[] array) {
        for (int i = 0; i < array.length; i++) {
            while (array[i] != i + 1 && array[i] > 0 && array[i] <= array.length
                    && array[i] != array[array[i] - 1]) { // [1,2,3,3,-3,0]  can not equal to the num on the target place
                       //avoid dup      //target place              ^ stuck at second 3
                swap(array, i, array[i] - 1);
            }
        }

        for (int i = 0; i < array.length; i++) {
            if (array[i] != i + 1) return i + 1;
        }
        return array.length + 1;
    }

    public void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int res = main.smallestMissing(new int[]{6,-2,-1,0,1,4});
        System.out.print(res);
    }
 */

/*
    3. Given n + 1 array, containing 1 to n, only one is dup, find it

      Given: [3,2,1,4,2]
cyclic swap: [1,2,3,4,2]
                      ^
        duplicate is the last one


    public int findDuplicate(int[] array) {
        for (int i = 0; i < array.length; i++) {
            while (array[i] != i + 1 && array[i] != array[array[i] - 1]) {
                swap(array, i, array[i] - 1);
            }
            if (array[i] != i + 1) return array[i]; //this is dup, little opt
        }

        return array[array.length - 1];
    }

    public void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int res = main.findDuplicate(new int[]{3,2,1,4,2});
        System.out.print(res);
    }
 */

/*
    5   LC287  find duplicate, which could be repeated more than once

        search range [1, n]
        count $ of elements <= mid

        select a number mid and count all the numbers equal to or less then mid
        - if count is more than mid, the search will be [left, mid], otherwise [mid + 1, right]


    public int duplicateRepeatedMoreThanOnce(int[] array) {
        int left = 1;
        int right = array.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;
            int count = numberEqualOrSmaller(array, mid);
            if (count <= mid) {
                left = mid + 1;
            } else {
                // mid is possible answer [2,2,2,3]  or [1,2,2,2]
                right = mid;                               m
            }
        }
        return left;
    }

    public int numberEqualOrSmaller(int[] array, int mid) {
        int count = 0;
        for (int i : array) {
            if (i <= mid) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int res = main.duplicateRepeatedMoreThanOnce(new int[]{2,3,3,3});
        System.out.print(res);
    }
 */

/*
    6.  find number of times of cycle

    swap from index 0
             0  1  2  3
            [1, 3, 2, 0]
            [3, 1, 2, 0]
            [0, 1, 2, 3]

            0->1
            1->3
            3->0

    swap from index 0:
             0 1 2 3 4
            [1,3,4,0,2]
            [3,1,4,0,2]
            [0,1,4,3,2]

             0 1 2 3 4
            [1,3,4,0,2]
            0 -> 1 -> 3 -> 0

    public int duplicateRepeatedMoreThanOnce(int[] array) {
        int lcm = 1;
        for (int i = 0; i < array.length; i++) {
            int cycleSize = 1;
            while (array[i] != i) {
                cycleSize++;
                swap(array, i, array[i]);
            }
            if (cycleSize > 1) {
                lcm = getLcm(lcm, cycleSize);
            }
        }
        return lcm;
    }

    public int getLcm(int a, int b) {
        if (a < b) return getLcm(b, a);
        return (a / gcd(a, b)) * b;
    }

    public int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    public void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int res = main.duplicateRepeatedMoreThanOnce(new int[]{1,3,2,0});
        System.out.print(res);
    }
 */