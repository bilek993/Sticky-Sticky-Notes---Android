package com.jakubbilinski.stickystickynotesandroid.networking.items;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jbili on 10.11.2017.
 */

public class NotesItem {

    @SerializedName("Id")
    @Expose
    private int id;
    @SerializedName("Context")
    @Expose
    private String context;
    @SerializedName("LastEditDate")
    @Expose
    private String lastEditDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getLastEditDate() {
        return lastEditDate;
    }

    public void setLastEditDate(String lastEditDate) {
        this.lastEditDate = lastEditDate;
    }
}
