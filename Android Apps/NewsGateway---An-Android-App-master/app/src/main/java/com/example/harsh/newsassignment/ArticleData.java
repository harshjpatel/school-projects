package com.example.harsh.newsassignment;

import java.io.Serializable;

/**
 * Created by harsh on 5/2/17.
 */

class ArticleData implements Serializable
{
    private String author;
    private String title;
    private String description;
    private String urlToImage;
    private String publishedAt;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String url;

    public ArticleData(String author, String title, String description, String urlToImage, String publishedAt, String url)
    {
        this.author = author;
        this.title = title;
        this.description = description;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.url = url;
    }

    public ArticleData(String author, String title, String description, String urlToImage)
    {
        this.author = author;
        this.title = title;
        this.description = description;
        this.urlToImage = urlToImage;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
}
