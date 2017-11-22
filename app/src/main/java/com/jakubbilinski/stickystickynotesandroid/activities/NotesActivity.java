package com.jakubbilinski.stickystickynotesandroid.activities;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MenuItem;
import android.view.View;

import com.jakubbilinski.stickystickynotesandroid.R;
import com.jakubbilinski.stickystickynotesandroid.adapters.NotesAdapter;
import com.jakubbilinski.stickystickynotesandroid.database.AppDatabase;
import com.jakubbilinski.stickystickynotesandroid.database.entities.NotesEntity;
import com.jakubbilinski.stickystickynotesandroid.helpers.DateConverter;
import com.jakubbilinski.stickystickynotesandroid.helpers.IntentExtras;

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

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(getDrawable(R.drawable.close));
        }

        setupRecycler();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    private void setupRecycler() {
        new GetAllNotes().execute();
    }

    @OnClick(R.id.floatingActionButtonAdd)
    void onAddButtonClick() {
        Calendar currentDate = Calendar.getInstance();
        NotesEntity newNote = new NotesEntity(getString(R.string.empty_note_message),
                DateConverter.calendarToDate(currentDate));

        new AddNewNote().execute(newNote);

        notesList.add(newNote);
        notesAdapter.notifyItemInserted(notesList.size() - 1);
    }

    private class AddNewNote extends AsyncTask<NotesEntity, Void, Void> {

        @Override
        protected Void doInBackground(NotesEntity... notesEntities) {
            if (notesEntities.length <= 0) {
                return null;
            }

            AppDatabase database = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, AppDatabase.DatabaseName).build();
            for (NotesEntity newNote : notesEntities) {
                database.notesDao().Insert(newNote);
            }
            database.close();

            return null;
        }
    }

    private class GetAllNotes extends AsyncTask<Void, Void, List<NotesEntity>> {

        @Override
        protected List<NotesEntity> doInBackground(Void... voids) {
            AppDatabase database = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, AppDatabase.DatabaseName).build();
            List<NotesEntity> listOfNotes = database.notesDao().getAll();
            database.close();

            return listOfNotes;
        }

        @Override
        protected void onPostExecute(List<NotesEntity> notesEntities) {
            super.onPostExecute(notesEntities);

            notesList = notesEntities;
            notesAdapter = new NotesAdapter(NotesActivity.this, notesList);

            recyclerViewNotes.setAdapter(notesAdapter);
            recyclerViewNotes.setLayoutManager(new StaggeredGridLayoutManager(
                    2, StaggeredGridLayoutManager.VERTICAL));

            notesAdapter.setOnItemClickListener((position, noteContext, lastEditDate, color, cardView) -> {
                Intent intent = new Intent(NotesActivity.this, EditorActivity.class);

                intent.putExtra(getString(R.string.animation_transition_notes_to_editor), position);
                intent.putExtra(IntentExtras.NOTE_ID, position);
                intent.putExtra(IntentExtras.NOTE_CONTEXT, noteContext);
                intent.putExtra(IntentExtras.NOTE_DATE, lastEditDate);
                intent.putExtra(IntentExtras.NOTE_COLOR, color);

                Pair<View, String> p1 = Pair.create((View) cardView, getString(R.string.animation_transition_note_background));
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(NotesActivity.this, p1);

                startActivity(intent, options.toBundle());
            });
        }
    }
}
