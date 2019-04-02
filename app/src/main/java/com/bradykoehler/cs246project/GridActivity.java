package com.bradykoehler.cs246project;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

public class GridActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
    }

    public void selectTile(View view) {
        ImageButton imgBtn = (ImageButton) view;
        if (view.getTag() == "set") {
            String url = view.getTag(view.getId()).toString();
            Uri myUri = Uri.parse(url);
            MediaPlayer mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(url);
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.prepare(); //don't use prepareAsync for mp3 playback
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            int imgBtnId = 0;

            switch (view.getId()) {
                case (R.id.imageButton0): {
                    imgBtnId = 0;
                    break;
                }
                case (R.id.imageButton1): {
                    imgBtnId = 1;
                    break;
                }
                case (R.id.imageButton2): {
                    imgBtnId = 2;
                    break;
                }
                case (R.id.imageButton3): {
                    imgBtnId = 3;
                    break;
                }
                case (R.id.imageButton4): {
                    imgBtnId = 4;
                    break;
                }
                case (R.id.imageButton5): {
                    imgBtnId = 5;
                    break;
                }
                case (R.id.imageButton6): {
                    imgBtnId = 6;
                    break;
                }
                case (R.id.imageButton7): {
                    imgBtnId = 7;
                    break;
                }
                case (R.id.imageButton8): {
                    imgBtnId = 8;
                    break;
                }
            }
            ((Activity) view.getContext()).startActivityForResult(new Intent(GridActivity.this, ActivityTileSel.class), imgBtnId);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("GridActivity", "requestCode: " + requestCode);
        if (resultCode == Activity.RESULT_OK) {
            ImageButton imgBtn;
            int imgBtnId = 0;

            switch(requestCode) {
                case (0) : {
                    imgBtnId = R.id.imageButton0;
                    break;
                }
                case (1) : {
                    imgBtnId = R.id.imageButton1;
                    break;
                }
                case (2) : {
                    imgBtnId = R.id.imageButton2;
                    break;
                }
                case (3) : {
                    imgBtnId = R.id.imageButton3;
                    break;
                }
                case (4) : {
                    imgBtnId = R.id.imageButton4;
                    break;
                }
                case (5) : {
                    imgBtnId = R.id.imageButton5;
                    break;
                }
                case (6) : {
                    imgBtnId = R.id.imageButton6;
                    break;
                }
                case (7) : {
                    imgBtnId = R.id.imageButton7;
                    break;
                }
                case (8) : {
                    imgBtnId = R.id.imageButton8;
                    break;
                }
            }

            imgBtn = findViewById(imgBtnId);
            Image img = (Image) data.getExtras().getSerializable("image");
            new DownloadImageTask((ImageButton) findViewById(imgBtnId)).execute(img.getData());
//            imgBtn.setTag(13, img.getSound());
            imgBtn.setTag(imgBtnId, img.getSound());
        }
//        switch(requestCode) {
//            case (R.id.imageButton0) : {
//                if (resultCode == Activity.RESULT_OK) {
//                    Image img = (Image) data.getExtras().getSerializable("image");
//                    Log.e("GridActivity", img.getName() + " " + img.getData());
//                    ImageButton imgBtn = findViewById(R.id.imageButton0);
//                    new DownloadImageTask(imgBtn).execute(img.getData());
//                }
//                break;
//            }
//        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
            bmImage.setTag("set");
        }
    }
}
