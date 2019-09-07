/**
 * Copyright (C) 2019, by Vasileios Nikakis
 *
 * graphima: yet another Java graph-theory library
 */
package com.sitienda.graphima.io;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sitienda.graphima.DirectedGraph;
import com.sitienda.graphima.DirectedWeightedGraph;
import com.sitienda.graphima.Graph;
import com.sitienda.graphima.UndirectedGraph;
import com.sitienda.graphima.UndirectedWeightedGraph;
import com.sitienda.graphima.exceptions.GraphIOException;
import com.sitienda.graphima.exceptions.GraphTypeNotFoundException;
import com.sitienda.graphima.exec.Student;
import java.io.File;
import java.io.IOException;

/**
 * Graph JSON writer.
 *
 * @param <V> the graph type
 * 
 * @author Vasileios Nikakis
 */
public class GraphJSONReader<V> extends GraphFileReader<V> {

    /**
     * The file path.
     */
    public GraphJSONReader(String filepath) {
        super(filepath);
    }

    /**
     * Reads the graph type of the graph from the JSON document.
     * 
     * @return the graph type
     * 
     * @throws GraphIOException if any IO error occurs
     * @throws GraphTypeNotFoundException if the graph type is not present in the JSON document
     */
    public GraphType.Type readType() throws GraphIOException, GraphTypeNotFoundException {
        try { 
            // Initialize a JSON parser
            File file = new File(filepath);
            JsonFactory factory = new JsonFactory();
            JsonParser json = factory.createParser(file);
            
            // Find the graph type
            GraphType.Type type = null;
            while (!json.isClosed()) { 
                if (JsonToken.FIELD_NAME.equals(json.nextToken())) { 
                    if (json.getCurrentName().equals("type") && 
                        JsonToken.VALUE_STRING.equals(json.nextToken())) { 
                        type = GraphType.getGraphType(json.getValueAsString(null));
                        break;
                    } else
                        json = json.skipChildren();
                }
            }
            // Check if the graph type was found
            if (type == null)
                throw new GraphTypeNotFoundException("Could not find a valid graph type");
            
            // Close the parser
            json.close();
            
            // Return the graph's type
            return type;
        }
        catch (IOException e) { 
            throw new GraphIOException(e.getMessage());
        }
    }
    
    /**
     * Reads the vertices from the JSON file
     * 
     * @param json the JsonParser object
     * @param objMapper the ObjectMapper object
     * @param classObj the Class object of the class that its objects are 
     *                 encapsulated inside graph's vertices.
     * @param graph the graph
     * 
     * @throws IOException in any IO error occurs
     */
    private void readVertices(JsonParser json, ObjectMapper objMapper, Class<V> classObj, Graph<V> graph) throws IOException { 
        while (!json.isClosed()) { 
            JsonToken token = json.nextToken();
            if (JsonToken.START_OBJECT.equals(token)) { 
                V vertexData = objMapper.readValue(json.readValueAsTree().toString(), classObj);
                graph.addVertex(vertexData);
            } else if (JsonToken.END_ARRAY.equals(token))
                return;
        }
    }
    
    /**
     * Reads the edges from the JSON file
     * 
     * @param json the JsonParser object
     * @param objMapper the ObjectMapper object
     * @param classObj the Class object of the class that its objects are 
     *                 encapsulated inside graph's vertices.
     * @param graph the graph
     * 
     * @throws IOException in any IO error occurs
     */
    private void readEdges(JsonParser json, ObjectMapper objMapper, Class<V> classObj, Graph<V> graph) throws IOException { 
        while (!json.isClosed()) { 
            JsonToken token = json.nextToken();
            if (JsonToken.START_OBJECT.equals(token)) { 
                while (!JsonToken.END_OBJECT.equals(token)) { 
                    if (JsonToken.FIELD_NAME.equals(token)) { 
                        System.out.println(json.currentName());
                    }
                    token = json.nextToken();
                }
            } else if (JsonToken.END_ARRAY.equals(token))
                return;
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Graph<V> readFile(Class<V> classObj) throws GraphIOException, GraphTypeNotFoundException {
        try { 
            // Initialize a JSON parser
            File file = new File(filepath);
            JsonFactory factory = new JsonFactory();
            JsonParser json = factory.createParser(file);
            ObjectMapper objMapper = new ObjectMapper();
            json.setCodec(objMapper);
            
            // Read the graph type
            GraphType.Type type = readType();
            // Instanciate the graph, according to the type
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
                    throw new GraphTypeNotFoundException();
            }
            
            // Construct the graph
            while (!json.isClosed()) { 
                if (JsonToken.FIELD_NAME.equals(json.nextToken())) { 
                    switch (json.getCurrentName()) { 
                        // Read the name of the graph
                        case "name":
                            if (JsonToken.VALUE_STRING.equals(json.nextToken())) { 
                                String name = json.getValueAsString().trim();
                                if (name.length() > 0)
                                    graph.setName(name);
                            }
                            break;
                        case "vertices":
                            if (JsonToken.START_ARRAY.equals(json.nextToken()))
                                readVertices(json,objMapper,classObj,graph);
                            break;
                        case "edges":
                            if (JsonToken.START_ARRAY.equals(json.nextToken()))
                                readEdges(json,objMapper,classObj,graph);
                            break;
                        default:
                            // Ignore any other field
                    }
                }
            }
            
            // Close the parser
            json.close();
            
            // Return the graph
            return graph;
        }
        catch (IOException e) { 
            throw new GraphIOException(e.getMessage());
        }
    }
    
}
