package com.upload.model;

import org.springframework.web.multipart.MultipartFile;

public class UploadFileForm {
    private Long id;
    private String name;
    private MultipartFile uploadedFile;
    private String uploadedFileUrl;
    private String description;
    private boolean isPublic;

    public UploadFileForm() {
    }

    public UploadFileForm(Long id, String name, MultipartFile uploadedFile, String uploadedFileUrl, String description, boolean isPublic) {
        this.id = id;
        this.name = name;
        this.uploadedFile = uploadedFile;
        this.uploadedFileUrl = uploadedFileUrl;
        this.description = description;
        this.isPublic = isPublic;
    }

    public UploadFileForm(String name, MultipartFile uploadedFile, String uploadedFileUrl, String description, boolean isPublic) {
        this.name = name;
        this.uploadedFile = uploadedFile;
        this.uploadedFileUrl = uploadedFileUrl;
        this.description = description;
        this.isPublic = isPublic;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultipartFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(MultipartFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public String getUploadedFileUrl() {
        return uploadedFileUrl;
    }

    public void setUploadedFileUrl(String uploadedFileUrl) {
        this.uploadedFileUrl = uploadedFileUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }
}
