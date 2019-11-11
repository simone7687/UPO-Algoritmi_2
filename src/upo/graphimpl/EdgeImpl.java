package upo.graphimpl;

import java.util.HashSet;
import java.util.Set;

import upo.graph.DirectedEdge;
import upo.graph.Edge;
import upo.graph.Graph;
import upo.graph.Vertex;

public class EdgeImpl implements DirectedEdge
{
    private double edgeWeight;
    private Vertex sourceVertex;
    private Vertex targetVertex;
    private Graph graph;

    
    //Constructor that initializes vertices, adds the vertices and sets the edge weight value
    public EdgeImpl(Vertex sourceVertex, Vertex targetVertex, double edgeWeight, Graph graph)
    {
    	this.sourceVertex = sourceVertex;
    	this.targetVertex = targetVertex;
    	this.edgeWeight = edgeWeight;
    	this.graph = graph;
    }
    
    //Constructor that initializes vertices, adds the vertices
    public EdgeImpl(Vertex sourceVertex, Vertex targetVertex, Graph graph)
    {
    	this.sourceVertex = sourceVertex;
    	this.targetVertex = targetVertex;
    	this.graph = graph;
    }
    
    public void setEdgeWeight(double edgeWeight)
    {
    	this.edgeWeight = edgeWeight;
    }

	/**
     * Returns the weight assigned to the edge. Unweighted edges return 1.0, allowing weighted-graph algorithms to apply to them when
     * meaningful.
     *
     * @return edge weight
     */
	@Override
	public double getEdgeWeight() 
	{
		return edgeWeight;
	}

    /**
     * Returns a set containing the two vertices to which the Edge is incident
     *
     * @return a set containing the two vertices to which the Edge is incident
     */
	@Override
	public Set<Vertex> getVertices() 
	{
		Set<Vertex> vertices = new HashSet<Vertex>();
		vertices.add(sourceVertex);
		vertices.add(targetVertex);
		return vertices;
	}

	@Override
	public Graph getGraph() 
	{
		return graph;
	}

	/**
     * Retrieves the source of this edge.
     *
     * @return source of this edge
     */
	@Override
	public Vertex getSource() 
	{
		return sourceVertex;
	}

	/**
     * Retrieves the target of this edge. 
     *
     * @return target of this edge
     */
	@Override
	public Vertex getTarget() 
	{
		return targetVertex;
	}

}
