/*
 * Copyright (C) 2019, Vasileios Nikakis
 *
 * graphima : yet another Java graph-theory library
 */
package com.sitienda.graphima;

/**
 * A directed weighted graph class.
 *
 * @param <V> the type of the objects that are encapsulated in each vertex
 *
 * @author Vasileios Nikakis
 */
public class DirectedWeightedGraph<V> extends WeightedGraph<V> {
    
    /**
     * Creates a directed graph with a default name.
     */
    public DirectedWeightedGraph() { 
        
    }
    
    /**
     * Creates a named directed graph.
     * 
     * @param name the graph's name.
     */
    public DirectedWeightedGraph(String name) { 
        super(name);
    }
    
    /**
     * Creates a directed weighted graph, based on another graph, 
     * with a default name.
     * 
     * @param graph the graph to be copied.
     */
    public DirectedWeightedGraph(Graph<V> graph) {
        super(graph);
    }

    /**
     * Creates a named directed weighted graph, based on another graph.
     * @param name the graph's name.
     * @param graph the graph to be copied.
     */
    public DirectedWeightedGraph(String name, Graph<V> graph) {
        super(name, graph);
    } 
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Graph<V> addEdge(V vertexData1, V vertexData2) { 
        return addEdge(vertexData1,vertexData2,DEFAULT_WEIGHT);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WeightedGraph<V> addEdge(V vertexData1, V vertexData2, int weight) { 
        Vertex<V> vertex1 = getVertexWithData(vertexData1);
        if (vertex1 == null)
            return this;
        Vertex<V> vertex2 = getVertexWithData(vertexData2);
        if (vertex2 == null)
            return this;
        vertex1.addEdge(new WeightedEdge<>(vertex2,weight));
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
