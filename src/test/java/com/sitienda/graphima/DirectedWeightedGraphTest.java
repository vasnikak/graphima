/**
 * Copyright (C) 2019, by Vasileios Nikakis
 *
 * graphima: yet another Java graph-theory library
 */
package com.sitienda.graphima;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Vasileios Nikakis
 */
public class DirectedWeightedGraphTest {

    /**
     * Test of addEdge method, of class DirectedWeightedGraph.
     */
    @Test
    public void testAddEdge_GenericType_GenericType() {
        DirectedWeightedGraph<String> g = new DirectedWeightedGraph<>();
        g.addVertex("A")
         .addVertex("B")
         .addVertex("C");
        g.addEdge("A","B")
         .addEdge("A","B")
         .addEdge("A","D")
         .addEdge("A","C");
        assertEquals(g.getEdgesSize(),2);
    }

    /**
     * Test of addEdge method, of class DirectedWeightedGraph.
     */
    @Test
    public void testAddEdge_3args() {
        DirectedWeightedGraph<String> g = new DirectedWeightedGraph<>();
        g.addVertex("A")
         .addVertex("B")
         .addVertex("C");
        g.addEdge("A","B",10)
         .addEdge("A","B",10)
         .addEdge("A","D",10)
         .addEdge("A","C",10);
        assertEquals(g.getEdgesSize(),2);
    }

    /**
     * Test of removeEdge method, of class DirectedWeightedGraph.
     */
    @Test
    public void testRemoveEdge() {
        DirectedWeightedGraph<String> g = new DirectedWeightedGraph<>();
        g.addVertex("A")
         .addVertex("B")
         .addVertex("C");
        g.addEdge("A","B",10)
         .addEdge("A","C",10);
        g.removeEdge("A","B")
         .removeEdge("A","D");
        assertEquals(g.getEdgesSize(),1);
    }
    
}
