package com.bradykoehler.cs246project;

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

public class ImagesActivity extends AppCompatActivity {
    public static RecyclerView recyclerView;

    public void setmAdapter(RecyclerView.Adapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Image[] images;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Set status bar color
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.tiles);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        AcaApi.getInstance().getImages(this);
    }

    public void loadImages(Image[] newImagesList) {
        mAdapter = new ImagesAdapter(newImagesList, this);
        recyclerView.setAdapter(mAdapter);
    }

    public void doSearch(View view){
        EditText editText = findViewById(R.id.search);
        String query = editText.getText().toString();

        AcaApi.getInstance().getImages(this, query);
    }

    public void getImages(Image[] images){
        this.images = images;
    }

    public void returnImage(Image img) {
        Intent resultIntent = new Intent();
        // TODO Add extras or a data URI to this intent as appropriate.
        resultIntent.putExtra("image", img);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}
