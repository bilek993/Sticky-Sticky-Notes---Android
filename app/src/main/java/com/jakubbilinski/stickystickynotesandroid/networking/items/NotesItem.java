package com.jakubbilinski.stickystickynotesandroid.networking.items;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jakubbilinski.stickystickynotesandroid.database.entities.NotesEntity;

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
    @SerializedName("Removed")
    @Expose
    private boolean removed;

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

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    public NotesItem(NotesEntity notesEntity) {
        id = notesEntity.getServerId();
        context = notesEntity.getContext();
        lastEditDate = notesEntity.getLastEditDate();
        removed = notesEntity.isRemoved();
    }
}
