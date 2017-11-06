package com.jakubbilinski.stickystickynotesandroid.animations;

import android.animation.ValueAnimator;
import android.view.animation.Animation;
import android.widget.ImageView;

/**
 * Created by jbili on 06.11.2017.
 */

public class ImageViewLoopOpacityAnimator {

    private ImageView imageViewAnimated;
    private ValueAnimator animation;

    public ImageViewLoopOpacityAnimator(ImageView imageViewAnimated, long duration) {
        this.imageViewAnimated = imageViewAnimated;

        animation = ValueAnimator.ofFloat(0f, 1f);
        animation.addUpdateListener((animation) -> {
            imageViewAnimated.setAlpha((float) animation.getAnimatedValue());
        });
        animation.setDuration(duration);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(ValueAnimator.REVERSE);
    }

    public void hideImage() {
        imageViewAnimated.setAlpha(0f);
    }

    public void showImage() {
        imageViewAnimated.setAlpha(1f);
    }

    public void playAnimation() {
        animation.start();
    }

    public void stopAnimation() {
        animation.cancel();
    }
}
