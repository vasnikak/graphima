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
 * @param <V> the vertex type
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
    public Path(List<V> nodes) { 
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
     * Append a list of nodes in the path
     * 
     * @param nodes the list of nodes
     */
    public void addAll(List<V> nodes) { 
        for (V node : nodes)
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
    public Path<V> reverse() { 
        Collections.reverse(path);
        return this;
    }
    
    /**
     * Checks if the path starts with a specific node.
     * 
     * @param node the node
     * 
     * @return true or false
     */
    public boolean startsWith(V node) { 
        return (!path.isEmpty() && path.get(0).equals(node));
    }
    
    /**
     * Checks if the path ends with a specific node.
     * 
     * @param node the node
     * 
     * @return true or false
     */
    public boolean endsWith(V node) { 
        return (!path.isEmpty() && path.get(path.size()-1).equals(node));
    }
    
    /**
     * Checks if the current path is valid.
     * A valid path has a connection between every two consecutive vertices.
     * 
     * @return true or false according to if the current path is valid.
     */
    public boolean validate() {
        for (int i = 0; i < path.size(); i++) { 
            if (i < path.size()-1 && 
                !path.get(i).hasEdgeWith(path.get(i+1)))
                return false;
        }
        return true;
    }
    
    /**
     * {@inheritDoc}
     */
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
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.path);
        return hash;
    }

    /**
     * {@inheritDoc}
     */
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
        final Path<?> other = (Path<?>) obj;
        if (!Objects.equals(this.path, other.path)) {
            return false;
        }
        return true;
    }
    
}
