package com.my.examples.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.activation.MimetypesFileTypeMap;

public class FolderService {
    
    public List<FileMetaData> listAllFiles(String folderPath) {
        
        List<FileMetaData> fileList;
        
        try {
            fileList = Files.walk(Paths.get(folderPath))
                            .filter(Files::isRegularFile)
                            .map(filePath -> new FileMetaData(
                                            filePath.getFileName().toString(),
                                            getMimeType(filePath),
                                            getSize(filePath),
                                            getExtension(filePath)
                            
                            )).collect(Collectors.toList());
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        return fileList;
    }
    
    public List<FileMetaData> listAllUnSupportedFiles(String folderName) throws IOException {
        
        final List<FileMetaData> allFiles = listAllFiles(folderName);
        final List<String> unsupportedFileTypes = unSupportedFilesFromConfig();
        
        return allFiles.stream()
                        .filter(thisFile -> unsupportedFileTypes.contains(thisFile.getFileExtention()))
                        .collect(Collectors.toList());
    }
    
    public List<FileMetaData> listAllSupportedFiles(String folderName) throws IOException {
        
        final List<FileMetaData> allFiles = listAllFiles(folderName);
        final List<String> unsupportedFileTypes = unSupportedFilesFromConfig();
        
        return allFiles.stream()
                        .filter(thisFile -> !unsupportedFileTypes.contains(thisFile.getFileExtention()))
                        .collect(Collectors.toList());
    }
    
    public List<String> unSupportedFilesFromConfig() throws IOException {
        
        final String unSupportedFilesByComma = valueFromConfig("unsupported.file.types");
        
        return Arrays.asList(unSupportedFilesByComma.split(","));
    }
    
    public String baseFolderFromConfig(){
        return valueFromConfig("test.base.folder");
    }
    
    public String valueFromConfig(String configKey){
        Properties properties = null;
        try {
            properties = loadProperties("service_config.properties");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        
        final String configValue = properties.get(configKey).toString();
        
        return configValue;
    }
    
    public String prettyPrintable(List<FileMetaData> files) {
        
        final List<String> fileNames = files.stream().map(thisFile -> thisFile.getFileName()).collect(Collectors.toList());
        
        PrettyPrinter.setFileNames(fileNames);
        
        return PrettyPrinter.prettyPrint();
    }

    // ---------------------
    // Private methods area
    // ---------------------
    
    private Properties loadProperties(String propFile) throws IOException {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream(propFile));
        
        return properties;
    }
    
    private String getMimeType(Path path) {
        //return Files.probeContentType(path.toAbsolutePath()) != null ? Files.probeContentType(path) : "UNDEFINED-MIME";
        return new MimetypesFileTypeMap().getContentType(path.getFileName().toString()) ;
    }
    
    private String getExtension(Path filePath) {
        final String fileName = filePath.getFileName().toString();
        
        if(fileName != null && !fileName.isEmpty()){
            return fileName.substring(fileName.lastIndexOf("."));
        }
        
        return null;
    }
    
    private Long getSize(Path filePath) {
        long size;
        try {
            size = Files.readAttributes(filePath, BasicFileAttributes.class).size();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return size;
    }
    
}
