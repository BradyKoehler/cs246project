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
    private Grid[] mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout relativeLayout;
        public MyViewHolder(RelativeLayout v) {
            super(v);
            relativeLayout = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public GridsAdapter(Grid[] myDataset) {
        mDataset = myDataset;
    }

    private final View.OnClickListener mOnClickListener = new MyOnClickListener();

    // Create new views (invoked by the layout manager)
    @Override
    public GridsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.grid_list_item_layout, parent, false);

//        v.setOnClickListener(mOnClickListener);

        MyViewHolder vh = new MyViewHolder(v);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int gridListPosition = NavMainActivity.recyclerView.getChildAdapterPosition(view);
                Grid grid = mDataset[gridListPosition];
                Intent intent = new Intent(view.getContext(), GridActivity.class);
                Bundle extras = new Bundle();
                extras.putInt("gridId", grid.getId());
                intent.putExtras(extras);
                view.getContext().startActivity(intent);
            }
        });

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ((TextView) holder.relativeLayout.findViewById(R.id.firstLine)).setText(mDataset[position].getName());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
