package com.jakubbilinski.stickystickynotesandroid.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.jakubbilinski.stickystickynotesandroid.R;
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

    private boolean checkServerAddress(String address) {
        if (!address.startsWith("http:") && !address.startsWith("https:")) {
            new AlertDialog.Builder(LoginActivity.this)
                    .setTitle(getString(R.string.encountered_problem))
                    .setMessage(getString(R.string.wrong_url_format))
                    .setPositiveButton(getText(R.string.ok), null)
                    .show();
            return true;
        } else {
            return false;
        }
    }

    @OnClick(R.id.floatingActionButtonLogin)
    void onFloatingActionButtonLoginClick() {
        String address = textInputEditTextServerAddress.getText().toString();

        if (checkServerAddress(address)) {
            return;
        }

        progressBarLogin.setVisibility(View.VISIBLE);
        disableFields();

        networking.Login(
                textInputEditTextServerAddress.getText().toString(),
                textInputEditTextUsername.getText().toString(),
                textInputEditTextPassword.getText().toString(),
                () -> {
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

        if (checkServerAddress(address)) {
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
    }

    private void enableFields() {
        textInputEditTextServerAddress.setEnabled(true);
        textInputEditTextUsername.setEnabled(true);
        textInputEditTextPassword.setEnabled(true);
        floatingActionButtonLogin.setEnabled(true);
        buttonRegister.setEnabled(true);
    }
}
