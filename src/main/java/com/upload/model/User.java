package com.upload.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    private boolean isAdmin;

    @OneToMany
    private List<UploadFile> uploadFiles;

    public User() {
    }

    public User(Long id, String username, String password, boolean isAdmin, List<UploadFile> uploadFiles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
        this.uploadFiles = uploadFiles;
    }

    public User(String username, String password, boolean isAdmin, List<UploadFile> uploadFiles) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
        this.uploadFiles = uploadFiles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public List<UploadFile> getUploadFiles() {
        return uploadFiles;
    }

    public void setUploadFiles(List<UploadFile> uploadFiles) {
        this.uploadFiles = uploadFiles;
    }
}
