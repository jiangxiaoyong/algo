package Wednesday.Sunday.QuickSelect;

public class Main {












/*
            [0,1,6,7,8]
             0 1 2 3 4
             5 4 3 2 1
 */

    public int hIndexSorted(int[] citations) {
        int left = 0;
        int right = citations.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (citations[mid] < citations.length - mid) {
                left = mid + 1; //original left = mid;
            } else if (citations[mid] > citations.length - mid){
                right = mid -1;
            } else {
                return citations[mid];
            }
        }
        return citations.length - left;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int res = main.hIndexSorted(new int[] {0,1,6,7,8});
        System.out.println(res);
    }
}

/*
    0. kth small element

    // Time: n + n/2 + n/4 +... 1 = O(n), worst case O(n^2)
    public int kthSmallestElement(int[] array, int k) {
        int left = 0;
        int right = array.length - 1;

        while (left <= right) {
            int pivot = partition(array, left, right); //O(n)

            if (pivot == k - 1) {
                return pivot;
            } else if (pivot < k - 1) {
                left = pivot + 1;
            } else {
                right = pivot - 1;
            }
        }
        return -1;
    }

    public int partition(int[] array, int left, int right) {
        int pivot = getPivot(left, right);

        swap(array, pivot, right);
        int leftPt = left;
        int rightPt = right - 1;

        while (leftPt <= rightPt) {
            if (array[leftPt] < array[pivot]) {
                leftPt++;
            } else if (array[rightPt] >= array[pivot]) {
                rightPt--;
            } else {
                swap(array, leftPt++, rightPt--);
            }
        }
        swap(array, leftPt, right);
        return leftPt;
    }

    public int getPivot(int left, int right) {
        return left + (int)(Math.random() * (right - left + 1));
    }

    public void swap(int[] array, int left, int right) {
        int temp = array[left];
        array[left] = array[right];
        array[right] = temp;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int res = main.kthSmallestElement(new int[] {1,3,2,4}, 2);
        System.out.println(res);
    }
 */

/*
    1.  H-index (with non-negative integer, unsorted)
         0 1 2 3 4
        [3,0,6,1,5]

        count = [1 1 0 1 0 2]       bucket sort
                 0 1 2 3 4 5
                       3 2 2
    public int hIndexUnsorted(int[] citations) {
        int[] buckets = new int[citations.length + 1];
        int count = 0;

        for (int i = 0; i < citations.length; i++) {
            if (citations[i] >= citations.length) {
                buckets[buckets.length - 1]++;
            } else {
                buckets[citations[i]]++;
            }
        }

        for (int i = buckets.length - 1; i >= 0; i--) {
            count += buckets[i];
            if (count >= i) {
                return i;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int res = main.hIndexUnsorted(new int[] {3,0,1,7,5});
        System.out.println(res);
    }
 */

/*
    1.1 H index with negative

    // negative number x:
    // # of elements >= x, guaranteed to  >= 1
    public int hIndexSorted(int[] citations) {
        int largestNegative = 0;
        for (int c : citations) {
            if (c < largestNegative) {
                largestNegative = c;
            }
        }

        if (largestNegative < 0) return largestNegative;

        int[] bucket = new int[citations.length + 1];
        for (int c : citations) {
            if (c >= citations.length) {
                bucket[citations.length]++;
            } else {
                bucket[c]++;
            }
        }

        int count = 0;
        for (int i = bucket.length - 1;  i>=0; i--) {
            count += bucket[i];
            if (count >= i) {
                return count;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int res = main.hIndexSorted(new int[] {3,0,-1,7,-5});
        System.out.println(res);
    }
 */
/*
    1.2 H index2 if array is sorted

    public int hIndexSorted(int[] citations) {
       int left = 0;
       int right = citations.length - 1;

       while (left <= right) {
           int mid = left + (right - left) / 2;
           if (citations[mid] < citations.length - mid) {
               left = mid + 1; //original left = mid;
           } else if (citations[mid] > citations.length - mid){
               right = mid -1;
           } else {
               return citations[mid];
           }
       }
       return citations.length - left;
   }

    public static void main(String[] args) {
        Main main = new Main();
        int res = main.hIndexSorted(new int[] {0,1,6,7,8});
        System.out.println(res);
    }
 */

/*
    1.3 Hindex 3
    - O(1) space
    - Can not change input

    input [0 ,3, ,1, 9, 10,11,12,8]
                 f(x): 8 7 6 6 5 5 5 5 5
        search reange: 0 1 2 3 4 5 6 7 8

   public int hIndex(int[] citations) {
       if (citations == null || citations.length == 0) return -1;

        int left = 0;
        int right = citations.length;

        // find the largest x such that f(x) >= x, the result is 5
        int targetIndex = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int count = 0;
            for (int i = 0; i < citations.length; i++) {
                if (citations[i] >= mid) {
                    count++;
                }
            }

            if (count < mid) {
                right = mid - 1;
            } else if (count > mid) {
                left = mid + 1;
            } else {
                targetIndex = mid;
                break;
            }
        }

        // step 2: iterate the original array, find the largest <= 5
        if (targetIndex == -1) return -1;
        int res = Integer.MIN_VALUE;
        for (int i =0; i < citations.length; i++) {
            if (citations[i] <= targetIndex) {
                res = Math.max(res, citations[i]);
            }
        }
        return res;
   }

    public static void main(String[] args) {
        Main main = new Main();
        int res = main.hIndex(new int[] {0,3,1,9,10,11,12,8});
        System.out.println(res);
    }
 */