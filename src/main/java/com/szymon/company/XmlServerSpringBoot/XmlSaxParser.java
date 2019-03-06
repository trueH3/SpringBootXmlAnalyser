package com.szymon.company.XmlServerSpringBoot;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

/**
 *
 * @author szymon
 */
@Service
public class XmlSaxParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlSaxParser.class);

    public Result parseXml(String stringUrl) {
        
        LOGGER.info("Starting to parse: " + stringUrl);
        
        SaxParserHandler myHandler = null;
        try {
            System.setProperty("jdk.xml.totalEntitySizeLimit", "0");
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            myHandler = new SaxParserHandler();
            if (stringUrl.startsWith("http")) {
                URL url = new URL(stringUrl);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                saxParser.parse(con.getInputStream(), myHandler);
            } else {
                saxParser.parse(new File(stringUrl), myHandler);
            }

        } catch (MalformedURLException ex) {
            LOGGER.error("Check url in request body, response result may be incorrect " + stringUrl, ex);
        } catch (ProtocolException | ParserConfigurationException | SAXException ex) {
            LOGGER.error("Error occured during parsing, response result may be incorrect  " + stringUrl, ex);
        } catch (IOException ex) {
            LOGGER.error("Problems occured  streaming input stream, response result may be incorrect  " + stringUrl, ex);
        }

        LOGGER.info("Finished parsing: " + stringUrl);
        System.clearProperty("jdk.xml.totalEntitySizeLimit");

        return new Result(stringUrl, myHandler.getTotalPosts(), myHandler.getFirstPost(), myHandler.getLastPost(),
                myHandler.getTotalAcceptedPosts(), myHandler.computeAvgScore());
    }
}
