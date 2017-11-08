package com.jakubbilinski.stickystickynotesandroid.networking;

import com.jakubbilinski.stickystickynotesandroid.networking.items.ResultItem;
import com.jakubbilinski.stickystickynotesandroid.networking.items.UserItem;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by jbili on 07.11.2017.
 */

public interface RestClient {

    @POST("/api/users")
    Call<ResultItem> verifyUserCredentials(@Body UserItem user);

    @PUT("/api/users")
    Call<ResultItem> createNewUser(@Body UserItem user);
}
