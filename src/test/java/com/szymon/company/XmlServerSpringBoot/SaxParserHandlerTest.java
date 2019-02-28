package com.szymon.company.XmlServerSpringBoot;

import java.time.LocalDateTime;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.xml.sax.Attributes;

/**
 *
 * @author szymon
 */
@RunWith(MockitoJUnitRunner.class)
public class SaxParserHandlerTest {
    
 
    @Mock
    Attributes atrbts;

    @Mock
    Attributes atrbts2;

    private SaxParserHandler sph;

    @Before
    public void init() {
        //Given
        sph = new SaxParserHandler();
        Mockito.when(atrbts.getValue("CreationDate")).thenReturn(LocalDateTime.now().toString());
        Mockito.when(atrbts.getValue("AcceptedAnswerId")).thenReturn("3");
        Mockito.when(atrbts.getValue("Score")).thenReturn("7");
        Mockito.when(atrbts2.getValue("CreationDate")).thenReturn(LocalDateTime.now().plusYears(1).toString());
        Mockito.when(atrbts2.getValue("AcceptedAnswerId")).thenReturn("2");
        Mockito.when(atrbts2.getValue("Score")).thenReturn("2");
    }

    @Test
    public void shouldReturnCorrectValues() {

        //When
        sph.startElement(null, null, "row", atrbts);
        sph.startElement(null, null, "row", atrbts2);
        sph.startElement(null, null, "otherRow", atrbts);
        //Then
        assertEquals(2L, sph.getTotalPosts());
        assertEquals(2L, sph.getTotalAcceptedPosts());
        assertEquals(5, sph.computeAvgScore());
        assertTrue(sph.getFirstPost().isBefore(sph.getLastPost()));
    }

    @Test
    public void shouldReturnZeroAndNullValues() {
        
        //When
        sph.startElement(null, null, "notRow", atrbts);
        sph.startElement(null, null, "anotherRow", atrbts2);
        sph.startElement(null, null, "otherRow", atrbts);
        //Then
        assertEquals(0, sph.getTotalPosts());
        assertEquals(0, sph.getTotalAcceptedPosts());
        assertEquals(0, sph.computeAvgScore());
        assertNull(sph.getFirstPost());
        assertNull(sph.getLastPost());
    }   
}
