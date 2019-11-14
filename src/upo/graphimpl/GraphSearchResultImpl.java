package upo.graphimpl;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import upo.graph.Edge;
import upo.graph.Graph;
import upo.graph.GraphSearchResult;
import upo.graph.SearchType;
import upo.graph.Vertex;

public class GraphSearchResultImpl implements GraphSearchResult
{
	private SearchType type;
	private Vertex source;
	private LinkedHashMap<Vertex, LinkedList<Vertex>> Dag;
	private Graph graph;

	public GraphSearchResultImpl(SearchType type, Vertex source, Graph graph)
	{
		this.type = type;
		this.source = source;
		this.graph = graph;
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
	public Edge addEdge(Vertex sourceVertex, Vertex targetVertex) 
	{
		//If any of the specified vertex are null, throws NullPointerException
		if (sourceVertex == null || targetVertex == null)
			throw new NullPointerException("One or more given parameter is null.");
		
		//If the graph doesn't contain both the specified vertex, throws IllegalArgumentException
		if (!containsLeaves(sourceVertex) || !containsLeaves(targetVertex))
			throw new IllegalArgumentException("One or both vertices are not contained in the graph.");
		
		//Checks if the list doesn't contain the value already, creates an edge, adds it to the edges set and returns it
		if (containsLeaves(sourceVertex))
		{
			if (!ListStructuresFunctions.adjListContains(ListStructuresFunctions.getAdjListIfExists(sourceVertex, Dag), targetVertex))
				Dag.get(ListStructuresFunctions.getKeyAsVertex(sourceVertex, Dag)).add(targetVertex);
			
			return new EdgeImpl(sourceVertex, targetVertex, this);
		}
				
		return null;
	}

	// TODO: java doc
	public boolean containsEdge(Vertex sourceVertex, Vertex targetVertex) 
	{
		//Returns false if any of the specified vertex does not exist in the current graph
		if (!containsLeaves(sourceVertex) || !containsLeaves(targetVertex))
			return false;
		
		//If the graph contains an edge from source to target, returns true. Does not consider edge weight.		
		LinkedList<Vertex> vertices = ListStructuresFunctions.getAdjListIfExists(sourceVertex, Dag);
		if (vertices != null && vertices.size() > 0)
			return ListStructuresFunctions.adjListContains(vertices, targetVertex);
		
		return false;
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
		
		if(containsEdge(source, v))
			return 1;
		return distance(source, v, 1);
	}
	// TODO: java doc
	private int distance(Vertex current, Vertex find, int currentDistance)
	{
		currentDistance++;
		List<Vertex> neighbors = Dag.get(current);
		for(Vertex neighbor : neighbors)
		{
			if(containsEdge(neighbor, find))
				return currentDistance;
			int ren = distance(neighbor, find, currentDistance);
			if(ren != 0)
				return ren;
		}
		return 0;
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

		//TODO: return Restituisce il tempo di visita (dove la prima volta è 1) in cui il vertice di destinazione v è stato scoperto nella visita corrente (ovvero, quando v è diventato grigio).

		return -1;
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
		
		//TODO: Restituisce il tempo di visita (dove la prima volta è 1) in cui il vertice di destinazione v è stato chiuso nella visita corrente (cioè quando v è diventato nero).
		
		return -1;
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

		if(containsEdge(v1, v2))
		{
			//TODO: non sono sicuro che il peso sia uguale sia per graph che per Dag
			return graph.getEdgeWeight(v1, v2);
		}
		else
		{
			throw new IllegalArgumentException("Does not exist the edge between v1 and v2");
		}
	}
}