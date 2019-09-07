/**
 * Copyright (C) 2019, by Vasileios Nikakis
 *
 * graphima: yet another Java graph-theory library
 */
package com.sitienda.graphima.alg;

import com.sitienda.graphima.Edge;
import com.sitienda.graphima.Graph;
import com.sitienda.graphima.Vertex;
import com.sitienda.graphima.exceptions.VertexNotInGraphException;
import com.sitienda.graphima.path.Path;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * BFS (Breadth First Search) algorithm. 
 * It discovers the shortest path in a graph between two vertices using the
 * BFS algorithm.
 * <p>
 * The class provides the option to supply a {@link com.sitienda.graphima.alg.NodeComparator}
 * object in case that a manual collision resolution is necessary.
 * </p>
 *
 * @param <T> the type of the objects that the graph contains
 * 
 * @author Vasileios Nikakis
 */
public class BFSShortestPath<T> extends GraphAlgorithm<T> {
    
    /**
     * Collision resolution comparator object.
     */
    private final NodeComparator<T> collisionComp;
    
    /**
     * Inner helper class.
     * For each node, the parent node is stored.
     * 
     * @param <V> each QueueItem encapsulates a Vertex
     */
    private class QueueItem<V extends Vertex<T>> { 
        
        V node;
        V parent;
        
        public QueueItem(V node, V parent) { 
            this.node = node;
            this.parent = parent;
        }
        
    }
    
    /**
     * Constructor.
     * 
     * @param graph the graph
     */
    public BFSShortestPath(Graph graph) {
        super(graph);
        collisionComp = null;
    }
    
    /**
     * Constructor.
     * 
     * @param graph the graph
     * @param collisionComp the node comparator to resolve any collisions
     */
    public BFSShortestPath(Graph graph, NodeComparator<T> collisionComp) {
        super(graph);
        this.collisionComp = collisionComp;
    }
    
    /**
     * Finds the shortest path between start and end vertices using the BFS algorithm.
     * 
     * @param start the starting vertex
     * @param end the destination vertex
     * 
     * @return the shortest path from start to end
     * 
     * @throws VertexNotInGraphException in case that any of the two vertices doesn't belong to the graph
     */
    public Path findShortestPath(Vertex<T> start, Vertex<T> end) throws VertexNotInGraphException { 
        // Both vertices have to exist inside the graph
        if (!graph.contains(start))
            throw new VertexNotInGraphException("The starting point vertex (" + start + ") doesn't exist in the graph");
        if (!graph.contains(end))
            throw new VertexNotInGraphException("The ending point vertex (" + end + ") doesn't exist in the graph");
        
        // Exec stats
        execStats.setAlgorithmName("BFS shortest path");
        execStats.reset();
        
        // We need a queue to store the nodes that wait to be examined
        Deque<QueueItem<Vertex<T>>> queue = new ArrayDeque<>();
        // We need a map to be able to extract the path after the execution of the algorithm
        Map<Vertex<T>,QueueItem<Vertex<T>>> visited = new HashMap<>();
        
        // Add the starting node in the queue
        QueueItem<Vertex<T>> first = new QueueItem<>(start,null);
        queue.add(first);
        visited.put(start,first);
        // We will assign the destination queue item to this variable
        QueueItem<Vertex<T>> target = null;
        // While the queue is not empty
        while (!queue.isEmpty()) { 
            // Get the queue's first node
            QueueItem<Vertex<T>> current = queue.pop();
            // If it is the destination, stop the iteration
            if (current.node.equals(end)) { 
                target = current;
                break;
            }
            // If no comparator was defined for collision resolution
            if (collisionComp == null) { 
                for (Edge edge : current.node.getEdges()) { 
                    // If we haven't visited yet the child node
                    if (visited.get(edge.getVertex()) == null) { 
                        // Add the node in the queue
                        QueueItem<Vertex<T>> child = new QueueItem<>(edge.getVertex(),current.node);
                        queue.add(child);
                        // Mark the node as visited
                        visited.put(edge.getVertex(),child);
                        // Exec stats
                        execStats.incNodesVisitedNum();
                    }
                }
            // If a comparator for collision resolution was defined
            } else { 
                // Create a temporary list to resolve collisions
                List<QueueItem<Vertex<T>>> children = new LinkedList<>();
                for (Edge edge : current.node.getEdges()) { 
                    // If we haven't visited yet the child node
                    if (visited.get(edge.getVertex()) == null) { 
                        // Add the node in the temporary list
                        QueueItem<Vertex<T>> child = new QueueItem<>(edge.getVertex(),current.node);
                        children.add(child);
                        // Mark the node as visited
                        visited.put(edge.getVertex(),child);
                    }
                }
                // Sort the list using the collision resolution comparator
                children.sort(new Comparator<QueueItem<Vertex<T>>>() {
                    @Override
                    public int compare(QueueItem<Vertex<T>> a, QueueItem<Vertex<T>> b) {
                        return collisionComp.compare(a.node.getData(),b.node.getData());
                    }
                });
                // Add the list items in the queue
                for (QueueItem<Vertex<T>> child : children)
                    queue.add(child);
            }
        }
        
        // Build the path from start to end
        Path<Vertex<T>> path = new Path<>();
        QueueItem<Vertex<T>> run = target;
        while (run != null) { 
            path.prepend(run.node);
            run = visited.get(run.parent);
        }
        // Exec stats
        execStats.stopExecution();
        execStats.setPathLength(path.size());
        // Return the path
        return path;
    }

    /**
     * Finds the shortest path between two nodes in a graph using the BFS algorithm.
     * For each vertex, the corresponding data has to be provided.
     * 
     * @param start the corresponding data of the starting vertex
     * @param end the corresponding data of the destination vertex
     * 
     * @return the shortest path from start to end
     * 
     * @throws VertexNotInGraphException in case that any of the two vertices doesn't belong to the graph
     */
    public Path findShortestPath(T start, T end) throws VertexNotInGraphException { 
        // Find the corresponding vertices
        Vertex<T> startVertex = graph.getVertexWithData(start);
        if (startVertex == null)
            throw new VertexNotInGraphException("The graph does not contain any vertex with data: " + start);
        Vertex<T> endVertex = graph.getVertexWithData(end);
        if (endVertex == null)
            throw new VertexNotInGraphException("The graph does not contain any vertex with data: " + end);
        return findShortestPath(startVertex,endVertex);
    }
    
}
