package com.natashapetrenko.popularmovies;

/**
 * Created by petrenkonv on 09.11.2017.
 */

public class Review {

    private String mUrl;
    private String mContent;

    public Review(String url, String content) {
        mUrl = url;
        mContent = content;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }
}
