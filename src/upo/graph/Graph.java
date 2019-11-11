package upo.graph;

import java.util.*;

/**
 * The root interface in the graph hierarchy.
 *
 */
public interface Graph extends Iterable<Vertex> 
{
	
	/**
	 * The default weight for an edge.
	 */
	final double DEFAULT_EDGE_WEIGHT = 1.0;

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
	public boolean addVertex(Vertex v);

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
	public boolean containsVertex(Vertex v);

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
	public boolean removeVertex(Vertex v);

	/**
	 * Returns a set of the vertices contained in this graph.
	 *
	 *
	 * @return a set view of the vertices contained in this graph.
	 */
	public Set<Vertex> vertexSet();
	
	/**
	 * Creates a new edge in this graph, going from the source vertex to the target
	 * vertex, and returns the created edge. Some graphs do not allow
	 * edge-multiplicity. In such cases, if the graph already contains an edge from
	 * the specified source to the specified target, than this method does not
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
	public Edge addEdge(Vertex sourceVertex, Vertex targetVertex);


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
	public boolean containsEdge(Vertex sourceVertex, Vertex targetVertex);


	/**
	 * Returns a set of the edges contained in this graph.
	 *
	 *
	 * @return a set of the edges contained in this graph.
	 */
	public Set<Edge> edgeSet();

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
	public int degreeOf(Vertex vertex);

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
	public int inDegreeOf(Vertex vertex);

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
	public int outDegreeOf(Vertex vertex);
	
	/**
	 * Returns true if this is a directed graph.
	 * 
	 * @return true if this is a directed graph, false otherwise
	 */
	public boolean isDirected();
	

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
	public Edge removeEdge(Vertex sourceVertex, Vertex targetVertex);
	
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
	public double getEdgeWeight(Vertex sourceVertex, Vertex targetVertex);

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
	public void setEdgeWeight(Vertex sourceVertex, Vertex targetVertex, double weight);

	/**
	 * Compares the specified object with this set for equality. 
	 * Returns true if the given object is also a Graph, the two graphs have the same number of vertices, with
	 * the same labels, and the given graph has an edge between each pair of adjacent vertices.
	 * This ensures that the equals method works properly across different implementations of the Graph interface.
	 * 	 
	 * @param o object to be compared for equality with this graph
	 * @return true if the specified object is equal to this graph, false otherwise
	 */
	@Override
	public boolean equals(Object o);

	/**
	 * Returns a string representation of <code>this</code>, of form: </br>
	 * Vertices: v1, v2, ... vn </br>
	 * v1 -> v4 </br>
	 * v2 -> v6 </br>
	 * 
	 * @return
	 */
	@Override
	public String toString();
	
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
	 * @return a GraphSearchResult representing the result of the visit performed.
	 * 
	 */
	public GraphSearchResult visit(SearchType type) throws UnsupportedOperationException;
	
	/**
	 * Check whether the current graph contains cycles or not.
	 * @return true if the current graph contains cycles, false otherwise.
	 */
	public boolean isCyclic();
	
	/**
	 * Check whether the current graph is a directed acyclic graph (DAG) or not.
	 * @return true if the current graph is a DAG, false otherwise.
	 */
	public boolean isDAG();
	
	/**
	 * If the current graph is a DAG, it returns a topological sort of this graph. 
	 * 
	 * @throws UnsupportedOperationException if the current graph is not a DAG.
	 * 
	 * @return a topological sort of this graph. 
	 */
	public Vertex[] topologicalSort();
	
	/**
	 * If the current graph is directed, this method returns the strongly connected components
	 * of the graph. Otherwise, the method throws an UnsupportedOperationException.
	 * 
	 * @throws UnsupportedOperationException if the current graph is not directed.
	 * 
	 * @return a collection of collections of vertices representing the strongly connected components of the graph.
	 */
	public Collection<Collection<Vertex>> stronglyConnectedComponents();
	
	/**
	 * Returns a string representing the strongly connected components of the graph.
	 * For instance: {{1,2,3},{4,6},{5}}
	 * 
	 * @throws UnsupportedOperationException if the current graph is not directed.
	 * 
	 * @return a string representing the strongly connected components of the graph.
	 */
	public String toStringSCC();
	
}

// End Graph.java
