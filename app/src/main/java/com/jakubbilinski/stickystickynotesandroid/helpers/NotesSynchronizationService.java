package com.jakubbilinski.stickystickynotesandroid.helpers;

import android.app.IntentService;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.jakubbilinski.stickystickynotesandroid.database.AppDatabase;
import com.jakubbilinski.stickystickynotesandroid.database.entities.NotesEntity;
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

    public NotesSynchronizationService() {
        super(String.valueOf((new Random()).nextInt())); // Random name generator for service
    }

    private void setupDatabase() {
        if (database == null) {
            database = Room.databaseBuilder(this,
                    AppDatabase.class, AppDatabase.DatabaseName).build();
        }
    }

    private List<NotesEntity> getNotesForUpdate(List<NotesEntity> listOfNotes) {
        List<NotesEntity> notesToBeUpdated = new ArrayList<>();

        for (NotesEntity note : listOfNotes) {
            if (note.getServerId() != -1) {
                notesToBeUpdated.add(note);
            }
        }
        return notesToBeUpdated;
    }

    private List<NotesEntity> getNotesForAdding(List<NotesEntity> listOfNotes) {
        List<NotesEntity> notesToBeAdded = new ArrayList<>();

        for (NotesEntity note : listOfNotes) {
            if (note.getServerId() == -1) {
                notesToBeAdded.add(note);
            }
        }
        return notesToBeAdded;
    }

    private void addNotes (List<NotesEntity> noteToBeAdded) {
        for (int i = 0; i < noteToBeAdded.size(); i++) {
            NotesItem notesItem = new NotesItem(noteToBeAdded.get(i));
            networking.addNote(notesItem, noteToBeAdded.get(i), () -> {
                itemsToBeAdded--;

                if (itemsToBeAdded <= 0) {
                    updateNotes(listOfNotesToBeUpdated);
                }
                return null;
            });
        }
    }

    private void updateNotes (List<NotesEntity> noteToBeUpdated) {
        List<NotesItem> notesItemsConverted = new ArrayList<>();

        for (int i = 0; i < noteToBeUpdated.size(); i++) {
            notesItemsConverted.add(new NotesItem(noteToBeUpdated.get(i)));
        }

        networking.updateNotes(notesItemsConverted, () -> {
            stopSelf();
            return null;
        });
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        setupDatabase();
        networking = new NotesNetworking(this, database);

        List<NotesEntity> listOfAllNote = database.notesDao().getAll();
        listOfNotesToBeUpdated = getNotesForUpdate(listOfAllNote);
        listOfNotesToBeAdded = getNotesForAdding(listOfAllNote);
        itemsToBeAdded = listOfNotesToBeAdded.size();

        if (itemsToBeAdded != 0) {
            addNotes(listOfNotesToBeAdded);
        }  else if (listOfNotesToBeUpdated.size() != 0) {
            updateNotes(listOfNotesToBeUpdated);
        } else {
            stopSelf();
        }
    }
}
