package upo.graph;

/**
 * The interface describing the result of a visit in a Graph.
 *
 */
public interface GraphSearchResult extends Iterable<Vertex> {
	
	/**
     * Returns the type of the search (e.g., BFS) which generates this result
     *
     * @return a SearchType describing the search which generates this result
     */
	public SearchType getType();
	
	/**
     * Returns the source vertex S of the search which generates this result
     *
     *
     * @return the source Vertex
     */
	public Vertex getSource();
	
	/**
     * For BFS and Dikstra search types, this method returns the <i> distance </i> (i.e., the length/weight 
     * of the shortest path between the source vertex S and v. If v is not reachable from S, this method 
     * returns Double.POSITIVE_INFINITY.
     * If v does not belong to the visited Graph, it throws an IllegalArgumentException.
     * For other visit types, it throws an UnsupportedOperationException.
     *
     *
     * @param v the target vertex.
     * 
     * @throws IllegalArgumentException if v does not belong to the graph.
     * @throws UnsupportedOperationException if this.getType() is different from BFS and DIJKSTRA
     *
     * @return \delta(S,v)
     */
	public double getDistance(Vertex v) throws UnsupportedOperationException, IllegalArgumentException;
	
	/**
     * Returns the parent of the target vertex v in the search tree/forest. If v is the root of a tree,
     * this method returns <code> null </code>.
     * If v does not belong to the visited Graph, it throws an IllegalArgumentException.
     *
     *
     * @param v the target vertex.
     * 
     * @throws IllegalArgumentException if v does not belong to the graph.
     *
     * @return the parent of v in the current search.
     */
	public Vertex getParentOf(Vertex v) throws IllegalArgumentException;
	
	/**
     * Returns the visit time (where the first time is 1) in which the target vertex v was discovered 
     * in the current visit (i.e., when v became gray). If v was not discovered, this method returns 
     * -1.
     * If v does not belong to the visited Graph, it throws an IllegalArgumentException.
     *
     * @param v the target vertex.
     * 
     * @throws IllegalArgumentException if v does not belong to the graph.
     *
     * @return the time in which v was discovered.
     */
	public int getStartTime(Vertex v) throws IllegalArgumentException;
	
	/**
     * Returns the visit time (where the first time is 1) in which the target vertex v was closed 
     * in the current visit (i.e., when v became black). If v was not discovered, this method returns 
     * -1.
     * If v does not belong to the visited Graph, it throws an IllegalArgumentException.
     *
     * @param v the target vertex.
     * 
     * @throws IllegalArgumentException if v does not belong to the graph.
     *
     * @return the time in which v was closed.
     */
	public int getEndTime(Vertex v) throws IllegalArgumentException;
	
	/**
     * Returns the weight of the edge between v1 and v2 in the current search result, if it exists. 
     * Otherwise, it returns an IllegalArgumentException.
     * If v1 and/or v2 do not belong to the visited Graph, it throws an IllegalArgumentException.
     *
     * @param v1 the parent vertex.
     * @param v2 the child vertex
     * 
     * @throws IllegalArgumentException if v1 or v2 do not belong to the graph.
     *
     * @return the weight of the edge (v1,v2).
     */
	public double getEdgeWeight(Vertex v1, Vertex v2) throws IllegalArgumentException;
	
}

