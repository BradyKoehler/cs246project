package com.bradykoehler.cs246project;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * This class handles the download of images, provided a URL to download
 * and an ImageView object reference
 */
public class ImageDownloadTask extends AsyncTask<String, Void, Bitmap> {
    @SuppressLint("StaticFieldLeak")
    private ImageView bmImage;

    /**
     * Default constructor
     * @param bmImage reference to ImageView
     */
    ImageDownloadTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    /**
     * Attempts to download the image from the given url
     * @param urls
     * @return
     */
    protected Bitmap doInBackground(String... urls) {
        String urlDisplay = urls[0];
        Bitmap image = null;

        // Attempt to connect and retrieve image data
        try {
            InputStream in = new java.net.URL(urlDisplay).openStream();
            image = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Return image data
        return image;
    }

    /**
     * Sets the image data in the ImageView object after download
     * @param result
     */
    protected void onPostExecute(Bitmap result) {

        // Load image data
        bmImage.setImageBitmap(result);

        // Mark image as loaded
        bmImage.setTag("set");
    }
}
