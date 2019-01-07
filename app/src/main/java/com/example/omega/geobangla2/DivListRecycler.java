package com.example.omega.geobangla2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DivListRecycler extends RecyclerView.ViewHolder{

    View view;
    public DivListRecycler(@NonNull View itemView) {
        super(itemView);
        view = itemView;

        //item click
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCLickListener.onItemCLick(v, getAdapterPosition());
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mCLickListener.onItemLongClock(v, getAdapterPosition());
                return true;
            }
        });
    }
    public void setDetails(Context context, String title, String description, String image){
        TextView mName = view.findViewById(R.id.division_name);
        ImageView mImage = view.findViewById(R.id.divison_img);
        TextView mDescription = view.findViewById(R.id.division_desc);

        mName.setText(title);
        mDescription.setText(description);
        Glide.with(context)
                .asBitmap()
                .load(image)
                .into(mImage);
    }
    private DivListRecycler.ClickListener mCLickListener;

    //interface to send callbacks
    public interface ClickListener{
        void onItemCLick(View view, int position);
        void onItemLongClock(View view, int position);
    }

    public void setOnClickListener(DivListRecycler.ClickListener clickListener){
        mCLickListener = clickListener;
    }
}