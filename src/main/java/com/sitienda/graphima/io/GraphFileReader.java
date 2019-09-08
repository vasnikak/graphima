/**
 * Copyright (C) 2019, by Vasileios Nikakis
 *
 * graphima: yet another Java graph-theory library
 */
package com.sitienda.graphima.io;

import com.sitienda.graphima.Graph;
import com.sitienda.graphima.exceptions.ErroneousFileFormatException;
import com.sitienda.graphima.exceptions.GraphDataMissingException;
import com.sitienda.graphima.exceptions.GraphIOException;

/**
 * The base class for each graph file reader.
 *
 * @param <V> the type of the objects that are encapsulated in each graph vertex
 * 
 * @author Vasileios Nikakis
 */
public abstract class GraphFileReader<V> extends GraphReader<V> {

    /**
     * The file path.
     */
    protected final String filepath;
    
    /**
     * Constructor
     * 
     * @param filepath the file path
     */
    public GraphFileReader(String filepath) {
        this.filepath = filepath;
    }
    
    /**
     * Creates and returns a new graph based on the data in the file.
     * 
     * @param cls the Class object of the class that its objects are 
     *            encapsulated inside graph's vertices.
     * 
     * @return the graph
     * 
     * @throws GraphIOException if any IO error occurs
     * @throws ErroneousFileFormatException if the graph type is not present in the file
     */
    public abstract Graph<V> readFile(Class<V> cls) throws GraphIOException, ErroneousFileFormatException;
    
}
