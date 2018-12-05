package Wednesday.Sunday.Tree;

import Util.ListNode;
import Util.TreeNode;

import java.util.*;

public class Main {




    /*

                 5
                /\
              1    9
            /  \    \
          0     3    11
               /
              2

              r1         r2 
           / \ \ \      /\ \ \
           0 1 0 1     0 1 1 0
           
                      r'
                   / \ \ \
                  0  1  0 0
                  
                        r
                /      \    \     \ 
             /\\\      0     0    /\\\
            0 100                0 100

                    5

                  /    \

                3        8

              /   \        \

            1      4        11

         0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17
         [ a [ b [ c ] [ d ] [  f  ]   ] [  e  ]  ]



    pre-order  A BFGH   CI  DJK E
    post-order   FGHB   IC  JKD  E A
     */


    public TreeNode deleteNodeInRange(TreeNode root, int min, int max) {
        if (root == null) return null;
        if (root.val < min) {
            return deleteNodeInRange(root.right, min, max);
        }
        if (root.val > max) {
            return deleteNodeInRange(root.left, min, max);
        }
        root.left = deleteNodeInRange(root.left, min, max);
        root.right = deleteNodeInRange(root.right, min, max);
        return root;
    }


    public static void main(String[] args) {
        Main main = new Main();
        ListNode node = new ListNode(1);
        node.next = new ListNode(2);
        node.next.next = new ListNode(3);
    }
}

/*
 *          class 1
 */

/*
    1.1 in-order traversal (iterative), with stack

     public List<Integer> inOrderIterative(TreeNode root) {
        List<Integer> result = new LinkedList<>();
        if (root == null) return result;

        Deque<TreeNode> stack = new LinkedList<>();
        pushLeft(root, stack);
        while (!stack.isEmpty()) {
            TreeNode cur = stack.pollLast();
            result.add(cur.key);

            if (cur.right != null) pushLeft(cur.right, stack);
        }
        return result;
    }

    public void pushLeft(TreeNode node, Deque<TreeNode> stack) {
        while (node!= null) {
            stack.offerLast(node);
            node = node.left;
        }
    }


    public static void main(String[] args) {
        Main main = new Main();
        TreeNode node = new TreeNode(1);
        node.left = new TreeNode(2);
        node.right = new TreeNode(3);
        node.left.left = new TreeNode(4);
        node.left.left.right = new TreeNode(7);
        node.right.left = new TreeNode(6);
        node.right.right = new TreeNode(5);

        List<Integer> res = main.inOrderIterative(node);
        System.out.println(res.toString());
    }
 */

/*
    1.1.1 pre-order iterator

    public List<Integer> preOrderIterative(TreeNode root) {
        Deque<TreeNode> stack = new LinkedList<>();
        List<Integer> result = new LinkedList<>();
        stack.offerLast(root);
        while (!stack.isEmpty()) {
            TreeNode cur = stack.pollLast();
            result.add(cur.key);

            if (cur.right != null) stack.offerLast(cur.right);
            if (cur.left != null) stack.offerLast(cur.left);
        }
        return result;
    }

    public static void main(String[] args) {
        Main main = new Main();
        TreeNode node = new TreeNode(5);
        node.left = new TreeNode(3);
        node.right = new TreeNode(8);
        node.left.left = new TreeNode(1);

        List<Integer> res = main.preOrderIterative(node);
        System.out.println(res.toString());
    }
 */

/*
    1.1.2 Post order iterative

    public List<Integer> postOrderIterative(TreeNode root) {
        Deque<TreeNode> stack = new LinkedList<>();
        List<Integer> result = new LinkedList<>();

        stack.offerLast(root);
        while (!stack.isEmpty()) {
            TreeNode cur = stack.pollLast();
            result.add(cur.key);
            if (cur.left != null) stack.offerLast(cur.left);
            if (cur.right != null) stack.offerLast(cur.right);
        }
        Collections.reverse(result);
        return result;
    }

    public static void main(String[] args) {
        Main main = new Main();
        TreeNode node = new TreeNode(5);
        node.left = new TreeNode(3);
        node.right = new TreeNode(8);
        node.left.left = new TreeNode(1);

        List<Integer> res = main.postOrderIterative(node);
        System.out.println(res.toString());
    }
 */

/*
    1.2 In-order traversal binary tree, with parent pointer
    space: O(1)

    public List<Integer> inOrderIterativeParentPointer(TreeNode root) {
        List<Integer> result = new LinkedList<>();
        if (root == null) return result;

        TreeNode cur = findFist(root);
        while (cur != null) {
            result.add(cur.key);
            cur = nextNode(cur);
        }
        return result;
    }

    public TreeNode nextNode(TreeNode cur) {
        if (cur == null) return null;

        // case 1 cur.right != null
        if (cur.right != null) return findFist(cur.right);

        // case 2 cur.right == null
        while (cur.parent != null && cur != cur.parent.left) {
            cur = cur.parent;
        }

        //termination cur.parent == null || node = node.parent.left
        return cur.parent;
    }

    public TreeNode findFist(TreeNode root) {
        if (root == null) return null;
        while (root.left != null) {
            root = root.left;
        }
        return root;
    }


    public static void main(String[] args) {
        Main main = new Main();
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        TreeNode node5 = new TreeNode(5);
        TreeNode node6 = new TreeNode(6);
        TreeNode node7 = new TreeNode(7);
        TreeNode node8 = new TreeNode(8);

        node1.left = node2;
        node1.right = node3;
        node2.parent = node1;
        node3.parent = node1;

        node2.left = node4;
        node2.right = node7;
        node4.parent = node2;
        node7.parent = node2;

        node3.left = node6;
        node3.right = node5;
        node6.parent = node3;
        node5.parent = node3;

        node4.right = node8;
        node8.parent = node4;

        List<Integer> res = main.inOrderIterativeParentPointer(node1);
        System.out.println(res.toString());
    }
 */

/*
    1.2.1 pre-order iterative with parent pointer

    public List<Integer> PreOrderIterativeParentPointer(TreeNode root) {
        List<Integer> result = new LinkedList<>();
        if (root == null) return result;

        TreeNode cur = findFist(root);
        while (cur != null) {
            result.add(cur.key);
            cur = nextNode(cur);
        }
        return result;
    }

    public TreeNode nextNode(TreeNode cur) {
        if (cur == null) return null;

        //case 1 left subtree exists
        if (cur.left != null) return findFist(cur.left);
        //case 2 right subtree exists
        if (cur.right != null) return findFist(cur.right);

        //case 3 both left and right are null
        while (cur.parent != null && (cur == cur.parent.right || cur.parent.right == null)) {
            cur = cur.parent;
        }

        return cur.parent == null ? null : findFist(cur.parent.right);
    }

    public TreeNode findFist(TreeNode root) {
        return root;
    }
 */

/*
    1.2.2 Post-order iterative with parent pointer

    public List<Integer> PostOrderIterativeParentPointer(TreeNode root) {
        List<Integer> result = new LinkedList<>();
        if (root == null) return result;

        TreeNode cur = findFist(root);
        while (cur != null) {
            result.add(cur.key);
            cur = nextNode(cur);
        }
        return result;
    }

    public TreeNode nextNode(TreeNode cur) {
        if (cur == null) return null;

        if (cur.parent != null && cur == cur.parent.left && cur.parent.right != null) {
            return findFist(cur.parent.right);
        }
        return cur.parent;
    }

    public TreeNode findFist(TreeNode cur) {
        if (cur == null) return null;
        while (cur.left != null || cur.right != null) {
            if (cur.left != null) {
                cur = cur.left;
            } else {
                cur = cur.right;
            }
        }
        return cur;
    }


    public static void main(String[] args) {
        Main main = new Main();
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        TreeNode node5 = new TreeNode(5);
        TreeNode node6 = new TreeNode(6);
        TreeNode node7 = new TreeNode(7);
        TreeNode node8 = new TreeNode(8);

        node1.left = node2;
        node1.right = node3;
        node2.parent = node1;
        node3.parent = node1;

        node2.left = node4;
        node2.right = node7;
        node4.parent = node2;
        node7.parent = node2;

        node3.left = node6;
        node3.right = node5;
        node6.parent = node3;
        node5.parent = node3;

        node4.right = node8;
        node8.parent = node4;

        List<Integer> res = main.PostOrderIterativeParentPointer(node1);
        System.out.println(res.toString());
    }
 */

/*
    1.3 In order iterator without parent pointer

    public List<Integer> inOrderIteratorNoParent(TreeNode root) {

        class iterator {
            Deque<TreeNode> stack;
            iterator(TreeNode node) {
                stack = new LinkedList<>();
                pushLeft(node);
            }

            public boolean hasNext() {
                return !stack.isEmpty();
            }

            public TreeNode next() {
                if (!hasNext()) throw new NoSuchElementException("");
                TreeNode node = stack.pollLast();
                pushLeft(node.right);
                return node;
            }

            private void pushLeft(TreeNode node) {
                while (node != null) {
                    stack.offerLast(node);
                    node = node.left;
                }
            }
        }
    }
 */

/*
    Q1. Second largest node in the BST

    public TreeNode secondLargestNodeBST(TreeNode root) {
        if (root == null) return null;
        if (root.left == null && root.right == null) return null;

        //step 1: find the largest and its parent
        TreeNode prev = root;
        TreeNode cur = root;
        while (cur.right != null) {
            prev = cur;
            cur = cur.right;
        }

        //step 2: cur.left == null, return parent, otherwise find largest of left side
        if (cur.left == null) {
            return prev;
        } else {
            cur = cur.left;
            while (cur.right != null) {
                cur = cur.right;
            }
            return cur;
        }
    }


    public static void main(String[] args) {
        Main main = new Main();
        TreeNode node = new TreeNode(5);
        node.left = new TreeNode(3);
        node.right = new TreeNode(8);
        node.left.left = new TreeNode(1);

        TreeNode res = main.secondLargestNodeBST(node);
        System.out.println(res.key);
    }
 */

/*
    Q3. kth largest node in the BST

    public TreeNode kthLargestNodeBST(TreeNode root, int k) {
        if (root == null) return null;

        List<TreeNode> result = new LinkedList<>();
        helpFn(root, result, k);
        return result.get(result.size() - 1);
    }

    public void helpFn(TreeNode root, List<TreeNode> result, int k) {
        if (root == null) return;

        helpFn(root.right, result, k);
        result.add(root);
        if (result.size() == k) return;
        helpFn(root.left, result, k);
    }


    public static void main(String[] args) {
        Main main = new Main();
        TreeNode node = new TreeNode(5);
        node.left = new TreeNode(3);
        node.right = new TreeNode(8);
        node.left.left = new TreeNode(1);

        TreeNode res = main.kthLargestNodeBST(node, 2);
        System.out.println(res.key);
    }
 */

/*
 *      class 2
 */

/*
    1. Left view, BFS

                5
                /\
              1    9
               \    \
                3    11
               /
              2

    public List<Integer> leftViewBFS(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            res.add(queue.peek().key);
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        Main main = new Main();
        TreeNode node = new TreeNode(5);
        node.left = new TreeNode(1);
        node.right = new TreeNode(9);
        node.left.right = new TreeNode(3);
        node.left.right.left = new TreeNode(2);
        node.right.right = new TreeNode(11);

        List<Integer> res = main.leftViewBFS(node);
        System.out.println(res.toString());
    }
 */

/*
    Left view DFS

    public List<Integer> leftViewDFS(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        helpFn(root, 0, res);
        return res;
    }

    public void helpFn(TreeNode node, int level, List<Integer> res) {
        if (node == null) return;

        if (level == res.size()) {
            res.add(node.key);
        }
        helpFn(node.left, level + 1, res);
        helpFn(node.right, level+ 1, res);
    }

    public static void main(String[] args) {
        Main main = new Main();
        TreeNode node = new TreeNode(5);
        node.left = new TreeNode(1);
        node.right = new TreeNode(9);
        node.left.right = new TreeNode(3);
        node.left.right.left = new TreeNode(2);
        node.right.right = new TreeNode(11);

        List<Integer> res = main.leftViewDFS(node);
        System.out.println(res.toString());
    }
 */
/*
    Top view BFS

                 5              [5]
                /\
              1    9            [1 5 9]
            /  \    \
          0     3    11       [0 1 5 9 11]
               /
              2

    class Cell {
        int col;
        TreeNode node;
        Cell(int col, TreeNode node) {
            this.col = col;
            this.node = node;
        }
    }

    public List<Integer> topViewBFS(TreeNode root) {
        Queue<Cell> queue = new ArrayDeque<>();
        Deque<Integer> deque = new LinkedList<>();
        deque.offer(root.key);
        int leftMost = 0;
        int rightMost = 0;
        queue.offer(new Cell(0, root));
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Cell cell = queue.poll();

                if (cell.node.left != null) {
                    if (cell.col == leftMost) {
                        deque.offerFirst(cell.node.left.key);
                        leftMost--;
                    }
                    queue.offer(new Cell(cell.col - 1, cell.node.left));
                }
                if (cell.node.right != null) {
                    if (cell.col == rightMost) {
                        deque.offerLast(cell.node.right.key);
                        rightMost++;
                    }
                    queue.offer(new Cell(cell.col + 1, cell.node.right));
                }
            }
        }
        return new ArrayList<>(deque);
    }

    public static void main(String[] args) {
        Main main = new Main();
        TreeNode node = new TreeNode(5);
        node.left = new TreeNode(1);
        node.right = new TreeNode(9);
        node.left.left = new TreeNode(0);
        node.left.right = new TreeNode(3);
        node.left.right.left = new TreeNode(2);
        node.right.right = new TreeNode(11);

        List<Integer> res = main.topViewBFS(node);
        System.out.println(res.toString());
    }
 */

/*
        Vertical view BFS

    class Cell {
        int col;
        TreeNode node;
        Cell(int col, TreeNode node) {
            this.col = col;
            this.node = node;
        }
    }

    public List<List<Integer>> verticalViewBFS(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        Map<Integer, List<Integer>> map = new HashMap<>();
        Queue<Cell> queue = new LinkedList<>();
        int left = 0;
        int right = 0;
        queue.offer(new Cell(0, root));
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int  i = 0; i < size; i++) {
                Cell cell = queue.poll();
                int col = cell.col;
                TreeNode node = cell.node;

                if (!map.containsKey(col)) {
                    map.put(col, new ArrayList<>());
                }
                map.get(col).add(node.key);

                if (node.left != null) {
                    queue.offer(new Cell(col - 1, node.left));
                    left = Math.min(left, col - 1);
                }

                if (node.right != null) {
                    queue.offer(new Cell(col + 1, node.right));
                    right = Math.max(right, col + 1);
                }
            }
        }
        for (int i = left; i <= right; i++) {
            result.add(map.get(i));
        }
        return result;
    }

    public static void main(String[] args) {
        Main main = new Main();
        TreeNode node = new TreeNode(5);
        node.left = new TreeNode(1);
        node.right = new TreeNode(9);
        node.left.left = new TreeNode(0);
        node.left.right = new TreeNode(3);
        node.left.right.left = new TreeNode(2);
        node.right.right = new TreeNode(11);

        List<List<Integer>> res = main.verticalViewBFS(node);
        System.out.println(res.toString());
    }
 */

/*
    Border view DFS

    public List<Integer> borderView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        helpFn(root, true, true, result);
        return result;
    }

    public void helpFn(TreeNode root, boolean leftMost, boolean rightMost, List<Integer> result) {
        if (root == null) return;

        if (leftMost || (root.left == null && root.right == null)) result.add(root.key);
        helpFn(root.left, leftMost, false, result);
        helpFn(root.right, false, rightMost, result);
        if (rightMost && !leftMost && (root.left != null || root.right != null)) result.add(root.key);
    }

    public static void main(String[] args) {
        Main main = new Main();
        TreeNode node = new TreeNode(5);
        node.left = new TreeNode(1);
        node.right = new TreeNode(9);
        node.left.left = new TreeNode(0);
        node.left.right = new TreeNode(3);
        node.left.right.left = new TreeNode(2);
        node.right.right = new TreeNode(11);

        List<Integer> res = main.borderView(node);
        System.out.println(res.toString());
    }
 */

/*
    level order, DFS

    using inorder traversal, which preserves level order property

                 5
                /\
              1    9
            /  \    \
          0     3    11
               /
              2

      in order
      [
        [5]
        [1,9]
        [0,3,11]
        [2]
      ]

     public List<List<Integer>> levelOrderDFS(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        Map<Integer, List<Integer>> map = new HashMap<>();
        if (root == null) return result;
        helpFn(root, map, 0);
        for (int i = 0; i < map.size(); i++) {
            result.add(map.get(i));
        }
        return result;
    }

    public void helpFn(TreeNode root, Map<Integer, List<Integer>> map, int level) {
        if (root == null) return;

        if (level == map.size()) map.put(level, new ArrayList<>()); // check if it's new level
        map.get(level).add(root.key);
        helpFn(root.left, map, level + 1);
        helpFn(root.right, map, level + 1);
    }

    public static void main(String[] args) {
        Main main = new Main();
        TreeNode node = new TreeNode(5);
        node.left = new TreeNode(1);
        node.right = new TreeNode(9);
        node.left.left = new TreeNode(0);
        node.left.right = new TreeNode(3);
        node.left.right.left = new TreeNode(2);
        node.right.right = new TreeNode(11);

        List<List<Integer>> res = main.levelOrderDFS(node);
        System.out.println(res.toString());
    }
 */

/*
    all node at k level

    public List<Integer> allNodeOnKthLevel(TreeNode root, int k) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;

        helpFn(root, 0, result, k);

        return result;
    }

    public void helpFn(TreeNode root, int level, List<Integer> result, int k) {
        if (root == null) return;

        if (level == k) {
            result.add(root.key);
            return;
        }
        helpFn(root.left, level + 1, result, k);
        helpFn(root.right, level + 1, result, k);
    }

    public static void main(String[] args) {
        Main main = new Main();
        TreeNode node = new TreeNode(5);
        node.left = new TreeNode(1);
        node.right = new TreeNode(9);
        node.left.left = new TreeNode(0);
        node.left.right = new TreeNode(3);
        node.left.right.left = new TreeNode(2);
        node.right.right = new TreeNode(11);

        List<Integer> res = main.allNodeOnKthLevel(node, 2);
        System.out.println(res.toString());
    }
 */

/*
    Q5.     populating next right pointers for each node

    BFS, but using O(1) space

    public TreeNode populateRightNextPointer(TreeNode root) {
        if (root == null) return null;
        TreeNode head;
        head = root;

        while (head != null) {
            TreeNode cur = head;
            TreeNode prev = null;
            head = null;

            while (cur != null) {
                if (cur.left != null) {
                    if (head == null) head = cur.left;
                    if (prev != null) prev.next = cur.left;
                    prev = cur.left;
                }

                if (cur.right != null) {
                    if (head == null) head = cur.right;
                    if (prev != null) prev.next = cur.right;
                    prev = cur.right;
                }
                cur = cur.next;
            }
        }
        return root;
    }

    public static void main(String[] args) {
        Main main = new Main();
        TreeNode node = new TreeNode(5);
        node.left = new TreeNode(1);
        node.right = new TreeNode(9);
        node.left.left = new TreeNode(0);
        node.left.right = new TreeNode(3);
        node.left.right.left = new TreeNode(2);
        node.right.right = new TreeNode(11);

        TreeNode res = main.populateRightNextPointer(node);
        System.out.println(res);
    }

 */

/*
 *      class 3  pure recursion vs backtracking
 */

/*
        Q1 print visible node
            2
            /\
          7    1
          /\   /\
       6   5  3   4
      visible node: [2,7,3,4]

    public void printVisibleNodes(TreeNode root) {
        DFS(root, null);
    }

    public void DFS(TreeNode cur, TreeNode max) {
        if (cur == null) return;

        if (max == null || cur.key > max.key) {
            max = cur;
            System.out.println(cur.key);
        }

        DFS(cur.left, max);
        DFS(cur.right, max);
    }

    public static void main(String[] args) {
        Main main = new Main();
        TreeNode node = new TreeNode(2);
        node.left = new TreeNode(7);
        node.right = new TreeNode(1);
        node.left.left = new TreeNode(6);
        node.left.right = new TreeNode(5);
        node.right.left = new TreeNode(3);
        node.right.right = new TreeNode(4);

        main.printVisibleNodes(node);
    }
 */

/*
    Q2 Sum of all paths from root to leaf nodes

    public int sumOfAllPathsFromRootToLeaf(TreeNode root) {
        return  DFS(root, 0);
    }

    public int DFS(TreeNode cur, int curSum) {
        curSum = curSum * 10 + cur.key;
        if (cur.left == null && cur.right == null) {
            return curSum;
        }

        int sum = 0;
        if (cur.left != null) sum += DFS(cur.left, curSum);
        if (cur.right != null) sum += DFS(cur.right, curSum);
        return sum;
    }

    public static void main(String[] args) {
        Main main = new Main();
        TreeNode node = new TreeNode(7);
        node.left = new TreeNode(4);
        node.right = new TreeNode(9);
        node.left.left = new TreeNode(1);
        node.left.right = new TreeNode(5);
        node.left.right.right = new TreeNode(3);
        node.right.left = new TreeNode(8);

        int res = main.sumOfAllPathsFromRootToLeaf(node);
        System.out.println(res);
    }
 */

/*
    Q2.2    sum of all paths from leaf to root

    public int sumOfAllPathsFromLeafToRoot(TreeNode root) {
        return  DFS(root, 1, 0);
    }

    public int DFS(TreeNode cur, int factor, int curSum) {
        curSum = cur.key * factor + curSum;
        if (cur.left == null && cur.right == null) {
            return curSum;
        }

        int sum = 0;
        if (cur.left != null) sum += DFS(cur.left, factor * 10, curSum);
        if (cur.right != null) sum += DFS(cur.right, factor * 10, curSum);
        return sum;
    }

    public static void main(String[] args) {
        Main main = new Main();
        TreeNode node = new TreeNode(7);
        node.left = new TreeNode(4);
        node.right = new TreeNode(9);
        node.left.left = new TreeNode(1);
        node.left.right = new TreeNode(5);
        node.left.right.right = new TreeNode(3);
        node.right.left = new TreeNode(8);

        int res = main.sumOfAllPathsFromLeafToRoot(node);
        System.out.println(res);
    }
 */

/*
 *      class 4 related to path in the tree
 */

/*
    Q 1.1  直上直下 any to any - largest path sum, straight up down

    backtracking way:

    public int largestPathSumStraightUpDown(TreeNode root) {
        int[] max = new int[1];
        DFS(root, 0, max);
        return max[0];
    }

    public void DFS(TreeNode root, int prevSum, int[] max) {
        if (root == null) return;

        if (prevSum < 0) {
            prevSum = root.key;
        } else {
            prevSum += root.key;
        }

        max[0] = Math.max(max[0], prevSum);
        DFS(root.left, prevSum, max);
        DFS(root.right, prevSum, max);
    }

    public static void main(String[] args) {
        Main main = new Main();
        TreeNode node = new TreeNode(7);
        node.left = new TreeNode(4);
        node.right = new TreeNode(9);
        node.left.left = new TreeNode(1);
        node.left.right = new TreeNode(5);
        node.left.right.right = new TreeNode(3);
        node.right.left = new TreeNode(8);

        int res = main.largestPathSumStraightUpDown(node);
        System.out.println(res);
    }
 */

/*
    Q 1.2 人字形 any to any - largest sum

    pure recursion

    public int largestPathSumAnyToAny(TreeNode root) {
        int[] max = new int[1];
        DFS(root, max);
        return max[0];
    }

    public int DFS(TreeNode root, int[] max) {
        if (root == null) return 0;

        int left = DFS(root.left, max);
        int right = DFS(root.right, max);
        left = Math.max(left, 0);
        right = Math.max(right, 0);

        max[0] = Math.max(max[0], left + right + root.key);
        return Math.max(left, right) + root.key;
    }

    public static void main(String[] args) {
        Main main = new Main();
        TreeNode node = new TreeNode(7);
        node.left = new TreeNode(4);
        node.right = new TreeNode(9);
        node.left.left = new TreeNode(1);
        node.left.right = new TreeNode(5);
        node.left.right.right = new TreeNode(3);
        node.right.left = new TreeNode(8);

        int res = main.largestPathSumAnyToAny(node);
        System.out.println(res);
    }
 */

/*
    Q 3.1 if there is path to given target number

    public boolean ifPathSumToTarget(TreeNode root, int target) {
        boolean[] found = new boolean[1];
        Set<Integer> set = new HashSet<>();
        set.add(0);
        DFS(root, set, 0, found, target);
        return found[0];
    }

    public void DFS(TreeNode root, Set<Integer> set, int sum, boolean[] found, int target) {
        if (root == null) return;

        sum += root.key;
        if (set.contains(sum - target)) {
            found[0] = true;
            return;
        }
        boolean exist = set.add(sum); //important

        DFS(root.left, set, sum, found, target);
        DFS(root.right, set, sum, found, target);

        if (exist) set.remove(sum);
    }
 */

/*
    Q 3.2 number of paths sum to a given target
    // TODO: wong solution here
    public int numberOfPathsSumToTarget(TreeNode root, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        int[] count = new int[1];
        DFS(root, 0, map, count, target);
        return count[0];
    }

    public void DFS(TreeNode root, int sum, Map<Integer, Integer> map, int[] count, int target) {
        if (root == null) return;

        sum += root.key;
        if (map.containsKey(sum - target)) {
            count[0] = map.get(sum - target);
        }
        map.put(sum - target, map.getOrDefault(sum- target, 0) + 1);
        DFS(root.left, sum, map, count, target);
        DFS(root.right, sum, map, count, target);
    }
 */

/*
    Q 3.3 // TODO follow up
 */

/*
    Q 4 longest increasing path parent to child

    // back tracking way
    public int longestIncreasingPathParentToChild(TreeNode root) {
        return DFS(root, 0, 0);
    }

    public int DFS(TreeNode root, int prevVal, int count) {
        if (root == null) return 0;

        count = (root.key > prevVal) ? count + 1 : 1;
        int left = DFS(root.left, root.key, count);
        int right = DFS(root.right,  root.key, count);
        return Math.max(Math.max(left, right), count);
    }

    // pure recursion way
    public int longestIncreasingPathParentToChild(TreeNode root) {
        DFS(root);
        return globalLongest;
    }

    int globalLongest = 0;
    public int DFS(TreeNode root) {
        if (root == null) return 0;
        int left = DFS(root.left);
        int right = DFS(root.right);

        int longest = 1;
        if (root.left != null && root.left.key > root.key) longest += left;
        if (root.right != null && root.right.key > root.key) longest = Math.max(longest, right + 1);
        globalLongest = Math.max(globalLongest, longest);
        return longest;
    }

    public static void main(String[] args) {
        Main main = new Main();
        TreeNode node = new TreeNode(9);
        node.left = new TreeNode(2);
        node.right = new TreeNode(3);
        node.left.left = new TreeNode(-4);
        node.left.right = new TreeNode(5);
        node.right.left = new TreeNode(-3);
        node.right.right = new TreeNode(-7);
        node.left.right.left = new TreeNode(6);
        node.left.right.right = new TreeNode(1);
        node.left.right.left.left = new TreeNode(3);
        node.left.right.left.right = new TreeNode(4);

        int res = main.longestIncreasingPathParentToChild(node);
        System.out.println(res);
    }
 */

/*
        class 5
 */

/*
    count the number of uni-value tree

    public int countUniValueTree(TreeNode root) {
        int[] count = new int[1];
        DFS(root, count);
        return count[0];
    }

    public boolean DFS(TreeNode root, int[] count) {
        if (root == null) return true;

        boolean left = DFS(root.left, count);
        boolean right = DFS(root.right, count);
        if ((root.left == null || root.key == root.left.key) &&
                (root.right == null || root.key == root.right.key) &&
                left && right) {
            count[0]++;
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Main main = new Main();
        TreeNode node = new TreeNode(5);
        node.left = new TreeNode(5);
        node.right = new TreeNode(5);
        node.left.left = new TreeNode(2);
        node.left.right = new TreeNode(1);
        node.left.left.left = new TreeNode(2);
        node.left.right.left = new TreeNode(1);
        node.left.right.right = new TreeNode(1);

        int res = main.countUniValueTree(node);
        System.out.println(res);
    }
 */

/*
 *      Modify the structure
 */

/*
       Q1 remove all-zero subtrees

    public TreeNode countUniValueTree(TreeNode root) {
        return removeZeroNode(root);
    }

    public TreeNode removeZeroNode(TreeNode root) {
        if (root == null) return null;

        TreeNode left = removeZeroNode(root.left);
        TreeNode right = removeZeroNode(root.right);

        if (left == null && right == null && root.key == 0) return null;
        return root;
    }

    public static void main(String[] args) {
        Main main = new Main();
        TreeNode node = new TreeNode(1);
        node.left = new TreeNode(1);
        node.right = new TreeNode(0);
        node.left.left = new TreeNode(1);
        node.left.right = new TreeNode(0);
        node.right.left = new TreeNode(1);
        node.right.right = new TreeNode(0);
        node.left.left.left = new TreeNode(0);
        node.left.right.left = new TreeNode(0);
        node.left.right.right = new TreeNode(0);

        TreeNode res = main.countUniValueTree(node);
        System.out.println(res);
    }
 */

/*
    Q2 AND two tree

              r1         r2
           / \ \ \      /\ \ \
           0 1 0 1     0 1 1 0

              merge r1 and r2, return:
                      r'
                   / \ \ \
                  0  1  0 0

                  Other example:
                        r
                /      \    \     \
             /\\\      0     0    /\\\
            0 100                0 100

    interface QuadTreeNode {
        int color = 0; // 0 white, 1 black, eliminate white, keep black
        boolean isLeaf();
        List<QuadTreeNode> getChildren();
    }

    class LeafNode implements QuadTreeNode {
        int color;
        LeafNode(int color) {
            this.color = color;
        }
        @Override
        public boolean isLeaf() {
            return true;
        }

        @Override
        public List<QuadTreeNode> getChildren() {
            return null;
        }
    }

    class NoneLeafNode implements QuadTreeNode {
        List<QuadTreeNode> children = new ArrayList<>();
        @Override
        public boolean isLeaf() {
            return false;
        }

        @Override
        public List<QuadTreeNode> getChildren() {
            return children;
        }
    }

    public QuadTreeNode and(QuadTreeNode root1, QuadTreeNode root2) {
        if (root2.isLeaf() && !root1.isLeaf()) {
            return and(root2, root1);
        }
        if (root1.isLeaf()) {
            return root1.color == 0 ? root1 : root2;
        }
        root1.getChildren().set(0, and(root1.getChildren().get(0), root2.getChildren().get(0)));
        root1.getChildren().set(1, and(root1.getChildren().get(1), root2.getChildren().get(1)));
        root1.getChildren().set(2, and(root1.getChildren().get(2), root2.getChildren().get(2)));
        root1.getChildren().set(3, and(root1.getChildren().get(3), root2.getChildren().get(3)));

        List<QuadTreeNode> children = root1.getChildren();
        if (children.get(0).isLeaf() && children.get(1).isLeaf()
            && children.get(2).isLeaf() && children.get(3).isLeaf()
            && children.get(0).color == 0 && children.get(1).color == 0
                && children.get(2).color == 0 && children.get(3).color == 0) {
            return new LeafNode(0);
        }
        return root1;
    }
 */

/*
    Q3  delete node in range

    public TreeNode deleteNodeInRange(TreeNode root, int min, int max) {
        return helpFn(root, min, max, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public TreeNode helpFn(TreeNode root, int min, int max, int leftBound, int rightBound) {
        if (root == null) return null;

        if (root.key < min) {
            return helpFn(root.right, min, max, root.key + 1, rightBound);
        }

        if (root.key > max) {
            return helpFn(root.left, min, max, leftBound, root.key - 1);
        }

        if (leftBound >= min && rightBound <= max) return root;
        root.left = helpFn(root.left, min, max, leftBound, root.key - 1);
        root.right = helpFn(root.right, min, max, root.key + 1, rightBound);
        return root;
    }

    public static void main(String[] args) {
        Main main = new Main();
        TreeNode node = new TreeNode(5);
        node.left = new TreeNode(1);
        node.right = new TreeNode(9);
        node.left.left = new TreeNode(0);
        node.left.right = new TreeNode(3);
        node.right.left = new TreeNode(8);
        node.right.right = new TreeNode(11);

        TreeNode res = main.deleteNodeInRange(node, 2, 8);
        System.out.println(res);
    }
 */

/*
    Q4 move target node to most left path

    public TreeNode mostLeftPaht(TreeNode root, TreeNode target) {
        exist(root, target);
        return root;
    }

    public boolean exist(TreeNode root, TreeNode target) {
        if (root == null) return false;
        if (root == target) return true;
        if (exist(root.left, target)) return true;
        if (exist(root.right, target)) {
            TreeNode left = root.left;
            root.left = root.right;
            root.right = left;
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Main main = new Main();
        TreeNode node = new TreeNode(4);
        node.left = new TreeNode(2);
        node.right = new TreeNode(5);
        node.left.left = new TreeNode(1);
        node.left.right = new TreeNode(3);
        node.right.left = new TreeNode(6);
        node.right.right = new TreeNode(7);

        TreeNode res = main.mostLeftPaht(node, new TreeNode(6));
        System.out.println(res);
    }
 */

/*
 *      multiple return value
 */

/*
        Q1 determine if complete tree

    class ReturnType {
        boolean isPerfect;
        boolean isComplete;
        int height;
        ReturnType(boolean isPerfect, boolean isComplete, int height) {
            this.isPerfect = isPerfect;
            this.isComplete = isComplete;
            this.height = height;
        }
    }

    public boolean determineCompleteTree(TreeNode root) {
        return helpFn(root).isComplete;
    }

    public ReturnType helpFn(TreeNode root) {
        if (root == null) return new ReturnType(true, true, 0);
        ReturnType left = helpFn(root.left);
        ReturnType right = helpFn(root.right);

        // case 1: left subtree is complete, right is perfect, left height = right height + 1
        // case 2: left subtree is perfect, right is complete, left height = right height
        if ((left.isComplete && right.isPerfect && left.height - right.height == 1)
            ||(left.isPerfect && right.isComplete && left.height == right.height)) {
            return new ReturnType(false, true, Math.max(left.height, right.height) + 1);
        }

        // check if it is perfect subtree starting from cur node
        if (left.isComplete && right.isComplete && left.height == right.height) {
            return new ReturnType(true, false, left.height + 1);
        }
        // it's neither complete nor perfect
        return new ReturnType(false, false, Math.max(left.height, right.height) + 1);
    }

    public static void main(String[] args) {
        Main main = new Main();
        TreeNode node = new TreeNode(1);
        node.left = new TreeNode(2);
        node.right = new TreeNode(3);
        node.left.left = new TreeNode(4);
        node.left.right = new TreeNode(5);
        node.right.left = new TreeNode(6);
        node.right.right = new TreeNode(7);
        node.left.left.left = new TreeNode(8);
        node.left.left.right = new TreeNode(9);
        node.left.right.left = new TreeNode(10);
        //node.right.right.right= new TreeNode(8);

        boolean res = main.determineCompleteTree(node);
        System.out.println(res);
    }
 */


/*
        Q 2 Determine if perfect tree

    public int determineIfPerfectTree(TreeNode root) {
        if (root == null) return 0;
        int left = determineIfPerfectTree(root.left);
        if (left  < 0) return -1;
        int right = determineIfPerfectTree(root.right);
        if (right < 0) return -1;
        return left == right ? left + 1 : -1;
    }
 */

/*
    Q3  Largest complete subtree

    class ReturnType {
        boolean isPerfect;
        boolean isComplete;
        int height;
        int size;
        ReturnType(boolean isPerfect, boolean isComplete, int height, int size) {
            this.isPerfect = isPerfect;
            this.isComplete = isComplete;
            this.height = height;
            this.size = size;
        }
    }

    public int determineCompleteTree(TreeNode root) {
        int[] max = new int[1];
        helpFn(root, max);
        return max[0];
    }

    public ReturnType helpFn(TreeNode root, int[] max) {
        if (root == null) return new ReturnType(true, true, 0, 0);
        ReturnType left = helpFn(root.left, max);
        ReturnType right = helpFn(root.right, max);

        // case 1: left subtree is complete, right is perfect, left height = right height + 1
        // case 2: left subtree is perfect, right is complete, left height = right height
        if ((left.isComplete && right.isPerfect && left.height - right.height == 1)
            ||(left.isPerfect && right.isComplete && left.height == right.height)) {
            max[0] = Math.max(max[0], left.size + right.size + 1);
            return new ReturnType(false, true, Math.max(left.height, right.height) + 1, left.size + right.size + 1);
        }

        // check if it is perfect subtree starting from cur node
        if (left.isComplete && right.isComplete && left.height == right.height) {
            return new ReturnType(true, false, left.height + 1, left.size + right.size + 1);
        }
        // it's neither complete nor perfect
        return new ReturnType(false, false, Math.max(left.height, right.height) + 1, left.size + right.size + 1);
    }

    public static void main(String[] args) {
        Main main = new Main();
        TreeNode node = new TreeNode(1);
        node.left = new TreeNode(2);
        node.right = new TreeNode(3);
        node.left.left = new TreeNode(4);
        node.left.right = new TreeNode(5);
        node.right.left = new TreeNode(11);
        node.right.right = new TreeNode(7);
        node.left.left.left = new TreeNode(6);
        node.left.left.right = new TreeNode(8);
        node.left.right.left = new TreeNode(9);
        node.right.right.left = new TreeNode(12);

        int res = main.determineCompleteTree(node);
        System.out.println(res);
    }
 */

/*
    Q4 number of nodes in complete tree

             1
          /    \
         2      3
        /\     / \
       4  5   6   7
      /
     8

                1
           /       \
          2          3
        / \         / \
       4   5       6   7
      /\   /\     /
     8  9 10 11  12


    public int numberOfNodesCompleteBinaryTree(TreeNode root) {
        if (root == null) return 0;
        int left = height(root.left);
        int right = height(root.right);
        if (left == right) { //left if perfect, right is complete tree(including perfect)
            return (1 << left) + numberOfNodesCompleteBinaryTree(root.right);
        } else { // left is complete, right is perfect tree
            return (1 << right) + numberOfNodesCompleteBinaryTree(root.left);
        }
    }

    public int height(TreeNode root) {
        int count = 0;
        while (root != null) {
            root = root.left;
            count++;
        }
        return count;
    }

    public static void main(String[] args) {
        Main main = new Main();
        TreeNode node = new TreeNode(1);
        node.left = new TreeNode(2);
        node.right = new TreeNode(3);
        node.left.left = new TreeNode(4);
        node.left.right = new TreeNode(5);
        node.right.left = new TreeNode(6);
        node.right.right = new TreeNode(7);
        node.left.left.left = new TreeNode(8);
        System.out.println(main.numberOfNodesCompleteBinaryTree(node));
    }
 */

/*
        class 5    LCA
 */

/*
    Q4 find LCA of all the deepest leaf nodes

    class ReturnType {
        int height;
        TreeNode lca;
        ReturnType(int height, TreeNode lca) {
            this.height =  height;
            this.lca = lca;
        }
    }

    public ReturnType LCADeepestLeafNodes(TreeNode root) {
        if (root == null) return new ReturnType(0, null);
        ReturnType left = LCADeepestLeafNodes(root.left);
        ReturnType right = LCADeepestLeafNodes(root.right);

        if (left != null) left.height++;
        if (right != null) right.height++;
        if (left != null && right != null ) {
            if (left.height == right.height) {
                return new ReturnType(left.height, root);
            }
            return left.height > right.height ? left : right;
        }
        return left != null ? left : right;
    }

    public static void main(String[] args) {
        Main main = new Main();
        TreeNode node = new TreeNode(5);
        node.left = new TreeNode(1);
        node.right = new TreeNode(4);
        node.left.left = new TreeNode(2);
        node.left.right = new TreeNode(3);
        node.right.left = new TreeNode(7);
        node.right.right = new TreeNode(8);
        node.right.left.left = new TreeNode(10);
        node.right.left.right = new TreeNode(11);
        node.right.right.left = new TreeNode(12);
        node.right.right.right = new TreeNode(13);
        ReturnType res = main.LCADeepestLeafNodes(node);
        System.out.println(res.lca.val);
    }

 */

/*
    Q 5 find the path between these two nodes

    public List<Integer> pathBetweenTwoNodes(TreeNode root, int n1, int n2) {
        List<List<Integer>> paths = new ArrayList<>();

        //back tracking find path root -> a, find path root -> b
        DFS(root, new ArrayList<>(), paths, n1, n2);


        // find LCA of two paths
        List<Integer> list1 = paths.get(0);
        List<Integer> list2 = paths.get(1);
        int pos = 0;
        int commonEle = 0;
        while (list1.get(pos).equals(list2.get(0))) {
            commonEle = list1.get(pos);
            pos++;
        }

        //construct path from two paths, which to two nodes
        List<Integer> res = new ArrayList<>();
        for (int i = list1.size() - 1; i >= pos; i--) {
            res.add(list1.get(i));
        }
        res.add(commonEle);
        for (int i = pos; i < list2.size(); i++) {
            res.add(list2.get(i));
        }
        return res;
    }

    public void DFS(TreeNode root, List<Integer> cur, List<List<Integer>> res, int n1, int n2) {
        if (root == null) return;

        cur.add(root.val);
        if (root.val == n1 || root.val == n2) {
            res.add(new ArrayList<>(cur));
            cur.remove(cur.size() - 1);
            return;
        }
        DFS(root.left, cur, res, n1, n2);
        DFS(root.right, cur, res, n1, n2);
        cur.remove(cur.size() - 1);
    }
 */

/*
    Identical subtrees
 */

/*
    Q1 check if sub tree exists in another tree

    O(m + n)   by constructing hash code of target tree

     class ReturnType {
        int sum;
        int size;
        ReturnType(int sum, int size) {
            this.sum = sum;
            this.size = size;
        }
    }

    public boolean checkIdenticalSubtrees(TreeNode root, TreeNode target) {
        ReturnType targetHashCode = getTargetHashCode(target);
        boolean[] match = new boolean[1];
        DFS(root, targetHashCode, match);
        return match[0];
    }

    public ReturnType DFS(TreeNode root, ReturnType hashCode, boolean[] match) {
        if (root == null) return new ReturnType(0,0);
        ReturnType left = DFS(root.left, hashCode, match);
        ReturnType right = DFS(root.right, hashCode, match);
        int sum = left.sum + right.sum + root.val;
        int size = left.size + right.size + 1;
        if (sum == hashCode.sum && size == hashCode.size) {
            match[0] = true;
        }
        return new ReturnType(sum, size);
    }

    public ReturnType getTargetHashCode(TreeNode root) {
        if (root == null) return new ReturnType(0, 0);
        ReturnType left = getTargetHashCode(root.left);
        ReturnType right = getTargetHashCode(root.right);
        return new ReturnType(left.sum + right.sum + root.val, left.size + right.size + 1);
    }

    public static void main(String[] args) {
        Main main = new Main();
        TreeNode node = new TreeNode(1);
        node.left = new TreeNode(3);
        node.right = new TreeNode(3);
        node.left.left = new TreeNode(4);
        node.right.left = new TreeNode(2);
        node.right.right = new TreeNode(4);
        node.right.left.left = new TreeNode(4);

        TreeNode target = new TreeNode(3);
        target.left = new TreeNode(2);
        target.right = new TreeNode(4);
        target.left.left = new TreeNode(4);
        System.out.println(main.checkIdenticalSubtrees(node, target));
    }
 */

/*
    class 6 work with return value and passing parameters.
 */

/*
    Q1 get the list of nodes after deletion

     public List<TreeNode> getListNodesAfterDeletion(TreeNode root) {
        List<TreeNode> ans = new ArrayList<>();
        DFS(root, null, ans);
        return ans;
    }

    public void DFS(TreeNode root, TreeNode parent, List<TreeNode> ans) {
        if (root == null) return;
        DFS(root.left, root, ans);
        DFS(root.right, root, ans);

        // should put the node into result
        if (shouldDelete(parent) && !shouldDelete(root)) ans.add(root);
        // remove the edge
        if (shouldDelete(parent) || shouldDelete(root)) remove(root, parent);
    }

    public void remove(TreeNode root, TreeNode parent) {
        if (parent ==  null) return;
        if (root == parent.left) parent.left = null;
        else parent.right = null;
    }

 */

/*
    Q2 change the value of nodes in BST to sum of all nodes <= its own value

    // hacky way using global prefix
    public void getListNodesAfterDeletion(TreeNode root) {
        int[] prefix = new int[1];
        DFS(root, prefix);
    }

    public void DFS(TreeNode root, int[] prefix) {
        if (root == null) return;
        DFS(root.left, prefix);
        prefix[0] += root.val;
        root.val = prefix[0];
        DFS(root.right, prefix);
    }

    public static void main(String[] args) {
        Main main = new Main();
        TreeNode node = new TreeNode(5);
        node.left = new TreeNode(3);
        node.right = new TreeNode(8);
        node.left.left = new TreeNode(2);
        node.right.left = new TreeNode(6);
        node.right.right = new TreeNode(12);
        main.getListNodesAfterDeletion(node);
        System.out.print(node);
    }

      //////////////////////////////////
     //Another no hacky global viable way
     ///////////////////////////////////

    // prefix: all the nodes already traveled
    public int getListNodesAfterDeletion(TreeNode root) {
        return DFS(root, 0);
    }

    public int DFS(TreeNode root, int preSum) {
        if (root == null) return preSum;
        root.val += DFS(root.left, preSum);
        return DFS(root.right, root.val);
    }

    public static void main(String[] args) {
        Main main = new Main();
        TreeNode node = new TreeNode(5);
        node.left = new TreeNode(3);
        node.right = new TreeNode(8);
        node.left.left = new TreeNode(2);
        node.right.left = new TreeNode(6);
        node.right.right = new TreeNode(12);
        int res = main.getListNodesAfterDeletion(node);
        System.out.print(res);
    }
 */

/*
    Q3  sum of all integers int he list weighted by their depth

    [[1,1] ,2, [1,1]], return 10
    [   , 2,        3]  - level 1
      /      \
    [1,1]   [1,1]       = level 2
    sum = 2 * 1 + (1 + 1 + 1 + 1) * 2 = 2 + 8 = 10

    // return value: sum of the subtree
    public interface NestedInteger {
        boolean isInteger();
        Integer getInteger();
        List<NestedInteger> getList();
    }

    public int getNestedSum(List<NestedInteger> input) {
        return DFS(input, 1);
    }

    public int DFS(List<NestedInteger> input, int level) {
        int sum = 0;
        for (NestedInteger nest : input) {
            if (nest.isInteger()) {
                sum += level * nest.getInteger();
            } else {
                sum += DFS(nest.getList(), level+ 1);
            }
        }
        return sum;
    }
 */

/*
    Q4 get node with equal probability

     interface TreeIterator extends Iterator{}
    //running on single machine
    public TreeNode sample(TreeIterator iter) {
        TreeNode cur = null;
        int count = 0;
        while (iter.hasNext()) {
            TreeNode node = iter.next();
            int val = (int)(Math.random()*++count);
            if (val == 0) {
                cur = node;
            }
        }
        return cur;
    }

    // divide and conquer
    class Wrapper {
        TreeNode sample;
        int size;
        Wrapper(TreeNode sample, int size) {
            this.sample = sample;
            this.size = size;
        }
    }

    public Wrapper sampleDivideConquer(TreeNode root) {
        if (root == null) return new Wrapper(null, 0);
        Wrapper left = sampleDivideConquer(root.left);
        Wrapper right = sampleDivideConquer(root.right);
        int randomPos = (int)(Math.random() * (left.size + right.size + 1));
        if (randomPos < left.size) return left;
        if (randomPos < left.size + right.size) return right;
        return new Wrapper(root, left.size + right.size + 1);
    }
 */

/*
        class 7  Flatten trees
 */

/*
    Q1  pre order : in place convert 3-ary tree to single linked list by c3 link

     static class TreeNode {
        public int val;
        TreeNode c1;
        TreeNode c2;
        TreeNode c3;
        TreeNode(int val) {
            this.val =val;
        }
    }

    TreeNode prev = null;
    public TreeNode preOrderThreeAryTreeToSingleLinkedLis(TreeNode root) {
        preOrder(root);
        return root;
    }

    public void preOrder(TreeNode root) {
        if (root == null) return;
        if (prev != null) prev.c3 = root;
        prev = root;
        TreeNode c3 = root.c3;
        preOrder(root.c1);
        preOrder(root.c2);
        preOrder(c3);
        root.c1 = null;
        root.c2 = null;
    }

    public static void main(String[] args) {
        Main main = new Main();
        TreeNode node = new TreeNode(1);
        node.c1 = new TreeNode(2);
        node.c2 = new TreeNode(3);
        node.c3 = new TreeNode(4);
        node.c1.c1 = new TreeNode(5);
        node.c1.c2 = new TreeNode(6);
        node.c2.c1 = new TreeNode(7);
        node.c2.c1.c1 = new TreeNode(8);
        node.c2.c1.c2 = new TreeNode(9);
        node.c2.c1.c3 = new TreeNode(10);
        TreeNode ans = main.preOrderThreeAryTreeToSingleLinkedLis(node);
        while (ans.c3 != null) {
            System.out.print(ans.c3.val);
            ans = ans.c3;
        }
    }
 */

/*
    Q2.1 convert BST to sorted double linked list in place

    TreeNode prev = null;
    TreeNode head = null;
    public TreeNode convertBSTtoSotredDLLInPlace(TreeNode root) {
        inOrder(root);
        return head;
    }

    public void inOrder(TreeNode root) {
        if (root == null) return;
        inOrder(root.left);
        if (prev == null) head = root;
        else {
            prev.right = root;
            root.left = prev;
        }
        prev = root;
        inOrder(root.right);
    }

    public static void main(String[] args) {
        Main main = new Main();
        TreeNode node = new TreeNode(2);
        node.left = new TreeNode(1);
        node.right = new TreeNode(3);
        TreeNode ans = main.convertBSTtoSotredDLLInPlace(node);
        while (ans != null) {
            System.out.print(ans.val);
            ans = ans.right;
        }
    }
*/

/*
    Q2.2 convert BST to sorted circular double linked list in-place

    //note: every sub-problem is circular dll

    public TreeNode convertBSTtoSortedCircularDLLInPlace(TreeNode root) {
        if (root == null) return null;
        TreeNode left = convertBSTtoSortedCircularDLLInPlace(root.left);
        TreeNode right = convertBSTtoSortedCircularDLLInPlace(root.right);
        root.left = root; // if one node, tail and head are itself
        root.right = root;
        left = connect(left, root);
        left = connect(left, right);
        return left;
    }


    public TreeNode connect(TreeNode h1, TreeNode h2) {
        if (h1 == null) return h2;
        if (h2 == null) return h1;
        TreeNode t1 = h1.left; //get the tail b.c circular
        TreeNode t2 = h2.left; //get the tail b.c circular

        h1.left = t2; //break the small circle, refer the graph on slide
        t2.right = h1; //break the small circle

        h2.left = t1;
        t1.right = h2;
        return h1;
    }

    public static void main(String[] args) {
        Main main = new Main();
        TreeNode node = new TreeNode(2);
        node.left = new TreeNode(1);
        node.right = new TreeNode(3);
        TreeNode ans = main.convertBSTtoSortedCircularDLLInPlace(node);
        while (ans != null) {
            System.out.print(ans.val);
            ans = ans.right;
        }
    }
 */

/*
    Construct/Reconstruct Trees
 */

/*
    V0. Sorted array to balanced binary search tree

     public TreeNode constructBST(int[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        return helpFn(array, 0, array.length - 1);
    }

    //base case need to consider size 0,1,2
    public TreeNode helpFn(int[] nums, int left, int right) {
        if (left > right) return null; //e.g. two element
        int mid = left + (right - left) / 2;
        TreeNode node = new TreeNode(nums[mid]);
        node.left = helpFn(nums, left, mid - 1);
        node.right = helpFn(nums, mid + 1, right);
        return node;
    }

    public static void main(String[] args) {
        Main main = new Main();
        TreeNode ans = main.constructBST(new int[] {1,2,3});
        System.out.print(ans);
    }
 */

/*
    V1  build binary search tree to  from sorted linked list, in place

         4
        / \
       2   5
      / \
     1   3

     0    1    2    3    4
     1 -> 2 -> 3 -> 4 -> 5

     3.next is 4, so we can get mid by O(1) if traversal in order
     in order traverse provide index of each node

    ListNode curInOrder;
    public TreeNode constructBSTbyLinkedList(ListNode head) {
        curInOrder = head;
        int len = getLength(head);
        return helpFn(0, len - 1);
    }

    public TreeNode helpFn(int left, int right) {
        if (left > right) return null;
        int mid = left + (right - left) / 2;
        TreeNode leftSub = helpFn(left , mid - 1);
        TreeNode root = new TreeNode(curInOrder.val);
        curInOrder = curInOrder.next; //O(1) to get the middle of list
        root.left = leftSub;
        root.right = helpFn(mid + 1, right);
        return root;
    }

    public int getLength(ListNode head) {
        int count = 0;
        while (head != null) {
            count++;
            head = head.next;
        }
        return count;
    }

    public static void main(String[] args) {
        Main main = new Main();
        ListNode node = new ListNode(1);
        node.next = new ListNode(2);
        node.next.next = new ListNode(3);
        TreeNode ans = main.constructBSTbyLinkedList(node);
        System.out.print(ans);
    }
 */

/*
 *      class 8   serialization / deserialization
 */

/*
        Q1 construct tree pre order + in order

                    5

                  /    \

                3        8

              /   \        \

            1      4        11


                    0  1  2  3  4  5
        pre-order {5, 3, 1, 4, 8, 11}

        in-order  {1, 3, 4, 5, 8, 11}
                   l       pos      r

    public TreeNode reconstruct(int[] in, int[] pre) {
        Map<Integer, Integer> inMap = new HashMap<>();
        for (int i = 0; i < in.length; i++) {
            inMap.put(in[i], i);
        }
        return buildTree(inMap, 0, in.length - 1, pre, 0, pre.length - 1);
    }

    public TreeNode buildTree(Map<Integer, Integer> inMap, int inLeft, int inRight, int[] pre, int preLeft, int preRight) {
        if (inLeft > inRight)  return null;
        TreeNode root = new TreeNode(pre[preLeft]);
        int pos = inMap.get(pre[preLeft]);
        int len = pos - inLeft; //important to get size, can not just use pos as boundary for pre-order, e.e. pos at node 3 index 1, pos + 1 can not give correct right subtree
        root.left = buildTree(inMap, inLeft, pos - 1, pre, preLeft + 1, preLeft + len);
        root.right = buildTree(inMap, pos + 1, inRight, pre,  preLeft + len + 1, preRight);
        return root;
    }

    public static void main(String[] args) {
        Main main = new Main();
        TreeNode ans = main.reconstruct(new int[]{1, 3, 4, 5, 8, 11}, new int[]{5, 3, 1, 4, 8, 11});
        System.out.print(ans);
    }
 */

/*
    Q1 method 2, traversing tree with pre order in order same time
                     5

                  /    \

                3        8

              /   \        \

            1      4        11

    construct subtree pre-order
    traverse constructed subtree inorder

    int preIndex = 0;
    int inIndex = 0;
    public TreeNode reconstruct(int[] in, int[] pre) {
        return buildTree(pre, in, Integer.MAX_VALUE);
    }

    public TreeNode buildTree(int[] pre, int[] in, int rootVale) {
        //inIndex == in.length when root finishing right subtree
        if (inIndex == in.length || in[inIndex] == rootVale) return null;
        TreeNode root = new TreeNode(pre[preIndex++]);
        root.left = buildTree(pre, in, root.val);
        // we need to know thd boundary
        inIndex++;
        root.right = buildTree(pre, in, rootVale);
        return root;
    }

    public static void main(String[] args) {
        Main main = new Main();
        TreeNode ans = main.reconstruct(new int[]{1, 3, 4, 5, 8, 11}, new int[]{5, 3, 1, 4, 8, 11});
        System.out.print(ans);
    }
 */

/*
    Q2  known pre order and in order, return post order traversal sequence

                    5

                  /    \

                3        8

              /   \        \

            1      4        11

    int preIndex = 0;
    int inIndex = 0;
    List<Integer> postOrder = new ArrayList<>();
    public List<Integer> reconstruct(int[] in, int[] pre) {
        buildTree(pre, in, Integer.MAX_VALUE);
        return postOrder;
    }

    // we don't need to construct the tree
    public void buildTree(int[] pre, int[] in, int rootVale) {
        if (inIndex == in.length || in[inIndex] == rootVale) return;
        int curRoot = pre[preIndex++];
        buildTree(pre, in, curRoot);
        inIndex++;
        buildTree(pre, in, rootVale);
        postOrder.add(curRoot);
    }

    public static void main(String[] args) {
        Main main = new Main();
        List<Integer> ans = main.reconstruct(new int[]{1, 3, 4, 5, 8, 11}, new int[]{5, 3, 1, 4, 8, 11});
        System.out.print(ans);
    }
 */

/*
    Q3 construct binary search tree, given pre order

    [5,1,3,4,|  7,   6,8]
              lend

    O(nlogn)
    public TreeNode constructBSTPreOrder(int[] pre) {
        return buildTree(pre, 0, pre.length - 1);
    }

    public TreeNode buildTree(int[] pre, int start, int end) {
        if (start > end)  return null;
        TreeNode root =new TreeNode(pre[start]);
        //find the boundary of left subtree
        int leftEnd = start + 1;
        while (leftEnd <= end && pre[leftEnd] < pre[start]) {
            leftEnd++;
        }
        root.left = buildTree(pre, start + 1, leftEnd - 1);
        root.right = buildTree(pre, leftEnd, end);
        return root;
    }

    public static void main(String[] args) {
        Main main = new Main();
        TreeNode ans = main.constructBSTPreOrder(new int[]{5,1,3,4,7,6,8});
        System.out.print(ans);
    }
 */

/*
    Q3 method 2    O(n)

    int preIndex = 0;
    public TreeNode constructBSTPreOrderOn(int[] pre) {
        return buildTree(pre, Integer.MAX_VALUE);
    }

    public TreeNode buildTree(int[] pre, int rootVal) {
        if (preIndex == pre.length || pre[preIndex] > rootVal) return null;
        TreeNode root = new TreeNode(pre[preIndex++]);
        root.left = buildTree(pre, root.val);
        root.right = buildTree(pre, rootVal);
        return root;
    }

    public static void main(String[] args) {
        Main main = new Main();
        TreeNode ans = main.constructBSTPreOrderOn(new int[]{5,1,3,4,7,6,8});
        System.out.print(ans);
    }
 */

/*
    Q 3.1 known BSt pre order return post order sequence

    int preIndex = 0;
    List<Integer> postOrdre = new ArrayList<>();
    public List<Integer> constructBSTPreOrderOn(int[] pre) {
        buildTree(pre, Integer.MAX_VALUE);
        return postOrdre;
    }

    public void buildTree(int[] pre, int rootVal) {
        if (preIndex == pre.length || pre[preIndex] > rootVal) return;
        int curRoot = pre[preIndex++];
        buildTree(pre, curRoot);
        buildTree(pre, rootVal);
        postOrdre.add(curRoot);
    }

    public static void main(String[] args) {
        Main main = new Main();
        List<Integer> ans = main.constructBSTPreOrderOn(new int[]{5,1,3,4,7,6,8});
        System.out.print(ans);
    }
 */

/*
    Q 3.2 Determine pre-order sequence is valid binary search tree

    [5,1,7,2]

     5
   /   \
  1     8
       /
      6
        7
    int preIndex = 0;
    public boolean checkPreOrderIsValidBST(int[] pre) {
        return buildTree(pre, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public boolean buildTree(int[] pre, int min, int max) {
        if (preIndex == pre.length || pre[preIndex] > max) return true;
        if (pre[preIndex] <= min) return false;
        int curRoot = pre[preIndex++];
        if (!buildTree(pre, min, curRoot)) return false;
        if (!buildTree(pre, curRoot, max)) return false;
        return true;
    }

    public static void main(String[] args) {
        Main main = new Main();
        boolean ans = main.checkPreOrderIsValidBST(new int[]{5,1,7,2});
        System.out.print(ans);
    }
 */

/*
    Q5  build tree, special signal

         2
        /\
       1   #
      /\
     #  3
        /\
       #  #

    int preIndex = 0;
    public TreeNode buildTreeWithSpecialSignal(String[] pre) {
        if (pre[preIndex].equals("#")) {
            preIndex++;
            return null;
        }

        TreeNode root = new TreeNode(Integer.valueOf(pre[preIndex++]));
        root.left = buildTreeWithSpecialSignal(pre);
        root.right = buildTreeWithSpecialSignal(pre);
        return root;
    }

    public static void main(String[] args) {
        Main main = new Main();
        TreeNode ans = main.buildTreeWithSpecialSignal(new String[]{"2", "1", "#", "3", "#", "#", "#"});
        System.out.print(ans);
    }
 */

/*
    Q6  pre-order + post-order reconstruct general n-ary tree

    pre-order  A BFGH   CI  DJK E
    post-order   FGHB   IC  JKD  E A

    class TreeNode {
        String value;
        List<TreeNode> childern;
        TreeNode(String value) {
            this.value = value;
            childern = new ArrayList<>();
        }
    }

    int postIndex = 0;
    int preIndex = 0;
    public TreeNode contructNaryTreePrePost(String[] pre, String[] post) {
        TreeNode root = new TreeNode(pre[preIndex]);
        preIndex++;
        while (!post[postIndex].equals(root.value)) {
            root.childern.add(contructNaryTreePrePost(pre, post));
        }
        postIndex++;
        return root;
    }

    public static void main(String[] args) {
        Main main = new Main();
        TreeNode ans = main.contructNaryTreePrePost(new String[]{"A", "B", "F", "G", "H", "C", "I", "D", "J", "K", "E"},
                new String[]{"F", "G", "H", "B", "I", "C", "J", "K", "D", "E", "A"});
        System.out.print(ans);
    }
 */

/*
    class 9 continue serialization/deserialization
 */

/*
    Q1 construct tree given [a[b[c][d][f]][e]]

         0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17
         [ a [ b [ c ] [ d ] [  f  ]   ] [  e  ]  ]

                    a
                   / \
                  b   e
                 /|\
                c d f

    class TreeNode {
        String value;
        List<TreeNode> childern;
        TreeNode(String value) {
            this.value = value;
            childern = new ArrayList<>();
        }
    }

    int index = 0;
    public TreeNode constructTree(String preOrder) {
        index++;
        TreeNode node = new TreeNode(String.valueOf(preOrder.charAt(index)));
        index++;
        while (!String.valueOf(preOrder.charAt(index)).equals("]")) {
            node.childern.add(constructTree(preOrder));
        }
        index++;
        return node;
    }

    public static void main(String[] args) {
        Main main = new Main();
        TreeNode ans = main.constructTree("[a[b[c][d][f]][e]]");
        System.out.print(ans);
    }
 */

/*
    Q 1.1   given   abd]d]f]]e]]

    class TreeNode {
        String value;
        List<TreeNode> childern;
        TreeNode(String value) {
            this.value = value;
            childern = new ArrayList<>();
        }
    }

    int index = 0;
    public TreeNode constructTree(String preOrder) {
        TreeNode node = new TreeNode(String.valueOf(preOrder.charAt(index)));
        index++;
        while (!String.valueOf(preOrder.charAt(index)).equals("]")) {
            node.childern.add(constructTree(preOrder));
        }
        index++;
        return node;
    }

    public static void main(String[] args) {
        Main main = new Main();
        TreeNode ans = main.constructTree("abd]d]f]]e]]");
        System.out.print(ans);
    }
 */

/*
    construct Cartesian tree given in-order, no dup

                15
               /   \
             8      7
            /       /\
           4       5   6
                   /\
                  1  3

    public TreeNode construct(int[] inOrder) {
        if (inOrder == null || inOrder.length == 0) return null;
        return helpFn(inOrder, 0, inOrder.length - 1);
    }

    public TreeNode helpFn(int[] inOrder, int left, int right) {
        if (left > right) return null;
        int maxIndex = findMax(inOrder, left, right);
        TreeNode root = new TreeNode(inOrder[maxIndex]);
        root.left = helpFn(inOrder, left, maxIndex - 1);
        root.right = helpFn(inOrder, maxIndex + 1, right);
        return root;
    }

    public int findMax(int[] inOrder, int left, int right) {
        int max = 0;
        int index= 0;
        for (int i = left; i <= right; i++) {
            if (inOrder[i] > max) {
                max = inOrder[i];
                index = i;
            }
        }
        return index;
    }

    public static void main(String[] args) {
        Main main = new Main();
        TreeNode ans = main.construct(new int[]{4,8,15,1,5,3,7,6});
        System.out.print(ans);
    }


    2) if the inorder sequence it not given, it is a stream

    // stack used to store the right most path nodes

    class TreeReader {
        Deque<TreeNode> stack = new LinkedList<>();
        TreeNode root = null;
        public TreeNode read(int value) {
            TreeNode cur = new TreeNode(value);
            TreeNode prev = null;
            while (!stack.isEmpty() && stack.peek().val < value) {
                prev = stack.pop();
            }
            cur.left = prev;
            if (stack.isEmpty()) root = cur;
            else stack.peek().right = cur;
            stack.push(cur);
            return root;
        }
    }
 */

/*
    Q2  construct n-ary tree given pre-order

            3
           /  \
        1      2
               /\
              4  5
      <3,-1> <1,3> <2,3> <4,1> <5,2>
    static class Pair {
        int nodeId;
        int parentId;
        Pair(int nodeId, int parentId) {
            this.nodeId = nodeId;
            this.parentId = parentId;
        }
    }

    class TreeNode {
        int val;
        List<TreeNode> childer;
        TreeNode(int val) {
            this.val = val;
            childer = new ArrayList<>();
        }
    }

    public TreeNode construct(Deque<Pair> list) {
        TreeNode root =  new TreeNode(list.pollFirst().nodeId);
        while (!list.isEmpty() && list.peekFirst().parentId == root.val) {
            root.childer.add(construct(list));
        }
        return root;
    }

    public static void main(String[] args) {
        Main main = new Main();
        Deque<Pair> list = new LinkedList<>();
        list.add(new Pair(3,-1));
        list.add(new Pair(1,3));
        list.add(new Pair(2,3));
        list.add(new Pair(4,2));
        list.add(new Pair(5,2));
        TreeNode ans = main.construct(list);
        System.out.print(ans);
    }
 */

/*
    Q 2.2 check if input is valid preorder

    // stack used to keep right most path nodes

    static class Pair {
        int nodeId;
        int parentId;
        Pair(int nodeId, int parentId) {
            this.nodeId = nodeId;
            this.parentId = parentId;
        }
    }

    public boolean construct(List<Pair> list) {
        Deque<Integer> stack = new LinkedList<>();
        Set<Integer> set = new HashSet<>();
        stack.offerFirst(list.remove(0).nodeId);
        set.add(stack.peekFirst());
        for (Pair p : list) {
            if (!set.add(p.nodeId)) return false; //dup element
            while (!stack.isEmpty() && stack.peekFirst() != p.parentId) {
                stack.pollFirst(); // poll all children node until parent node
            }
            if (stack.isEmpty()) return false;
            stack.offerFirst(p.nodeId); //update right most path node
        }
        return true;
    }

    public static void main(String[] args) {
        Main main = new Main();
        List<Pair> list = new LinkedList<>();
        list.add(new Pair(3,-1));
        list.add(new Pair(1,3));
        list.add(new Pair(2,3));
        list.add(new Pair(4,2));
        list.add(new Pair(5,2));
        boolean ans = main.construct(list);
        System.out.print(ans);
    }
 */