package com.bradykoehler.cs246project;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * This adapter provides the interface allowing an array of Images to be
 * displayed in the ImagesActivity view
 */
class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImageViewHolder> {

    private Image[] images;   // Array of images
    private Context mContext; // Adapter context

    // Provide a reference to the views for each data item
    static class ImageViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout relativeLayout;

        ImageViewHolder(RelativeLayout v) {
            super(v);
            relativeLayout = v;
        }
    }

    /**
     * Basic constructor using an array of images
     * @param images array of images to be displayed
     * @param context adapter context
     */
    ImagesAdapter(Image[] images, Context context) {
        this.images = images;
        mContext = context;
    }

    /**
     * Handles creation of ViewHolders
     * @param parent containing ViewGroup
     * @param viewType view type id
     * @return ViewHolder
     */
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Create layout
        RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_list_item_layout, parent, false);

        // Create view holder
        ImageViewHolder vh = new ImageViewHolder(v);

        // Set onClick listener
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Find position of selected item in list
                int itemPosition = ImagesActivity.recyclerView.getChildLayoutPosition(view);

                // Get image data
                Image image = images[itemPosition];

                // Return image data
                ((ImagesActivity) mContext).returnImage(image);
            }
        });

        return vh;
    }

    /**
     * Handle binding of data to a view element
     * @param imageViewHolder image view holder
     * @param i index of view item
     */
    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder imageViewHolder, int i) {

        // Set image name
        ((TextView) imageViewHolder.relativeLayout.findViewById(R.id.tileName)).setText(images[i].getName());

        // Start task to download image file
        new ImageDownloadTask((ImageView) imageViewHolder.relativeLayout.findViewById(R.id.tileImage))
                .execute(images[i].getData());
    }

    /**
     * Returns number of items in data set
     * @return length of images array
     */
    @Override
    public int getItemCount() {
        return images.length;
    }
}
