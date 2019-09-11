/**
 * Copyright (C) 2019, by Vasileios Nikakis
 *
 * graphima: yet another Java graph-theory library
 */
package com.sitienda.graphima.io;

import com.sitienda.graphima.Graph;
import com.sitienda.graphima.Vertex;
import com.sitienda.graphima.WeightedGraph;
import com.sitienda.graphima.exceptions.GraphDataMissingException;
import com.sitienda.graphima.exceptions.GraphIOException;
import com.sitienda.graphima.exceptions.VertexNotInGraphException;
import static com.sitienda.graphima.io.GraphSQLManager.TBL_GRAPH;
import static com.sitienda.graphima.io.GraphSQLManager.TBL_VERTEX;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Graph MySQL reader
 * 
 * @param <V> the type of objects that the graph contains
 * 
 * @author Vasileios Nikakis
 */
public class GraphMySQLReader<V> extends GraphMySQLManager implements GraphSQLReader<V> {
    
    /**
     * The graph's name
     */
    private final String graphName;
    
    /**
     * Constructor
     * 
     * @param host the DB host
     * @param port the DB port
     * @param dbname the DB name
     * @param user the username
     * @param pass the password
     * @param graphName the graph's name
     * 
     * @throws SQLException in case of an error
     */
    public GraphMySQLReader(String host, int port, String dbname, 
                            String user, String pass, String graphName) throws SQLException { 
        super(host,port,dbname,user,pass);
        this.graphName = graphName;
    }
    
    /**
     * 
     * @return the graph's name
     */
    public String getGraphName() { 
        return graphName;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean graphExists() throws SQLException { 
        return (getGraphId() != null);
    }
    
    /**
     * Retrieves the id (primary key) of the graph.
     * If the graph doesn't exist, returns null.
     * 
     * @return the id of the graph
     * 
     * @throws SQLException in case of an error
     */
    private Integer getGraphId() throws SQLException { 
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try { 
            stmt = conn.prepareStatement("SELECT `graph_id` " + 
                                         "FROM   `" + TBL_GRAPH + "` " + 
                                         "WHERE  `name` = ?");
            stmt.setString(1,graphName);
            rs = stmt.executeQuery();
            Integer graphId = null;
            if (rs.next())
                graphId = rs.getInt("graph_id");
            return graphId;
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
     * Retrieves the type of the graph.
     * 
     * @return the type of the graph
     * 
     * @throws SQLException in case of an error
     */
    private GraphType.Type getGraphType() throws SQLException { 
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try { 
            stmt = conn.prepareStatement("SELECT `type` " + 
                                         "FROM   `" + TBL_GRAPH + "` " + 
                                         "WHERE  `name` = ?");
            stmt.setString(1,graphName);
            rs = stmt.executeQuery();
            String strType = null;
            if (rs.next())
                strType = rs.getString("type");
            return GraphType.getGraphType(strType);
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
     * Reads the graph's vertices and returns a vertex index.
     * 
     * @param graph the graph
     * @param graphId the graph's id (primary key)
     * @param cls the class of the contained objects inside graph's vertices
     * 
     * @return the vertex index
     * 
     * @throws SQLException in case of an error
     */
    private Map<Integer,Vertex<V>> readVertices(Graph<V> graph, int graphId, Class<V> cls) throws SQLException { 
        // We need an index to store the id (primary key) of each vertex
        Map<Integer,Vertex<V>> vertexIdx = new HashMap<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        JSONConverter<V> jsonConverter = new JSONConverter<>();
        try { 
            stmt = conn.prepareStatement("SELECT * " + 
                                         "FROM   `" + TBL_VERTEX + "` " + 
                                         "WHERE  `graph_id` = ?");
            stmt.setInt(1,graphId);
            rs = stmt.executeQuery();
            while (rs.next()) { 
                // Get each vertex entry's data
                Integer vertexId = rs.getInt("vertex_id");
                V vertexData = JSONConverter.fromJson(rs.getString("data"), cls);
                // Insert the data to the graph
                graph.addVertex(vertexData);
                // Insert the data to the vertex index
                vertexIdx.put(vertexId,graph.getVertexWithData(vertexData));
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
     * Reads the graph's edges.
     * 
     * @param graph the graph
     * @param vertexIdx the vertex index
     * 
     * @throws SQLException in case of an error
     * @throws VertexNotInGraphException if there's an edge to a vertex that doesn't belong to the graph
     */
    private void readEdges(Graph<V> graph, Map<Integer,Vertex<V>> vertexIdx)throws SQLException, VertexNotInGraphException { 
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try { 
            stmt = conn.prepareStatement("SELECT * " + 
                                         "FROM   `edge` " + 
                                         "WHERE  `vertex_from_id` = ?");
            // For each vertex
            for (Map.Entry<Integer,Vertex<V>> entry : vertexIdx.entrySet()) { 
                int vertexFromId = entry.getKey();
                Vertex<V> vertexFrom = entry.getValue();
                // Get all its outgoing edges
                stmt.setInt(1,vertexFromId);
                rs = stmt.executeQuery();
                // For each outgoing edge
                while (rs.next()) { 
                    // Get the edge's data
                    int vertexToId = rs.getInt("vertex_to_id");
                    int weight = rs.getInt("weight");
                    // Get the destination vertex
                    Vertex<V> vertexTo = vertexIdx.get(vertexToId);
                    if (vertexTo == null)
                        throw new VertexNotInGraphException("The vertex is not in the graph");
                    // Add the edge to the graph
                    if (graph instanceof WeightedGraph)
                        ((WeightedGraph) graph).addEdge(vertexFrom,vertexTo,weight);
                    else
                        graph.addEdge(vertexFrom,vertexTo);
                }
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
    }
    
    /**
     * @{inheritDoc}
     */
    @Override
    public Graph<V> read(Class<V> cls) throws GraphIOException { 
        // The graph
        Graph<V> graph = null;
        try { 
            // Get graph's id
            Integer graphId = getGraphId();
            if (graphId == null)
                throw new GraphIOException("Graph " + graphName + " doesn't exist");
            // Get graph's type and create the correct instance
            GraphType.Type type = getGraphType();
            graph = GraphType.getInstance(type);
            // Set graph's name
            graph.setName(graphName);
            // Read graph's vertices
            Map<Integer,Vertex<V>> vertexIdx = readVertices(graph,graphId,cls);
            // Read graph's edges
            readEdges(graph,vertexIdx);
            // Return the graph
            return graph;
        }
        catch (SQLException | GraphDataMissingException | VertexNotInGraphException e) { 
            if (graph != null)
                graph.clear();
            // Throw a GraphIOException
            throw new GraphIOException(e.getMessage());
        }
    }
    
}
