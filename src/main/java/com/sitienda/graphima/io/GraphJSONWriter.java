/**
 * Copyright (C) 2019, by Vasileios Nikakis
 *
 * graphima: yet another Java graph-theory library
 */
package com.sitienda.graphima.io;

import com.sitienda.graphima.Graph;
import com.sitienda.graphima.exceptions.GraphIOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 * Graph JSON writer.
 * 
 * @param <V> the type of objects that the graph contains
 * 
 * @author Vasileios Nikakis
 */
public class GraphJSONWriter<V> extends GraphFileWriter<V> {
    
    /**
     * Constructor
     * 
     * @param filepath the output file path
     */
    public GraphJSONWriter(String filepath) {
        super(filepath);
    }

    /**
     * Exports a graph to a JSON file.
     * 
     * @param graph the graph
     * 
     * @throws GraphIOException in case of any error
     */
    @Override
    public void write(Graph<V> graph) throws GraphIOException {
        try { 
            // Get the JSON representation of the graph
            GraphJSONConverter<V> jsonConverter = 
                    new GraphJSONConverter<>(Arrays.asList(GraphJSONConverter.Feature.PRETTY_PRINT));
            String json = jsonConverter.toJson(graph);
            // Write the data to the output file
            BufferedWriter out = null;
            try { 
                out = new BufferedWriter(new FileWriter(filepath));
                out.write(json + "\n");
            }
            finally { 
                if (out != null) { 
                    try { out.close(); } catch (IOException e) { }
                }
            }
        }
        catch (IOException e) { 
            throw new GraphIOException(e.getMessage());
        }
    }

}
