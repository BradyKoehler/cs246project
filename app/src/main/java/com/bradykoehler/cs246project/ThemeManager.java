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
     * Update the theme for the specified activity.
     * Default to no action bar.
     * @param activity activity to update
     */
    public static void setTheme(AppCompatActivity activity) {
        setTheme(activity, false);
    }

    /**
     * Update the theme for the specified activity
     * @param activity activity to update
     * @param action_bar whether or not to use the action bar
     */
    public static void setTheme(AppCompatActivity activity, boolean action_bar) {
        if (action_bar) {
            activity.setTheme(getThemeWithActionBar(activity));
        } else {
            activity.setTheme(getTheme(activity));
        }
    }

    /**
     * Return the currently selected theme based on preferences.
     * Do not include action bar.
     * @param activity activity to check
     * @return theme id
     */
    public static int getTheme(AppCompatActivity activity) {
        if(usingDarkTheme(activity)) {
            return R.style.AppTheme_Dark_NoActionBar;
        } else {
            return R.style.AppTheme_NoActionBar;
        }
    }

    /**
     * Return the currently selected theme based on preferences.
     * Include action bar.
     * @param activity activity to check
     * @return theme id
     */
    public static int getThemeWithActionBar(AppCompatActivity activity) {
        if(usingDarkTheme(activity)) {
            return R.style.AppTheme_Dark;
        } else {
            return R.style.AppTheme;
        }
    }

    /**
     * Checks if the dark theme is enabled
     * @return boolean
     */
    public static boolean usingDarkTheme(AppCompatActivity activity) {
        SharedPreferences preferences = activity.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return preferences.getBoolean(PREF_DARK_THEME, false);
    }
}
