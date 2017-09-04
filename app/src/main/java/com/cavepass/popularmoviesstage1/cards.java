package com.cavepass.popularmoviesstage1;


public class cards {

    private String mTitle="1";

    private String mYear="1";
    private String mImageID;
    private String mOverview;
    private String mVote;


    cards(String title, String year, String imageID, String overview, String vote) {

        mTitle = title;
        mImageID = imageID;
        mYear = year;



        mOverview = overview;
        mVote = vote;
    }


    public String getmImageID() {
        return mImageID;
    }

    public String getmYear() {
        return mYear;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmOverview() {
        return mOverview;
    }

    public String getmVote() {
        return mVote;
    }
}
