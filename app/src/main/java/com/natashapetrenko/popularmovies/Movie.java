package com.natashapetrenko.popularmovies;

public class Movie {

    private int mId;

    private String mTitle;

    private String mImagePath;

    private String mPlotSynopsis;

    private String mUserRating;

    private String mReleaseDate;

    public Movie(int id, String title, String imageURL, String userRating, String releaseDate, String plotSynopsis) {
        mId = id;
        mTitle = title;
        mImagePath = imageURL;
        mUserRating = userRating;
        mReleaseDate = releaseDate;
        mPlotSynopsis = plotSynopsis;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getPlotSynopsys() {
        return mPlotSynopsis;
    }

    public void setPlotSynopsys(String plotSynopsis) {
        mPlotSynopsis = plotSynopsis;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getImagePath() {
        return mImagePath;
    }

    public void setImagePath(String imagePath) {
        mImagePath = imagePath;
    }

    public String getUserRating() {
        return mUserRating;
    }

    public void setUserRating(String userRating) {
        mUserRating = userRating;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }
}
