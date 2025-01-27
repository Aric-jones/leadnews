package com.heima.minio.test;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;

import java.io.FileInputStream;
import java.io.IOException;

public class MinIOTest {


    public static void main(String[] args) {
        try (FileInputStream fileInputStream = new FileInputStream("D:\\plugins\\js\\index.js")) {
            // 1. 创建 MinIO 客户端
            MinioClient minioClient = MinioClient.builder()
                    .endpoint("http://192.168.200.130:9001")
                    .credentials("minio", "minio123")
                    .build();

            // 2. 上传对象
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket("leadnews") // 存储桶名称
                    .object("plugins/js/index.js") // 对象名称
                    .contentType("text/javascript") // 内容类型
                    .stream(fileInputStream, fileInputStream.available(), -1) // 文件输入流
                    .build();

            minioClient.putObject(putObjectArgs);

//            System.out.println("文件上传成功，访问地址: http://192.168.200.130:9001/leadnews/list.html");

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("文件操作失败: " + e.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("MinIO 操作失败: " + ex.getMessage());
        }
    }

}