package Wednesday.Sunday.HybridDataStructure;

import java.util.*;

public class Main {


















    static class MusicPlayer {

        Queue<Integer> queue;
        List<Integer> list;
        Random  rand;
        int k;
        MusicPlayer(int[] nums, int k) {
            if (k >= nums.length) throw new IllegalArgumentException("k can't larger than available music");
            rand = new Random();
            queue = new LinkedList<>();
            list = new ArrayList<>();
            for (int i : nums) {
                list.add(i);
            }
            this.k = k;
        }

        public int playRandom() {
            int posToDelete = rand.nextInt(list.size());
            int music = list.get(posToDelete);
            queue.offer(music);

            int last = list.get(list.size() - 1);
            list.set(posToDelete, last);
            list.remove(list.size() - 1);

            if (queue.size() > k - 1) {
                list.add(queue.poll());
            }
            return music;
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        MusicPlayer player = new MusicPlayer(new int[]{1,2,3,4,5}, 3);
        System.out.println(player.playRandom());
        System.out.println(player.playRandom());
        System.out.println(player.playRandom());
        System.out.println(player.playRandom());
        System.out.println(player.playRandom());
    }
}

/*
        class 1
 */

/*
    Q1  implement insert delete median with duplicate, using two treeSet

                                 median
            insert(1)           1
            insert(1)           1
            insert(2)           1
            insert(3)           1.5
            insert(4)           2
            delete(1)           2.5

    static class InsertDeleteMedian {
        class Element implements Comparable<Element> {
            int val;
            int seqNum;

            Element(int val, int seqNum) {
                this.val = val;
                this.seqNum = seqNum;
            }

            @Override
            public int compareTo(Element other) {
                if (this.val != other.val) return Integer.compare(this.val, other.val);
                return Integer.compare(this.seqNum, other.seqNum);
            }
        }

        TreeSet<Element> smallerPart;
        TreeSet<Element> largerPart;
        int seqNum;

        public InsertDeleteMedian() {
            smallerPart = new TreeSet<>();
            largerPart = new TreeSet<>();
            seqNum = 0;
        }

        public void insert(int val) {
            smallerPart.add(new Element(val, seqNum++));
            largerPart.add(smallerPart.pollLast());

            if (largerPart.size() > smallerPart.size()) {
                smallerPart.add(largerPart.pollFirst());
            }
        }

        public double median() {
            if (smallerPart.size() == largerPart.size()) {
                return (smallerPart.last().val + largerPart.first().val) / 2.0;
            } else {
                return smallerPart.last().val;
            }
        }

        public void delete(int val) {
            // if val in either part, ceiling should return that specific element
            Element atLarger = largerPart.ceiling(new Element(val, 0));
            Element atSmaller = smallerPart.ceiling(new Element(val, 0));
            if (atLarger != null && atLarger.val == val) {
                largerPart.remove(atLarger);
            } else if (atSmaller != null &&  atSmaller.val == val){
                smallerPart.remove(atSmaller);
            }

            if (largerPart.size() > smallerPart.size()) {
                smallerPart.add(largerPart.pollFirst());
            }
        }
    }
    public static void main(String[] args) {
        Main main = new Main();
        InsertDeleteMedian solu = new InsertDeleteMedian();
        solu.insert(1);
        System.out.println(solu.median());
        solu.insert(1);
        System.out.println(solu.median());
        solu.insert(2);
        System.out.println(solu.median());
        solu.insert(3);
        System.out.println(solu.median());
        solu.insert(4);
        System.out.println(solu.median());
        solu.delete(1);
        System.out.println(solu.median());
    }
 */

/*
    Q2 sliding window median, using two treeSet, no dup

    class InsertDeleteMedian {
        TreeSet<Integer> smaller;
        TreeSet<Integer> larger;
        InsertDeleteMedian() {
            this.smaller = new TreeSet<>();
            this.larger = new TreeSet<>();
        }

        public void insert(int val) {
            smaller.add(val);
            larger.add(smaller.pollLast());
            if (larger.size() > smaller.size()) {
                smaller.add(larger.pollFirst());
            }
        }

        public void delete(int val) {
            if (smaller.contains(val)) {
                smaller.remove(val);
            } else if (larger.contains(val)) {
                larger.remove(val);
            }

            if (larger.size() > smaller.size()) {
                smaller.add(larger.pollFirst());
            }
        }

        public double median() {
            if (smaller.size() == larger.size()) {
                return (smaller.last() + larger.first()) / 2.0;
            } else {
                return smaller.last();
            }
        }
    }

    public List<Double> slidingWindowMedian(int[] array, int k) {
        List<Double> list = new ArrayList<>();
        InsertDeleteMedian ds = new InsertDeleteMedian();
        for (int i = 0; i < array.length; i++) {
            ds.insert(array[i]);
            if (i - k + 1 >= 0) {
                list.add(ds.median());
                ds.delete(array[i - k + 1]);
            }
        }
        return list;
    }

    public static void main(String[] args) {
        Main main = new Main();
        List<Double> res = main.slidingWindowMedian(new int[] {1,3,7,4,2,0}, 3);
        System.out.print(res);
    }
 */

/*
        class 2
 */

/*
    Q1 Design key-value store support
    get(keu, time) return the value at most recent to the given time

    put{key, 100, v1}
    put{key, 120, v2}
    put{key, 200, v3}

    get{key, 150}   return v2
    get{key, 101}   return v1

    //implement by map and treeMap
    // Map<key, TreeMap<time, value>>

    class {
        long time;
        Value v;
    }
    It can also be implemented by using:
    Map<Key, ArrayList<Entry>>
        - insert -> append at the end
        - get() -> binary search

    static class MostRecentKeyValueStore<K, V> {
        Map<K, TreeMap<Long, V>> map;

        MostRecentKeyValueStore() {
            map = new HashMap<>();
        }

        public void put (K key, long time, V val) {
            map.putIfAbsent(key, new TreeMap<>());
            map.get(key).put(time, val);
        }

        public V get(K key, long time) {
            if (map.containsKey(key)) {
                long val = map.get(key).floorKey(time);
                return map.get(key).get(val);
            }
            return null;
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        MostRecentKeyValueStore dataStore = new MostRecentKeyValueStore<String, String>();
        dataStore.put("a", 100, "v1");
        dataStore.put("a", 120, "v2");
        dataStore.put("a", 200, "v3");
        String res1 = (String) dataStore.get("a", 150);
        String res2 = (String) dataStore.get("a", 101);
        System.out.println(res1);
        System.out.println(res2);
    }
 */

/*
    Q3      Design a resume system support operations:
            Update(String profileId, String key, String val)
            get(String profileId, int version)
            get(String profileId, String key, String val)

        system.update("ABC", "skills", "java");
        system.update("ABC", "skills", "python");
        system.update("ABBC", "education", "USC");
        Map<String, String> res = system.get("ABC", 2);
        should return {"ABC": {"skills": "java, python", "education": "USC"}}


        The data structure we used here is:
        Map<ProfileId, Map<Key, TreeMap<Version, Value>>

    static class ResumeSystem {
        Map<String, Map<String, TreeMap<Integer, String>>> profile;
        int version;
        ResumeSystem() {
            profile = new HashMap<>();
            version = 1;
        }

        public void update(String profileId, String key, String val) {
            if (!profile.containsKey(profileId) || !profile.get(profileId).containsKey(key)) {
                profile.put(profileId, new HashMap<>());
                profile.get(profileId).putIfAbsent(key, new TreeMap<>());
                profile.get(profileId).get(key).put(version, val);
            } else {
                int mostRecentVersion = profile.get(profileId).get(key).floorKey(version);
                String mostRecentValue = profile.get(profileId).get(key).get(mostRecentVersion);
                profile.get(profileId).get(key).put(++version, mostRecentValue + "," + val);
            }
        }

        public Map<String, String> get(String profileId, int version) {
            if (profile.containsKey(profileId)) {
                Map<String, String> res = new HashMap<>();
                for (Map.Entry<String, TreeMap<Integer, String>> entry : profile.get(profileId).entrySet()) {
                    res.put(entry.getKey(), entry.getValue().get(version));
                }
                return res;
            }
            return null;
        }

        public String get(String profileId, String key, int version) {
            if (profile.containsKey(profileId)) {
                return profile.get(profileId).get(key).get(version);
            }
            return null;
        }
    }

    public static void main(String[] args) {
        Main main = new Main();

        ResumeSystem system = new ResumeSystem();
        system.update("ABC", "skills", "java");
        system.update("ABC", "skills", "python");
        system.update("ABBC", "education", "USC");
        Map<String, String> res = system.get("ABC", 2);
        for (String key : res.keySet()) {
            System.out.println(key);
            System.out.println(res.get(key));
        }
    }
 */

/*
    Q4  Design cache with TTL

    idea: use TreeMap to store all nodes with its expired time
    api: removeAllKeys should delete all nodes that has expired time less than current time
    continue call floor api of treeMap able to get expired node

    static class LRUwithTTL<K, V> {
        class Node<K, V> {
            K key;
            V val;
            Node prev;
            Node next;
            Node(K key, V val) {
                this.key = key;
                this.val = val;
            }
        }

        Map<K, Node<K, V>> map;
        TreeMap<Long, Node<K, V>> ttlMap;

        Node head;
        Node tail;
        int limit;
        LRUwithTTL(int limit) {
            map = new HashMap<>();
            ttlMap = new TreeMap<>();
            head = new Node<>(-1, -1);
            tail = new Node<>(-1, -1);
            head.next = tail;
            tail.prev = head;
            this.limit = limit;
        }

        public void removeAllExpiredKeys() {
            long curTime = System.currentTimeMillis();
            Long pastTime = ttlMap.floorKey(curTime);
            while (pastTime != null) {
                Node node = ttlMap.get(pastTime);
                map.remove(node.key);
                remove(node);
                ttlMap.remove(pastTime);
                pastTime = ttlMap.floorKey(curTime);
            }
        }

        public V get(K key) {
            if (map.containsKey(key)) {
                Node node = map.get(key);
                remove(node);
                addHead(node);
                return (V) node.val;
            }
            return null;
        }

        public void put(K key, V val, long time) {
            if (map.containsKey(key)) {
                Node node = map.get(key);
                remove(node);
                map.remove(node);
            }

            Node newNode = new Node<>(key, val);
            addHead(newNode);
            map.put(key, newNode);

            ttlMap.put(System.currentTimeMillis() + time, newNode);

            if (map.size() > this.limit) {
                Node last = tail.prev;
                remove(last);
                map.remove(last.key);
            }
        }

        public void remove(Node node) {
            Node prev = node.prev;
            Node next = node.next;
            prev.next = next;
            next.prev = prev;
            node.next = null;
            node.prev = null;
        }

        public void addHead(Node node) {
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        LRUwithTTL lru = new LRUwithTTL(10);
        lru.put(1,1,10);
        lru.put(2,2,3);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lru.removeAllExpiredKeys();
        System.out.print(lru.get(2));
    }
 */

/*
    Q5 Design map that  clear() has O(1)

    compare current version of value with last clear version for get()

    static class MapClearO1<K, V> {
        class Entry<V> {
            V val;
            long version;
            Entry(V val, long version) {
                this.val = val;
                this.version = version;
            }
        }
        long globalVersion;
        long lastClearVersion;

        Map<K, Entry<V>> map;

        MapClearO1() {
            globalVersion = 0;
            lastClearVersion = -1;
            map = new HashMap<>();
        }

        public void put(K key, V val) {
            map.put(key, new Entry<>(val, ++globalVersion));
        }

        public void remove(K key) {
            map.remove(key);
        }

        public void clear() {
            this.lastClearVersion = globalVersion;
        }

        public V get(K key) {
            if (map.containsKey(key)) {
                Entry entry = map.get(key);
                if (entry.version < lastClearVersion) {
                    return null;
                }
                return (V) entry.val;
            }
            return null;
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        MapClearO1 map = new MapClearO1();
        map.put("a", 1);
        map.put("b", 2);
        System.out.println(map.get("a"));
        map.clear();
        System.out.println(map.get("a"));
        map.put("c", 3);
        System.out.println(map.get("c"));
    }
 */
/*
    Q6 lazy delete entry

    static class LoggerLazyDelete {
        class LogEntry {
            long id;
            long start;
            long end;
            LogEntry(long id, long start) {
                this.id = id;
                this.start = start;
                this.end = -1; // used for lazy delete entry
            }
        }

        Map<Long, LogEntry> map;
        Queue<LogEntry> rq;

        public void start(long id) {
            long cur = System.currentTimeMillis();
            LogEntry entry = new LogEntry(id, cur);
            map.put(id, entry);
        }

        public void end(long id) {
            LogEntry entry = map.get(id);
            entry.end = System.currentTimeMillis(); //mark delete
            while (!rq.isEmpty() && rq.peek().end != -1) { // delete all queue head that should be delete
                LogEntry head = rq.poll();
                map.remove(head.id);
                System.out.println(head);
            }
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
    }
 */

/*
    class 3  Handling Randomness
 */

/*
    Q1  Design map that support , the same as LC 380
    put(), get(), getRandom(), removeRandom();

    static class Entry<K, V> {
        K key;
        V val;
        Entry(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }

    static class RandomMap<K, V> {

        Map<K, Integer> map;
        List<Entry> list;
        Random rand;
        RandomMap() {
            map = new HashMap<>();
            list = new ArrayList<>();
            rand = new Random();
        }

        public V get(K key) {
            if (map.containsKey(key)) {
                int index = map.get(key);
                return (V) list.get(index).val;
            }
            return null;
        }

        public void put(K key, V val) {
            if (!map.containsKey(key)) {
                map.put(key, list.size());
                list.add(new Entry<>(key, val));
            } else {
                int index = map.get(key);
                list.get(index).val = val;
            }
        }

        public void remove(K key) {
            if (!map.containsKey(key)) return;
            int posToDelete = map.get(key);
            Entry last = list.get(list.size() - 1);
            list.set(posToDelete, last);
            map.remove(key);
            map.put((K) last.key, posToDelete);
            list.remove(list.size() - 1);
        }

        public Entry<K, V> getRandom() {
            int pos = rand.nextInt(list.size());
            return list.get(pos);
        }

        public void removeRandom() {
            int pos = rand.nextInt(list.size());
            K key = (K) list.get(pos).key;
            remove(key);
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        RandomMap<String, Integer> map = new RandomMap<>();
        map.put("a", 0);
        map.put("b", 1);
        map.put("c", 2);
        map.put("d", 3);
        map.put("e", 4);
        Entry<String, Integer> res = map.getRandom();
        System.out.println(res.val);
        map.removeRandom();
        Entry<String, Integer> res1 = map.getRandom();
        System.out.println(res1.val);
    }
 */

/*
    Q2. Music player

    playRandom() method guarantees that any k consecutive calls won't return duplicate musics

    // This solution using ArrayList + Queue
    // another solution just using circular array list

    static class MusicPlayer {

        Queue<Integer> queue;
        List<Integer> list;
        Random  rand;
        int k;
        MusicPlayer(int[] nums, int k) {
            if (k >= nums.length) throw new IllegalArgumentException("k can't larger than available music");
            rand = new Random();
            queue = new LinkedList<>();
            list = new ArrayList<>();
            for (int i : nums) {
                list.add(i);
            }
            this.k = k;
        }

        public int playRandom() {
            int posToDelete = rand.nextInt(list.size());
            int music = list.get(posToDelete);
            queue.offer(music);

            int last = list.get(list.size() - 1);
            list.set(posToDelete, last);
            list.remove(list.size() - 1);

            if (queue.size() > k - 1) {
                list.add(queue.poll());
            }
            return music;
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        MusicPlayer player = new MusicPlayer(new int[]{1,2,3,4,5}, 3);
        System.out.println(player.playRandom());
        System.out.println(player.playRandom());
        System.out.println(player.playRandom());
        System.out.println(player.playRandom());
        System.out.println(player.playRandom());
    }

 */