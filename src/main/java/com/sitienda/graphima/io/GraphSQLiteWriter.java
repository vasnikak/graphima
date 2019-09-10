/**
 * Copyright (C) 2019, by Vasileios Nikakis
 *
 * graphima: yet another Java graph-theory library
 */
package com.sitienda.graphima.io;

import com.sitienda.graphima.Edge;
import com.sitienda.graphima.Graph;
import com.sitienda.graphima.Vertex;
import com.sitienda.graphima.WeightedEdge;
import com.sitienda.graphima.exceptions.GraphIOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * Graph SQLite writer
 * 
 * @param <V> the type of objects that the graph contains
 * 
 * @author Vasileios Nikakis
 */
public class GraphSQLiteWriter<V> extends GraphSQLiteManager implements GraphSQLWriter<V> {
    
    /**
     * Table create commands
     */
    private static final String TBL_GRAPH_CREATE = 
            "CREATE TABLE IF NOT EXISTS `graph` (" + 
            "	`graph_id` INTEGER PRIMARY KEY AUTOINCREMENT," + 
            "   `type`     VARCHAR(32) NOT NULL, " + 
            "   `name`     VARCHAR(256) UNIQUE NOT NULL" + 
            ")";
    private static final String TBL_VERTEX_CREATE = 
            "CREATE TABLE IF NOT EXISTS `vertex` (" + 
            "	`vertex_id` INTEGER PRIMARY KEY AUTOINCREMENT," +
            "   `graph_id`  INTEGER NOT NULL, " + 
            "   `data`      TEXT NOT NULL, " + 
            "   FOREIGN KEY(`graph_id`) REFERENCES `graph`(`graph_id`) " + 
            "       ON DELETE CASCADE " + 
            "       ON UPDATE NO ACTION " + 
            ")";
    private static final String TBL_EDGE_CREATE = 
            "CREATE TABLE IF NOT EXISTS `edge` (" +
            "	`vertex_from_id` INTEGER NOT NULL," +
            "	`vertex_to_id`	 INTEGER NOT NULL," +
            "	`weight`	 INTEGER NOT NULL DEFAULT 1," +
            "   PRIMARY KEY(`vertex_from_id`,`vertex_to_id`) " + 
            "	FOREIGN KEY(`vertex_from_id`) REFERENCES `vertex`(`vertex_id`) " +
            "	FOREIGN KEY(`vertex_to_id`) REFERENCES `vertex`(`vertex_id`) " + 
            "       ON DELETE CASCADE " +
            "       ON UPDATE NO ACTION " + 
            ")";
    
    /**
     * Constructor
     * 
     * @param dbpath the SQLite database path
     * 
     * @throws SQLException in case of a connection error
     */
    public GraphSQLiteWriter(String dbpath) throws SQLException { 
        super(dbpath);
    }
    
    /**
     * Creates a table.
     * 
     * @param createCommand the SQL create command
     * 
     * @throws SQLException in case of an error
     */
    private void createTable(String createCommand) throws SQLException { 
        Statement stmt = null;
        try { 
            stmt = conn.createStatement();
            stmt.executeUpdate(createCommand);
        }
        finally { 
            if (stmt != null) {
                try { stmt.close(); } catch (SQLException e) { }
            }
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void createTables() throws SQLException { 
        if (!tableExists(TBL_GRAPH))
            createTable(TBL_GRAPH_CREATE);
        if (!tableExists(TBL_VERTEX))
            createTable(TBL_VERTEX_CREATE);
        if (!tableExists(TBL_EDGE))
            createTable(TBL_EDGE_CREATE);
    }
    
    /**
     * Drops a table.
     * 
     * @param tblName the table name
     * 
     * @throws SQLException in case of an error
     */
    private void dropTable(String tblName) throws SQLException { 
        Statement stmt = null;
        try { 
            // SQLite doesn't support prepared statements that use the DROP
            // command with variable table names.
            // Therefore, the DROP command has to be manually constructed
            // Since the names of the tables are constants and defined in the
            // SQLManager class, there is no risk of SQL injection.
            stmt = conn.createStatement();
            String dropTblCommand = "DROP TABLE `" + tblName + "`";
            stmt.executeUpdate(dropTblCommand);
        }
        finally { 
            if (stmt != null) { 
                try { stmt.close(); } catch (SQLException e) { }
            }
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void dropTables() throws SQLException { 
        if (tableExists(TBL_EDGE))
            dropTable(TBL_EDGE);
        if (tableExists(TBL_VERTEX))
            dropTable(TBL_VERTEX);
        if (tableExists(TBL_GRAPH))
            dropTable(TBL_GRAPH);
    }
    
    /**
     * Inserts the graph entry into the TBL_GRAPH table.
     * 
     * @param graph the graph
     * 
     * @return the new graph entry's id
     * 
     * @throws SQLException in case of an error
     */
    private int insertGraph(Graph<V> graph) throws SQLException { 
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try { 
            // Insert the graph entry
            stmt = conn.prepareStatement(
                    "INSERT INTO `" + TBL_GRAPH + "`" + 
                    "(`type`,`name`) " + 
                    "VALUES(?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1,GraphType.getGraphTypeString(graph));
            stmt.setString(2,graph.getName());
            stmt.executeUpdate();
            // Get the last generated id
            rs = stmt.getGeneratedKeys();
            Integer lastId = null;
            if (rs.next())
                lastId = rs.getInt(1);
            if (lastId == null)
                throw new SQLException("Could not retrieve graph's id");
            return lastId;
        }
        finally { 
            if (stmt != null) { 
                try { stmt.close(); } catch (SQLException e) { }
            }
            if (rs != null) { 
                try { rs.close(); } catch (SQLException e) { }
            }
        }
    }
    
    /**
     * Inserts the vertex entries into the TBL_VERTEX table.
     * 
     * @param graph the graph
     * @param graphId the graph id (primary key)
     * 
     * @return a vertex index, where for each vertex we store the corresponding primary key
     * 
     * @throws SQLException in case of an error
     */
    private Map<Vertex<V>,Integer> insertVertices(Graph<V> graph, int graphId) throws SQLException { 
        // A vertex index where the id of each vertex is stored
        Map<Vertex<V>,Integer> vertexIdx = new HashMap<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        JSONConverter<V> jsonConverter = new JSONConverter<>();
        try { 
            stmt = conn.prepareStatement(
                    "INSERT INTO `" + TBL_VERTEX + "`" + 
                    "(`graph_id`,`data`) " + 
                    "VALUES(?,?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            for (Vertex<V> vertex : graph.getVertices()) { 
                // Insert the vertex entry
                stmt.setInt(1,graphId);
                stmt.setString(2,jsonConverter.toJson(vertex.getData()));
                stmt.executeUpdate();
                // Get the last generated id
                rs = stmt.getGeneratedKeys();
                Integer lastId = null;
                if (rs.next())
                    lastId = rs.getInt(1);
                if (lastId == null)
                    throw new SQLException("Could not retrieve vertex's id");
                // Insert a new entry in the vertex index
                vertexIdx.put(vertex,lastId);
            }
        }
        finally { 
            if (stmt != null) { 
                try { stmt.close(); } catch (SQLException e) { }
            }
            if (rs != null) { 
                try { rs.close(); } catch (SQLException e) { }
            }
        }
        return vertexIdx;
    }
    
    /**
     * Inserts the edge entries into the TBL_EDGE table.
     * 
     * @param vertexIdx the vertex index
     * 
     * @throws SQLException in case of an error
     */
    private void insertEdges(Map<Vertex<V>,Integer> vertexIdx) throws SQLException { 
        PreparedStatement stmt = null;
        try { 
            stmt = conn.prepareStatement(
                    "INSERT INTO `" + TBL_EDGE + "`" + 
                    "(`vertex_from_id`,`vertex_to_id`,`weight`) " + 
                    "VALUES(?,?,?)"
            );
            // For each entry in the index
            for (Map.Entry<Vertex<V>,Integer> entry : vertexIdx.entrySet()) { 
                // Get the vertex and the corresponding id
                Vertex<V> vertex = entry.getKey();
                int vertexFromId = entry.getValue();
                // For each outgoing edge of the vertex
                for (Edge<Vertex<V>> edge : vertex.getEdges()) { 
                    // Get the destination edge's id
                    int vertexToId = vertexIdx.get(edge.getVertex());
                    // Get the weight of this particular edge
                    int weight = (edge instanceof WeightedEdge) ? 
                                    ((WeightedEdge) edge).getWeight() : 1;
                    // Set the parameter values and execute the statement
                    stmt.setInt(1,vertexFromId);
                    stmt.setInt(2,vertexToId);
                    stmt.setInt(3,weight);
                    stmt.executeUpdate();
                }
            }
        }
        finally { 
            if (stmt != null) { 
                try { stmt.close(); } catch (SQLException e) { }
            }
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void write(Graph<V> graph) throws GraphIOException { 
        try { 
            // Create the tables if they don't exist
            createTables();
            // The graph has to have a custom name
            if (!graph.hasName())
                throw new GraphIOException("The graph has to have a custom name");
        }
        catch (SQLException e) { 
            throw new GraphIOException(e.getMessage());
        }
        
        // We need to store the old auto commit value as we will use a transaction
        Boolean oldAutoCommit = null;
        Boolean transactionActive = false;
        try { 
            // Get the old auto commit value
            oldAutoCommit = conn.getAutoCommit();
            // Start a transaction
            conn.setAutoCommit(false);
            transactionActive = true;
            // Delete the graph data, if it already exists
            deleteGraph(graph);
            // Insert the graph
            int graphId = insertGraph(graph);
            // Insert the vertices
            Map<Vertex<V>,Integer> vertexIdx = insertVertices(graph,graphId);
            // Insert the edges
            insertEdges(vertexIdx);
            // Commit the transaction
            conn.commit();
        }
        catch (SQLException e) { 
            // Rollback the transaction
            if (transactionActive) { 
                try { conn.rollback(); } catch (SQLException ex) { }
            }
            // Throw again the exception
            throw new GraphIOException(e.getMessage());
        }
        finally { 
            if (oldAutoCommit != null) { 
                try { conn.setAutoCommit(oldAutoCommit); } catch (SQLException e) { }
            }
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteGraph(Graph<V> graph) throws SQLException { 
        PreparedStatement stmt = null;
        try { 
            // SQLite doesn't support prepared statements where table's name
            // is a parameter
            // The graph's deletion will cascade to its vertices and edges
            stmt = conn.prepareStatement("DELETE FROM `" + TBL_GRAPH + "` " + 
                                         "WHERE  name = ?");
            stmt.setString(1,graph.getName());
            stmt.executeUpdate();
        }
        finally { 
            if (stmt != null) { 
                try { stmt.close(); } catch (SQLException e) { }
            }
        }
    }
    
}
