# Algoritmi 2

## Compito pratico 1
- Implementazione di grafi diretti.
- Vengono fornite le interface Graph, Edge, Vertex, DirectedEdge.
- Si implementino le classi DirectedGraphAdjList e DirectedGraphAdjMatr , che rappresentano rispettivamente grafi diretti implementati con liste di adiacenza e con matrici di adiacenza. 
- Si implementino anche i relativi JUnit test.

## Compito pratico 2
- Implementazione di grafi diretti e pesati con matrici di adiacenza.
- Utilizzando, ove possibile, le classi/interfacce relative al compito pratico 1, si implementi la classe DirectedWeightedGraphAdjMatr, che rappresenta grafi diretti e pesati implementati con matrici di adiacenza. 
- Oltre ai metodi di Graph sviluppati per il primo compito, la classe dovrà:
  - nel metodo "visit", implementare Dijkstra (al contrario, non dovrà permettere di effettuare visite BFS e DFS/DFS_TOT)
  - implementare il metodo public double[][] allToAllShortestPaths(), che calcola i cammini minimi tra tutte le coppie di vertici, utilizzando l'algoritmo di Floyd Warshall (per semplicità, si ignori il fatto che la matrice così restituita non farà riferimento ai label dei vertici, ma solo al loro posizionamento nella matrice. Per i test, è possibile implementare un metodo public int labelToIndex(String label), che restituisce la posizione di un certo vertice dentro alla matrice).
- (Facoltativo, ma consigliato) si documentino con Javadoc i nuovi metodi sviluppati che non sono commentati in Graph.
- (Facoltativo) Si implementino anche i relativi JUnit test.

NOTA1: notate che lo pseudocodice mostrato nelle slide si riferisce a grafi implementati con liste di adiacenza. Per questo, il vostro codice potrebbe differire da quello.

NOTA2: chi avesse già sviluppato grafi pesati per il compito pratico 1, oltre ad implementare Dijkstra e Floyd Warshall, si dedichi a sviluppare una classe per grafi diretti non pesati implementati con matrici di adiacenza. In questo modo, la consegna conterrà sia una classe che implementa grafi pesati, sia una che implementa grafi non pesati.

## Compito pratico 3
Implementare in Java una funzione (basta un file Java con la funzione, e un test JUnit o un main che la testino) che calcola la LCS di 2 array di char dati in input, utilizzando l'algoritmo di programmazione dinamica visto a lezione. Non usi metodi Java che trovano la LCS. Le scrivo qui sotto la possibile intestazione. public static char[] LCS(char[] S1, char[] S2)