package com.example.omega.geobangla2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class FavoriteListRecycler extends RecyclerView.ViewHolder {

    View view;
    public FavoriteListRecycler(@NonNull View itemView) {
        super(itemView);
        view = itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemCLick(v, getAdapterPosition());
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mClickListener.onItemLongClick(v, getAdapterPosition());
                return true;
            }
        });
    }
    public void setDetails(String name, String type, String stars, String price, String pack){
        TextView mName = view.findViewById(R.id.favlist_resortname);
        TextView mType = view.findViewById(R.id.favlist_hoteltag);
        TextView mPrice = view.findViewById(R.id.favlist_cost);
        TextView mPack = view.findViewById(R.id.favlist_pack1);
        RatingBar mStars = view.findViewById(R.id.favlist_rating);

        String pricetostring = "Tk. " + price;

        mName.setText(name);
        if(type.equals("Hotel")){
            mType.setBackgroundResource(R.drawable.text_rounded);
        }
        else{
            mType.setBackgroundResource(R.drawable.text_rounded_green);
        }
        mType.setText(type);

        mPrice.setText(pricetostring);
        mPack.setText(pack);
        mStars.setRating(Integer.parseInt(stars));
    }
    private FavoriteListRecycler.CliclListener mClickListener;

    public interface CliclListener{
        void onItemCLick(View view, int position);
        void onItemLongClick(View view, int position);
    }
    public void setOnClickListener(FavoriteListRecycler.CliclListener clickListener){
        mClickListener = clickListener;
    }
}