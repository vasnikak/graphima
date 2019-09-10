/**
 * Copyright (C) 2019, by Vasileios Nikakis
 *
 * graphima: yet another Java graph-theory library
 */
package com.sitienda.graphima.alg;

import com.sitienda.graphima.Edge;
import com.sitienda.graphima.Graph;
import com.sitienda.graphima.Vertex;
import com.sitienda.graphima.WeightedEdge;
import com.sitienda.graphima.exceptions.VertexNotInGraphException;
import com.sitienda.graphima.path.Path;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * A* algorithm. 
 * It discovers the shortest paths in a graph between a source vertex and
 * all the rest.
 *
 * @param <V> the type of the objects that the graph contains
 * 
 * @author Vasileios Nikakis
 */
public class DijkstraShortestPath<V> extends GraphAlgorithm<V> {
    
    /**
     * Collision resolution comparator object.
     */
    private final NodeComparator<Vertex<V>> collisionComp;
    /**
     * Max integer as infinity.
     */
    private final int INF = Integer.MAX_VALUE;
    
    /**
     * Inner helper class.
     * For each node, the parent node is stored.
     * 
     * @param <T> each QueueItem encapsulates a Vertex
     */
    private class QueueItem<T extends Vertex<V>> { 
        
        T node;
        T parent;
        int totalCost;
        
        public QueueItem(T node, T parent, int totalCost) { 
            this.node = node;
            this.parent = parent;
            this.totalCost = totalCost;
        }
        
    }
    
    /**
     * Constructor
     * 
     * @param graph the graph
     */
    public DijkstraShortestPath(Graph<V> graph) {
        super(graph);
        collisionComp = null;
        execStats = new AlgorithmExecutionStats("Dijkstra shortest path");
    }
    
    /**
     * Constructor
     * 
     * @param graph the graph
     * @param collisionComp the vertex comparator to resolve any collisions
     */
    public DijkstraShortestPath(Graph<V> graph, NodeComparator<Vertex<V>> collisionComp) {
        super(graph);
        this.collisionComp = collisionComp;
        execStats = new AlgorithmExecutionStats("Dijkstra shortest path");
    }
    
    /**
     * Finds the shortest paths from {@link Vertex} <i>start</i> to every other vertex
     * in the graph.
     * 
     * @param start the start vertex
     * 
     * @return a {@link java.util.Map} with the path for each vertex
     * 
     * @throws VertexNotInGraphException in case a vertex was not found 
     */
    public Map<Vertex<V>,Path> findShortestPaths(Vertex<V> start) throws VertexNotInGraphException { 
        // The vertex has to exist inside the graph
        if (!graph.contains(start))
            throw new VertexNotInGraphException("Vertex (" + start + ") doesn't exist in the graph");
        
        // Exec stats
        execStats.reset();
        
        // The open set will be a priority queue
        PriorityQueue<QueueItem<Vertex<V>>> queue = new PriorityQueue<>(new Comparator<QueueItem<Vertex<V>>>() { 
            @Override
            public int compare(QueueItem<Vertex<V>> a, QueueItem<Vertex<V>> b) {
                if (a.totalCost != b.totalCost || collisionComp == null)
                    return (a.totalCost - b.totalCost);
                else
                    return collisionComp.compare(a.node,b.node);
            }
        });
        // The closed set will be a map
        Map<Vertex<V>,QueueItem<Vertex<V>>> visited = new HashMap<>();
        
        // Initialize the open set
        QueueItem<Vertex<V>> source = new QueueItem<>(start,null,0);
        queue.add(source);
        for (Vertex<V> vertex : graph.getVertices()) { 
            if (!start.equals(vertex)) { 
                QueueItem<Vertex<V>> vItem = new QueueItem<>(vertex,null,INF);
                queue.add(vItem);
            }
        }
        // While the open set is not empty
        while (!queue.isEmpty()) { 
            // Get the item with the smallest total cost
            QueueItem<Vertex<V>> current = queue.peek();
            // Mark the current item as visited
            visited.put(current.node,current);
            // Exec stats
            execStats.incNodesVisitedNum();
            // Iterate over its direct neighbors
            for (Edge<Vertex<V>> edge : current.node.getEdges()) { 
                // We need only those that they are still in the open set
                if (visited.get(edge.getVertex()) != null)
                    continue;

                // Calculate the new cost
                int newCost = current.totalCost + 
                                ((edge instanceof WeightedEdge) ? 
                                    ((WeightedEdge) edge).getCost() : 1);
                // If the new cost is less than the actual one, we have to update it
                for (QueueItem<Vertex<V>> qItem : queue) { 
                    if (qItem.node.equals(edge.getVertex())) { 
                        if (newCost < qItem.totalCost) { 
                            qItem.parent = current.node;
                            qItem.totalCost = newCost;
                        }
                        break;
                    }
                }
            }
            // Remove the current item from the priority queue. This way the
            // queue will be re-ordered for the next iteration
            queue.poll();
        }
        
        // Create a map with the shortest paths from start to each node
        Map<Vertex<V>,Path> paths = new HashMap<>();
        // For each one of the vertices
        for (Map.Entry<Vertex<V>,QueueItem<Vertex<V>>> entry : visited.entrySet()) { 
            // Construct its path
            Vertex<V> vertex = entry.getKey();
            QueueItem<Vertex<V>> qItem = entry.getValue();
            Path<Vertex<V>> path = new Path<>();
            QueueItem<Vertex<V>> run = qItem;
            while (run != null) { 
                path.prepend(run.node);
                run = visited.get(run.parent);
            }
            // If the path starts with the start vertex
            if (path.startsWith(start))
                // Insert its path in the map
                paths.put(vertex,path);
            // Else, insert an empty path
            else
                paths.put(vertex,new Path<>());
        }
        // Exec stats
        execStats.stopExecution();
        return paths;
    }
    
    /**
     * Finds the shortest paths from a vertex to every other vertex in the graph.
     * The data of the the vertex has to be provided.
     * 
     * @param start the start vertex
     * 
     * @return a {@link java.util.Map} with the path for each vertex
     * 
     * @throws VertexNotInGraphException in case a vertex was not found 
     */
    public Map<V,Path> findShortestPaths(V start) throws VertexNotInGraphException { 
        // Find the corresponding vertices
        Vertex<V> startVertex = graph.getVertexWithData(start);
        if (startVertex == null)
            throw new VertexNotInGraphException("The graph does not contain any vertex with data: " + start);
        Map<Vertex<V>,Path> vertexPaths = findShortestPaths(startVertex);
        Map<V,Path> objPaths = new HashMap<>();
        for (Map.Entry<Vertex<V>,Path> entry : vertexPaths.entrySet())
            objPaths.put(entry.getKey().getData(),entry.getValue());
        return objPaths;
    }
    
}
