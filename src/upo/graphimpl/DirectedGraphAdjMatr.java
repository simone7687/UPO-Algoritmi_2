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
		graph = new Vertex[100][];
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

	@Override
	public Iterator<Vertex> iterator()
	{
		return graph[0][0].iterator();
	}

	@Override
	public boolean addVertex(Vertex v)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsVertex(Vertex v)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeVertex(Vertex v)
	{
		// TODO Auto-generated method stub
		return false;
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