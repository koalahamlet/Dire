package com.nightlynexus.playingatmyhouse.punk.model;

public class Picture {

    private final int mImageResourceId;
    private boolean mIsChecked;

    public Picture(int imageResourceId) {
        this(imageResourceId, false);
    }

    public Picture(int imageResourceId, boolean isChecked) {
        mImageResourceId = imageResourceId;
        mIsChecked = isChecked;
    }

    public void setChecked(boolean checked) {
        mIsChecked = checked;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    public boolean isChecked() {
        return mIsChecked;
    }
}
