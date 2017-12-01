package com.jakubbilinski.stickystickynotesandroid;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.jakubbilinski.stickystickynotesandroid.database.AppDatabase;
import com.jakubbilinski.stickystickynotesandroid.database.daos.NotesDao;
import com.jakubbilinski.stickystickynotesandroid.database.entities.NotesEntity;
import com.jakubbilinski.stickystickynotesandroid.helpers.DateConverter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Created by jbili on 01.12.2017.
 */

@RunWith(AndroidJUnit4.class)
public class RoomDatabaseInstrumentedTests {

    private AppDatabase database;
    private NotesDao notesDao;

    @Before
    public void preparingDatabase() {
        Context context = InstrumentationRegistry.getTargetContext();
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        notesDao = database.notesDao();
    }

    @Test
    public void testAllNotesDaoFunctions() {
        List<NotesEntity> notes = new ArrayList<>();
        Calendar currentDate = Calendar.getInstance();

        for (int i = 0; i < 1000; i++) {
            notes.add(new NotesEntity("Note i:" + i, DateConverter.calendarToDate(currentDate)));
        }

        notesDao.Insert(notes);
        notes = notesDao.getAll(); // Getting list for ID

        notes.get(0).setContext("ZERO");
        notesDao.Update(notes.get(0));

        notes.get(1).setContext("ONE");
        notesDao.Update(notes);

        notes.add(new NotesEntity("Note not from loop.", DateConverter.calendarToDate(currentDate)));
        notesDao.Insert(notes.get(notes.size() - 1));

        boolean differenceFound = false;
        List<NotesEntity> notesFromDatabase = notesDao.getAll();

        for  (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).getContext().equals(notesFromDatabase.get(i).getContext())) {
                differenceFound = true;
                break;
            }
        }

        assertTrue(differenceFound);
    }

    @After
    public void closeDatabase() {
        database.close();
    }
}
