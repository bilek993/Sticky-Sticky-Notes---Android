package com.jakubbilinski.stickystickynotesandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.jakubbilinski.stickystickynotesandroid.R;
import com.jakubbilinski.stickystickynotesandroid.animations.ImageViewLoopOpacityAnimator;
import com.jakubbilinski.stickystickynotesandroid.helpers.LocalStorageHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LandingActivity extends AppCompatActivity {

    @BindView(R.id.imageViewIconBackground)
    ImageView imageViewBackgroundIcon;

    private ImageViewLoopOpacityAnimator backgroundAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        ButterKnife.bind(this);

        playBackgroundAnimation();

        if (!checkCredentials()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, NotesActivity.class);
            startActivity(intent);
        }
        finish();
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
}
