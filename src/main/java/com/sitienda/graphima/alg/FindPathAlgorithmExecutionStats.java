/**
 * Copyright (C) 2019, by Vasileios Nikakis
 *
 * graphima: yet another Java graph-theory library
 */
package com.sitienda.graphima.alg;

/**
 * Execution statistics for the execution of each path find graph algorithm.
 * 
 * @author Vasileios Nikakis
 */
public class FindPathAlgorithmExecutionStats extends AlgorithmExecutionStats {
    
    /**
     * Path's length (find path algorithms).
     */
    private int pathLength;
    /**
     * Solution found (find path algorithms).
     */
    private boolean solutionFound;
    
    /**
     * Constructor
     */
    public FindPathAlgorithmExecutionStats() { 
        
    }
    
    /**
     * Constructor
     * 
     * @param algorithmName algorithm's name
     */
    public FindPathAlgorithmExecutionStats(String algorithmName) { 
        super(algorithmName);
        reset();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() { 
        super.reset();
        pathLength = 0;
        solutionFound = false;
    }
    
    /**
     * 
     * @return path's length
     */
    public int getPathLength() {
        return pathLength;
    }

    /**
     * 
     * @param pathLength path's length
     */
    public void setPathLength(int pathLength) {
        this.pathLength = pathLength;
    }
    
    /**
     * 
     * @return true or false according to if the solution was found
     */
    public boolean wasSolutionFound() { 
        return solutionFound;
    }
    
    /**
     * 
     * @param solutionFound true or false according to if the solution was found
     */
    public void setSolutionFound(boolean solutionFound) { 
        this.solutionFound = solutionFound;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() { 
        String str = super.toString();
        str += "Solution was found: " + (solutionFound ? "Yes" : "No") + "\n";
        if (pathLength > 0)
            str += "Path length: " + pathLength + "\n";
        return str;
    }
    
}
