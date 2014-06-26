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

    public void addOption(int optionId, String optionText) {
        addOption(new ResponseOption(optionId, optionText));
    }

    public ArrayList<ResponseOption> getResponseOptions() {
        return mResponseOptions;
    }

    public int getOptionCount() {
        return mResponseOptions.size();
    }

    public ResponseOption getOption(int id) {
        for (ResponseOption option: mResponseOptions) {
            if (id == option.getId())
                return option;
        }
        return null;
    }
}
