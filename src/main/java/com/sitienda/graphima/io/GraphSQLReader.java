/**
 * Copyright (C) 2019, by Vasileios Nikakis
 *
 * graphima: yet another Java graph-theory library
 */
package com.sitienda.graphima.io;

import java.sql.SQLException;

/**
 * Graph SQL reader base interface
 * 
 * @param <V> the type of objects that the graph contains
 * 
 * @author Vasileios Nikakis
 */
public interface GraphSQLReader<V> extends GraphReader<V> {
    
    /**
     * Checks if a graph exists.
     * 
     * @return true or false according to if the graph exists
     * 
     * @throws SQLException in case of an error
     */
    public boolean graphExists() throws SQLException;
    
}
