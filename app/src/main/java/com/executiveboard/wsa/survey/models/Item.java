package com.executiveboard.wsa.survey.models;

import java.util.ArrayList;
import java.util.UUID;

public class Item {
	private final String mId;
	private final String mText;
	private ResponseScale mResponseScale;
	
	@Override
	public String toString() {
		return mText;
	}
	
	public Item(String id, String text) {
		mId = id;
		mText = text;
	}
	
	public Item(String text) {
        this(UUID.randomUUID().toString(), text);
	}
	
	public Item(String text, ResponseScale scale) {
		mId = UUID.randomUUID().toString();
		mText = text;
		mResponseScale = scale;
	}
	
	public void setResponseScale(ResponseScale scale) {
		mResponseScale = scale;
	}
	
	public ArrayList<ResponseOption> getOptions() {
		return mResponseScale.getResponseOptions();
	}
	
	public int getOptionCount() {
		return mResponseScale.getOptionCount();
	}
	
	public ResponseOption getOption(int idx) {
		return mResponseScale.getOption(idx);
	}
	
	public String getId() {
		return mId;
	}
	
	public String getText() {
		return mText;
	}		
}
