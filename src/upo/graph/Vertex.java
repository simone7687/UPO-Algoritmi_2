package upo.graph;

public abstract class Vertex implements Iterable<Vertex> {
	
	/**
	 * The label of this vertex
	 */
	private final String label;
	
	/**
	 * Constructs a vertex with the given label.
	 *  
	 * @param label the name of the vertex.
	 */
	public Vertex(String label) {
		this.label = label;
	}
	
	/**
	 * Returns the label of this vertex.
	 * 
	 * @return a String which is the label of this vertex.
	 */
	public String getLabel() {
		return this.label;
	}
	
	/**
	 * Returns a string representation of this vertex. 
	 * 
	 * @return a string representation of this vertex
	 */
	@Override
    public String toString() {
		return this.label;
	}
	
	/**
	 * Returns the graph this vertex belongs to
	 * 
	 * @return the graph this vertex belongs to
	 */
	public abstract Graph getGraph();
	
	/**
	 * Compares this vertex to the specified object. The result is true if and only if 
	 * the argument is not null and is a Vertex object with the same label.
	 * 
	 * @param o The object to compare this Vertex against
	 * 
	 * @return true if the given object represents a vertex with the same label of this vertex, false otherwise
	 */
	@Override
	public boolean equals(Object o) {
		return (o != null) && (o instanceof Vertex) && ((Vertex) o).getLabel().equals(this.getLabel()); 
	}


}
