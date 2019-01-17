import Util.TreeNode;
import sun.awt.image.ImageWatched;

import java.util.*;
import java.util.List;

public class Main {

    /*
   index      0   1  2  3  4 5
              0   0  0  1  1 1
             -1  -1 -1  1  1 1
   prefix 0  -1  -2 -3 -2 -1 0

         {0: -1}
         {-1: 0}
         {-2: 1}
         {-3: 2}

  index     0  1   2 3  4 5 6
            1  0  0  0  1 1 1
            1 -1 -1 -1  1 1 1
  prefix  0 1 0  -1 -2 -1 0 1

  map:  {0: -1}
        {1: 0}
        {-1: 2}
        {-2: 3}
     */


    /*

            m[i] represent longest common substring at index i
            induction rule:
            m[i] = m[i - 1][j - 1] + 1   if str[i] == str[j]
                                         else max(m[i - 1][j], m[i][j - 1])

             0 1 2 3 4 5 6 7
        t      c b a b d f e
         0   0 0 0 0 0 0 0 0
    s    1 a 0 0 0 1 1 1 1 1
         2 b 0 0 1 1 2 2 2 2
         3 c 0 1 1 1 2 2 2 2
         4 d 0 1 1 1 2 3 3 3
         5 e 0 1 1 1 2 3 3 4
     */


    /*

                {1, 2, 2, 4},
     */
    public class Element {
        int row;
        int col;
        int val;
        Element(int row, int col, int val) {
            this.row = row;
            this.col = col;
            this.val = val;
        }
    }

    /*
            0       1  2  3  4  5  6
            1,      2, 3, 3, 3, 2, 2
                       end
                          fast


            0 0 0 0
            1 1 1 1
            0 1 1 1
            1 0 1 1


            0 0 0 0
            1 1 1 1
            0 1 2 2
            1 0 1 2

            0 1 2 3 4
            a b c d e

    */



    class Cell {
        int col;
        TreeNode node;
        Cell(int col, TreeNode node) {
            this.col = col;
            this.node = node;
        }
    }

    /*
        Input: nums is [1, 1, 1, 1, 1], S is 3.
        Output: 5
        Explanation:

        -1+1+1+1+1 = 3
        +1-1+1+1+1 = 3
        +1+1-1+1+1 = 3
        +1+1+1-1+1 = 3
        +1+1+1+1-1 = 3

                            1,1,1,1
                               /\
                            1      -1
                          /\        /\
                    1      -1

     */

    /*
        Input: ["abc", "bcd", "acef", "xyz", "az", "ba", "a", "z"],
        Output:
        [
          ["abc","bcd","xyz"],
          ["az","ba"],
          ["acef"],
          ["a","z"]
        ]

        11111155555224434

       [5,1]:6 <-> [4]:3 <-> [2]:2 <-> [3]:1

       delete set if it's empty after moving the key
       increment count:
            key in the map:
                case 1:   3-2-1
                            ^
                case 2:   8-4-2
                            ^
            key not in the map:
                case 1:    3-2-1     add key to the last node with count 1
                               ^
                case 2:    8-4-2 -1   create new node with key count 1
                                  ^
        decrement count:
             key in the map:
                case 1:   3-2-1   the decremented count == next count, add to next set
                            ^
                case 2:   8-4-2  the decremented count > next count, create new node with count - 1
                            ^
                case 3:   count of key ==1, remove from map

Input: 19
Output: true
Explanation:
1^2 + 9^2 = 82
8^2 + 2^2 = 68
6^2 + 8^2 = 100
1^2 + 0^2 + 0^2 = 1


Input:
     01234567890
s = "c a t s a n d d o g"
           t t     t     t

wordDict = ["cat", "cats", "and", "sand", "dog"]
Output:
[
  "cats and dog",
  "cat sand dog"
]


p i n e a p p l e p e n a p p l e
        t         t     t         t

pine apple pen apple
pineapple pen apple
pine applepen apple

     */

    /*

Input: S = "ababcbacadefegdehijhklij"
Output: [9,7,8]
Explanation:
                  012345678    9012345
The partition is "ababcbaca", "defegde", "hijhklij".
{a:8}
{b:5}
{c:7}

{d:14}
{e:15}
{f:11}
{g:13}

1 0 1
i

1
j
                                aab
                                /\\
                       aab       aab     baa
                       /\       /\       /\
                    aab aba   aab aba    baa baa

                                aab
                                /\\
                            aab    baa
                            /\\     /\\
                        aab  aba   baa

                        1 ,2,2
                        /   \
                     1        2
                    / \       /\
                   12        22
                   /\
                 122

        m[i][j] represent if s[i + j] can be make form s1 at i, s2 at j

        m[i][j] = m[i - 1][j] && s1[i] == s3[i + j] || m[i][j - 1] && s2[j] == s3[i + j]


Given the sorted linked list: [-10,-3,0,5,9],

One possible answer is: [0,-3,9,-10,null,5], which represents the following height balanced BST:


-10 -> -3 -> 0 -> 5 ->9
      0
     / \
   -3   9
   /   /
 -10  5


Input: 12
Output:
[
  [2, 6],
  [2, 2, 3],
  [3, 4]
]

                            12
                            /\
                       1   2   3  4   6  12
                          /\\
                       1  2  3  6

         0 1 2 3 4
        [0,1,3,5,6]

        [0] -> min
        [1] -> max
        [2] -> number of nodes

    practice: 0
    makes: 1, 4
    perfect: 2
    coding: 3

    dummy -> 1 -> 2 -> 3 -> 4 -> 5 -> 6
                                         f
                            s
Input:
                      4
                   /      \
                  4        8
                /\         / \
               4  100     8   9


Input: nums1 = [1,7,11], nums2 = [2,4,6], k = 3
Output: [[1,2],[1,4],[1,6]]
Explanation: The first 3 pairs are returned from the sequence:
             [1,2],[1,4],[1,6],[7,2],[7,4],[11,2],[7,6],[11,4],[11,6]
        [1,2] [1,4]
        queue:  [7,2] [1,6][7,4]

   "bbbab"


    01234
 0  12334
 1  01223
 2  00113
 3  00011
 4  00001


Input:nums = [1,1,1], k = 2
Output: 2
  0 1 2 3 4  5 6
 [3,1,3,1,-4,1, 3]    k = 4
0 3 4 7 8  4,5, 8

[3,1][1,3][3,1][3,1,3,1,-4][1,3][3,1,-4,1,3]

equations = [ ["a", "b"], ["b", "c"] ],
values = [2.0, 3.0],
queries = [ ["a", "c"], ["b", "a"], ["a", "e"], ["a", "a"], ["x", "x"] ].

Input:
[[0,1,10],
 [1,0,1],
 [1,2,5],
 [2,0,5]]

Output:
1

Explanation:
Person #0 gave person #1 $10.
Person #1 gave person #0 $1.
Person #1 gave person #2 $5.
Person #2 gave person #0 $5.


Input: quality = [10,20,5], wage = [70,50,30], K = 2
Output: 105.00000
Explanation: We pay 70 to 0-th worker and 35 to 2-th worker.
50
70/10 = 50/20 = 25
70/10 = 30/5 = 60
30/5 = 70/10 = 35
30/5 = 50/20 = 10

70/10

30

        10---------20
                                   50--------60
        10--------------------40
5--------------15
5-------10
                        25----------------55
[1,3,6]



Input: s = "aabbcc", k = 3
Output: "abcabc"
Explanation: The same letters are at least distance 3 from each other.

{a: 2
 b: 2
 c: 2
}

{
 a:3
 b:0
 c:0
}

flights =
[[0,1,1],
[1,0,1],
[1,1,0]]

days =
[[1,3,1],
 [6,0,3],
 [3,3,3]]


m[i] represent max vacation at city i

week 0:
    m[0] = 1
    m[1] = 6
    m[2] = 3

week 1:
    m[0] = max(m[1], m[2]) + 3
    m[1] = max(m[0), m[2] + 0
    m[2] = max(m[0], m[1]) + 3;

week 2:
    m[2] = m[1] +




Input:  n = 2
Output: ["11","69","88","96"]


    1_1            8_8            6_9           9_6
    /\             /\             /\            /\
111 181  101   818  808 888    609 619 689     906 916 986


                  0 1  2  3 4           0 1 2 3 4
Input: quality = [3,1,10,10,1], wage = [4,8,2,2,7], K = 3
Output: 30.66667
Explanation: We pay 4 to 0-th worker, 13.33333 to 2-th and 3-th workers seperately.

k = 1
sum = 4

k = 2
x/4 = 1/3 --> x = 1.3       8
x/4 = 10/3 --> x = 13.3     2
x/4 = 10/3 --> x = 13.3     2
x/4 = 1/3 --> x = 1.3       7

k = 3
x/2 = 3/10   x = 0.6
x/2 = 1/10   x = 0.2
x/2 = 1/10   x = 0.2

x/4 = 10/3

4/3 = 1.3
8/1 = 8
2/10 = 0.2
2/10 = 0.2
7/1 = 7



Input: [1,2,3,4,5]

             1
         /      \
        2           3
       / \          /\
      4   5       6   7
                /\    /\
              8 9     8   9

Output: [[4,5,3],[2],[1]]




[1,2]   [4,7] [8,10]

1 5 6 7 3 4


10
4 5 10 7 3 4 7 4 1 8
20


1 1  + 2
2   + 2


1 + 2
1 + 1 + 1


m[i] represent number of ways to get money i

m[0] = 1

m[1] = m[0] = 1

m[2] = m[0] + m[1] = 1 + 1 = 2

m[3] = m[1] + m[2] = 1 + 2 = 3

m[4] = m[3] + m[2] = 3 + 2 = 5

m[5] = m[4] + m[3] + m[0] = 5 + 3 + 1


            2 1 2
             /\
          2              1
         /\             /\
       2,1  2,2        1,2



            1,2,2
             /\
         1           2
         /\          /\
      1,2           2,2
       /\
     1,2,2
     0     1   2  3
     -10  -3  0 5 9

     0 + 3/2 = 1 = 1

     +--+

Input: S = "ADOBBECODEBANC", T = "ABC"
Output: "BANC"
{A:1
 B:-1
 C:1}

Input:s1 = "ab" s2 = "eidbaooo"
Output:True
Explanation: s2 contains one permutation of s1 ("ba").


Input:
 1---2---3---4---5---6--NULL
         |
         7---8---9---10--NULL
             |
             11--12--NULL

*/



//    int minimumDistance(int numRows, int numColumns, List<List<Integer>> area)
//    {
//        if (area == null || area.size() ==0) return -1;
//        int[][] m = new int[numRows][numColumns];
//        for (int i = 0; i < numRows; i++) {
//            for (int j = 0; j < numColumns; j++) {
//                m[i][j] = area.get(i).get(j);
//            }
//        }
//
//        int[][] visited = new int[numRows][numColumns];
//        for (int i = 0; i < numRows; i++) {
//             Arrays.fill(visited[i], Integer.MAX_VALUE);
//         }
//        int[] des = new int[2];
//        des[0] = -1;
//        des[1] = -1;
//        dfs(m, 0, 0, 0, visited, des, numRows, numColumns);
//        if (des[0] == -1 && des[1] == -1) return -1;
//        if (visited[des[0]][des[1]] == Integer.MAX_VALUE) return -1;
//        return visited[des[0]][des[1]];
//    }
//
//    public void dfs(int[][] m, int i, int j, int count, int[][] visited, int[] des, int rows, int cols) {
//        if (i < 0 || i >= rows || j < 0 || j >= cols || m[i][j] == 0) return;
//        if (count >= visited[i][j]) return;
//
//        if (m[i][j] == 9) {
//            des[0] = i;
//            des[1] = j;
//        }
//
//        visited[i][j] = count;
//        dfs(m, i + 1, j, count + 1, visited, des, rows, cols);
//        dfs(m, i - 1, j, count + 1, visited, des, rows, cols);
//        dfs(m, i, j + 1, count + 1, visited, des, rows, cols);
//        dfs(m, i, j - 1, count + 1, visited, des, rows, cols);
//    }

    /*



        m[i][j] longest palindromic subseq at index i and j

        induction rule:
                if s(i) == s(j)    m[i][j] = m[i + 1][j - 1] + 2
                else                m[i][j] = Math.max(m[i + 1][j], m[i][j - 1]

       x x x x x x
       x x x x x x
       x x x x x x

       base case:
                    m[i][j]  = 1  if i == j
                    m[i][j] =  2  i + 1 = j && s(i) == s(j)


         1 3 2 4
           f
             s
     */

    /*

        ab   redred


Input:
org: [4,1,5,2,6,3], seqs: [[5,2,6,3],[4,1,5,2]]
     */

    public boolean sequenceReconstruction(int[] org, List<List<Integer>> seqs) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        Map<Integer, Integer> indegree = new HashMap<>();
        for (List<Integer> seq : seqs) {
            for (int i = 0; i < seq.size() - 1; i++) {
                map.putIfAbsent(seq.get(i), new ArrayList<>());
                indegree.put(seq.get(i), indegree.getOrDefault(seq.get(i), 0));
                if (i == 0) continue;
                map.putIfAbsent(seq.get(i + 1), new ArrayList<>());
                indegree.put(seq.get(i + 1), indegree.getOrDefault(seq.get(i + 1), 0) + 1);
                map.get(seq.get(i)).add(seq.get(i + 1));
            }
        }

        int index = 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.add(org[0]);
        while (!queue.isEmpty()) {
            int size = queue.size();
            if (size > 1) return false;
            int cur = queue.poll();
            if (cur != org[index]) return false;

            for (int neib : map.get(cur)) {
                int degree = indegree.get(neib);
                indegree.put(neib, degree - 1);
                if (indegree.get(neib) == 0) {
                    queue.offer(neib);
                }
            }
            index++;
        }
        return index == org.length;
    }

    public static void main(String[] args) {
        Main main = new Main();
        List<List<Integer>> list = new LinkedList<>();
        List<Integer> l1 = new LinkedList<>();
        l1.add(1);l1.add(2);
        //l1.add(5);l1.add(2);l1.add(6);l1.add(3);
        List<Integer> l2 = new LinkedList<>();
        l2.add(1);l2.add(3);
        //l2.add(4);l2.add(1);l2.add(5);l2.add(2);
        list.add(l1);
        list.add(l2);
        boolean res = main.sequenceReconstruction(new int[] {1,2,3}, list);
        System.out.println(res);
//        List<Integer> l1 = new ArrayList<>();
//        l1.add(1);
//        l1.add(0);
//        l1.add(0);
//
//        List<Integer> l2 = new ArrayList<>();
//        l2.add(1);
//        l2.add(0);
//        l2.add(0);
//
//        List<Integer> l3 = new ArrayList<>();
//        l3.add(1);
//        l3.add(9);
//        l3.add(1);
//
//        List<List<Integer>> list = new ArrayList<>();
//        list.add(l1);
//        list.add(l2);
//        list.add(l3);
//
//        int res = main.minimumDistance(3,3, list);
//        System.out.println(res);
    }
}