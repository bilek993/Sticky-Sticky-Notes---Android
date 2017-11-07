package com.jakubbilinski.stickystickynotesandroid.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by jbili on 07.11.2017.
 */

public class LocalStorageHelper {

    private static String SERVER_ADDRESS = "serverAddress";

    public static String getServerAddress(Context context) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getString(SERVER_ADDRESS, null);
    }

    public static void setServerAddress(Context context, String value) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings.edit().putString(SERVER_ADDRESS, value).commit();
    }
}
