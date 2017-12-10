package com.jakubbilinski.stickystickynotesandroid.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.jakubbilinski.stickystickynotesandroid.networking.items.NotesItem;

/**
 * Created by jbili on 13.11.2017.
 */

@Entity
public class NotesEntity {
    @PrimaryKey(autoGenerate = true)
    private int Id;
    @ColumnInfo(name = "ServerId")
    private int serverId;
    @ColumnInfo(name = "Context")
    private String context;
    @ColumnInfo(name = "LastEditDate")
    private String lastEditDate;
    @ColumnInfo(name = "Removed")
    private boolean removed;

    public NotesEntity(String context, String lastEditDate) {
        this.serverId = -1;
        this.context = context;
        this.lastEditDate = lastEditDate;
        this.removed = false;
    }

    public NotesEntity(NotesItem notesItem) {
        this.serverId = notesItem.getId();
        this.context = notesItem.getContext();
        this.lastEditDate = notesItem.getLastEditDate();
        this.removed = false;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
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
}
