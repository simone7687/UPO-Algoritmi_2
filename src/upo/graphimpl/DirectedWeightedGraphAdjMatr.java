package upo.graphimpl;

import upo.graph.*;

/**
 * TODO: javadoc
 */
public class DirectedWeightedGraphAdjMatr extends DirectedGraphAdjMatr implements Graph {
    /**
     * Creates a new edge in this graph, going from the source vertex to the target
     * vertex, and returns the created edge. Some graphs do not allow
     * edge-multiplicity. In such cases, if the graph already contains an edge from
     * the specified source to the specified target, then this method does not
     * change the graph and returns <code>null</code>.
     *
     * <p>
     * The source and target vertices must already be contained in this graph. If
     * they are not found in graph IllegalArgumentException is thrown.
     * </p>
     *
     * @param sourceVertex source vertex of the edge.
     * @param targetVertex target vertex of the edge.
     * @param weight
     * @return The newly created edge if added to the graph, otherwise <code>
     * null</code>.
     * @throws IllegalArgumentException if source or target vertices are not found in the graph.
     * @throws NullPointerException     if any of the specified vertices is <code>
     *                                  null</code>.
     */
    public Edge addEdge(Vertex sourceVertex, Vertex targetVertex, double weight) {
        //If any of the specified vertex are null, throws NullPointerException
        if (sourceVertex == null || targetVertex == null)
            throw new NullPointerException("One or more given parameter is null.");

        //If the graph doesn't contain both the specified vertex, throws IllegalArgumentException
        if (!containsVertex(sourceVertex) || !containsVertex(targetVertex))
            throw new IllegalArgumentException("One or both vertices are not contained in the graph.");

        //Checks if the list doesn't contain the value already, creates an edge, adds it to the edges set and returns it
        if (!containsEdge(sourceVertex, targetVertex))
            for (int i = 1; i < maxNVertex; i++)
                if (edgees[i] == null) {
                    EdgeImpl e = new EdgeImpl(sourceVertex, targetVertex, weight, this);
                    edgees[i] = e;
                    return e;
                }

        return null;
    }

    /**
     * Assigns a weight to an edge.
     *
     * @param sourceVertex source vertex of the edge.
     * @param targetVertex target vertex of the edge.
     * @param weight       new weight for edge
     * @throws UnsupportedOperationException if the graph does not support weights
     */
    @Override
    public void setEdgeWeight(Vertex sourceVertex, Vertex targetVertex, double weight) {
        if (containsEdge(sourceVertex, targetVertex))
            for (int i = 0; i < maxNVertex; i++)
                if (edgees[i] != null)
                    if (edgees[i].getSource().equals(sourceVertex) && edgees[i].getTarget().equals(targetVertex))
                        edgees[i].setEdgeWeight(weight);
    }

    /**
     * TODO: java doc
     */
    @Override
    public GraphSearchResult visit(SearchType type) throws UnsupportedOperationException {
        //If there's no nodes, returns null
        if (graph == null)
            return null;

        GraphSearchResultImpl tree;

        setNotVisitedNodes();

        Vertex root = getHeadVertices().get(0);
        tree = new GraphSearchResultImpl(type, root, this);

        switch (type) {
            case BFS:
                throw new UnsupportedOperationException("This visit cannot be performed on unweighted graphs");
            case DFS:
                throw new UnsupportedOperationException("This visit cannot be performed on unweighted graphs");
            case DFS_TOT:
                throw new UnsupportedOperationException("This visit cannot be performed on unweighted graphs");
            case DIJKSTRA:
                return null;    //TODO:...
            default:
                return null;
        }
    }

    /**
     * TODO: javdoc
     */
    public double[][] allToAllShortestPaths() {
        double[][] paths = new double[maxNVertex][maxNVertex];
        //TODO:...
        return paths;
    }
}
