package com.jakubbilinski.stickystickynotesandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

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
    }

    private void playBackgroundAnimation() {
        backgroundAnimator = new ImageViewLoopOpacityAnimator(imageViewBackgroundIcon, 1000);
        backgroundAnimator.hideImage();
        backgroundAnimator.playAnimation();
    }
}
