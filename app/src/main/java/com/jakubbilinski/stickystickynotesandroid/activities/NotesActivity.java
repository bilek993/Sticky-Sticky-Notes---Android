package com.jakubbilinski.stickystickynotesandroid.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.jakubbilinski.stickystickynotesandroid.R;
import com.jakubbilinski.stickystickynotesandroid.adapters.NotesAdapter;
import com.jakubbilinski.stickystickynotesandroid.database.entities.NotesEntity;

import java.util.ArrayList;
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
        notesList.add(new NotesEntity("abc", "def"));

        notesAdapter = new NotesAdapter(notesList);
        recyclerViewNotes.setAdapter(notesAdapter);
        recyclerViewNotes.setLayoutManager(new StaggeredGridLayoutManager(
                2, StaggeredGridLayoutManager.VERTICAL));
    }

    @OnClick(R.id.floatingActionButtonAdd)
    void onAddButtonClick() {
        notesList.add(new NotesEntity("eee", "fff"));
        notesAdapter.notifyDataSetChanged();
    }
}
