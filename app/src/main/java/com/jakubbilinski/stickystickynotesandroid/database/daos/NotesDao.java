package com.jakubbilinski.stickystickynotesandroid.database.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.jakubbilinski.stickystickynotesandroid.database.entities.NotesEntity;

import java.util.List;

/**
 * Created by jbili on 13.11.2017.
 */

@Dao
public interface NotesDao {
    @Query("SELECT * FROM notesentity")
    List<NotesEntity> getAll();

    @Insert
    void Insert(NotesEntity note);
}