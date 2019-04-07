package com.bradykoehler.cs246project;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import static android.content.Context.MODE_PRIVATE;

/**
 * This class provides functions for managing the application theme preferences
 */
public class ThemeManager {

    // SharedPreferences string constants
    public static final String PREFS_NAME = "prefs";
    public static final String PREF_DARK_THEME = "dark_theme";

    /**
     * Update the theme for the specified activity
     * @param activity activity to update
     */
    public static void setTheme(AppCompatActivity activity) {
        activity.setTheme(getTheme(activity));
    }

    /**
     * Return the currently selected theme based on preferences
     * @param activity activity to check
     * @return theme id
     */
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
