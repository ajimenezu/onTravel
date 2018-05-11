package com.tec_diseno.ontravel.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

public class SnackBarManager {
    private static Snackbar sSnackBar;
    private static Context sContext;

    public static void displaySnackBar(String message, int duration, View view) {
        if (sSnackBar != null) {
            sSnackBar.dismiss();
        }
        sSnackBar = Snackbar.make(view,message,duration);
        sSnackBar.show();
    }

    public static void displayLongSnackBar(String message, View view) {
        displaySnackBar(message, Snackbar.LENGTH_LONG, view);
    }


}
