package com.jakubbilinski.stickystickynotesandroid.activities;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.jakubbilinski.stickystickynotesandroid.R;
import com.jakubbilinski.stickystickynotesandroid.helpers.DateConverter;
import com.jakubbilinski.stickystickynotesandroid.helpers.IntentExtras;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Converter;

public class EditorActivity extends AppCompatActivity {

    @BindView(R.id.editTextContext)
    EditText editTextContext;
    @BindView(R.id.textViewDate)
    TextView textViewDate;
    @BindView(R.id.ConstraintLayoutEditor)
    ConstraintLayout constraintLayoutEditor;

    private int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        ButterKnife.bind(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        initSetupFields(getIntent().getExtras());
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
        resultIntent.putExtra(IntentExtras.NOTE_ID, noteId);
        resultIntent.putExtra(IntentExtras.NOTE_CONTEXT, editTextContext.getText().toString());
        resultIntent.putExtra(IntentExtras.NOTE_DATE, DateConverter.calendarToDate(currentDate));
        setResult(RESULT_OK, resultIntent);

        finishAfterTransition();
    }

    private void initSetupFields(Bundle bundle) {
        if (bundle != null) {
            noteId = bundle.getInt(IntentExtras.NOTE_ID);
            editTextContext.setText(bundle.getString(IntentExtras.NOTE_CONTEXT));
            textViewDate.setText(bundle.getString(IntentExtras.NOTE_DATE));
            constraintLayoutEditor.setBackgroundColor(bundle.getInt(IntentExtras.NOTE_COLOR));
        }
    }
}
