/*
 * Copyright (C) 2019, Vasileios Nikakis
 *
 * graphima : yet another Java graph-theory library
 */
package com.sitienda.graphima.exceptions;

/**
 * VertexNotInGraphException
 *
 * @author Vasileios Nikakis
 */
public class VertexNotInGraphException extends Exception {
    
    /**
     * Constructor.
     */
    public VertexNotInGraphException() {
        super("The graph doesn't contain this vertex");
    }

    /**
     * Constructor.
     * @param string The exception message.
     */
    public VertexNotInGraphException(String string) {
        super(string);
    }
    
}
