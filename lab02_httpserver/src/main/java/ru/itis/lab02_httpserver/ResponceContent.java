package ru.itis.lab02_httpserver;

public class ResponceContent {
    String mimeType;
    byte[] content;

    public ResponceContent(String mimeType, byte[] content) {
        this.mimeType = mimeType;
        this.content = content;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}

