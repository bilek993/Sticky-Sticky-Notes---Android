package com.jakubbilinski.stickystickynotesandroid.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.jakubbilinski.stickystickynotesandroid.animations.ImageViewLoopOpacityAnimator;
import com.jakubbilinski.stickystickynotesandroid.R;

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

        // TODO: Remove this code
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void playBackgroundAnimation() {
        backgroundAnimator = new ImageViewLoopOpacityAnimator(imageViewBackgroundIcon, 1000);
        backgroundAnimator.hideImage();
        backgroundAnimator.playAnimation();
    }
}
