package com.kentheken.survey.models;

public class ResponseOption {
    private final int mId;
    private final String mText;
    private int mCount;
    
    public ResponseOption(int id, String text) {
        mId = id;
        mText = text;
        mCount = 0;
    }
    
    @Override
    public boolean equals(Object that) {
        if (that == null) return false;
        if (this == that) return true;
        if (!(that instanceof ResponseOption)) return false;
        return mId == ((ResponseOption)that).getId();
    }

    public void setCount(int count) {
        mCount = count;
    }

    public int getCount() {
        return mCount;
    }

    @Override
    public String toString() {
        return mText;
    }
    
    public int getId() {
        return mId;
    }
    
    public String getText() {
        return mText;
    }
}
