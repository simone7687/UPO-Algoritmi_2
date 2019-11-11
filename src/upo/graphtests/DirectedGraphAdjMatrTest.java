package upo.graphtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import upo.graphimpl.DirectedGraphAdjMatr;
import upo.graphimpl.VertexImpl;

class DirectedGraphAdjMatrTest {
	private DirectedGraphAdjMatr graphMatr;
	
	public DirectedGraphAdjMatrTest()
	{
		graphMatr = new DirectedGraphAdjMatr();
	}

	@Test
	void addVertexTest() 
	{
		//Tests exception
		assertThrows(NullPointerException.class, () -> {
			graphMatr.addVertex(null);
		});	
		
		assertEquals(0, graphMatr.vertexSet().size());
		
		assertTrue(graphMatr.addVertex(new VertexImpl("A")));
		assertFalse(graphMatr.addVertex(new VertexImpl("A")));
		assertTrue(graphMatr.addVertex(new VertexImpl("B")));
		assertTrue(graphMatr.addVertex(new VertexImpl("C")));
		
		assertEquals(3, graphMatr.vertexSet().size());
	}
		
	
	@Test
	public void containsVertexTest() 
	{
		assertTrue(graphMatr.addVertex(new VertexImpl("A")));
		assertFalse(graphMatr.addVertex(new VertexImpl("A")));
		assertTrue(graphMatr.addVertex(new VertexImpl("B")));
		assertTrue(graphMatr.addVertex(new VertexImpl("C")));
		
		assertTrue(graphMatr.containsVertex(new VertexImpl("A")));
		assertFalse(graphMatr.containsVertex(new VertexImpl("I")));
		assertTrue(graphMatr.containsVertex(new VertexImpl("B")));
		assertTrue(graphMatr.containsVertex(new VertexImpl("C")));
	}
	
	@Test
	public void removeVertexTest() 
	{
		VertexImpl v0 = new VertexImpl("A");
		graphMatr.addVertex(v0);
		
		VertexImpl v1 = new VertexImpl("B");
		graphMatr.addVertex(v1);
		
		VertexImpl v2 = new VertexImpl("C");
		graphMatr.addVertex(v2);
		
		assertEquals(3, graphMatr.vertexSet().size());
		
		graphMatr.removeVertex(v0);
		
		assertEquals(2, graphMatr.vertexSet().size());
	}
	
	@Test
	public void vertexSetTest() 
	{
		graphMatr.addVertex(new VertexImpl("A"));
		graphMatr.addVertex(new VertexImpl("B"));
		graphMatr.addVertex(new VertexImpl("C"));
		assertEquals(3, graphMatr.vertexSet().size());
	}
	
	@Test
	public void addEdgeTest() 
	{	
		VertexImpl v0 = new VertexImpl("A");
		graphMatr.addVertex(v0);
		
		VertexImpl v1 = new VertexImpl("B");
		graphMatr.addVertex(v1);
		
		VertexImpl v2 = new VertexImpl("C");
		graphMatr.addVertex(v2);
		
		assertThrows(NullPointerException.class, () -> {
			graphMatr.addEdge(null, null);
		});	
		
		assertThrows(NullPointerException.class, () -> {
			graphMatr.addEdge(v0, null);
		});	
		
		assertThrows(NullPointerException.class, () -> {
			graphMatr.addEdge(null, v0);
		});	
		
		assertThrows(IllegalArgumentException.class, () -> {
			graphMatr.addEdge(new VertexImpl("D"), new VertexImpl("F"));
		});	
		
		assertThrows(IllegalArgumentException.class, () -> {
			graphMatr.addEdge(v0, new VertexImpl("F"));
		});	
		
		assertThrows(IllegalArgumentException.class, () -> {
			graphMatr.addEdge(new VertexImpl("F"), v0);
		});	
		
		assertNotNull(graphMatr.addEdge(v0, v1));
	}
}
