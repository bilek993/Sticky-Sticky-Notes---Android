package com.jakubbilinski.stickystickynotesandroid.helpers;

import android.util.Base64;

import java.io.UnsupportedEncodingException;

/**
 * Created by jbili on 29.11.2017.
 */

public class Base64Converter {

    public static String encode(String input) throws UnsupportedEncodingException {
        byte[] valueBytes = input.getBytes("UTF-8");
        byte[] result = Base64.encode(valueBytes, Base64.DEFAULT) ;
        return new String(result, "UTF-8").trim().replace("\r","");
    }

    public static String generateUserCredentials(String login, String password) throws UnsupportedEncodingException {
        String mergedLoginAndPassword = login + ':' + password;
        return encode(mergedLoginAndPassword);
    }
}
