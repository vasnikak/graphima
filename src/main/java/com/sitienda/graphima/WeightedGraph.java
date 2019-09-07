/**
 * Copyright (C) 2019, by Vasileios Nikakis
 *
 * graphima: yet another Java graph-theory library
 */
package com.sitienda.graphima;

import com.sitienda.graphima.exceptions.NoSuchPathException;
import com.sitienda.graphima.path.Path;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * A base class weighted graphs.
 * Each edge of the graph has a weight.
 *
 * @param <V> the type of the objects that are encapsulated in each vertex
 *
 * @author Vasileios Nikakis
 */
public abstract class WeightedGraph<V> extends Graph<V> {
    
    /**
     * Default edge weight.
     */
    public static final int DEFAULT_WEIGHT = 1;
    
    /**
     * Creates a weighted graph with a default name.
     */
    public WeightedGraph() { 
        
    }
    
    /**
     * Creates a named weighted graph.
     * 
     * @param name the graph's name
     */
    public WeightedGraph(String name) { 
        super(name);
    }
    
    /**
     * Copy constructor.
     * Creates a weighted graph, based on another graph, with a default name.
     * 
     * @param graph the graph to be copied
     */
    public WeightedGraph(Graph<V> graph) { 
        copyWeightedGraph(graph);
    }
    
    /**
     * Copy constructor.
     * Creates a named weighted graph, based on another graph.
     * 
     * @param name the graph's name
     * @param graph the source graph
     */
    public WeightedGraph(String name, Graph<V> graph) { 
        super(name);
        copyWeightedGraph(graph);
    }
    
    /**
     * Copies the data of the given graph to the current graph.
     * 
     * @param graph the source graph
     */
    private void copyWeightedGraph(Graph<V> graph) { 
        // Clear any existing data
        vertices.clear();
        // First copy all vertices
        // We need also a Map to be able to quickly find the equivalent
        // vertices when we will copy the edges
        Map<Vertex<V>,Vertex<V>> copyMap = new HashMap<>();
        for (Vertex<V> copyVertex : graph.getVertices()) { 
            Vertex<V> vertex = new Vertex<>(copyVertex.getData());
            vertices.add(vertex);
            copyMap.put(copyVertex,vertex);
        }
        // Then copy all edges
        for (Vertex<V> copyVertex : graph.getVertices()) { 
            for (Edge copyEdge : copyVertex.getEdges()) { 
                Vertex<V> vertexFrom = copyMap.get(copyVertex);
                Vertex<V> vertexTo = copyMap.get(copyEdge.getVertex());
                // The default 
                int weight = (copyEdge instanceof WeightedEdge) ? 
                                ((WeightedEdge) copyEdge).getWeight() : 
                                DEFAULT_WEIGHT;
                vertexFrom.addEdge(new WeightedEdge(vertexTo,weight));
            }
        }
    }
    
    /**
     * Adds an edge between two vertices by supplying the corresponding data objects.
     * 
     * @param vertexData1 the data object of the first vertex
     * @param vertexData2 the data object of the second vertex
     * @param weight the weight of the connection
     * 
     * @return The graph.
     */
    public abstract WeightedGraph<V> addEdge(V vertexData1, V vertexData2, int weight);
    
    /**
     * Returns the sum of the weights of a path.
     * 
     * @param pathNodes the path expressed as a list of vertices
     * 
     * @return int the sum of the corresponding weights of the edges of the path
     * 
     * @throws NoSuchPathException if the path is not valid
     */
    public int totalWeight(List<Vertex<V>> pathNodes) throws NoSuchPathException { 
        int total = 0;
        // For each node in the path
        for (int i = 0; i < pathNodes.size(); i++) { 
            Vertex<V> currentNode = (Vertex<V>) pathNodes.get(i);
            // Check if the node actually exist in the current graph
            if (!vertices.contains(currentNode))
                throw new NoSuchPathException("Graph does not contain " + currentNode);
            // If it is not the last node in the path
            if (i < pathNodes.size()-1) { 
                // There has to be a connection with the next node in the path
                Vertex<V> nextNode = (Vertex<V>) pathNodes.get(i+1);
                Edge edge = currentNode.getEdgeWith(nextNode);
                if (edge == null)
                    throw new NoSuchPathException(currentNode + " and " + nextNode + " are not connected");
                total += ((WeightedEdge) edge).getWeight();
            }
        }
        return total;
    }
    
    /**
     * Returns the sum of the weights of a path.
     * 
     * @param path the path object
     * 
     * @return int the sum of the corresponding weights of the edges of the path
     * 
     * @throws NoSuchPathException if the path is not valid
     */
    public int totalWeight(Path path) throws NoSuchPathException { 
        List<Vertex<V>> pathNodes = new LinkedList(path.getPath());
        return totalWeight(pathNodes);
    }
    
}
