/*
 * Copyright (C) 2019, Vasileios Nikakis
 *
 * graphima : yet another Java graph-theory library
 */
package com.sitienda.graphima.io;

import com.sitienda.graphima.Graph;
import com.sitienda.graphima.exceptions.GraphIOException;

/**
 * The base class for each graph file writer.
 * 
 * @author Vasileios Nikakis
 */
public abstract class GraphFileWriter extends GraphWriter {
    
    /**
     * Constructor
     * 
     * @param graph the graph
     */
    public GraphFileWriter(Graph<?> graph) {
        super(graph);
    }
    
    /**
     * Exports the graph to a file.
     * 
     * @param filepath the path of the export file
     * 
     * @throws GraphIOException if any IO error occurs
     */
    public abstract void writeFile(String filepath) throws GraphIOException;
    
}
