package com.example.ihjony.recyclerviewjsonparsing;

public class ExampleItem {
    private String mImageUrl;
    private String mCreatorName;
    private int mLike;

    public ExampleItem(String mImageUrl, String mCreatorName, int mLike) {
        this.mImageUrl = mImageUrl;
        this.mCreatorName = mCreatorName;
        this.mLike = mLike;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getCreatorName() {
        return mCreatorName;
    }

    public int getLike() {
        return mLike;
    }
}

