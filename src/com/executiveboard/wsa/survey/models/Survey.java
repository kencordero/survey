package com.executiveboard.wsa.survey.models;

import java.util.ArrayList;

public class Survey {
	private ArrayList<Item> mItems;
	
	public void addItem(Item i) {
		mItems.add(i);
	}
	
	public void deleteItem(Item i) {
		mItems.remove(i);
	}
	
	public ArrayList<Item> getItems() {
		return mItems;
	}
	
	public Item getItem(String id) {
		for (Item item: mItems) {
			if (item.getId().equals(id))
				return item;
		}
		return null;
	}
}
