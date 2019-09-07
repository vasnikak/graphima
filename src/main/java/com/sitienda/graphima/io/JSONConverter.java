/**
 * Copyright (C) 2019, by Vasileios Nikakis
 *
 * graphima: yet another Java graph-theory library
 */
package com.sitienda.graphima.io;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Wrapper class for JSON serialization/de-serialization.
 * It currently uses the Gson library.
 *
 * @param <T> the type of the objects that will be serialized/de-serialized to/from JSON.
 * 
 * @author Vasileios Nikakis
 */
public class JSONConverter<T> {
    
    /**
     * The gson object.
     */
    private final Gson gson;
    
    /**
     * Constructor
     */
    public JSONConverter() { 
        gson = new Gson();
    }
    
    /**
     * Converts an object to JSON format.
     * 
     * @param obj the object
     * 
     * @return the object's JSON representation
     */
    public String toJson(T obj) { 
        return gson.toJson(obj);
    }
    
    /**
     * Creates an object based on its JSON representation
     * 
     * @param json the object's JSON representation
     * 
     * @return the object
     */
    public T fromJson(String json) { 
        return gson.fromJson(json, new TypeToken<T>(){}.getType());
    }
    
}
