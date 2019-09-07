/**
 * Copyright (C) 2019, by Vasileios Nikakis
 *
 * graphima: yet another Java graph-theory library
 */
package com.sitienda.graphima.alg;

import com.sitienda.graphima.alg.heuristics.ZeroHeuristicFunction;
import com.sitienda.graphima.alg.heuristics.HeuristicFunction;
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
 * It discovers the shortest path in a graph between two vertices using the
 * A* heuristic algorithm.
 *
 * @param <T> the type of the objects that the graph contains
 * 
 * @author Vasileios Nikakis
 */
public class AStarShortestPath<T> extends GraphAlgorithm {
    
    /**
     * Collision resolution comparator object.
     */
    private final NodeComparator<Vertex<T>> collisionComp;
    /**
     * The heuristic function.
     */
    protected HeuristicFunction<T> heuristicFunc;
    
    /**
     * Inner helper class.
     * For each node, the parent node is stored.
     * 
     * @param <V> each QueueItem encapsulates a Vertex
     */
    private class QueueItem<V extends Vertex<T>> { 
        
        V node;
        V parent;
        int totalCost;
        int eval;
        
        public QueueItem(V node, V parent, int totalCost) { 
            this.node = node;
            this.parent = parent;
            this.totalCost = totalCost;
            updateEval();
        }
        
        public final int updateEval() { 
            eval = totalCost + heuristicFunc.h(node.getData());
            return eval;
        }
        
    }
    
    /**
     * Constructor
     * 
     * @param graph the graph
     */
    public AStarShortestPath(Graph graph) {
        super(graph);
        collisionComp = null;
        heuristicFunc = new ZeroHeuristicFunction<>();
        execStats.setAlgorithmName("A* shortest path");
    }
    
    /**
     * Constructor
     * 
     * @param graph the graph
     * @param collisionComp the vertex comparator to resolve any collisions
     */
    public AStarShortestPath(Graph graph, NodeComparator<Vertex<T>> collisionComp) {
        super(graph);
        this.collisionComp = collisionComp;
        heuristicFunc = new ZeroHeuristicFunction<>();
        execStats.setAlgorithmName("A* shortest path");
    }
    
    /**
     * Constructor
     * 
     * @param graph the graph
     * @param heuristicFunc the heuristic function
     */
    public AStarShortestPath(Graph graph, HeuristicFunction<T> heuristicFunc) { 
        super(graph);
        collisionComp = null;
        this.heuristicFunc = heuristicFunc;
        execStats.setAlgorithmName("A* shortest path");
    }
    
    /**
     * Constructor
     * 
     * @param graph the graph
     * @param collisionComp the vertex comparator to resolve any collisions
     * @param heuristicFunc the heuristic function
     */
    public AStarShortestPath(Graph graph, 
                             NodeComparator<Vertex<T>> collisionComp, 
                             HeuristicFunction<T> heuristicFunc) { 
        super(graph);
        this.collisionComp = collisionComp;
        this.heuristicFunc = heuristicFunc;
        execStats.setAlgorithmName("A* shortest path");
    }
    
    /**
     * Finds the shortest path between start and end vertices using the A* algorithm.
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
        execStats.reset();
        
        // We need a priority queue to store the nodes that wait to be examined
        PriorityQueue<QueueItem<Vertex<T>>> queue = new PriorityQueue<>(new Comparator<QueueItem<Vertex<T>>>() { 
            @Override
            public int compare(QueueItem<Vertex<T>> a, QueueItem<Vertex<T>> b) {
                if (a.eval != b.eval || collisionComp == null)
                    return (a.eval - b.eval);
                else
                    return collisionComp.compare(a.node,b.node);
            }
        });
        // We need a map to be able to extract the path after the execution of the algorithm
        Map<Vertex<T>,QueueItem<Vertex<T>>> visited = new HashMap<>();
        
        // Add the starting node in the queue
        QueueItem<Vertex<T>> first = new QueueItem<>(start,null,0);
        queue.add(first);
        visited.put(start,first);
        // We will assign the destination queue item to this variable
        QueueItem<Vertex<T>> target = null;
        // While the queue is not empty
        while (!queue.isEmpty()) { 
            // Get the queue's first node
            QueueItem<Vertex<T>> current = queue.poll();
            // If it is the destination, stop the iteration
            if (current.node.equals(end)) { 
                target = current;
                break;
            }
            // Add the neighbors in the queue
            for (Edge edge : current.node.getEdges()) { 
                // Calculate the total cost
                int totalCost = current.totalCost + 
                                ((edge instanceof WeightedEdge) ? 
                                    ((WeightedEdge) edge).getCost() : 1);
                // If we haven't visited yet the child node
                QueueItem<Vertex<T>> child = visited.get(edge.getVertex());
                if (child == null) { 
                    // Add the node in the queue
                    child = new QueueItem<>(edge.getVertex(),current.node,totalCost);
                    queue.add(child);
                    // Mark the node as visited
                    visited.put(edge.getVertex(),child);
                    // Exec stats
                    execStats.incNodesVisitedNum();
                // If we have already visited the child node, we have to
                // check if its path from the root node has to be updated
                } else { 
                    // If the new total cost is less than the existing one
                    if (totalCost < child.totalCost)
                        // Update child's path from the root
                        child.parent = current.node;
                }
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
     * Finds the shortest path between two nodes in a graph using the A* algorithm.
     * For each vertex, the corresponding data has to be provided.
     * 
     * @param start the corresponding data of the starting vertex
     * @param end the corresponding data of the destination vertex
     * 
     * @return the shortest path from start to end
     * 
     * @throws VertexNotInGraphException in case that any of the two vertices doesn't belong to the graph
     */
    public Path findShortestPath(Object start, Object end) throws VertexNotInGraphException { 
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
