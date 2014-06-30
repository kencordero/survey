package com.executiveboard.wsa.survey.models;

import java.util.ArrayList;

public class Item {
    private final int mId;
    private final String mText;
    private ResponseOption mResponse;
    private ResponseScale mResponseScale;
    
    @Override
    public String toString() {
        return mText;
    }
    
    public Item(int id, String text) {
        mId = id;
        mText = text;
    }
    
    public void setResponseScale(ResponseScale scale) {
        mResponseScale = scale;
    }

    public void setResponse(ResponseOption response) {
        mResponse = response;
    }

    public ResponseOption getResponse() {
        return mResponse;
    }
    
    public ArrayList<ResponseOption> getOptions() {
        return mResponseScale.getResponseOptions();
    }
    
    public int getOptionCount() {
        return mResponseScale.getOptionCount();
    }
    
    public int getId() {
        return mId;
    }
    
    public String getText() {
        return mText;
    }        
}
