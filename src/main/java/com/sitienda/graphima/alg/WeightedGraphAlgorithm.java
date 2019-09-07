/*
 * Copyright (C) 2019, Vasileios Nikakis
 *
 * graphima : yet another Java graph-theory library
 */
package com.sitienda.graphima.alg;

import com.sitienda.graphima.WeightedGraph;

/**
 * The base class for all graph algorithms that require a weighted graph.
 *
 * @param <T> the graph type
 *
 * @author Vasileios Nikakis
 */
public abstract class WeightedGraphAlgorithm<T extends WeightedGraph> extends GraphAlgorithm<T> {
    
    /**
     * Constructor.
     * 
     * @param graph the graph
     */
    public WeightedGraphAlgorithm(T graph) {
        super(graph);
    }
    
}
