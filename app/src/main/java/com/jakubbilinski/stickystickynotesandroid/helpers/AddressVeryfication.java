package com.jakubbilinski.stickystickynotesandroid.helpers;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.jakubbilinski.stickystickynotesandroid.R;
import com.jakubbilinski.stickystickynotesandroid.activities.LoginActivity;

/**
 * Created by jbili on 11.12.2017.
 */

public class AddressVeryfication {

    public static boolean verify(Context context, String address) {
        if (!address.startsWith("http:") && !address.startsWith("https:")) {
            new AlertDialog.Builder(context)
                    .setTitle(context.getString(R.string.encountered_problem))
                    .setMessage(context.getString(R.string.wrong_url_format))
                    .setPositiveButton(context.getString(R.string.ok), null)
                    .show();
            return false;
        } else {
            return true;
        }
    }
}
