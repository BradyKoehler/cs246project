package com.bradykoehler.cs246project;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.bradykoehler.cs246project.api.AcaApi;

public class ActivityTileSel extends AppCompatActivity {
    public static RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Image[] images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tile_sel);

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

//        AcaApi api = AcaApi.getInstance();
//        api.getImages(this);
//        mAdapter = new ImageAdapter(images);
//        recyclerView.setAdapter(mAdapter);
//        Intent intent = getIntent();
//        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
//            String query = intent.getStringExtra(SearchManager.QUERY);
//            doMySearch(query);
//        }
    }

    public void loadImages(Image[] newImagesList) {
        mAdapter = new ImageAdapter(newImagesList, this);
        recyclerView.setAdapter(mAdapter);
    }

    public void getImages(Image[] images){
        this.images = images;
    }

    private void doMySearch(String query){
        mAdapter = new ImageAdapter(images, this);
    }

    public void returnImage(Image img) {
        AcaApi.getInstance().img = img;
        Intent resultIntent = new Intent();
        // TODO Add extras or a data URI to this intent as appropriate.
        resultIntent.putExtra("image", img);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}
