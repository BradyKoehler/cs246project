package com.bradykoehler.cs246project;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

/**
 * This activity displays a list of images and allows them to be searched.
 * Once an image is selected, it is sent back to the GridActivity to be added to a Grid.
 */
public class ImagesActivity extends AppCompatActivity {

    @SuppressLint("StaticFieldLeak")
    public static RecyclerView recyclerView;

    /**
     * Initialize settings for the activity
     * @param savedInstanceState default
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Set the correct theme
        ThemeManager.setTheme(this);
        
        // Set status bar color
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));

        // Load default activity settings
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up recycler view
        recyclerView = findViewById(R.id.tiles);
        recyclerView.setHasFixedSize(true);

        // Set up linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Load list of images
        AcaApi.getImages(this);
    }

    /**
     * Load list of images into the view
     * @param images array of images
     */
    public void loadImages(Image[] images) {
        RecyclerView.Adapter mAdapter = new ImagesAdapter(images, this);
        recyclerView.setAdapter(mAdapter);
    }

    /**
     * Filter list of images using a search query
     * @param view search button
     */
    public void search(View view){

        // Get search input
        EditText editText = findViewById(R.id.search);
        String query = editText.getText().toString();

        // Retrieve images matching query
        AcaApi.getImages(this, query);
    }

    /**
     * Sends the selected image to the parent activity
     * @param image selected image data
     */
    public void returnImage(Image image) {

        // Create the intent
        Intent intent = new Intent();

        // Add image data to intent
        // TODO Add extras or a data URI to this intent as appropriate.
        intent.putExtra("image", image);

        // Set intent as activity result
        setResult(Activity.RESULT_OK, intent);

        // Complete activity
        finish();
    }
}
