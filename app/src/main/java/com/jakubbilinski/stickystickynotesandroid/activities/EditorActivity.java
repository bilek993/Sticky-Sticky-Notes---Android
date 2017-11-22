package com.jakubbilinski.stickystickynotesandroid.activities;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.jakubbilinski.stickystickynotesandroid.R;
import com.jakubbilinski.stickystickynotesandroid.helpers.IntentExtras;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditorActivity extends AppCompatActivity {

    @BindView(R.id.editTextContext)
    EditText editTextContext;
    @BindView(R.id.textViewDate)
    TextView textViewDate;
    @BindView(R.id.ConstraintLayoutEditor)
    ConstraintLayout constraintLayoutEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            editTextContext.setText(bundle.getString(IntentExtras.NOTE_CONTEXT));
            textViewDate.setText(bundle.getString(IntentExtras.NOTE_DATE));
            constraintLayoutEditor.setBackgroundColor(bundle.getInt(IntentExtras.NOTE_COLOR));
        }
    }
}
