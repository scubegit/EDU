package com.scube.edu.awsconfig;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BucketName {
    TODO_IMAGE("educred");
	// TODO_IMAGE("educredaws");
	
    private final String bucketName;
}