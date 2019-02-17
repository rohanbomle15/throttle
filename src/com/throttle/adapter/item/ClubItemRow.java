package com.throttle.adapter.item;

import android.graphics.drawable.Drawable;

public class ClubItemRow {
	String itemName;
	Drawable icon;

	public ClubItemRow(String itemName, Drawable icon) {
		super();
		this.itemName = itemName;
		this.icon = icon;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Drawable getIcon() {
		return icon;
	}

	public void setIcon(Drawable icon) {
		this.icon = icon;
	}

}
