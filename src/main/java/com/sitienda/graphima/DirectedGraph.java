/*
 * Copyright (C) 2019, by Vasileios Nikakis
 *
 * graphima : yet another Java graph-theory library
 */
package com.sitienda.graphima;

/**
 * A directed graph class.
 *
 * @param <V> the type of the objects that are encapsulated in each vertex
 *
 * @author Vasileios Nikakis
 */
public class DirectedGraph<V> extends Graph<V> {
    
    /**
     * Creates a directed graph with a default name.
     */
    public DirectedGraph() { 
        
    }
    
    /**
     * Creates a named directed graph.
     * @param name the graph's name.
     */
    public DirectedGraph(String name) { 
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
        vertex1.addEdge(new Edge<>(vertex2));
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
        vertex1.removeEdgeWith(vertex2);
        return this;
    }
    
}
