package com.jakubbilinski.stickystickynotesandroid.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.jakubbilinski.stickystickynotesandroid.database.daos.NotesDao;
import com.jakubbilinski.stickystickynotesandroid.database.entities.NotesEntity;

/**
 * Created by jbili on 13.11.2017.
 */

@Database(entities = {NotesEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NotesDao notesDao();
}
