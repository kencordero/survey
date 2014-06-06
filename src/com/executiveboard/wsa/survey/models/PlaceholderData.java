package com.executiveboard.wsa.survey.models;

public class PlaceholderData {
	private static Survey sSurvey;
		
	public static void init() {
		if (sSurvey == null) {
			ResponseScale agreementScale = new ResponseScale();		
			agreementScale.addOption("Strongly Agree");
			agreementScale.addOption("Agree");
			agreementScale.addOption("Neither Agree Nor Disagree");
			agreementScale.addOption("Disagree");
			agreementScale.addOption("Strongly Disagree");
				
			ResponseScale frequencyScale = new ResponseScale();
			frequencyScale.addOption("Very Often");
			frequencyScale.addOption("Not Very Often");
			frequencyScale.addOption("Never");
			
			ResponseScale movies = new ResponseScale();
			movies.addOption("Breakin' 2: Electric Boogaloo");
			movies.addOption("Land Before Time VII");
			movies.addOption("Catwoman");
			movies.addOption("Gigli");
				
			sSurvey = new Survey();
			sSurvey.addItem("The people I work with cooperate to get the job done.", agreementScale);
			sSurvey.addItem("I like traffic lights.", agreementScale);
			sSurvey.addItem("I eat yellow snow.", frequencyScale);
			sSurvey.addItem("The greatest movie ever is...", movies);
		}					
	}
	
	public static Item getRandomItem() {
		return sSurvey.getRandomItem();
	}
}
