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
import org.sqlite.SQLiteConfig;

/**
 * Graph SQLite manager class to setup the SQLite environment.
 * 
 * @author Vasileios Nikakis
 */
public abstract class GraphSQLiteManager implements GraphSQLManager {
    
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
     * @param dbpath the SQLite database path
     * 
     * @throws SQLException in case of a connection error
     */
    public GraphSQLiteManager(String dbpath) throws SQLException { 
        String connUrl = "jdbc:sqlite:" + dbpath;
        SQLiteConfig config = new SQLiteConfig();  
        config.enforceForeignKeys(true);  
        conn = DriverManager.getConnection(connUrl,config.toProperties());
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
