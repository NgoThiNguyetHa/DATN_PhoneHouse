package com.example.appkhachhang.Model;

public class FileData {
    private String type;
    private byte[] buffer;

    public FileData(String type, byte[] buffer) {
        this.type = type;
        this.buffer = buffer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getBuffer() {
        return buffer;
    }

    public void setBuffer(byte[] buffer) {
        this.buffer = buffer;
    }
}
