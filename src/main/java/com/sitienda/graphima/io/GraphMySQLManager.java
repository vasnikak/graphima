/**
 * Copyright (C) 2019, by Vasileios Nikakis
 *
 * graphima: yet another Java graph-theory library
 */
package com.sitienda.graphima.io;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Graph MySQL manager class to setup the MySQL environment.
 * 
 * @author Vasileios Nikakis
 */
public abstract class GraphMySQLManager implements GraphSQLManager {
    
    /**
     * The database connection
     */
    protected final Connection conn;
    /**
     * Active connection flag
     */
    protected boolean activeConn;
    
    /**
     * Constructor
     * 
     * @param host the DB host
     * @param port the DB port
     * @param dbname the DB name
     * @param user the username
     * @param pass the password
     * 
     * @throws SQLException in case of an error
     */
    public GraphMySQLManager(String host, int port, String dbname, 
                             String user, String pass) throws SQLException { 
        String connUrl = "jdbc:mysql://" + host + ":" + port + "/" + dbname;
        conn = DriverManager.getConnection(connUrl,user,pass);
        activeConn = true;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isConnectionActive() { 
        return activeConn;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Connection getConnection() { 
        return conn;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void closeConnection() throws SQLException { 
        if (!activeConn)
            return;
        conn.close();
        activeConn = false;
    }
    
    /**
     * Checks if a table exists.
     * 
     * @param tblName the table name
     * 
     * @return true or false according to if the table exists
     * 
     * @throws SQLException in case of an error
     */
    @Override
    public boolean tableExists(String tblName) throws SQLException { 
        ResultSet rs = null;
        try { 
            DatabaseMetaData meta = conn.getMetaData();
            rs = meta.getTables(null,null,tblName,null);
            int rowCount = 0;
            while (rs.next())
                rowCount++;
            return (rowCount > 0);
        }
        finally { 
            if (rs != null) {
                try { rs.close(); } catch (SQLException e) { }
            }
        }
    }
    
}
