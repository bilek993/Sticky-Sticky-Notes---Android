package com.jakubbilinski.stickystickynotesandroid.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jakubbilinski.stickystickynotesandroid.R;
import com.jakubbilinski.stickystickynotesandroid.database.entities.NotesEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jbili on 21.11.2017.
 */

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    List<NotesEntity> notesList = new ArrayList<>();

    public NotesAdapter(List<NotesEntity> notesList) {
        this.notesList = notesList;
    }

    @Override
    public NotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycleritem_note, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotesViewHolder holder, int position) {
        holder.textViewNoteContext.setText(notesList.get(position).getContext());
        holder.textViewDate.setText(notesList.get(position).getLastEditDate());
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {

        TextView textViewNoteContext;
        TextView textViewDate;

        public NotesViewHolder(View itemView) {
            super(itemView);

            textViewNoteContext = itemView.findViewById(R.id.textViewNoteContext);
            textViewDate = itemView.findViewById(R.id.textViewDate);
        }
    }
}
