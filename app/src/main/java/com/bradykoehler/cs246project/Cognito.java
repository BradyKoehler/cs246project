package com.bradykoehler.cs246project;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;
import com.amazonaws.regions.Regions;

import static android.content.ContentValues.TAG;

/**
 * The Cognito class handles user authentication by utilizing the
 * Amazon AWS Cognito API.
 */
class Cognito {
    private CognitoUserPool userPool;
    private Context appContext;
    private String userPassword;

    /**
     * Creates a new Cognito instance
     * @param context application context
     */
    Cognito(Context context){

        // Store application context
        appContext = context;

        // User pool attributes
        String poolID = "us-east-1_jj2mY0NQq";
        String clientID = "5kpm479btm057jd9738h61gu1e";
        String clientSecret = "dgrb6l8jvuqvoiqd1munbpg5gk8abko07jabfd2neprfr11bous";
        Regions awsRegion = Regions.US_EAST_1;

        // Create user pool object
        userPool = new CognitoUserPool(context, poolID, clientID, clientSecret, awsRegion);
    }

    /**
     * Authenticates a user with username and password
     * @param username input username
     * @param password input password
     */
    void userLogin(String username, String password){

        // Create user object
        CognitoUser cognitoUser =  userPool.getUser(username);

        // Store input password
        this.userPassword = password;

        // Begin authentication process
        cognitoUser.getSessionInBackground(authenticationHandler);
    }

    // Callback handler for the sign-in process
    private AuthenticationHandler authenticationHandler = new AuthenticationHandler() {

        /**
         * Handles authentication challenges
         * @param continuation passed from previous function
         */
        @Override
        public void authenticationChallenge(ChallengeContinuation continuation) {
            // handle authentication challenges
        }

        /**
         * Runs after successful sign-in
         * @param userSession session object for user
         * @param newDevice device identifier
         */
        @Override
        public void onSuccess(CognitoUserSession userSession, CognitoDevice newDevice) {
            Toast.makeText(appContext,"Signed in", Toast.LENGTH_LONG).show();

            // Store tokens in shared preferences
            String accessToken = userSession.getAccessToken().getJWTToken();
            String idToken = userSession.getIdToken().getJWTToken();

            SharedPreferences jwt = appContext.getSharedPreferences("jwt", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = jwt.edit();

            editor.putString("accessToken", accessToken);
            editor.putString("idToken", idToken);

            editor.apply();

            // Mark stored credentials as valid
            SharedPreferences loginData = appContext.getSharedPreferences("loginData", Context.MODE_PRIVATE);
            SharedPreferences.Editor loginEditor = loginData.edit();
            loginEditor.putBoolean("valid", true);
            loginEditor.apply();

            Log.d(TAG, "Signed in");

            // Create intent
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setClass(appContext, GridsActivity.class);

            // Proceed to main application
            appContext.startActivity(intent);
        }

        /**
         * Primary authentication function. Sends auth details to server.
         * @param authenticationContinuation passed from previous function
         * @param userId unique identifier for user
         */
        @Override
        public void getAuthenticationDetails(AuthenticationContinuation authenticationContinuation, String userId) {

            // The API needs user sign-in credentials to continue
            AuthenticationDetails authenticationDetails = new AuthenticationDetails(userId, userPassword, null);

            // Pass the user sign-in credentials to the continuation
            authenticationContinuation.setAuthenticationDetails(authenticationDetails);

            // Allow the sign-in to continue
            authenticationContinuation.continueTask();
        }

        /**
         * Handles the MFA authentication
         * @param multiFactorAuthenticationContinuation passed from previous function
         */
        @Override
        public void getMFACode(MultiFactorAuthenticationContinuation multiFactorAuthenticationContinuation) {
            // handle MFA

            // Allow the sign-in process to continue
            multiFactorAuthenticationContinuation.continueTask();
        }

        /**
         * Runs when the sign-in process fails
         * @param exception sign-in exception thrown
         */
        @Override
        public void onFailure(Exception exception) {
            // Sign-in failed, check exception for the cause
            Toast.makeText(appContext,"Sign in Failure", Toast.LENGTH_LONG).show();

            // Mark stored credentials as invalid
            SharedPreferences loginData = appContext.getSharedPreferences("loginData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = loginData.edit();
            editor.putBoolean("valid", false);
            editor.apply();
        }
    };
}