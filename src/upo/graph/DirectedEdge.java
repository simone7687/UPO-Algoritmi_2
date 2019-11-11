package upo.graph;

public interface DirectedEdge extends Edge {
	/**
     * Retrieves the source of this edge.
     *
     * @return source of this edge
     */
	Vertex getSource();
	
	/**
     * Retrieves the target of this edge. 
     *
     * @return target of this edge
     */
	Vertex getTarget();
	
}
