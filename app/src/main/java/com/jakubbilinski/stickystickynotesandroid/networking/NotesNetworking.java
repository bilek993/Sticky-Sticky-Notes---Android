package com.jakubbilinski.stickystickynotesandroid.networking;

import android.content.Context;
import android.os.AsyncTask;

import com.jakubbilinski.stickystickynotesandroid.database.AppDatabase;
import com.jakubbilinski.stickystickynotesandroid.database.entities.NotesEntity;
import com.jakubbilinski.stickystickynotesandroid.networking.items.NotesItem;

import java.util.concurrent.Callable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jbili on 23.11.2017.
 */

public class NotesNetworking {

    private Context context;
    private AppDatabase database;

    public NotesNetworking(Context context, AppDatabase lastKnownDatabase) {
        this.context = context;
        this.database = lastKnownDatabase;
    }

    public void addNote(NotesItem notesItem, NotesEntity notesEntity, Callable<Void> after) {
        RestClient restClient = RestSystem.buildWithAuthentication(context);
        Call<Integer> call = restClient.createNewNote(notesItem);

        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                try {
                    if (response.body() != null) {
                        notesEntity.setServerId(response.body());
                        new UpdateNoteWithId().execute(notesEntity);
                    }

                    after.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                try {
                    after.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private class UpdateNoteWithId extends AsyncTask<NotesEntity, Void, Void> {

        @Override
        protected Void doInBackground(NotesEntity... notesEntities) {
            if (notesEntities.length != 1) {
                return null;
            }

            database.notesDao().Update(notesEntities[0]);
            return null;
        }
    }
}
