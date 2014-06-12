package com.executiveboard.wsa.survey.models;

import java.util.ArrayList;

public class ResponseScale {
	private ArrayList<ResponseOption> mResponseOptions;

    public ResponseScale() {
        mResponseOptions = new ArrayList<ResponseOption>();
    }

	public void addOption(ResponseOption option) {
		mResponseOptions.add(option);
	}

    public void addOption(String optionId, String optionText) {
        addOption(new ResponseOption(optionId, optionText));
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
