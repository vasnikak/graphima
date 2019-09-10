/**
 * Copyright (C) 2019, by Vasileios Nikakis
 *
 * graphima: yet another Java graph-theory library
 */
package com.sitienda.graphima.exceptions;

/**
 * GraphIOException
 *
 * @author Vasileios Nikakis
 */
public class GraphIOException extends Exception {
    
    /**
     * Constructor
     */
    public GraphIOException() {
        super("Graph IO exception");
    }

    /**
     * Constructor
     * 
     * @param string The exception message
     */
    public GraphIOException(String string) {
        super(string);
    }
    
}
