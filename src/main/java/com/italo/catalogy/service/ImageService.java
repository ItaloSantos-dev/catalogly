package com.italo.catalogy.service;

import com.italo.catalogy.infra.MinioConfig;
import io.minio.*;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class ImageService {

    private final MinioClient minioClient;

    @Value("${minio.bucket.name}")
    private String bucketName;

    public ImageService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }


    public void uploadImage(MultipartFile file, String imagePath){
        try{
            boolean bucketExists = this.minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!bucketExists){
                this.minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
            this.minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(imagePath)
                            .stream(file.getInputStream(), file.getSize(), -1L)
                            .contentType(file.getContentType())
                            .build()
            );
        }catch (MinioException | IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
