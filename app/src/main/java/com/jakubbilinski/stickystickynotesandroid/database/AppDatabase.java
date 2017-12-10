package com.jakubbilinski.stickystickynotesandroid.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;

import com.jakubbilinski.stickystickynotesandroid.database.daos.NotesDao;
import com.jakubbilinski.stickystickynotesandroid.database.entities.NotesEntity;

/**
 * Created by jbili on 13.11.2017.
 */

@Database(entities = {NotesEntity.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public static String DatabaseName = "MainDB";
    public abstract NotesDao notesDao();

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE NotesEntity ADD COLUMN Removed INTEGER DEFAULT 0 NOT NULL");
        }
    };
}
