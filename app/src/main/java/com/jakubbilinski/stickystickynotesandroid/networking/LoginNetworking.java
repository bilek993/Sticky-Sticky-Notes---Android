package com.jakubbilinski.stickystickynotesandroid.networking;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.jakubbilinski.stickystickynotesandroid.R;
import com.jakubbilinski.stickystickynotesandroid.helpers.LocalStorageHelper;
import com.jakubbilinski.stickystickynotesandroid.networking.items.ResultItem;
import com.jakubbilinski.stickystickynotesandroid.networking.items.UserItem;

import java.util.concurrent.Callable;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by jbili on 08.11.2017.
 */

public class LoginNetworking {

    Context context;

    public LoginNetworking(Context context) {
        this.context = context;
    }

    public void Login(String address, String username, String password, Callable<Void> OnSuccess, Callable<Void> onFinish) {
        try {
            LocalStorageHelper.setServerAddress(context, address);

            RestClient restClient = RestSystem.buildSimple(context);
            Call<ResultItem> call = restClient.verifyUserCredentials(
                    new UserItem(username, password));

            call.enqueue(new AdvancedCallback<ResultItem>(context) {
                @Override
                public void onRetry() {
                    Login(address, username, password, OnSuccess, onFinish);
                }

                @Override
                public void onResponse(Call<ResultItem> call, Response<ResultItem> response) {
                    super.onResponse(call, response);

                    if (response.isSuccessful()) {
                        if (response.body().isSuccessful()){
                            try {
                                OnSuccess.call();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            new AlertDialog.Builder(context)
                                    .setTitle(context.getString(R.string.encountered_problem))
                                    .setMessage(response.body().getErrorMessage())
                                    .setPositiveButton(context.getText(R.string.ok), null)
                                    .show();
                        }
                    }

                    finished(onFinish);
                }

                @Override
                public void onFailure(Call<ResultItem> call, Throwable t) {
                    super.onFailure(call, t);
                    finished(onFinish);
                }
            });

        } catch (Exception e) {
            RestSystem.showMessageUnknownError(context, e);
            finished(onFinish);
        }

    }

    public void CreateUser(String address, String username, String password, Callable<Void> onFinish) {
        try {
            LocalStorageHelper.setServerAddress(context, address);

            RestClient restClient = RestSystem.buildSimple(context);
            Call<ResultItem> call = restClient.createNewUser(
                    new UserItem(username, password));

            call.enqueue(new AdvancedCallback<ResultItem>(context) {
                @Override
                public void onRetry() {
                    CreateUser(address, username, password, onFinish);
                }

                @Override
                public void onResponse(Call<ResultItem> call, Response<ResultItem> response) {
                    super.onResponse(call, response);

                    if (response.isSuccessful()) {
                        if (response.body().isSuccessful()){
                            new AlertDialog.Builder(context)
                                    .setTitle(context.getString(R.string.new_user_title))
                                    .setMessage(context.getString(R.string.new_user))
                                    .setPositiveButton(context.getText(R.string.ok), null)
                                    .show();
                        } else {
                            new AlertDialog.Builder(context)
                                    .setTitle(context.getString(R.string.encountered_problem))
                                    .setMessage(response.body().getErrorMessage())
                                    .setPositiveButton(context.getText(R.string.ok), null)
                                    .show();
                        }
                    }

                    finished(onFinish);
                }

                @Override
                public void onFailure(Call<ResultItem> call, Throwable t) {
                    super.onFailure(call, t);
                    finished(onFinish);
                }
            });

        } catch (Exception e) {
            RestSystem.showMessageUnknownError(context, e);
            finished(onFinish);
        }
    }

    private void finished(Callable<Void> onFinish) {
        try {
            onFinish.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
