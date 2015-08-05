package com.kentheken.survey.models;

import android.content.Context;

import java.util.ArrayList;

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
    
    public void addItem(int itemId, String itemText) {
        mItems.add(new Item(itemId, itemText));
    }
    
    public ArrayList<Item> getItems() {
        return mItems;
    }
    
    public int getItemCount() {
        return mItems.size();
    }
    
    public Item getItem(int id) {
        for (Item item: mItems) {
            if (id == item.getId())
                return item;
        }
        return null;
    }
}
