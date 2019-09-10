/**
 * Copyright (C) 2019, by Vasileios Nikakis
 *
 * graphima: yet another Java graph-theory library
 */
package com.sitienda.graphima.exceptions;

/**
 * ErroneousFileFormatException
 *
 * @author Vasileios Nikakis
 */
public class ErroneousFileFormatException extends Exception {
    
    /**
     * Constructor
     */
    public ErroneousFileFormatException() {
        super("Erroneous file format");
    }

    /**
     * Constructor
     * 
     * @param string The exception message
     */
    public ErroneousFileFormatException(String string) {
        super(string);
    }
    
}
