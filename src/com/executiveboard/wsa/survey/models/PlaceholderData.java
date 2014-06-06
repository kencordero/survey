package com.executiveboard.wsa.survey.models;

public class PlaceholderData {
	private static Survey sSurvey;
		
	public static void init() {
		if (sSurvey == null) {
			ResponseScale agreeScale = new ResponseScale();		
			agreeScale.addOption(new ResponseOption("Strongly Agree"));
			agreeScale.addOption(new ResponseOption("Agree"));
			agreeScale.addOption(new ResponseOption("Neither Agree Nor Disagree"));
			agreeScale.addOption(new ResponseOption("Disagree"));
			agreeScale.addOption(new ResponseOption("Strongly Disagree"));
				
			ResponseScale frequencyScale = new ResponseScale();
			frequencyScale.addOption(new ResponseOption("Very Often"));
			frequencyScale.addOption(new ResponseOption("Not Very Often"));
			frequencyScale.addOption(new ResponseOption("Never"));
				
			sSurvey = new Survey();
			sSurvey.addItem(new Item("The people I work with cooperate to get the job done.", agreeScale));
			sSurvey.addItem(new Item("I like traffic lights.", agreeScale));
			sSurvey.addItem(new Item("I eat yellow snow.", frequencyScale));
		}					
	}
	
	public static Item getRandomItem() {
		return sSurvey.getRandomItem();
	}
}
