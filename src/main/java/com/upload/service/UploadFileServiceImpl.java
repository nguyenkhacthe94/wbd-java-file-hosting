package com.upload.service;

import com.upload.model.UploadFile;
import com.upload.repository.UploadFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class UploadFileServiceImpl implements UploadFileService {
    @Autowired
    UploadFileRepository uploadFileRepository;

    @Override
    public Page<UploadFile> findAll(Pageable pageable) {
        return uploadFileRepository.findAll(pageable);
    }

    @Override
    public UploadFile findById(Long id) {
        return uploadFileRepository.findOne(id);
    }

    @Override
    public UploadFile save(UploadFile file) {
        return uploadFileRepository.save(file);
    }

    @Override
    public void delete(Long id) {
        uploadFileRepository.delete(id);
    }
}
