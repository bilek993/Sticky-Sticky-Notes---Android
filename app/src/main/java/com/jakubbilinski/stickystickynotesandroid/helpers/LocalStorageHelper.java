package com.jakubbilinski.stickystickynotesandroid.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by jbili on 07.11.2017.
 */

public class LocalStorageHelper {

    private static String SERVER_ADDRESS = "serverAddress";
    private static String LOGIN = "login";
    private static String PASSWORD = "password";

    // Loading values

    public static String getServerAddress(Context context) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getString(SERVER_ADDRESS, null);
    }

    public static String getLogin(Context context) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getString(LOGIN, null);
    }

    public static String getPassword(Context context) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getString(PASSWORD, null);
    }

    // Saving values

    public static void setServerAddress(Context context, String value) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings.edit().putString(SERVER_ADDRESS, value).commit();
    }

    public static void setLogin(Context context, String value) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings.edit().putString(LOGIN, value).commit();
    }

    public static void setPassword(Context context, String value) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings.edit().putString(PASSWORD, value).commit();
    }

    // Clearing data
    public static void removeAll(Context context) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.commit();
    }
}
