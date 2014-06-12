package com.executiveboard.wsa.survey.models;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;

public class Survey {
	private ArrayList<Item> mItems;
	private static Survey sSurvey;
	private Context mAppContext;
		
	public static Survey get(Context c) {
		if (sSurvey == null)
			sSurvey = new Survey(c.getApplicationContext());
		return sSurvey;
	}
	
	private Survey(Context appContext) {
		mAppContext = appContext;
		mItems = new ArrayList<Item>();
	}
	
	public void addItem(Item i) {
		mItems.add(i);
	}
	
	public void addItem(String itemText, ResponseScale scale) {
		addItem(new Item(itemText, scale));		
	}

    public void addItem(String itemId, String itemText) {
        mItems.add(new Item(itemId, itemText));
    }
	
	public void deleteItem(Item i) {
		mItems.remove(i);
	}
	
	public ArrayList<Item> getItems() {
		return mItems;
	}
	
	public int getItemCount() {
		return mItems.size();
	}
	
	public Item getItem(String id) {
		for (Item item: mItems) {
			if (item.getId().equals(id))
				return item;
		}
		return null;
	}
	
	public Item getItem(int idx) {
		return mItems.get(idx);
	}

	public Item getRandomItem() {
		Random random = new Random();
		return getItem(random.nextInt(getItemCount()));		
	}
}
