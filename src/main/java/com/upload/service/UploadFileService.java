package com.upload.service;

import com.upload.model.UploadFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UploadFileService {
    Iterable<UploadFile> findAll();

    UploadFile findById(Long id);

    UploadFile save(UploadFile file);

    void delete (Long id);
}
