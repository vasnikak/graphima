/**
 * Copyright (C) 2019, by Vasileios Nikakis
 *
 * graphima: yet another Java graph-theory library
 */
package com.sitienda.graphima.alg;

import com.sitienda.graphima.Graph;
import com.sitienda.graphima.Maze;
import com.sitienda.graphima.MazeCell;
import com.sitienda.graphima.alg.heuristics.HeuristicFunction;
import com.sitienda.graphima.exceptions.VertexNotInGraphException;
import com.sitienda.graphima.path.Path;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;

/**
 *
 * @author Vasileios Nikakis
 */
@TestInstance(Lifecycle.PER_CLASS)
public class AStarShortestPathTest {
    
    /**
     * The maze
     */
    private Maze maze;
    /**
     * The graph
     */
    private Graph<MazeCell> graph;
    /**
     * Start cell
     */
    private MazeCell start;
    /**
     * End cell
     */
    private MazeCell end;
    
    public AStarShortestPathTest() {
    }
    
    @BeforeAll
    public void init() {
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
        graph = maze.generateGraph();
        start = maze.getCell(0,0);
        end = maze.getCell(9,9);
    }
    
    @AfterAll
    public void tearDown() {
        maze = null;
        graph = null;
        start = null;
        end = null;
    }

    /**
     * Test of findShortestPath method, of class AStarShortestPath.
     */
    @Test
    public void testFindShortestPath_Object_Object() {
        try { 
            AStarShortestPath<MazeCell> astar = new AStarShortestPath<>(graph, new HeuristicFunction<MazeCell>() { 
                @Override
                public int h(MazeCell obj) {
                    return Math.abs(obj.getX() - end.getX()) + Math.abs(obj.getY() - end.getY());
                }
            });
            Path path = astar.findShortestPath(start,end);
            AlgorithmExecutionStats stats = astar.getExecStats();
            assertEquals(stats.getNodesVisitedNum(),69);
            assertEquals(((FindPathAlgorithmExecutionStats) stats).getPathLength(),19);
        }
        catch (VertexNotInGraphException e) { 
            fail(e.getMessage());
        }
    }
    
}
