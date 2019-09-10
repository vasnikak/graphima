/**
 * Copyright (C) 2019, by Vasileios Nikakis
 *
 * graphima: yet another Java graph-theory library
 */
package com.sitienda.graphima.io;

import com.sitienda.graphima.Graph;
import com.sitienda.graphima.exceptions.GraphIOException;

/**
 * The base interface for each graph reader.
 *
 * @param <V> the type of the objects that the graph contains
 * 
 * @author Vasileios Nikakis
 */
public interface GraphReader<V> {
    
    /**
     * Reads a graph from an underlying structure.
     * 
     * @param cls the Class object of the class that its objects are 
     *            encapsulated inside graph's vertices.
     * 
     * @return the graph
     * 
     * @throws GraphIOException in case of any error
     */
    public Graph<V> read(Class<V> cls) throws GraphIOException;
    
}
