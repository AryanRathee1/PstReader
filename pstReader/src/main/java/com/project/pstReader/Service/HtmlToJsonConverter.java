package com.project.pstReader.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class HtmlToJsonConverter {

    public String convertHtmlToJson(String htmlContent) throws IOException {
        Document document = Jsoup.parse(htmlContent);

        // Extract the body content as plain text
        Element body = document.body();
        String bodyText = body.text();

        // Create a map or any other structure you want to convert to JSON
        Map<String, String> emailContentMap = new HashMap<>();
        emailContentMap.put("body", bodyText);

        // Convert the map to a JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(emailContentMap);
    }
}
