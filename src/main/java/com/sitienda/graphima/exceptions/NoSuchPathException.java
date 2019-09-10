/**
 * Copyright (C) 2019, by Vasileios Nikakis
 *
 * graphima: yet another Java graph-theory library
 */
package com.sitienda.graphima.exceptions;

/**
 * NoSuchPathException
 *
 * @author Vasileios Nikakis
 */
public class NoSuchPathException extends Exception {

    /**
     * Constructor
     */
    public NoSuchPathException() {
        super("No such path");
    }

    /**
     * Constructor
     * 
     * @param string The exception message
     */
    public NoSuchPathException(String string) {
        super(string);
    }
    
}
