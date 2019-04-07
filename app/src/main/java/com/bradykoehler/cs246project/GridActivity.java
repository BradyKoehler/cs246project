package com.bradykoehler.cs246project;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.io.IOException;

/**
 * This activity displays the currently selected Grid
 */
public class GridActivity extends AppCompatActivity {

    private int gridId; // ID of the current Grid

    /**
     * Initialize settings for the activity
     * @param savedInstanceState default
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Set the correct theme
        ThemeManager.setTheme(this);

        // Call override function
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        // Initialize toolbar
        Toolbar myToolbar = findViewById(R.id.gridToolbar);
        setSupportActionBar(myToolbar);

        // Set back button
        myToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_chevron_left_black_24dp));
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Get intent
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        assert extras != null;

        // Get selected Grid ID
        gridId = extras.getInt("gridId");

        // Get selected Grid name and set as title
        String gridName = extras.getString("gridName");
        setTitle(gridName);

        // Load grid data
        AcaApi.getGrid(this, gridId);

        // Set LongPress listeners for each button
        setButtonListeners();
    }

    /**
     * Set LongPress listeners for each button
     */
    private void setButtonListeners() {
        for (int i = 0; i < 9; i++) {
            findViewById(getIdFromBtnNumber(i)).setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    // Edit the selected tile
                    editTile(v);

                    return true;
                }
            });
        }
    }

    /**
     * Plays an audio file
     * @param url audio file location
     */
    private void playAudio(String url) {

        // Initialize media player
        MediaPlayer mediaPlayer = new MediaPlayer();

        // Attempt to play audio
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
            mediaPlayer.start();
            // TODO determine how to properly release the MediaPlayer object
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Map button id to position
     * @param id android view id
     * @return position
     */
    private int getBtnNumberFromId(int id) {
        switch (id) {
            case (R.id.imageButton0): {
                return 0;
            }
            case (R.id.imageButton1): {
                return 1;
            }
            case (R.id.imageButton2): {
                return 2;
            }
            case (R.id.imageButton3): {
                return 3;
            }
            case (R.id.imageButton4): {
                return 4;
            }
            case (R.id.imageButton5): {
                return 5;
            }
            case (R.id.imageButton6): {
                return 6;
            }
            case (R.id.imageButton7): {
                return 7;
            }
            case (R.id.imageButton8): {
                return 8;
            }
        }

        return 0;
    }

    /**
     * Map button position to id
     * @param position tile position
     * @return id android button id
     */
    private int getIdFromBtnNumber(int position) {

        // Ordered array of android button ids
        int btnList[] = {
                R.id.imageButton0,
                R.id.imageButton1,
                R.id.imageButton2,
                R.id.imageButton3,
                R.id.imageButton4,
                R.id.imageButton5,
                R.id.imageButton6,
                R.id.imageButton7,
                R.id.imageButton8
        };

        return  btnList[position];
    }

    /**
     * Handles a tile being clicked
     * @param view selected button
     */
    public void selectTile(View view) {

        // Check if button is set
        if (view.getTag() == "set") {

            // Get audio url
            String url = view.getTag(view.getId()).toString();

            // Play audio file
            playAudio(url);
        }

        // If the button isn't set, edit it
        else {
            editTile(view);
        }
    }

    /**
     * Handles a tile to be edited
     * @param view selected button
     */
    public void editTile(View view) {

        // Get button id
        int imgBtnId = getBtnNumberFromId(view.getId());

        // Create intent
        Intent intent = new Intent(GridActivity.this, ImagesActivity.class);

        // Begin intent
        ((Activity) view.getContext()).startActivityForResult(intent, imgBtnId);
    }

    /**
     * Handle data returned from ImagesActivity
     * @param requestCode tile position
     * @param resultCode status of request
     * @param data image data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Call override function
        super.onActivityResult(requestCode, resultCode, data);

        // Ensure successful request
        if (resultCode == Activity.RESULT_OK) {

            // Find button element
            int imgBtnId = getIdFromBtnNumber(requestCode);
            ImageButton imgBtn = findViewById(imgBtnId);

            // Parse image data
            Image img = (Image) data.getExtras().getSerializable("image");

            assert img != null;
            Log.d("GridActivity", "Selected Image ID: " + img.getId());

            // Create the tile
            AcaApi.createTile(this, img.getId(), requestCode, gridId);

            // Set image data
            imgBtn.setTag(imgBtnId, img.getSound());

            // Begin downloading image
            new ImageDownloadTask((ImageButton) findViewById(imgBtnId)).execute(img.getData());
        }
    }

    /**
     * Loads tile data into the view
     * @param tile Tile object
     */
    public void setTile(Tile tile) {
        // Find button
        ImageButton btn = findViewById(getIdFromBtnNumber(tile.getPosition()));

        // Set image data
        btn.setTag(getIdFromBtnNumber(tile.getPosition()), tile.getSound());

        // Begin downloading image
        new ImageDownloadTask(btn).execute(tile.getData());
    }
}
