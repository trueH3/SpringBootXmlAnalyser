package com.szymon.company.XmlServerSpringBoot;

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author szymon
 */
public class AnalyzeControllerUtilitiesTest {
    
    @Test
    public void shouldReturnCorrectValues() {
        
        //Given
        String s = "url1=aaaa&url2=bbbb";
        //When
        List<String> list = AnalyzeControllerUtilities.parseRequestBody(s);
        //Then
        assertEquals(2, list.size());
        assertEquals("aaaa", list.get(0));
        assertEquals("bbbb", list.get(1));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowException(){
    
        //Given
        String s = "url1=aaurl2=bbbb";
        //When
        List<String> list = AnalyzeControllerUtilities.parseRequestBody(s);
    }
}
