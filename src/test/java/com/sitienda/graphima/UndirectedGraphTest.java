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
public class UndirectedGraphTest {

    /**
     * Test of addEdge method, of class UndirectedWeightedGraph.
     */
    @Test
    public void testAddEdge_GenericType_GenericType() {
        UndirectedGraph<String> g = new UndirectedGraph<>();
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
     * Test of removeEdge method, of class UndirectedWeightedGraph.
     */
    @Test
    public void testRemoveEdge() {
        UndirectedGraph<String> g = new UndirectedGraph<>();
        g.addVertex("A")
         .addVertex("B")
         .addVertex("C");
        g.addEdge("A","B")
         .addEdge("A","C");
        g.removeEdge("A","B")
         .removeEdge("A","D");
        assertEquals(g.getEdgesSize(),2);
    }
    
}
