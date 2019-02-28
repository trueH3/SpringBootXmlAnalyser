package com.szymon.company.XmlServerSpringBoot;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author szymon
 */
@RestController
public class AnalyzeController  {
    
  
    
    @PostMapping(value = "/analyze", consumes = "text/HTML")
    public Map<String, Object> produceDataFromText(@RequestBody String data) {
        List<String> urls = AnalyzeControllerUtilities.parseRequestBody(data);
        List<Result> results = new ArrayList<>();
        urls.forEach(a -> results.add(XmlSaxParser.parseXml(a)));
        Map<String, Object> dataMap = new LinkedHashMap<>();
        dataMap.put("analyseDate", LocalDateTime.now());
        dataMap.put("details", results);
        return dataMap;
    }
    
    @PostMapping(value = "/analyze", consumes = "application/json")
    public Map<String, Object> produceDataFromJson(@RequestBody Map<String, Object> data){
        List<Result> results = new ArrayList<>();
        data.forEach((k, v) -> results.add(XmlSaxParser.parseXml((String)v)));
        data = new LinkedHashMap<>();
        data.put("analyseDate", LocalDateTime.now());
        data.put("details", results);
        return data;
    }
}