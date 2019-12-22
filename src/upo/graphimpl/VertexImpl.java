package upo.graphimpl;

import upo.graph.Graph;
import upo.graph.Vertex;

import java.util.Iterator;

public class VertexImpl extends Vertex {
    private Graph graph;

    public VertexImpl(String label, Graph graph) {
        super(label);
        this.graph = graph;
    }

    @Override
    public Iterator<Vertex> iterator() {
        return graph.iterator();
    }

    @Override
    public Graph getGraph() {
        return graph;
    }

}
