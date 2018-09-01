package com.udhamini;



/**
 * Created by brian on 04/Apr/2018.
 */

public class Scholarship {

    int itemId;
    String title;
    String date;





    Scholarship(int itemId, String title, String date) {
        this.itemId = itemId;
        this.title = title;
        this.date = date;

    }

    public Scholarship() {

    }
    public int getId() {
        return itemId;
    }
    public void setId(int id) {
        this.itemId = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
}
