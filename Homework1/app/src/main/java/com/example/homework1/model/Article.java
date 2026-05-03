package com.example.homework1.model;

import com.google.gson.annotations.SerializedName;

public class Article {
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("urlToImage")
    private String urlToImage;
    @SerializedName("publishedAt")
    private String publishedAt;
    @SerializedName("source")
    private Source source;

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getUrlToImage() { return urlToImage; }
    public String getPublishedAt() { return publishedAt; }
    public Source getSource() { return source; }

    public static class Source {
        @SerializedName("name")
        private String name;
        public String getName() { return name; }
    }
}
