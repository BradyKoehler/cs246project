package com.bradykoehler.cs246project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * This adapter provides the interface allowing an array of Grids to be
 * displayed in the GridsActivity view
 */
public class GridsAdapter extends RecyclerView.Adapter<GridsAdapter.GridViewHolder> {

    private Grid[] grids; // Array of grids

    // Provides a reference to the views for each data item
    static class GridViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout relativeLayout;

        GridViewHolder(RelativeLayout v) {
            super(v);

            relativeLayout = v;
        }
    }

    /**
     * Basic constructor using array of grids
     * @param grids array of grids to be displayed
     */
    GridsAdapter(Grid[] grids) {
        this.grids = grids;
    }

    /**
     * Handles creation of ViewHolders
     * @param parent containing ViewGroup
     * @param viewType view type id
     * @return ViewHolder
     */
    @Override
    public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Create layout
        RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.grid_list_item_layout, parent, false);

        // Create view holder
        GridViewHolder vh = new GridViewHolder(v);

        // Set onClick listener
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get selected grid
                int gridListPosition = GridsActivity.recyclerView.getChildAdapterPosition(view);
                Grid grid = grids[gridListPosition];

                // Store grid data in an intent
                Intent intent = new Intent(view.getContext(), GridActivity.class);
                Bundle extras = new Bundle();
                extras.putInt("gridId", grid.getId());
                extras.putString("gridName", grid.getName());
                intent.putExtras(extras);

                // Start activity
                view.getContext().startActivity(intent);
            }
        });

        return vh;
    }

    /**
     * Handle binding of data to a view element
     * @param holder grid view holder
     * @param position position of view item
     */
    @Override
    public void onBindViewHolder(GridViewHolder holder, int position) {

        // Set grid name
        ((TextView) holder.relativeLayout.findViewById(R.id.firstLine)).setText(grids[position].getName());

        // Set grid tiles count
        ((TextView) holder.relativeLayout.findViewById(R.id.secondLine)).setText("Tiles: " + grids[position].getCount() + "/9");
    }

    /**
     * Returns number of items in data set
     * @return length of grids array
     */
    @Override
    public int getItemCount() {
        return grids.length;
    }
}
