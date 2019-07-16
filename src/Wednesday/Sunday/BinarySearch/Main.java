package Wednesday.Sunday.BinarySearch;


import java.util.*;

public class Main {


    /*
        dp[i][j] represent min largest sum at index i with j sub array

        induction:
            dp[i][j] = min(max(dp[k][j- 1], subArraySum(k + 1, i))),   0 <= k < i


        dp[i][j] = min(max(dp[i][k - 1]))


         0 1 2  3 4
        [7,2,5,10,8]
        base case:
            dp[0][j] = invalid;

        m = 1:
            dp[0][1] = 7
            dp[1][1] = 9
            dp[2][1] = 14
            dp[3][1] = 24
            dp[4][1] = 32

            dp[i][1] = array[0] + array[i];


        m = 2;
            dp[1][2] =  7
                        min(
                            max(dp[0][1], subArraySum(1, 1)) = max(7,2) = 7)

            dp[2][2] =  7
                        min(
                            max(dp[0][1], subArraySum(1, 2)) = max(7, 7) = 7
                            max(dp[1][1], sbuArraySum(2, 2)) = max(9, 5) = 9)

            dp[3][2] =  14
                        min(
                            max(dp[0][1], subArraySum(1, 3)) = max(7, 17) = 17
                            max(dp[1][1], sbuArraySum(2, 3)) = max(9, 15) = 15
                            max(dp[2][1], subArraySum(3, 3)) = max(14, 10) = 14)

            dp[4][2] =  18
                        min(
                            max(dp[0][1], subArraySum(1, 4)) = max(7, 25) = 25
                            max(dp[1][1], subArraySum(2, 4)) = max(9, 27) = 27
                            max(dp[2][1], subArraySum(3, 4)) = max(14, 18) = 18
                            max(dp[3][1], subArraySum(4, 4))= max(24, 8) = 24)
        m = 3:
            dp[1][3] =  invalid
                        min(
                            max(dp[0][2], sbuArraySum(1, 1)) = max(7, 2) = 7)
            dp[2][3] =  7
                        min(
                            //max(dp[0][2], subArraySum(1, 2) = max(INF, ))
                            max(dp[1][2], subArraySum(2, 2) = max(7, 5)) = 7)
            dp[3][3] =  10
                        min(
                           // max(dp[0][2], subArraySum(1, 3) = max(INF,) = INF)
                            max(dp[1][2], subArraySUm(2, 3) = max(7, 15) = 15)
                            max(dp[2][2], subArraySum(3, 3)) = max(7, 10) = 10)
            dp[4][3] =  14
                        min(
                            //max(dp[0][2], subArray(1, 4)) = max(INF, ) = IFN
                            max(dp[1][2], subArray(2, 4)) = max(7, 27) = 27
                            max(dp[2][2], subArray(3, 4) = max(7, 18)) = 18
                            max(dp[3][2], subArray(4, 4)) =max(14, 8) = 14)


     */

    /*
         0 1 2
        [1,4,4]
        base case:
            dp[0][j] = INF;
            dp[0][2] =

        m = 1:
            dp[0][1] = 1
            dp[1][1] = 5
            dp[2][1] = 9


            dp[i][1] = array[0] + array[i];


        m = 2;
            dp[1][2] =  4
                        min(
                            max(dp[0][1], subArraySum(1, 1)) = max(1,4) = 4)

            dp[2][2] =  8
                        min(
                            max(dp[0][1], subArraySum(1, 2)) = max(1, 8) = 8
                            max(dp[1][1], sbuArraySum(2, 2)) = max(5, 4) = 5)


        m = 3:
            dp[1][3] =  invalid
                        min(
                            max(dp[0][2], sbuArraySum(1, 1)) = max(7, 2) = 7)
            dp[2][3] =  4
                        min(
                            //max(dp[0][2], subArraySum(1, 2) = max(INF, ))
                            max(dp[1][2], subArraySum(2, 2) = max(4, 4)) = 4)



        a/b = c

        16/3
    0.9 * 0.9 = 0.81

      num < 1:  left : num, right 1
      num > 1:  left : 1, right : num

     */
    public double sqrtInteger(double num) {
        if (num == 1) return 1;

        double left = num > 1 ? 1 : num;
        double right = num > 1 ? num : 1;

        double delta = 0.1;
        while (true) {
            double mid = left + (right - left) / 2;
            if (Math.abs(num - mid * mid) / num < 0.1) {
                return mid;
            }
            if (mid * mid < num) {
                left = mid;
            } else {
                right = mid;
            }
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        System.out.println(main.sqrtInteger(2));
    }
}


/**
 *  Binary search class 1
 *
 *  summary:
 *
 *      rule of choosing of left < right - 1, right < right, right <= right
 *      if left = mid + 1 => left < right
 *      if left = mid => left < right - 1
 *
 *      find smallest larger than target, -> left can be mid + 1， right = mid
 *      find largest smaller then target, -> left should be mid, right = mid - 1
 *
 */

/*
    Q.0 find the inserting position in a sorted array
        = find largest smaller element comparing to target

    // This problem actually if to find largest SMALLER element comparing to target, means left can't mid + 1
    // like:  1 ,2,3,3,6  about to find number 2, if target is 3
    public int findInsertPosition(int[] array, int target) {
        int left = 0;
        int right = array.length;

        while (left < right - 1) {
            int mid = left + (right - left) / 2;

            if (target <= array[mid]) { // == assign to here b.c we find largest smaller than target
                right = mid - 1;
            } else {
                //  1 2 3, insert 3, left must begin from mid in order not miss 2
                //  l m r
                left = mid;
            }
        }
        if (target <= array[left]) return left;
        if (target <= array[right]) return right;
        return right + 1;
    }

     size = 1
            1
            2
            3

     size = 2
            target > array[right]
            1 2
            2 2

            target < array[right]
            2 3
            2 4

            target <= array[left]
            3 3
            3 4
            4 5


    public static void main(String[] args) {
        Main main = new Main();
        System.out.println(main.findInsertPosition(new int[] {1,2,3,3,3,6}, 4));
    }
*/

/*
    1.1 find min position in rotated sorted array
    [4,5,1,2,3] return index 2

    public int findRotatedSortedMinPosition(int[] array) {
        int left = 0;
        int right = array.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (array[mid] <= array[right]) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    public static void main(String[] args) {
        Main main = new Main();
        System.out.println(main.findRotatedSortedMinPosition(new int[] {4,5,1,2,3}));
    }
 */

/*
    1.2 find min position in rotated sorted array (possible duplicate elements)

    [3,1,3,3,3]
    [3,3,3,1,3]

    public int findRotatedSortedMinPositionDup(int[] array) {
        int left = 0;
        int right = array.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (array[mid] < array[right]) {
                right = mid;
            } else if (array[mid] > array[right]) {
                left = mid + 1;
            } else {
                right--;
            }
        }
        return left;
    }

    public static void main(String[] args) {
        Main main = new Main();
        System.out.println(main.findRotatedSortedMinPositionDup(new int[] {3,1,3,3,3}));
    }
 */

/*
    2 find if target in rotated sorted array (no dup)

    public int findIfTargetInRotatedArray(int[] array, int target) {
        int left = 0;
        int right = array.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (array[mid] == target) return mid;
            if (array[mid] < array[right]) {
                if (target >= array[mid] && target <= array[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            } else {
                if (target >= array[left] && target <= array[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
        }
        return -1;
    }
 */

/*
    3   find target in first increasing then decreasing array

    public int findTargetIncreasingDecreasingArray(int[] array, int target) {
        int pivot = getPivot(array);
        int increasePart = bsIncrease(array, target,0, pivot);
        int decreasePart = bsDecrease(array, target, pivot + 1, array.length - 1);
        if (increasePart != -1) return increasePart;
        if (decreasePart != -1) return decreasePart;
        return -1;
    }

    public int bsIncrease(int[] array, int target, int left, int right) {
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (array[mid] == target) return mid;
            if (target < array[mid]) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }

    public int bsDecrease(int[] array, int target, int left, int right) {
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (array[mid] == target) return mid;
            if (target > array[mid]) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }

    public int getPivot(int[] array) {
        int left = 0;
        int right = array.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (array[mid] < array[mid + 1]) { //ensure index not out of bound
                left = mid + 1;
            } else {
                right = mid; //self could be the pivot
            }
        }
        return left;
    }
    public static void main(String[] args) {
        Main main = new Main();
        System.out.println(main.findTargetIncreasingDecreasingArray(new int[] {1,3,6,7,4,2,0}, 1));
    }
 */

/*
    4 find the position of any local min (no dup)

    public int findPositionAnyLocalMin(int[] array) {
        int left = 0;
        int right = array.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (array[mid] > array[mid + 1]) { // xxxx 5 4 xxxx
                left = mid + 1;
            } else { // xxxx 4 5 xxx
                right = mid;
            }
        }
        return left;
    }
 */

/*
    5 find element in sorted array the appears more then 1/4 times

    public int findElementAppearsMorethanOneOverFourth(int[] array) {
        int len = array.length - 1;
        int candi1 = array[len / 4];
        int candi2 = array[len / 2];
        int candi3 = array[len * 3 / 4];

        int range1 = lastOccurrence(array, candi1) - firstOccurrence(array, candi1) + 1;
        int range2 = lastOccurrence(array, candi2) - firstOccurrence(array, candi2) + 1;
        int range3 = lastOccurrence(array, candi3) - firstOccurrence(array, candi3) + 1;

        if (range1 > array.length / 4) {
            return candi1;
        } else if (range2 > array.length / 4) {
            return candi2;
        } else if (range3 > array.length / 4) {
            return candi3;
        } else {
            return -1;
        }
    }

    public int firstOccurrence(int[] array, int target) {
        int left = 0;
        int right = array.length - 1;

        while (left < right - 1) {
            int mid = left + (right - left) / 2;
            if (target <= array[mid]) {
                right = mid;
            } else {
                left = mid;
            }
        }
        if (array[left] == target) return left;
        if (array[right] == target) return right;
        return -1;
    }

    public int lastOccurrence(int[] array, int target) {
        int left = 0;
        int right = array.length - 1;
        while (left < right - 1) {
            int mid = left + (right - left) / 2;
            if (target >= array[mid]) {
                left = mid;
            } else {
                right = mid;
            }
        }
        if (array[left] == target) return left;
        if (array[right] == target) return right;
        return -1;
    }

    public static void main(String[] args) {
        Main main = new Main();
        System.out.println(main.findElementAppearsMorethanOneOverFourth(new int[] {1,1,2,3,3,3,4,5}));
    }
 */

/*
    7 get sorted array of quadratic function
    [-3, 0,2,3] -> x^2 + 2x + 1 -> [4, 1, 9, 16] -> [1,4,9,16]

    public List<Integer> sortResultsFromQuadraticFunction(int[] array) {
        // x^2 + 2x + 1
        // pivot is :  -b/2a = -1

        //[-3, 0, 2, 3]
        int xPivot = -1;
        int closestIndex = getClosest(array, -1);

        List<Integer> result = new ArrayList<>();

        int left = closestIndex;
        int right = closestIndex + 1;
        for (int i = 0; i < array.length; i++) {
            // 1. right point out of bound
            // 2. right and left not out of bound, and left is closer
            if (right >= array.length || left >= 0 && Math.abs(array[left] - xPivot) < Math.abs(array[right] - xPivot)) {
                result.add(quadratic(array[left--]));
            } else {
                result.add(quadratic(array[right++]));
            }
        }
        return result;
    }

    public int quadratic(int num) {
        return (int)Math.pow(num, 2.0) + 2*num + 1;
    }

    public int getClosest(int[] array, int taregt) {
        int left = 0;
        int right = array.length - 1;

        while (left < right - 1) {
            int mid = left + (right - left) / 2;
            if (taregt == array[mid]) return mid;
            if (taregt < array[mid]) {
                right = mid;
            } else {
                left = mid;
            }
        }
        if (taregt - array[left] <= array[right] - taregt) {
            return left;
        } else {
            return right;
        }
    }

    public static void main(String[] args) {
        Main main = new Main();

        System.out.print(main.sortResultsFromQuadraticFunction(new int[] {-3,0,2,3}));
    }
 */

/*
    8. Find the ring that has most points inside

    static class Point {
        double x;
        double y;
        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    public int findRingOfMostPoints(int[] radius, List<Point> points) {
        List<Double> distances = getDistance(points);

        int max = 0;
        int[] pointsInRange = new int[radius.length];
        int ringHasMostPoints = -1;
        for (Double dis : distances) {
            int ring = searchSmallestLargerThanDistance(radius, dis);
            pointsInRange[ring]++;
            if (pointsInRange[ring] > max) {
                max = pointsInRange[ring];
                ringHasMostPoints = ring;
            }
        }
        return ringHasMostPoints;
    }

    public int searchSmallestLargerThanDistance(int[] radius, double target) {
        int left = 0;
        int right = radius.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (target >= radius[mid]) { // if target == mid, the result should mid + 1
                left = mid + 1;  //find smallest LARGER than target means left can be mid + 1
            } else {
                right = mid;
            }
        }
        return left;
    }

    public List<Double> getDistance(List<Point> points) {
        List<Double> distance = new ArrayList<>();
        for (Point p : points) {
            Double dis = Math.sqrt(Math.pow(p.x, 2.0) + Math.pow(p.y, 2.0));
            distance.add(dis);
        }
        Collections.sort(distance);
        return distance;
    }

    public static void main(String[] args) {
        Main main = new Main();
        List<Point> points = new ArrayList<>();
        points.add(new Point(0, 0.1));
        points.add(new Point(0, 0.2));

        points.add(new Point(0, 1.1));
        points.add(new Point(0.1, 1.2));
        points.add(new Point(0.2, 1.3));
        points.add(new Point(0.3, -1.4));

        points.add(new Point(0.1, 2.1));
        System.out.println(main.findRingOfMostPoints(new int[] {1,2,3}, points));
    }
 */

/**
 *  Binary search class 2
 *
 */

/*
    1. sqrt()  find its sqrt  integer x, where x is the largest integer that x * x <= num

    public int sqrtInteger(int num) {
        if (num < 4) return 1;
        int left = 1;
        int right = num / 2;

        while (left < right - 1) {
            int mid = left + (right - left) / 2;
            if (mid == num) {
                return mid;
            } else if (mid * mid < num) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        if (right * right <= num) return right;
        return left;
    }

    public static void main(String[] args) {
        Main main = new Main();
        System.out.println(main.sqrtInteger(5));
    }
 */

/*
    1.2 sqrt() for double num

    num = 2  x = 1.414
    num = 0.8 x - 0.899

    num == 1; return1 ;
    num < 1 [num, 1]
    num > 1 [1, num]

    public double sqrtInteger(double num) {
        if (num == 1) return 1;
        double left = 1;
        double right = num;
        if (num < 1) {
            left = num;
            right = 1;
        }

        double epsilon = 0.01;
        while (true) {
            double mid = left + (right - left) / 2;
            double diff = (num - mid * mid) /num;
            if (Math.abs(diff) < epsilon) {
                return mid;
            } else if (diff > 0) {
                left = mid;
            } else {
                right = mid;
            }
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        System.out.println(main.sqrtInteger(2));
    }
 */

/*
    2.  Divide two positive integer a/b without using "/", return int value

    public int divide(int a, int b) {
        int left = 0;
        int right = a;

        while (left < right - 1) {
            int mid = left + (right - left) / 2;

            if (b * mid == a){
                return mid;
            } else if (b * mid < a) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        if (right * b <= a) return right;
        return left;
    }

    public static void main(String[] args) {
        Main main = new Main();
        System.out.println(main.divide(16, 3));
    }

    Optimization:   16/3 = 5
    3 * 1 = 3
    3 * 2 = 6
    3 * 4 = 12
    3 * 8 = 25
    time complexity: O(log(a/b))
     public int divide(int a, int b) {
        int bits = getBits(a, b);

        int res = 0;
        int mask = 1 << (bits - 1);
        while (b <= a) {
            if (b * mask < a) {
                res |= mask;
                a = a - b * mask;
            }
            mask >>>= 1;
        }
        return res;
    }

    public int getBits(int a, int b) {
        int count = 0;
        int val = b;
        while (val <= a) {
            val <<= 1;
            count++;
        }
        return count;
    }

    public static void main(String[] args) {
        Main main = new Main();
        System.out.println(main.divide(16, 3));
    }
 */

/**
 *      Class 3
 */

/*
    1. Random generator
    static class Generator {
        Random random;
        int[] ranges;
        Character[] symbols;
        int rangeSum;

        Generator(Character[] symbols, int[] weights) {
            if (symbols == null || symbols.length == 0 || weights == null || weights.length == 0) {
                new RuntimeException("not valid");
            }
            this.symbols = symbols;
            this.random = new Random();
            this.ranges = new int[symbols.length];
            this.rangeSum = initialize(weights);
        }

        private int initialize(int[] weights) {
            int sum = 0;
            for (int i = 0; i < weights.length; i++) {
                sum += weights[i];
                this.ranges[i] = sum;
            }
            return sum;
        }

        // find smallest element that is larger than
        public int getSymbols() {
            int target = this.random.nextInt(rangeSum);

            int left = 0;
            int right = ranges.length - 1;

            while (left < right) {
                int mid = left + (right - left) / 2;

                if (target < ranges[mid]) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            }
            return this.symbols[left];
        }
 */

/*
    2.  random generator exclude elements in the given range

    Given [0, 2,   5,       9]
          [  1, 3,4,  6,7,8]

    ranges =   [1,3,6] -> symbol
    length =   [1,2,3] -> weight
    prefix =   [1,3,6] ->

     static class Generator {
        Random random;
        int[] nums;
        int[] lens;
        int[] prefixSum;
        int totalExclude;

        Generator(int[] input) {
            random = new Random();

            int len = input.length;
            this.nums = new int[len - 1];
            this.lens = new int[len - 1];
            this.prefixSum = new int[len - 1];

            for (int i = 0; i < input.length - 1; i++) {
                int first = input[i];
                int second = input[i + 1];
                nums[i] = first + 1;
                lens[i] = second - first - 1;
                prefixSum[i] = i == 0 ? lens[i] : prefixSum[i - 1] + lens[i];

                if (i == input.length - 2) {
                    totalExclude = prefixSum[i];
                }
            }
        }

        public int getRandom() {
            int target = this.random.nextInt(totalExclude);

            int left = 0;
            int right = prefixSum.length - 1;

            // find smallest element that is larger than target
            while (left < right) {
                int mid = left + (right - left) / 2;
                if (prefixSum[mid] <= target) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }

            int symbol = nums[left];
            int remain = left > 0 ? target - prefixSum[left - 1] : target;
            int resNum = symbol + remain;
            return resNum;
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        Generator gen = new Generator(new int[] {0, 2, 5, 9});
        System.out.println(gen.getRandom());
    }
 */

/*
    One dimension to two dimension

    1.  local min in two dimension matrix

     public int findLocalMin2DMatrix(int[][] matrix) {
        int rows = matrix.length;

        int top = 0;
        int bottom = rows - 1;

        while (top < bottom) {
            int mid = top + (bottom - top) / 2;
            int minCol = getGlobalMin(matrix[mid]);
            int minVal = matrix[mid][minCol];

            if (matrix[mid+1][minCol] < minVal) {
                top = mid + 1;
            } else {
                bottom = mid;
            }
        }

        int resCol = getGlobalMin(matrix[top]); // find the global min col of result row
        return matrix[top][resCol];
    }

    public int getGlobalMin(int[] array) {
        int min = Integer.MAX_VALUE;
        int col = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] < min) {
                min = array[i];
                col = i;
            }
        }
        return col;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int[][] matrix = new int[][] {
                {1,3,2,5,-1},
                {3,2,1,3,2},
                {4,6,3,1,5},
                {2,-5,-1,-2,-3},
                {0,5,1,-4,1}
        };
        System.out.println(main.findLocalMin2DMatrix(matrix));
    }
 */

/*
    Yong's matrix
    2. Find target in yong's matrix

    public int[] findTargetInYongsMatrix(int[][] matrix, int target) {
        int[] res = new int[2];

        int rows = matrix.length;
        int cols = matrix[0].length;

        int row = 0;
        int col = cols - 1;

        while (row < rows && col >= 0) {
            int cur = matrix[row][col];
            if (cur == target) {
                res[0] = row;
                res[1] = col;
                break;
            } else if (target < cur) {
                col--;
            } else {
                row++;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int[][] matrix = new int[][] {
                {3,5,8,12,15,19},
                {8,10,13,17,20,21},
                {9,11,14,18,25,30},
                {13,15,18,22,26,32},
                {15,16,19,28,30,36}
        };
        int[] res = main.findTargetInYongsMatrix(matrix, 16);
        System.out.println(res[0]);
        System.out.println(res[1]);
    }
 */

/*
    3.  Smallest rectangle covers all the area of black cells

    Solution 1:
    public int smallestRectangleCoverAllBlackCells(int[][] matrix, int starRow, int startCol) {
        int[] top = new int[1];
        top[0] = starRow;
        int[] bottom = new int[1];
        bottom[0] = starRow;
        int[] left = new int[1];
        left[0] = startCol;
        int[] right = new int[1];
        right[0] = startCol;

        DFS(matrix, starRow, startCol, top, bottom, left, right);
        return (bottom[0] - top[0] + 1) * (right[0] - left[0] + 1);
    }

    public void DFS(int[][] matrix, int row, int col, int[]top, int[] bottom, int[] left, int[] right) {
        if (row < 0 || row >= matrix.length || col < 0 || col >= matrix[0].length || matrix[row][col] == 0 || matrix[row][col] == -1) {
            return;
        }

        top[0] = Math.min(top[0], row);
        bottom[0] = Math.max(bottom[0], row);
        left[0] = Math.min(left[0], col);
        right[0] = Math.max(right[0], col);

        matrix[row][col] = -1; // mark visited
        DFS(matrix, row + 1, col, top, bottom, left, right);
        DFS(matrix, row - 1, col, top, bottom, left, right);
        DFS(matrix, row, col + 1, top, bottom, left, right);
        DFS(matrix, row, col - 1, top, bottom, left, right);
    }

    public static void main(String[] args) {
        Main main = new Main();
        int[][] matrix = new int[][] {
                {0,0,0,0,0,0},
                {0,0,1,0,0,0},
                {0,1,1,1,1,0},
                {0,1,1,1,1,0},
                {0,0,1,1,0,0},
                {0,0,0,1,0,0}
        };
        System.out.print(main.smallestRectangleCoverAllBlackCells(matrix,3,3));
    }

    solution 2: binary search on hour direction
    public int smallestRectangleCoverAllBlackCells(int[][] matrix, int startRow, int startCol) {

        int[] top = new int[1];
        top[0] = startRow;
        int[] bottom = new int[1];
        bottom[0] = startRow;
        int[] left = new int[1];
        left[0] = startCol;
        int[] right = new int[1];
        right[0] = startCol;

        binarySearchFirstOccurrence(matrix, startRow, startCol, top, true);
        binarySearchFirstOccurrence(matrix, startRow, startCol, left, false);

        binarySearchLastOccurrence(matrix, startRow, startCol, bottom, true);
        binarySearchLastOccurrence(matrix, startRow, startCol, right, false);

        return (bottom[0] - top[0] + 1) * (right[0] - left[0] + 1);
    }

    public void binarySearchFirstOccurrence(int[][] matrix, int startRow, int startCol, int[] res, boolean vertical) {
        int left = 0;
        int right = vertical ? startRow : startCol;

        while (left < right) {
            int mid = left + (right - left) / 2;
            int val = vertical? findOne(matrix[mid]) : findOne(getColVals(matrix, mid));
            if (val == 1) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        res[0] = left;
    }

    public void binarySearchLastOccurrence(int[][] matrix, int startRow, int startCol, int[] res, boolean vertical) {
        int left = vertical ? startRow : startCol;
        int right = vertical ? matrix.length - 1 : matrix[0].length - 1;

        while (left < right - 1) {
            int mid = left + (right - left) / 2;
            int val = vertical? findOne(matrix[mid]) : findOne(getColVals(matrix, mid));
            if (val == 1) {
                left = mid;
            } else {
                right = mid + 1;
            }
        }
        int lastVal = vertical ? findOne(matrix[right]) : findOne(getColVals(matrix, right));
        if (lastVal == 1) {
            res[0] = right;
        } else {
            res[0] = left;
        }
    }

    public int[] getColVals(int[][] matrix, int col) {
        int[] res = new int[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            res[i] = matrix[i][col];
        }
        return res;
    }

    public int findOne(int[] input) {
        for (int i = 0; i < input.length; i++) {
            if (input[i] == 1) {
                return 1;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int[][] matrix = new int[][] {
                {0,0,0,0,0,0},
                {0,0,1,0,0,0},
                {0,1,1,1,1,0},
                {0,1,1,1,1,0},
                {0,0,1,1,0,0},
                {0,0,0,1,0,0}
        };
        System.out.print(main.smallestRectangleCoverAllBlackCells(matrix,3,3));
    }

 */

/**
 *      Class 4
 */

/*
    2 number of value <= target in yong's matrix

    public int numberOfValueSmallerEqualTarget(int[][] matrix, int target) {
        int row = 0;
        int col = matrix[0].length - 1;

        int num = 0;
        while (row < matrix.length && col >= 0) {
            if (target < matrix[row][col]) {
                col--;
            } else {   //Note: move down when target == matrix[row][col]
                row++;
                num += col + 1;
            }
        }
        return num;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int[][] matrix = new int[][] {
                {3,5,8,12,18,19},
                {8,10,13,17,20,21},
                {9,11,14,18,25,30},
                {13,15,18,22,26,32},
                {15,16,19,28,30,36},
                {20,21,22,30,32,40}
        };
        int res = main.numberOfValueSmallerEqualTarget(matrix, 8);
        System.out.println(res);
    }

    ========================================================================
    number of value > target in yong's matrix

    public int numberOfValueLargerThanTarget(int[][] matrix, int target) {
        int row = 0;
        int col = matrix[0].length - 1;

        int num = 0;
        while (row < matrix.length && col >= 0) {
            if (target < matrix[row][col]) {
                col--;
            } else {
                row++;
                num += (matrix[0].length - 1 - col);
            }
        }
        int remainRows = matrix.length - row;
        num += remainRows * matrix[0].length;
        return num;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int[][] matrix = new int[][] {
                {3,5,8,12,18,19},
                {8,10,13,17,20,21},
                {9,11,14,18,25,30},
                {13,15,18,22,26,32},
                {15,16,19,28,30,36},
                {20,21,22,30,32,40}
        };
        int res = main.numberOfValueLargerThanTarget(matrix, 30);
        System.out.println(res);
    }
 */

/*
    2.1 find rightmost column where all the elements on the left side are all 0

    // maintain res that store the rightmost column for all i rows above
    public int rightMostColumnLeftSideAllZero(int[][] matrix) {
        int res = matrix[0].length - 1;
        int col = matrix[0].length - 1;
        int row = 0;

        while (row < matrix.length && col > 0) {
            if (matrix[row][col] == 1) {
                col--;
            } else {
                res = col;
                row++;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int[][] matrix = new int[][] {
                {0,0,0,0,1,1},
                {0,0,1,1,1,1},
                {0,1,1,1,1,1},
                {0,1,1,1,1,1},
                {0,0,1,1,1,1},
                {0,0,0,1,1,1}
        };
        System.out.print(main.rightMostColumnLeftSideAllZero(matrix));
    }
 */

/*
    5. two sum in tow sorted array

   public boolean twoSumOfTwoArray(int[] a1, int[] a2, int target) {
        int col = a2.length - 1;
        int row = 0;

//            matrix[i][j] = a1[i] + a2[j]
//               0 1  2
//             0 3 5  6
//             1 5 7  8
//             2 8 10 11

        while (row < a1.length && col >= 0) {
            if (a1[row] + a2[col] == target) {
                return true;
            } else if (a1[row] + a2[col] < target) {
                row++;
            } else {
                col--;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Main main = new Main();
        System.out.print(main.twoSumOfTwoArray(new int[]{1,3,6}, new int[] {2,4,5}, 5));
    }
 */

/*
    6 two sum in one array
    public boolean twoSumOfOneArray(int[] a1, int target) {
        int col = a1.length - 1;
        int row = 0;

        // array = [1,3,6,7]
//            matrix[i][j] = a1[i] + a1[j]
//               0 1  2  3
//             0 2 4  7  8
//             1 4 6  9 10
//             2 7 9 12 13
//             3 8 10 13 14
//
        while (row < col) {
            if (a1[row] + a1[col] == target) {
                return true;
            } else if (a1[row] + a1[col] < target) {
                row++;
            } else {
                col--;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Main main = new Main();
        System.out.print(main.twoSumOfOneArray(new int[]{1,3,6, 7}, 5));
    }
 */

/*
    7. two sorted array, pick two elements that is the kth smallest sum

    public int kthSmallestTwoSortedArray(int[] a1, int[]a2, int k) {
        PriorityQueue<Element> minHeap = new PriorityQueue<>(k, new Comparator<Element>() {
            @Override
            public int compare(Element o1, Element o2) {
                if (o1.val == o2.val) {
                    return 0;
                }
                return o1.val > o2.val ? 1 : -1;
            }
        });

        boolean[][] visited = new boolean[a1.length][a2.length];
        int row = 0;
        int col = 0;
        visited[0][0] = true;
        minHeap.offer(new Element(row, col, a1[row] + a2[col]));
        while (k > 1) {
            Element el = minHeap.poll();
            if (el.row + 1 < a1.length && !visited[el.row + 1][el.col]) {
                visited[el.row + 1][el.col] = true;
                minHeap.offer(new Element(el.row + 1, el.col, a1[el.row + 1] + a2[el.col]));
            }

            if (el.col + 1 < a2.length && !visited[el.row][el.col + 1]) {
                visited[el.row][el.col + 1] = true;
                minHeap.offer(new Element(el.row, el.col + 1, a1[el.row]+ a2[el.col + 1]));
            }
            k--;
        }
        return minHeap.poll().val;
    }

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

    public static void main(String[] args) {
        Main main = new Main();
        System.out.print(main.kthSmallestTwoSortedArray(new int[]{1,3,6}, new int[] {2,4,5},4));
    }
 */

/*
    7.1 One sorted array, pick two elements that is kth smallest sum

    public int kthSmallestTwoSortedArray(int[] a1, int k) {
        PriorityQueue<Element> minHeap = new PriorityQueue<>(k, new Comparator<Element>() {
            @Override
            public int compare(Element o1, Element o2) {
                if (o1.val == o2.val) {
                    return 0;
                }
                return o1.val > o2.val ? 1 : -1;
            }
        });

        boolean[][] visited = new boolean[a1.length][a1.length];
        int row = 0;
        int col = 1;
        visited[0][1] = true;
        minHeap.offer(new Element(row, col, a1[row] + a1[col]));
        while (k > 1) {
            Element el = minHeap.poll();

            if (el.row + 1 < a1.length && !visited[el.row + 1][el.col]) {
                Element next = new Element(el.row + 1, el.col, a1[el.row + 1] + a1[el.col]);
                if (next.row < next.col) {
                    visited[el.row + 1][el.col] = true;
                    minHeap.offer(next);
                }
            }

            if (el.col + 1 < a1.length && !visited[el.row][el.col + 1]) {
                Element next = new Element(el.row, el.col + 1, a1[el.row] + a1[el.col + 1]);
                if (next.row < next.col) {
                    visited[el.row][el.col + 1] = true;
                    minHeap.offer(next);
                }
            }

            k--;
        }
        return minHeap.poll().val;
    }

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

    public static void main(String[] args) {
        Main main = new Main();
        System.out.print(main.kthSmallestTwoSortedArray(new int[]{1,3,6,7},4));
    }
 */

/*
    revisit, kth smallest element in n*n matrix, using binary search
    //search range is the from top left to bottom right
    //Translate to find the smallest element which f(x) >= k

    public int kthSmallestInMatrixBinarySearch(int[][] matrix, int k) {
        int left = matrix[0][0];
        int right = matrix[matrix.length - 1][matrix[0].length -1];

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (numSmallerEquals(matrix, mid) < k) { // O(n)
                left = mid + 1;
            } else {
                right = mid; // like search first occurrence, gradually move to left
            }
        }
        return left;
    }

    public int numSmallerEquals(int[][] matrix, int mid) {
        int row = 0;
        int col = matrix[0].length - 1;

        int sum = 0;
        while (row < matrix.length && col >= 0) {
            if (mid < matrix[row][col]) {
                col--;
            } else {
                row++;
                sum += col + 1;
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int[][] matrix = new int[][] {
                {3, 5, 8, 12,15,19},
                {8, 10,13,17,20,21},
                {9, 11,14,18,25,30},
                {13,15,18,22,26,32},
                {15,16,19,28,30,36}
        };

        System.out.print(main.kthSmallestInMatrixBinarySearch(matrix,5));
    }
 */

/*
    split array largest sum


        dp[i][j] represent min largest sum at index i with j sub array

        induction:
            dp[i][j] = min(max(dp[k][j- 1], subArraySum(k + 1, i))),   0 <= k < i

         0 1 2  3 4
        [7,2,5,10,8]
        base case:
            dp[0][j] = invalid;

        m = 1:
            dp[0][1] = 7
            dp[1][1] = 9
            dp[2][1] = 14
            dp[3][1] = 24
            dp[4][1] = 32

            dp[i][1] = array[0] + array[i];


        m = 2;
            dp[1][2] =  7
                        min(
                            max(dp[0][1], subArraySum(1, 1)) = max(7,2) = 7)

            dp[2][2] =  7
                        min(
                            max(dp[0][1], subArraySum(1, 2)) = max(7, 7) = 7
                            max(dp[1][1], sbuArraySum(2, 2)) = max(9, 5) = 9)

            dp[3][2] =  14
                        min(
                            max(dp[0][1], subArraySum(1, 3)) = max(7, 17) = 17
                            max(dp[1][1], sbuArraySum(2, 3)) = max(9, 15) = 15
                            max(dp[2][1], subArraySum(3, 3)) = max(14, 10) = 14)

            dp[4][2] =  18
                        min(
                            max(dp[0][1], subArraySum(1, 4)) = max(7, 25) = 25
                            max(dp[1][1], subArraySum(2, 4)) = max(9, 27) = 27
                            max(dp[2][1], subArraySum(3, 4)) = max(14, 18) = 18
                            max(dp[3][1], subArraySum(4, 4))= max(24, 8) = 24)
        m = 3:
            dp[1][3] =  invalid
                        min(
                            max(dp[0][2], sbuArraySum(1, 1)) = max(7, 2) = 7)
            dp[2][3] =  7
                        min(
                            //max(dp[0][2], subArraySum(1, 2) = max(INF, ))
                            max(dp[1][2], subArraySum(2, 2) = max(7, 5)) = 7)
            dp[3][3] =  10
                        min(
                           // max(dp[0][2], subArraySum(1, 3) = max(INF,) = INF)
                            max(dp[1][2], subArraySUm(2, 3) = max(7, 15) = 15)
                            max(dp[2][2], subArraySum(3, 3)) = max(7, 10) = 10)
            dp[4][3] =  14
                        min(
                            //max(dp[0][2], subArray(1, 4)) = max(INF, ) = IFN
                            max(dp[1][2], subArray(2, 4)) = max(7, 27) = 27
                            max(dp[2][2], subArray(3, 4) = max(7, 18)) = 18
                            max(dp[3][2], subArray(4, 4)) =max(14, 8) = 14)


     =============================================================================


         0 1 2
        [1,4,4]
        base case:
            dp[0][j] = INF;
            dp[0][2] =

        m = 1:
            dp[0][1] = 1
            dp[1][1] = 5
            dp[2][1] = 9


            dp[i][1] = array[0] + array[i];


        m = 2;
            dp[1][2] =  4
                        min(
                            max(dp[0][1], subArraySum(1, 1)) = max(1,4) = 4)

            dp[2][2] =  8
                        min(
                            max(dp[0][1], subArraySum(1, 2)) = max(1, 8) = 8
                            max(dp[1][1], sbuArraySum(2, 2)) = max(5, 4) = 5)


        m = 3:
            dp[1][3] =  invalid
                        min(
                            max(dp[0][2], sbuArraySum(1, 1)) = max(7, 2) = 7)
            dp[2][3] =  4
                        min(
                            //max(dp[0][2], subArraySum(1, 2) = max(INF, ))
                            max(dp[1][2], subArraySum(2, 2) = max(4, 4)) = 4)


    public int splitArrayLargestSum(int[] nums, int m) {
        int[][] dp = new int[nums.length][m + 1];
        dp[0][1] = nums[0];

        for (int i = 0; i < nums.length; i++) {
            dp[i][1] = sum(nums, i);
        }

        for (int j = 2; j <= m; j++) {
            for (int i = j - 1; i < nums.length; i++) {
                dp[i][j] = Integer.MAX_VALUE;
                for (int k = j - 2; k < i; k++) {
                    dp[i][j] = Math.min(dp[i][j], Math.max(dp[k][j-1], subArraySum(nums, k + 1, i)));
                }
            }
        }
        return dp[nums.length - 1][m];
    }

    public int subArraySum(int[] nums, int i, int j) {
        int sum = 0;
        for (int k = i; k <= j; k++) {
            sum+= nums[k];
        }
        return sum;
    }

    public int sum(int[] nums, int end) {
        int res = 0;
        for (int i = 0; i <= end; i++) {
            res += nums[i];
        }
        return res;
    }

    public static void main(String[] args) {
        Main main = new Main();

//        int res = main.splitArrayLargestSum(new int[] {7,2,5,10,8}, 2);
        int res = main.splitArrayLargestSum(new int[] {1,4,4}, 3);

        System.out.println(res);
    }

 */