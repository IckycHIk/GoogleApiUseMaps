package com.example.alex.audiogid.BackClass;

import android.graphics.drawable.Drawable;

public class DrawerMenuItem {

    private int id;
private String NameOfItem;
private int IconOfItem;



    public DrawerMenuItem(int id, String nameOfItem, int iconOfItem ) {
        this.id = id;
        NameOfItem = nameOfItem;
        IconOfItem = iconOfItem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameOfItem() {
        return NameOfItem;
    }

    public void setNameOfItem(String nameOfItem) {
        NameOfItem = nameOfItem;
    }

    public int getIconOfItem() {
        return IconOfItem;
    }

    public void setIconOfItem(int iconOfItem) {
        IconOfItem = iconOfItem;
    }


}
