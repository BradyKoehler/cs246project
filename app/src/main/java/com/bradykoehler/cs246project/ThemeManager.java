package com.bradykoehler.cs246project;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import static android.content.Context.MODE_PRIVATE;

public class ThemeManager {

    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_THEME = "dark_theme";

    public static void setTheme(AppCompatActivity activity) {
        activity.setTheme(getTheme(activity));
    }

    public static int getTheme(AppCompatActivity activity) {
        SharedPreferences preferences = activity.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);

        if(useDarkTheme) {
            return R.style.AppTheme_Dark_NoActionBar;
        } else {
            return R.style.AppTheme_NoActionBar;
        }
    }
}
