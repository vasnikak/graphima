/**
 * Copyright (C) 2019, by Vasileios Nikakis
 *
 * graphima: yet another Java graph-theory library
 */
package com.sitienda.graphima.io;

/**
 * The base class for each graph file reader.
 *
 * @param <V> the type of the objects that are encapsulated in each graph vertex
 * 
 * @author Vasileios Nikakis
 */
public abstract class GraphFileReader<V> implements GraphReader<V> {

    /**
     * The input file path
     */
     protected final String filepath;
    
    /**
     * Constructor
     * 
     * @param filepath the input file path
     */
    public GraphFileReader(String filepath) {
        this.filepath = filepath;
    }
    
}
