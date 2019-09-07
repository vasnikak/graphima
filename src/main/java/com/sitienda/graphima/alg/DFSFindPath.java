/*
 * Copyright (C) 2019, Vasileios Nikakis
 *
 * graphima : yet another Java graph-theory library
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
import java.util.ListIterator;
import java.util.Map;

/**
 * DFS (Depth First Search) algorithm. 
 * It discovers at path in a graph between two vertices using the DFS algorithm.
 * <p>
 * The class provides the option to supply a {@link com.sitienda.graphima.alg.NodeComparator}
 * object in case that a manual collision resolution is necessary.
 * </p>
 *
 * @param <T> the type of the objects that the graph contains
 * 
 * @author Vasileios Nikakis
 */
public class DFSFindPath<T> extends GraphAlgorithm<T> {
    
    /**
     * Collision resolution comparator object.
     */
    private final NodeComparator<T> collisionComp;
    
    /**
     * Inner helper class.
     * For each node, the parent node is stored.
     * 
     * @param <V> each StackItem encapsulates a Vertex.
     */
    private class StackItem<V extends Vertex<T>> { 
        
        V node;
        V parent;
        
        public StackItem(V node, V parent) { 
            this.node = node;
            this.parent = parent;
        }
        
    }
    
    /**
     * Constructor.
     * 
     * @param graph the graph
     */
    public DFSFindPath(Graph graph) {
        super(graph);
        collisionComp = null;
    }
    
    /**
     * Constructor.
     * 
     * @param graph the graph.
     * @param collisionComp the vertex comparator to resolve any collisions.
     */
    public DFSFindPath(Graph graph, NodeComparator<T> collisionComp) {
        super(graph);
        this.collisionComp = collisionComp;
    }
    
    /**
     * Finds a path between start and end vertices using the DFS algorithm.
     * 
     * @param start the starting vertex
     * @param end the destination vertex
     * 
     * @return the shortest path from start to end
     * 
     * @throws VertexNotInGraphException in case that any of the two vertices doesn't belong to the graph
     */
    public Path findPath(Vertex<T> start, Vertex<T> end) throws VertexNotInGraphException { 
        // Both vertices have to exist inside the graph
        if (!graph.contains(start))
            throw new VertexNotInGraphException("The starting point vertex (" + start + ") doesn't exist in the graph");
        if (!graph.contains(end))
            throw new VertexNotInGraphException("The ending point vertex (" + end + ") doesn't exist in the graph");
        
        // Exec stats
        execStats.setAlgorithmName("DFS find path");
        execStats.reset();
        
        // We need a stack to store the nodes that wait to be examined
        Deque<StackItem<Vertex<T>>> stack = new ArrayDeque<>();
        // We need a map to be able to extract the path after the execution of the algorithm
        Map<Vertex<T>,StackItem<Vertex<T>>> visited = new HashMap<>();
        
        // Push the starting node in the stack
        StackItem<Vertex<T>> first = new StackItem<>(start,null);
        stack.push(first);
        visited.put(start,first);
        // We will assign the destination stack item to this variable
        StackItem<Vertex<T>> target = null;
        // While the stack is not empty
        while (!stack.isEmpty()) { 
            // Get the stack's first node
            StackItem<Vertex<T>> current = stack.pop();
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
                        // Push the node in the stack
                        StackItem<Vertex<T>> child = new StackItem<>(edge.getVertex(),current.node);
                        stack.add(child);
                        // Mark the node as visited
                        visited.put(edge.getVertex(),child);
                        // Exec stats
                        execStats.incNodesVisitedNum();
                    }
                }
            // If a comparator for collision resolution was defined
            } else { 
                // Create a temporary list to resolve collisions
                List<StackItem<Vertex<T>>> children = new LinkedList<>();
                for (Edge edge : current.node.getEdges()) { 
                    // If we haven't visited yet the child node
                    if (visited.get(edge.getVertex()) == null) { 
                        // Add the node in the temporary list
                        StackItem<Vertex<T>> child = new StackItem<>(edge.getVertex(),current.node);
                        children.add(child);
                        // Mark the node as visited
                        visited.put(edge.getVertex(),child);
                    }
                }
                // Sort the list using the collision resolution comparator
                children.sort(new Comparator<StackItem<Vertex<T>>>() {
                    @Override
                    public int compare(StackItem<Vertex<T>> a, StackItem<Vertex<T>> b) {
                        return collisionComp.compare(a.node.getData(),b.node.getData());
                    }
                });
                // Push the list items in the stack in a reverse order
                // (we are pushing the items in a stack)
                for (ListIterator<StackItem<Vertex<T>>> it = children.listIterator(children.size());
                     it.hasPrevious();)
                    stack.push(it.previous());
            }
        }
        
        // Build the path from start to end
        Path<Vertex<T>> path = new Path<>();
        StackItem<Vertex<T>> run = target;
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
     * Finds a path between two nodes in a graph using the DFS algorithm.
     * For each vertex, the corresponding data has to be provided.
     * 
     * @param start the corresponding data of the starting vertex
     * @param end the corresponding data of the destination vertex
     * 
     * @return the shortest path from start to end
     * 
     * @throws VertexNotInGraphException in case that any of the two vertices doesn't belong to the graph
     */
    public Path findPath(T start, T end) throws VertexNotInGraphException { 
        // Find the corresponding vertices
        Vertex<T> startVertex = graph.getVertexWithData(start);
        if (startVertex == null)
            throw new VertexNotInGraphException("The graph does not contain any vertex with data: " + start);
        Vertex<T> endVertex = graph.getVertexWithData(end);
        if (endVertex == null)
            throw new VertexNotInGraphException("The graph does not contain any vertex with data: " + end);
        return findPath(startVertex,endVertex);
    }
    
}
