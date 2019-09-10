/**
 * Copyright (C) 2019, by Vasileios Nikakis
 *
 * graphima: yet another Java graph-theory library
 */
package com.sitienda.graphima.exceptions;

/**
 * VertexNullPointerException
 *
 * @author Vasileios Nikakis
 */
public class VertexNullPointerException extends RuntimeException {
    
    /**
     * Constructor
     */
    public VertexNullPointerException() {
        super("The vertex cannot contain null as data");
    }

    /**
     * Constructor
     * 
     * @param string The exception message
     */
    public VertexNullPointerException(String string) {
        super(string);
    }
    
}
