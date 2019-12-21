/**
 *
 */
package upo.graphimpl;

import upo.graph.*;

import java.util.*;

/**
 * Implements Graph that extends an iterable through List
 *
 * @author Simone Massaro
 */
public class DirectedGraphAdjList implements Graph {
    private LinkedHashMap<Vertex, LinkedList<Vertex>> graph;
    private HashMap<Vertex, Colors> visitedNodes;
    private final boolean isDirected = true;

    /**
     * Initializes the graph
     *
     * @param isDirected Indicates if the graph is directed
     */
    public DirectedGraphAdjList() {
        graph = new LinkedHashMap<>();
        visitedNodes = new HashMap<>();
    }

    /**
     * @return the number of vertices of the graph.
     */
    private int getVerticesNumber() {
        return graph.keySet().size();
    }

    /**
     * Return a list of head vertices of the graph.
     * If there aren't, returns the first vertex inserted in the graph.
     *
     * @return list of head vertices
     */
    private LinkedList<Vertex> getHeadVertices() {
        LinkedList<Vertex> root = new LinkedList<Vertex>();
        for (Vertex v : graph.keySet())
            if (inDegreeOf(v) == 0 && !graph.get(v).isEmpty())
                root.add(v);
        if (root.isEmpty())
            root.add(graph.keySet().stream().findFirst().orElse(null));
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
            for (Vertex neighbor : graph.get(current)) {
                if (neighbor.equals(find) && (currentComponents.size() < components.size() || components.isEmpty())) {
                    components.clear();
                    components.add(neighbor);
                } else if (visitedNodes.get(neighbor).equals(Colors.WHITE)) {
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
        visitedNodes.clear();
        for (Vertex v : graph.keySet())
            visitedNodes.put(v, Colors.WHITE);
    }

    /**
     * Change the value of the vertex v in the variable visitedNodes with color.
     *
     * @param v
     * @param color
     */
    private void setColorVertextVisitedNodes(Vertex v, Colors color) {
        visitedNodes.put(ListStructuresFunctions.getKeyAsVertex(v, graph), color);
    }

    @Override
    public Iterator<Vertex> iterator() {
        //Iterator over the HashMap keys
        return graph.keySet().iterator();
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
            graph.put(v, new LinkedList<Vertex>());
            return true;
        }
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
        return v != null && ListStructuresFunctions.keyExists(v, graph);
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
        //If the vertex is null or the graph doesn't contain it, returns null
        //graph.keySet().stream().noneMatch(x -> x.getLabel().equals(v.getLabel()))
        if (v == null || !containsVertex(v))
            return false;

        //Removes the key (aka the vertex)
        graph.remove(ListStructuresFunctions.getKeyAsVertex(v, graph));

        //For each keys, checks if the LinkedList contains the vertex and if it does, deletes it
        for (Vertex key : graph.keySet()) {
            if (ListStructuresFunctions.adjListContains(ListStructuresFunctions.getAdjListIfExists(v, graph), v))
                (ListStructuresFunctions.getAdjListIfExists(v, graph)).remove(graph.get(key).stream().filter(x -> x.getLabel().equals(v.getLabel())).findFirst().orElse(null));
        }

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

        for (Vertex key : graph.keySet())
            vertices.add(key);

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
        if (!containsEdge(sourceVertex, targetVertex)) {
            graph.get(ListStructuresFunctions.getKeyAsVertex(sourceVertex, graph)).add(targetVertex);
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
        LinkedList<Vertex> vertices = ListStructuresFunctions.getAdjListIfExists(sourceVertex, graph);
        if (vertices != null && vertices.size() > 0)
            return ListStructuresFunctions.adjListContains(vertices, targetVertex);

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

        for (Vertex key : graph.keySet())
            for (Vertex vtx : graph.get(key))
                edges.add(new EdgeImpl(key, vtx, this));

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
            for (Vertex key : graph.keySet()) {
                //Doesn't consider itself
                if (key.getLabel().equals(vertex.getLabel()))
                    continue;

                //Increments the counter if the given vertex is present in the Adj list
                for (Vertex vtx : graph.get(key))
                    if (vtx.getLabel().equals(vertex.getLabel()))
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
            return ListStructuresFunctions.getAdjListIfExists(vertex, graph).size();
        else
            throw new UnsupportedOperationException();
    }

    /**
     * Returns true if this is a directed graph.
     *
     * @return true if this is a directed graph, false otherwise
     */
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
        if (ListStructuresFunctions.adjListContains(ListStructuresFunctions.getAdjListIfExists(sourceVertex, graph), targetVertex)) {
            if (ListStructuresFunctions.getAdjListIfExists(sourceVertex, graph).remove(targetVertex))
                return new EdgeImpl(sourceVertex, targetVertex, this);
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
        if (graph.keySet().isEmpty())
            return null;
        setNotVisitedNodes();
        Vertex root = getHeadVertices().get(0);
        GraphSearchResultImpl tree = new GraphSearchResultImpl(type, root, this);
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
                    for (Vertex neighbor : graph.get(current)) {
                        //If the node has not been visited, adds it to the queue
                        if (visitedNodes.get(neighbor).equals(Colors.WHITE)) {
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
        for (Vertex neighbor : graph.get(current)) {
            //If the node has not been visited, adds it to the queue
            if (visitedNodes.get(neighbor).equals(Colors.WHITE)) {
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
        for (Vertex v : graph.keySet()) {
            setNotVisitedNodes();
            if (!checkCicle(v, v).isEmpty())
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
        return !isCyclic() && isDirected;
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
            for (Vertex neighbor : graph.get(current)) {
                //If the node has not been visited, adds it to the queue
                if (visitedNodes.get(neighbor).equals(Colors.WHITE)) {
                    queue.add(neighbor);
                    setColorVertextVisitedNodes(neighbor, Colors.GREY);
                }
            }
            // delete the first of the queue end set it black
            queue.remove(0);
            if (!visitedNodes.get(current).equals(Colors.BLACK)) {
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

        for (Vertex current : graph.keySet()) {
            if (!visitedNodes.get(current).equals(Colors.BLACK)) {
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
        String ren = "";
        boolean k = false, i = false;

        ren += "{";
        for (Collection<Vertex> collectionV : components) {
            if (k)
                ren += ",";
            k = true;
            ren += "{";
            i = false;
            for (Vertex v : collectionV) {
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