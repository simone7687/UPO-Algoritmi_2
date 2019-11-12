package upo.graphimpl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import upo.graph.Edge;
import upo.graph.Graph;
import upo.graph.GraphSearchResult;
import upo.graph.SearchType;
import upo.graph.Vertex;

public class GraphSearchResultImpl implements GraphSearchResult
{
	private SearchType type;
	private Vertex source;
	private HashMap<Vertex, LinkedList<Vertex>> Dag;
	private Graph graph;

	public GraphSearchResultImpl(SearchType type, Vertex source)
	{
		this.type = type;
		this.source = source;
	}

	// TODO: java doc
	public boolean addLeaves(Vertex v) 
	{
		// If the vertex is null, throws NullPointerException
		if (v == null)
			throw new NullPointerException("The given parameter is null");
		
		//Vertex exists
		if (containsLeaves(v))
			return false;
		else
		{
			//Adds the vertex and initializes an empty list
			Dag.put(v, new LinkedList<Vertex>());
			return true;
		}
	}

	// TODO: java doc
	private boolean keyExists(Vertex v)
	{
		return Dag.keySet().stream().anyMatch(x -> x.getLabel().equals(v.getLabel()));
	}

	// TODO: java doc
	public boolean containsLeaves(Vertex v) 
	{
		// If the vertex is null or does not exist in the graph, returns false
		if (v == null || !keyExists(v))
			return false;
		
		return true;
	}
	
	// TODO: java doc
	@Override
	public Iterator<Vertex> iterator() 
	{
		return null;		//TODO: forse graph.iterator();
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
			throw new IllegalArgumentException("The vertex is not contained in the visited graph");

		if (!containsLeaves(v))
			return Double.POSITIVE_INFINITY;
		
		if (this.getType() != SearchType.BFS && this.getType() != SearchType.DIJKSTRA)
			throw new UnsupportedOperationException("This type of visit is not supported.");	
		
		//TODO: return lunghezza / peso del percorso più breve tra il vertice sorgente Sorgente e v;
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
		if (source == v)
			return null;

		for (Vertex x : Dag.keySet())
		{
			if(Dag.get(x).getFirst() == v);
			{
				return x;
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
     * 
     * @throws IllegalArgumentException if v does not belong to the graph.
     *
     * @return the time in which v was discovered.
     */
	@Override
	public int getStartTime(Vertex v) throws IllegalArgumentException 
	{
		if (!graph.containsVertex(v))
			throw new IllegalArgumentException("The vertex is not contained in the visited graph");

		//TODO: Se v non è stato rilevato, questo metodo restituisce -1.
	
		//TODO: return Restituisce il tempo di visita (dove la prima volta è 1) in cui il vertice di destinazione v è stato scoperto nella visita corrente (ovvero, quando v è diventato grigio).
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
			throw new IllegalArgumentException("The vertex is not contained in the visited graph");
		
		//TODO: Se v non è stato rilevato, questo metodo restituisce -1.
		
		//TODO: Restituisce il tempo di visita (dove la prima volta è 1) in cui il vertice di destinazione v è stato chiuso nella visita corrente (cioè quando v è diventato nero).
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
			throw new IllegalArgumentException("The vertex is not contained in the visited graph");

		//TODO: Restituisce il peso del bordo tra v1 e v2 nel risultato della ricerca corrente, se esiste.

		//TODO: In caso contrario, restituisce un'eccezione IllegalArgumentException.
	}
}