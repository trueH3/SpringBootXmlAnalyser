package com.szymon.company.XmlServerSpringBoot;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author szymon
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AnalyzeControllerTest {

    @Autowired
    AnalyzeController controller;

    @Test
    public void checkIfReturnedMapContainsProperKeyVal() {

        //When
        Map<String, Object> map = controller.produceDataFromText("url1=https://trueh3.github.io/remoteTestFile.xml");
        //Then
        assertFalse(map.isEmpty());
        assertTrue(map.containsKey("analyseDate"));
        assertTrue(map.containsKey("details"));
        assertTrue(map.get("analyseDate") instanceof LocalDateTime);
        assertTrue(map.get("details") instanceof ArrayList);
    }

    @Test
    public void shouldReturnCorrectValues() {

        //Given
        Map <String, Object> inputMap = new HashMap<>();
        inputMap.put("url1", "https://trueh3.github.io/remoteTestFile.xml");
        //When
        Map<String, Object> outputMap = controller.produceDataFromJson(inputMap);
        List<Result> list = (List)outputMap.get("details");
        Result r = list.get(0);
        //Then
        assertEquals(10L, r.getTotalPosts());
        assertEquals(LocalDateTime.of(2017, 8, 3, 16, 32, 47, 943000000), r.getFirstPost());
        assertEquals(LocalDateTime.of(2017, 8, 7, 19, 30, 58, 747000000), r.getLastPost());
        assertEquals(1L, r.getTotalAcceptedPosts());
        assertEquals(8, r.getAvgScore());
    }
}