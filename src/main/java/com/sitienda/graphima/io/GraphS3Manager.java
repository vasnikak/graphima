/**
 * Copyright (C) 2019, by Vasileios Nikakis
 *
 * graphima: yet another Java graph-theory library
 */
package com.sitienda.graphima.io;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.sitienda.graphima.exceptions.GraphIOException;
import java.io.File;
import java.util.List;
import java.util.UUID;

/**
 * Graph AWS S3 base class
 * 
 * @author Vasileios Nikakis
 */
public abstract class GraphS3Manager {
    
    /**
     * Amazon S3 client
     */
    protected final AmazonS3 s3;
    /**
     * Bucket name
     */
    protected final String bucketName;
    /**
     * Key name
     */
    protected final String keyName;
    
    /**
     * Creates an S3 client using the default profile credentials.
     * 
     * @param region the region
     * @param bucketName the bucket name
     * @param keyName they key name
     */
    public GraphS3Manager(Regions region, String bucketName, String keyName) { 
        this.bucketName = bucketName;
        this.keyName = keyName;
        s3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new ProfileCredentialsProvider())
                .withRegion(region)
                .build();
    }
    
    /**
     * 
     * @return the bucket name
     */
    public String getBucketName() { 
        return bucketName;
    }
    
    /**
     * 
     * @return the key name
     */
    public String getKeyName() { 
        return keyName;
    }
    
    /**
     * Checks if the bucket is listed in the provided profile.
     * 
     * @return true or false according to if the bucket is listed in the provided profile
     */
    public boolean bucketExistsInProfile() { 
        List<Bucket> buckets = s3.listBuckets();
        boolean found = false;
        for (Bucket bucket : buckets) { 
            if (bucket.getName().equals(bucketName)) { 
                found = true;
                break;
            }
        }
        return found;
    }
    
    /**
     * Returns a random local file path.
     * 
     * @return the random local file path
     * @throws GraphIOException in case of an error
     */
    protected String getRandomFilepath() throws GraphIOException { 
        // First check if we can use the system's temporary directory
        File dir = new File(System.getProperty("java.io.tmpdir"));
        // If not, check if we can use current working directory
        if (!dir.canWrite()) { 
            dir = new File(".");
            if (!dir.canWrite())
                throw new GraphIOException("No write access to neither system's " + 
                                           "tmp directory nor to current working directory");
        }
        // Generate and return a random filepath
        String filepath = dir + File.separator + UUID.randomUUID();
        return filepath;
    }
    
}
