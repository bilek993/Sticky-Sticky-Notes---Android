package com.jakubbilinski.stickystickynotesandroid.services;

import android.app.IntentService;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.jakubbilinski.stickystickynotesandroid.database.AppDatabase;
import com.jakubbilinski.stickystickynotesandroid.database.entities.NotesEntity;
import com.jakubbilinski.stickystickynotesandroid.helpers.IntentExtras;
import com.jakubbilinski.stickystickynotesandroid.networking.NotesNetworking;
import com.jakubbilinski.stickystickynotesandroid.networking.items.NotesItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

/**
 * Created by jbili on 13.11.2017.
 */

public class NotesSynchronizationService extends IntentService{

    private static AppDatabase database;

    private NotesNetworking networking;
    private int itemsToBeAdded;
    private List<NotesEntity> listOfNotesToBeUpdated;
    private List<NotesEntity> listOfNotesToBeAdded;
    private List<NotesEntity> listOfAllNotes;

    public NotesSynchronizationService() {
        super(String.valueOf((new Random()).nextInt())); // Random name generator for service
    }

    private void setupDatabase() {
        if (database == null) {
            database = Room.databaseBuilder(this,
                    AppDatabase.class, AppDatabase.DatabaseName).build();
        }
    }

    private List<NotesEntity> getNotesForUpdate() {
        List<NotesEntity> notesToBeUpdated = new ArrayList<>();

        for (NotesEntity note : listOfAllNotes) {
            if (note.getServerId() != -1) {
                notesToBeUpdated.add(note);
            }
        }
        return notesToBeUpdated;
    }

    private List<NotesEntity> getNotesForAdding() {
        List<NotesEntity> notesToBeAdded = new ArrayList<>();

        for (NotesEntity note : listOfAllNotes) {
            if (note.getServerId() == -1) {
                notesToBeAdded.add(note);
            }
        }
        return notesToBeAdded;
    }

    private void addNotes () {
        for (int i = 0; i < listOfNotesToBeAdded.size(); i++) {
            NotesItem notesItem = new NotesItem(listOfNotesToBeAdded.get(i));
            networking.addNote(notesItem, listOfNotesToBeAdded.get(i), () -> {
                itemsToBeAdded--;

                if (itemsToBeAdded <= 0) {
                    updateNotes();
                }
                return null;
            });
        }
    }

    private void updateNotes () {
        List<NotesItem> notesItemsConverted = new ArrayList<>();

        for (int i = 0; i < listOfNotesToBeUpdated.size(); i++) {
            notesItemsConverted.add(new NotesItem(listOfNotesToBeUpdated.get(i)));
        }

        networking.updateNotes(notesItemsConverted, () -> {
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction(IntentExtras.ACTION_BROADCAST_RESPONSE);
            broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
            sendBroadcast(broadcastIntent);
            getNotesFromServer();
            return null;
        });
    }

    private void getNotesFromServer() {
        networking.getNotes(() -> {
            stopSelf();
            return null;
        });
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        setupDatabase();
        networking = new NotesNetworking(this, database);

        listOfAllNotes = database.notesDao().getAll();
        listOfNotesToBeUpdated = getNotesForUpdate();
        listOfNotesToBeAdded = getNotesForAdding();
        itemsToBeAdded = listOfNotesToBeAdded.size();

        if (itemsToBeAdded != 0) {
            addNotes();
        }  else if (listOfNotesToBeUpdated.size() != 0) {
            updateNotes();
        } else {
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction(IntentExtras.ACTION_BROADCAST_RESPONSE);
            broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
            sendBroadcast(broadcastIntent);
            getNotesFromServer();
        }
    }
}
