/**
 * 
 */
package upo.graphimpl;

import java.util.Collection;
import java.util.Iterator;
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
	private final int maxNVertex = 100;
	
	private Vertex graph[][];
	private Vertex visitedNodes[][];
	private final boolean isDirected = true;

	/**
	 * Initializes the graph
	 *
	 * @param isDirected Indicates if the graph is directed
	 */
	public DirectedGraphAdjMatr()
	{
		graph = new Vertex[maxNVertex][maxNVertex];
		setNotVisitedNodes();
	}
		
	//TODO: javadoc: Initializes the visited nodes to 'not visited'
	private void setNotVisitedNodes()
	{
		// TODO
	}
	//TODO: javadoc
	private void setColorVertextVisitedNodes(Vertex v, Colors color)
	{
		// TODO
	}

	//TODO: javadoc
	private int findVertex(Vertex v)
	{
		// If the vertex is null or does not exist in the graph, returns false
		if (v == null)
			return 0;
		for (int i=0; i<maxNVertex; i++)
		{
			if (graph[i][0] == v)
			{
				return i+1;
			}
		}
		return 0;
	}

	@Override
	public Iterator<Vertex> iterator()
	{
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
	public boolean addVertex(Vertex v)
	{
		// If the vertex is null, throws NullPointerException
		if (v == null)
			throw new NullPointerException("The given parameter is null");
		
		//Vertex exists
		if (containsVertex(v))
			return false;
		else
		{
			//Adds the vertex and initializes an empty list
			for (int i=0; i<maxNVertex; i++)
			{
				if (graph[i][0] == null)
				{
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
	public boolean containsVertex(Vertex v)
	{
		// If the vertex is null or does not exist in the graph, returns false
		if (v == null)
			return false;
		for (int i=0; i<maxNVertex; i++)
		{
			if (graph[i][0] == v)
			{
				return true;
			}
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
	public boolean removeVertex(Vertex v)
	{
		if (v == null || !containsVertex(v))
			return false;

		for (int k=0; k<maxNVertex; k++)
			graph[findVertex(v)][k] = null;
		return true;
	}

	@Override
	public Set<Vertex> vertexSet()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Edge addEdge(Vertex sourceVertex, Vertex targetVertex)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean containsEdge(Vertex sourceVertex, Vertex targetVertex)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<Edge> edgeSet()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int degreeOf(Vertex vertex)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int inDegreeOf(Vertex vertex)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int outDegreeOf(Vertex vertex)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isDirected()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Edge removeEdge(Vertex sourceVertex, Vertex targetVertex)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getEdgeWeight(Vertex sourceVertex, Vertex targetVertex)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setEdgeWeight(Vertex sourceVertex, Vertex targetVertex, double weight)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public GraphSearchResult visit(SearchType type) throws UnsupportedOperationException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isCyclic()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDAG()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Vertex[] topologicalSort()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Collection<Vertex>> stronglyConnectedComponents()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toStringSCC()
	{
		// TODO Auto-generated method stub
		return null;
	}
}