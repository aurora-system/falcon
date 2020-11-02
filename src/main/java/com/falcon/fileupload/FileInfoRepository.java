package com.falcon.fileupload;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface FileInfoRepository extends PagingAndSortingRepository<FileInfo, Long> {
	List<FileInfo> findAllByProductId(long productId);
}
