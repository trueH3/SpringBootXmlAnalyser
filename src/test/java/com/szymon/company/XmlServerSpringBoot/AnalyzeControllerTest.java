package com.szymon.company.XmlServerSpringBoot;

import java.time.LocalDateTime;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author szymon
 */
@RunWith(SpringRunner.class)
@WebMvcTest
public class AnalyzeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private XmlSaxParser parserService;

    @Before
    public void init() {

        //Given
        Mockito.when(parserService.parseXml("http://example.com")).thenReturn(new Result("http://example.com", 7, LocalDateTime.of(1988, 5, 8, 12, 33, 22, 1), LocalDateTime.of(2019, 3, 4, 15, 12, 11, 7), 4, 2));
    }

    @Test
    public void shouldReturnStatus200WithWithRequestBodyJson() throws Exception {

        //Then
        mockMvc.perform(MockMvcRequestBuilders
                .post("/analyze")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"url1\":\"http://example.com\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void shouldContainCorrectValWithRequestBodyJson() throws Exception {

        //When
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/analyze")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"url1\":\"http://example.com\"}"))
                .andReturn();
        //Then
        String resultString = result.getResponse().getContentAsString();

        assertThat(resultString, Matchers.containsString("\"analyseDate\":"));
        assertThat(resultString, Matchers.containsString("\"url\":\"http://example.com\""));
        assertThat(resultString, Matchers.containsString("\"totalPosts\":7"));
        assertThat(resultString, Matchers.containsString("\"firstPost\":\"1988-05-08T12:33:22.000000001\""));
        assertThat(resultString, Matchers.containsString("\"lastPost\":\"2019-03-04T15:12:11.000000007\""));
        assertThat(resultString, Matchers.containsString("\"totalAcceptedPosts\":4"));
        assertThat(resultString, Matchers.containsString("\"avgScore\":2"));
    }

    @Test
    public void shouldReturnStatus200WithWithRequestBodyText() throws Exception {

        //Then
        mockMvc.perform(MockMvcRequestBuilders
                .post("/analyze")
                .contentType(MediaType.TEXT_HTML)
                .content("url1=http://example.com"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void shouldContainCorrectValWithRequestBodyText() throws Exception {

        //When
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/analyze")
                .contentType(MediaType.TEXT_HTML)
                .content("url1=http://example.com"))
                .andReturn();
        //Then
        String resultString = result.getResponse().getContentAsString();

        assertThat(resultString, Matchers.containsString("\"analyseDate\":"));
        assertThat(resultString, Matchers.containsString("\"url\":\"http://example.com\""));
        assertThat(resultString, Matchers.containsString("\"totalPosts\":7"));
        assertThat(resultString, Matchers.containsString("\"firstPost\":\"1988-05-08T12:33:22.000000001\""));
        assertThat(resultString, Matchers.containsString("\"lastPost\":\"2019-03-04T15:12:11.000000007\""));
        assertThat(resultString, Matchers.containsString("\"totalAcceptedPosts\":4"));
        assertThat(resultString, Matchers.containsString("\"avgScore\":2"));
    }
}