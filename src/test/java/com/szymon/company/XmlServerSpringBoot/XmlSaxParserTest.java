
package com.szymon.company.XmlServerSpringBoot;

import java.time.LocalDateTime;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author szymon
 */
public class XmlSaxParserTest {
    
   @Test
    public void shouldGiveCorrectValGivenUrl() {

        //Given
        String string = "https://trueh3.github.io/remoteTestFile.xml";
        //When
        Result result = XmlSaxParser.parseXml(string);
        //Then
        assertEquals(string, result.getUrl());
        assertEquals(10L, result.getTotalPosts());
        assertEquals(LocalDateTime.of(2017, 8, 3, 16, 32, 47, 943000000), result.getFirstPost());
        assertEquals(LocalDateTime.of(2017, 8, 7, 19, 30, 58, 747000000), result.getLastPost());
        assertEquals(1L, result.getTotalAcceptedPosts());
        assertEquals(8, result.getAvgScore());
    }

    @Test
    public void shouldGiveCorrectValGivenPathToLocalFile() {

        //Given
        String string = "./src/test/java/com/szymon/company/XmlServerSpringBoot/localTestFile.xml";
        //When
        Result result = XmlSaxParser.parseXml(string);
        //Then
        assertEquals(string, result.getUrl());
        assertEquals(8L, result.getTotalPosts());
        assertEquals(LocalDateTime.of(2017, 8, 5, 4, 47, 45, 557000000), result.getFirstPost());
        assertEquals(LocalDateTime.of(2017, 8, 7, 7, 22, 20, 770000000), result.getLastPost());
        assertEquals(0L, result.getTotalAcceptedPosts());
        assertEquals(7, result.getAvgScore());
    }
}
