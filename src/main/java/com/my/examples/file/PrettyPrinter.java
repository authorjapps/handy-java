package com.my.examples.file;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PrettyPrinter {
    private static List<String> fileNames = new ArrayList<>();
    
    public PrettyPrinter( List<String> fileNames) {
        this.fileNames = fileNames;
    }
    
    public static List<String> getFileNames() {
        return fileNames;
    }
    
    public static void setFileNames(List<String> fileNames) {
        PrettyPrinter.fileNames = fileNames;
    }
    
    public static String prettyPrint() {
        if(fileNames.isEmpty()){
            return "***   NOTHING TO PRINT!   ***";
        }
        
        final int PADDING_RIGHT = 3;
        final String TEST_PACK_GAP = "\n\n";
        
        final int MAX_LENGTH = Collections.max(fileNames, Comparator.comparing(s -> s.length())).length() + PADDING_RIGHT;
        
        final String DOTTED_LINE = dottedLine(MAX_LENGTH);
    
        final String printableReport = tabledReport(MAX_LENGTH, DOTTED_LINE);
    
        // ----------------
        // Reset the report
        // ----------------
        resetReport();
        
        return "\n" + DOTTED_LINE + printableReport + TEST_PACK_GAP;
    }
    
    @Override
    public String toString() {
        return "TestReport{" +
               "fileNames=" + fileNames +
               '}';
    }
    
    private static String tabledReport(int maxLength, String dottedLine) {
        return fileNames.stream().map(testResult -> {
            
                String spaces = extraSpaces(maxLength, testResult);
                
                return "\n| " + testResult + spaces + "|" + "\n" + dottedLine;
            
            }).collect(Collectors.joining(" "));
    }
    
    private static String extraSpaces(int maxLength, String testResult) {
        String spaces = "";
        for (int i = testResult.length(); i < maxLength-1; i++) {
            spaces = spaces + " ";
        }
        return spaces;
    }
    
    private static String dottedLine(int maxLength) {
        String dottedLine = " ";
        for (int i = 0; i < maxLength; i++) {
            dottedLine = dottedLine + "-";
        }
        return dottedLine;
    }
    
    private static void resetReport() {
        PrettyPrinter.getFileNames().clear();
    }
    
}
