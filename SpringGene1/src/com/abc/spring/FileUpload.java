package com.abc.spring;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.ObjectMetadata;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class FileUpload
{
  private static String responsemessage = null;

  public static String uploadFile(MultipartFile file, HttpServletRequest request)
    throws IOException
  {
    String fileName = file.getOriginalFilename();

    String endpoint = "http://oss-cn-shanghai.aliyuncs.com";
    String accessKeyId = "ONzYRFQQ7TQoVBiY";
    String accessKeySecret = "E6zzurPsNtKNIxCjYHaxKBf2K7xG6j";
    String bucketName = "myfirst1990";

    System.out.println("创建OSSClient之前");
    OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    String key = new Date().getTime() + fileName;
    try
    {
      System.out.println("正在上传...");
      uploadFile(client, bucketName, key, file);
    } catch (Exception e) {
      e.printStackTrace();
    }

    client.shutdown();
    return "http://" + bucketName + ".oss-cn-shanghai.aliyuncs.com/" + key;
  }

  private static void uploadFile(OSSClient client, String bucketName, String Objectkey, MultipartFile filename)
    throws OSSException, ClientException, FileNotFoundException
  {
    ObjectMetadata objectMeta = new ObjectMetadata();
    objectMeta.setContentLength(filename.getSize());

    CommonsMultipartFile commonsMultipartFile = (CommonsMultipartFile)filename;
    DiskFileItem diskFileItem = (DiskFileItem)commonsMultipartFile.getFileItem();
    File file = diskFileItem.getStoreLocation();

    objectMeta.setContentType("image/png");
    try
    {
      InputStream inputStream = new FileInputStream(file);
      client.putObject(bucketName, Objectkey, inputStream, objectMeta);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
}