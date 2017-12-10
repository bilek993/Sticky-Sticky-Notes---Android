package com.jakubbilinski.stickystickynotesandroid.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jakubbilinski.stickystickynotesandroid.R;
import com.jakubbilinski.stickystickynotesandroid.database.entities.NotesEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jbili on 21.11.2017.
 */

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    public static final int COLORS_COUNT = 7;

    private List<NotesEntity> notesList = new ArrayList<>();
    private Context context;
    private OnItemClickListener onItemClickListener;
    private OnItemClickListener onItemClickLongListener;

    public NotesAdapter(Context context, List<NotesEntity> notesList) {
        this.context = context;
        this.notesList = notesList;
    }

    public void setNotesList(List<NotesEntity> notesList) {
        this.notesList = notesList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemClickLongListener(OnItemClickListener onItemClickLongListener) {
        this.onItemClickLongListener = onItemClickLongListener;
    }

    public interface OnItemClickListener {
        void onClick(int position, int id, String noteContext, String lastEditDate, int color, CardView cardView);
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
        holder.cardViewNote.setCardBackgroundColor(generateColor(notesList.get(position).getId()));

        holder.cardViewNote.setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.onClick(holder.getAdapterPosition(),
                        notesList.get(position).getId(),
                        holder.textViewNoteContext.getText().toString(),
                        holder.textViewDate.getText().toString(),
                        generateColor(notesList.get(position).getId()),
                        holder.cardViewNote);
            }
        });

        holder.cardViewNote.setOnLongClickListener(view -> {
            if (onItemClickLongListener != null) {
                onItemClickLongListener.onClick(holder.getAdapterPosition(),
                        notesList.get(position).getId(),
                        holder.textViewNoteContext.getText().toString(),
                        holder.textViewDate.getText().toString(),
                        -1, // Color is irrelevant, so it's set to -1 value
                        holder.cardViewNote);
            }

            return true;
        });
    }

    public int generateColor(int id) {
        switch (id%COLORS_COUNT) {
            case 0:
                return ContextCompat.getColor(context, R.color.color_note_0_background);
            case 1:
                return ContextCompat.getColor(context, R.color.color_note_1_background);
            case 2:
                return ContextCompat.getColor(context, R.color.color_note_2_background);
            case 3:
                return ContextCompat.getColor(context, R.color.color_note_3_background);
            case 4:
                return ContextCompat.getColor(context, R.color.color_note_4_background);
            case 5:
                return ContextCompat.getColor(context, R.color.color_note_5_background);
            case 6:
                return ContextCompat.getColor(context, R.color.color_note_6_background);
            default:
                return 0;
        }
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {

        CardView cardViewNote;
        TextView textViewNoteContext;
        TextView textViewDate;

        NotesViewHolder(View itemView) {
            super(itemView);

            cardViewNote = itemView.findViewById(R.id.cardViewNote);
            textViewNoteContext = itemView.findViewById(R.id.textViewNoteContext);
            textViewDate = itemView.findViewById(R.id.textViewDate);
        }
    }
}
