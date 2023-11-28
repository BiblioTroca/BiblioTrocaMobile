package com.projeto.bibliotroca.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String NAME = "Bibliotroca";
    private final SharedPreferences globalPreferences;
    private final SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        globalPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        editor = globalPreferences.edit();
    }

    public void setToken(String token) {
        editor.putString("TOKEN", token);
        editor.apply();
    }

    public String getToken() {
        return globalPreferences.getString("TOKEN", "");
    }

    public void clearSession() {
        editor.clear();
        editor.apply();
    }
}
