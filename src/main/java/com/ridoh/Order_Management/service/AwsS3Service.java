package com.ridoh.Order_Management.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;

/**
 * Service class for handling file uploads to Amazon S3.
 */
@Service
public class AwsS3Service {

    // Amazon S3 bucket name
    private final String bucketName = "ridoh-ecommerce";

    @Value("${aws.s3.access.key}")
    private String awsS3AccessKey;

    @Value("${aws.s3.secret.key}")
    private String awsS3SecretKey;

    /**
     * Uploads an image file to an Amazon S3 bucket and returns the URL of the uploaded image.
     *
     * @param photo the image file to be uploaded
     * @return the URL of the uploaded image in the S3 bucket
     * @throws RuntimeException if an error occurs during the upload process
     */
    public String saveImageToS3(MultipartFile photo) {
        try {
            // Get the original filename
            String s3Filename = photo.getOriginalFilename();

            // Create AWS credentials using access and secret key
            BasicAWSCredentials awsCredentials = new BasicAWSCredentials(awsS3AccessKey, awsS3SecretKey);

            // Create an S3 client with the configured credentials and region
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                    .withRegion(Regions.EU_NORTH_1)
                    .build();

            // Get input stream from the uploaded file
            InputStream inputStream = photo.getInputStream();

            // Set metadata for the object
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("image/jpeg");

            // Create a PutObjectRequest to upload the image to S3
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, s3Filename, inputStream, metadata);
            s3Client.putObject(putObjectRequest);

            // Return the S3 URL of the uploaded image
            return "https://" + bucketName + ".s3.amazonaws.com/" + s3Filename;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to upload image to S3 bucket: " + e.getMessage());
        }
    }
}
