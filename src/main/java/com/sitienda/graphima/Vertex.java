/**
 * Copyright (C) 2019, by Vasileios Nikakis
 *
 * graphima: yet another Java graph-theory library
 */
package com.sitienda.graphima;

import com.sitienda.graphima.exceptions.VertexNullPointerException;
import java.util.HashSet;
import java.util.Objects;

/**
 * The vertex of a graph.
 * Each vertex contains a set with all the outgoing edges.
 *
 * @param <V> the type of the objects that are encapsulated in each vertex
 *
 * @author Vasileios Nikakis
 */
public class Vertex<V> {
    /**
     * The contained object.
     */
    private final V data;
    /**
     * The edges of the vertex.
     */
    private final HashSet<Edge<Vertex<V>>> edges;
    
    /**
     * Constructor
     * 
     * @param data the contained object. 
     * 
     * @throws VertexNullPointerException if the data is null
     */
    public Vertex(V data) throws VertexNullPointerException { 
        if (data == null)
            throw new VertexNullPointerException("The vertex cannot contain null as data");
        this.data = data;
        edges = new HashSet<>();
    }
    
    /**
     * 
     * @return the contained object.
     */
    public V getData() { 
        return data;
    }
    
    /**
     * 
     * @return the edges of the vertex.
     */
    public HashSet<Edge<Vertex<V>>> getEdges() { 
        return edges;
    }
    
    /**
     * Checks if the contained data object is the same with the argument.
     * 
     * @param data the item that needs to be checked.
     * 
     * @return true or false.
     */
    public boolean contains(V data) { 
        return this.data.equals(data);
    }
    
    /**
     * Checks if the current vertex has an edge with another vertex.
     * 
     * @param vertex the vertex that needs to be checked.
     * 
     * @return true or false.
     */
    public boolean hasEdgeWith(Vertex<V> vertex) { 
        for (Edge<Vertex<V>> edge : edges) { 
            if (edge.containsVertex(vertex))
                return true;
        }
        return false;
    }
    
    /**
     * Checks if the current vertex has a specific edge.
     * 
     * @param edge the edge that needs to be checked.
     * 
     * @return true or false.
     */
    public boolean hasEdge(Edge<Vertex<V>> edge) { 
        return hasEdgeWith(edge.getVertex());
    }
    
    /**
     * Returns the corresponding edge with a specific edge or null if this
     * edge doesn't exist.
     * 
     * @param vertex the edge to be found.
     * 
     * @return true or false.
     */
    public Edge<Vertex<V>> getEdgeWith(Vertex<V> vertex) { 
        for (Edge<Vertex<V>> edge: edges) { 
            if (edge.containsVertex(vertex))
                return edge;
        }
        return null;
    }
    
    /**
     * Adds an edge to the current vertex's set of edges.
     * 
     * @param edge the edge to be added.
     * 
     * @return true if the edge was added or false if the connection already exists.
     */
    public boolean addEdge(Edge<Vertex<V>> edge) { 
        return edges.add(edge);
    }
    
    /**
     * Removes an edge from the current vertex's set of edges.
     * 
     * @param edge the edge to be removed.
     * 
     * @return true if the edge was in the edge, false otherwise.
     */
    public boolean removeEdge(Edge<Vertex<V>> edge) { 
        return edges.remove(edge);
    }
    
    /**
     * Removes an edge with a specific vertex.
     * 
     * @param vertex the vertex that the corresponding edge will be removed.
     * 
     * @return true if the connection was present, false otherwise.
     */
    public boolean removeEdgeWith(Vertex<V> vertex) { 
        Edge<Vertex<V>> edge = getEdgeWith(vertex);
        if (edge != null) { 
            removeEdge(edge);
            return true;
        } else
            return false;
    }
    
    /**
     * Returns a HashSet that contains the neighbor vertices.
     * 
     * @return the neighbor vertices.
     */
    public HashSet<Vertex<V>> getNeighbors() { 
        HashSet<Vertex<V>> neighbors = new HashSet<>();
        for (Edge<Vertex<V>> edge : edges)
            neighbors.add(edge.getVertex());
        return neighbors;
    }
    
    /**
     * Returns the number of neighbor vertices.
     * 
     * @return the number of neighbor vertices.
     */
    public int getNeighborsSize() { 
        return edges.size();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() { 
        return "Vertex<" + data + ">";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.data);
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
        final Vertex<?> other = (Vertex<?>) obj;
        if (!Objects.equals(this.data, other.data)) {
            return false;
        }
        return true;
    }
    
}
