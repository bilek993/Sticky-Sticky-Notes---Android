package com.jakubbilinski.stickystickynotesandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ProgressBar;

import com.jakubbilinski.stickystickynotesandroid.R;
import com.jakubbilinski.stickystickynotesandroid.helpers.AddressVeryfication;
import com.jakubbilinski.stickystickynotesandroid.helpers.LocalStorageHelper;
import com.jakubbilinski.stickystickynotesandroid.networking.LoginNetworking;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.TextInputServerAddress)
    TextInputEditText textInputEditTextServerAddress;
    @BindView(R.id.TextInputUsername)
    TextInputEditText textInputEditTextUsername;
    @BindView(R.id.TextInputPassword)
    TextInputEditText textInputEditTextPassword;
    @BindView(R.id.progressBarLogin)
    ProgressBar progressBarLogin;
    @BindView(R.id.floatingActionButtonLogin)
    FloatingActionButton floatingActionButtonLogin;
    @BindView(R.id.buttonRegister)
    Button buttonRegister;

    private LoginNetworking networking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        networking = new LoginNetworking(this);
    }

    @OnClick(R.id.floatingActionButtonLogin)
    void onFloatingActionButtonLoginClick() {
        String address = textInputEditTextServerAddress.getText().toString();

        if (!AddressVeryfication.verify(this, address)) {
            return;
        }

        progressBarLogin.setVisibility(View.VISIBLE);
        disableFields();

        String username = textInputEditTextUsername.getText().toString();
        String password = textInputEditTextPassword.getText().toString();

        networking.Login(
                textInputEditTextServerAddress.getText().toString(),
                username, password,
                () -> {
                    LocalStorageHelper.setLogin(this, username);
                    LocalStorageHelper.setPassword(this, password);

                    Intent intent = new Intent(this, LandingActivity.class);
                    startActivity(intent);

                    finish();
                    return null;
                },
                () -> {
                    progressBarLogin.setVisibility(View.INVISIBLE);
                    enableFields();
                    return null;
                });
    }

    @OnClick(R.id.buttonRegister)
    void onRegisterButtonClick() {
        String address = textInputEditTextServerAddress.getText().toString();

        if (!AddressVeryfication.verify(this, address)) {
            return;
        }

        progressBarLogin.setVisibility(View.VISIBLE);
        disableFields();

        networking.CreateUser(
                textInputEditTextServerAddress.getText().toString(),
                textInputEditTextUsername.getText().toString(),
                textInputEditTextPassword.getText().toString(),
                () -> {
                    progressBarLogin.setVisibility(View.INVISIBLE);
                    enableFields();
                    return null;
                });
    }

    private void disableFields() {
        textInputEditTextServerAddress.setEnabled(false);
        textInputEditTextUsername.setEnabled(false);
        textInputEditTextPassword.setEnabled(false);
        floatingActionButtonLogin.setEnabled(false);
        buttonRegister.setEnabled(false);

        AlphaAnimation animationAlpha = new AlphaAnimation(1f, 0f);
        animationAlpha.setDuration(250);
        animationAlpha.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                floatingActionButtonLogin.setVisibility(View.INVISIBLE);
                buttonRegister.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        floatingActionButtonLogin.startAnimation(animationAlpha);
        buttonRegister.startAnimation(animationAlpha);
    }

    private void enableFields() {
        textInputEditTextServerAddress.setEnabled(true);
        textInputEditTextUsername.setEnabled(true);
        textInputEditTextPassword.setEnabled(true);
        floatingActionButtonLogin.setEnabled(true);
        buttonRegister.setEnabled(true);

        AlphaAnimation animationAlpha = new AlphaAnimation(0f, 1f);
        animationAlpha.setDuration(250);
        floatingActionButtonLogin.setVisibility(View.VISIBLE);
        buttonRegister.setVisibility(View.VISIBLE);
        floatingActionButtonLogin.startAnimation(animationAlpha);
        buttonRegister.startAnimation(animationAlpha);
    }
}
