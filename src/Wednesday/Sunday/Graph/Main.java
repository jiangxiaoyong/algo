package Wednesday.Sunday.Graph;


import java.util.*;

public class Main {







/*
                         1234560
                           /\\\
               1        12   123   1234   12345
              /\       /\   /\
        1+2  1+23 1+234
        /\
    1+2+3 1+2+34 1+2+345
 */

    static class Person {
        String name;
        int cost;
        List<Person> neibs;
        Person(String name, int cost) {
            this.name = name;
            this.cost = cost;
            this.neibs = new LinkedList<>();
        }
    }

    static class Node {
        Person person;
        int cost;
        Node(Person person, int cost) {
            this.person = person;
            this.cost = cost;
        }
    }

    static class BFSIterator implements Iterator<Person>{
        PriorityQueue<Node> pq;
        Set<Person> expanded;
        Map<Person, Integer> generated;
        BFSIterator(Person ceo) {
            this.pq = new PriorityQueue<>(1, new Comparator<Node>() {
                @Override
                public int compare(Node o1, Node o2) {
                    if (o1.cost == o2.cost) return 0;
                    return o1.cost > o2.cost ? 1 : -1;
                }
            });
            this.pq.offer(new Node(ceo, 0));
            expanded = new HashSet<>();
            generated = new HashMap<>();
        }

        @Override
        public boolean hasNext() {
            while (!this.pq.isEmpty() && expanded.contains(this.pq.peek())) {
                this.pq.poll();
            }
            return !pq.isEmpty();
        }

        @Override
        public Person next() {
            if (!hasNext()) throw new RuntimeException("Empty");
            Node cur = this.pq.poll();
            expanded.add(cur.person);
            for (Person neib : cur.person.neibs) {
                if (expanded.contains(neib)) continue;
                int cost = cur.cost + cur.person.cost;
                if (!generated.containsKey(neib)) {
                    this.pq.offer(new Node(neib, cost));
                    generated.put(neib, cost);
                } else if (cost < generated.get(neib)) {
                    this.pq.offer(new Node(neib, cost));
                    generated.put(neib, cost);
                }
            }
            return cur.person;
        }
    }


    public static void main(String[] args) {
        Main main = new Main();
        Person ceo = new Person("ceo", 10);
        Person a1 = new Person("a1", 20);
        Person a2 = new Person("a2", 10);
        Person b1 = new Person("b1", 30);
        Person b2 = new Person("b2", 1);
        Person c1 = new Person("c1", 30);
        Person d1 = new Person("d1", 2);
        List<Person> ceolist = new LinkedList<>();
        ceolist.add(a1);ceolist.add(a2);ceolist.add(b2);
        ceo.neibs = ceolist;

        List<Person> a1list = new LinkedList<>();
        a1list.add(b1);a1list.add(b2);
        a1.neibs = a1list;

        List<Person> a2list = new LinkedList<>();
        a2list.add(c1);
        a2.neibs = a2list;

        List<Person> b2list = new LinkedList<>();
        b2list.add(d1);
        b2.neibs = b2list;

        BFSIterator it = new BFSIterator(ceo);
        while (it.hasNext()) {
            Person p = it.next();
            System.out.println("cost:" + p.cost + " name:" + p.name);
        }
    }
}

/*
    class 0 Side topic: Sparse data structure
 */

/*
    Q1  Dot product of two parse array
    A = [1,2,0,0,3]
    B = [0,1,3,0,2]

    class Pair {
        int index;
        int val;
        Pair(int index, int val) {
            this.index = index;
            this.val = val;
        }
    }

    public int dotProduct(int[] a1, int[] a2) {
        List<Pair> list1 = new ArrayList<>();
        List<Pair> list2 = new ArrayList<>();
        for (int i = 0; i < a1.length; i++) {
            if (a1[i] != 0) {
                list1.add(new Pair(i, a1[i]));
            }
        }
        for (int i = 0; i < a2.length; i++) {
            if (a2[i] != 0) {
                list2.add(new Pair(i, a2[i]));
            }
        }

        int i = 0, j = 0;
        int sum = 0;
        while (i < list1.size() && j < list2.size()) {
            if (list1.get(i).index == list2.get(j).index) {
                sum += list1.get(i).val * list2.get(j).val;
                i++;
                j++;
            } else if (list1.get(i).index < list2.get(j).index) {
                i++;
            } else {
                j++;
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int res = main.dotProduct(new int[]{1,2,0,0,3}, new int[]{0,1,3,0,2});
        System.out.println(res);
    }
 */

/*
    Q2 2d sparse matrix dot product     LC 311

        represent each matrix as List<Triple>
        e.g
        {7,0,0,1},
        {0,0,0,0},
        {0,2,3,0},
        {4,0,0,0}
        List<[0,0,7], [0,3,1],[2,1,2],[2,2,3],[3,0,4]>

    class Triple {
        int x;
        int y;
        int val;
        Triple(int x, int y, int val) {
            this.x = x;
            this.y = y;
            this.val = val;
        }
    }

    public int[][] multiply(int[][] A, int[][] B) {
        List<Triple> listA = new ArrayList<>();
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                if (A[i][j] != 0) {
                    listA.add(new Triple(i, j, A[i][j]));
                }
            }
        }

        List<Triple> listB = new ArrayList<>();
        for (int i = 0; i < B.length; i++) {
            for (int j = 0; j < B[0].length; j++) {
                if (B[i][j] != 0) {
                    listB.add(new Triple(i, j, B[i][j]));
                }
            }
        }

        int[][] res = new int[A.length][B[0].length];
        int sum = 0;
        for (Triple a : listA) {
            for (Triple b : listB) {
                if (a.y == b.x) { // x is row, y is col, only a col == b row
                    res[a.x][b.y] += a.val * b.val;
                }
            }
        }
        return res;
    }
 */

/*
    class 1 BFS     The graph representation problem
 */

/*
    Q1 Array hopper IV

    Minimum jumps to reach the right end of array. Array[i] means the max jump distance, either to left or right

    public int arrayHopper(int[] array) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        Set<Integer> set= new HashSet<>();
        int count = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int cur = queue.poll();
                if (cur == array.length - 1) return count;
                for (int index = Math.max(0, cur - array[cur]); index <= Math.min(array.length - 1, cur + array[cur]); index++) {
                    if (set.add(index)) {
                        queue.offer(index);
                    }
                }
            }
            count++;
        }
        return -1;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int res = main.arrayHopper(new int[]{1,3,1,2,2});
        System.out.println(res);
    }
*/

/*
    Strategy 1: About number of init and destination
 */
/*
    Q2. calculate the distance form each 1 to the closest 0

    010
    111
    101
    --->
    010
    121
    010

    Time: O(1 * BFS)
    put all 0s into the queue as the initial nodes
    run BFS until all 1s are covered

    public void distanceFrom1toClosest0(int[][] matrix) {
        List<int[]> zeros = new LinkedList<>();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    zeros.add(new int[]{i, j});
                }
            }
        }
        boolean[][] visited = new boolean[matrix.length][matrix[0].length];
        Queue<int[]> queue = new LinkedList<>();
        for (int[] z : zeros) {
            queue.offer(z);
            visited[z[0]][z[1]] = true;
        }

        int[][] dirs = new int [][] {{0,1}, {0,-1},{1,0},{-1,0}};
        int count = 1;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] cur = queue.poll();
                int x = cur[0];
                int y = cur[1];
                for (int[] dir : dirs) {
                    int dx = dir[0];
                    int dy = dir[1];
                    if (x + dx < matrix.length && x + dx >= 0 && y + dy < matrix[0].length && y + dy >= 0 &&
                    !visited[x + dx][y + dy]) {
                        visited[x + dx][y + dy] = true;
                        matrix[x + dx][y + dy] = count;
                        queue.offer(new int[]{x + dx, y + dy});
                    }
                }
            }
            count++;
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        int[][] matrix = new int[][] {
                {0,1,0},
                {1,1,1},
                {0,1,0}
        };
        main.distanceFrom1toClosest0(matrix);
    }
 */

/*
    class 1 :   Topological order 2
 */

/*
    Q1  return the lexical order of topological order

    // using priority queue to ensure lexical order

    public List<Integer> getLexiOrderTopo(int[][] edges) {
        Map<Integer, Integer> indegree = new HashMap<>();
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int[] e : edges) {
            map.putIfAbsent(e[0], new LinkedList<>());
            map.putIfAbsent(e[1], new LinkedList<>());
            map.get(e[0]).add(e[1]);
            indegree.putIfAbsent(e[0], 0);
            indegree.put(e[1], indegree.getOrDefault(e[1], 0) + 1);
        }

        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int key : indegree.keySet()) {
            if (indegree.get(key) == 0) { //input all nodes with indegree 0 into initial queue
                queue.offer(key);
            }
        }

        List<Integer> res = new ArrayList<>();
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            res.add(cur);
            for (int neib : map.get(cur)) {
                indegree.put(neib, indegree.get(neib) - 1);
                if (indegree.get(neib) == 0) {
                    queue.offer(neib);
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int[][] input = new int[][] {
                {1,2},
                {1,4},
                {3,2},
                {3,4},
                {2,4}
        };
        List<Integer> res = main.getLexiOrderTopo(input);
        System.out.println(res);
    }
 */

/*
    Q2  check if input order is topological order

    public boolean checkIfTopoOrder(int[][] edges, int[] order) {
        Map<Integer, Integer> indegree = new HashMap<>();
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int[] e : edges) {
            map.putIfAbsent(e[0], new LinkedList<>());
            map.putIfAbsent(e[1], new LinkedList<>());
            map.get(e[0]).add(e[1]);
            indegree.putIfAbsent(e[0], 0);
            indegree.put(e[1], indegree.getOrDefault(e[1], 0) + 1);
        }
        Set<Integer> set = new HashSet<>();
        for (int key : indegree.keySet()) {
            if (indegree.get(key) == 0) {
                set.add(key);
            }
        }
        int index = 0;
        while (!set.isEmpty()) {
            int cur = order[index++];
            if (!set.contains(cur)) {
                return false;
            }
            //follow the order of input order array to delete set, otherwise random delete set may result in cur not in set
            set.remove(cur);

            for (int neib : map.get(cur)) {
                indegree.put(neib, indegree.get(neib) - 1);
                if (indegree.get(neib) == 0) {
                    set.add(neib);
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int[][] input = new int[][] {
                {1,2},
                {1,4},
                {3,2},
                {3,4},
                {2,4}
        };
        boolean res = main.checkIfTopoOrder(input, new int[] {3,1,2,4});
        System.out.println(res);
    }
 */

/*
    Q3  find all possible topological orders

    what are the branches of current level?
        - all indegree 0 tasks

     public List<List<Integer>> allTopoOrder(int[][] edges) {
        Map<Integer, Integer> indegree = new HashMap<>();
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int[] e : edges) {
            map.putIfAbsent(e[0], new LinkedList<>());
            map.putIfAbsent(e[1], new LinkedList<>());
            map.get(e[0]).add(e[1]);
            indegree.putIfAbsent(e[0], 0);
            indegree.put(e[1], indegree.getOrDefault(e[1], 0) + 1);
        }

        List<List<Integer>> res = new LinkedList<>();
        dfs(map, indegree, new LinkedList<>(), res);

        return res;
    }

    public void dfs(Map<Integer, List<Integer>> map, Map<Integer, Integer> indegree, List<Integer> path, List<List<Integer>> res) {
        if (path.size() == map.size()) {
            res.add(new ArrayList<>(path));
            return;
        }

        for (int key : indegree.keySet()) {
            if (indegree.get(key) == 0 && !path.contains(key)) {
                path.add(key);
                for (int neib : map.get(key)) {
                    indegree.put(neib, indegree.get(neib) - 1);
                }
                dfs(map, indegree, path, res);
                for (int neib : map.get(key)) {
                    indegree.put(neib, indegree.get(neib) + 1);
                }
                path.remove(path.size() - 1);
            }
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        int[][] input = new int[][] {
                {1,2},
                {1,4},
                {3,2},
                {3,4},
                {2,4}
        };
        List<List<Integer>> res = main.allTopoOrder(input);
        System.out.println(res);
    }
 */
/*
    Q4.1    get minimum time to finish all the tasks

    static class Node {
        int val;
        int time;
        Node(int val, int time) {
            this.val = val;
            this.time = time;
        }
    }

    public int minTimeFinishAllTasks(Node[][] edges) {
        Map<Node, List<Node>> map = new HashMap<>();
        Map<Node, Integer> indegree = new HashMap<>();
        for (Node[] e : edges) { //construct reversed graph
            map.putIfAbsent(e[0], new LinkedList<>());
            map.putIfAbsent(e[1], new LinkedList<>());
            map.get(e[1]).add(e[0]);
            indegree.putIfAbsent(e[1], 0);
            indegree.put(e[0], indegree.getOrDefault(e[0], 0) + 1);
        }

        int res = 0;
        Map<Node, Integer> visited = new HashMap<>();
        for (Node node : indegree.keySet()) {
            if (indegree.get(node) == 0) {
                res = Math.max(res, dfs(node, map, visited));
            }
        }
        return res;
    }

    public int dfs(Node cur, Map<Node, List<Node>> map, Map<Node, Integer> visited) {
        if (visited.containsKey(cur)) {
            return visited.get(cur);
        }
        if (map.get(cur).size() == 0) {
            return cur.time;
        }

        int max = 0;
        for (Node neib : map.get(cur)) {
            max = Math.max(max, dfs(neib, map, visited)) + cur.time;
        }
        visited.put(cur, max);
        return max;
    }

    public static void main(String[] args) {
        Main main = new Main();
        Node[][] input = new Node[][] {
                {new Node(1,2), new Node(2,4)},
                {new Node(1,2), new Node(4,1)},
                {new Node(3, 3), new Node(2,4)},
                {new Node(3,3), new Node(4,1)},
                {new Node(2,4), new Node(4, 1)}
        };
        int res = main.minTimeFinishAllTasks(input);
        System.out.println(res);
    }
 */

/*
    class 3 Graph representation
 */

/*
    Q1.1    shortest path to the exit for the person

    public int shortestPathToExit(int[][] matrix, int[] start) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int count = 0;
        int[][] dirs = new int[][] {{0,1},{0,-1},{1,0},{-1,0}};
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(start);
        boolean[][] visited = new boolean[rows][cols];
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0;  i < size; i++) {
                int[] cur = queue.poll();
                if (cur[0] == 0 || cur[1] == 0) return count;
                for (int[] dir : dirs) {
                    int dx = dir[0];
                    int dy = dir[1];
                    int nx = cur[0] + dx;
                    int ny = cur[1] + dy;
                    if (nx >= 0 && nx < rows && ny >= 0 && ny < cols && matrix[nx][ny] == 0 && !visited[nx][ny]) {
                        visited[nx][ny] = true;
                        queue.offer(new int[]{nx, ny});
                    }
                }
            }
            count++;
        }
        return count;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int[][] matrix = new int[][] {
                {1,0,1,1,1,1},
                {1,0,0,0,0,1},
                {1,1,0,1,0,0},
                {1,0,0,1,0,1},
                {1,1,1,1,1,1}
        };
        int res = main.shortestPathToExit(matrix, new int[]{3,1});
        System.out.println(res);
    }
 */

/*
    Q4.2    Shortest path to exit if can break at most k obstacles

    vertex(i, j, x)
        -x: # of obstacles broken to reach (i, j)

    public int shortestPathToExitCanBreakAtMostKObstacles(int[][] matrix, int[] start, int[][] exits, int k) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int count = 0;
        int[][] dirs = new int[][] {{0,1},{0,-1},{1,0},{-1,0}};
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(start);
        boolean[][] visited = new boolean[rows][cols];
        visited[start[0]][start[1]] = true;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] cur = queue.poll();
                for (int [] e : exits) {
                    if (e[0] == cur[0] && e[1] == cur[1]) {
                        return count;
                    }
                }
                for (int[] dir : dirs) {
                    int nx = cur[0] + dir[0];
                    int ny = cur[1] + dir[1];
                    int broken = cur[2];
                    if (nx >= 0 && nx < rows && ny >= 0 && ny < cols && !visited[nx][ny] && broken <= k) { //still able to move when broken == k
                        visited[nx][ny] = true;
                        if (matrix[nx][ny] == 1) {
                            broken++;
                        }
                        if (broken <= k) { //not able to break more than k
                            queue.offer(new int[]{nx, ny, broken});
                        }
                    }
                }
            }
            count++;
        }
        return count;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int[][] matrix = new int[][] {
                {1,0,1,1,1,1},
                {1,1,1,0,0,1},
                {1,1,0,0,0,1},
                {1,1,0,1,0,0},
                {1,0,0,1,0,1},
                {1,1,1,1,1,1}
        };
        int[][] exits = new int[][] {{0,1},{3,5}};
        int res = main.shortestPathToExitCanBreakAtMostKObstacles(matrix, new int[]{4,1, 0}, exits, 2);
        System.out.println(res);
    }
 */

/*
    Q1  2 cups to get 4L water, shortest steps

    public int stepsToGetFourLitterWater(int[] cups) {
        int cup1 = cups[0];
        int cup2 = cups[1];
        List<Integer> start = new ArrayList<Integer>() {{add(0); add(0);}};
        Queue<List<Integer>> queue = new LinkedList<>();
        queue.offer(start);
        int count = 0;
        List<List<Integer>> visited = new LinkedList<>();
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                List<Integer> cur = queue.poll();
                if (cur.get(0) == 4 || cur.get(1) == 4) {
                    return count;
                }
                // 3L full -> <3, y>
                int nx = cup1;
                int ny = cur.get(1);
                List<Integer> next = new LinkedList<>();
                next.add(nx);
                next.add(ny);
                if (!visited.contains(next)) {
                    visited.add(next);
                    queue.offer(next);
                }

                // 5L full -> <x, 5>
                nx = cur.get(0);
                ny = cup2;
                next = new LinkedList<>();
                next.add(nx);
                next.add(ny);
                if (!visited.contains(next)) {
                    visited.add(next);
                    queue.offer(next);
                }

                // 5L to 3L, cup2 to cup1
                nx = cur.get(0) + cur.get(1) > cup1 ? cup1 : cur.get(0) + cur.get(1);
                ny = Math.max(0, cur.get(1) - (cup1 - cur.get(0)));
                next = new LinkedList<>();
                next.add(nx);
                next.add(ny);
                if (!visited.contains(next)) {
                    visited.add(next);
                    queue.offer(next);
                }

                // 3L to 5L, cup1 to cup2
                nx = Math.max(0, cur.get(0) - (cup2 - cur.get(1)));
                ny = cur.get(1) + cur.get(0) > cup2 ? cup2 : cur.get(1) + cur.get(0);
                next = new LinkedList<>();
                next.add(nx);
                next.add(ny);
                if (!visited.contains(next)) {
                    visited.add(next);
                    queue.offer(next);
                }

                // empty cup1 3L
                nx = 0;
                ny = cur.get(1);
                next = new LinkedList<>();
                next.add(nx);
                next.add(ny);
                if (!visited.contains(next)) {
                    visited.add(next);
                    queue.offer(next);
                }

                // empty cup2 5L
                nx = cur.get(0);
                ny = 0;
                next = new LinkedList<>();
                next.add(nx);
                next.add(ny);
                if (!visited.contains(next)) {
                    visited.add(next);
                    queue.offer(next);
                }
            }
            count++;
        }
        return -1;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int res = main.stepsToGetFourLitterWater(new int[] {3,5});
        System.out.println(res);
    }
 */

/*
    Q3  Bulb switch, least steps to turn on all bulbs

    public int bulkSwtich(int[][] matrix) {
        int[] res = new int[1];
        res[0] = Integer.MAX_VALUE;
        dfs(matrix, 0, 0, res, matrix.length * matrix[0].length, 0);
        return res[0] == Integer.MAX_VALUE ? -1 : res[0];
    }

    public void dfs(int[][] matrix, int index, int steps, int[] res, int total, int cur) {
        int i = index / matrix[0].length;
        int j = index % matrix[0].length;
        if (i < 0 || i >= matrix.length || j < 0 || j > matrix[0].length) return;
        if (cur == total) {
            res[0] = Math.min(res[0], steps);
            return;
        }

        List<int[]> neibs = getNeib(matrix, i, j);
        for (int[] neib : neibs) {
            int x = neib[0];
            int y = neib[1];
            if (matrix[x][y] == 0) cur++;
            else cur--;
            matrix[x][y] ^= 1;
        }
        dfs(matrix, index + 1, steps + 1, res, total, cur);
        for (int[] neib : neibs) {
            int x = neib[0];
            int y = neib[1];
            if (matrix[x][y] == 0) cur++;
            else cur--;
            matrix[neib[0]][neib[1]] ^= 1;
        }
        dfs(matrix, index + 1, steps + 1, res, total, cur);
    }

    public List<int[]> getNeib(int[][] matrix, int i, int j) {
        List<int[]> res = new ArrayList<>();
        int[][] dirs = new int[][]{{0,1},{0,-1},{1,0},{-1,0}};
        for (int[] dir : dirs) {
            int ni = i + dir[0];
            int nj = j + dir[1];
            if (ni >= 0 && ni < matrix.length && nj >= 0 && nj < matrix[0].length) {
                res.add(new int[] {ni, nj});
            }
        }
        return res;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int[][] matrix = new int[][] {
                {0,0,0,0},
                {0,0,0,0},
                {0,0,0,0},
                {0,0,0,0}
        };
        int res = main.bulkSwtich(matrix);
        System.out.println(res);
    }
 */

/*
    Q3  Radar problem

    Given y = 0, y = 1, and list of radar, find if a car able to go through the road without detected by list of radars

    run dfs to find overlapped radars that block vertically the entire road y from 0 to 1

    static class Radar {
        double x, y, r;
        Radar(double x, double y, double r) {
            this.x = x;
            this.y = y;
            this.r = r;
        }
    }

    public boolean passAllRadar(List<Radar> radars) {
        double[] minY = new double[1];
        double[] maxY = new double[1];
        minY[0] = 0.5;
        maxY[0] = 0.5;
        boolean[] visited = new boolean[radars.size()];
        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]) {
                visited[i] = true;
                dfs(radars.get(i), radars, minY, maxY, visited);
                if (minY[0] < 0 && maxY[0] > 1) {
                    return false;
                }
            }
        }
        return true;
    }

    public void dfs(Radar cur, List<Radar> radars, double[] minY, double[] maxY, boolean[] visited) {
        for (int i = 0; i < radars.size(); i++) {
            Radar next = radars.get(i);
            if (!visited[i] && dis(cur, next) < cur.r + next.r) {
                visited[i] = true;
                minY[0] = Math.min(minY[0], next.y - next.r);
                maxY[0] = Math.max(maxY[0], next.y + next.r);
                dfs(next, radars, minY, maxY, visited);
            }
        }
    }

    public double dis(Radar r1, Radar r2) {
        return Math.sqrt(Math.pow(Math.abs(r1.x - r2.x),2.0) + Math.pow(Math.abs(r1.y - r2.y), 2.0));
    }

    public static void main(String[] args) {
        Main main = new Main();
        List<Radar> input = new LinkedList<>();
        input.add(new Radar(0.6, 0.6, 0.1));
        input.add(new Radar(0.5, 0.5, 0.2));
        input.add(new Radar(0.3, 0.3, 0.5));
        input.add(new Radar(0.8, 0.8, 0.5));
        input.add(new Radar(10, 0.5, 0.1));
        boolean res = main.passAllRadar(input);
        System.out.println(res);
    }
 */

/*
    class Backtracking DFS3
 */

/*
    Q7 算数表达式
/*
                         1234560
                           /\\\
               1        12   123   1234   12345
              /\       /\   /\
        1+2  1+23 1+234
        /\
    1+2+3 1+2+34 1+2+345

    Given "123450"  add '+' so that form expression "12+3+45+0" = 60

    public boolean formExpressionToTargetOnlyAdd(String str, int target) {
        if (str == null || str.length() == 0) return false;
        boolean[] res = new boolean[1];
        dfs(str, 0, 0, target, res);
        return res[0];
    }

    public void dfs(String str, int index, int sum, int target, boolean[] res) {
        if (index >= str.length()) {
            if (sum == target) {
                res[0] = true;
            }
            return;
        }

        for (int i = index + 1; i <= str.length(); i++) {
            String sub = str.substring(index, i);
            int val = Integer.valueOf(sub);
            dfs(str, i, sum + val, target, res);
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        boolean res = main.formExpressionToTargetOnlyAdd("123450", 60);
        System.out.println(res);
    }
 */

/*
    Problem of connectivity, DFS1,2/BFS1
 */

/*
    Q1  围棋
    -1: empty
    1: black
    0: white

     11
    1001
     11

    public boolean weiqiGame(int[][] matrix, int x, int y) {
        boolean[][] visited = new boolean[matrix.length][matrix[0].length];
        return isDead(matrix, visited, x, y);
    }

    public boolean isDead(int[][] matrix, boolean[][] visited, int x, int y) {
        if (x < 0 || x >= matrix.length || y < 0 || y > matrix[0].length) {
            return true;
        }

        if (matrix[x][y] == 1) return true;
        if (matrix[x][y] == -1) return false;
        if (visited[x][y]) return true;
        visited[x][y] = true;

        if (!isDead(matrix, visited, x + 1, y)) return false;
        if (!isDead(matrix, visited, x - 1, y)) return false;
        if (!isDead(matrix, visited, x, y + 1)) return false;
        if (!isDead(matrix, visited, x, y - 1)) return false;
        return true;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int[][] matrix = new int[][] {
                {-1,1,1,-1},
                {1, 0,0, 1},
                {-1,1,1,-1}
        };
        boolean res = main.weiqiGame(matrix, 1, 2);
        System.out.println(res);
    }
 */

/*
    Q2  Continental Divider

                {1,1,2,3,5},
                {1,2,3,4,4},
                {2,4,5,3,1},
                {6,7,1,4,5},
                {5,1,1,2,4}

    find point that can be reachable from both left and right side,
    water only flow from high to low

    public List<Integer> continentalDivider(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        boolean[][] fromLeft = new boolean[rows][cols];
        boolean[][] fromRight = new boolean[rows][cols];
        for (int x = 0; x < matrix.length; x++) {
            dfs(matrix, x, 0, fromLeft, Integer.MIN_VALUE);
        }

        for (int x = 0; x < matrix.length; x++) {
            dfs(matrix, x, cols - 1, fromRight, Integer.MIN_VALUE);
        }

        //find intersection of two visited sets
        List<Integer> list = new LinkedList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (fromLeft[i][j] && fromRight[i][j]) {
                    list.add(matrix[i][j]);
                }
            }
        }
        return list;
    }

    public void dfs(int[][] matrix, int i, int j, boolean[][] visited, int prev) {
        if (i < 0 || i >= matrix.length || j < 0 ||j >= matrix[0].length || visited[i][j] || matrix[i][j] <= prev) return;
        visited[i][j] = true;

        dfs(matrix, i + 1, j, visited, matrix[i][j]);
        dfs(matrix, i - 1, j, visited, matrix[i][j]);
        dfs(matrix, i, j + 1, visited, matrix[i][j]);
        dfs(matrix, i, j - 1, visited, matrix[i][j]);
    }

    public static void main(String[] args) {
        Main main = new Main();
        int[][] matrix = new int[][] {
                {1,1,2,3,5},
                {1,2,3,4,4},
                {2,4,5,3,1},
                {6,7,1,4,5},
                {5,1,1,2,4}
        };
        List<Integer> list = main.continentalDivider(matrix);
        for (int i : list) System.out.println(i);
    }
 */
/*
    Q1  Number of Lakes

    Lake: surrounded by island:

                {1,1,1,1,0},
                {1,0,0,1,0},
                {1,1,1,1,1},
                {0,0,0,0,0}
                # of lake: 1

     public int numberOfLake(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int count = 0;
        boolean[][] visited = new boolean[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] == 0 && !visited[i][j] && !dfs(matrix, visited, i, j)) {
                    count++;
                }
            }
        }
        return count;
    }

    public boolean dfs(int[][] matrix, boolean[][] visited, int i, int j) {
        if (i < 0 || i >= matrix.length || j < 0 || j >= matrix[0].length) return true;
        if (matrix[i][j] == 1) return false;
        if (visited[i][j]) return false;
        visited[i][j] = true;

        boolean toBoundry = false;
        toBoundry |= dfs(matrix, visited, i + 1, j);
        toBoundry |= dfs(matrix, visited, i - 1, j);
        toBoundry |= dfs(matrix, visited, i, j + 1);
        toBoundry |= dfs(matrix, visited, i, j - 1);
        return toBoundry;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int[][] matrix = new int[][] {
                {1,1,1,1,0},
                {1,0,0,1,0},
                {1,1,1,1,1},
                {0,0,0,0,0}
        };
        int res =main.numberOfLake(matrix);
        System.out.println(res);
    }
 */

/*
    Q2 Largest island value

    island value = sum of all the cell's value belong to the island
                {1,4,0,0,0},
                {2,0,3,0,0},
                {0,0,5,0,0},
                {0,0,0,6,1}
                return 8

    public int largestIslandValue(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int max = 0;
        boolean[][] visited = new boolean[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] != 0 && !visited[i][j]) {
                    max = Math.max(max, dfs(matrix, visited, i, j));
                }
            }
        }
        return max;
    }

    public int dfs(int[][] matrix, boolean[][] visited, int i, int j) {
        if (i < 0 || i >= matrix.length || j < 0 || j >= matrix[0].length || matrix[i][j] == 0) return 0;
        if (visited[i][j]) return 0;
        visited[i][j] = true;
        int sum = matrix[i][j];
        sum += dfs(matrix, visited, i + 1, j);
        sum += dfs(matrix, visited, i - 1, j);
        sum += dfs(matrix, visited, i, j + 1);
        sum += dfs(matrix, visited, i, j - 1);
        return sum;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int[][] matrix = new int[][] {
                {1,4,0,0,0},
                {2,0,3,0,0},
                {0,0,5,0,0},
                {0,0,0,6,1}
        };
        int res =main.largestIslandValue(matrix);
        System.out.println(res);
    }
 */

/*
    Q4  Minimum Tree Height

    This is not optimal solution, O(n^2), run get height for each node, and record the min one
    1 2 3
    \ | /
      4
      |
      5
      /\
     6  7
     return [4,5]

    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        List<Integer> res = new LinkedList<>();
        if (edges == null || edges.length == 0) {
            res.add(0);
            return res;
        }
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int[] edge : edges) {
            map.putIfAbsent(edge[0], new ArrayList<>());
            map.putIfAbsent(edge[1], new ArrayList<>());
            map.get(edge[0]).add(edge[1]);
            map.get(edge[1]).add(edge[0]);
        }
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            int height = dfs(i, map, -1);
            if (height <= min) {
                if (height < min) {
                    res = new ArrayList<>();
                }
                res.add(i);
                min = height;
            }
        }
        return res;
    }

    public int dfs(int node, Map<Integer, List<Integer>> map, int parent) {
        int maxHeight = 0;
        for (int neib : map.get(node)) {
            if (neib  != parent) {
                maxHeight = Math.max(dfs(neib, map, node), maxHeight);
            }
        }
        return maxHeight + 1;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int[][] input = new int[][] {
                {0,3},
                {1,3},
                {2,3},
                {4,3},
                {5,4}
        };
        List<Integer> res = main.findMinHeightTrees(6, input);
        System.out.println(res);
    }
 */

/*
    Best first Search
 */

/*
    Q2 Company notify all employee

    ==> largest path sum in the tree

    static class Person {
        String name;
        int cost;
        List<Person> neibs;
        Person(String name, int cost) {
            this.name = name;
            this.cost = cost;
        }
    }

    public int minTimeNotifyCompanyWide(Person root) {
        if (root == null) return 0;
        int[] max = new int[1];
        dfs(root, root.cost, max);
        return max[0];
    }

    public void dfs(Person root, int sum, int[] max) {
        if (root.neibs.size() == 1) {
            max[0] = Math.max(max[0], sum);
            return;
        }
        for (Person neib : root.neibs) {
            dfs(neib, sum + neib.cost, max);
        }
    }


    public static void main(String[] args) {
        Main main = new Main();
        Person ceo = new Person("ceo", 10);
        Person a1 = new Person("a1", 20);
        Person a2 = new Person("a2", 10);
        Person b1 = new Person("b1", 30);
        Person c1 = new Person("c1", 30);
        b1.neibs = new LinkedList<>();
        c1.neibs = new LinkedList<>();
        List<Person> ceolist = new LinkedList<>();
        ceolist.add(a1);ceolist.add(a2);
        ceo.neibs = ceolist;

        List<Person> a1list = new LinkedList<>();
        a1list.add(b1);
        a1.neibs = a1list;

        List<Person> a2list = new LinkedList<>();
        a2list.add(c1);
        a2.neibs = a2list;
        int res = main.minTimeNotifyCompanyWide(ceo);
        System.out.println(res);
    }
 */

/*
    follow up 0, create iterator to output next person receive message

     static class Person {
        String name;
        int cost;
        List<Person> neibs;
        Person(String name, int cost) {
            this.name = name;
            this.cost = cost;
        }
    }

    static class BFSIterator implements Iterator<Person>{
        PriorityQueue<Person> pq;
        BFSIterator(Person ceo) {
            pq = new PriorityQueue<>(1, new Comparator<Person>() {
                @Override
                public int compare(Person o1, Person o2) {
                    if (o1.cost == o2.cost) return 0;
                    return o1.cost - o2.cost > 0 ? 1 : -1;
                }
            });
            pq.offer(ceo);
        }

        @Override
        public boolean hasNext() {
            return !pq.isEmpty();
        }

        @Override
        public Person next() {
            if (!hasNext()) throw new RuntimeException("Empty");
            Person cur = this.pq.poll();
            for (Person neib : cur.neibs) {
                neib.cost += cur.cost;
                this.pq.offer(neib);
            }
            return cur;
        }
    }


    public static void main(String[] args) {
        Main main = new Main();
        Person ceo = new Person("ceo", 10);
        Person a1 = new Person("a1", 20);
        Person a2 = new Person("a2", 10);
        Person b1 = new Person("b1", 30);
        Person c1 = new Person("c1", 30);
        b1.neibs = new LinkedList<>();
        c1.neibs = new LinkedList<>();
        List<Person> ceolist = new LinkedList<>();
        ceolist.add(a1);ceolist.add(a2);
        ceo.neibs = ceolist;

        List<Person> a1list = new LinkedList<>();
        a1list.add(b1);
        a1.neibs = a1list;

        List<Person> a2list = new LinkedList<>();
        a2list.add(c1);
        a2.neibs = a2list;
        BFSIterator it = new BFSIterator(ceo);
        while (it.hasNext()) {
            Person p = it.next();
            System.out.println("cost:" + p.cost + " name:" + p.name);
        }
    }
 */

/*
    Follow up 2: has multiple report lines
          CEO
        /  | \
       a1  |  a2
       /\  |   |
    b1   b2   c1
          |
         d1
    static class Person {
        String name;
        int cost;
        List<Person> neibs;
        Person(String name, int cost) {
            this.name = name;
            this.cost = cost;
            this.neibs = new LinkedList<>();
        }
    }

    static class Node {
        Person person;
        int cost;
        Node(Person person, int cost) {
            this.person = person;
            this.cost = cost;
        }
    }

    static class BFSIterator implements Iterator<Person>{
        PriorityQueue<Node> pq;
        Set<Person> expanded;
        Map<Person, Integer> generated;
        BFSIterator(Person ceo) {
            this.pq = new PriorityQueue<>(1, new Comparator<Node>() {
                @Override
                public int compare(Node o1, Node o2) {
                    if (o1.cost == o2.cost) return 0;
                    return o1.cost > o2.cost ? 1 : -1;
                }
            });
            this.pq.offer(new Node(ceo, 0));
            expanded = new HashSet<>();
            generated = new HashMap<>();
        }

        @Override
        public boolean hasNext() {
            while (!this.pq.isEmpty() && expanded.contains(this.pq.peek())) {
                this.pq.poll();
            }
            return !pq.isEmpty();
        }

        @Override
        public Person next() {
            if (!hasNext()) throw new RuntimeException("Empty");
            Node cur = this.pq.poll();
            expanded.add(cur.person);
            for (Person neib : cur.person.neibs) {
                if (expanded.contains(neib)) continue;
                int cost = cur.cost + cur.person.cost;
                if (!generated.containsKey(neib)) {
                    this.pq.offer(new Node(neib, cost));
                    generated.put(neib, cost);
                } else if (cost < generated.get(neib)) {
                    this.pq.offer(new Node(neib, cost));
                    generated.put(neib, cost);
                }
            }
            return cur.person;
        }
    }


    public static void main(String[] args) {
        Main main = new Main();
        Person ceo = new Person("ceo", 10);
        Person a1 = new Person("a1", 20);
        Person a2 = new Person("a2", 10);
        Person b1 = new Person("b1", 30);
        Person b2 = new Person("b2", 1);
        Person c1 = new Person("c1", 30);
        Person d1 = new Person("d1", 2);
        List<Person> ceolist = new LinkedList<>();
        ceolist.add(a1);ceolist.add(a2);ceolist.add(b2);
        ceo.neibs = ceolist;

        List<Person> a1list = new LinkedList<>();
        a1list.add(b1);a1list.add(b2);
        a1.neibs = a1list;

        List<Person> a2list = new LinkedList<>();
        a2list.add(c1);
        a2.neibs = a2list;

        List<Person> b2list = new LinkedList<>();
        b2list.add(d1);
        b2.neibs = b2list;

        BFSIterator it = new BFSIterator(ceo);
        while (it.hasNext()) {
            Person p = it.next();
            System.out.println("cost:" + p.cost + " name:" + p.name);
        }
    }
 */