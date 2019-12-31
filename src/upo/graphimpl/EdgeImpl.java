package upo.graphimpl;

import upo.graph.DirectedEdge;
import upo.graph.Graph;
import upo.graph.GraphSearchResult;
import upo.graph.Vertex;

import java.util.HashSet;
import java.util.Set;

public class EdgeImpl implements DirectedEdge {
    private double edgeWeight = 0;
    private Vertex sourceVertex;
    private Vertex targetVertex;
    private Graph graph;
    GraphSearchResult tree;

    /**
     * Constructor that initializes vertices, adds the vertices and sets the edge weight value
     *
     * @param sourceVertex
     * @param targetVertex
     * @param edgeWeight
     * @param graph
     */
    public EdgeImpl(Vertex sourceVertex, Vertex targetVertex, double edgeWeight, Graph graph) {
        this.sourceVertex = sourceVertex;
        this.targetVertex = targetVertex;
        this.edgeWeight = edgeWeight;
        this.graph = graph;
    }

    /**
     * Constructor that initializes vertices, adds the vertices
     *
     * @param sourceVertex
     * @param targetVertex
     * @param graph
     */
    public EdgeImpl(Vertex sourceVertex, Vertex targetVertex, Graph graph) {
        this.sourceVertex = sourceVertex;
        this.targetVertex = targetVertex;
        this.graph = graph;
    }

    /**
     * Constructor that initializes vertices, adds the vertices and sets the edge weight value
     *
     * @param sourceVertex
     * @param targetVertex
     * @param edgeWeight
     * @param tree
     */
    public EdgeImpl(Vertex sourceVertex, Vertex targetVertex, double edgeWeight, GraphSearchResult tree) {
        this.sourceVertex = sourceVertex;
        this.targetVertex = targetVertex;
        this.edgeWeight = edgeWeight;
        this.tree = tree;
    }

    /**
     * Constructor that initializes vertices, adds the vertices
     *
     * @param sourceVertex
     * @param targetVertex
     * @param tree
     */
    public EdgeImpl(Vertex sourceVertex, Vertex targetVertex, GraphSearchResult tree) {
        this.sourceVertex = sourceVertex;
        this.targetVertex = targetVertex;
        this.tree = tree;
    }

    //TODO:javadoc
    public void setEdgeWeight(double edgeWeight) {
        this.edgeWeight = edgeWeight;
    }

    /**
     * Returns the weight assigned to the edge. Unweighted edges return 1.0, allowing weighted-graph algorithms to apply to them when
     * meaningful.
     *
     * @return edge weight
     */
    @Override
    public double getEdgeWeight() {
        return edgeWeight;
    }

    /**
     * Returns a set containing the two vertices to which the Edge is incident
     *
     * @return a set containing the two vertices to which the Edge is incident
     */
    @Override
    public Set<Vertex> getVertices() {
        Set<Vertex> vertices = new HashSet<Vertex>();
        vertices.add(sourceVertex);
        vertices.add(targetVertex);
        return vertices;
    }

    //TODO: java dock
    @Override
    public Graph getGraph() {
        return graph;
    }

    /**
     * Retrieves the source of this edge.
     *
     * @return source of this edge
     */
    @Override
    public Vertex getSource() {
        return sourceVertex;
    }

    /**
     * Retrieves the target of this edge.
     *
     * @return target of this edge
     */
    @Override
    public Vertex getTarget() {
        return targetVertex;
    }

}
