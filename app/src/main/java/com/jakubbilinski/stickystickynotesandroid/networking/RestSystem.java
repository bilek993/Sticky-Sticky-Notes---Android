package com.jakubbilinski.stickystickynotesandroid.networking;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.google.gson.GsonBuilder;
import com.jakubbilinski.stickystickynotesandroid.R;
import com.jakubbilinski.stickystickynotesandroid.helpers.Base64Converter;
import com.jakubbilinski.stickystickynotesandroid.helpers.LocalStorageHelper;

import java.io.UnsupportedEncodingException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jbili on 07.11.2017.
 */

public class RestSystem {

    public static RestClient buildSimple(Context context) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(LocalStorageHelper.getServerAddress(context))
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()));

        Retrofit retrofit = builder.build();
        return retrofit.create(RestClient.class);
    }

    public static RestClient buildWithAuthentication(Context context) {
        String login = LocalStorageHelper.getLogin(context);
        String password = LocalStorageHelper.getPassword(context);
        String authHeader = "";
        try {
            authHeader = "Basic " + Base64Converter.generateUserCredentials(login, password);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String finalAuthHeader = authHeader;
        OkHttpClient defaultOkHttpClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request authorisedRequest = chain.request().newBuilder()
                            .addHeader("Authorization", finalAuthHeader).build();
                    return chain.proceed(authorisedRequest);
                }).build();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(LocalStorageHelper.getServerAddress(context))
                .client(defaultOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()));

        Retrofit retrofit = builder.build();
        return retrofit.create(RestClient.class);
    }

    public static void showMessageUnknownError(Context context, Exception e) {
        new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.encountered_problem))
                .setMessage(e.getMessage())
                .setPositiveButton(context.getString(R.string.ok), null)
                .show();
    }

}
