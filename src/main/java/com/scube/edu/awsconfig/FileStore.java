package com.scube.edu.awsconfig;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import lombok.AllArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.amazonaws.services.s3.model.DeleteObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Service
public class FileStore {
	
	private static final Logger logger = LoggerFactory.getLogger(FileStore.class);
	
    private final AmazonS3 amazonS3;
    
//    private BucketName bucketname;

    public void upload(String path,
                       String fileName,
                       Optional<Map<String, String>> optionalMetaData,
                       InputStream inputStream) {
    	
    	
    	logger.info("---------TodoServiceImpl path----------------", path );
    	logger.info("---------TodoServiceImpl fileName----------------", fileName );
    	
    	
        ObjectMetadata objectMetadata = new ObjectMetadata();
        optionalMetaData.ifPresent(map -> {
            if (!map.isEmpty()) {
                map.forEach(objectMetadata::addUserMetadata);
            }
        });
        try {
        
        	logger.info("---------TodoServiceImpl start amazonS3 put Object----------------");
        	amazonS3.putObject(path, fileName, inputStream, objectMetadata);
        	
        	logger.info("---------TodoServiceImpl end amazonS3 put Object----------------");
        } catch (AmazonServiceException e) {
            throw new IllegalStateException("Failed to upload the file", e);
        }
    }

    public byte[] download(String path, String key) {
        try {
            S3Object object = amazonS3.getObject(path, key);
            S3ObjectInputStream objectContent = object.getObjectContent();
            return IOUtils.toByteArray(objectContent);
        } catch (AmazonServiceException | IOException e) {
            throw new IllegalStateException("Failed to download the file", e);
        }
    }
    
    public void deleteFile(final String keyName) {
    	String bucketName = BucketName.TODO_IMAGE.getBucketName();
        logger.info("Deleting file with name= " + bucketName);
        
//        String keyname = "file/verification_docs/0e233706-971c-4961-b817-4d73ad580dbd/page.png";
        final DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucketName, keyName);
        amazonS3.deleteObject(deleteObjectRequest);
        logger.info("File deleted successfully.");
    }

}