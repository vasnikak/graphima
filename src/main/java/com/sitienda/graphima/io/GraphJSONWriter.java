/**
 * Copyright (C) 2019, by Vasileios Nikakis
 *
 * graphima: yet another Java graph-theory library
 */
package com.sitienda.graphima.io;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sitienda.graphima.Edge;
import com.sitienda.graphima.Graph;
import com.sitienda.graphima.Vertex;
import com.sitienda.graphima.WeightedEdge;
import com.sitienda.graphima.exceptions.GraphIOException;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;

/**
 * Graph JSON writer.
 * 
 * @author Vasileios Nikakis
 */
public class GraphJSONWriter extends GraphFileWriter {
    
    /**
     * Constructor
     * 
     * @param graph the graph
     */
    public GraphJSONWriter(Graph<?> graph) {
        super(graph);
    }

    /**
     * Exports the graph to a JSON file.
     * 
     * @param filepath the path of the export file
     * 
     * @throws GraphIOException if any IO error occurs.
     */
    @Override
    public void writeFile(String filepath) throws GraphIOException {
        try { 
            // Initialize a JSON generator
            File file = new File(filepath);
            JsonFactory factory = new JsonFactory();
            JsonGenerator json = factory.createGenerator(file, JsonEncoding.UTF8);
            json.setCodec(new ObjectMapper());
            json.writeStartObject();
            
            // Write the type of the graph
            json.writeStringField("type",GraphType.getGraphTypeString(graph));
            // Write the name of the graph
            json.writeStringField("name",graph.getName());
            // Get graph's vertices
            HashSet<Vertex<?>> vertices = (HashSet<Vertex<?>>) graph.getVertices();
            // Write the vertices of the graph
            json.writeArrayFieldStart("vertices");
            for (Vertex<?> vertex : vertices)
                json.writeObject(vertex.getData());
            json.writeEndArray();
            // Write the edges of the graph
            json.writeArrayFieldStart("edges");
            for (Vertex<?> vertex : vertices) { 
                for (Edge<Vertex<?>> edge : vertex.getEdges()) { 
                    json.writeStartObject();
                    json.writeObjectField("from",vertex.getData());
                    json.writeObjectField("to",edge.getVertex().getData());
                    if (edge instanceof WeightedEdge)
                        json.writeNumberField("weight",((WeightedEdge) edge).getWeight());
                    else
                        json.writeNumberField("weight",null);
                    json.writeEndObject();
                }
            }
            json.writeEndArray();
            
            // Close the JSON file
            json.writeEndObject();
            json.close();
        }
        catch (IOException e) { 
            throw new GraphIOException(e.getMessage());
        }
    }

}
