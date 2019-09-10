/**
 * Copyright (C) 2019, by Vasileios Nikakis
 *
 * graphima: yet another Java graph-theory library
 */
package com.sitienda.graphima.alg.heuristics;

/**
 * Zero heuristic function - it always returns 0.
 *
 * @param <V> the type of the object where the heuristic function will be applied on
 *
 * @author Vasileios Nikakis
 */
public class ZeroHeuristicFunction<V> implements HeuristicFunction<V> {
    
    /**
     * The heuristic function - it always returns 0.
     * 
     * @param obj the object where the heuristic function be applied on
     * 
     * @return the heuristic value.
     */
    @Override
    public int h(V obj) { 
        return 0;
    }
    
}
