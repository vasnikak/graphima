/**
 * Copyright (C) 2019, by Vasileios Nikakis
 *
 * graphima: yet another Java graph-theory library
 */
package com.sitienda.graphima.alg;

/**
 * Execution statistics for the execution of each graph algorithm.
 * 
 * @author Vasileios Nikakis
 */
public class AlgorithmExecutionStats {
    
    /**
     * Algorithm's name.
     */
    private String algorithmName;
    /**
     * Execution start timestamp.
     */
    private long execStart;
    /**
     * Total execution time (ms).
     */
    private long execTime;
    /**
     * Number of the vertices that the algorithm visited.
     */
    private int nodesVisitedNum;
    
    /**
     * Constructor
     */
    public AlgorithmExecutionStats() { 
        reset();
    }
    
    /**
     * Constructor
     * 
     * @param algorithmName algorithm's name
     */
    public AlgorithmExecutionStats(String algorithmName) { 
        this.algorithmName = algorithmName;
        reset();
    }
    
    /**
     * Resets the stats for the current object.
     */
    public void reset() { 
        execStart = System.currentTimeMillis();
        execTime = 0;
        nodesVisitedNum = 0;
    }

    /**
     * 
     * @return algorithm's name
     */
    public String getAlgorithmName() {
        return algorithmName;
    }
    
    /**
     * 
     * @param algorithmName the algorithm's name
     */
    public void setAlgorithmName(String algorithmName) { 
        this.algorithmName = algorithmName;
    }
    
    /**
     * 
     * @return the execution time (ms)
     */
    public long getExecTime() {
        return execTime;
    }

    /**
     * Returns the execution time in a readable format.
     * 
     * @return the execution time in a readable format
     */
    public String getExectimeReadableFormat() { 
        int seconds = (int) (execTime / 1000) % 60 ;
        int minutes = (int) ((execTime / (1000*60)) % 60);
        int hours   = (int) ((execTime / (1000*60*60)) % 24);
        if (hours == 0 && minutes == 0 && hours == 0)
            return execTime + " ms";
        return hours + " h, " + minutes + " min, " + seconds + " sec";
    }
    
    /**
     * Calculates the execution time.
     */
    public void stopExecution() { 
        execTime = System.currentTimeMillis() - execStart;
    }
    
    /**
     * 
     * @return the number of vertices that the algorithm visited
     */
    public int getNodesVisitedNum() {
        return nodesVisitedNum;
    }

    /**
     * 
     * @param nodesVisitedNum the number of vertices that the algorithm visited
     */
    public void setNodesVisitedNum(int nodesVisitedNum) {
        this.nodesVisitedNum = nodesVisitedNum;
    }
    
    /**
     * Increases the number of the vertices that the algorithm visited by one.
     */
    public void incNodesVisitedNum() { 
        nodesVisitedNum++;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() { 
        String str = "Execution statistics";
        if (algorithmName != null)
            str += " for " + algorithmName;
        str += ":\n";
        str += "Execution time: " + getExectimeReadableFormat() + "\n";
        str += "Nodes visited: " + nodesVisitedNum + "\n";
        return str;
    }
    
}
