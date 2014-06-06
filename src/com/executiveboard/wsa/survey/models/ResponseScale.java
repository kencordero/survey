package com.executiveboard.wsa.survey.models;

import java.util.ArrayList;
import java.util.UUID;

public class ResponseScale {
	private String mId;
	private ArrayList<ResponseOption> mResponseOptions;
	
	public ResponseScale(String id, ArrayList<ResponseOption> responseOptions) {
		mId = id;
		mResponseOptions = responseOptions;
	}
	
	public ResponseScale() {
		mId = UUID.randomUUID().toString();
		mResponseOptions = new ArrayList<ResponseOption>();
	}
	
	public void addOption(ResponseOption option) {
		mResponseOptions.add(option);
	}
	
	public ArrayList<ResponseOption> getResponseOptions() {
		return mResponseOptions;
	}
	
	public int getOptionCount() {
		return mResponseOptions.size();
	}
	
	public ResponseOption getOption(int idx) {
		return mResponseOptions.get(idx);
	}
}
