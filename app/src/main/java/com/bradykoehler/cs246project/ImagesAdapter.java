package com.bradykoehler.cs246project;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.MyViewHolder> {
    private Image[] mDataSet;
    private Context mContext;

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
    public ImagesAdapter(Image[] myDataSet, Context context) {
        mDataSet = myDataSet;
        mContext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ImagesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_list_item_layout, parent, false);

        MyViewHolder vh = new MyViewHolder(v);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemPosition = ImagesActivity.recyclerView.getChildLayoutPosition(view);
                Image img = mDataSet[itemPosition];
                ((ImagesActivity) mContext).returnImage(img);
            }
        });

        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        ((TextView) myViewHolder.relativeLayout.findViewById(R.id.tileName)).setText(mDataSet[i].getName());
        new ImageDownloadTask((ImageView) myViewHolder.relativeLayout.findViewById(R.id.tileImage))
                .execute(mDataSet[i].getData());
    }

    // Return the size of your data set (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.length;
    }
}
