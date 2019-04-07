package com.bradykoehler.cs246project;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * This activity displays a form allowing the user to login
 */
public class LoginActivity extends AppCompatActivity {

    // Store references to form inputs
    EditText mUsernameView;
    EditText mPasswordView;

    /**
     * Initialize settings for the activity
     * @param savedInstanceState default
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Set correct theme
        ThemeManager.setTheme(this, true);

        // Call override functions
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Get stored authentication data
        SharedPreferences loginData = getSharedPreferences("loginData", Context.MODE_PRIVATE);
        String username = loginData.getString("username", "");
        String password = loginData.getString("password", "");

        // Store references to form inputs
        mUsernameView = findViewById(R.id.username);
        mPasswordView = findViewById(R.id.password);

        // Fill form with stored data
        mUsernameView.setText(username);
        mPasswordView.setText(password);

        // Attempt to log in automatically
        if (loginData.getBoolean("valid", false)) {
            login(username, password);
        }

        // Find sign in button
        Button mEmailSignInButton = findViewById(R.id.email_sign_in_button);

        // Set onClick listener for sign in button
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get input data
                String username = mUsernameView.getText().toString();
                String password = mPasswordView.getText().toString();

                // Store input
                SharedPreferences loginData = getSharedPreferences("loginData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = loginData.edit();
                editor.putString("username", username);
                editor.putString("password", password);
                editor.putBoolean("valid", false);
                editor.apply();

                // Authenticate the user
                login(username, password);
            }
        });
    }

    /**
     * Authenticate the user using a username and password
     * @param username login username
     * @param password login password
     */
    private void login(String username, String password) {

        // Create authentication object
        Cognito auth = new Cognito(getApplicationContext());

        // Begin authentication
        auth.userLogin(username, password);
    }
}

