/**
 * Copyright (C) 2019, by Vasileios Nikakis
 *
 * graphima: yet another Java graph-theory library
 */
package com.sitienda.graphima.io;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.sitienda.graphima.DirectedGraph;
import com.sitienda.graphima.DirectedWeightedGraph;
import com.sitienda.graphima.Edge;
import com.sitienda.graphima.Graph;
import com.sitienda.graphima.UndirectedGraph;
import com.sitienda.graphima.UndirectedWeightedGraph;
import com.sitienda.graphima.Vertex;
import com.sitienda.graphima.WeightedEdge;
import com.sitienda.graphima.WeightedGraph;
import com.sitienda.graphima.exceptions.GraphDataMissingException;
import com.sitienda.graphima.exceptions.VertexNotInGraphException;
import com.sitienda.graphima.exec.Student;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Graph JSON converter.
 * It currently uses the Gson library.
 * 
 * @param <V> the type of objects that the graph contains
 * 
 * @author Vasileios Nikakis
 */
public class GraphJSONConverter<V> {
    
    /**
     * Features.
     */
    public enum Feature { 
        PRETTY_PRINT
    };
    
    /**
     * The gson object.
     */
    private final Gson gson;
    /**
     * Enabled features
     */
    private final HashSet<Feature> features;
    
    /**
     * Constructor
     */
    public GraphJSONConverter() { 
        gson = new Gson();
        features = new HashSet<>();
    }
    
    /**
     * Provides a collection of features
     * 
     * @param features the collection of features
     */
    public GraphJSONConverter(Collection<Feature> features) { 
        this.features = new HashSet<>();
        for (Feature feature : features)
            this.features.add(feature);
        if (this.features.contains(Feature.PRETTY_PRINT))
            gson = new GsonBuilder().setPrettyPrinting().create();
        else
            gson = new Gson();
    }
    
    /**
     * Checks if a feature has been set.
     *
     * @param feature the feature
     */
    public boolean hasFeature(Feature feature) { 
        return features.contains(feature);
    }
    
    /**
     * Creates the vertex index for a graph.
     * 
     * @param graph the graph
     * 
     * @return the vertex index.
     */
    private Map<Vertex<V>,String> createVertexIdx(Graph<V> graph) { 
        int vertexId = 1;
        Map<Vertex<V>,String> vertexIdx = new HashMap<>();
        for (Vertex<V> vertex : graph.getVertices())
            vertexIdx.put(vertex,Integer.toString(vertexId++));
        return vertexIdx;
    }
    
    /**
     * Serializes a graph to JSON format.
     * 
     * @param graph the graph
     * 
     * @return the JSON representation of the graph
     */
    public String toJson(Graph<V> graph) { 
        // A map object that will hold graph's data
        Map<String,Object> graphData = new HashMap<>();
        
        // Type of the graph
        graphData.put("type",GraphType.getGraphTypeString(graph));
        // Name of the graph
        graphData.put("name",graph.getName());
        
        // Vertices of the graph
        Map<Vertex<V>,String> vertexIdx = this.createVertexIdx(graph);
        Map<String,V> vertices = new HashMap<>();
        for (Map.Entry<Vertex<V>,String> entry : vertexIdx.entrySet()) 
            vertices.put(entry.getValue(),entry.getKey().getData());
        graphData.put("vertices",vertices);
        
        // Edges of the graph
        List<Map<String,String>> edges = new LinkedList<>();
        for (Vertex<V> vertex : graph.getVertices()) { 
            String vertexFromId = vertexIdx.get(vertex);
            for (Edge<Vertex<V>> edge : vertex.getEdges()) { 
                String vertexToId = vertexIdx.get(edge.getVertex());
                Integer weight = (edge instanceof WeightedEdge) ? 
                                    ((WeightedEdge) edge).getWeight() : null;
                Map<String,String> edgeData = new HashMap<>();
                edgeData.put("from",vertexFromId);
                edgeData.put("to",vertexToId);
                if (weight != null)
                    edgeData.put("weight",Integer.toString(weight));
                edges.add(edgeData);
            }
        }
        graphData.put("edges",edges);
        
        // Convert and return graph's data to json
        return gson.toJson(graphData);
    }
    
    /**
     * De-serializes a graph from its JSON representation.
     * 
     * @param json the graph's JSON representation
     * @param cls the class of the object that the graph contains
     * 
     * @return the graph
     * 
     * @throws GraphDataMissingException if some graph data is missing in JSON representation
     * @throws VertexNotInGraphException if the JSON representation is not well formatted
     */
    public Graph<V> fromJson(String json, Class<V> cls) throws GraphDataMissingException, VertexNotInGraphException { 
        // Decode the json to a map object
        Map<String,Object> graphData = gson.fromJson(json,HashMap.class);
        
        // Find the type of the graph
        GraphType.Type type = GraphType.getGraphType((String) graphData.get("type"));
        if (type == null)
            throw new GraphDataMissingException("Graph type is missing");
        // Instanciate the correct graph type
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
        
        // Set the name of the graph
        String name = (String) graphData.get("name");
        if (name != null)
            graph.setName(name);
        
        // Insert the vertices and construct the appropriate index
        Map<String,V> vertices = (Map<String,V>) graphData.get("vertices");
        Map<String,Vertex<V>> vertexIdx = new HashMap<>();
        if (vertices == null)
            throw new GraphDataMissingException("The vertex data is missing");
        for (Map.Entry<String,V> entry : vertices.entrySet()) { 
            // A quick hack to cast LinkedTreeMap to V
            V vertexData = (V) gson.fromJson(gson.toJson((LinkedTreeMap) entry.getValue()),cls);
            graph.addVertex(vertexData);
            Vertex<V> vertex = graph.getVertexWithData(vertexData);
            vertexIdx.put(entry.getKey(),vertex);
        }
        
        // Insert the edges
        List<Map<String,String>> edges = (List<Map<String,String>>) graphData.get("edges");
        for (Map<String,String> edgeData : edges) { 
            String vertexFromId = edgeData.get("from");
            if (vertexFromId == null)
                throw new GraphDataMissingException("The edge data is missing");
            String vertexToId = edgeData.get("to");
            if (vertexToId == null)
                throw new GraphDataMissingException("The edge data is missing");
            String weight = edgeData.get("weight");
            if (graph instanceof WeightedGraph && weight == null)
                throw new GraphDataMissingException("The edge data is missing");
            Vertex<V> vertexFrom = vertexIdx.get(vertexFromId);
            if (vertexFrom == null)
                throw new VertexNotInGraphException("The vertex is not in the graph");
            Vertex<V> vertexTo = vertexIdx.get(vertexToId);
            if (vertexTo == null)
                throw new VertexNotInGraphException("The vertex is not in the graph");
            if (graph instanceof WeightedGraph)
                ((WeightedGraph) graph).addEdge(vertexFrom,vertexTo,Integer.parseInt(weight));
            else
                graph.addEdge(vertexFrom,vertexTo);
        }
        
        // Return the graph
        return graph;
    }
    
}
