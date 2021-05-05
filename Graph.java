import java.util.*;

public class Graph {

    ArrayList<HashMap<Integer, Integer>> graphArray = new ArrayList<HashMap<Integer, Integer>>();

    /**
     * Initializes a graph of size {@code n}. All valid vertices in this graph thus have integer
     * indices in the half-open range {@code [0, n)}, n > 0.
     *
     * @param n the number of vertices in the graph
     * @throws IllegalArgumentException if {@code n} is zero or negative
     * @implSpec This method should run in expected O(n) time
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
     * <p/>
     * Do NOT modify this method header.
     *
     * @return the number of vertices in the graph
     * @implSpec This method should run in expected O(1) time.
     */
    public int getSize() {
        return graphArray.size();
    }

    /**
     * Returns the weight of an the directed edge {@code u-v}.
     * <p/>
     * Do NOT modify this method header.
     *
     * @param u source vertex
     * @param v target vertex
     * @return the edge weight of {@code u-v}
     * @throws NoSuchElementException   if the {@code u-v} edge does not exist
     * @throws IllegalArgumentException if a specified vertex does not exist
     * @implSpec This method should run in expected O(1) time.
     */
    public int getWeight(int u, int v) {
        if (!hasEdge(u, v)) {
            throw new NoSuchElementException();
        }
        return (graphArray.get(u).get(v));
    }

    /**
     * Creates an edge from {@code u} to {@code v} if it does not already exist. A call to this
     * method should <em>not</em> modify the edge weight if the {@code u-v} edge already exists.
     * <p/>
     * Do NOT modify this method header.
     *
     * @param u      the source vertex to connect
     * @param v      the target vertex to connect
     * @param weight the edge weight
     * @return {@code true} if the graph changed as a result of this call, false otherwise (i.e., if
     * the edge is already present)
     * @throws IllegalArgumentException if a specified vertex does not exist or if u == v
     * @implSpec This method should run in expected O(1) time
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
     * <p/>
     * Do NOT modify this method header.
     *
     * @param v the vertex
     * @return all out neighbors of the specified vertex or an empty set if there are no out
     * neighbors
     * @throws IllegalArgumentException if the specified vertex does not exist
     * @implSpec This method should run in expected O(outdeg(v)) time.
     */
    public Set<Integer> outNeighbors(int v) {
        if (v >= graphArray.size() || v < 0) {
            throw new IllegalArgumentException();
        }
        return graphArray.get(v).keySet();
    }
}