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
 * @author Vasileios Nikakis
 */
public abstract class GraphWriter {
    
    /**
     * The graph.
     */
    protected final Graph<?> graph;
    
    /**
     * Constructor
     * 
     * @param graph the graph
     */
    public GraphWriter(Graph<?> graph) { 
        this.graph = graph;
    }
    
}
