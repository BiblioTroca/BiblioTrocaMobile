package com.projeto.bibliotroca.utils;

import android.content.Context;

public class AlertModal {
    public static void show(Context context, String message) {
        new android.app.AlertDialog.Builder(context)
                .setTitle("Atenção")
                .setMessage(message)
                .show();
    }
}
