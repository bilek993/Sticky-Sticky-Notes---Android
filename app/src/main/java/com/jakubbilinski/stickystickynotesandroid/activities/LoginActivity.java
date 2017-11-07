package com.jakubbilinski.stickystickynotesandroid.activities;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.jakubbilinski.stickystickynotesandroid.R;
import com.jakubbilinski.stickystickynotesandroid.helpers.LocalStorageHelper;
import com.jakubbilinski.stickystickynotesandroid.networking.AdvancedCallback;
import com.jakubbilinski.stickystickynotesandroid.networking.RestBuilder;
import com.jakubbilinski.stickystickynotesandroid.networking.RestClient;
import com.jakubbilinski.stickystickynotesandroid.networking.items.ResultItem;
import com.jakubbilinski.stickystickynotesandroid.networking.items.UserItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.TextInputServerAddress)
    TextInputEditText textInputEditTextServerAddress;
    @BindView(R.id.TextInputUsername)
    TextInputEditText textInputEditTextUsername;
    @BindView(R.id.TextInputPassword)
    TextInputEditText textInputEditTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.floatingActionButtonLogin)
    void onFloatingActionButtonLoginClick() {
        String address = textInputEditTextServerAddress.getText().toString();
        if (!address.startsWith("http:") && !address.startsWith("https:")) {
            new AlertDialog.Builder(LoginActivity.this)
                    .setTitle(getString(R.string.encountered_problem))
                    .setMessage(getString(R.string.wrong_url_format))
                    .setPositiveButton(getText(R.string.ok), null)
                    .show();
            return;
        }


        try {
            LocalStorageHelper.setServerAddress(this, address);

            RestClient restClient = RestBuilder.buildSimple(this);
            Call<ResultItem> call = restClient.verifyUserCredentials(
                    new UserItem(textInputEditTextUsername.getText().toString(),
                            textInputEditTextPassword.getText().toString()));

            call.enqueue(new AdvancedCallback<ResultItem>(this) {
                @Override
                public void onRetry() {
                    onFloatingActionButtonLoginClick();
                }

                @Override
                public void onResponse(Call<ResultItem> call, Response<ResultItem> response) {
                    super.onResponse(call, response);

                    if (response.isSuccessful()) {
                        if (response.body().isSuccessful()){
                            finish();
                        } else {
                            new AlertDialog.Builder(LoginActivity.this)
                                    .setTitle(getString(R.string.encountered_problem))
                                    .setMessage(response.body().getErrorMessage())
                                    .setPositiveButton(getText(R.string.ok), null)
                                    .show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResultItem> call, Throwable t) {
                    super.onFailure(call, t);
                }
            });

        } catch (Exception e) {

            new AlertDialog.Builder(LoginActivity.this)
                    .setTitle(getString(R.string.encountered_problem))
                    .setMessage(e.getMessage())
                    .setPositiveButton(getText(R.string.ok), null)
                    .show();
        }
    }
}
