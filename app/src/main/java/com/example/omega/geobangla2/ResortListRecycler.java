package com.example.omega.geobangla2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ResortListRecycler extends RecyclerView.ViewHolder {

    View view;
    public ResortListRecycler(@NonNull View itemView) {
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
    public void setDetails(Context context, String name, String type, String stars, String image, String price, String pack){
        ImageView mImage = view.findViewById(R.id.resort_image);
        TextView mName = view.findViewById(R.id.resort_name);
        TextView mType = view.findViewById(R.id.hotel_tag);
        TextView mPrice = view.findViewById(R.id.resort_cost);
        TextView mPack = view.findViewById(R.id.resort_pack1);
        RatingBar mStars = view.findViewById(R.id.rating);

        String pricetostring = "Tk. " + price;
        Glide.with(context)
                .asBitmap()
                .load(image)
                .into(mImage);
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
    private ResortListRecycler.CliclListener mClickListener;

    public interface CliclListener{
        void onItemCLick(View view, int position);
        void onItemLongClick(View view, int position);
    }
    public void setOnClickListener(ResortListRecycler.CliclListener clickListener){
        mClickListener = clickListener;
    }
}
