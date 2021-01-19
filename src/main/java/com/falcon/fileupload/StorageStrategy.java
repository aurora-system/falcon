package com.falcon.fileupload;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface StorageStrategy {
    String[] uploadFile(MultipartFile multipartFile, String filename) throws Exception;
    ResponseEntity<Object> downloadFile(String fileUrl, HttpServletRequest request) throws Exception;
}
