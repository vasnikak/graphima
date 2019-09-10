/**
 * Copyright (C) 2019, by Vasileios Nikakis
 *
 * graphima: yet another Java graph-theory library
 */
package com.sitienda.graphima.path;

import com.sitienda.graphima.Vertex;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a path in the graph.
 * <p>
 * A path is a list of sequential vertices (nodes) that all belong in the
 * same graph and for each sequential pair of vertices Va -> Vb, an edge has
 * to exist between them.
 * </p>
 *
 * @param <V> the type of each vertex
 *
 * @author Vasileios Nikakis
 */
public class Path<V extends Vertex> {
    
    /**
     * The list that contains the path nodes.
     */
    private List<V> path;
    
    /**
     * Constructs an empty path.
     */
    public Path() { 
        path = new LinkedList<>();
    }
    
    /**
     * Construct a path from a list of vertices.
     * 
     * @param nodes the corresponding vertices
     */
    public <T> Path(List<V> nodes) { 
        path = new LinkedList<>();
        for (V node : nodes)
            path.add(node);
    }
    
    /**
     * 
     * @return a list with the path's nodes.
     */
    public List<V> getPath() { 
        return path;
    }
    
    /**
     * 
     * @return the number of nodes of the path
     */
    public int size() { 
        return path.size();
    }
    
    /**
     * 
     * @return true if the path has no nodes
     */
    public boolean isEmpty() { 
        return path.isEmpty();
    }
    
    /**
     * Appends a node in the path.
     * 
     * @param node the node
     */
    public void add(V node) { 
        path.add(node);
    }
    
    /**
     * A synonym of {@link #add(com.sitienda.graphima.Vertex)}.
     * 
     * @param node the node
     */
    public void push(V node) { 
        path.add(node);
    }
    
    /**
     * Inserts a node at the start of the path.
     * 
     * @param node the node
     */
    public void prepend(V node) { 
        path.add(0,node);
    }
    
    /**
     * Reverses the nodes in the path.
     * 
     * @return The current path
     */
    public Path reverse() { 
        Collections.reverse(path);
        return this;
    }
    
    @Override
    public String toString() { 
        String str = "";
        boolean first = true;
        for (Vertex node : path) { 
            if (!first)
                str += " -> ";
            else
                first = false;
            str +=  node.getData();
        }
        return str;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.path);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Path other = (Path) obj;
        if (!Objects.equals(this.path, other.path)) {
            return false;
        }
        return true;
    }
    
}
