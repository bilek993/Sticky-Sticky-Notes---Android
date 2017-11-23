package com.jakubbilinski.stickystickynotesandroid.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.jakubbilinski.stickystickynotesandroid.R;
import com.jakubbilinski.stickystickynotesandroid.animations.ImageViewLoopOpacityAnimator;
import com.jakubbilinski.stickystickynotesandroid.helpers.IntentExtras;
import com.jakubbilinski.stickystickynotesandroid.helpers.LocalStorageHelper;
import com.jakubbilinski.stickystickynotesandroid.helpers.NotesSynchronizationService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LandingActivity extends AppCompatActivity {

    @BindView(R.id.imageViewIconBackground)
    ImageView imageViewBackgroundIcon;

    private ImageViewLoopOpacityAnimator backgroundAnimator;
    private ResponseReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        ButterKnife.bind(this);

        playBackgroundAnimation();

        if (!checkCredentials()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            IntentFilter filter = new IntentFilter(IntentExtras.ACTION_BROADCAST_RESPONSE);
            filter.addCategory(Intent.CATEGORY_DEFAULT);
            receiver = new ResponseReceiver();
            registerReceiver(receiver, filter);

            Intent syncIntentService = new Intent(this, NotesSynchronizationService.class);
            startService(syncIntentService);
        }
    }

    private void playBackgroundAnimation() {
        backgroundAnimator = new ImageViewLoopOpacityAnimator(imageViewBackgroundIcon, 1000);
        backgroundAnimator.hideImage();
        backgroundAnimator.playAnimation();
    }

    private boolean checkCredentials () {
        return !(LocalStorageHelper.getLogin(this) == null ||
                LocalStorageHelper.getPassword(this) == null);
    }

    public class ResponseReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("test123", "IT WORKS!!!!!!!!!!!");

            Intent intentNotes = new Intent(LandingActivity.this, NotesActivity.class);
            startActivity(intentNotes);
            finish();
        }
    }
}
