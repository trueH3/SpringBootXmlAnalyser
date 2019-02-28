package com.szymon.company.XmlServerSpringBoot;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author szymon
 */
public class AnalyzeControllerUtilities {

    public static List<String>  parseRequestBody(String query) {
        List<String> urls = new ArrayList<>();
        if (query != null && !query.isEmpty()) {
            String pairs[] = query.split("[&]");
            for (String pair : pairs) {
                String param[] = pair.split("[=]");
                if (param.length ==2 && !param[1].isEmpty()) {
                   urls.add(param[1]);
                } else {
                throw new IllegalArgumentException("Wrong structure of Http Request body");
                }
            }
        }
        return urls;
    }
}