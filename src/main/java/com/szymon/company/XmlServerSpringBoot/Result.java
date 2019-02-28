package com.szymon.company.XmlServerSpringBoot;

import java.time.LocalDateTime;

/**
 *
 * @author szymon
 */
public class Result {

    private final  String url;
    private final  long totalPosts;
    private final  LocalDateTime firstPost;
    private final  LocalDateTime lastPost;
    private final  long totalAcceptedPosts;
    private final  int avgScore;

    public Result(String url, long totalPosts, LocalDateTime firstPost, LocalDateTime lastPost, long totalAcceptedPosts, int avgScore) {
        this.url= url;
        this.totalPosts = totalPosts;
        this.firstPost = firstPost;
        this.lastPost = lastPost;
        this.totalAcceptedPosts = totalAcceptedPosts;
        this.avgScore = avgScore;
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

    public int getAvgScore() {
        return avgScore;
    }  

    public String getUrl() {
        return url;
    }
    
}