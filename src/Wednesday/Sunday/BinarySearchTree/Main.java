package Wednesday.Sunday.BinarySearchTree;


import Util.TreeNode;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class Main {















    public TreeNode[] partition(TreeNode root, int pivot) {
        TreeNode[] res = new TreeNode[2];
        TreeNode largestT1 = null;
        TreeNode smallestT2 = null;
        while (root != null) {
            if (root.val <= pivot) {
                if (res[0] == null) {
                    res[0] = root;
                } else {
                    smallestT2.right = root;
                }
                smallestT2 = root;
                root = root.right;
            } else {
                if (res[1] == null) {
                    res[1] = root;
                } else {
                    largestT1.left = root;
                }
                largestT1 = root;
                root = root.left;
            }
        }
        if (largestT1 != null) {
            largestT1.left = null;
        }
        if (smallestT2 != null) {
            smallestT2.right = null;
        }
        return res;
    }




    public static void main(String[] args) {
        Main main = new Main();
        TreeNode node = new TreeNode(8);
        node.left = new TreeNode(4);
        node.right = new TreeNode(15);
        node.left.left = new TreeNode(1);
        node.left.right = new TreeNode(7);
        node.right.left = new TreeNode(9);
        node.right.right = new TreeNode(20);
        node.right.left.right = new TreeNode(12);
        TreeNode[] res = main.partition(node, 10);
        System.out.print("");
    }
}

/*
    class 1
 */

/*
    Q3 largest number smaller than target

    public TreeNode largestSmallerThanTarget(TreeNode root, int target) {
        if (root == null) return null;
        TreeNode result = null;
        while (root != null) {
            if (root.val < target) {
                result = root;
                root = root.right;
            } else { // root.val >= target
                root = root.left;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Main main = new Main();
        TreeNode node = new TreeNode(7);
        node.left = new TreeNode(4);
        node.right = new TreeNode(15);
        node.right.left = new TreeNode(9);
        node.right.right = new TreeNode(20);
        node.right.left.left = new TreeNode(8);
        TreeNode res = main.largestSmallerThanTarget(node, 9);
        System.out.print(res.val);
    }
 */

/*
    Q4  2 closest to target, 2 nodes  with value closest to target
            8
           /\
          4  15
         /\   /\
        1 7  9  20
             \
             12
    public TreeNode[] twoClosestToTarget(TreeNode root, int target) {
        TreeNode closest = getClosest(root, target);
        TreeNode largestSmaller = getLargestSmaller(root, target);
        TreeNode smallestLarger = getSmallestLarger(root, target);
        TreeNode[] res = new TreeNode[2];
        res[0] = largestSmaller;
        res[1] = smallestLarger;
        return res;
    }

    public TreeNode getLargestSmaller(TreeNode root, int target) {
        TreeNode result = null;
        while (root != null) {
            if (root.val < target) {
                result = root;
                root = root.right;
            } else {
                root = root.left;
            }
        }
        return result;
    }

    public TreeNode getSmallestLarger(TreeNode root, int target) {
        TreeNode result = null;
        while (root != null) {
            if (root.val > target) {
                result = root;
                root = root.left;
            } else {
                root = root.right;
            }
        }
        return result;
    }

    public TreeNode getClosest(TreeNode root, int target) {
        TreeNode result = root;
        int diff = Integer.MAX_VALUE;
        while (root != null) {
            if (Math.abs(target - root.val) < diff) {
                result = root;
                diff = Math.abs(target - root.val);
            }
            if (root.val == target) return root;
            else if (root.val < target) {
                root = root.right;
            } else {
                root=  root.left;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Main main = new Main();
        TreeNode node = new TreeNode(8);
        node.left = new TreeNode(4);
        node.right = new TreeNode(15);
        node.left.left = new TreeNode(1);
        node.left.right = new TreeNode(7);
        node.right.left = new TreeNode(9);
        node.right.right = new TreeNode(20);
        node.right.left.right = new TreeNode(12);
        TreeNode[] res = main.twoClosestToTarget(node, 9);
        System.out.print(res[0].val);
        System.out.print(res[1].val);
    }
 */

/*
    Q5      k closest to target

    time: O(logn + k)   amortized
    space: O(logn)

    class AscIterator implements Iterator<TreeNode> {
        private Deque<TreeNode> stack;

        public AscIterator(TreeNode root, double target) {
            stack = new LinkedList<>();
            while (root != null) {
                if (root.val == target) { // stop whenever meet target
                    stack.push(root);
                    break;
                } else if (root.val > target) {
                    stack.push(root);
                    root = root.left;
                } else {
                    root = root.right;
                }
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public TreeNode next() {
            if (hasNext()) {
                TreeNode node = stack.pop();
                TreeNode result = node;
                node = node.right;
                while (node != null) {
                    stack.push(node);
                    node = node.left;
                }
                return result;
            }
            return null;
        }
    }

    class DesIterator implements Iterator<TreeNode> {
        Deque<TreeNode> stack;

        DesIterator(TreeNode root, double target) {
            stack = new LinkedList<>();
            while (root != null) {
                if (root.val == target) { // stop whenever meet target
                    stack.push(root);
                    break;
                } else if (root.val < target) {
                    stack.push(root);
                    root = root.right;
                } else {
                    root = root.left;
                }
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public TreeNode next() {
            if (hasNext()) {
                TreeNode node = stack.pop();
                TreeNode result = node;
                node = node.left;
                while (node != null) {
                    stack.push(node);
                    node = node.right;
                }
                return result;
            }
            return null;
        }
    }

    public List<Integer> closestKValues(TreeNode root, double target, int k) {
        AscIterator asc = new AscIterator(root, target);
        DesIterator des = new DesIterator(root, target);
        TreeNode smallestLarger = asc.next();
        TreeNode largestSmaller = des.next();
        //b.c both ASC and DES stop at target, their begin value are the same if target in the BST
        //in this case, just move forward either side of iterator
        if (smallestLarger != null && largestSmaller != null && smallestLarger.val == largestSmaller.val) {
            largestSmaller = des.next();
        }
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            if (smallestLarger == null ||
                    (largestSmaller != null && Math.abs(smallestLarger.val - target) > Math.abs(largestSmaller.val - target))) {
                res.add(largestSmaller.val);
                largestSmaller = des.next();
            } else if (smallestLarger != null){
                res.add(smallestLarger.val);
                smallestLarger = asc.next();
            }
        }
        return res;
    }


    public static void main(String[] args) {
        Main main = new Main();

        TreeNode node = new TreeNode(3);
        node.left = new TreeNode(2);
        node.right = new TreeNode(4);
        node.left.left = new TreeNode(1);

        List<Integer> res = main.closestKValues(node, 2.0000, 3);
        System.out.print(res);
    }
 */

/*
    Q 8  2 sum to target in BST

    Space: O(1)
    if using set, it's O(n) space

    public boolean twoSumBST(TreeNode root, int target) {
        TreeNode s = getSmallest(root);
        TreeNode l = getLargest(root);
        while (s.val != l.val) {
            if (s.val + l.val == target) return true;
            else if (s.val + l.val < target) {
                s = getSmallestLarger(root, s.val);
            } else {
                l = getLargestSmaller(root, l.val);
            }
        }
        return false;
    }

    public TreeNode getSmallestLarger(TreeNode root, int target) {
        TreeNode res = null;
        while (root != null) {
            if (root.val > target) {
                res = root;
                root = root.left;
            } else {
                root = root.right;
            }
        }
        return res;
    }

    public TreeNode getLargestSmaller(TreeNode root, int target) {
        TreeNode res = null;
        while (root != null) {
            if (root.val < target) {
                res = root;
                root = root.right;
            } else {
                root = root.left;
            }
        }
        return res;
    }

    public TreeNode getSmallest(TreeNode root) {
        while (root.left != null) root = root.left;
        return root;
    }

    public TreeNode getLargest(TreeNode root) {
        while (root.right != null) root= root.right;
        return root;
    }

    public static void main(String[] args) {
        Main main = new Main();
        TreeNode node = new TreeNode(8);
        node.left = new TreeNode(4);
        node.right = new TreeNode(15);
        node.left.left = new TreeNode(1);
        node.left.right = new TreeNode(7);
        node.right.left = new TreeNode(9);
        node.right.right = new TreeNode(20);
        node.right.left.right = new TreeNode(12);
        boolean res = main.twoSumBST(node, 17);
        System.out.print(res);
    }
 */

/*
    Q9 find most freq integer in BST

    class State {
        public TreeNode maxValue;
        public TreeNode prevValue;
        public int maxFreq = 0;
        public int prevCount = 0;
        State() {
            maxFreq = 0;
            prevCount = 0;
        }
    }

    public int findMostFreqElement(TreeNode root) {
        State res = new State();
        helpFn(root, res);
        return res.maxFreq;
    }

    public void helpFn(TreeNode root, State state) {
        if (root == null) return;
        helpFn(root.left, state);
        if (state.prevValue == null || state.prevValue.val != root.val) {
            state.prevValue = root;
            state.prevCount = 1;
        } else {
            state.prevCount++;
        }

        if (state.prevCount > state.maxFreq) {
            state.maxValue = root;
            state.maxFreq =state.prevCount;
        }
        helpFn(root.right, state);
    }

    public static void main(String[] args) {
        Main main = new Main();
        TreeNode node = new TreeNode(8);
        node.left = new TreeNode(4);
        node.right = new TreeNode(15);
        node.left.left = new TreeNode(1);
        node.left.right = new TreeNode(7);
        node.right.left = new TreeNode(8);
        node.right.right = new TreeNode(20);
        node.right.left.right = new TreeNode(8);
        int res = main.findMostFreqElement(node);
        System.out.print(res);
    }
 */

/*
    Q10  recover BST , two nodes swapped, LC99

    TreeNode first = null;
    TreeNode second = null;
    TreeNode prev =  new TreeNode(Integer.MIN_VALUE);
    public void recoverBST(TreeNode node) {
        helpFn(node);
        int temp = first.val;
        first.val = second.val;
        second.val = temp;
    }

    public void helpFn(TreeNode root) {
        if (root == null) return;

        helpFn(root.left);
        if (first == null && prev.val > root.val) {
            first = prev;
        }

        if (first != null && prev.val > root.val) {
            second = root;
        }
        prev = root;
        helpFn(root.right);
    }

    public static void main(String[] args) {
        Main main = new Main();
        TreeNode node = new TreeNode(8);
        node.left = new TreeNode(4);
        node.right = new TreeNode(15);
        node.left.left = new TreeNode(1);
        node.left.right = new TreeNode(7);
        node.right.left = new TreeNode(12);
        node.right.right = new TreeNode(20);
        node.right.left.right = new TreeNode(9);
        main.recoverBST(node);
        System.out.print("");
    }
 */

/*
    Q 11 split partition BST by pivot

    time: O(logn)
    space: O(1)

     public TreeNode[] partition(TreeNode root, int pivot) {
        TreeNode[] res = new TreeNode[2];
        TreeNode largestT1 = null;
        TreeNode smallestT2 = null;
        while (root != null) {
            if (root.val <= pivot) {
                if (res[0] == null) {
                    res[0] = root;
                } else {
                    smallestT2.right = root;
                }
                smallestT2 = root;
                root = root.right;
            } else {
                if (res[1] == null) {
                    res[1] = root;
                } else {
                    largestT1.left = root;
                }
                largestT1 = root;
                root = root.left;
            }
        }
        if (largestT1 != null) {
            largestT1.left = null;
        }
        if (smallestT2 != null) {
            smallestT2.right = null;
        }
        return res;
    }

    public static void main(String[] args) {
        Main main = new Main();
        TreeNode node = new TreeNode(8);
        node.left = new TreeNode(4);
        node.right = new TreeNode(15);
        node.left.left = new TreeNode(1);
        node.left.right = new TreeNode(7);
        node.right.left = new TreeNode(9);
        node.right.right = new TreeNode(20);
        node.right.left.right = new TreeNode(12);
        TreeNode[] res = main.partition(node, 10);
        System.out.print("");
    }
 */