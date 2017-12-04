package com.jakubbilinski.stickystickynotesandroid.activities;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.jakubbilinski.stickystickynotesandroid.R;
import com.jakubbilinski.stickystickynotesandroid.animations.ImageViewLoopOpacityAnimator;
import com.jakubbilinski.stickystickynotesandroid.helpers.IntentExtras;
import com.jakubbilinski.stickystickynotesandroid.helpers.LocalStorageHelper;
import com.jakubbilinski.stickystickynotesandroid.services.NotesSynchronizationService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LandingActivity extends AppCompatActivity {

    @BindView(R.id.imageViewIconBackground)
    ImageView imageViewBackgroundIcon;
    @BindView(R.id.imageViewIconSimple)
    ImageView imageViewIconSimple;

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

    private void hideLogo() {
        ValueAnimator animatior = ValueAnimator.ofFloat(1f, 0f);
        animatior.addUpdateListener((animation) -> {
            imageViewIconSimple.setAlpha((float) animation.getAnimatedValue());
        });
        animatior.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                Intent intentNotes = new Intent(LandingActivity.this, NotesActivity.class);
                startActivity(intentNotes);
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        animatior.setDuration(1000);
        animatior.start();
    }

    public class ResponseReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            unregisterReceiver(receiver);
            LandingActivity.this.hideLogo();
        }
    }
}
