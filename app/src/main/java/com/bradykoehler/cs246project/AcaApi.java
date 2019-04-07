package com.bradykoehler.cs246project;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

/**
 * The AcaApi class provides functions for sending and
 * retrieving data from the API server.
 */
class AcaApi {

    // Main url for API requests
    private static String baseUrl = "https://ifd58d1qri.execute-api.us-east-1.amazonaws.com/latest";

    /**
     * Returns the API access token
     * @param activity needed to access SharedPreferences
     * @return access token string
     */
    private static String getAccessToken(AppCompatActivity activity) {
        SharedPreferences loginData = activity.getSharedPreferences("jwt", Context.MODE_PRIVATE);
        return loginData.getString("accessToken", null);
    }

    /**
     * Submits a request for a new image
     * @param activity activity to return results
     * @param message image request content
     */
    static void createRequest(final GridsActivity activity, final String message) {
        final WeakReference<GridsActivity> main = new WeakReference<>(activity);

        Log.d("AcaApi", "Running createRequest()");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Build URL and start connection
                    URL obj = new URL(baseUrl + "/request?token=" + getAccessToken(activity));
                    HttpsURLConnection conn = (HttpsURLConnection) obj.openConnection();

                    // Set request headers
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("User-Agent", "Java Client");
                    conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

                    String urlParameters = "message=" + message;

                    // Send request
                    conn.setDoOutput(true);
                    DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                    wr.writeBytes(urlParameters);
                    wr.flush();
                    wr.close();

                    // Get HTTP response code
                    final int responseCode = conn.getResponseCode();

                    conn.disconnect();

                    // Notify user of result
                    main.get().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (responseCode == 200) {
                                Toast.makeText(main.get(),"Request submitted", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(main.get(),"Error: Request not submitted", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * Retrieves a list of grids
     * @param activity activity to return results
     */
    static void getGrids(final GridsActivity activity) {
        final WeakReference<GridsActivity> main = new WeakReference<>(activity);

        Log.d("AcaApi", "Running getGrids()");
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                try {

                    // Build request url
                    InputStream response = new URL(baseUrl + "/grids?token=" + getAccessToken(activity)).openStream();

                    // Check for response
                    try (Scanner scanner = new Scanner(response)) {

                        // Read json
                        String responseBody = scanner.useDelimiter("\\A").next();

                        // Parse response JSON
                        final Grid[] grids = new Gson().fromJson(responseBody, Grid[].class);

                        Log.d("AcaApi", "Retrieved Grids list");

                        // Load Grids into list
                        main.get().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            Log.d("AcaApi", "Attempting to load new grids");
                            main.get().loadGrids(grids);
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * Creates a new grid
     * @param activity activity to return results
     * @param name name for the new Grid
     */
    static void createGrid(final GridsActivity activity, final String name) {
        final WeakReference<GridsActivity> main = new WeakReference<>(activity);

        Log.d("AcaApi", "Running createGrid()");
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                try {

                    // Build request url
                    InputStream response = new URL(baseUrl + "/grids/create/" + name + "?token=" + getAccessToken(activity)).openStream();

                    // Check for response
                    try (Scanner scanner = new Scanner(response)) {

                        // Read json
                        String responseBody = scanner.useDelimiter("\\A").next();

                        // Get Grid data from JSON
                        final Grid grid = new Gson().fromJson(responseBody, Grid.class);

                        Log.d("AcaApi", "Created Grid: " + responseBody);

                        // Update user view
                        main.get().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                main.get().addGrid(grid);
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * Gets the list of Tiles belonging to a Grid
     * @param activity activity to return results
     * @param id identifier for the selected Grid
     */
    static void getGrid(final GridActivity activity, final int id) {
        final WeakReference<GridActivity> main = new WeakReference<>(activity);

        Log.d("AcaApi", "Running getGrid()");
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                try {
                    Log.d("AcaApi", "Trying to open URL");

                    // Build request url
                    InputStream response = new URL(baseUrl + "/grid/" + id + "?token=" + getAccessToken(activity)).openStream();

                    // Check for response
                    try (Scanner scanner = new Scanner(response)) {

                        // Read json
                        String responseBody = scanner.useDelimiter("\\A").next();

                        // Parse JSON data
                        final Tile[] tiles = new Gson().fromJson(responseBody, Tile[].class);

                        Log.d("AcaApi", "Retrieved Grid data");

                        // Load Tiles into view
                        main.get().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("AcaApi", "Attempting to load tiles for grid");

                                // Load each Tile
                                for (Tile tile : tiles) {
                                    main.get().setTile(tile);
                                }
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * Gets a list of all images
     * @param activity activity to return results
     */
    static void getImages(final ImagesActivity activity) {
        getImages(activity, "");
    }

    /**
     * Gets a list of images matching the search query
     * @param activity activity to return results
     * @param search query string
     */
    static void getImages(final ImagesActivity activity, final String search) {
        final WeakReference<ImagesActivity> main = new WeakReference<>(activity);

        Log.d("AcaApi", "Running getImages()");
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                try {
                    Log.d("AcaApi", "Trying to open URL");

                    // Build request url
                    InputStream response = new URL(baseUrl + "/images?search=" + search + "&token=" + getAccessToken(activity)).openStream();

                    // Check for response
                    try (Scanner scanner = new Scanner(response)) {

                        // Read json
                        String responseBody = scanner.useDelimiter("\\A").next();

                        // Get Images data from JSON
                        final Image[] images = new Gson().fromJson(responseBody, Image[].class);

                        Log.d("AcaApi", "Retrieved Images list");

                        // Load images into view
                        main.get().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("AcaApi", "Attempting to load images");
                                main.get().loadImages(images);
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * Creates a new Tile for a Grid
     * @param activity activity to return results
     * @param id identifier for the selected image
     * @param pos position for the new tile
     * @param gridId identifier for the selected grid
     */
    static void createTile(final GridActivity activity, final int id, final int pos, final int gridId) {
        final WeakReference<GridActivity> main = new WeakReference<>(activity);

        Log.d("AcaApi", "Running createTile()");
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                try {
                    Log.d("AcaApi", "Trying to open URL");

                    // Build request url
                    InputStream response = new URL(baseUrl + "/grid/" + gridId + "/tiles/new/" + id + "/" + pos + "?token=" + getAccessToken(activity)).openStream();

                    // Check for response
                    try (Scanner scanner = new Scanner(response)) {

                        // Read json
                        String responseBody = scanner.useDelimiter("\\A").next();

                        Log.d("AcaApi:createTile", responseBody);

                        // Return to main thread
                        main.get().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // Do something with the tiles here
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
