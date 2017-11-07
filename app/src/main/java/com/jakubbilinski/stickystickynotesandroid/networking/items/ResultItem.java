package com.jakubbilinski.stickystickynotesandroid.networking.items;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jbili on 07.11.2017.
 */

public class ResultItem {

    @SerializedName("Successful")
    @Expose
    private boolean successful = false;
    @SerializedName("ErrorMessage")
    @Expose
    private String errorMessage;

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
