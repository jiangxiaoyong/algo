package Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoyongjiang on 6/4/17.
 */
public class GraphNode {
    public int key;
    public List<GraphNode> neighbors;
    public GraphNode(int key) {
        this.key = key;
        this.neighbors = new ArrayList<GraphNode>();
    }
}
