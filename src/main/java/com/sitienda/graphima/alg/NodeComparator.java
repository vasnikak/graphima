/*
 * Copyright (C) 2019, Vasileios Nikakis
 *
 * graphima : yet another Java graph-theory library
 */
package com.sitienda.graphima.alg;

import java.util.Comparator;

/**
 * Some graph algorithms have to resolve collisions between vertices.
 * In cases like this, a NodeComparator object can be provided, if the
 * collision has to be resolved in a manual manner.
 *
 * @param <T> the type of the objects that the graph contains
 *
 * @author Vasileios Nikakis
 */
public interface NodeComparator<T> extends Comparator<T> {
    
}
