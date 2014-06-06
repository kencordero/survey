package com.executiveboard.wsa.survey.models;

import java.util.ArrayList;

public class ResponseScale {
	private String mId;
	private ArrayList<ResponseOption> mResponseOptions;
	
	public ResponseScale(String id, ArrayList<ResponseOption> responseOptions) {
		mId = id;
		mResponseOptions = responseOptions;
	}
}
