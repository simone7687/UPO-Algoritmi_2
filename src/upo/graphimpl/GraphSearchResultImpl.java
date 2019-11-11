package upo.graphimpl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.stream.Collectors;
import upo.graphimpl.Colors;
import upo.graph.Graph;
import upo.graph.GraphSearchResult;
import upo.graph.SearchType;
import upo.graph.Vertex;

public class GraphSearchResultImpl implements GraphSearchResult
{
	private SearchType type;
	private Vertex source;
	private Colors[] color;
	private Vertex[] parent;
	private double[] dist;
	private int[] start;
	private int[] end;
	private int time;
	private HashMap<Vertex, Integer> Dag;
	private Graph graph;
	

	public GraphSearchResultImpl(SearchType type, Vertex source, Graph graph)
	{
		final int DIM = graph.edgeSet().size();
		this.type = type;
		this.source = source;
		this.dist = new double[DIM];
		this.color = new Colors[DIM];
		this.start = new int[DIM];
		this.end = new int[DIM];
		this.parent = new Vertex[DIM];
		this.time = 0;
		this.graph = graph;
		
		for(int i = 0; i < DIM; i++)
		{
			this.color[i] = Colors.WHITE;
			this.dist[i] = Double.POSITIVE_INFINITY;
			this.start[i] = Integer.MAX_VALUE;
			this.end[i] = Integer.MAX_VALUE;
		}		
	}
	
	@Override
	public Iterator<Vertex> iterator() 
	{
		return graph.iterator();
	}

	/**
     * Returns the type of the search (e.g., BFS) which generates this result
     *
     * @return a SearchType describing the search which generates this result
     */
	@Override
	public SearchType getType() 
	{	
		return type;
	}
	
	
	/**
     * Returns the source vertex S of the search which generates this result
     *
     *
     * @return the source Vertex
     */
	@Override
	public Vertex getSource() 
	{
		return source;
	}

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
	@Override
	public double getDistance(Vertex v) throws UnsupportedOperationException, IllegalArgumentException 
	{
		if (!graph.containsVertex(v))
			throw new IllegalArgumentException("The vertex is not contained in the current graph");
		
		if (this.getType() != SearchType.BFS && this.getType() != SearchType.DIJKSTRA)
			throw new UnsupportedOperationException("This type of visit is not supported.");	
		
		return dist[Dag.get(Dag.keySet().stream().filter(x -> x.getLabel().equals(v.getLabel())).findFirst().orElse(null))];
	}
	
	public void setDistance(Vertex v, double dist)
	{
		this.dist[Dag.get(Dag.keySet().stream().filter(x -> x.getLabel().equals(v.getLabel())).findFirst().orElse(null))] = dist;
	}


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
	@Override
	public Vertex getParentOf(Vertex v) throws IllegalArgumentException 
	{
		if (!graph.containsVertex(v))
			throw new IllegalArgumentException("The vertex is not contained in the current graph");

		return this.parent[Dag.get(Dag.keySet().stream().filter(x -> x.getLabel().equals(v.getLabel())).findFirst().orElse(null))];
	}
	
	public void setParent(Vertex parent, Vertex v)
	{
		this.parent[Dag.get(Dag.keySet().stream().filter(x -> x.getLabel().equals(v.getLabel())).findFirst().orElse(null))] = parent;
	}

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
	@Override
	public int getStartTime(Vertex v) throws IllegalArgumentException 
	{
		if (!graph.containsVertex(v))
			throw new IllegalArgumentException("The vertex is not contained in the current graph");

		return this.start[Dag.get(Dag.keySet().stream().filter(x -> x.getLabel().equals(v.getLabel())).findFirst().orElse(null))];
	}
	
	public void setStartTime(Vertex v) 
	{
		start[Dag.get(Dag.keySet().stream().filter(x -> x.getLabel().equals(v.getLabel())).findFirst().orElse(null))] = time++;
	}

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
	@Override
	public int getEndTime(Vertex v) throws IllegalArgumentException 
	{
		if (!graph.containsVertex(v))
			throw new IllegalArgumentException("The vertex is not contained in the current graph");
		
		return this.end[Dag.get(Dag.keySet().stream().filter(x -> x.getLabel().equals(v.getLabel())).findFirst().orElse(null))];
	}
	
	public void setEndTime(Vertex v) 
	{
		end[Dag.get(Dag.keySet().stream().filter(x -> x.getLabel().equals(v.getLabel())).findFirst().orElse(null))] = time++;
	}
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
	@Override
	public double getEdgeWeight(Vertex v1, Vertex v2) throws IllegalArgumentException 
	{
		if (!graph.containsVertex(v1) || !graph.containsVertex(v2))
			throw new IllegalArgumentException("The vertex is not contained in the current graph");

		return Graph.DEFAULT_EDGE_WEIGHT;
	}
	
	public Colors getColor(Vertex v) {
		return this.color[Dag.get(Dag.keySet().stream().filter(x -> x.getLabel().equals(v.getLabel())).findFirst().orElse(null))];
	}
	
	public void setColor(Vertex v, Colors color) {
		this.color[Dag.get(Dag.keySet().stream().filter(x -> x.getLabel().equals(v.getLabel())).findFirst().orElse(null))] = color;
	}

}
