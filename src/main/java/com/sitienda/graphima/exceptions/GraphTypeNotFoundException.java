/**
 * Copyright (C) 2019, by Vasileios Nikakis
 *
 * graphima: yet another Java graph-theory library
 */
package com.sitienda.graphima.exceptions;

/**
 * GraphTypeNotFoundException
 *
 * @author Vasileios Nikakis
 */
public class GraphTypeNotFoundException extends Exception {
    
    /**
     * Constructor
     */
    public GraphTypeNotFoundException() {
        super("Graph type not found");
    }

    /**
     * Constructor
     * 
     * @param string The exception message.
     */
    public GraphTypeNotFoundException(String string) {
        super(string);
    }
    
}
