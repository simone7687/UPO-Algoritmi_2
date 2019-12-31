package upo.graphimpl;

import upo.graph.*;

import java.util.LinkedList;

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

    private int findEdge(Vertex sourceVertex, Vertex targetVertex) {
        for(int i = 0; i < edgees.length; i++) {
            if (edgees[i] != null)
                if (edgees[i].getSource() == sourceVertex && edgees[i].getTarget() == targetVertex)
                    return i;
        }
        throw new UnsupportedOperationException("");
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
            case DFS:
            case DFS_TOT:
                throw new UnsupportedOperationException("This visit cannot be performed on unweighted graphs");
            case DIJKSTRA:
                LinkedList<Vertex> queue = new LinkedList<Vertex>();
                //Adds the root to the queue and sets it as gray
                setColorVertextVisitedNodes(root, Colors.GREY);
                queue.add(root);
                //Adds the root in the BFS tree
                tree.addLeaves(root);
                //While the queue is not empty
                while (!queue.isEmpty()) {
                    //Gets the first element from the queue
                    Vertex current = queue.element();
                    //Add the vertices connected to it in the queue and in the BFS tree if they are white
                    for (Vertex neighbor : getEdgees(current)) {
                        //If the node has not been visited, adds it to the queue
                        if (visitedNodes[findVertex(neighbor)].equals(Colors.WHITE)) {
                            queue.add(neighbor);
                            tree.addLeaves(neighbor);
                            if (containsEdge(current, neighbor))
                                tree.addEdge(current, neighbor, edgees[findEdge(current, neighbor)].getEdgeWeight());
                            else
                                tree.addEdge(current, neighbor);
                            setColorVertextVisitedNodes(neighbor, Colors.GREY);
                        } else if (visitedNodes[findVertex(neighbor)].equals(Colors.GREY)) {
                            if (tree.getDistance(neighbor) > tree.getDistance(current) + getEdgeWeight(current, neighbor)) {
                                tree.removeLeaves(neighbor);
                                tree.addLeaves(neighbor);
                                if (containsEdge(current, neighbor))
                                    tree.addEdge(current, neighbor, edgees[findEdge(current, neighbor)].getEdgeWeight());
                                else
                                    tree.addEdge(current, neighbor);
                            }
                        }
                    }
                    // delete the first of the queue end set it black
                    queue.remove(0);
                    setColorVertextVisitedNodes(current, Colors.BLACK);
                }
                return tree;
            default:
                return null;
        }
    }

    /**
     * TODO: javdoc
     */
    public double[][] allToAllShortestPaths() {
        double[][] distance = new double[maxNVertex][maxNVertex];

        for (int i = 0; i < getVerticesNumber(); i++)
            for (int j = 0; j < getVerticesNumber(); j++)
                distance[i][j] = Double.POSITIVE_INFINITY;

        for (int i = 0; i < getVerticesNumber(); i++)
            distance[i][i] = 0;

        for (int i = 0; i < edgees.length; i++)
            if (edgees[i] != null) {
                Vertex vs = edgees[i].getSource();
                Vertex vt = edgees[i].getTarget();
                int s = findVertex(vs);
                int t = findVertex(vt);
                distance[s - 1][t - 1] = edgees[i].getEdgeWeight();
            }

        for (int k = 0; k < getVerticesNumber(); k++)
            for (int i = 0; i < getVerticesNumber(); i++)
                for (int j = 0; j < getVerticesNumber(); j++)
                    if (distance[i][j] > distance[i][k] + distance[k][j])
                        distance[i][j] = distance[i][k] + distance[k][j];
        return distance;
    }
}