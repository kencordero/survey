package com.executiveboard.wsa.survey.models;

import java.util.ArrayList;
import java.util.UUID;

public class Item {
	private String mId;
	private String mText;
	private ResponseScale mResponseScale;
	
	public Item(String id, String text) {
		mId = id;
		mText = text;
	}
	
	public Item(String text) {
		mId = UUID.randomUUID().toString();
		mText = text;
	}
	
	public void setResponseScale(ResponseScale scale) {
		mResponseScale = scale;
	}
	
	public ResponseScale getResponseScale() {
		return mResponseScale;
	}
	
	public ArrayList<ResponseOption> getResponseOptions() {
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
