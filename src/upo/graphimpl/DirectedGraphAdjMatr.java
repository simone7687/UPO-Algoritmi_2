/**
 *
 */
package upo.graphimpl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import upo.graph.Edge;
import upo.graph.Graph;
import upo.graph.GraphSearchResult;
import upo.graph.SearchType;
import upo.graph.Vertex;

/**
 * Implements Graph that extends an iterable through Matrices
 * 
 * @author Simone Massaro
 */
public class DirectedGraphAdjMatr implements Graph {
    private final int maxNVertex = 100;

    private Vertex graph[][];
    private Colors visitedNodes[];
    private final boolean isDirected = true;

    /**
     * Initializes the graph
     *
     * @param isDirected Indicates if the graph is directed
     */
    public DirectedGraphAdjMatr() {
        graph = new Vertex[maxNVertex][maxNVertex];
        visitedNodes = new Colors[maxNVertex];
    }

    /**
     * @return the number of vertices of the graph.
     */
    private int getVerticesNumber() {
        return vertexSet().size();
    }
    /**
     * Return a list of head vertices of the graph.
     * If there aren't, returns the first vertex inserted in the graph.
     * 
     * @return list of head vertices
     */
    private LinkedList<Vertex> getHeadVertices() {
        LinkedList<Vertex> root = new LinkedList<Vertex>();
        for (Vertex v : vertexSet())
            if (inDegreeOf(v) == 0 && v != null) {
                root.add(v);
                System.out.println(v.getLabel());
            }
        if (root.isEmpty())
            for (Vertex v : vertexSet()) {
                root.add(v);
                return root;
            }
        return root;
    }
    /**
     * Find the shortest path from current to find.
     * Returns null if it does not exist.
     * 
     * @param current
     * @param find
     * @return the list of vertices of the route
     */
    private Collection<Vertex> checkCicle(Vertex current, Vertex find) {
        Collection<Vertex> currentComponents = new LinkedList<Vertex>();
        Collection<Vertex> neighborComponents = new LinkedList<Vertex>();
        Collection<Vertex> components = new LinkedList<Vertex>();
        setColorVertextVisitedNodes(current, Colors.GREY);
        //Gets the vertices connected to it
        if (current != null)
            for (Vertex neighbor : getEdgees(current)) {
                if (neighbor.equals(find) && (currentComponents.size() < components.size() || components.isEmpty())) {
                    components.clear();
                    components.add(neighbor);
                } else if (visitedNodes[findVertex(neighbor)].equals(Colors.WHITE)) {
                    neighborComponents = checkCicle(neighbor, find);
                    if (!neighborComponents.isEmpty() && (neighborComponents.size() < components.size() || components.isEmpty())) {
                        components.clear();
                        components.addAll(neighborComponents);
                        components.add(neighbor);
                    }
                }
            }
        return components;
    }
    /**
     * Initializes the variable visitedNodes to 'not visited'.
     */
    private void setNotVisitedNodes() {
        for (int i = 0; i < maxNVertex; i++)
            visitedNodes[i] = Colors.WHITE;
    }
    /**
     * Change the value of the vertex v in the variable visitedNodes with color.
     * 
     * @param v
     * @param color
     */
    private void setColorVertextVisitedNodes(Vertex v, Colors color) {
        visitedNodes[findVertex(v)] = color;
    }
    /**
     * Returns the position of v in the graph.
     * if v is null returns 0.
     * 
     * @param v
     * @return the position of v
     */
    private int findVertex(Vertex v) {
        // If the vertex is null or does not exist in the graph, returns false
        if (v == null)
            return 0;
        for (int i = 0; i < maxNVertex; i++) {
            if (graph[i][0].equals(v)) {
                return i + 1;
            }
        }
        return 0;
    }
    /**
     * returns the list of Edgees of v.
     * if v is null returns null.
     * 
     * @param v
     * @return
     */
    private Collection<Vertex> getEdgees(Vertex v) {
        LinkedList<Vertex> edgees = new LinkedList<Vertex>();
        // If the vertex is null or does not exist in the graph, returns false
        if (v == null)
            return null;
        for (int i = 1; i < maxNVertex; i++)
            if (graph[findVertex(v)][i] != null)
                edgees.add(graph[findVertex(v)][i]);
        return edgees;
    }

    @Override
    public Iterator<Vertex> iterator() {
        return graph[0][0].iterator();
    }

    /**
     * Adds the specified vertex to this graph if not already present. More
     * formally, adds the specified vertex, <code>v</code>, to this graph if this
     * graph contains no vertex <code>u</code> such that <code>
     * u.equals(v)</code>. If this graph already contains such vertex, the call
     * leaves this graph unchanged and returns <tt>false</tt>. In combination with
     * the restriction on constructors, this ensures that graphs never contain
     * duplicate vertices.
     *
     * @param v vertex to be added to this graph.
     *
     * @return <tt>true</tt> if this graph did not already contain the specified
     *         vertex.
     *
     * @throws NullPointerException if the specified vertex is <code>
     * null</code>               .
     */
    @Override
    public boolean addVertex(Vertex v) {
        // If the vertex is null, throws NullPointerException
        if (v == null)
            throw new NullPointerException("The given parameter is null");

        //Vertex exists
        if (containsVertex(v))
            return false;
        else {
            //Adds the vertex and initializes an empty list
            for (int i = 0; i < maxNVertex; i++) {
                if (graph[i][0] == null) {
                    graph[i][0] = v;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns <tt>true</tt> if this graph contains the specified vertex. More
     * formally, returns <tt>true</tt> if and only if this graph contains a vertex
     * <code>u</code> such that <code>u.equals(v)</code>. If the specified vertex is
     * <code>null</code> returns <code>false</code>.
     *
     * @param v vertex whose presence in this graph is to be tested.
     *
     * @return <tt>true</tt> if this graph contains the specified vertex.
     */
    @Override
    public boolean containsVertex(Vertex v) {
        // If the vertex is null or does not exist in the graph, returns false
        if (v == null)
            return false;
        for (int i = 0; i < maxNVertex; i++) {
            if (graph[i][0] != null)
                if (graph[i][0].equals(v))
                    return true;
        }
        return false;
    }

    /**
     * Removes the specified vertex from this graph including all its touching edges
     * if present. More formally, if the graph contains a vertex <code>
     * u</code> such that <code>u.equals(v)</code>, the call removes all edges that
     * touch <code>u</code> and then removes <code>u</code> itself. If no such
     * <code>u</code> is found, the call leaves the graph unchanged. Returns
     * <tt>true</tt> if the graph contained the specified vertex. (The graph will
     * not contain the specified vertex once the call returns).
     *
     * <p>
     * If the specified vertex is <code>null</code> returns <code>
     * false</code>.
     * </p>
     *
     * @param v
     *            vertex to be removed from this graph, if present.
     *
     * @return <code>true</code> if the graph contained the specified vertex;
     *         <code>false</code> otherwise.
     */
    @Override
    public boolean removeVertex(Vertex v) {
        if (v == null || !containsVertex(v))
            return false;

        for (int i = 0; i < maxNVertex; i++)
            graph[findVertex(v)][i] = null;
        return true;
    }

    /**
     * Returns a set of the vertices contained in this graph.
     *
     *
     * @return a set view of the vertices contained in this graph.
     */
    @Override
    public Set<Vertex> vertexSet() {
        Set<Vertex> vertices = new HashSet<Vertex>();

        for (int i = 0; i < maxNVertex; i++) {
            if (graph[i][0] != null)
                vertices.add(graph[i][0]);
        }

        return vertices;
    }

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
     *
     * @param sourceVertex
     *            source vertex of the edge.
     * @param targetVertex
     *            target vertex of the edge.
     *
     * @return The newly created edge if added to the graph, otherwise <code>
     * null</code>.
     *
     * @throws IllegalArgumentException
     *             if source or target vertices are not found in the graph.
     * @throws NullPointerException
     *             if any of the specified vertices is <code>
     * null</code>.
     *
     */
    @Override
    public Edge addEdge(Vertex sourceVertex, Vertex targetVertex) {
        //If any of the specified vertex are null, throws NullPointerException
        if (sourceVertex == null || targetVertex == null)
            throw new NullPointerException("One or more given parameter is null.");

        //If the graph doesn't contain both the specified vertex, throws IllegalArgumentException
        if (!containsVertex(sourceVertex) || !containsVertex(targetVertex))
            throw new IllegalArgumentException("One or both vertices are not contained in the graph.");

        //Checks if the list doesn't contain the value already, creates an edge, adds it to the edges set and returns it
        if (!containsEdge(sourceVertex, targetVertex))
            for (int i = 1; i < maxNVertex; i++)
                if (graph[findVertex(sourceVertex)][i] == null) {
                    graph[findVertex(sourceVertex)][i] = targetVertex;
                    return new EdgeImpl(sourceVertex, targetVertex, this);
                }

        return null;
    }

    /**
     * Returns <tt>true</tt> if and only if this graph contains an edge going from
     * the source vertex to the target vertex. If any of the specified vertices does
     * not exist in the graph, or if is <code>
     * null</code>, returns <code>false</code>.
     *
     * @param sourceVertex
     *            source vertex of the edge.
     * @param targetVertex
     *            target vertex of the edge.
     *
     * @return <tt>true</tt> if this graph contains the specified edge.
     */
    @Override
    public boolean containsEdge(Vertex sourceVertex, Vertex targetVertex) {
        //Returns false if any of the specified vertex does not exist in the current graph
        if (!containsVertex(sourceVertex) || !containsVertex(targetVertex))
            return false;

        //If the graph contains an edge from source to target, returns true. Does not consider edge weight.
        for (Vertex v : getEdgees(sourceVertex))
            if (v == targetVertex)
                return true;

        return false;
    }

    /**
     * Returns a set of the edges contained in this graph.
     *
     *
     * @return a set of the edges contained in this graph.
     */
    @Override
    public Set<Edge> edgeSet() {
        Set<Edge> edges = new HashSet<Edge>();

        for (int i = 0; i < maxNVertex; i++)
            if (graph[i][0] != null)
                for (Vertex v : getEdgees(graph[i][0]))
                    if (v != null)
                        edges.add(new EdgeImpl(graph[i][0], v, this));

        return edges;
    }

    /**
     * Returns the degree of the specified vertex.
     *
     * <p>
     * A degree of a vertex in an undirected graph is the number of edges touching
     * that vertex. Edges with same source and target vertices (self-loops) are
     * counted twice.
     *
     * <p>
     * In directed graphs this method returns the sum of the "in degree" and the
     * "out degree".
     *
     * @param vertex
     *            vertex whose degree is to be calculated.
     * @return the degree of the specified vertex.
     */
    @Override
    public int degreeOf(Vertex vertex) {
        if (!isDirected)
            throw new UnsupportedOperationException();

        return inDegreeOf(vertex) + outDegreeOf(vertex);
    }

    /**
     * Returns the "in degree" of the specified vertex.
     *
     * <p>
     * The "in degree" of a vertex in a directed graph is the number of inward
     * directed edges from that vertex.
     *
     * <p>
     * In the case of undirected graphs this method returns the number of edges
     * touching the vertex. Edges with same source and target vertices (self-loops)
     * are counted twice.
     *
     * @param vertex
     *            vertex whose degree is to be calculated.
     * @return the degree of the specified vertex.
     */
    @Override
    public int inDegreeOf(Vertex vertex) {
        if (isDirected) {
            int inCount = 0;
            //Iterates between all vertices
            for (Vertex i : vertexSet()) {
                //Doesn't consider itself
                if (i == vertex)
                    continue;

                //Increments the counter if the given vertex is present in the Adj list
                for (Vertex v : getEdgees(i))
                    if (v == vertex)
                        inCount++;
            }

            return inCount;
        } else {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Returns the "out degree" of the specified vertex.
     *
     * <p>
     * The "out degree" of a vertex in a directed graph is the number of outward
     * directed edges from that vertex.
     *
     * <p>
     * Edges with same source and target vertices (self-loops) are counted twice.
     *
     * @param vertex
     *            vertex whose degree is to be calculated.
     * @return the degree of the specified vertex.
     */
    @Override
    public int outDegreeOf(Vertex vertex) {
        if (isDirected)
            return getEdgees(vertex).size();
        else
            throw new UnsupportedOperationException();
    }

    @Override
    public boolean isDirected() {
        return isDirected;
    }

    /**
     * Removes an edge going from source vertex to target vertex, if such vertices
     * and such edge exist in this graph. Returns the edge if removed or
     * <code>null</code> otherwise.
     *
     * @param sourceVertex
     *            source vertex of the edge.
     * @param targetVertex
     *            target vertex of the edge.
     *
     * @return The removed edge, or <code>null</code> if no edge removed.
     */
    @Override
    public Edge removeEdge(Vertex sourceVertex, Vertex targetVertex) {
        //Returns null if any of the specified vertex does not exist in the current graph
        if (!containsVertex(sourceVertex) || !containsVertex(targetVertex))
            return null;

        //Finds the edge with sourceVertex and targetVertex and removes it
        if (containsEdge(sourceVertex, targetVertex))
            for (int i = 1; i < maxNVertex; i++) {
                if (graph[findVertex(sourceVertex)][i].equals(sourceVertex)) {
                    graph[findVertex(sourceVertex)][i] = null;
                    return new EdgeImpl(sourceVertex, targetVertex, this);
                }
            }

        return null;
    }

    /**
     * Returns the weight assigned to a given edge. Unweighted graphs return 1.0 (as
     * defined by {@link #DEFAULT_EDGE_WEIGHT}), allowing weighted-graph algorithms
     * to apply to them when meaningful.
     *
     * @param sourceVertex
     *            source vertex of the edge.
     * @param targetVertex
     *            target vertex of the edge.
     * @return edge weight
     */
    @Override
    public double getEdgeWeight(Vertex sourceVertex, Vertex targetVertex) {
        //Returns 0 if any of the specified vertex does not exist in the current graph
        if (!containsVertex(sourceVertex) || !containsVertex(targetVertex))
            return 0;

        //Unweighted graph
        return DEFAULT_EDGE_WEIGHT;
    }

    /**
     * Assigns a weight to an edge.
     *
     * @param sourceVertex
     *            source vertex of the edge.
     * @param targetVertex
     *            target vertex of the edge.
     * @param weight
     *            new weight for edge
     * @throws UnsupportedOperationException
     *             if the graph does not support weights
     */
    @Override
    public void setEdgeWeight(Vertex sourceVertex, Vertex targetVertex, double weight) {
        //If any of the specified vertex does not exist in the current graph does nothing
        if (!containsVertex(sourceVertex) || !containsVertex(targetVertex))
            return;

        //Unweighted graph
        throw new UnsupportedOperationException("This graph does not support weights");
    }

    /**
     * Performs a visit (of type <code>type</code>) over the current graph.
     * DA IMPLEMENTARE (per pratico 1): solo BFS e DFS_TOT.
     * HINT: si consideri l'implementazione di una visita generica che prenda in input un oggetto "frangia",
     * con frangia diversa a seconda della visita (come visto a lezione).
     *
     * @param type the search type.
     *
     * @throws UnsupportedOperationException if the visit cannot be performed on the current graph
     * (e.g., a Dijkstra visit on an unweighted graph).
     *
     * @return a GraphSearchtreeult reptreeenting the treeult of the visit performed.
     *
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
                            tree.addEdge(current, neighbor);
                            setColorVertextVisitedNodes(neighbor, Colors.GREY);
                        }
                    }
                    // delete the first of the queue end set it black
                    queue.remove(0);
                    setColorVertextVisitedNodes(current, Colors.BLACK);
                }
                return tree;
            case DFS:
                throw new UnsupportedOperationException();    // TODO: non so cosa si deve fare
            case DFS_TOT:
                //Adds the root to the queue and sets it as gray
                setColorVertextVisitedNodes(root, Colors.GREY);
                //Adds the root in the BFS tree
                tree.addLeaves(root);
                return DFSrecursive(tree, root);
            case DIJKSTRA:
                throw new UnsupportedOperationException("This visit cannot be performed on unweighted graphs");    // TODO: non sono sicuro che non si veve implementare per questo grafico
            default:
                return null;
        }
    }

    private GraphSearchResultImpl DFSrecursive(GraphSearchResultImpl tree, Vertex current) {
        setColorVertextVisitedNodes(current, Colors.GREY);
        //Add the vertices connected to it in the queue and in the DFS tree if they are white
        for (Vertex neighbor : getEdgees(current)) {
            //If the node has not been visited, adds it to the queue
            if (visitedNodes[findVertex(neighbor)].equals(Colors.WHITE)) {
                tree.addLeaves(neighbor);
                tree.addEdge(current, neighbor);
                tree = DFSrecursive(tree, neighbor);
            }
        }
        setColorVertextVisitedNodes(current, Colors.BLACK);
        return tree;
    }

    /**
     * Check whether the current graph contains cycles or not.
     * @return true if the current graph contains cycles, false otherwise.
     */
    @Override
    public boolean isCyclic() {
        for (int i = 0; i < maxNVertex; i++) {
            setNotVisitedNodes();
            if (!(checkCicle(graph[i][0], graph[i][0]).isEmpty()))    //ERRORE
                return true;
        }
        return false;
    }

    /**
     * Check whether the current graph is a directed acyclic graph (DAG) or not.
     * @return true if the current graph is a DAG, false otherwise.
     */
    @Override
    public boolean isDAG() {
        if (!isCyclic() && isDirected)
            return true;

        return false;
    }

    /**
     * If the current graph is a DAG, it returns a topological sort of this graph.
     *
     * @throws UnsupportedOperationException if the current graph is not a DAG.
     *
     * @return a topological sort of this graph.
     */
    @Override
    public Vertex[] topologicalSort() {
        if (!isDAG())
            throw new UnsupportedOperationException("The current graph is not a DAG.");
        setNotVisitedNodes();
        LinkedList<Vertex> queue = new LinkedList<Vertex>();
        Vertex[] topologicalSortVertexs = new Vertex[getVerticesNumber()];
        int i = 0;
        //Adds the root to the queue and sets it as gray
        queue.addAll(getHeadVertices());
        for (Vertex v : getHeadVertices())
            setColorVertextVisitedNodes(v, Colors.GREY);
        //While the queue is not empty
        while (!queue.isEmpty()) {
            //Gets the first element from the queue
            Vertex current = queue.element();
            //Add the vertices connected to it in the queue if they are white
            for (Vertex neighbor : getEdgees(current)) {
                //If the node has not been visited, adds it to the queue
                if (visitedNodes[findVertex(neighbor)].equals(Colors.WHITE)) {
                    queue.add(neighbor);
                    setColorVertextVisitedNodes(neighbor, Colors.GREY);
                }
            }
            // delete the first of the queue end set it black
            queue.remove(0);
            if (!visitedNodes[findVertex(current)].equals(Colors.BLACK)) {
                topologicalSortVertexs[i] = current;
                i++;
                setColorVertextVisitedNodes(current, Colors.BLACK);
            }
        }
        return topologicalSortVertexs;
    }

    /**
     * If the current graph is directed, this method returns the strongly connected components
     * of the graph. Otherwise, the method throws an UnsupportedOperationException.
     *
     * @throws UnsupportedOperationException if the current graph is not directed.
     *
     * @return a collection of collections of vertices representing the strongly connected components of the graph.
     */
    @Override
    public Collection<Collection<Vertex>> stronglyConnectedComponents() {
        if (!isDirected)
            throw new UnsupportedOperationException("The current graph is not directed.");

        Collection<Collection<Vertex>> components = new LinkedList<Collection<Vertex>>();

        setNotVisitedNodes();

        for (Vertex current : vertexSet()) {
            if (!visitedNodes[findVertex(current)].equals(Colors.BLACK)) {
                Collection<Vertex> currentComponents = new LinkedList<Vertex>();
                currentComponents.addAll(checkCicle(current, current));
                if (currentComponents.isEmpty())
                    currentComponents.add(current);
                for (Vertex v : currentComponents)
                    setColorVertextVisitedNodes(v, Colors.BLACK);
                components.add(currentComponents);
            }
        }
        return components;
    }

    /**
     * Returns a string representing the strongly connected components of the graph.
     * For instance: {{1,2,3},{4,6},{5}}
     *
     * @throws UnsupportedOperationException if the current graph is not directed.
     *
     * @return a string representing the strongly connected components of the graph.
     */
    @Override
    public String toStringSCC() {
        if (!isDirected)
            throw new UnsupportedOperationException("The current graoh is not directed.");

        Collection<Collection<Vertex>> components = stronglyConnectedComponents();
        String ren = new String();
        boolean k = false, i = false;

        ren += "{";
        for (Collection<Vertex> collectionV : components) {
            if (k)
                ren += ",";
            k = true;
            ren += "{";
            i = false;
            for (Vertex v : collectionV)
                if (v != null) {
                    if (i)
                        ren += ",";
                    i = true;
                    ren += v.getLabel();
                }
            ren += "}";
        }
        ren += "}";

        return ren;
    }
}