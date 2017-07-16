package com.my.examples.file;

import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class FolderServiceTest {
    static String TEST_FOLDER_BASE = new FolderService().baseFolderFromConfig();
    final String TEST_FOLDER_LOCATION = TEST_FOLDER_BASE + "/test_folder";
    final String TEST_FOLDER_LOCATION_GIF = TEST_FOLDER_BASE + "/test_folder_gif";
    final String TEST_FOLDER_LOCATION_15 = TEST_FOLDER_BASE + "/test_folder_15";
    final String TEST_FOLDER_LOCATION_UNSUPP = TEST_FOLDER_BASE + "/test_unsupp";
    final String TEST_FOLDER_LOCATION_DEPTH = TEST_FOLDER_BASE + "/test_folder_depth";
     
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    private FolderService folderService;
    
    @Before
    public void setUpStuff() throws Exception {
        folderService = new FolderService();
        //TEST_FOLDER_BASE = folderService.baseFolderFromConfig();
    }
    
    @Test
    public void test_invalidFolderName() throws Exception {
    
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(".NoSuchFileException: /invalid/folder/name");
    
        folderService.listAllFiles("/invalid/folder/name");
    }
    
    @Test
    public void test_getAllFiles() throws Exception {
    
        List<FileMetaData> allFiles =  folderService.listAllFiles(TEST_FOLDER_LOCATION);
        
        assertThat(allFiles.size(), is(1));
        assertThat(allFiles.get(0).getFileName(), is("file_1.txt"));
        assertThat(allFiles.get(0).getFileExtention(), is(".txt"));
        assertThat(allFiles.get(0).getFileSize(), is(24L));
        assertThat(allFiles.get(0).getMimeType(), is("text/plain"));
    }
    
    @Test
    public void test_getAllFilesWith_gif() throws Exception {
        
        List<FileMetaData> allFiles =  folderService.listAllFiles(TEST_FOLDER_LOCATION_GIF);
        
        assertThat(allFiles.size(), is(1));
        assertThat(allFiles.get(0).getFileName(), is("giphy.gif"));
        assertThat(allFiles.get(0).getFileExtention(), is(".gif"));
        assertThat(allFiles.get(0).getFileSize(), is(592907L));
        assertThat(allFiles.get(0).getMimeType(), is("image/gif"));
    }
    
    @Test
    public void testGetAll_15Files() throws Exception {
        
        List<FileMetaData> allFiles =  folderService.listAllFiles(TEST_FOLDER_LOCATION_15);
        
        assertThat(allFiles.size(), is(15));
        assertThat(allFiles.get(0).getFileName(), is("file_1 copy 10.txt"));
        assertThat(allFiles.get(0).getFileExtention(), is(".txt"));
        assertThat(allFiles.get(0).getFileSize(), is(24L));
        assertThat(allFiles.get(0).getMimeType(), is("text/plain"));
    
        assertThat(allFiles.get(1).getFileName(), is("file_1 copy 11.txt"));
        assertThat(allFiles.get(1).getFileExtention(), is(".txt"));
        assertThat(allFiles.get(1).getFileSize(), is(34L));
        assertThat(allFiles.get(1).getMimeType(), is("text/plain"));
    }
    
    @Test
    public void test_unSupportedFilesFromConfig() throws Exception {
        List<String> fileTypes =  folderService.unSupportedFilesFromConfig();
    
        assertThat(fileTypes.size(), is(3));
        assertThat(fileTypes.get(0), is(".iml"));
        assertThat(fileTypes.get(1), is(".xyz"));
        assertThat(fileTypes.get(2), is(".docx"));
    }
    
    @Test
    public void test_getBaseFolderFromConfig() throws Exception {
        String baseFolderFromConfig =  folderService.baseFolderFromConfig();
        assertThat(baseFolderFromConfig, containsString("/src/test/resources"));
    
        String value =  folderService.valueFromConfig("test.base.folder");
        assertThat(value, containsString("/src/test/resources"));
    }
    
    @Test
    public void test_listAllUnSupportedFiles() throws Exception {
        List<FileMetaData> allFiles =  folderService.listAllUnSupportedFiles(TEST_FOLDER_LOCATION_UNSUPP);
    
        assertThat(allFiles.get(0).getFileName(), is("unsupp_x.docx"));
        assertThat(allFiles.get(0).getFileExtention(), is(".docx"));
        assertThat(allFiles.get(0).getFileSize(), is(24L));
        assertThat(allFiles.get(0).getMimeType(), is("application/octet-stream"));
    }
    
    @Test
    public void test_listAllSupported() throws Exception {
        List<FileMetaData> allFiles =  folderService.listAllSupportedFiles(TEST_FOLDER_LOCATION_UNSUPP);
        
        assertThat(allFiles.get(0).getFileName(), is("file_1.txt"));
        assertThat(allFiles.get(0).getFileExtention(), is(".txt"));
        assertThat(allFiles.get(0).getFileSize(), is(24L));
        assertThat(allFiles.get(0).getMimeType(), is("text/plain"));
    }
    
    @Test
    public void test_prettyPrint() throws Exception {
        List<FileMetaData> allSuppFiles =  folderService.listAllSupportedFiles(TEST_FOLDER_LOCATION_UNSUPP);
        String prettyPrintableSupp =  folderService.prettyPrintable(allSuppFiles);
        assertNotNull(prettyPrintableSupp);
        assertTrue(prettyPrintableSupp.contains("file_1.txt"));
    
        List<FileMetaData> allUnSuppFiles =  folderService.listAllUnSupportedFiles(TEST_FOLDER_LOCATION_UNSUPP);
        String prettyPrintableUnSupp =  folderService.prettyPrintable(allUnSuppFiles);
        assertNotNull(prettyPrintableUnSupp);
        assertTrue(prettyPrintableUnSupp.contains("unsupp_x.docx"));
    
    }
    
    @Test
    public void test_getAllFilesFrom_depth() throws Exception {
        
        List<FileMetaData> allFiles =  folderService.listAllFiles(TEST_FOLDER_LOCATION_DEPTH);
        
        assertThat(allFiles.size(), is(2));
        assertThat(allFiles.get(0).getFileName(), is("file_level1.txt"));
        assertThat(allFiles.get(0).getFileExtention(), is(".txt"));
        assertThat(allFiles.get(0).getFileSize(), is(24L));
        assertThat(allFiles.get(0).getMimeType(), is("text/plain"));
    
        assertThat(allFiles.get(1).getFileName(), is("file_level2.gif"));
        assertThat(allFiles.get(1).getFileExtention(), is(".gif"));
        assertThat(allFiles.get(1).getFileSize(), is(592907L));
        assertThat(allFiles.get(1).getMimeType(), is("image/gif"));
    }
}