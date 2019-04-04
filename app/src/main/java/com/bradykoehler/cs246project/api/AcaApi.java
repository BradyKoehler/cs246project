package com.bradykoehler.cs246project.api;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bradykoehler.cs246project.ActivityTileSel;
import com.bradykoehler.cs246project.Grid;
import com.bradykoehler.cs246project.GridActivity;
import com.bradykoehler.cs246project.Image;
import com.bradykoehler.cs246project.LoginActivity;
import com.bradykoehler.cs246project.NavMainActivity;
import com.bradykoehler.cs246project.Tile;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class AcaApi {
    private static final AcaApi ourInstance = new AcaApi();

    public static AcaApi getInstance() {
        return ourInstance;
    }

    public static Image img;

    public static String baseUrl = "https://ifd58d1qri.execute-api.us-east-1.amazonaws.com/latest";

    private AcaApi() {

    }

    private String getAccessToken(AppCompatActivity activity) {
        SharedPreferences loginData = activity.getSharedPreferences("jwt", Context.MODE_PRIVATE);
        String token = loginData.getString("accessToken", null);
        return token;
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

    public void createGrid(final NavMainActivity activity, final String name) {
        final WeakReference<NavMainActivity> main = new WeakReference<>(activity);

        Log.d("AcaApi", "Running createGrid()");
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
//                OutputStream out = null;
                try {


//                    JSONObject jsonParam = new JSONObject();
//                    jsonParam.put("name", name);
//                    String dataString = jsonParam.toString();
//
//                    URL url = new URL(baseUrl + "/grids/" + name);
//                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//                    urlConnection.setRequestMethod("POST");
//                    out = new BufferedOutputStream(urlConnection.getOutputStream());
//
//                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
//                    writer.write(dataString);
//                    writer.flush();
//                    writer.close();
//                    out.close();
//
//                    urlConnection.connect();
////
//                    Log.i("STATUS", String.valueOf(urlConnection.getResponseCode()));
//                    Log.i("MSG" , urlConnection.getResponseMessage());
//                    Log.i("BODY", urlConnection.getContent().toString());
//
//                    urlConnection.disconnect();

//
//                    URL url = new URL(baseUrl + "/grids");
//                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                    conn.setRequestMethod("POST");
//                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
//                    conn.setRequestProperty("Accept","application/json");
//                    conn.setDoOutput(true);
//                    conn.setDoInput(true);
//
//                    JSONObject jsonParam = new JSONObject();
//                    jsonParam.put("name", name);
////                    jsonParam.put("timestamp", 1488873360);
////                    jsonParam.put("uname", message.getUser());
////                    jsonParam.put("message", message.getMessage());
////                    jsonParam.put("latitude", 0D);
////                    jsonParam.put("longitude", 0D);
//
//                    Log.i("JSON", jsonParam.toString());
//                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
//                    //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
//                    os.writeBytes(jsonParam.toString());
//
//                    os.flush();
//                    os.close();
//
//                    Log.i("STATUS", String.valueOf(conn.getResponseCode()));
//                    Log.i("MSG" , conn.getResponseMessage());
//
//                    conn.disconnect();

//                     build request url
                    InputStream response = new URL(baseUrl + "/grids/create/" + name + "?token=" + getAccessToken(activity)).openStream();

                    // check for response
                    try (Scanner scanner = new Scanner(response)) {
                        // read json
                        String responseBody = scanner.useDelimiter("\\A").next();

                        Log.d("AcaApi", responseBody);

//                        final Image[] images = new Gson().fromJson(responseBody, Image[].class);
                        final Grid grid = new Gson().fromJson(responseBody, Grid.class);

                        Log.d("AcaApi", "Created Grid: " + responseBody);

                        main.get().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                Log.d("AcaApi", "Attempting to load images");
//                                main.get().getImages(images);
//                                main.get().loadImages(images);
                                main.get().addGrid(grid);
                            }
                        });
                    }

//                    main.get().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            main.get().startActivity(new Intent(main.get(), GridActivity.class));
//                        }
//                    });
//                    Log.d("AcaApi", "Trying to open URL");
//
//                    // build request url
//                    InputStream response = new URL(baseUrl + "/images").openStream();
//
//                    // check for response
//                    try (Scanner scanner = new Scanner(response)) {
//                        // read json
//                        String responseBody = scanner.useDelimiter("\\A").next();
//
//                        Log.d("AcaApi", responseBody);
//
//                        final Image[] images = new Gson().fromJson(responseBody, Image[].class);
//
//                        Log.d("AcaApi", "Retrieved Images list");
//
//                        main.get().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Log.d("AcaApi", "Attempting to load images");
////                                main.get().getImages(images);
//                                main.get().loadImages(images);
//                            }
//                        });
//                    }
                } catch (Exception e) {
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
                    InputStream response = new URL(baseUrl + "/grid/" + id + "?token=" + getAccessToken(activity)).openStream();

                    // check for response
                    try (Scanner scanner = new Scanner(response)) {
                        // read json
                        String responseBody = scanner.useDelimiter("\\A").next();

                        Log.d("AcaApi", responseBody);

//                        final Grid grid = new Gson().fromJson(responseBody, Grid.class);
                        final Tile[] tiles = new Gson().fromJson(responseBody, Tile[].class);
                        Log.d("AcaApi", "Retrieved Grid data");

                        main.get().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("AcaApi", "Attempting to load tiles for grid");

                                for (int i = 0; i < tiles.length; i++)
                                {
                                    Tile tile = tiles[i];
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

    public void createTile(final GridActivity activity, final int id, final int pos, final int gridId) {
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

//                        final Grid grid = new Gson().fromJson(responseBody, Grid.class);
//                        final Tile[] tiles = new Gson().fromJson(responseBody, Tile[].class);

                        main.get().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                Log.d("AcaApi", "Attempting to load tiles for grid");
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
