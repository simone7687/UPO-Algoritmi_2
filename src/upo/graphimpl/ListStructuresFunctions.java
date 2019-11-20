package upo.graphimpl;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import upo.graph.Vertex;

public class ListStructuresFunctions
{
	/**
	 * Checks if the Vertex exists in the HashMap keys
	 * @param v
	 * 			The vertex to find in the HashMap
	 * @return
	 * 			true if contained in the HashMap otherwise false
	 */
	static boolean keyExists(Vertex v, LinkedHashMap<Vertex, LinkedList<Vertex>> graph)
	{
		return graph.keySet().stream().anyMatch(x -> x.getLabel().equals(v.getLabel()));
	}
	
	static Vertex getKeyAsVertex(Vertex v, LinkedHashMap<Vertex, LinkedList<Vertex>> graph)
	{
		return graph.keySet().stream().filter(x -> x.getLabel().equals(v.getLabel())).findFirst().orElse(null);
	}
	
	/**
	 * Gets the AdjList associated to the given key
	 * @param v The Hashmap key
	 * @return LinkedList<Vertex>
	 */
	static LinkedList<Vertex> getAdjListIfExists(Vertex v, LinkedHashMap<Vertex, LinkedList<Vertex>> graph)
	{
		Vertex vc = graph.keySet().stream().filter(x -> x.getLabel().equals(v.getLabel())).findFirst().orElse(null);
		if (graph.containsKey(vc))
			return graph.get(vc);
		
		return null;
	}
	
	/**
	 * Checks if a given adjList contains the specified vertex
	 * @param sourceVertex
	 * @param targetVertex
	 * @return true if the AdjList contains the targetVertex otherwise returns false
	 */
	static boolean adjListContains(LinkedList<Vertex> adjList, Vertex targetVertex)
	{
		if (adjList == null || targetVertex == null)
			return false;
		
		return adjList.stream().anyMatch(x -> x.getLabel().equals(targetVertex.getLabel()));
	}

	static int getVerticesNumber (LinkedHashMap<Vertex, LinkedList<Vertex>> graph)
	{
		int count = 0;
		//TODO: ...
		return count;
	}
}