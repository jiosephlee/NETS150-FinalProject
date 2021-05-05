import java.util.*;

public class Graph {

    ArrayList<HashMap<Integer, Integer>> graphArray = new ArrayList<HashMap<Integer, Integer>>();

    /**
     * Initializes a graph of size n
     */
    public Graph(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < n; i++) {
            HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>();
            graphArray.add(i, hm);
        }
    }

    /**
     * Returns the number of vertices in the graph.
     */
    public int getSize() {
        return graphArray.size();
    }

    /**
     * Determines if there's an directed edge from u to v.
     */
    public boolean hasEdge(int u, int v) {
        if (Math.max(u, v) >= graphArray.size() || Math.min(u, v) < 0) {
            throw new IllegalArgumentException();
        }
        return (graphArray.get(u).containsKey(v));
    }

    /**
     * Returns weight of the edge
     */
    public int getWeight(int u, int v) {
        if (!hasEdge(u, v)) {
            throw new NoSuchElementException();
        }
        return (graphArray.get(u).get(v));
    }

    /**
     * Adds an edge between two vertices with a weight
     */
    public boolean addEdge(int u, int v, int weight) {
        if (u == v || Math.max(u, v) >= graphArray.size() || Math.min(u, v) < 0) {
            throw new IllegalArgumentException();
        }
        if (graphArray.get(u).containsKey(v)) {
            return false;
        } else {
            graphArray.get(u).put(v, weight);
            return true;
        }
    }

    /**
     * Returns the out-neighbors of the specified vertex.
     */
    public Set<Integer> outNeighbors(int v) {
        if (v >= graphArray.size() || v < 0) {
            throw new IllegalArgumentException();
        }
        return graphArray.get(v).keySet();
    }
}