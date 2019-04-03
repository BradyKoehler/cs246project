package com.bradykoehler.cs246project.api;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.bradykoehler.cs246project.ActivityTileSel;
import com.bradykoehler.cs246project.Grid;
import com.bradykoehler.cs246project.GridActivity;
import com.bradykoehler.cs246project.Image;
import com.bradykoehler.cs246project.NavMainActivity;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.Scanner;

public class AcaApi {
    private static final AcaApi ourInstance = new AcaApi();

    public static AcaApi getInstance() {
        return ourInstance;
    }

    public static Image img;

    public static String baseUrl = "https://glacial-anchorage-60833.herokuapp.com";

    private AcaApi() {

    }

    public void getGrids(final NavMainActivity activity) {
        final WeakReference<NavMainActivity> main = new WeakReference<>(activity);

        Log.d("AcaApi", "Running getGrids()");
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                try {
                    Log.d("AcaApi", "Trying to open URL");

                    // build request url
                    InputStream response = new URL(baseUrl + "/grids").openStream();

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

    public void getGrid(final GridActivity activity, final int id) {
        final WeakReference<GridActivity> main = new WeakReference<>(activity);

        Log.d("AcaApi", "Running getGrid()");
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                try {
                    Log.d("AcaApi", "Trying to open URL");

                    // build request url
                    InputStream response = new URL(baseUrl + "/grid/" + id).openStream();

                    // check for response
                    try (Scanner scanner = new Scanner(response)) {
                        // read json
                        String responseBody = scanner.useDelimiter("\\A").next();

                        Log.d("AcaApi", responseBody);

                        final Grid grid = new Gson().fromJson(responseBody, Grid.class);

                        Log.d("AcaApi", "Retrieved Grid data");

                        main.get().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("AcaApi", "Attempting to load grid data");
                                // Do something with the grid data here
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void getImages(final ActivityTileSel activity) {
        final WeakReference<ActivityTileSel> main = new WeakReference<>(activity);

        Log.d("AcaApi", "Running getImages()");
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                try {
                    Log.d("AcaApi", "Trying to open URL");

                    // build request url
                    InputStream response = new URL(baseUrl + "/images").openStream();

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
//                                main.get().getImages(images);
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
}
