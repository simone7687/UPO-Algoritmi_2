package upo.graphtests;

import org.junit.Test;
import upo.graph.GraphSearchResult;
import upo.graphimpl.DirectedWeightedGraphAdjMatr;
import upo.graphimpl.VertexImpl;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static upo.graph.SearchType.DIJKSTRA;

public class DirectedWeightedGraphAdjMatrTest {
    private DirectedWeightedGraphAdjMatr graph;

    public DirectedWeightedGraphAdjMatrTest() {
        graph = new DirectedWeightedGraphAdjMatr();
    }

    @Test
    public void addVertexTest() {
        //Tests exception
        assertThrows(NullPointerException.class, () -> {
            graph.addVertex(null);
        });

        assertEquals(graph.vertexSet().size(), 0);

        //Add 3 vertices
        VertexImpl v0 = new VertexImpl("A", graph);
        boolean v0res = graph.addVertex(v0);
        assertTrue(v0res);

        VertexImpl v1 = new VertexImpl("B", graph);
        assertTrue(graph.addVertex(v1));

        VertexImpl v2 = new VertexImpl("C", graph);
        assertTrue(graph.addVertex(v2));

        boolean v3res = graph.addVertex(v0);
        assertFalse(v3res);

        VertexImpl v4 = new VertexImpl("A", graph);
        assertFalse(graph.addVertex(v4));

        //Confirm that vertices have been added correctly
        assertEquals(graph.vertexSet().size(), 3);
    }

    @Test
    public void containsVertexTest() {
        VertexImpl v2 = new VertexImpl("C", graph);
        boolean v2res = graph.addVertex(v2);
        assertTrue(v2res);

        assertFalse(graph.containsVertex(null));
        assertTrue(graph.containsVertex(v2));
    }

    @Test
    public void removeVertexTest() {
        VertexImpl v0 = new VertexImpl("A", graph);
        graph.addVertex(v0);

        VertexImpl v1 = new VertexImpl("B", graph);
        graph.addVertex(v1);

        VertexImpl v2 = new VertexImpl("C", graph);
        graph.addVertex(v2);

        assertEquals(3, graph.vertexSet().size());
        assertFalse(graph.removeVertex(null));

        boolean vertexnotexists = graph.removeVertex(new VertexImpl("F", graph));
        assertFalse(vertexnotexists);

        assertEquals(3, graph.vertexSet().size());
        assertTrue(graph.removeVertex(v0));
        assertEquals(2, graph.vertexSet().size());
    }

    @Test
    public void vertexSetTest() {
        graph.addVertex(new VertexImpl("A", graph));
        graph.addVertex(new VertexImpl("B", graph));
        graph.addVertex(new VertexImpl("C", graph));

        assertEquals(3, graph.vertexSet().size());
    }

    @Test
    public void addEdgeTest() {
        VertexImpl v0 = new VertexImpl("A", graph);
        graph.addVertex(v0);

        VertexImpl v1 = new VertexImpl("B", graph);
        graph.addVertex(v1);

        VertexImpl v2 = new VertexImpl("C", graph);
        graph.addVertex(v2);

        assertThrows(NullPointerException.class, () -> {
            graph.addEdge(null, null);
        });

        assertThrows(NullPointerException.class, () -> {
            graph.addEdge(v0, null);
        });

        assertThrows(NullPointerException.class, () -> {
            graph.addEdge(null, v0);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            graph.addEdge(new VertexImpl("D", graph), new VertexImpl("F", graph));
        });

        assertThrows(IllegalArgumentException.class, () -> {
            graph.addEdge(v0, new VertexImpl("F", graph));
        });

        assertThrows(IllegalArgumentException.class, () -> {
            graph.addEdge(new VertexImpl("F", graph), v0);
        });

        assertNotNull(graph.addEdge(v0, v1));
        assertEquals(1, graph.edgeSet().size());
    }

    @Test
    public void containsEdgeTest() {
        VertexImpl v0 = new VertexImpl("A", graph);
        graph.addVertex(v0);

        VertexImpl v1 = new VertexImpl("B", graph);
        graph.addVertex(v1);

        graph.addEdge(v0, v1);
        assertFalse(graph.containsEdge(new VertexImpl("F", graph), v0));
        assertFalse(graph.containsEdge(v0, new VertexImpl("F", graph)));

        assertTrue(graph.containsEdge(v0, v1));
        assertFalse(graph.containsEdge(v1, v0));
    }

    @Test
    public void edgeSetTest() {
        VertexImpl v0 = new VertexImpl("A", graph);
        graph.addVertex(v0);

        VertexImpl v1 = new VertexImpl("B", graph);
        graph.addVertex(v1);

        VertexImpl v2 = new VertexImpl("C", graph);
        graph.addVertex(v2);

        VertexImpl v3 = new VertexImpl("D", graph);
        graph.addVertex(v3);

        assertEquals(0, graph.edgeSet().size());

        graph.addEdge(v0, v1);
        graph.addEdge(v0, v2);
        graph.addEdge(v0, v3);

        assertEquals(3, graph.edgeSet().size());
    }

    @Test
    public void degreeOfTest() {
        VertexImpl v0 = new VertexImpl("A", graph);
        graph.addVertex(v0);

        VertexImpl v1 = new VertexImpl("B", graph);
        graph.addVertex(v1);

        VertexImpl v2 = new VertexImpl("C", graph);
        graph.addVertex(v2);

        assertEquals(0, graph.degreeOf(v0));

        assertNotNull(graph.addEdge(v0, v1));
        assertNotNull(graph.addEdge(v0, v2));

        assertEquals(2, graph.degreeOf(v0));
    }

    @Test
    public void inDegreeOfTest() {
        VertexImpl v0 = new VertexImpl("A", graph);
        graph.addVertex(v0);

        VertexImpl v1 = new VertexImpl("B", graph);
        graph.addVertex(v1);

        VertexImpl v2 = new VertexImpl("C", graph);
        graph.addVertex(v2);

        assertEquals(0, graph.degreeOf(v0));

        assertNotNull(graph.addEdge(v0, v1));
        assertNotNull(graph.addEdge(v1, v0));
        assertNotNull(graph.addEdge(v0, v2));

        assertEquals(1, graph.inDegreeOf(v0));
    }

    @Test
    public void outDegreeOf() {
        VertexImpl v0 = new VertexImpl("A", graph);
        graph.addVertex(v0);

        VertexImpl v1 = new VertexImpl("B", graph);
        graph.addVertex(v1);

        VertexImpl v2 = new VertexImpl("C", graph);
        graph.addVertex(v2);

        assertEquals(0, graph.outDegreeOf(v0));

        assertNotNull(graph.addEdge(v0, v1));
        assertNotNull(graph.addEdge(v0, v2));

        assertEquals(2, graph.outDegreeOf(v0));
    }

    @Test
    public void removeEdgeTest() {
        VertexImpl v0 = new VertexImpl("A", graph);
        graph.addVertex(v0);

        VertexImpl v1 = new VertexImpl("B", graph);
        graph.addVertex(v1);

        VertexImpl v2 = new VertexImpl("C", graph);
        graph.addVertex(v2);

        assertNotNull(graph.addEdge(v0, v1));
        assertNotNull(graph.addEdge(v0, v2));
        assertEquals(2, graph.edgeSet().size());

        assertNull(graph.removeEdge(null, null));
        assertNull(graph.removeEdge(v0, null));
        assertNull(graph.removeEdge(null, v0));
        assertNull(graph.removeEdge(new VertexImpl("F", graph), v0));
        assertNull(graph.removeEdge(v0, new VertexImpl("F", graph)));
    }

    @Test
    public void getEdgeWeightTest() {
        VertexImpl v0 = new VertexImpl("A", graph);
        graph.addVertex(v0);

        VertexImpl v1 = new VertexImpl("B", graph);
        graph.addVertex(v1);

        assertNotNull(graph.addEdge(v0, v1));

        assertEquals(1.0, graph.getEdgeWeight(v0, v1), 0);
    }

    @Test
    public void setEdgeWeightTest() {
        VertexImpl v0 = new VertexImpl("A", graph);
        graph.addVertex(v0);

        VertexImpl v1 = new VertexImpl("B", graph);
        graph.addVertex(v1);

        assertNotNull(graph.addEdge(v0, v1));

        graph.setEdgeWeight(v0, v1, 2.0);

        assertEquals(2.0, graph.getEdgeWeight(v0, v1), 0);
    }

    @Test
    public void visitTest() {
        VertexImpl v0 = new VertexImpl("A", graph);
        graph.addVertex(v0);

        VertexImpl v1 = new VertexImpl("B", graph);
        graph.addVertex(v1);

        VertexImpl v2 = new VertexImpl("C", graph);
        graph.addVertex(v2);

        VertexImpl v3 = new VertexImpl("D", graph);
        graph.addVertex(v3);

        VertexImpl v4 = new VertexImpl("F", graph);
        graph.addVertex(v4);

        VertexImpl v5 = new VertexImpl("G", graph);
        graph.addVertex(v5);

        VertexImpl v6 = new VertexImpl("H", graph);
        graph.addVertex(v6);

        assertNotNull(graph.addEdge(v0, v1, 2));
        assertNotNull(graph.addEdge(v0, v4, 6));
        assertNotNull(graph.addEdge(v1, v2, 1));
        assertNotNull(graph.addEdge(v2, v3, 1));
        assertNotNull(graph.addEdge(v2, v6, 3));
        assertNotNull(graph.addEdge(v4, v3, 7));
        assertNotNull(graph.addEdge(v4, v5, 3));
        assertNotNull(graph.addEdge(v5, v6, 3));

        GraphSearchResult treeDIJKSTRA = graph.visit(DIJKSTRA);
        assertEquals(4.0, treeDIJKSTRA.getDistance(v3), 0);
        assertEquals(6, treeDIJKSTRA.getStartTime(v5));
        assertEquals(1, treeDIJKSTRA.getEndTime(v5));
    }

    @Test
    public void isCyclicTest() {
        VertexImpl v0 = new VertexImpl("A", graph);
        graph.addVertex(v0);

        VertexImpl v1 = new VertexImpl("B", graph);
        graph.addVertex(v1);

        VertexImpl v2 = new VertexImpl("C", graph);
        graph.addVertex(v2);

        assertNotNull(graph.addEdge(v0, v1));
        assertNotNull(graph.addEdge(v1, v2));

        assertEquals(false, graph.isCyclic());

        assertNotNull(graph.addEdge(v2, v0));

        assertEquals(true, graph.isCyclic());
    }

    @Test
    public void isDAGTest() {
        VertexImpl v0 = new VertexImpl("A", graph);
        graph.addVertex(v0);

        VertexImpl v1 = new VertexImpl("B", graph);
        graph.addVertex(v1);

        VertexImpl v2 = new VertexImpl("C", graph);
        graph.addVertex(v2);

        VertexImpl v3 = new VertexImpl("D", graph);
        graph.addVertex(v3);

        assertNotNull(graph.addEdge(v0, v1));
        assertNotNull(graph.addEdge(v1, v2));
        assertNotNull(graph.addEdge(v2, v3));

        assertEquals(false, graph.isCyclic());

        assertNotNull(graph.addEdge(v2, v0));

        assertEquals(true, graph.isCyclic());
    }

    @Test
    public void topologicalSortTest() {
        VertexImpl v0 = new VertexImpl("A", graph);
        graph.addVertex(v0);

        VertexImpl v1 = new VertexImpl("B", graph);
        graph.addVertex(v1);

        VertexImpl v2 = new VertexImpl("C", graph);
        graph.addVertex(v2);

        VertexImpl v3 = new VertexImpl("D", graph);
        graph.addVertex(v3);

        VertexImpl v4 = new VertexImpl("E", graph);
        graph.addVertex(v4);

        VertexImpl v5 = new VertexImpl("F", graph);
        graph.addVertex(v5);

        assertNotNull(graph.addEdge(v0, v1));
        assertNotNull(graph.addEdge(v0, v2));
        assertNotNull(graph.addEdge(v2, v3));
        assertNotNull(graph.addEdge(v4, v0));
        assertNotNull(graph.addEdge(v0, v5));

        VertexImpl[] a = {v4, v0, v1, v2, v5, v3};

        assertArrayEquals(a, graph.topologicalSort());
    }

    @Test
    public void toStringSCCTest() {
        VertexImpl v0 = new VertexImpl("A", graph);
        graph.addVertex(v0);

        VertexImpl v1 = new VertexImpl("B", graph);
        graph.addVertex(v1);

        VertexImpl v2 = new VertexImpl("C", graph);
        graph.addVertex(v2);

        VertexImpl v3 = new VertexImpl("D", graph);
        graph.addVertex(v3);

        VertexImpl v4 = new VertexImpl("E", graph);
        graph.addVertex(v4);

        assertNotNull(graph.addEdge(v0, v1));
        assertNotNull(graph.addEdge(v1, v2));
        assertNotNull(graph.addEdge(v2, v0));

        assertNotNull(graph.addEdge(v1, v3));
        assertNotNull(graph.addEdge(v3, v4));
        assertNotNull(graph.addEdge(v3, v2));

        assertNotNull(graph.stronglyConnectedComponents());

        assertEquals("{{A,C,B},{D},{E}}", graph.toStringSCC());
    }

    @Test
    public void allToAllShortestPathsTest() {
        VertexImpl v0 = new VertexImpl("A", graph);
        graph.addVertex(v0);

        VertexImpl v1 = new VertexImpl("B", graph);
        graph.addVertex(v1);

        VertexImpl v2 = new VertexImpl("C", graph);
        graph.addVertex(v2);

        VertexImpl v3 = new VertexImpl("D", graph);
        graph.addVertex(v3);

        assertNotNull(graph.addEdge(v0, v2, -2));
        assertNotNull(graph.addEdge(v2, v3, 2));
        assertNotNull(graph.addEdge(v3, v1, -1));
        assertNotNull(graph.addEdge(v1, v0, 4));
        assertNotNull(graph.addEdge(v1, v2, 3));

        double[][] a = {
                {0, -1, -2, 0},
                {4, 0, 2, 4},
                {5, 1, 0, 2},
                {3, -1, 1, 0}
        };
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                assertEquals(a[i][j], graph.allToAllShortestPaths()[i][j], 0);
    }
}
