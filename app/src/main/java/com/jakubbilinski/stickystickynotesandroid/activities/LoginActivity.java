package com.jakubbilinski.stickystickynotesandroid.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jakubbilinski.stickystickynotesandroid.R;
import com.jakubbilinski.stickystickynotesandroid.networking.AdvancedCallback;
import com.jakubbilinski.stickystickynotesandroid.networking.RestBuilder;
import com.jakubbilinski.stickystickynotesandroid.networking.RestClient;
import com.jakubbilinski.stickystickynotesandroid.networking.items.ResultItem;
import com.jakubbilinski.stickystickynotesandroid.networking.items.UserItem;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.floatingActionButtonLogin)
    void onFloatingActionButtonLoginClick() {
        RestClient restClient = RestBuilder.buildSimple();
        Call<ResultItem> call = restClient.verifyUserCredentials(new UserItem());

        call.enqueue(new AdvancedCallback<ResultItem>(this) {
            @Override
            public void onRetry() {
                onFloatingActionButtonLoginClick();
            }

            @Override
            public void onResponse(Call<ResultItem> call, Response<ResultItem> response) {
                super.onResponse(call, response);

                Log.e("test123", "onResponse");
            }

            @Override
            public void onFailure(Call<ResultItem> call, Throwable t) {
                super.onFailure(call, t);

                Log.e("test123", "onFailure");
            }
        });
    }
}
