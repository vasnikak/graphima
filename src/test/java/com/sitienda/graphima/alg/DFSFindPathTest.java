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
 * DFS find path algorithm tests.
 * 
 * @author Vasileios Nikakis
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DFSFindPathTest {
    
    /**
     * The maze
     */
    private Maze maze;
    /**
     * The graph
     */
    private Graph<MazeCell> graph;
    
    public DFSFindPathTest() {
        
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
     * Test of findPath method, of class DFSFindPath.
     */
    @Test
    public void testFindPath1() {
        // Test data
        MazeCell start = maze.getCell(0,0);
        MazeCell end = maze.getCell(9,9);
        
        // Execute test
        try { 
            DFSFindPath<MazeCell> dfs = new DFSFindPath<>(graph);
            Path path = dfs.findPath(start,end);
            AlgorithmExecutionStats stats = dfs.getExecStats();
            assertEquals(stats.getNodesVisitedNum(),78);
            assertEquals(((FindPathAlgorithmExecutionStats) stats).getPathLength(),19);
        }
        catch (VertexNotInGraphException e) { 
            fail(e.getMessage());
        }
    }
    
    /**
     * Test of findPath method, of class DFSFindPath.
     */
    @Test
    public void testFindPath2() {
        // Test data
        MazeCell start = maze.getCell(0,0);
        MazeCell end = maze.getCell(0,9);
        
        // Execute test
        try { 
            DFSFindPath<MazeCell> dfs = new DFSFindPath<>(graph);
            Path path = dfs.findPath(start,end);
            AlgorithmExecutionStats stats = dfs.getExecStats();
            assertEquals(((FindPathAlgorithmExecutionStats) stats).wasSolutionFound(),false);
            assertEquals(stats.getNodesVisitedNum(),79);
        }
        catch (VertexNotInGraphException e) { 
            fail(e.getMessage());
        }
    }
    
}
