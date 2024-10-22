package upo.graphimpl;

import upo.graph.*;

import java.util.Iterator;
import java.util.LinkedList;

public class GraphSearchResultImpl implements GraphSearchResult {
    private SearchType type;
    private Vertex source;
    private LinkedList<Vertex> tree;
    private LinkedList<EdgeImpl> edgees;
    private Graph graph;

    public GraphSearchResultImpl(SearchType type, Vertex source, Graph graph) {
        this.type = type;
        this.source = source;
        this.graph = graph;
        tree = new LinkedList<>();
        edgees = new LinkedList<>();
    }

    private LinkedList<Vertex> getTargetEdge(Vertex v) {
        LinkedList<Vertex> targetEdge = new LinkedList<>();
        for (EdgeImpl e : edgees) {
            if (e.getSource().equals(v)) {
                targetEdge.add(e.getTarget());
            }
        }
        return targetEdge;
    }

    public boolean addLeaves(Vertex l) {
        // If the vertex is null, throws NullPointerException
        if (l == null)
            throw new NullPointerException("The given parameter is null");

        //Vertex exists
        if (containsLeaves(l))
            return false;
        else {
            //Adds the vertex and initializes an empty list
            tree.add(l);
            return true;
        }
    }

    public boolean removeLeaves(Vertex v) {
        //If the vertex is null or the graph doesn't contain it, returns null
        if (v == null || !containsLeaves(v))
            return false;

        //Removes the key (aka the vertex)
        tree.remove(v);

        //For each keys, checks if the LinkedList contains the vertex and if it does, deletes it
        for (Vertex key : tree) {
            if (containsEdge(v, key))
                removeEdge(v, key);
            if (containsEdge(key, v))
                removeEdge(key, v);
        }

        return true;
    }

    public boolean containsLeaves(Vertex l) {
        // If the leaf is null or does not exist in the graph, returns false
        for (Vertex key : tree) {
            if (l.equals(key)) {
                return true;
            }
        }
        return false;
    }

    public Edge addEdge(Vertex sourceLeaf, Vertex targetLeaf) {
        //If any of the specified vertex are null, throws NullPointerException
        if (sourceLeaf == null || targetLeaf == null)
            throw new NullPointerException("One or more given parameter is null.");

        //If the graph doesn't contain both the specified vertex, throws IllegalArgumentException
        if (!containsLeaves(sourceLeaf) || !containsLeaves(targetLeaf))
            throw new IllegalArgumentException("One or both vertices are not contained in the graph.");

        //Checks if the list doesn't contain the value already, creates an edge, adds it to the edges set and returns it
        if (!containsEdge(sourceLeaf, targetLeaf)) {
            EdgeImpl e = new EdgeImpl(sourceLeaf, targetLeaf, this);
            edgees.add(e);
            return e;
        }

        return null;
    }

    public Edge addEdge(Vertex sourceLeaf, Vertex targetLeaf, double weight) {
        //If any of the specified vertex are null, throws NullPointerException
        if (sourceLeaf == null || targetLeaf == null)
            throw new NullPointerException("One or more given parameter is null.");

        //If the graph doesn't contain both the specified vertex, throws IllegalArgumentException
        if (!containsLeaves(sourceLeaf) || !containsLeaves(targetLeaf))
            throw new IllegalArgumentException("One or both vertices are not contained in the graph.");

        //Checks if the list doesn't contain the value already, creates an edge, adds it to the edges set and returns it
        if (!containsEdge(sourceLeaf, targetLeaf)) {
            EdgeImpl e = new EdgeImpl(sourceLeaf, targetLeaf, weight, this);
            edgees.add(e);
            return e;
        }

        return null;
    }

    public boolean containsEdge(Vertex sourceLeaf, Vertex targetLeaf) {
        //Returns false if any of the specified vertex does not exist in the current graph
        if (!containsLeaves(sourceLeaf) || !containsLeaves(targetLeaf))
            return false;

        //If the graph contains an edge from source to target, returns true. Does not consider edge weight.
        for (EdgeImpl e : edgees)
            if (e.getSource().equals(sourceLeaf) && e.getTarget().equals(targetLeaf))
                return true;

        return false;
    }

    public Edge removeEdge(Vertex sourceLeaf, Vertex targetLeaf) {
        //Returns null if any of the specified vertex does not exist in the current graph
        if (!containsLeaves(sourceLeaf) || !containsLeaves(targetLeaf))
            return null;

        //Finds the edge with sourceVertex and targetVertex and removes it
        for (EdgeImpl e : edgees)
            if (e.getSource().equals(sourceLeaf) && e.getTarget().equals(targetLeaf))
                edgees.remove(e);

        return null;
    }

    @Override
    public Iterator<Vertex> iterator() {
        return graph.iterator();
    }

    /**
     * Returns the type of the search (e.g., BFS) which generates this result
     *
     * @return a SearchType describing the search which generates this result
     */
    @Override
    public SearchType getType() {
        return type;
    }

    /**
     * Returns the source vertex S of the search which generates this result
     *
     * @return the source Vertex
     */
    @Override
    public Vertex getSource() {
        return source;
    }

    /**
     * For BFS and Dikstra search types, this method returns the <i> distance </i> (i.e., the length/weight
     * of the shortest path between the source vertex S and v. If v is not reachable from S, this method
     * returns Double.POSITIVE_INFINITY.
     * If v does not belong to the visited Graph, it throws an IllegalArgumentException.
     * For other visit types, it throws an UnsupportedOperationException.
     *
     * @param v the target vertex.
     * @return \delta(S,v)
     * @throws IllegalArgumentException      if v does not belong to the graph.
     * @throws UnsupportedOperationException if this.getType() is different from BFS and DIJKSTRA
     */
    @Override
    public double getDistance(Vertex v) throws UnsupportedOperationException, IllegalArgumentException {
        if (!graph.containsVertex(v))
            throw new IllegalArgumentException("The vertex is not contained in the visited graph");

        if (!containsLeaves(v))
            return Double.POSITIVE_INFINITY;

        if (this.getType() != SearchType.BFS && this.getType() != SearchType.DIJKSTRA)
            throw new UnsupportedOperationException("This type of visit is not supported.");

        if (source.equals(v))
            return 0;
        return distance(source, v, 0);
    }

    /**
     * Returns the distance between current and find.
     * Otherwise it returns Double.POSITIVE_INFINITY.
     *
     * @param current
     * @param find
     * @param currentDistance
     * @return distance between current and find
     */
    private double distance(Vertex current, Vertex find, double currentDistance) {
        double distance;
        for (Vertex neighbor : getTargetEdge(current)) {
            distance = currentDistance + getEdgeWeight(current, neighbor);
            if (containsEdge(current, find))
                return distance;
            double ren = distance(neighbor, find, distance);
            if (ren != Double.POSITIVE_INFINITY)
                return ren;
        }
        return Double.POSITIVE_INFINITY;
    }

    /**
     * Returns the parent of the target vertex v in the search tree/forest. If v is the root of a tree,
     * this method returns <code> null </code>.
     * If v does not belong to the visited Graph, it throws an IllegalArgumentException.
     *
     * @param v the target vertex.
     * @return the parent of v in the current search.
     * @throws IllegalArgumentException if v does not belong to the graph.
     */
    @Override
    public Vertex getParentOf(Vertex v) throws IllegalArgumentException {
        if (source == v)
            return null;

        for (EdgeImpl e : edgees) {
            if (e.getTarget() == v) {
                return e.getSource();
            }
        }

        throw new IllegalArgumentException("The vertex is not contained in the visited graph");
    }

    /**
     * Returns the visit time (where the first time is 1) in which the target vertex v was discovered
     * in the current visit (i.e., when v became gray). If v was not discovered, this method returns
     * -1.
     * If v does not belong to the visited Graph, it throws an IllegalArgumentException.
     *
     * @param v the target vertex.
     * @return the time in which v was discovered.
     * @throws IllegalArgumentException if v does not belong to the graph.
     */
    @Override
    public int getStartTime(Vertex v) throws IllegalArgumentException {
        if (!graph.containsVertex(v))
            throw new IllegalArgumentException("The vertex is not contained in the visited graph");
        if (v.equals(source))
            return 1;
        int count = 1;
        switch (type) {
            case DIJKSTRA:
            case BFS:
                LinkedList<Vertex> queue = new LinkedList<Vertex>();
                queue.add(source);
                while (!queue.isEmpty()) {
                    Vertex current = queue.element();
                    for (Vertex neighbor : getTargetEdge(current)) {
                        count++;
                        if (v == neighbor)
                            return count;
                        queue.add(neighbor);
                    }
                    queue.remove(0);
                }
            case DFS_TOT:
                DFSv = 0;
                DFSStartTime(source, v, 2);
                if (DFSv != 0)
                    return DFSv;
            default:
                return -1;
        }
    }

    private int DFSv = 0;

    private int DFSStartTime(Vertex current, Vertex find, int count) {
        if (current.equals(find))
            return count;
        for (Vertex neighbor : getTargetEdge(current)) {
            count++;
            if (containsEdge(neighbor, find)) {
                if (DFSv == 0)
                    DFSv = count;
                return count;
            }
            count = DFSStartTime(neighbor, find, count);
        }
        return count;
    }

    /**
     * Returns the visit time (where the first time is 1) in which the target vertex v was closed
     * in the current visit (i.e., when v became black). If v was not discovered, this method returns
     * -1.
     * If v does not belong to the visited Graph, it throws an IllegalArgumentException.
     *
     * @param v the target vertex.
     * @return the time in which v was closed.
     * @throws IllegalArgumentException if v does not belong to the graph.
     */
    @Override
    public int getEndTime(Vertex v) throws IllegalArgumentException {
        if (!graph.containsVertex(v))
            throw new IllegalArgumentException("The vertex is not contained in the visited graph");
        if (v == null)
            return -1;
        return tree.size() - getStartTime(v);
    }

    /**
     * Returns the weight of the edge between v1 and v2 in the current search result, if it exists.
     * Otherwise, it returns an IllegalArgumentException.
     * If v1 and/or v2 do not belong to the visited Graph, it throws an IllegalArgumentException.
     *
     * @param v1 the parent vertex.
     * @param v2 the child vertex
     * @return the weight of the edge (v1,v2).
     * @throws IllegalArgumentException if v1 or v2 do not belong to the graph.
     */
    @Override
    public double getEdgeWeight(Vertex v1, Vertex v2) throws IllegalArgumentException {
        if (!graph.containsVertex(v1) || !graph.containsVertex(v2))
            throw new IllegalArgumentException("The vertex is not contained in the visited graph");

        if (containsEdge(v1, v2)) {
            return graph.getEdgeWeight(v1, v2);
        } else {
            throw new IllegalArgumentException("Does not exist the edge between v1 and v2");
        }
    }
}