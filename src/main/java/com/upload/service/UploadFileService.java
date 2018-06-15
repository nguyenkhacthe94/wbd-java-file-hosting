package com.upload.service;

import com.upload.model.UploadFile;

public interface UploadFileService {
    Iterable<UploadFile> findAll();

    UploadFile findById(Long id);

    UploadFile save(UploadFile file);

    void delete (Long id);
}
