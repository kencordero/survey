package com.executiveboard.wsa.survey.models;

import java.util.UUID;

public class ResponseOption {
	private String mId;
	private String mText;
	
	public ResponseOption(String id, String text) {
		mId = id;
		mText = text;
	}
	
	public ResponseOption(String text) {
		mId = UUID.randomUUID().toString();
		mText = text;
	}
	
	@Override
	public String toString() {
		return mText;
	}
	
	public String getId() {
		return mId;
	}
	
	public String getText() {
		return mText;
	}
}
