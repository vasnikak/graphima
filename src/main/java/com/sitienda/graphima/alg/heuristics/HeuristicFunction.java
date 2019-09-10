/**
 * Copyright (C) 2019, by Vasileios Nikakis
 *
 * graphima: yet another Java graph-theory library
 */
package com.sitienda.graphima.alg.heuristics;

/**
 * Heuristic function
 *
 * @param <V> the type of the objects that the graph contains
 *
 * @author Vasileios Nikakis
 */
public interface HeuristicFunction<V> {
    
    /**
     * The heuristic function.
     * 
     * @param obj the object that the heuristic function be applied on
     * 
     * @return the heuristic value.
     */
    public abstract int h(V obj);
    
}
