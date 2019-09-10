/**
 * Copyright (C) 2019, by Vasileios Nikakis
 *
 * graphima: yet another Java graph-theory library
 */
package com.sitienda.graphima.io;

import com.sitienda.graphima.DirectedGraph;
import com.sitienda.graphima.DirectedWeightedGraph;
import com.sitienda.graphima.Graph;
import com.sitienda.graphima.UndirectedGraph;
import com.sitienda.graphima.UndirectedWeightedGraph;
import com.sitienda.graphima.exceptions.GraphDataMissingException;

/**
 *
 * @author Vasileios Nikakis
 */
public final class GraphType {
    
    /**
     * Graph types
     */
    public enum Type { 
        UNDIRECTED_GRAPH,
        UNDIRECTED_WEIGHTED_GRAPH,
        DIRECTED_GRAPH,
        DIRECTED_WEIGHTED_GRAPH
    };
    
    /**
     * Returns the respective type according to the graph type.
     * 
     * @param graph the graph
     * 
     * @return the respective type
     */
    public static Type getGraphType(Graph graph) { 
        if (graph instanceof UndirectedGraph)
            return Type.UNDIRECTED_GRAPH;
        else if (graph instanceof UndirectedWeightedGraph)
            return Type.UNDIRECTED_WEIGHTED_GRAPH;
        else if (graph instanceof DirectedGraph)
            return Type.DIRECTED_GRAPH;
        else
            return Type.DIRECTED_WEIGHTED_GRAPH;
    }
    
    /**
     * Returns the respective string representation of the graph type.
     * 
     * @param graph the graph
     * 
     * @return the type's string representation
     */
    public static String getGraphTypeString(Graph graph) { 
        Type type = getGraphType(graph);
        switch (type) { 
            case UNDIRECTED_GRAPH:
                return "UNDIRECTED_GRAPH";
            case UNDIRECTED_WEIGHTED_GRAPH:
                return "UNDIRECTED_WEIGHTED_GRAPH";
            case DIRECTED_GRAPH:
                return "DIRECTED_GRAPH";
            case DIRECTED_WEIGHTED_GRAPH:
                return "DIRECTED_WEIGHTED_GRAPH";
        }
        // It will never reach here
        return null;
    }
    
    /**
     * Returns the respective type according to the type's string representation.
     * If the type could not be found, it returns null.
     * 
     * @param type the string representation of the type
     * 
     * @return the respective type or null.
     */
    public static Type getGraphType(String type) { 
        switch (type) { 
            case "UNDIRECTED_GRAPH":
                return Type.UNDIRECTED_GRAPH;
            case "UNDIRECTED_WEIGHTED_GRAPH":
                return Type.UNDIRECTED_WEIGHTED_GRAPH;
            case "DIRECTED_GRAPH":
                return Type.DIRECTED_GRAPH;
            case "DIRECTED_WEIGHTED_GRAPH":
                return Type.DIRECTED_WEIGHTED_GRAPH;
        }
        return null;
    }
    
    /**
     * Creates an instance of a graph according to its type.
     * 
     * @param <V> the type of objects that the graph contains
     * @param type the type of the graph
     * 
     * @return the new (empty) graph
     * 
     * @throws GraphDataMissingException in case that the type doesn't have a valid value
     */
    public static <V> Graph<V> getInstance(Type type) throws GraphDataMissingException { 
        Graph<V> graph = null;
        switch (type) { 
            case UNDIRECTED_GRAPH:
                graph = new UndirectedGraph<>();
                break;
            case UNDIRECTED_WEIGHTED_GRAPH:
                graph = new UndirectedWeightedGraph<>();
                break;
            case DIRECTED_GRAPH:
                graph = new DirectedGraph<>();
                break;
            case DIRECTED_WEIGHTED_GRAPH:
                graph = new DirectedWeightedGraph<>();
                break;
            default:
                // It should never reach here
                throw new GraphDataMissingException("Graph type doesn't have a valid value");
        }
        return graph;
    }
    
}
