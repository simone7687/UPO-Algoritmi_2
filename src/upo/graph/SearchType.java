package upo.graph;

public enum SearchType {
	/**
	 * Breadth First Search (single visit).
	 */
	BFS,
	/**
	 * Deep First Search (single visit).
	 */
	DFS,
	/**
	 * Deep First Search (complete).
	 */
	DFS_TOT,
	/**
	 * Dijkstra's algorithm result.
	 */
	DIJKSTRA;
}