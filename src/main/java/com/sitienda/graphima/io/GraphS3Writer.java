/**
 * Copyright (C) 2019, by Vasileios Nikakis
 *
 * graphima: yet another Java graph-theory library
 */
package com.sitienda.graphima.io;

import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.sitienda.graphima.Graph;
import com.sitienda.graphima.exceptions.GraphIOException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Graph AWS S3 writer
 * 
 * @param <V> the type of objects that the graph contains
 * 
 * @author Vasileios Nikakis
 */
public class GraphS3Writer<V> extends GraphS3Manager implements GraphWriter<V> {
    
    /**
     * Creates an S3 client using the default profile credentials.
     * 
     * @param region the region
     * @param bucketName the bucket name
     * @param keyName they key name
     */
    public GraphS3Writer(Regions region, String bucketName, String keyName) { 
        super(region,bucketName,keyName);
    }
    
    /**
     * Creates the bucket, if it doesn't exist.
     * 
     * @throws GraphIOException in case of an error
     */
    public void createBucket() throws GraphIOException { 
        try { 
            // If the bucket already exists
            if (s3.doesBucketExistV2(bucketName)) { 
                // Check if the bucket exists in the current profile
                if (!bucketExistsInProfile())
                    throw new GraphIOException("The requested bucket (" + bucketName + ") exists but not in the provided profile");
            // if the bucket doesn't exist, create it
            } else { 
                s3.createBucket(new CreateBucketRequest(bucketName));
                // Verify that the bucket was created
                if (!bucketExistsInProfile())
                    throw new GraphIOException("The requested bucket (" + bucketName + ") could not be created");
            }
        }
        catch (SdkClientException | GraphIOException e) { 
            throw new GraphIOException(e.getMessage());
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void write(Graph<V> graph) throws GraphIOException { 
        try { 
            // Get a random file path
            String filepath = getRandomFilepath();
            // Write the file using GraphJSONWriter
            GraphJSONWriter<V> jsonWriter = new GraphJSONWriter<>(filepath);
            jsonWriter.write(graph);
            // Create the S3 bucket, if it doesn't exist
            createBucket();
            // Upload the file to the bucket
            s3.putObject(bucketName,keyName,new File(filepath));
            // Remove the local file
            Files.delete(new File(filepath).toPath());
        }
        catch (SdkClientException | IOException e) { 
            throw new GraphIOException(e.getMessage());
        }
    }
    
}
