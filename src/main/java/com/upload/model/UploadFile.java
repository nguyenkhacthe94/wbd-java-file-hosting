package com.upload.model;

import javax.persistence.*;

@Entity
@Table(name = "files")
public class UploadFile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String uploadedFile;
    private String description;
    private boolean isPublic;

    public UploadFile() {
    }

    public UploadFile(String name, String uploadedFile, String description, boolean isPublic) {
        this.name = name;
        this.uploadedFile = uploadedFile;
        this.description = description;
        this.isPublic = isPublic;
    }

    public UploadFile(Long id, String name, String uploadedFile, String description, boolean isPublic) {
        this.id = id;
        this.name = name;
        this.uploadedFile = uploadedFile;
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

    public String getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(String uploadedFile) {
        this.uploadedFile = uploadedFile;
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
