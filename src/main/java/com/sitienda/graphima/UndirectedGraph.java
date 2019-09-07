/*
 * Copyright (C) 2019, Vasileios Nikakis
 *
 * graphima : yet another Java graph-theory library
 */
package com.sitienda.graphima;

/**
 * An undirected graph class.
 *
 * @param <V> the type of the objects that are encapsulated in each vertex
 *
 * @author Vasileios Nikakis
 */
public class UndirectedGraph<V> extends Graph<V> {
    
    /**
     * Creates an undirected graph with a default name.
     */
    public UndirectedGraph() { 
        
    }
    
    /**
     * Creates a named undirected graph.
     * @param name the graph's name.
     */
    public UndirectedGraph(String name) { 
        super(name);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Graph<V> addEdge(V vertexData1, V vertexData2) { 
        Vertex<V> vertex1 = getVertexWithData(vertexData1);
        if (vertex1 == null)
            return this;
        Vertex<V> vertex2 = getVertexWithData(vertexData2);
        if (vertex2 == null)
            return this;
        // Two edges have to be added
        vertex1.addEdge(new Edge<>(vertex2));
        vertex2.addEdge(new Edge<>(vertex1));
        return this;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Graph<V> removeEdge(V vertexData1, V vertexData2) { 
        Vertex<V> vertex1 = getVertexWithData(vertexData1);
        if (vertex1 == null)
            return this;
        Vertex<V> vertex2 = getVertexWithData(vertexData2);
        if (vertex2 == null)
            return this;
        // Both edges have to be removed
        vertex1.removeEdgeWith(vertex2);
        vertex2.removeEdgeWith(vertex1);
        return this;
    }
    
}
