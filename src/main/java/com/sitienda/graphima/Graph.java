/**
 * Copyright (C) 2019, by Vasileios Nikakis
 *
 * graphima: yet another Java graph-theory library
 */
package com.sitienda.graphima;

import com.sitienda.graphima.path.Path;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Base class for each graph class.
 * Each graph has a name and a set of vertices.
 *
 * @param <V> the type of the objects that are encapsulated in each vertex
 *
 * @author Vasileios Nikakis
 */
public abstract class Graph<V> {
    
    /**
     * Default graph name.
     */
    public static final String DEFAULT_NAME = "Unnamed graph";
    
    /**
     * The label of the graph.
     */
    private String name;
    /**
     * The set of vertices.
     */
    protected final HashSet<Vertex<V>> vertices;
    
    /**
     * Creates a graph with a default name.
     */
    public Graph() { 
        name = DEFAULT_NAME;
        vertices = new HashSet<>();
    }
    
    /**
     * Creates a named graph.
     * 
     * @param name the graph's name
     */
    public Graph(String name) { 
        this.name = name;
        vertices = new HashSet<>();
    }
    
    /**
     * 
     * @return the graph's name
     */
    public String getName() { 
        return name;
    }
    
    /**
     * 
     * @param name the graph's name
     */
    public void setName(String name) { 
        this.name = name;
    }
    
    /**
     * 
     * @return the graph's set of vertices.
     */
    public HashSet<Vertex<V>> getVertices() { 
        return vertices;
    }
    
    /**
     * Returns the number of vertices of the current graph.
     * 
     * @return The number of vertices of the current graph.
     */
    public int size() { 
        return vertices.size();
    }
    
    /**
     * Returns the number of edges of the current graph.
     * 
     * @return The number of edges of the current graph
     */
    public int getEdgesSize() { 
        int edgeCount = 0;
        for (Vertex vertex : vertices)
            edgeCount += vertex.getNeighborsSize();
        return edgeCount;
    }
    
    /**
     * Checks if the graph contains a specific vertex.
     * 
     * @param vertex the specific vertex
     * 
     * @return true or false
     */
    public boolean contains(Vertex<V> vertex) { 
        return vertices.contains(vertex);
    }
    
    /**
     * Returns the vertex with the specific data.
     * If such a vertex does not exist, it returns null.
     * 
     * @param vertexData the specific data
     * 
     * @return the corresponding vertex or null
     */
    public Vertex<V> getVertexWithData(V vertexData) { 
        for (Vertex<V> vertex : vertices) { 
            if (vertex.contains(vertexData))
                return vertex;
        }
        return null;
    }
    
    /**
     * Checks if the graph contains a vertex with specific data.
     * 
     * @param vertexData the data to be checked
     * 
     * @return true or false
     */
    public boolean contains(V vertexData) { 
        return (getVertexWithData(vertexData) != null);
    }
    
    /**
     * Adds a new vertex to the graph.
     * 
     * @param vertexData the vertex's data
     * 
     * @return the graph
     */
    public Graph<V> addVertex(V vertexData) { 
        synchronized(vertices) { 
            vertices.add(new Vertex<>(vertexData));
        }
        return this;
    }
    
    /**
     * Adds a set of vertices to the graph.
     * 
     * @param verticesData the data of the vertices
     * 
     * @return the graph
     */
    public Graph<V> addVertices(Collection<V> verticesData) { 
        for (V vertexData: verticesData)
            addVertex(vertexData);
        return this;
    }
    
    /**
     * Returns a list with the vertices that correspond to the list of 
     * vertex data objects that is supplied.
     * 
     * @param vertexData a list with the vertex data objects
     * 
     * @return the corresponding vertices
     */
    protected List<Vertex<V>> findVertices(List<V> vertexData) { 
        List<Vertex<V>> myVertices = new LinkedList<>();
        for (V data : vertexData)
            myVertices.add(getVertexWithData(data));
        return myVertices;
    }
    
    /**
     * Checks if two vertices are connected, by supplying the corresponding
     * vertex data object.
     * 
     * @param vertexData1 the data object of the first vertex
     * @param vertexData2 the data object of the second vertex
     * 
     * @return true or false according to if the two vertices are connected
     */
    public boolean hasEdge(V vertexData1, V vertexData2) { 
        Vertex<V> vertex1 = getVertexWithData(vertexData1);
        if (vertex1 == null)
            return false;
        Vertex<V> vertex2 = getVertexWithData(vertexData2);
        if (vertex2 == null)
            return false;
        return vertex1.hasEdgeWith(vertex2);
    }
    
    /**
     * Adds an edge between two vertices by supplying the corresponding vertices.
     * 
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * 
     * @return the graph
     */
    public abstract Graph<V> addEdge(Vertex<V> vertex1, Vertex<V> vertex2);
    
    /**
     * Adds an edge between two vertices by supplying the corresponding data objects.
     * 
     * @param vertexData1 the data object of the first vertex
     * @param vertexData2 the data object of the second vertex
     * 
     * @return the graph
     */
    public abstract Graph<V> addEdge(V vertexData1, V vertexData2);
    
    /**
     * Removes an edge between two vertices by supplying the corresponding data objects.
     * 
     * @param vertexData1 the data object of the first vertex
     * @param vertexData2 the data object of the second vertex
     * 
     * @return the graph
     */
    public abstract Graph<V> removeEdge(V vertexData1, V vertexData2);
    
    /**
     * Checks if a path exists in the graph.
     * 
     * @param pathNodes the path expressed as a list of vertices
     * 
     * @return true or false according to if the path exists
     */
    public boolean pathExists(List<Vertex<V>> pathNodes) { 
        // For each node in the path
        for (int i = 0; i < pathNodes.size(); i++) { 
            Vertex<V> currentNode = (Vertex<V>) pathNodes.get(i);
            // Check if the node actually exist in the current graph
            if (!vertices.contains(currentNode))
                return false;
            // If it is not the last node in the path
            if (i < pathNodes.size()-1) { 
                // There has to be a connection with the next node in the path
                Vertex<V> nextNode = (Vertex<V>) pathNodes.get(i+1);
                if (!currentNode.hasEdgeWith(nextNode))
                    return false;
            }
        }
        return true;
    }
    
    /**
     * Checks if a path exists in the graph.
     * 
     * @param path the path object
     * 
     * @return true or false
     */
    public boolean pathExists(Path path) { 
        List<Vertex<V>> pathNodes = new LinkedList(path.getPath());
        return pathExists(pathNodes);
    }
    
    /**
     * Removes all vertices (and the corresponding edges) from the graph.
     * The graph will remain empty after this action.
     */
    public void clear() { 
        synchronized(vertices) { 
            vertices.clear();
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() { 
        int vertexNum = vertices.size();
        // Graph's name and number of vertices
        String str = "Graph " + name + " (" + vertexNum + " vertices)\n\n";
        // The data of each vertex
        int vertexCount = 0;
        for (Vertex<V> vertex : vertices) { 
            str += vertex + "\n";
            // The data of each edge
            HashSet<Edge> edges = vertex.getEdges();
            for (Edge edge : vertex.getEdges())
                str += "   " + edge + "\n";
            if (++vertexCount < vertexNum)
                str += "\n";
        }
        return str;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.vertices);
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Graph<?> other = (Graph<?>) obj;
        if (!Objects.equals(this.vertices, other.vertices)) {
            return false;
        }
        return true;
    }
    
}
