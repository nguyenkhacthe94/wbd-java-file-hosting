package com.upload.controller.api;

import com.upload.model.UploadFile;
import com.upload.service.upload.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UploadFileControllerApi {
    @Autowired
    UploadFileService uploadFileService;

    @PostMapping("/{id}/status")
    public ResponseEntity<UploadFile> changePrivacyStatus(@PathVariable("id") Long id) {
        UploadFile uploadFile = uploadFileService.findById(id);
        boolean isPublic = uploadFile.isPublic();
        uploadFile.setPublic(!isPublic);
        return new ResponseEntity<UploadFile>(uploadFile, HttpStatus.OK);
    }

}
