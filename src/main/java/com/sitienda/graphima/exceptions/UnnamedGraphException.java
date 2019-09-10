/**
 * Copyright (C) 2019, by Vasileios Nikakis
 *
 * graphima: yet another Java graph-theory library
 */
package com.sitienda.graphima.exceptions;

/**
 * UnnamedGraphException
 *
 * @author Vasileios Nikakis
 */
public class UnnamedGraphException extends Exception {
    
    /**
     * Constructor
     */
    public UnnamedGraphException() {
        super("The graph is unnamed");
    }

    /**
     * Constructor
     * 
     * @param string The exception message
     */
    public UnnamedGraphException(String string) {
        super(string);
    }
    
}
