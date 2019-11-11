package upo.graph;

import java.util.Set;

public interface Edge {
	
	/**
     * Returns the weight assigned to the edge. Unweighted edges return 1.0, allowing weighted-graph algorithms to apply to them when
     * meaningful.
     *
     * @return edge weight
     */
    double getEdgeWeight();
    
    /**
     * Returns a set containing the two vertices to which the Edge is incident
     *
     * @return a set containing the two vertices to which the Edge is incident
     */
    Set<Vertex> getVertices();
    
    Graph getGraph();
    
	@Override
    public String toString();

}
