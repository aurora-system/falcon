package com.falcon.fileupload;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/file")
public class StorageController {

    private StorageService storageService;

    public StorageController(StorageService storageService
            ) {
        this.storageService = storageService;
    }


    @PostMapping("/upload")
    @Transactional
    public ResponseEntity<FileDTO> uploadFile(@RequestParam("file") MultipartFile file,
            @PathVariable long productId
            , HttpServletRequest request
            ) throws Exception {
        log.info("REST request to upload file");
        //upload files
        FileDTO fileDTO = this.storageService.uploadFile(file, file.getOriginalFilename());
        request.getUserPrincipal().getName();
        return new ResponseEntity<>(fileDTO, null, HttpStatus.OK);
    }


    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Object> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws Exception {
        return this.storageService.downloadFile(fileName, request);
    }
}