package Wednesday.Sunday.SegmentTree;


import java.util.*;

public class Main {





















    static class CountSmaller {
        class Node {
            int start, end;
            Node left, right;
            int count;
            Node(int s, int e) {
                this.start = s;
                this.end = e;
                this.count = 0;
            }
        }
        private Node root;
        private int min;
        private int max;
        private int[] nums;
        public CountSmaller(int[] nums) {
            int min = Integer.MAX_VALUE;
            int max = Integer.MIN_VALUE;
            for (int i : nums) {
                min = Math.min(i, min);
                max = Math.max(i, max);
            }
            root = new Node(min, max);
            this.nums = new int[nums.length];
            for (int i = 0; i < nums.length; i++) {
                this.nums[i] = nums[i];
            }
        }

        public List<Integer> countSmaller() {
            List<Integer> res = new ArrayList<>();
            for (int i = nums.length - 1; i >= 0; i--) {
                res.add(query(root, min, nums[i] - 1));
                update(root, nums[i]);
            }
            Collections.reverse(res);
            return res;
        }

        private void update(Node root, int val) {
            if (root.start == root.end) {
                root.count++;
                return;
            }
            int mid = root.start + (root.end - root.start) / 2;
            if (val <= mid) { //go left
                if (root.left == null) {
                    root.left = new Node(root.start, mid);
                }
                update(root.left, val);
            } else {
                if (root.right == null) {
                    root.right = new Node(mid + 1, root.end);
                }
                update(root.right, val);
            }
            root.count++;
        }

        private int query(Node root, int min, int max) {
            if (min > max || root == null) return 0;
            if (min <= root.start && root.end <= max) return root.count;
            int mid = root.start + (root.end - root.start) / 2;
            if (max <= mid) {
                return query(root.left, min, max);
            } else if (mid < min) {
                return query(root.right, min, max);
            } else {
                return query(root.left, min, mid) + query(root.right, mid + 1, max);
            }
        }
    }

    public static void main(String[] args) {
        CountSmaller na = new CountSmaller(new int[] {50,2,200,1});
        List<Integer> res = na.countSmaller();
        System.out.println(res);
    }
}

/*
    class 1
 */

/*
    Q1 range sum query  LC307

    static class NumArray {
        class Node {
            int start, end;
            Node left, right;
            int sum;

            Node(int s, int e, int sum) {
                this.start = s;
                this.end = e;
                this.sum = sum;
            }
        }

        Node root;
        int[] nums;

        public NumArray(int[] nums) {
            if (nums == null || nums.length == 0) return;
            this.nums = new int[nums.length];
            for (int i = 0; i < nums.length; i++) {
                this.nums[i] = nums[i];
            }
            root = buildTree(this.nums, 0, nums.length - 1);
        }

        public Node buildTree(int[] nums, int i, int j) {
            if (i > j) return null;
            if (i == j) return new Node(i, j, nums[i]);
            int mid = i + (j - i) / 2;
            Node left = buildTree(nums, i, mid);
            Node right = buildTree(nums, mid + 1, j);
            Node node = new Node(i, j, left.sum + right.sum);
            node.left = left;
            node.right = right;
            return node;
        }

        public void update(int i, int val) {
            int diff = val - nums[i];
            nums[i] = val;
            updateHelper(root, i, diff);
        }

        private void updateHelper(Node root, int index, int diff) {
            if (root == null || index > root.end || index < root.start) return;
            root.sum += diff;
            if (root.start == root.end) return;
            int mid = root.start + (root.end - root.start) / 2;
            if (index <= mid) {
                updateHelper(root.left, index, diff);
            } else {
                updateHelper(root.right, index, diff);
            }
            // above two if else can be replaced by two lines below
            // updateHelper(root.left, index, diff);
            // updateHelper(root.right, index, diff);
        }

        public int sumRange(int i, int j) {
            if (i > j) return 0;
            return sumRangeHelper(root, i, j);
        }

        private int sumRangeHelper(Node root, int start, int end) {
            if (root == null || start > root.end || end < root.start) return 0;
            if (start <= root.start && end >= root.end) return root.sum;
            int mid = root.start + (root.end - root.start) / 2;
            if (end <= mid) { //go left
                return sumRangeHelper(root.left, start, end);
            } else if (start > mid) { //go right
                return sumRangeHelper(root.right, start, end);
            }
            return sumRangeHelper(root.left, start, mid) + sumRangeHelper(root.right, mid + 1, end);

            //above if else can be removed, b.c the condition checked by first line of this method as well
            //So we can only keep the line below
            //return sumRangeHelper(root.left, start, end) + sumRangeHelper(root.right, start, end);
        }
    }

    public static void main(String[] args) {
        NumArray na = new NumArray(new int[] {1,3,5});
        int res = na.sumRange(0,2);
        na.update(1,2);
        int res1 = na.sumRange(0,2);
        System.out.println(res1);
    }
 */

/*
    Q2  Count array by segment tree

    input: [5,2,6,1]
    output:[2,1,1,0]

     static class CountSmaller {
        class Node {
            int start, end;
            Node left, right;
            int count;
            Node(int s, int e) {
                this.start = s;
                this.end = e;
                this.count = 0;
            }
        }
        private Node root;
        private int min;
        private int max;
        private int[] nums;
        public CountSmaller(int[] nums) {
            int min = Integer.MAX_VALUE;
            int max = Integer.MIN_VALUE;
            for (int i : nums) {
                min = Math.min(i, min);
                max = Math.max(i, max);
            }
            root = new Node(min, max);
            this.nums = new int[nums.length];
            for (int i = 0; i < nums.length; i++) {
                this.nums[i] = nums[i];
            }
        }

        public List<Integer> countSmaller() {
            List<Integer> res = new ArrayList<>();
            for (int i = nums.length - 1; i >= 0; i--) {
                res.add(query(root, min, nums[i] - 1));
                update(root, nums[i]);
            }
            Collections.reverse(res);
            return res;
        }

        private void update(Node root, int val) {
            if (root.start == root.end) {
                root.count++;
                return;
            }
            int mid = root.start + (root.end - root.start) / 2;
            if (val <= mid) { //go left
                if (root.left == null) {
                    root.left = new Node(root.start, mid);
                }
                update(root.left, val);
            } else {
                if (root.right == null) {
                    root.right = new Node(mid + 1, root.end);
                }
                update(root.right, val);
            }
            root.count++;
        }

        private int query(Node root, int min, int max) {
            if (min > max || root == null) return 0;
            if (min <= root.start && root.end <= max) return root.count;
            int mid = root.start + (root.end - root.start) / 2;
            return query(root.left, min, mid) + query(root.right, mid + 1, max);
        }
    }

    public static void main(String[] args) {
        CountSmaller na = new CountSmaller(new int[] {50,2,200,1});
        List<Integer> res = na.countSmaller();
        System.out.println(res);
    }
 */

/*
    class 2
 */

/*
    Q1 2d range sum query   LC 308

Given matrix = [
  [3, 0, 1, 4, 2],
  [5, 6, 3, 2, 1],
  [1, 2, 0, 1, 5],
  [4, 1, 0, 1, 7],
  [1, 0, 3, 0, 5]
]

sumRegion(2, 1, 4, 3) -> 8
update(3, 2, 2)
sumRegion(2, 1, 4, 3) -> 10


    class NumMatrix {
        class Node {
            int row1, row2, col1, col2, sum;
            Node c1, c2, c3, c4;
            Node(int row1, int col1, int row2, int col2) {
                this.row1 = row1;
                this.row2 = row2;
                this.col1 = col1;
                this.col2 = col2;
                this.sum = 0;
            }
        }

        Node root;
        public NumMatrix(int[][] matrix) {
            if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return;
            root = buildTree(matrix, 0, 0, matrix.length - 1, matrix[0].length - 1);
        }

        public Node buildTree(int[][] matrix, int row1, int col1, int row2, int col2) {
            if (row1 > row2 || col1 > col2) return null;
            Node root = new Node(row1, col1, row2, col2);
            if (row1 == row2 && col1 == col2) {
                root.sum = matrix[row1][col1];
                return root;
            }
            int r_mid = row1 + (row2 - row1) / 2;
            int c_mid = col1 + (col2 - col1) / 2;
            root.c1 = buildTree(matrix, row1, col1, r_mid, c_mid);
            root.c2 = buildTree(matrix, row1, c_mid + 1, r_mid, col2);
            root.c3 = buildTree(matrix, r_mid + 1, col1, row2, c_mid);
            root.c4 = buildTree(matrix, r_mid + 1, c_mid + 1, row2, col2);

            root.sum = (root.c1 == null ? 0 : root.c1.sum) +
                    (root.c2 == null ? 0 : root.c2.sum) +
                    (root.c3 == null ? 0 : root.c3.sum) +
                    (root.c4 == null ? 0 : root.c4.sum);
            return root;
        }

        public void update(int row, int col, int val) {
            updateHelper(root, row, col, val);
        }

        private void updateHelper(Node root, int row, int col, int val) {
            if (root == null || row > root.row2 || row < root.row1 || col > root.col2 || col < root.col1) {
                return;
            }
            if (root.row1 == row && root.row2 == row && root.col1 == col && root.col2 == col) {
                root.sum = val;
                return;
            }
            updateHelper(root.c1, row, col, val);
            updateHelper(root.c2, row, col, val);
            updateHelper(root.c3, row, col, val);
            updateHelper(root.c4, row, col, val);

            root.sum = (root.c1 == null ? 0 : root.c1.sum) +
                    (root.c2 == null ? 0 : root.c2.sum) +
                    (root.c3 == null ? 0 : root.c3.sum) +
                    (root.c4 == null ? 0 : root.c4.sum);
        }

        public int sumRegion(int row1, int col1, int row2, int col2) {
            return sumRegionHelper(root, row1, col1, row2, col2);
        }

        private int sumRegionHelper(Node root, int row1, int col1, int row2, int col2) {
            if (root == null || row1 > root.row2 || row2 < root.row1 || col1 > root.col2 || col2 < root.col1) {
                return 0;
            }
            if (row1 <= root.row1 && row2 >= root.row2 && col1 <= root.col1 && col2 >= root.col2) {
                return root.sum;
            }

            return sumRegionHelper(root.c1, row1, col1, row2, col2) +
                    sumRegionHelper(root.c2, row1, col1, row2, col2) +
                    sumRegionHelper(root.c3, row1, col1, row2, col2) +
                    sumRegionHelper(root.c4, row1, col1, row2, col2);
        }
    }

    public static void main(String[] args) {

    }
 */

/*
    Q2 falling square   LC 699

    class Node {
        int start, end, height;
        Node left, right;
        Node(int start, int end, int h) {
            this.start = start;
            this.end = end;
            this.height = h;
        }
    }

    Node root;

    public List<Integer> fallingSquares(int[][] positions) {
        List<Integer> res = new ArrayList<>();
        Map<Integer, Integer> coor = coorCompression(positions);
        root = buildTree(0, coor.size() - 1);
        int heighest = 0;
        for (int[] pos : positions) {
            int L = coor.get(pos[0]);
            int R = coor.get(pos[0] + pos[1] - 1);
            int h = query(L, R) + pos[1];
            update(L, R, h);
            heighest = Math.max(heighest, h);
            res.add(heighest);
        }
        return res;
    }

    private Node buildTree(int start, int end) {
        if (start > end) return null;
        Node node = new Node(start, end, 0);
        if (start == end) return node;
        int mid = start + (end - start) / 2;
        node.left = buildTree(start, mid);
        node.right = buildTree(mid + 1, end);
        return node;
    }

    private int query(int start, int end) {
        return queryHelper(root, start, end);
    }

    private int queryHelper(Node root, int start, int end) {
        if (root == null || start > end || end < root.start || start > root.end) {
            return 0;
        }
        if (start <= root.start && end >= root.end) {
            return root.height;
        }

        int mid = root.start + (root.end - root.start) / 2;
        if (end <= mid) {
            return queryHelper(root.left, start, end);
        } else if (mid < start) {
            return queryHelper(root.right, start, end);
        } else {
            return Math.max(queryHelper(root.left, start, mid), queryHelper(root.right, mid + 1, end));
        }
        //above two if else can be replaced by one line below, b.c it's checked by first line of this method
        //return Math.max(queryHelper(root.left, start, end), queryHelper(root.right, start, end));
    }

    private void update(int start, int end, int h) {
        updateHelper(root, start, end, h);
    }

    private void updateHelper(Node root, int start, int end, int height) {
        if (root == null || end < start || end  < root.start || start > root.end) {
            return;
        }

        root.height = Math.max(root.height, height);
        if (root.start == root.end) return;

        int mid = root.start + (root.end - root.start) / 2;
        if (end <= mid) {
            updateHelper(root.left, start, end, height);
        } else if (start > mid){
            updateHelper(root.right, start, end, height);
        } else {
            updateHelper(root.left, start, mid, height);
            updateHelper(root.right, mid + 1, end, height);
        }
        //above three if else can be replaced by two lines below
//        updateHelper(root.left, start, end, height);
//        updateHelper(root.right, start, end, height);
    }

    private Map<Integer, Integer> coorCompression(int[][] positions) {
        Set<Integer> set =  new HashSet<>();
        for (int[] pos : positions) {
            set.add(pos[0]);
            set.add(pos[0] + pos[1] - 1);
        }
        // [1,2][2,3][6,1] -> set: 1,2,4,6000000
        // <1,0>
        // <2,0>
        // <4,2>
        // <6,3>
        List<Integer> list = new ArrayList<>(set);
        Collections.sort(list);
        Map<Integer, Integer> map = new HashMap<>();
        int rank =0;
        for (int idx : list) {
            map.put(idx, rank++);
        }
        return map;
    }

    public static void main(String[] args) {
        Main main = new Main();
        List<Integer> res = main.fallingSquares(new int[][]{{1,2}, {2,3}, {6,1}});
        System.out.println(res);
    }
 */