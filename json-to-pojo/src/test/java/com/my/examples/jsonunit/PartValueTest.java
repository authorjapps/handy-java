package com.my.examples.jsonunit;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;

public class PartValueTest {
    
    /**
     // Try these Regex:
     Two passport photos for ([a-zA-Z])\w+ DCJCID-FOURTEEN
     Two passport photos for ([0-9])\w+ DCJCID-FOURTEEN
     Two passport photos for ([a-z]*[0-9])\w+ DCJCID-FOURTEEN
     Two passport photos for ([a-zA-Z0-9])\w+ DCJCID-FOURTEEN
 
     // Strings to match
     "documentDescription": "Two passport photos for Kusum DCJCID-FOURTEEN",
     "documentDescription": "Two passport photos for 1254 DCJCID-FOURTEEN",
     "documentDescription": "Two passport photos for Scott DCJCID-FOURTEEN",
     "documentDescription": "Two passport photos for Scott39 DCJCID-FOURTEEN",
     "documentDescription": "Two passport photos for 9865809 DCJCID-FOURTEEN",
 
     * @throws Exception
     */
    @Test
    public void testPartWith_Regex() throws Exception {
        assertTrue(true);
        assertJsonEquals("{\"test\": \"${json-unit.regex}[A-Z]+\"}",
                        "{\"test\": \"ABCD\"}");
        
        assertJsonEquals("{\"test\": \"${json-unit.regex}Two passport photos for \\\\w+ DCJCID-FOURTEEN\"}",
                        "{\"test\": \"Two passport photos for Kusum DCJCID-FOURTEEN\"}");
        
    }
    
    
}
