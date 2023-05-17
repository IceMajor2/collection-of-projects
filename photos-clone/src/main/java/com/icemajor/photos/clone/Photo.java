package com.icemajor.photos.clone;

import jakarta.validation.constraints.NotEmpty;

public class Photo {

    private String id;
    @NotEmpty
    private String fileName;
    private byte[] data;

    public Photo() {}

    public Photo(String id, String fileName) {
        this.id = id;
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
