/**
 * 
 */
package upo.graphimpl;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import upo.graph.Edge;
import upo.graph.Graph;
import upo.graph.GraphSearchResult;
import upo.graph.SearchType;
import upo.graph.Vertex;

/**
 * @author Luca Piovesan
 *
 */
public class DirectedGraphAdjMatr implements Graph 
{
	private int DEFAULT_CAP = 8;
	private int[][] adjMatrix;
	private int numVertices; //number of vertex into the graph
	private HashMap<Vertex,Integer> VMap;
	private HashMap<Integer,Vertex> VMapRev;
	private boolean isDirect = true;
	
	
	public DirectedGraphAdjMatr()
	{
		numVertices = 0;
		VMap = new HashMap<>();
		VMapRev = new HashMap<>();
		adjMatrix = new int[DEFAULT_CAP][DEFAULT_CAP];
		
		for (int i = 0; i < adjMatrix.length; i++) // number of rows
			for(int j = 0; j < adjMatrix[i].length; j++) // number of columns
				adjMatrix[i][j] = 0;
	}

	@Override
	public Iterator<Vertex> iterator() 
	{
		return VMap.keySet().iterator();	
	}
	
	private void createExtendedGraph()
	{
		int[][] tempMatrix = new int[DEFAULT_CAP*DEFAULT_CAP][DEFAULT_CAP*DEFAULT_CAP];
		
		for (int i = 0; i < tempMatrix.length; i++) // number of rows
		{
			for(int j = 0; j < tempMatrix[i].length; j++) // number of columns
			{
				if(i < DEFAULT_CAP && j < DEFAULT_CAP)
				{
					tempMatrix[i][j] = adjMatrix[i][j];
				}
				else
					tempMatrix[i][j] = 0;
			}
		}
		
		adjMatrix = tempMatrix;
		DEFAULT_CAP = DEFAULT_CAP*DEFAULT_CAP;	
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
	public boolean addVertex(Vertex v) 
	{
		if(v == null)
			throw new NullPointerException("The given parameter is null");
		
		if(numVertices >= DEFAULT_CAP)
			createExtendedGraph();
		
		if(this.containsVertex(v))
			return false;
		else
		{
			VMap.put(v,numVertices);
			VMapRev.put(numVertices, v);
			numVertices++;
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
	public boolean containsVertex(Vertex v) 
	{
		if(v == null)
			return false;
		
		return VMap.keySet().stream().anyMatch(x -> x.getLabel().equals(v.getLabel()));
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
	public boolean removeVertex(Vertex v) 
	{
		if(!containsVertex(v))
			return false;
		
		int vertex = VMap.get(v);
		
		for(int i = 0; i < numVertices; i++)
		{
			adjMatrix[vertex][i] = 0;
			adjMatrix[i][vertex] = 0;
			
			VMap.remove(v);
			VMapRev.remove(vertex);
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
	public Set<Vertex> vertexSet() 
	{
		Set<Vertex> vertices = new HashSet<Vertex>();
		
		for (Vertex key : VMap.keySet())
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
	public Edge addEdge(Vertex sourceVertex, Vertex targetVertex) 
	{
		if(sourceVertex == null || targetVertex == null)
			throw new NullPointerException("One or more given parameter is null.");
		
		if(!containsVertex(sourceVertex) || !containsVertex(targetVertex))
			throw new IllegalArgumentException("One or both vertices are not contained in the graph.");
		
		if(containsVertex(sourceVertex) && containsVertex(targetVertex))
		{
			int v1 = VMap.get(sourceVertex);
			int v2 = VMap.get(targetVertex);
			adjMatrix[v1][v2] = 1;
			adjMatrix[v2][v1] = 1;
			
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
	public boolean containsEdge(Vertex sourceVertex, Vertex targetVertex) 
	{
		if(VMap.containsKey(sourceVertex) && VMap.containsKey(targetVertex)) 
		{
			if(adjMatrix[VMap.get(sourceVertex)][VMap.get(targetVertex)] == 1)
			{
				if(adjMatrix[VMap.get(targetVertex)][VMap.get(sourceVertex)] == 1)
				{
					return true;
				}
			}	
		}
		
		return false;
	}

	/**
	 * Returns a set of the vertices contained in this graph.
	 *
	 *
	 * @return a set view of the vertices contained in this graph.
	 */
	@Override
	public Set<Edge> edgeSet() 
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see upo.graph.Graph#degreeOf(upo.graph.Vertex)
	 */
	@Override
	public int degreeOf(Vertex vertex) 
	{
		return inDegreeOf(vertex) + outDegreeOf(vertex);
	}

	/* (non-Javadoc)
	 * @see upo.graph.Graph#inDegreeOf(upo.graph.Vertex)
	 */
	@Override
	public int inDegreeOf(Vertex vertex) 
	{ 
		int tempMatr[][] = adjMatrix.clone();
		int k = VMap.get(VMap.keySet().stream().filter(x -> x.getLabel().equals(vertex.getLabel())).findFirst().orElse(null));
		int d = 0;
		
		for (int i = 0; i < adjMatrix.length; i++)
		{
			if(tempMatr[i][k] == 1)
				d++;
			return d;
		}
		
		return 0;
	}

	/* (non-Javadoc)
	 * @see upo.graph.Graph#outDegreeOf(upo.graph.Vertex)
	 */
	@Override
	public int outDegreeOf(Vertex vertex) 
	{
		int tempMatr[][] = adjMatrix.clone();
		int k = VMap.get(VMap.keySet().stream().filter(x -> x.getLabel().equals(vertex.getLabel())).findFirst().orElse(null));
		int d = 0;
		
		for (int i = 0; i < adjMatrix.length; i++)
		{
			if(tempMatr[k][i] == 1)
				d++;
			return d;
		}
		
		return 0;
	}

	/* (non-Javadoc)
	 * @see upo.graph.Graph#isDirected()
	 */
	@Override
	public boolean isDirected() 
	{
		return isDirect;		
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
	public Edge removeEdge(Vertex sourceVertex, Vertex targetVertex) 
	{	
		if(!VMap.containsKey(sourceVertex) || !VMap.containsKey(targetVertex))
			throw new IllegalArgumentException("One or both given vertices are not contained in the graph.");
		
		int v1 = VMap.get(sourceVertex);
		int v2 = VMap.get(targetVertex);
		adjMatrix[v1][v2] = 0;
		adjMatrix[v2][v1] = 0;
		
		return null;
	}

	/* (non-Javadoc)
	 * @see upo.graph.Graph#getEdgeWeight(upo.graph.Vertex, upo.graph.Vertex)
	 */
	@Override
	public double getEdgeWeight(Vertex sourceVertex, Vertex targetVertex) 
	{
		return 0;
		
	}

	/* (non-Javadoc)
	 * @see upo.graph.Graph#setEdgeWeight(upo.graph.Vertex, upo.graph.Vertex, double)
	 */
	@Override
	public void setEdgeWeight(Vertex sourceVertex, Vertex targetVertex, double weight) 
	{
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see upo.graph.Graph#visit(upo.graph.SearchType)
	 */
	@Override
	public GraphSearchResult visit(SearchType type) throws UnsupportedOperationException 
	{
		GraphSearchResultImpl res = new GraphSearchResultImpl(type,new VertexImpl((String)VMap.keySet().toArray()[0]),this);
		
		switch(type) {
		case BFS:
			BFS(new VertexImpl((String)VMap.keySet().toArray()[0]), res);
			break;
		case DFS_TOT:
			DFS_tot(res);
			break;
		default:
				return null;
		}
		
		return res;
	}
	
	//TODO: CONTROLLARE PERCHE' """"QUALCUNO"""" L'HA COPIATO
	public boolean searchCycle(Vertex v, GraphSearchResultImpl res) {
		int tempMatrix[][] = adjMatrix;
		res.setColor(v, Colors.GREY);
		
		int x = VMap.get(v);
		for(int i = 0; i<adjMatrix.length;i++) {
			if(tempMatrix[x][i] == 1) {
				Vertex u = new VertexImpl((String)VMap.keySet().toArray()[0]);
				
				if(res.getColor(u) == Colors.WHITE) {
					res.setParent(u, v);
					if(searchCycle(u,res))
						return true;
				} else if(u != res.getParentOf(v))
					return true;
				res.setColor(v, Colors.BLACK);
			}
		}
		return false;
	}
	
	public void BFS(Vertex v, GraphSearchResultImpl res)
	{
		Queue<Vertex> D = new LinkedList<Vertex>();
		res.setColor(v, Colors.GREY);
		res.setDistance(v, 0.0);
		res.setStartTime(v);
		D.add(v);
		int tempMatr[][] = adjMatrix;
		
		while(!D.isEmpty()) {
			Vertex u = D.peek();
			int d = VMap.get(VMap.keySet().stream().filter(x -> x.getLabel().equals(u.getLabel())).findFirst().orElse(null));
			for(int i = 0; i< adjMatrix.length;i++) {
				Vertex p = new VertexImpl((String)VMap.keySet().toArray()[i]);
				if(res.getColor(v).equals(Colors.WHITE) && tempMatr[d][i] ==1) {
					res.setColor(v, Colors.GREY);
					res.setDistance(p,res.getDistance(u)+1);
					res.setStartTime(p);
					res.setParent(p, u);
					
				}
				
			}
			
			res.setEndTime(u);
			res.setColor(u, Colors.BLACK);
			D.poll();
		}
		
	}

	public void DFS(Vertex v, GraphSearchResultImpl res) {
		int tempMatrix[][] = adjMatrix;
		res.setColor(v, Colors.GREY);
		res.setStartTime(v);
		
		int x = VMap.get(v);
		for(int i = 0; i < adjMatrix.length;i++) {
			Vertex p = new VertexImpl((String)VMap.keySet().toArray()[i]);
			if(res.getColor(v).equals(Colors.WHITE) && tempMatrix[x][i] ==1) {
				res.setParent(p, v);
				DFS(p,res);
			}
			
		}
		
		res.setColor(v, Colors.BLACK);
		res.setEndTime(v);
	}
	
	public void DFS_tot(GraphSearchResultImpl res)
	{
		for(int i = 0; i < adjMatrix.length;i++) {
			Vertex v = new VertexImpl((String)VMap.keySet().toArray()[i]);
			if(res.getColor(v) == Colors.WHITE)
			{
				DFS(v,res);
			}
		}
	}
	
	public void DFS_topologica(GraphSearchResultImpl grafo, Vertex v, Vertex[] sort, int[] temp) 
	{
		int tempMatrix[][] = adjMatrix;
		int x = VMap.get(v);
		grafo.setColor(v, Colors.GREY);
		grafo.setStartTime(v);
		for(int i = 0; i<adjMatrix.length;i++) {
			Vertex u = new VertexImpl((String)VMap.keySet().toArray()[i]);
			if(grafo.getColor(u) == Colors.WHITE && tempMatrix[x][i] == 1) {
				grafo.setParent(u, v);
				DFS_topologica(grafo,u,sort,temp);
			}
		}
		
		grafo.setColor(v, Colors.BLACK);
		grafo.setEndTime(v);
		sort[temp[0]] = v;
		temp[0]--;
		
	}

	/* (non-Javadoc)
	 * @see upo.graph.Graph#isCyclic()
	 */
	@Override
	public boolean isCyclic() 
	{
		GraphSearchResultImpl res = new GraphSearchResultImpl(null, new VertexImpl("A"),this);
		for(int i = 0; i < adjMatrix.length;i++) {
			Vertex v = new VertexImpl((String)VMap.keySet().toArray()[i]);
			if(res.getColor(v) == Colors.WHITE)
			{
				return true;
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see upo.graph.Graph#isDAG()
	 */
	@Override
	public boolean isDAG() 
	{
		if(!isCyclic() && isDirect)
		{
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see upo.graph.Graph#topologicalSort()
	 */
	@Override
	public Vertex[] topologicalSort() 
	{
		if(!isDAG())
			return null;
		
		int[] temp = new int[1];
		temp[0] = adjMatrix.length -1;
		GraphSearchResultImpl grafo = new GraphSearchResultImpl(null, new VertexImpl("A"), this);
		Vertex[] sort = new Vertex[adjMatrix.length];
		for(int i = 0; i< adjMatrix.length; i++) 
		{
			Vertex v = new VertexImpl((String)VMap.keySet().toArray()[i]);
			if(grafo.getColor(v) == Colors.WHITE)
				DFS_topologica(grafo,v,sort,temp);
		}
		
		return sort;
	}

	/* (non-Javadoc)
	 * @see upo.graph.Graph#stronglyConnectedComponents()
	 */
	@Override
	public Collection<Collection<Vertex>> stronglyConnectedComponents() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see upo.graph.Graph#toStringSCC()
	 */
	@Override
	public String toStringSCC() 
	{
		// TODO Auto-generated method stub
		return null;
	}

}
