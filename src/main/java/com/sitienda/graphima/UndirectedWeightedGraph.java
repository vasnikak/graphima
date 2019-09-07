/**
 * Copyright (C) 2019, by Vasileios Nikakis
 *
 * graphima: yet another Java graph-theory library
 */
package com.sitienda.graphima;

/**
 * An undirected weighted graph class.
 *
 * @param <V> the type of the objects that are encapsulated in each vertex
 *
 * @author Vasileios Nikakis
 */
public class UndirectedWeightedGraph<V> extends WeightedGraph<V> {
    
    /**
     * Creates an undirected graph with a default name.
     */
    public UndirectedWeightedGraph() { 
        
    }

    /**
     * Creates a named undirected graph.
     * @param name the graph's name.
     */
    public UndirectedWeightedGraph(String name) { 
        super(name);
    }

    /**
     * Creates a undirected weighted graph, based on another graph, 
     * with a default name.
     * @param graph the graph to be copied.
     */
    public UndirectedWeightedGraph(Graph<V> graph) {
        super(graph);
    }

    /**
     * Creates a named undirected weighted graph, based on another graph.
     * @param name the graph's name.
     * @param graph the graph to be copied.
     */
    public UndirectedWeightedGraph(String name, Graph<V> graph) {
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
        // Two edges have to be added
        vertex1.addEdge(new WeightedEdge<>(vertex2,weight));
        vertex2.addEdge(new WeightedEdge<>(vertex1,weight));
        return this;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Graph<V> removeEdge(V vertexData1, V vertexData2) { 
        Vertex<V> vertex1 = getVertexWithData(vertexData1);
        if (vertex1 == null)
            return null;
        Vertex<V> vertex2 = getVertexWithData(vertexData2);
        if (vertex2 == null)
            return null;
        // Both edges have to be removed
        vertex1.removeEdgeWith(vertex2);
        vertex2.removeEdgeWith(vertex1);
        return this;
    }
    
}
