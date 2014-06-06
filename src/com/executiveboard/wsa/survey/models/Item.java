package com.executiveboard.wsa.survey.models;

public class Item {
	private String mId;
	private String mText;
	private ResponseScale mResponseScale;
	
	public Item(String id, String text) {
		mId = id;
		mText = text;
	}
	
	public String getId() {
		return mId;
	}
	
	public String getText() {
		return mText;
	}		
}
