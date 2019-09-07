/*
 * Copyright (C) 2019, Vasileios Nikakis
 *
 * graphima : yet another Java graph-theory library
 */
package com.sitienda.graphima;

import java.util.Objects;

/**
 * The edge between two vertices.
 * Each edge has a direction from a vertex A to a vertex B.
 * The edge has knowledge only of vertex B.
 *
 * @param <V> the vertex type
 *
 * @author Vasileios Nikakis
 */
public class Edge<V extends Vertex> {
    
    /**
     * The link to the vertex.
     */
    protected V vertex;
    
    /**
     * 
     * @param vertex the linked vertex.
     */
    public Edge(V vertex) { 
        this.vertex = vertex;
    }

    /**
     * 
     * @return the linked vertex.
     */
    public V getVertex() {
        return vertex;
    }

    /**
     * Checks if the current edge corresponds to a specific vertex.
     * @param vertex the vertex to be checked.
     * @return true or false.
     */
    public boolean containsVertex(V vertex) { 
        return this.vertex.equals(vertex);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() { 
        return "Edge<" + vertex + ">";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.vertex);
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
        final Edge<?> other = (Edge<?>) obj;
        if (!Objects.equals(this.vertex, other.vertex)) {
            return false;
        }
        return true;
    }
    
}
