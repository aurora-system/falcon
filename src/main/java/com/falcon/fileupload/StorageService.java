package com.falcon.fileupload;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StorageService {
    private final StorageStrategy storageStrategy;

    public StorageService(StorageFactory storageFactory) {
        this.storageStrategy = storageFactory.createStrategy();
    }

    public FileDTO uploadFile(MultipartFile file) throws Exception {
        String[] uploadedFile = this.storageStrategy.uploadFile(file);
        String fileDownloadUri = uploadedFile[0];
        String fileName = uploadedFile[1];
        log.info("fileDownloadUri, {0}" + fileDownloadUri);
        log.info("filename, {0}" + fileName);

        return new FileDTO(
                fileName,
                file.getContentType(),
                fileDownloadUri, file.getSize());
    }
    
    public ResponseEntity<Object> downloadFile(String fileUrl, HttpServletRequest request) throws Exception {
        return this.storageStrategy.downloadFile(fileUrl, request);
    }
}