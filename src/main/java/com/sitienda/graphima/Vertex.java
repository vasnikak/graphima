/**
 * Copyright (C) 2019, by Vasileios Nikakis
 *
 * graphima: yet another Java graph-theory library
 */
package com.sitienda.graphima;

import java.util.HashSet;
import java.util.Objects;

/**
 * The vertex of a graph.
 * Each vertex contains a set with all the outgoing edges.
 *
 * @param <T> the type of the objects that are encapsulated in each vertex
 *
 * @author Vasileios Nikakis
 */
public class Vertex<T> {
    /**
     * The contained object.
     */
    private final T data;
    /**
     * The edges of the vertex.
     */
    private final HashSet<Edge> edges;
    
    /**
     * 
     * @param data the contained object. 
     */
    public Vertex(T data) { 
        this.data = data;
        edges = new HashSet<>();
    }
    
    /**
     * 
     * @return the contained object.
     */
    public T getData() { 
        return data;
    }
    
    /**
     * 
     * @return the edges of the vertex.
     */
    public HashSet<Edge> getEdges() { 
        return edges;
    }
    
    /**
     * Checks if the contained data object is the same with the argument.
     * @param data the item that needs to be checked.
     * @return true or false.
     */
    public boolean contains(T data) { 
        return this.data.equals(data);
    }
    
    /**
     * Checks if the current vertex has an edge with another vertex.
     * @param vertex the vertex that needs to be checked.
     * @return true or false.
     */
    public boolean hasEdgeWith(Vertex vertex) { 
        for (Edge edge : edges) { 
            if (edge.containsVertex(vertex))
                return true;
        }
        return false;
    }
    
    /**
     * Checks if the current vertex has a specific edge.
     * @param edge the edge that needs to be checked.
     * @return true or false.
     */
    public boolean hasEdge(Edge edge) { 
        return hasEdgeWith(edge.getVertex());
    }
    
    /**
     * Returns the corresponding edge with a specific edge or null if this
     * edge doesn't exist.
     * @param vertex the edge to be found.
     * @return true or false.
     */
    public Edge getEdgeWith(Vertex vertex) { 
        for (Edge edge: edges) { 
            if (edge.containsVertex(vertex))
                return edge;
        }
        return null;
    }
    
    /**
     * Adds an edge to the current vertex's set of edges.
     * @param edge the edge to be added.
     * @return true if the edge was added or false if the connection already exists.
     */
    public boolean addEdge(Edge edge) { 
        synchronized(edges) { 
            return edges.add(edge);
        }
    }
    
    /**
     * Removes an edge from the current vertex's set of edges.
     * @param edge the edge to be removed.
     * @return true if the edge was in the edge, false otherwise.
     */
    public boolean removeEdge(Edge edge) { 
        synchronized(edges) { 
            return edges.remove(edge);
        }
    }
    
    /**
     * Removes an edge with a specific vertex.
     * @param vertex the vertex that the corresponding edge will be removed.
     * @return true if the connection was present, false otherwise.
     */
    public boolean removeEdgeWith(Vertex vertex) { 
        Edge edge = getEdgeWith(vertex);
        if (edge != null) { 
            removeEdge(edge);
            return true;
        } else
            return false;
    }
    
    /**
     * Returns a HashSet that contains the neighbor vertices.
     * @return the neighbor vertices.
     */
    public HashSet<Vertex<T>> getNeighbors() { 
        HashSet<Vertex<T>> neighbors = new HashSet<>();
        for (Edge edge : edges)
            neighbors.add((Vertex<T>) edge.getVertex());
        return neighbors;
    }
    
    /**
     * Returns the number of neighbor vertices.
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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        final Vertex<T> other = (Vertex<T>) obj;
        return data.equals(other.data);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.data);
        return hash;
    }
    
}
