/**
 * Copyright (C) 2019, by Vasileios Nikakis
 *
 * graphima: yet another Java graph-theory library
 */
package com.sitienda.graphima.io;

import com.sitienda.graphima.Graph;
import java.sql.SQLException;

/**
 * Graph SQL writer base interface
 * 
 * @param <V> the type of objects that the graph contains
 * 
 * @author Vasileios Nikakis
 */
public interface GraphSQLWriter<V> extends GraphWriter<V> {
    
    /**
     * Creates all the necessary tables.
     * 
     * @throws SQLException in case of an error
     */
    public void createTables() throws SQLException;
    
    /**
     * Drops all the tables.
     * 
     * @throws SQLException in case of an error
     */
    public void dropTables() throws SQLException;
    
    /**
     * Deletes a graph with a specific name.
     * 
     * @param graph the graph
     * 
     * @throws SQLException in case of an error
     */
    public void deleteGraph(Graph<V> graph) throws SQLException;
    
}
