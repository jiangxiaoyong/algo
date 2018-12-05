package Wednesday.Sunday.LinkedList;


import Util.ListNode;

public class Main {



    static class ListNodeChild {
        int val;
        ListNodeChild child;
        ListNodeChild next;
        ListNodeChild prev;
        ListNodeChild(int val) {
            this.val = val;
        }
    }

    public ListNodeChild flattenDoubleLinkedList(ListNodeChild head) {
        if (head == null || head.next == null) return head;

        ListNodeChild queueHead = head;
        ListNodeChild queueTail = findTail(head);

        while (queueHead != null) {
            if (queueHead.child != null) {
                queueTail.next = queueHead.child;
                queueHead.child.prev = queueTail;
                queueHead.child = null;
                queueTail = findTail(queueTail);
            }
            queueHead = queueHead.next;
        }
        return head;
    }

    public ListNodeChild findTail(ListNodeChild head) {
        ListNodeChild cur = head;
        while (cur.next != null) {
            cur = cur.next;
        }
        return cur;
    }

    public static void main(String[] args) {
        Main main = new Main();

        ListNodeChild n1 = new ListNodeChild(1);
        ListNodeChild n2 = new ListNodeChild(2);
        ListNodeChild n3 = new ListNodeChild(3);
        ListNodeChild n4= new ListNodeChild(4);
        ListNodeChild n5 = new ListNodeChild(5);

        n1.next = n2;
        n2.prev = n1;
        n1.child = n3;
        n3.prev = n1;
        n3.next = n4;
        n4.prev = n3;
        n4.child = n5;
        n5.prev = n4;

        ListNodeChild res = main.flattenDoubleLinkedList(n1);
        while (res != null) {
            System.out.println(res.val);
            res = res.next;
        }
    }
}

/**
 *  Class 1
 */

/*
    Q 1.2 Reverse double linked list

    static class ListNode {
        int val;
        ListNode prev;
        ListNode next;
        ListNode(int val) {
            this.val = val;
        }
    }

    public ListNode reverseDoubleLinkedList(ListNode head) {
        ListNode cur = head;
        ListNode prev = null;
        while (cur != null) {
            swap(cur);
            prev = cur;
            cur = cur.prev;  //use the previous as next because it's already reversed
        }
        return prev;
    }

    public void swap(ListNode node) {
        ListNode next = node.next;
        node.next = node.prev;
        node.prev = next;
    }

    public static void main(String[] args) {
        ListNode n1 = new ListNode(1);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(3);
        ListNode n4=  new ListNode(4);
        n1.next = n2;
        n2.prev = n1;
        n2.next = n3;
        n3.prev = n2;
        n3.next = n4;
        n4.prev = n3;

        Main main = new Main();
        ListNode res = main.reverseDoubleLinkedList(n1);
        while (res != null) {
            System.out.println(res.val);
            res = res.next;
        }
    }
 */

/*
    1.3 Reverse circular single linked list

    public ListNode reverseCircularSingleLinkedList(ListNode head) {
        ListNode prev = null;
        ListNode cur = head;
        ListNode next = null;
        while (true) {
            next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
            if (cur.next == head) break;
        }
        // cur is at the tail of the original list
        cur.next = prev;
        head.next = cur;
        return head;
    }

    public static void main(String[] args) {
        Main main = new Main();

        ListNode n1 = new ListNode(1);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(3);
        ListNode n4 = new ListNode(4);

        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n1;

        ListNode res = main.reverseCircularSingleLinkedList(n1);
        while (res != null) {
            System.out.println(res.val);
        }
    }
 */

/*
    2. Single lined list a and b, intersection

    public ListNode intersection(ListNode a, ListNode b) {
        int aLen = getLength(a);
        int bLen = getLength(b);

        if (aLen > bLen) {
            return intersect(a, b, aLen - bLen);
        }
        return intersect(b, a, bLen - aLen);
    }

    public ListNode intersect(ListNode longer, ListNode shorter, int diff) {
        while (diff > 0) {
            longer = longer.next;
            diff--;
        }

        while (longer != shorter) {
            longer = longer.next;
            shorter = shorter.next;
        }
        return longer;
    }

    public int getLength(ListNode node) {
        ListNode cur = node;
        int count = 0;
        while (cur != null) {
            count++;
            cur = cur.next;
        }
        return count;
    }

    public static void main(String[] args) {
        Main main = new Main();

        ListNode n1 = new ListNode(1);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(3);
        ListNode n4 = new ListNode(4);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;

        ListNode n5 = new ListNode(5);
        n5.next = n3;

        ListNode res = main.intersection(n1, n5);
        System.out.println(res.val);
    }
 */

/*
    3.0 Add ont to linked list
    v1   1->2->3->null
        +1
       = 2->2->3->null
       321 + 1 = 322

    public ListNode addOne(ListNode node) {
        int carry = 1;
        ListNode prev = null;
        ListNode cur = node;
        while (cur != null && carry == 1) {
            carry = (cur.val + carry) / 10;
            cur.val = (cur.val + carry) % 10;
            prev = cur;
            cur = cur.next;
        }
        if (cur == null) {
            prev.next = new ListNode(1);
        }
        return node;
    }

    public static void main(String[] args) {
        Main main = new Main();

        ListNode n1 = new ListNode(9);
        ListNode n2 = new ListNode(9);
        ListNode n3 = new ListNode(9);
        n1.next = n2;
        n2.next = n3;

        ListNode res = main.addOne(n1);
        while (res != null) {
            System.out.println(res.val);
            res = res.next;
        }
    }
 */

/*
    3.0
    v2
        1 -> 2-> 3->null
                +1
     = 1 -> 2 -> 4 -> null

    public ListNode addOne(ListNode head) {
        int carry = recursiveAdd(head);
        if (carry == 0) {
            return head;
        }
        ListNode newHead = new ListNode(1);
        newHead.next = head;
        return newHead;
    }

    public int recursiveAdd(ListNode head) {
        if (head == null) {
            return 1;
        }

        int carry = recursiveAdd(head.next);
        head.val += carry;
        carry = head.val / 10;
        head.val %= 10;
        return carry;
    }

    public static void main(String[] args) {
        Main main = new Main();

        ListNode n1 = new ListNode(9);
        ListNode n2 = new ListNode(9);
        ListNode n3 = new ListNode(9);
        n1.next = n2;
        n2.next = n3;

        ListNode res = main.addOne(n1);
        while (res != null) {
            System.out.println(res.val);
            res = res.next;
        }
    }
 */

/*
    3.1 Add two list
    v1
        1 -> 2 -> 3 -> null
        7 -> 8 -> 9 -> 9 -> null
      = 8 -> 0 -> 3 -> 0 -> 1 -> null
      321 +  9987 = 10308

    public ListNode addTwoLists(ListNode node1, ListNode node2) {
        if (node1 == null) return node2;
        if (node2 == null) return node1;

        ListNode dummy = new ListNode(-1);
        ListNode tail = dummy;
        int carry = 0;
        while (node1 != null || node2 != null) {
            int val1 = node1 == null ? 0 : node1.val;
            int val2 = node2 == null ? 0 : node2.val;

            int sum = val1 + val2 + carry;
            int val = sum % 10;
            carry = sum / 10;
            ListNode newNode = new ListNode(val);
            tail.next = newNode;

            //advance node1, node2, tail
            tail = tail.next;
            if (node1 != null) node1 = node1.next;
            if (node2 != null) node2 = node2.next;
        }

        if (carry != 0) {
            tail.next = new ListNode(1);
        }
        return dummy.next;
    }

    public static void main(String[] args) {
        Main main = new Main();

        ListNode n1 = new ListNode(1);
        n1.next = new ListNode(2);
        n1.next.next = new ListNode(3);

        ListNode n2 = new ListNode(7);
        n2.next = new ListNode(8);
        n2.next.next = new ListNode(9);
        n2.next.next.next = new ListNode(9);

        ListNode res = main.addTwoLists(n1, n2);
        while (res != null) {
            System.out.println(res.val);
            res = res.next;
        }
    }
 */

/*
    3.1
    v2
    1 -> 2 -> 3 -> null
         9 -> 8 -> null
    2 -> 2 -> 1 -> null
    123 + 98 = 221

    public ListNode addTwoLists(ListNode a, ListNode b) {
        if (a == null) return b;
        if (b == null) return a;

        int aLen = getLength(a);
        int bLen = getLength(b);

        ListNode head = aLen > bLen ? recursive(a, b, aLen - bLen) : recursive(b, a, bLen - aLen);
        if (head.val < 10) {
            return head;
        }
        head.val %= 10;
        ListNode newHead = new ListNode(1);
        newHead.next = head;
        return newHead;
    }

    public ListNode recursive(ListNode longer, ListNode shorter, int diff) {
        if (longer == null) {
            return null;
        }

        //calling sub-problem
        ListNode nextLonger = longer.next;
        ListNode nextShorter = diff > 0 ? shorter : shorter.next;
        int nextDiff = diff > 0? diff - 1 : 0;
        ListNode subHead = recursive(nextLonger, nextShorter, nextDiff);

        //induction rul: what do we do at current level
        // calculate current head value
        int carry = subHead == null ? 0 : subHead.val / 10;
        int aval = longer.val;
        int bval = diff > 0 ? 0 : shorter.val;
        int sum = carry + aval + bval;
        ListNode newNode = new ListNode(sum);

        //update val in subHead
        if (subHead != null) subHead.val %= 10;
        newNode.next = subHead;
        return newNode;
    }
 */

/*
 *      Class 2
 */

/*
    3. Get nth node from tail

    1 -> 2 -> 3 -> 4 -> 5
                           f
                   s

    public ListNode getNthNode(ListNode root, int k) {
        if (root == null) return null;

        ListNode fast = root;
        while (k > 0 && fast != null) {
            fast = fast.next;
            k--;
        }

        if (k > 0) return null;

        ListNode slow = root;
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }

        return slow;
    }

    public static void main(String[] args) {
        Main main = new Main();

        ListNode n1 = new ListNode(1);
        n1.next = new ListNode(2);
        n1.next.next = new ListNode(3);
        n1.next.next.next = new ListNode(4);
        n1.next.next.next.next = new ListNode(5);

        ListNode res = main.getNthNode(n1, 2);
        System.out.println(res.val);
    }
 */

/*
    4 delete nth node from tail
    public ListNode deleteNthNode(ListNode root, int k) {
        if (root == null) return null;

        ListNode fast = root;
        while (k > 0 && fast != null) {
            fast = fast.next;
            k--;
        }

        if (k > 0) return root;

        ListNode dummy = new ListNode(-1);
        dummy.next = root;
        ListNode slow = dummy;
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }
        slow.next = slow.next.next;
        return dummy.next;
    }

    public static void main(String[] args) {
        Main main = new Main();

        ListNode n1 = new ListNode(1);
        n1.next = new ListNode(2);
        n1.next.next = new ListNode(3);
        n1.next.next.next = new ListNode(4);
        n1.next.next.next.next = new ListNode(5);

        ListNode res = main.deleteNthNode(n1, 2);
        System.out.println(res.val);
    }
 */

/*
        4.1 remove vowels

     public ListNode removeNodesContainsVowels(ListNode root, int k) {
        if (root == null) return null;

        ListNode dummy = new ListNode(-1);
        dummy.next = root;
        ListNode tail = dummy;

        while (tail.next != null) {
            if (isVowle(tail.next)) {
                tail.next = tail.next.next;
            } else {
                tail = tail.next;
            }
        }
        return dummy.next;
    }

    public boolean isVowle(ListNode node) {
        return node.val == 'a' || node.val == 'e' || node.val == 'i';
    }

    public static void main(String[] args) {
        Main main = new Main();

        ListNode n1 = new ListNode(1);
        n1.next = new ListNode(2);
        n1.next.next = new ListNode(3);
        n1.next.next.next = new ListNode(4);
        n1.next.next.next.next = new ListNode(5);

        ListNode res = main.removeNodesContainsVowels(n1, 2);
        System.out.println(res.val);
    }
 */

/*
        4.2 remove duplicate

    public ListNode removeNodesContainsVowels(ListNode root, int k) {
        if (root == null) return root;

        ListNode tail = root;
        while (tail.next != null) {
            if (tail.next.val == tail.val) {
                tail.next = tail.next.next;
            } else {
                tail = tail.next;
            }
        }
        return root;
    }

    public static void main(String[] args) {
        Main main = new Main();

        ListNode n1 = new ListNode(1);
        n1.next = new ListNode(2);
        n1.next.next = new ListNode(3);
        n1.next.next.next = new ListNode(4);
        n1.next.next.next.next = new ListNode(5);

        ListNode res = main.removeNodesContainsVowels(n1, 2);
        System.out.println(res.val);
    }
 */

/*
    remove dup without any

    dummy -> 2  ->2->   2 -> 3->
         tail.next     fast


    public ListNode removeToNoDup(ListNode head) {
        if (head == null) return head;

        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode tail = dummy;
        //group by group all the nodes in the given list instead of one by one
        while (tail.next != null) {
            //check the group of nodes with same val as tail.next
            ListNode fast = tail.next;
            //fast eventually stops at the last node with same val
            while (fast.next != null && fast.next.val == tail.next.val) {
                fast = fast.next;
            }
            //check duplicates
            if (fast != tail.next) {
                tail.next = fast.next;
            } else {
                tail = tail.next;
            }
        }
        return dummy.next;
    }

    public static void main(String[] args) {
        Main main = new Main();

        ListNode n1 = new ListNode(1);
        n1.next = new ListNode(2);
        n1.next.next = new ListNode(3);
        n1.next.next.next = new ListNode(4);
        n1.next.next.next.next = new ListNode(5);
        n1.next.next.next.next.next = new ListNode(5);

        ListNode res = main.removeToNoDup(n1);
        System.out.println(res.val);
    }
 */

/*
    4.3 remove node by indices

    public ListNode removeToNoDup(ListNode head, int[] array) {
        if (head == null) return head;

        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode tail = dummy;

        int count = 0;
        int indexOfArray = 0; //next node we need to remove
        while (tail.next != null && indexOfArray < array.length) {
            if (count == array[indexOfArray]) {
                tail.next = tail.next.next;
                indexOfArray++;
            } else {
                tail = tail.next;
            }
            count++;
        }
        return dummy.next;
    }

    public static void main(String[] args) {
        Main main = new Main();

        ListNode n1 = new ListNode(1);
        n1.next = new ListNode(2);
        n1.next.next = new ListNode(3);
        n1.next.next.next = new ListNode(4);
        n1.next.next.next.next = new ListNode(5);

        ListNode res = main.removeToNoDup(n1, new int[] {1, 3});
        System.out.println(res.val);
    }
 */

/*
    6   determine if list is palindrome, (recursion method)

    ListNode head = null;
    public boolean isPalindrome(ListNode head) {
        if (head == null) return false;
        this.head = head;
        return recursion(head);
    }

    public boolean recursion(ListNode tail) {
        if (tail ==  null) return true;
        if (!recursion(tail.next)) return false;
        if (head.val != tail.val) return false;
        head = head.next;
        return true;
    }

    public static void main(String[] args) {
        Main main = new Main();

        ListNode n1 = new ListNode(1);
        n1.next = new ListNode(2);
        n1.next.next = new ListNode(3);
        n1.next.next.next = new ListNode(2);
        n1.next.next.next.next = new ListNode(1);

        boolean res = main.isPalindrome(n1);
        System.out.println(res);
    }
 */

/*
    7.1 reverse k group linked list

    public ListNode reverseListByGroup(ListNode head, int k) {
        if (head == null || head.next == null) return head;

        return recursive(head, k);
    }

    public ListNode recursive(ListNode head, int k) {
        if (head == null || head.next == null) return head;

        ListNode kth = head;
        int c = k;
        while (c > 1 && kth != null) {
            kth = kth.next;
            c--;
        }

        if (c > 1) return head;

        ListNode next = kth.next;
        kth.next = null;

        ListNode newHead = reverse(head);
        head.next = recursive(next, k);
        return newHead;
    }

    public ListNode reverse(ListNode head) {
        ListNode prev = null;
        ListNode next = null;
        ListNode cur = head;

        while (cur != null) {
            next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        return prev;
    }

    public static void main(String[] args) {
        Main main = new Main();

        ListNode n1 = new ListNode(1);
        n1.next = new ListNode(2);
        n1.next.next = new ListNode(3);
        n1.next.next.next = new ListNode(4);
        n1.next.next.next.next = new ListNode(5);
        n1.next.next.next.next.next = new ListNode(6);
        n1.next.next.next.next.next.next = new ListNode(7);

        ListNode res = main.reverseListByGroup(n1, 3);
        while (res != null) {
            System.out.print(res.val);
            res = res.next;
        }
    }
 */

/*
    7.2 reverse k group by odd level

   public ListNode reverseListByGroup(ListNode head, int k) {
        if (head == null || head.next == null) return head;

        return recursive(head, k, 1);
    }

    public ListNode recursive(ListNode head, int k, int level) {
        if (head == null || head.next == null) return head;

        ListNode kth = head;
        int c = k;
        while (c > 1 && kth != null) {
            kth = kth.next;
            c--;
        }

        if (c > 1) return head;

        ListNode next = kth.next;
        kth.next = null;
        ListNode sub = recursive(next, k, level + 1);

        if (level % 2 != 0) {
            ListNode newHead = reverse(head);
            head.next = sub;
            return newHead;
        } else {
            kth.next = sub;
            return head;
        }
    }

    public ListNode reverse(ListNode head) {
        ListNode prev = null;
        ListNode next = null;
        ListNode cur = head;

        while (cur != null) {
            next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        return prev;
    }

    public static void main(String[] args) {
        Main main = new Main();

        ListNode n1 = new ListNode(1);
        n1.next = new ListNode(2);
        n1.next.next = new ListNode(3);
        n1.next.next.next = new ListNode(4);
        n1.next.next.next.next = new ListNode(5);
        n1.next.next.next.next.next = new ListNode(6);
        n1.next.next.next.next.next.next = new ListNode(7);

        ListNode res = main.reverseListByGroup(n1, 3);
        while (res != null) {
            System.out.print(res.val);
            res = res.next;
        }
    }
 */

/*
    7.3 move k the element to the head position
    1-2-3-4-5-6-7   3-1-2-6-4-5-7

     public ListNode reverseListByGroup(ListNode head, int k) {
        if (head == null || head.next == null) return head;

        return recursive(head, k);
    }

    public ListNode recursive(ListNode head, int k) {
        if (head == null || head.next == null) return head;

        ListNode prev = head;
        int c = k -1;
        while (c > 1 && prev != null) {
            prev = prev.next;
            c--;
        }
        if (c > 1) return head;


        ListNode sub = recursive(prev.next.next, k);

        ListNode newHead = prev.next;
        prev.next = sub;
        newHead.next = head;
        return newHead;
    }



    public static void main(String[] args) {
        Main main = new Main();

        ListNode n1 = new ListNode(1);
        n1.next = new ListNode(2);
        n1.next.next = new ListNode(3);
        n1.next.next.next = new ListNode(4);
        n1.next.next.next.next = new ListNode(5);
        n1.next.next.next.next.next = new ListNode(6);
        n1.next.next.next.next.next.next = new ListNode(7);

        ListNode res = main.reverseListByGroup(n1, 3);
        while (res != null) {
            System.out.print(res.val);
            res = res.next;
        }
    }
 */

/*
    Flatten linked list

    static class ListNodeChild {
        int val;
        ListNodeChild child;
        ListNodeChild next;
        ListNodeChild prev;
        ListNodeChild(int val) {
            this.val = val;
        }
    }

    public ListNodeChild flattenDoubleLinkedList(ListNodeChild head) {
        if (head == null || head.next == null) return head;

        ListNodeChild queueHead = head;
        ListNodeChild queueTail = findTail(head);

        while (queueHead != null) {
            if (queueHead.child != null) {
                queueTail.next = queueHead.child;
                queueHead.child.prev = queueTail;
                queueHead.child = null;
                queueTail = findTail(queueTail);
            }
            queueHead = queueHead.next;
        }
        return head;
    }

    public ListNodeChild findTail(ListNodeChild head) {
        ListNodeChild cur = head;
        while (cur.next != null) {
            cur = cur.next;
        }
        return cur;
    }

    public static void main(String[] args) {
        Main main = new Main();

        ListNodeChild n1 = new ListNodeChild(1);
        ListNodeChild n2 = new ListNodeChild(2);
        ListNodeChild n3 = new ListNodeChild(3);
        ListNodeChild n4= new ListNodeChild(4);
        ListNodeChild n5 = new ListNodeChild(5);

        n1.next = n2;
        n2.prev = n1;
        n1.child = n3;
        n3.prev = n1;
        n3.next = n4;
        n4.prev = n3;
        n4.child = n5;
        n5.prev = n4;

        ListNodeChild res = main.flattenDoubleLinkedList(n1);
        while (res != null) {
            System.out.println(res.val);
            res = res.next;
        }
    }
 */