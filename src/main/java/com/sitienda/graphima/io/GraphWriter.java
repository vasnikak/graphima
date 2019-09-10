/**
 * Copyright (C) 2019, by Vasileios Nikakis
 *
 * graphima: yet another Java graph-theory library
 */
package com.sitienda.graphima.io;

import com.sitienda.graphima.Graph;
import com.sitienda.graphima.exceptions.GraphIOException;

/**
 * The base interface for each graph writer.
 * 
 * @param <V> the type of objects that the graph contains
 * 
 * @author Vasileios Nikakis
 */
public interface GraphWriter<V> {
    
    /**
     * Writes the graph to an underlying structure.
     * 
     * @param graph the graph
     * 
     * @throws GraphIOException in case of any error
     */
    public void write(Graph<V> graph) throws GraphIOException;
    
}
