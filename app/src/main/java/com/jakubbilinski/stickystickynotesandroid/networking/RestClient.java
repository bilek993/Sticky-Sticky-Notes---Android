package com.jakubbilinski.stickystickynotesandroid.networking;

import com.jakubbilinski.stickystickynotesandroid.networking.items.ResultItem;
import com.jakubbilinski.stickystickynotesandroid.networking.items.UserItem;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by jbili on 07.11.2017.
 */

public interface RestClient {

    @POST("/api/users")
    Call<ResultItem> verifyUserCredentials(@Body UserItem user);
}
