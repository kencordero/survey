package com.executiveboard.wsa.survey.models;

import java.util.ArrayList;
import java.util.UUID;

public class ResponseScale {
	private final String mId;
	private ArrayList<ResponseOption> mResponseOptions;
	
	public ResponseScale(String id, ArrayList<ResponseOption> responseOptions) {
		mId = id;
		mResponseOptions = responseOptions;
	}
	
	public ResponseScale() {
        this(UUID.randomUUID().toString(), new ArrayList<ResponseOption>());
	}
	
	public void addOption(ResponseOption option) {
		mResponseOptions.add(option);
	}
	
	public void addOption(String optionText) {
		addOption(new ResponseOption(optionText));
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
