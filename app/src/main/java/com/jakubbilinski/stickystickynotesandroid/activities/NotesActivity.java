package com.jakubbilinski.stickystickynotesandroid.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.jakubbilinski.stickystickynotesandroid.R;
import com.jakubbilinski.stickystickynotesandroid.adapters.NotesAdapter;
import com.jakubbilinski.stickystickynotesandroid.database.entities.NotesEntity;
import com.jakubbilinski.stickystickynotesandroid.helpers.DateConverter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotesActivity extends AppCompatActivity {

    @BindView(R.id.RecyclerViewNotes)
    RecyclerView recyclerViewNotes;

    private NotesAdapter notesAdapter;
    private List<NotesEntity> notesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        ButterKnife.bind(this);

        setupRecycler();
    }

    private void setupRecycler() {
        notesAdapter = new NotesAdapter(this, notesList);
        recyclerViewNotes.setAdapter(notesAdapter);
        recyclerViewNotes.setLayoutManager(new StaggeredGridLayoutManager(
                2, StaggeredGridLayoutManager.VERTICAL));
    }

    @OnClick(R.id.floatingActionButtonAdd)
    void onAddButtonClick() {
        Calendar currentDate = Calendar.getInstance();
        notesList.add(new NotesEntity(getString(R.string.empty_note_message), DateConverter.calendarToDate(currentDate)));
        notesAdapter.notifyItemInserted(notesList.size() - 1);
    }
}
