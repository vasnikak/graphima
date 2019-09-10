/**
 * Copyright (C) 2019, by Vasileios Nikakis
 *
 * graphima: yet another Java graph-theory library
 */
package com.sitienda.graphima.exceptions;

/**
 * GraphDataMissingException
 *
 * @author Vasileios Nikakis
 */
public class GraphDataMissingException extends Exception {
    
    /**
     * Constructor
     */
    public GraphDataMissingException() {
        super("Graph type not found");
    }

    /**
     * Constructor
     * 
     * @param string The exception message
     */
    public GraphDataMissingException(String string) {
        super(string);
    }
    
}
