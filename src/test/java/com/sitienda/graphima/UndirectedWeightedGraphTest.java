/*
 * Copyright (C) 2019, Vasileios Nikakis
 *
 * graphima : yet another Java graph-theory library
 */
package com.sitienda.graphima;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Vasileios Nikakis
 */
public class UndirectedWeightedGraphTest {

    /**
     * Test of addEdge method, of class UndirectedWeightedGraph.
     */
    @Test
    public void testAddEdge_GenericType_GenericType() {
        UndirectedWeightedGraph<String> g = new UndirectedWeightedGraph<>();
        g.addVertex("A")
         .addVertex("B")
         .addVertex("C");
        g.addEdge("A","B")
         .addEdge("A","B")
         .addEdge("A","D")
         .addEdge("A","C");
        assertEquals(g.getEdgesSize(),4);
    }

    /**
     * Test of addEdge method, of class UndirectedWeightedGraph.
     */
    @Test
    public void testAddEdge_3args() {
        UndirectedWeightedGraph<String> g = new UndirectedWeightedGraph<>();
        g.addVertex("A")
         .addVertex("B")
         .addVertex("C");
        g.addEdge("A","B",10)
         .addEdge("A","B",10)
         .addEdge("A","D",10)
         .addEdge("A","C",10);
        assertEquals(g.getEdgesSize(),4);
    }

    /**
     * Test of removeEdge method, of class UndirectedWeightedGraph.
     */
    @Test
    public void testRemoveEdge() {
        UndirectedWeightedGraph<String> g = new UndirectedWeightedGraph<>();
        g.addVertex("A")
         .addVertex("B")
         .addVertex("C");
        g.addEdge("A","B",10)
         .addEdge("A","C",10);
        g.removeEdge("A","B")
         .removeEdge("A","D");
        assertEquals(g.getEdgesSize(),2);
    }
    
}
