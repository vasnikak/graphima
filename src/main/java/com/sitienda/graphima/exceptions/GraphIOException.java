/*
 * Copyright (C) 2019, Vasileios Nikakis
 *
 * graphima : yet another Java graph-theory library
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
        
    }

    /**
     * Constructor
     * 
     * @param string The exception message.
     */
    public GraphIOException(String string) {
        super(string);
    }
    
}
