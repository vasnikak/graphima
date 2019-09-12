/**
 * Copyright (C) 2019, by Vasileios Nikakis
 *
 * graphima: yet another Java graph-theory library
 */
package com.sitienda.graphima.alg;

import com.sitienda.graphima.DirectedWeightedGraph;
import com.sitienda.graphima.exceptions.VertexNotInGraphException;
import com.sitienda.graphima.path.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

/**
 * Dijkstra shortest path algorithm tests.
 * 
 * @author Vasileios Nikakis
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DijkstraShortestPathTest {
    
    /**
     * The graph
     */
    private DirectedWeightedGraph<String> graph;
    
    public DijkstraShortestPathTest() {
        
    }
    
    /**
     * Init test data.
     */
    @BeforeAll
    public void init() { 
        graph = new DirectedWeightedGraph<>();
        graph.addVertices(Arrays.asList("A","B","C","D","E","F"));
        graph.addEdge("A","B",4)
             .addEdge("A","C",2)
             .addEdge("B","C",3)
             .addEdge("B","D",2)
             .addEdge("B","E",3)
             .addEdge("C","B",1)
             .addEdge("C","D",4)
             .addEdge("C","E",5)
             .addEdge("E","D",1);
    }

    /**
     * Test of findShortestPaths method, of class DijkstraShortestPath.
     */
    @Test
    public void testFindShortestPaths1() {
        // Execute test
        try { 
            DijkstraShortestPath<String> dij = new DijkstraShortestPath(graph);
            Map<String,Path> paths = dij.findShortestPaths("A");
            
            Map<String,Path> correctPaths = new HashMap<>();
            correctPaths.put("A", graph.getPath(Arrays.asList("A")));
            correctPaths.put("B", graph.getPath(Arrays.asList("A","C","B")));
            correctPaths.put("C", graph.getPath(Arrays.asList("A","C")));
            correctPaths.put("D", graph.getPath(Arrays.asList("A","C","B","D")));
            correctPaths.put("E", graph.getPath(Arrays.asList("A","C","B","E")));
            correctPaths.put("F", graph.getPath(Arrays.asList()));
            
            for (Map.Entry<String,Path> entry : paths.entrySet()) { 
                if (!entry.getValue().equals(correctPaths.get(entry.getKey())))
                    fail("Path for destination node " + entry.getKey() + " is not correct " + 
                         (correctPaths.get(entry.getKey())) + " insteaf of " + entry.getValue());
            }
        }
        catch (VertexNotInGraphException e) { 
            fail(e.getMessage());
        }
    }
    
}
