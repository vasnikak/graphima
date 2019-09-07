/**
 * Copyright (C) 2019, by Vasileios Nikakis
 *
 * graphima: yet another Java graph-theory library
 */
package com.sitienda.graphima.alg;

import com.sitienda.graphima.Graph;
import com.sitienda.graphima.Vertex;
import com.sitienda.graphima.alg.heuristics.ZeroHeuristicFunction;

/**
 * UCS (Uniform Cost Search) algorithm. 
 * It discovers the shortest path in a graph between two vertices using the
 * UCS algorithm.
 *
 * @param <T> the type of the objects that the graph contains
 * 
 * @author Vasileios Nikakis
 */
public class UCSShortestPath<T> extends AStarShortestPath<T> {
    
    /**
     * Constructor
     * 
     * @param graph the graph
     */
    public UCSShortestPath(Graph graph) {
        super(graph);
        heuristicFunc = new ZeroHeuristicFunction<>();
        execStats.setAlgorithmName("UCS shortest path");
    }
    
    /**
     * Constructor
     * 
     * @param graph the graph
     * @param collisionComp the vertex comparator to resolve any collisions
     */
    public UCSShortestPath(Graph graph, NodeComparator<Vertex<T>> collisionComp) {
        super(graph,collisionComp);
        heuristicFunc = new ZeroHeuristicFunction<>();
        execStats.setAlgorithmName("UCS shortest path");
    }
    
}
