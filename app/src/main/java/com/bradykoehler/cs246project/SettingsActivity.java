package com.bradykoehler.cs246project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

/**
 * This activity provides the options for customizing the user experience in the application
 */
public class SettingsActivity extends AppCompatActivity {

    /**
     * Initialize settings for the activity
     * @param savedInstanceState default
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Set the correct theme
        ThemeManager.setTheme(this, true);

        // Call override function
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Get current preferences
        SharedPreferences preferences = getSharedPreferences(ThemeManager.PREFS_NAME, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(ThemeManager.PREF_DARK_THEME, false);

        // Initialize toggle
        Switch toggle = findViewById(R.id.switch1);
        toggle.setChecked(useDarkTheme);

        // Set onChange listener for toggle
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean isChecked) {
                toggleTheme(isChecked);
            }
        });
    }

    /**
     * Handles the update of application theme preferences
     * @param darkTheme boolean
     */
    private void toggleTheme(boolean darkTheme) {

        // Update preferences entry
        SharedPreferences.Editor editor = getSharedPreferences(ThemeManager.PREFS_NAME, MODE_PRIVATE).edit();
        editor.putBoolean(ThemeManager.PREF_DARK_THEME, darkTheme);
        editor.apply();

        // Finish activity
        finish();

        // Create action intent
        Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());

        // Add intent flags to clear stack
        assert i != null;
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Reload app to set changes to theme
        startActivity(i);
    }
}
