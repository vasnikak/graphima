/**
 * Copyright (C) 2019, by Vasileios Nikakis
 *
 * graphima: yet another Java graph-theory library
 */
package com.sitienda.graphima.exceptions;

/**
 * PathNotValidException
 *
 * @author Vasileios Nikakis
 */
public class PathNotValidException extends Exception {
    
    /**
     * Constructor
     */
    public PathNotValidException() {
        super("The path is not valid");
    }

    /**
     * Constructor
     * 
     * @param string The exception message
     */
    public PathNotValidException(String string) {
        super(string);
    }
    
}
