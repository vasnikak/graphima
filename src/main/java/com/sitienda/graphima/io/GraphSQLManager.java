/**
 * Copyright (C) 2019, by Vasileios Nikakis
 *
 * graphima: yet another Java graph-theory library
 */
package com.sitienda.graphima.io;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Graph SQL manager base interface
 * 
 * @author Vasileios Nikakis
 */
public interface GraphSQLManager {
    
    /**
     * The graph table name
     */
    public static final String TBL_GRAPH = "graph";
    /**
     * The vertex table name
     */
    public static final String TBL_VERTEX = "vertex";
    /**
     * The edge table name
     */
    public static final String TBL_EDGE = "edge";
    
    /**
     * Checks if the DB connection is active.
     * 
     * @return active connection flag
     */
    public boolean isConnectionActive();
    
    /**
     *
     * @return the DB connection
     */
    public Connection getConnection();
    
    /**
     * Closes the connection with the database.
     * 
     * @throws SQLException in case of any error
     */
    public void closeConnection() throws SQLException;
    
    /**
     * Checks if a table exists.
     * 
     * @param tblName the table name
     * 
     * @return true or false according to if the table exists
     * 
     * @throws SQLException in case of an error
     */
    public boolean tableExists(String tblName) throws SQLException;
    
}
