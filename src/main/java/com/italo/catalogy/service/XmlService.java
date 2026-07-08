package com.italo.catalogy.service;

import io.minio.*;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.italo.catalogy.dto.invoice_xml.InvoiceXmlDTO;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class XmlService {
    private final MinioClient minioClient;

    @Value("${minio.bucket.name}")
    private String bucketName;

    public XmlService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public void uploadXml(MultipartFile file, String xmlPath){
        try{
            boolean bucketExists = this.minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!bucketExists){
                this.minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
            this.minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(xmlPath)
                            .stream(file.getInputStream(), file.getSize(), -1L)
                            .contentType(file.getContentType())
                            .build()
            );
        }catch (MinioException | IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public String  getAssignedUrlXml(String path){
        try{
            return this.minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Http.Method.GET)
                            .bucket(bucketName)
                            .object(path)
                            .extraQueryParams(Map.of(
                                "response-content-disposition",
                                "attachment; filename=\"nota.xml\""
                            ))
                            .expiry(2, TimeUnit.HOURS)
                            .build()
            );
        }catch (MinioException e){
            throw  new RuntimeException(e.getMessage());
        }
    }

    public InputStream getInvoiceXml(String path){
        try{
            GetObjectResponse respose =  minioClient.getObject(GetObjectArgs.builder()
                .bucket(bucketName)
                .object(path)
                .build()
            );
            InputStream inputStream = respose;
            return inputStream;
        }catch (MinioException e){
            throw  new RuntimeException(e.getMessage());
        }
    }
    

}
