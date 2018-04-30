package com.example.PermissionGrantingTool.Entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = FileRequest.COLLECTION_NAME)
public class FileRequest {

    public static final String COLLECTION_NAME = "FileCollection" ;
    private String userId;
    private String fileName;
    private String sensitivity;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSensitivity() {
        return sensitivity;
    }

    public void setSensitivity(String sensitivity) {
        this.sensitivity = sensitivity;
    }
}
