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
}