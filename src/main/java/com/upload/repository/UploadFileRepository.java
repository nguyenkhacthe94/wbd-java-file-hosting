package com.upload.repository;

import com.upload.model.UploadFile;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UploadFileRepository extends PagingAndSortingRepository<UploadFile, Long> {
}
