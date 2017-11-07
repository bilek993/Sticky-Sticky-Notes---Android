package com.jakubbilinski.stickystickynotesandroid.networking;

import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jbili on 07.11.2017.
 */

public class RestBuilder {

    public static RestClient buildSimple() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://192.168.1.6") // TODO: Change it to variable
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()));

        Retrofit retrofit = builder.build();
        return retrofit.create(RestClient.class);
    }
}
