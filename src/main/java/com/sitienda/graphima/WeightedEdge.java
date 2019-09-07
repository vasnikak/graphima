/**
 * Copyright (C) 2019, by Vasileios Nikakis
 *
 * graphima: yet another Java graph-theory library
 */
package com.sitienda.graphima;

/**
 * An edge between two vertices, that holds as well a weight.
 * The weight is represented as an integer.
 *
 * @param <V> the vertex type
 *
 * @author Vasileios Nikakis
 */
public class WeightedEdge<V extends Vertex> extends Edge<V> {

    /**
     * The weight of the edge.
     */
    private int weight;
    
    /**
     * Constructor.
     * 
     * @param vertex the linked vertex.
     * @param weight the connection's weight.
     */
    public WeightedEdge(V vertex, int weight) { 
        super(vertex);
        this.weight = weight;
    }

    /**
     * 
     * @return the edge's weight.
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Synonym for {@link #getWeight()}.
     * @return the edge's cost.
     */
    public int getCost() { 
        return weight;
    }
    
    /**
     * 
     * @param weight the edge's weight.
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }
    
    /**
     * Synonym for {@link #setWeight(int)}.
     * @param cost the edge's cost.
     */
    public void setCost(int cost) { 
        weight = cost;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() { 
        return "WeighteEdge<" + vertex + ", weight: " + weight + ">";
    }
    
}
