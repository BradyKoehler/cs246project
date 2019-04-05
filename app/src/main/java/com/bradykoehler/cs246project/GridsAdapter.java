package com.bradykoehler.cs246project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GridsAdapter extends RecyclerView.Adapter<GridsAdapter.MyViewHolder> {
    private Grid[] mDataSet;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout relativeLayout;
        MyViewHolder(RelativeLayout v) {
            super(v);
            relativeLayout = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of data set)
    GridsAdapter(Grid[] myDataSet) {
        mDataSet = myDataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public GridsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.grid_list_item_layout, parent, false);

        MyViewHolder vh = new MyViewHolder(v);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            // Get selected grid
            int gridListPosition = MainActivity.recyclerView.getChildAdapterPosition(view);
            Grid grid = mDataSet[gridListPosition];

            // Store grid data in an intent
            Intent intent = new Intent(view.getContext(), GridActivity.class);
            Bundle extras = new Bundle();
            extras.putInt("gridId", grid.getId());
            extras.putString("gridName", grid.getName());
            intent.putExtras(extras);

            view.getContext().startActivity(intent);
            }
        });

        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ((TextView) holder.relativeLayout.findViewById(R.id.firstLine)).setText(mDataSet[position].getName());
        ((TextView) holder.relativeLayout.findViewById(R.id.secondLine)).setText("Tiles: " + mDataSet[position].getCount() + "/9");
    }

    @Override
    public int getItemCount() {
        return mDataSet.length;
    }
}
