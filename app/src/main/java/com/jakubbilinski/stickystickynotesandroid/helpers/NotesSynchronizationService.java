package com.jakubbilinski.stickystickynotesandroid.helpers;

import android.app.IntentService;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;

import com.jakubbilinski.stickystickynotesandroid.database.AppDatabase;
import com.jakubbilinski.stickystickynotesandroid.database.entities.NotesEntity;

import java.util.List;
import java.util.Random;

/**
 * Created by jbili on 13.11.2017.
 */

public class NotesSynchronizationService extends IntentService{

    private static AppDatabase database;

    public NotesSynchronizationService() {
        super(String.valueOf((new Random()).nextInt())); // Random name generator for service
    }

    private void setupDatabase() {
        if (database == null) {
            database = Room.databaseBuilder(this,
                    AppDatabase.class, AppDatabase.DatabaseName).build();
        }
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        setupDatabase();
    }
}
