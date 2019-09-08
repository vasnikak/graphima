/**
 * Copyright (C) 2019, by Vasileios Nikakis
 *
 * graphima: yet another Java graph-theory library
 */
package com.sitienda.graphima.io;

import com.sitienda.graphima.Graph;
import com.sitienda.graphima.exceptions.ErroneousFileFormatException;
import com.sitienda.graphima.exceptions.GraphDataMissingException;
import com.sitienda.graphima.exceptions.GraphIOException;
import com.sitienda.graphima.exceptions.VertexNotInGraphException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Graph JSON writer.
 *
 * @param <V> the type of objects that the graph contains
 * 
 * @author Vasileios Nikakis
 */
public class GraphJSONReader<V> extends GraphFileReader<V> {

    /**
     * Constructor
     * 
     * @param filepath the file path
     */
    public GraphJSONReader(String filepath) {
        super(filepath);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Graph<V> readFile(Class<V> cls) throws GraphIOException, ErroneousFileFormatException {
        try { 
            byte[] fileData = Files.readAllBytes(Paths.get(filepath));
            String json = new String(fileData,StandardCharsets.UTF_8);
            GraphJSONConverter<V> jsonConverter = new GraphJSONConverter<>();
            Graph<V> graph = jsonConverter.fromJson(json,cls);
            return graph;
        }
        catch (IOException e) { 
            throw new GraphIOException(e.getMessage());
        } catch (GraphDataMissingException | VertexNotInGraphException e) {
            throw new ErroneousFileFormatException(e.getMessage());
        }
    }
    
}
