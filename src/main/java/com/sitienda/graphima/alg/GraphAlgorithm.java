/**
 * Copyright (C) 2019, by Vasileios Nikakis
 *
 * graphima: yet another Java graph-theory library
 */
package com.sitienda.graphima.alg;

import com.sitienda.graphima.Graph;

/**
 * The base class for all graph algorithms.
 *
 * @param <V> the type of the objects that the graph contains
 * 
 * @author Vasileios Nikakis
 */
public abstract class GraphAlgorithm<V> {
    
    /**
     * The graph.
     */
    protected Graph<V> graph;
    /**
     * Algorithm execution statistics.
     */
    protected AlgorithmExecutionStats execStats;
    
    /**
     * Constructor
     * 
     * @param graph the graph
     */
    public GraphAlgorithm(Graph<V> graph) { 
        this.graph = graph;
    }
    
    /**
     * 
     * @return the algorithm execution statistics.
     */
    public AlgorithmExecutionStats getExecStats() { 
        return execStats;
    }
    
}
