package com.scube.edu.awsconfig;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonConfig {
    @Bean
    public AmazonS3 s3() {
        AWSCredentials awsCredentials =
          new BasicAWSCredentials("AKIA5PZXMO3IMHZIMOJY", "g1D8WR48U2vCPYO9iMp/mxZeEt17+B2puZPyZbKl");
//         new BasicAWSCredentials("AKIAXR4AIMFVNDKTGEHY", "bnjgEdgI3y+ccp1vFfbcft344FH+EPJN/ZHA3GNV");
        return AmazonS3ClientBuilder
                .standard()
                .withRegion("ap-south-1")
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();

    }
}