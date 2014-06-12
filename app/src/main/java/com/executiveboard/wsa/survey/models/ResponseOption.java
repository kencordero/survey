package com.executiveboard.wsa.survey.models;

public class ResponseOption {
	private final String mId;
	private final String mText;
	
	public ResponseOption(String id, String text) {
		mId = id;
		mText = text;
	}
	
	@Override
    public boolean equals(Object that) {
        if (that == null) return false;
        if (this == that) return true;
        if (!(that instanceof ResponseOption)) return false;
        ResponseOption otherOption = (ResponseOption)that;
        if (this.mId == otherOption.getId()) return true;
        return false;
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
