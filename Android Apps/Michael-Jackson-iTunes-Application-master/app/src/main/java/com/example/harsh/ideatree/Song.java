package com.example.harsh.ideatree;

import android.widget.ImageView;

import java.io.Serializable;

//Song Java Beans Class
class Song implements Serializable
{

    private String songname;
    private String collectionname;
    private String price;
    private String image;
    private String collectionId;
    private String trackId;
    private String releaseDate;
    private String trackCount;
    private String trackNumber;
    private String trackTimeMillis;
    private String currency;
    private String primaryGenreName;
    private String artistName;
    private String previewUrl;
    private String artistViewUrl;

    public Song(String songname, String collectionname, String price, String image, String collectionId, String trackId, String releaseDate, String trackCount, String trackNumber, String trackTimeMillis, String currency, String primaryGenreName, String artistName, String previewUrl, String artistViewUrl) {
        this.songname = songname;
        this.collectionname = collectionname;
        this.price = price;
        this.image = image;
        this.collectionId = collectionId;
        this.trackId = trackId;
        this.releaseDate = releaseDate;
        this.trackCount = trackCount;
        this.trackNumber = trackNumber;
        this.trackTimeMillis = trackTimeMillis;
        this.currency = currency;
        this.primaryGenreName = primaryGenreName;
        this.artistName = artistName;
        this.previewUrl = previewUrl;
        this.artistViewUrl = artistViewUrl;
    }

    public String getSongname() {
        return songname;
    }

    public void setSongname(String songname) {
        this.songname = songname;
    }

    public String getCollectionname() {
        return collectionname;
    }

    public void setCollectionname(String collectionname) {
        this.collectionname = collectionname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTrackCount() {
        return trackCount;
    }

    public void setTrackCount(String trackCount) {
        this.trackCount = trackCount;
    }

    public String getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(String trackNumber) {
        this.trackNumber = trackNumber;
    }

    public String getTrackTimeMillis() {
        return trackTimeMillis;
    }

    public void setTrackTimeMillis(String trackTimeMillis) {
        this.trackTimeMillis = trackTimeMillis;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPrimaryGenreName() {
        return primaryGenreName;
    }

    public void setPrimaryGenreName(String primaryGenreName) {
        this.primaryGenreName = primaryGenreName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public String getArtistViewUrl() {
        return artistViewUrl;
    }

    public void setArtistViewUrl(String artistViewUrl) {
        this.artistViewUrl = artistViewUrl;
    }
}
