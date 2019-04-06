package com.bradykoehler.cs246project;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.io.IOException;

public class GridActivity extends AppCompatActivity {

    private int gridId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeManager.setTheme(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        // initialize toolbar
        Toolbar myToolbar = findViewById(R.id.gridToolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_chevron_left_black_24dp));
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        gridId = extras.getInt("gridId");
        String gridName = extras.getString("gridName");
        setTitle(gridName);

        AcaApi.getGrid(this, gridId);

        for (int i = 0; i < 9; i++) {
            Log.d("GridActivity:LongPress", "Setting long click listener for button " + i);
            findViewById(getIdFromBtnNumber(i)).setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    editTile(v);
                    return true;
                }
            });
        }
    }

    private void playAudio(String url) {
        MediaPlayer mediaPlayer = new MediaPlayer();

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

    private int getIdFromBtnNumber(int id) {
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

        return  btnList[id];
    }

    public void selectTile(View view) {
        if (view.getTag() == "set") {
            String url = view.getTag(view.getId()).toString();
            playAudio(url);
        } else {
            editTile(view);
        }
    }

    public void editTile(View view) {
        int imgBtnId = getBtnNumberFromId(view.getId());
        Intent intent = new Intent(GridActivity.this, ImagesActivity.class);

        ((Activity) view.getContext()).startActivityForResult(intent, imgBtnId);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            int imgBtnId = getIdFromBtnNumber(requestCode);
            ImageButton imgBtn = findViewById(imgBtnId);
            Image img = (Image) data.getExtras().getSerializable("image");

            Log.d("GridActivity", "Selected Image ID: " + img.getId());

            AcaApi.createTile(this, img.getId(), requestCode, gridId);

            new ImageDownloadTask((ImageButton) findViewById(imgBtnId)).execute(img.getData());
            imgBtn.setTag(imgBtnId, img.getSound());
        }
    }

    public void setTile(Tile tile) {
        ImageButton btn = findViewById(getIdFromBtnNumber(tile.getPosition()));
        new ImageDownloadTask(btn).execute(tile.getData());
        btn.setTag(getIdFromBtnNumber(tile.getPosition()), tile.getSound());
    }
}
