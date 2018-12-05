package Wednesday.Sunday.Augmented_BST;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Main {


































    static class Racer {
        int start;
        int end;
        Racer(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    static class TreeNode {
        int val;
        int numLeft;
        TreeNode left;
        TreeNode right;
        boolean enabled;
        TreeNode(int val, int numLeft) {
            this.numLeft = numLeft;
            this.val = val;
            this.enabled = false;
        }
    }

    public TreeNode construct(int[] array, int left, int right) {
        if (left > right) return null;
        int mid = left + (right - left) / 2;
        TreeNode root = new TreeNode(array[mid], 0);
        root.left = construct(array, left, mid - 1);
        root.right = construct(array, mid + 1, right);
        return root;
    }

    public int insertAndGetNumSmaller(TreeNode root, int target) {
        int result = 0;
        while (root.val != target) {
            if (root.val < target) {
                result += root.numLeft + (root.enabled ? 1 : 0);
                root = root.right;
            } else {
                root.numLeft++;
                root = root.left;
            }
        }
        root.enabled = true;
        result += root.numLeft;
        return result;
    }

    public int[] numberOfRacer(List<Racer> racers) {
        racers.sort(new Comparator<Racer>() {
            @Override
            public int compare(Racer o1, Racer o2) {
                if (o1.start == o2.start) return 0;
                return o1.start > o2.start ? 1 : -1;
            }
        });

        int[] finishTime = new int[racers.size()];
        for (int i = 0; i < racers.size(); i++) {
            finishTime[i] = racers.get(i).end;
        }

        int[] res = new int[racers.size()];
        int[] copy = Arrays.copyOf(finishTime, finishTime.length);
        Arrays.sort(copy);
        TreeNode node = construct(copy, 0, finishTime.length - 1);
        for (int i = finishTime.length - 1; i >= 0; i--) {
            res[i] = insertAndGetNumSmaller(node, finishTime[i]);
        }
        return res;
    }


    public TreeNode buildTree(int[] array, int left, int right) {
        if (left > right) return null;
        int mid = left + (right - left) / 2;
        TreeNode node = new TreeNode(array[mid], 0);
        node.left= buildTree(array, left, mid - 1);
        node.right = buildTree(array, mid + 1, right);
        return node;
    }

    public static void main(String[] args) {
        Main main = new Main();
        Racer r1 = new Racer(0,100);
        Racer r2 = new Racer(50,80);
        Racer r3 = new Racer(40,60);
        Racer r4 = new Racer(20, 70);
        List<Racer> list = new ArrayList<>();
        list.add(r1);list.add(r2);list.add(r3);list.add(r4);
        int[] res = main.numberOfRacer(list);
        for (int i : res) {
            System.out.print(i);
        }
    }
}

/*
    class 1
 */

/*
    Q1 kth smallest node

    O(logn)

    public TreeNode kthSmallestNode(TreeNode root, int k) {
        while (root != null) {
            if (root.numLeft == k -1) { // moving down left minus root itself, that is one, so k - 1
                return root;
            } else if (root.numLeft >= k) { // should decrement root itself as one node
                root = root.left;
            } else {
                k = k - root.numLeft - 1; // decrement num left and root itself
                root = root.right;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Main main = new Main();
        TreeNode node = new TreeNode(8, 4);
        node.left = new TreeNode(4, 1);
        node.right = new TreeNode(15,2);
        node.left.left = new TreeNode(1,0);
        node.left.right = new TreeNode(6,0);
        node.left.right.right = new TreeNode(7, 0);
        node.right.left = new TreeNode(9,0);
        node.right.left.right = new TreeNode(12,0);
        TreeNode res = main.kthSmallestNode(node, 7);
        System.out.print(res.val);
    }
 */

/*
    Q2 # of elements < target

    O(logn)

    public int howManySmallerThanTarget(TreeNode root, int target) {
        int result = 0;
        while (root != null) {
            if (root.val == target) {
                return result + root.numLeft;
            } else if (root.val < target) {
                result += root.numLeft + 1;
                root = root.right;
            } else {
                root = root.left;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Main main = new Main();
        TreeNode node = new TreeNode(8, 4);
        node.left = new TreeNode(4, 1);
        node.right = new TreeNode(15,2);
        node.left.left = new TreeNode(1,0);
        node.left.right = new TreeNode(6,0);
        node.left.right.right = new TreeNode(7, 0);
        node.right.left = new TreeNode(9,0);
        node.right.left.right = new TreeNode(12,0);
        int res = main.howManySmallerThanTarget(node, 8);
        System.out.print(res);
    }
 */

/*
    Q3 insert node

    public void insertNode(TreeNode root, int target) {
        int result = 0;
        while (root != null) {
            if (root.val < target) {
                if (root.right == null) {
                    root.right = new TreeNode(target, 0);
                    return;
                } else {
                    root = root.right;
                }
            } else {
                root.numLeft++;
                if (root.left == null) {
                    root.left = new TreeNode(target, 0);
                    return;
                } else {
                    root = root.left;
                }
            }
        }
    }
 */

/*
    Q 3.1 insert and get num of smaller nodes

     public void insertAndGetNumSmaller(TreeNode root, int target) {
        int result = 0;
        while (root != null) {
            if (root.val < target) {
                result += root.numLeft + 1;
                if (root.right == null) {
                    root.right = new TreeNode(target, 0);
                    return;
                } else {
                    root = root.right;
                }
            } else {
                root.numLeft++;
                if (root.left == null) {
                    root.left = new TreeNode(target, 0);
                    return;
                } else {
                    root = root.left;
                }
            }
        }
    }
 */

/*
    Q4 get count array

    static class TreeNode {
        int val;
        int numLeft;
        TreeNode left;
        TreeNode right;
        boolean enabled;
        TreeNode(int val, int numLeft) {
            this.numLeft = numLeft;
            this.val = val;
            this.enabled = false;
        }
    }

    public TreeNode construct(int[] array, int left, int right) {
        if (left > right) return null;
        int mid = left + (right - left) / 2;
        TreeNode root = new TreeNode(array[mid], 0);
        root.left = construct(array, left, mid - 1);
        root.right = construct(array, mid + 1, right);
        return root;
    }

    public int[] numberOfSmallerElements(int[] array) {
        int[] res = new int[array.length];
        int[] copy = Arrays.copyOf(array, array.length);
        Arrays.sort(copy);
        TreeNode node = construct(copy, 0, array.length - 1);
        for (int i = array.length - 1; i >= 0; i--) {
            res[i] = insertAndGetNumSmaller(node, array[i]);
        }
        return res;
    }

    public int insertAndGetNumSmaller(TreeNode root, int target) {
        int result = 0;
        while (root.val != target) {
            if (root.val < target) {
                result += root.numLeft + (root.enabled ? 1 : 0);
                root = root.right;
            } else {
                root.numLeft++;
                root = root.left;
            }
        }
        root.enabled = true;
        result += root.numLeft; //important
        return result;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int[] res = main.numberOfSmallerElements(new int[]{4,1,3,2});
        for (int i : res) {
            System.out.print(i);
        }
    }
 */

/*
    Q5   restore array form count array

    static class TreeNode {
        int val;
        int numLeft;
        TreeNode left;
        TreeNode right;
        boolean deleted;
        TreeNode(int val, int numLeft) {
            this.numLeft = numLeft;
            this.val = val;
            this.deleted = false;
        }
    }

    public TreeNode construct(int[] array, int left, int right) {
        if (left > right) return null;
        int mid = left + (right - left) / 2;
        TreeNode root = new TreeNode(array[mid], 0);
        root.numLeft = mid - left;
        root.left = construct(array, left, mid - 1);
        root.right = construct(array, mid + 1, right);
        return root;
    }

    public int[] numberOfSmallerElements(int[] array) {
        int[] res = new int[array.length];
        int[] sorted = new int[array.length];
        int start = array[0] + 1; //start from 0 element in the array, which is biggest
        for (int i = sorted.length - 1; i >= 0; i--) {
            sorted[i] = start--;
        }
        TreeNode node = construct(sorted, 0, sorted.length - 1);
        for (int i = 0; i < array.length; i++) {
            res[i] = removeKth(node, array[i] + 1); // return the kth node and delete it
        }
        return res;
    }

    public int removeKth(TreeNode root, int k) {
        while (root.numLeft != k - 1 || root.deleted) {
            if (root.numLeft >= k) {
                root.numLeft--;
                root = root.left;
            } else {
                k = k - root.numLeft - (root.deleted ? 0 : 1);
                root = root.right;
            }
        }
        root.deleted = true;
        return root.val;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int[] res = main.numberOfSmallerElements(new int[]{3,0,1,0});
        for (int i : res) {
            System.out.print(i);
        }
    }
 */

/*
    Q1  if there exists triple such that i < j < k && array[i] < array[j] < array[k]
    LC 334

    public boolean ifThereExistsTriplet(int[] array) {
        int first = Integer.MAX_VALUE;
        int second = Integer.MAX_VALUE;
        for (int n : array) {
            if (n <= first) {
                first = n;
            } else if (n < second) {
                second = n;
            } else {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Main main = new Main();
        boolean res = main.ifThereExistsTriplet(new int[]{1,2,3,4,0});
        System.out.print(res);
    }
 */

/*
    Q2 Racer problem

    racer1 = (0,100), score = 3
    racer2 = (50,80), score = 0
    racer3 = (40, 60), score = 0
    racer4 = (20, 70), score = 1

    sort by start time:
    racer1(0,100), racer4(20,70), racer3(40,60), racer2(50,80)
           [100             70,             60          80]
    equivalent to question of count array

          70 ()
        /       \
      60()       80 ()
                  \
                  100 ()

    static class Racer {
        int start;
        int end;
        Racer(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    static class TreeNode {
        int val;
        int numLeft;
        TreeNode left;
        TreeNode right;
        boolean enabled;
        TreeNode(int val, int numLeft) {
            this.numLeft = numLeft;
            this.val = val;
            this.enabled = false;
        }
    }

    public TreeNode construct(int[] array, int left, int right) {
        if (left > right) return null;
        int mid = left + (right - left) / 2;
        TreeNode root = new TreeNode(array[mid], 0);
        root.left = construct(array, left, mid - 1);
        root.right = construct(array, mid + 1, right);
        return root;
    }

    public int[] numberOfSmallerElements(int[] array) {
        int[] res = new int[array.length];
        int[] copy = Arrays.copyOf(array, array.length);
        Arrays.sort(copy);
        TreeNode node = construct(copy, 0, array.length - 1);
        for (int i = array.length - 1; i >= 0; i--) {
            res[i] = insertAndGetNumSmaller(node, array[i]);
        }
        return res;
    }

    public int insertAndGetNumSmaller(TreeNode root, int target) {
        int result = 0;
        while (root.val != target) {
            if (root.val < target) {
                result += root.numLeft + (root.enabled ? 1 : 0);
                root = root.right;
            } else {
                root.numLeft++;
                root = root.left;
            }
        }
        root.enabled = true;
        result += root.numLeft;
        return result;
    }

    public int[] numberOfRacer(List<Racer> racers) {
        racers.sort(new Comparator<Racer>() {
            @Override
            public int compare(Racer o1, Racer o2) {
                if (o1.start == o2.start) return 0;
                return o1.start > o2.start ? 1 : -1;
            }
        });

        int[] finishTime = new int[racers.size()];
        for (int i = 0; i < racers.size(); i++) {
            finishTime[i] = racers.get(i).end;
        }

        int[] res = new int[racers.size()];
        int[] copy = Arrays.copyOf(finishTime, finishTime.length);
        Arrays.sort(copy);
        TreeNode node = construct(copy, 0, finishTime.length - 1);
        for (int i = finishTime.length - 1; i >= 0; i--) {
            res[i] = insertAndGetNumSmaller(node, finishTime[i]);
        }
        return res;
    }

    public static void main(String[] args) {
        Main main = new Main();
        Racer r1 = new Racer(0,100);
        Racer r2 = new Racer(50,80);
        Racer r3 = new Racer(40,60);
        Racer r4 = new Racer(20, 70);
        List<Racer> list = new ArrayList<>();
        list.add(r1);list.add(r2);list.add(r3);list.add(r4);
        int[] res = main.numberOfRacer(list);
        for (int i : res) {
            System.out.print(i);
        }
    }
 */