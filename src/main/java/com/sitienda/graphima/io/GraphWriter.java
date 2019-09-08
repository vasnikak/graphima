/**
 * Copyright (C) 2019, by Vasileios Nikakis
 *
 * graphima: yet another Java graph-theory library
 */
package com.sitienda.graphima.io;

import com.sitienda.graphima.Graph;

/**
 * The base class for each graph writer.
 * 
 * @param <V> the type of objects that the graph contains
 * 
 * @author Vasileios Nikakis
 */
public abstract class GraphWriter<V> {
    
    /**
     * The graph.
     */
    protected final Graph<V> graph;
    
    /**
     * Constructor
     * 
     * @param graph the graph
     */
    public GraphWriter(Graph<V> graph) { 
        this.graph = graph;
    }
    
}
