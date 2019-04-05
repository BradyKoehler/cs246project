package com.bradykoehler.cs246project;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.Scanner;

class AcaApi {
    private static final AcaApi ourInstance = new AcaApi();

    static AcaApi getInstance() {
        return ourInstance;
    }

    private static String baseUrl = "https://ifd58d1qri.execute-api.us-east-1.amazonaws.com/latest";

    private AcaApi() {

    }

    private String getAccessToken(AppCompatActivity activity) {
        SharedPreferences loginData = activity.getSharedPreferences("jwt", Context.MODE_PRIVATE);
        return loginData.getString("accessToken", null);
    }

    void getGrids(final NavMainActivity activity) {
        final WeakReference<NavMainActivity> main = new WeakReference<>(activity);

        Log.d("AcaApi", "Running getGrids()");
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
            try {
                Log.d("AcaApi", "Trying to open URL");

                // build request url
                InputStream response = new URL(baseUrl + "/grids?token=" + getAccessToken(activity)).openStream();

                // check for response
                try (Scanner scanner = new Scanner(response)) {
                    // read json
                    String responseBody = scanner.useDelimiter("\\A").next();

                    Log.d("AcaApi", responseBody);

                    final Grid[] grids = new Gson().fromJson(responseBody, Grid[].class);

                    Log.d("AcaApi", "Retrieved Grids list");

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

    void createGrid(final NavMainActivity activity, final String name) {
        final WeakReference<NavMainActivity> main = new WeakReference<>(activity);

        Log.d("AcaApi", "Running createGrid()");
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
            try {
                // build request url
                InputStream response = new URL(baseUrl + "/grids/create/" + name + "?token=" + getAccessToken(activity)).openStream();

                // check for response
                try (Scanner scanner = new Scanner(response)) {
                    // read json
                    String responseBody = scanner.useDelimiter("\\A").next();

                    Log.d("AcaApi", responseBody);

                    final Grid grid = new Gson().fromJson(responseBody, Grid.class);

                    Log.d("AcaApi", "Created Grid: " + responseBody);

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

    void getGrid(final GridActivity activity, final int id) {
        final WeakReference<GridActivity> main = new WeakReference<>(activity);

        Log.d("AcaApi", "Running getGrid()");
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
            try {
                Log.d("AcaApi", "Trying to open URL");

                // build request url
                InputStream response = new URL(baseUrl + "/grid/" + id + "?token=" + getAccessToken(activity)).openStream();

                // check for response
                try (Scanner scanner = new Scanner(response)) {
                    // read json
                    String responseBody = scanner.useDelimiter("\\A").next();

                    Log.d("AcaApi", responseBody);

                    final Tile[] tiles = new Gson().fromJson(responseBody, Tile[].class);
                    Log.d("AcaApi", "Retrieved Grid data");

                    main.get().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        Log.d("AcaApi", "Attempting to load tiles for grid");

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

    void getImages(final ActivityTileSel activity) {
        final WeakReference<ActivityTileSel> main = new WeakReference<>(activity);

        Log.d("AcaApi", "Running getImages()");
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
            try {
                Log.d("AcaApi", "Trying to open URL");

                // build request url
                InputStream response = new URL(baseUrl + "/images?token=" + getAccessToken(activity)).openStream();

                // check for response
                try (Scanner scanner = new Scanner(response)) {
                    // read json
                    String responseBody = scanner.useDelimiter("\\A").next();

                    Log.d("AcaApi", responseBody);

                    final Image[] images = new Gson().fromJson(responseBody, Image[].class);

                    Log.d("AcaApi", "Retrieved Images list");

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

    void createTile(final GridActivity activity, final int id, final int pos, final int gridId) {
        final WeakReference<GridActivity> main = new WeakReference<>(activity);

        Log.d("AcaApi", "Running createTile()");
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
            try {
                Log.d("AcaApi", "Trying to open URL");

                // build request url
                InputStream response = new URL(baseUrl + "/grid/" + gridId + "/tiles/new/" + id + "/" + pos + "?token=" + getAccessToken(activity)).openStream();

                // check for response
                try (Scanner scanner = new Scanner(response)) {
                    // read json
                    String responseBody = scanner.useDelimiter("\\A").next();

                    Log.d("AcaApi:createTile", responseBody);

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
