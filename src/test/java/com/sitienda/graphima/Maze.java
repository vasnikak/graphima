/*
 * Copyright (C) 2019, Vasileios Nikakis
 *
 * graphima : yet another Java graph-theory library
 */
package com.sitienda.graphima;

import com.sitienda.graphima.UndirectedGraph;

/**
 *
 * @author Vasileios Nikakis
 */
public class Maze {
    
    private MazeCell[][] maze;
    
    public Maze(int rows, int columns) { 
        maze = new MazeCell[rows][columns];
        for (int i = 0; i < rows; i++) { 
            for (int j = 0; j < columns; j++)
                maze[i][j] = new MazeCell(i,j);
        }
    }
    
    public Maze(int[][] maze) { 
        int rows = maze.length;
        int cols = (maze.length > 0) ? maze[0].length : 0;
        this.maze = new MazeCell[rows][cols];
        for (int i = 0; i < rows; i++) { 
            for (int j = 0; j < cols; j++)
                this.maze[i][j] = new MazeCell(i,j,maze[i][j]);
        }
    }
    
    public int getRows() { 
        return maze.length;
    }
    
    public int getColumns() { 
        if (maze.length == 0)
            return 0;
        return maze[0].length;
    }
    
    public MazeCell getCell(int x, int y) { 
        return maze[x][y];
    }
    
    public void setCellValue(int x, int y, int value) { 
        maze[x][y].setValue(value);
    }
    
    public UndirectedGraph<MazeCell> generateGraph() { 
        UndirectedGraph<MazeCell> graph = new UndirectedGraph<>("Maze");
        for (int i = 0; i < maze.length; i++) { 
            for (int j = 0; j < maze[i].length; j++)
                graph.addVertex(maze[i][j]);
        }
        for (int i = 0; i < maze.length; i++) { 
            for (int j = 0; j < maze[i].length; j++) { 
                if (maze[i][j].isBlocked())
                    continue;
                if (i > 0 && maze[i-1][j].isFree())
                    graph.addEdge(maze[i][j],maze[i-1][j]);
                if (i < maze.length-1 && maze[i+1][j].isFree())
                    graph.addEdge(maze[i][j],maze[i+1][j]);
                if (j > 0 && maze[i][j-1].isFree())
                    graph.addEdge(maze[i][j],maze[i][j-1]);
                if (j < maze[i].length-1 && maze[i][j+1].isFree())
                    graph.addEdge(maze[i][j],maze[i][j+1]);
            }
        }
        return graph;
    }
    
}
