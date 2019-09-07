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
 * @param <T> the type of the objects that the graph contains
 * 
 * @author Vasileios Nikakis
 */
public abstract class GraphAlgorithm<T> {
    
    /**
     * The graph.
     */
    protected Graph<T> graph;
    /**
     * Algorithm execution statistics.
     */
    protected AlgorithmExecutionStats execStats;
    
    /**
     * Constructor
     * 
     * @param graph the graph
     */
    public GraphAlgorithm(Graph<T> graph) { 
        this.graph = graph;
        execStats = new AlgorithmExecutionStats();
    }
    
    /**
     * 
     * @return the algorithm execution statistics.
     */
    public AlgorithmExecutionStats getExecStats() { 
        return execStats;
    }
    
}
