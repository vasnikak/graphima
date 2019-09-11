/**
 * Copyright (C) 2019, by Vasileios Nikakis
 *
 * graphima: yet another Java graph-theory library
 */
package com.sitienda.graphima.io;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.sitienda.graphima.Graph;
import com.sitienda.graphima.exceptions.GraphIOException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Graph AWS S3 reader
 * 
 * @param <V> the type of objects that the graph contains
 * 
 * @author Vasileios Nikakis
 */
public class GraphS3Reader<V> extends GraphS3Manager implements GraphReader<V> {

    /**
     * Read buffer size from S3 input stream (bytes).
     */
    private static final int BUFF_SIZE = 1024;
    
    /**
     * Creates an S3 client using the default profile credentials.
     * 
     * @param region the region
     * @param bucketName the bucket name
     * @param keyName they key name
     */
    public GraphS3Reader(Regions region, String bucketName, String keyName) { 
        super(region,bucketName,keyName);
    }
    
    /**
     * Creates an S3 reader using explicitly defined credentials.
     * 
     * @param accessKeyId the access key
     * @param secretKeyId the secret key
     * @param region the region
     * @param bucketName the bucket name
     * @param keyName they key name
     */
    public GraphS3Reader(String accessKeyId, String secretKeyId, Regions region, 
                         String bucketName, String keyName) { 
        super(accessKeyId,secretKeyId,region,bucketName,keyName);
    }
    
    /**
     * Creates an S3 reader using explicitly defined credentials.
     * 
     * @param awsCreds the AWS credentials
     * @param region the region
     * @param bucketName the bucket name
     * @param keyName they key name
     */
    public GraphS3Reader(BasicAWSCredentials awsCreds, Regions region, 
                         String bucketName, String keyName) { 
        super(awsCreds,region,bucketName,keyName);
    }
    
    /**
     * Downloads the S3 file locally.
     * 
     * @param filepath the local temporary file path
     * 
     * @throws GraphIOException in case of an error
     */
    private void downloadFile(String filepath) throws GraphIOException { 
        S3ObjectInputStream s3InputStream = null;
        FileOutputStream out = null;
        boolean error = false;
        try { 
            S3Object obj = s3.getObject(bucketName,keyName);
            s3InputStream = obj.getObjectContent();
            out = new FileOutputStream(new File(filepath));
            byte[] readBuff = new byte[BUFF_SIZE];
            int readLen;
            while ((readLen = s3InputStream.read(readBuff)) > 0) {
                out.write(readBuff,0,readLen);
            }
        }
        catch (SdkClientException | IOException e) { 
            error = true;
            throw new GraphIOException(e.getMessage());
        }
        finally { 
            // Close the S3 input stream
            if (s3InputStream != null) { 
                try { 
                    s3InputStream.close(); 
                } 
                catch (IOException e) { 
                    if (!error)
                        throw new GraphIOException(e.getMessage());
                }
            }
            // Close the local file output stream
            if (out != null) { 
                try { 
                    out.close();
                }
                catch (IOException e) { 
                    if (!error)
                        throw new GraphIOException(e.getMessage());
                }
            }
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Graph<V> read(Class<V> cls) throws GraphIOException {
        try { 
            // Get a random file path
            String filepath = getRandomFilepath();
            // Download the S3 file locally
            downloadFile(filepath);
            // Load the graph in the memory using GraphJSONReader
            GraphJSONReader<V> jsonReader = new GraphJSONReader(filepath);
            Graph<V> graph = jsonReader.read(cls);
            // Remove the local file
            Files.delete(new File(filepath).toPath());
            // Return the graph
            return graph;
        }
        catch (IOException e) { 
            throw new GraphIOException(e.getMessage());
        }
    }
 
}
