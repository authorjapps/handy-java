package com.my.examples.file;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class PrettyPrinterTest {
    
    @Test
    public void testASampleReport_2Failures() throws Exception {
        List<String> fileNames = new ArrayList<>();
        fileNames.add("file.txt");
        fileNames.add("file_222.txt");
        fileNames.add("F.txt");
        
        PrettyPrinter.setFileNames(fileNames);
    
        final String finalString = PrettyPrinter.prettyPrint();
        System.out.println(finalString);
        
        assertThat(finalString, notNullValue());
        assertThat(StringUtils.countMatches(finalString, " ---------------"), is(4));
        assertThat(finalString.indexOf("---------------"), is(2));
        assertThat(StringUtils.countMatches(finalString, "| "), is(3));
        assertThat(StringUtils.countMatches(finalString, " |"), is(3));
        assertThat(StringUtils.countMatches(finalString, "|"), is(6));
    }
}