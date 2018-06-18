package com.upload.repository;

import com.upload.model.UploadFile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface UploadFileRepository extends PagingAndSortingRepository<UploadFile, Long> {
}
