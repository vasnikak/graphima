/*
 * Copyright (C) 2019, Vasileios Nikakis
 *
 * graphima : yet another Java graph-theory library
 */
package com.sitienda.graphima.alg.heuristics;

/**
 * Zero heuristic function - it always returns 0.
 *
 * @param <T> the type of the object where the heuristic function will be applied on
 *
 * @author Vasileios Nikakis
 */
public class ZeroHeuristicFunction<T> implements HeuristicFunction<T> {
    
    /**
     * The heuristic function - it always returns 0.
     * 
     * @param obj the object where the heuristic function be applied on
     * 
     * @return the heuristic value.
     */
    @Override
    public int h(T obj) { 
        return 0;
    }
    
}
