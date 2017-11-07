package com.jakubbilinski.stickystickynotesandroid.networking;

import android.content.Context;

import com.google.gson.GsonBuilder;
import com.jakubbilinski.stickystickynotesandroid.helpers.LocalStorageHelper;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jbili on 07.11.2017.
 */

public class RestBuilder {

    public static RestClient buildSimple(Context context) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(LocalStorageHelper.getServerAddress(context))
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()));

        Retrofit retrofit = builder.build();
        return retrofit.create(RestClient.class);
    }
}
