package com.falcon.fileupload;

import java.time.LocalDate;

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
@RequestMapping("/api")
public class StorageController {

    private StorageService storageService;
    private FileInfoRepository fileInfoRepository;

    public StorageController(StorageService storageService,
    		FileInfoRepository fileInfoRepository
    		) {
        this.storageService = storageService;
        this.fileInfoRepository = fileInfoRepository;
    }


    @PostMapping("/upload")
    @Transactional
    public ResponseEntity<FileDTO> uploadFile(@RequestParam("file") MultipartFile file,
    		@PathVariable long productId
    		, HttpServletRequest request
    		) throws Exception {
        log.info("REST request to upload file");
        //upload files
        FileDTO fileDTO = storageService.uploadFile(file);
        String username = request.getUserPrincipal().getName();
        fileInfoRepository.save(toFileInfo(fileDTO, username, productId));
        return new ResponseEntity<>(fileDTO, null, HttpStatus.OK);
    }


    private FileInfo toFileInfo(FileDTO fileDTO, String username, long productId) {
		return new FileInfo(0L,
				fileDTO.getFilename(),
				fileDTO.getContentType(),
				fileDTO.getDownloadUri(),
				fileDTO.getFileSize(),
				LocalDate.now(),
				username,
				productId
				);
	}


	@GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Object> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws Exception {
        return storageService.downloadFile(fileName, request);
    }
}