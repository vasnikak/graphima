/**
 * Copyright (C) 2019, by Vasileios Nikakis
 *
 * graphima: yet another Java graph-theory library
 */
package com.sitienda.graphima.alg;

import com.sitienda.graphima.Graph;
import com.sitienda.graphima.Maze;
import com.sitienda.graphima.MazeCell;
import com.sitienda.graphima.exceptions.VertexNotInGraphException;
import com.sitienda.graphima.path.Path;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

/**
 * BFS shortest path algorithm tests.
 * 
 * @author Vasileios Nikakis
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BFSShortestPathTest {
    
    /**
     * The maze
     */
    private Maze maze;
    /**
     * The graph
     */
    private Graph<MazeCell> graph;
    
    public BFSShortestPathTest() {
        
    }

    /**
     * Init test data.
     */
    @BeforeAll
    public void init() { 
        // Test Data
        int[][] mazeData = {
            {0,0,0,0,0,0,0,0,0,1},
            {0,1,1,0,0,0,0,0,1,0},
            {0,1,0,0,0,0,0,0,0,0},
            {0,0,0,1,0,0,1,0,0,0},
            {0,0,1,0,0,0,0,0,0,1},
            {0,0,1,0,0,1,0,0,0,0},
            {0,0,0,0,1,0,0,0,1,1},
            {1,1,0,0,1,0,1,1,0,0},
            {0,0,0,0,0,1,0,0,0,0},
            {0,0,0,1,0,0,0,0,0,0}
        };
        maze = new Maze(mazeData);
        graph = maze.generateGraph("Maze");
    }
    
    /**
     * Test of findShortestPath method, of class BFSShortestPath.
     */
    @Test
    public void testFindShortestPath1() {
        // Test data
        MazeCell start = maze.getCell(0,0);
        MazeCell end = maze.getCell(9,9);
        
        // Execute test
        try { 
            BFSShortestPath<MazeCell> bfs = new BFSShortestPath<>(graph);
            Path path = bfs.findShortestPath(start,end);
            AlgorithmExecutionStats stats = bfs.getExecStats();
            assertEquals(stats.getNodesVisitedNum(),78);
            assertEquals(((FindPathAlgorithmExecutionStats) stats).getPathLength(),19);
        }
        catch (VertexNotInGraphException e) { 
            fail(e.getMessage());
        }
    }
    
    /**
     * Test of findShortestPath method, of class BFSShortestPath.
     */
    @Test
    public void testFindShortestPath2() {
        // Test data
        MazeCell start = maze.getCell(0,0);
        MazeCell end = maze.getCell(0,9);
        
        // Execute test
        try { 
            BFSShortestPath<MazeCell> bfs = new BFSShortestPath<>(graph);
            Path path = bfs.findShortestPath(start,end);
            AlgorithmExecutionStats stats = bfs.getExecStats();
            assertEquals(((FindPathAlgorithmExecutionStats) stats).wasSolutionFound(),false);
            assertEquals(stats.getNodesVisitedNum(),79);
        }
        catch (VertexNotInGraphException e) { 
            fail(e.getMessage());
        }
    }
    
}
