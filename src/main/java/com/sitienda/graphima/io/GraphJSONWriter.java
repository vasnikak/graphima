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
     * @param graph the graph
     */
    public GraphJSONWriter(Graph<V> graph) {
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
            // Get the JSON representation of the graph
            GraphJSONConverter<V> jsonConverter = 
                    new GraphJSONConverter<>(Arrays.asList(GraphJSONConverter.Feature.PRETTY_PRINT));
            String json = jsonConverter.toJson(graph);
            // Write the data to the output file
            BufferedWriter out = new BufferedWriter(new FileWriter(filepath));
            out.write(json + "\n");
            out.close();
        }
        catch (IOException e) { 
            throw new GraphIOException(e.getMessage());
        }
    }

}
