package com.szymon.company.XmlServerSpringBoot;

import java.time.LocalDateTime;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author szymon
 */
public class SaxParserHandler extends DefaultHandler {

    private long totalPosts;
    private LocalDateTime firstPost;
    private LocalDateTime lastPost;
    private long totalAcceptedPosts;
    private long sumOfScore;
    private long scoredPosts;

    @Override
    public void startElement(String string, String string1, String string2, Attributes atrbts) {
        if (string2.equalsIgnoreCase("row")) {
            totalPosts++;

            LocalDateTime temp = LocalDateTime.parse(atrbts.getValue("CreationDate"));

            if (totalPosts == 1) {
                firstPost = temp;
            }

            if (temp.isAfter(firstPost)) {
                lastPost = temp;
            }

            if (atrbts.getValue("AcceptedAnswerId") != null) {
                totalAcceptedPosts++;
            }

            if (atrbts.getValue("Score") != null) {
                sumOfScore += Integer.parseInt(atrbts.getValue("Score"));
                scoredPosts++;
            }
        }
    }

    public int computeAvgScore() {
        return scoredPosts != 0 ? Math.round(sumOfScore / (float) scoredPosts) : 0;
    }

    public long getTotalPosts() {
        return totalPosts;
    }

    public LocalDateTime getFirstPost() {
        return firstPost;
    }

    public LocalDateTime getLastPost() {
        return lastPost;
    }

    public long getTotalAcceptedPosts() {
        return totalAcceptedPosts;
    }
}
