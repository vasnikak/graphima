/*
 * Copyright (C) 2019, Vasileios Nikakis
 *
 * graphima : yet another Java graph-theory library
 */
package com.sitienda.graphima.alg.heuristics;

/**
 * Heuristic function
 *
 * @param <T> the type of the objects that the graph contains
 *
 * @author Vasileios Nikakis
 */
public interface HeuristicFunction<T> {
    
    /**
     * The heuristic function.
     * 
     * @param obj the object that the heuristic function be applied on
     * 
     * @return the heuristic value.
     */
    public abstract int h(T obj);
    
}
