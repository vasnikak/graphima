/**
 * Copyright (C) 2019, by Vasileios Nikakis
 *
 * graphima: yet another Java graph-theory library
 */
package com.sitienda.graphima.io;

/**
 * The base class for each graph file writer.
 * 
 * @param <V> the type of objects that the graph contains
 * 
 * @author Vasileios Nikakis
 */
public abstract class GraphFileWriter<V> implements GraphWriter<V> {
    
    /**
     * The output file path
     */
     protected final String filepath;

    /**
     * Constructor
     * 
     * @param filepath the output file path
     */
    public GraphFileWriter(String filepath) {
        this.filepath = filepath;
    }
    
}
