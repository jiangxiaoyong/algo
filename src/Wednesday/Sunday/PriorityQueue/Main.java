package Wednesday.Sunday.PriorityQueue;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Main {

















    public void sortArray(int[] array, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (int i = 0; i <= k; i++) {
            minHeap.offer(array[i]);
        }

        for (int i = 0; i < array.length; i++) {
            array[i] = minHeap.poll();
            if (i + k + 1 < array.length) {
                minHeap.offer(array[i + k + 1]);
            }
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.sortArray(new int[] {3,2,1,5,4,6}, 2);
    }
}

/*
    Class 1

    k sorted array, what is smallest range

    {1,5,11}
    {2,4,20}
    {6,15}

    [4,5,6] si the smallest range, 2

    class Cell {
        int row;
        int col;
        int val;
        public Cell(int row, int col, int val) {
            this.row = row;
            this.col = col;
            this.val = val;
        }
    }

    public int smallestRangeKSortedArray(int[][] array) {
        int minRange = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        PriorityQueue<Cell> minHeap = new PriorityQueue<>(array.length, new Comparator<Cell>() {
            @Override
            public int compare(Cell c1, Cell c2) {
                return Integer.compare(c1.val, c2.val);
            }
        });

        for (int i = 0; i < array.length; i++) {
            minHeap.offer(new Cell(i, 0, array[i][0]));
            max = Math.max(max, array[i][0]);
        }

        while (minHeap.size() == array.length) {
            Cell cur = minHeap.poll();
            minRange = Math.min(minRange, max - cur.val);
            if (cur.col + 1 < array[cur.row].length) {
                minHeap.offer(new Cell(cur.row, cur.col + 1, array[cur.row][cur.col + 1]));
                max = Math.max(max, array[cur.row][cur.col + 1]);
            }
        }
        return minRange;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int[][] input = new int[][] {
                {1,5,11},
                {2,4,20},
                {6,15},
        };
        int res = main.smallestRangeKSortedArray(input);
        System.out.print(res);
    }
 */

/*
    Q2 sort array, (each elements has at most k distance to its sorted position)

    A = [3,2,1,5,4,6]   k = 2

    public void sortArray(int[] array, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (int i = 0; i <= k; i++) {
            minHeap.offer(array[i]);
        }

        for (int i = 0; i < array.length; i++) {
            array[i] = minHeap.poll();
            if (i + k + 1 < array.length) {
                minHeap.offer(array[i + k + 1]);
            }
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.sortArray(new int[] {3,2,1,5,4,6}, 2);
    }
 */
