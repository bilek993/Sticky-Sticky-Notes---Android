package com.jakubbilinski.stickystickynotesandroid.database.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.jakubbilinski.stickystickynotesandroid.database.entities.NotesEntity;

import java.util.List;

/**
 * Created by jbili on 13.11.2017.
 */

@Dao
public interface NotesDao {
    @Query("SELECT * FROM notesentity")
    List<NotesEntity> getAll();

    @Query("SELECT * FROM notesentity WHERE Removed = 0")
    List<NotesEntity> getNotRemovedNotes();

    @Insert
    void Insert(NotesEntity note);

    @Insert
    void Insert(List<NotesEntity> notes);

    @Update
    void Update(NotesEntity note);

    @Update
    void Update(List<NotesEntity> notes);
}
