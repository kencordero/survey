package com.executiveboard.wsa.survey.models;

public class ResponseOption {
	private String mId;
	private String mText;
	
	public ResponseOption(String id, String text) {
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
