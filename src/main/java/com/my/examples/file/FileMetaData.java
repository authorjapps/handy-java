package com.my.examples.file;

public class FileMetaData {
    private String fileName;
    private String mimeType;
    private Long fileSize;
    private String fileExtention;
    
    public FileMetaData(String fileName, String mimeType, Long fileSize, String fileExtention) {
        this.fileName = fileName;
        this.mimeType = mimeType;
        this.fileSize = fileSize;
        this.fileExtention = fileExtention;
    }
    
    public String getFileName() {
        return fileName;
    }
    
    public String getMimeType() {
        return mimeType;
    }
    
    public Long getFileSize() {
        return fileSize;
    }
    
    public String getFileExtention() {
        return fileExtention;
    }
    
    @Override
    public String toString() {
        return "FileData{" +
               "fileName='" + fileName + '\'' +
               ", mimeType='" + mimeType + '\'' +
               ", fileSize=" + fileSize +
               ", fileExtention='" + fileExtention + '\'' +
               '}';
    }
}
