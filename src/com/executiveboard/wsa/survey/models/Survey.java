package com.executiveboard.wsa.survey.models;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class Survey {
	private ArrayList<Item> mItems;
	private String mId;
	
	public Survey() {
		mId = UUID.randomUUID().toString();
		mItems = new ArrayList<Item>();
	}
	
	public void addItem(Item i) {
		mItems.add(i);
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
