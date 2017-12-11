package com.jakubbilinski.stickystickynotesandroid.activities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.jakubbilinski.stickystickynotesandroid.R;
import com.jakubbilinski.stickystickynotesandroid.adapters.NotesAdapter;
import com.jakubbilinski.stickystickynotesandroid.helpers.DateConverter;
import com.jakubbilinski.stickystickynotesandroid.helpers.IntentExtras;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditorActivity extends AppCompatActivity {

    @BindView(R.id.editTextContext)
    EditText editTextContext;
    @BindView(R.id.textViewDate)
    TextView textViewDate;
    @BindView(R.id.ConstraintLayoutEditor)
    ConstraintLayout constraintLayoutEditor;

    private int notePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        ButterKnife.bind(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Bundle bundle = getIntent().getExtras();
        initSetupFields(bundle);
        setWindowColors(bundle);
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

    @Override
    public void onBackPressed() {
        Calendar currentDate = Calendar.getInstance();

        Intent resultIntent = new Intent();
        resultIntent.putExtra(IntentExtras.NOTE_POSITION, notePosition);
        resultIntent.putExtra(IntentExtras.NOTE_CONTEXT, editTextContext.getText().toString());
        resultIntent.putExtra(IntentExtras.NOTE_DATE, DateConverter.calendarToDate(currentDate));
        setResult(RESULT_OK, resultIntent);

        finishAfterTransition();
    }

    private void initSetupFields(Bundle bundle) {
        if (bundle != null) {
            notePosition = bundle.getInt(IntentExtras.NOTE_POSITION);
            editTextContext.setText(bundle.getString(IntentExtras.NOTE_CONTEXT));
            textViewDate.setText(DateConverter.timezoneDateToNormal(bundle.getString(IntentExtras.NOTE_DATE)));
            constraintLayoutEditor.setBackgroundColor(bundle.getInt(IntentExtras.NOTE_COLOR));
        }
    }

    private void setWindowColors(Bundle bundle) {
        if (bundle == null) {
            return;
        }

        int noteId = bundle.getInt(IntentExtras.NOTE_ID);
        int primaryColor = getPrimaryColor(noteId);
        int primaryColorDark = getPrimaryDarkColor(noteId);

        Window window = getWindow();
        window.setNavigationBarColor(primaryColor);
        window.setStatusBarColor(primaryColorDark);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(new ColorDrawable(primaryColor));
        }
    }

    private int getPrimaryColor(int id) {
        switch (id % NotesAdapter.COLORS_COUNT) {
            case 0:
                return ContextCompat.getColor(this, R.color.color_note_0_primary);
            case 1:
                return ContextCompat.getColor(this, R.color.color_note_1_primary);
            case 2:
                return ContextCompat.getColor(this, R.color.color_note_2_primary);
            case 3:
                return ContextCompat.getColor(this, R.color.color_note_3_primary);
            case 4:
                return ContextCompat.getColor(this, R.color.color_note_4_primary);
            case 5:
                return ContextCompat.getColor(this, R.color.color_note_5_primary);
            case 6:
                return ContextCompat.getColor(this, R.color.color_note_6_primary);
            default:
                return 0;
        }
    }

    private int getPrimaryDarkColor(int id) {
        switch (id % NotesAdapter.COLORS_COUNT) {
            case 0:
                return ContextCompat.getColor(this, R.color.color_note_0_primary_dark);
            case 1:
                return ContextCompat.getColor(this, R.color.color_note_1_primary_dark);
            case 2:
                return ContextCompat.getColor(this, R.color.color_note_2_primary_dark);
            case 3:
                return ContextCompat.getColor(this, R.color.color_note_3_primary_dark);
            case 4:
                return ContextCompat.getColor(this, R.color.color_note_4_primary_dark);
            case 5:
                return ContextCompat.getColor(this, R.color.color_note_5_primary_dark);
            case 6:
                return ContextCompat.getColor(this, R.color.color_note_6_primary_dark);
            default:
                return 0;
        }
    }
}
