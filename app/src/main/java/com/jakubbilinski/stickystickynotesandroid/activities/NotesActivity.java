package com.jakubbilinski.stickystickynotesandroid.activities;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.jakubbilinski.stickystickynotesandroid.R;
import com.jakubbilinski.stickystickynotesandroid.adapters.NotesAdapter;
import com.jakubbilinski.stickystickynotesandroid.database.AppDatabase;
import com.jakubbilinski.stickystickynotesandroid.database.entities.NotesEntity;
import com.jakubbilinski.stickystickynotesandroid.helpers.AddressVeryfication;
import com.jakubbilinski.stickystickynotesandroid.helpers.DateConverter;
import com.jakubbilinski.stickystickynotesandroid.helpers.IntentExtras;
import com.jakubbilinski.stickystickynotesandroid.helpers.LocalStorageHelper;
import com.jakubbilinski.stickystickynotesandroid.services.NotesSynchronizationService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Callable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotesActivity extends AppCompatActivity {

    @BindView(R.id.RecyclerViewNotes)
    RecyclerView recyclerViewNotes;
    @BindView(R.id.swipeRefreshLayoutNotes)
    SwipeRefreshLayout swipeRefreshLayoutNotes;

    private NotesAdapter notesAdapter;
    private List<NotesEntity> notesList = new ArrayList<>();
    private ResponseReceiver receiver;

    private final int REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        ButterKnife.bind(this);

        setupRecycler();
        setupSwipeLayout();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_notes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_change_ip:
                changeAddressDialog();
                break;
            case R.id.menu_logout:
                LocalStorageHelper.removeAll(this);
                new RemoveAllNotes().execute((Callable) () -> {
                    finishAffinity();
                    return null;
                });
                break;
            case R.id.menu_close:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void changeAddressDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View alertView = layoutInflater.inflate(R.layout.alertdialog_simple_input, null);

        final EditText editTextinput = new EditText(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.enter_address))
                .setView(alertView)
                .setPositiveButton(getString(R.string.set_new_address),
                        (dialog, which) -> {
                            String input = editTextinput.getText().toString();

                            if (AddressVeryfication.verify(this, input)) {
                                LocalStorageHelper.setServerAddress(NotesActivity.this, input);
                            }
                        });

        builder.show();
    }

    private void setupRecycler() {
        new GetAllNotes().execute();
    }

    private void setupSwipeLayout() {
        swipeRefreshLayoutNotes.setColorSchemeResources(
                R.color.color_note_0_background,
                R.color.primary_dark,
                R.color.color_note_1_background,
                R.color.primary,
                R.color.color_note_5_background
        );

        IntentFilter filter = new IntentFilter(IntentExtras.ACTION_BROADCAST_RESPONSE);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new ResponseReceiver();
        registerReceiver(receiver, filter);

        swipeRefreshLayoutNotes.setOnRefreshListener(() -> {
            Intent syncIntentService = new Intent(this, NotesSynchronizationService.class);
            startService(syncIntentService);
        });
    }

    @OnClick(R.id.floatingActionButtonAdd)
    void onAddButtonClick() {
        Calendar currentDate = Calendar.getInstance();
        NotesEntity newNote = new NotesEntity("",
                DateConverter.calendarToDate(currentDate));

        new AddNewNote().execute(newNote);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();

            if (extras != null) {
                int id = extras.getInt(IntentExtras.NOTE_POSITION);
                notesList.get(id).setContext(extras.getString(IntentExtras.NOTE_CONTEXT));
                notesList.get(id).setLastEditDate(extras.getString(IntentExtras.NOTE_DATE));
                notesAdapter.notifyItemChanged(id);

                new UpdateNote().execute(notesList.get(extras.getInt(IntentExtras.NOTE_POSITION)));
            }
        }
    }

    private void refreshingServiceFinished() {
        swipeRefreshLayoutNotes.setRefreshing(false);
        new GetAllNotes().execute();
    }

    public class ResponseReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            NotesActivity.this.refreshingServiceFinished();
        }
    }

    private class AddNewNote extends AsyncTask<NotesEntity, Void, List<NotesEntity>> {

        @Override
        protected List<NotesEntity> doInBackground(NotesEntity... notesEntities) {
            if (notesEntities.length != 1) {
                return null;
            }

            AppDatabase database = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, AppDatabase.DatabaseName)
                    .addMigrations(AppDatabase.MIGRATION_1_2)
                    .build();
            database.notesDao().Insert(notesEntities[0]);
            List<NotesEntity> listOfNotes = database.notesDao().getNotRemovedNotes();
            database.close();

            return listOfNotes;
        }

        @Override
        protected void onPostExecute(List<NotesEntity> notesEntities) {
            super.onPostExecute(notesEntities);

            notesList = notesEntities;
            notesAdapter.setNotesList(notesEntities);
            notesAdapter.notifyItemInserted(notesList.size() - 1);
        }
    }

    private class GetAllNotes extends AsyncTask<Void, Void, List<NotesEntity>> {

        @Override
        protected List<NotesEntity> doInBackground(Void... voids) {
            AppDatabase database = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, AppDatabase.DatabaseName)
                    .addMigrations(AppDatabase.MIGRATION_1_2)
                    .build();
            List<NotesEntity> listOfNotes = database.notesDao().getNotRemovedNotes();
            database.close();

            return listOfNotes;
        }

        // Suppressing error message due to the bug in latest Android Build Tools
        // More info: https://issuetracker.google.com/issues/37130193
        @SuppressLint("RestrictedApi")
        @Override
        protected void onPostExecute(List<NotesEntity> notesEntities) {
            super.onPostExecute(notesEntities);

            notesList = notesEntities;
            notesAdapter = new NotesAdapter(NotesActivity.this, notesList);

            recyclerViewNotes.setAdapter(notesAdapter);
            recyclerViewNotes.setLayoutManager(new StaggeredGridLayoutManager(
                    2, StaggeredGridLayoutManager.VERTICAL));

            notesAdapter.setOnItemClickListener((position, id, noteContext, lastEditDate, color, cardView) -> {
                Intent intent = new Intent(NotesActivity.this, EditorActivity.class);

                intent.putExtra(getString(R.string.animation_transition_notes_to_editor), position);
                intent.putExtra(IntentExtras.NOTE_POSITION, position);
                intent.putExtra(IntentExtras.NOTE_ID, id);
                intent.putExtra(IntentExtras.NOTE_CONTEXT, noteContext);
                intent.putExtra(IntentExtras.NOTE_DATE, lastEditDate);
                intent.putExtra(IntentExtras.NOTE_COLOR, color);

                Pair<View, String> p1 = Pair.create(cardView, getString(R.string.animation_transition_note_background));
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(NotesActivity.this, p1);

                startActivityForResult(intent, REQUEST_CODE, options.toBundle());
            });

            notesAdapter.setOnItemClickLongListener((position, id, noteContext, lastEditDate, color, cardView) -> { {
                new AlertDialog.Builder(NotesActivity.this)
                        .setTitle(getString(R.string.remove_dialog_title))
                        .setMessage(getString(R.string.remove_dialog_message))
                        .setPositiveButton(getString(R.string.yes), (dialogInterface, i) -> {
                            NotesEntity noteToBeRemoved = notesList.get(position);
                            noteToBeRemoved.setRemoved(true);
                            new UpdateNote().execute(noteToBeRemoved);

                            notesList.remove(position);
                            notesAdapter.notifyItemRemoved(position);
                        })
                        .setNegativeButton(getString(R.string.no), null)
                        .show();
            }});
        }
    }

    private class UpdateNote extends AsyncTask<NotesEntity, Void, Void> {

        @Override
        protected Void doInBackground(NotesEntity... notesEntities) {
            if (notesEntities.length != 1) {
                return null;
            }

            AppDatabase database = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, AppDatabase.DatabaseName)
                    .addMigrations(AppDatabase.MIGRATION_1_2)
                    .build();
            database.notesDao().Update(notesEntities[0]);
            database.close();

            return null;
        }
    }

    private class RemoveAllNotes extends AsyncTask<Callable,Void,Void> {

        @Override
        protected Void doInBackground(Callable[] callables) {
            AppDatabase database = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, AppDatabase.DatabaseName)
                    .addMigrations(AppDatabase.MIGRATION_1_2)
                    .build();
            database.notesDao().NukeData();
            database.close();

            for (Callable callback : callables) {
                try {
                    callback.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}
