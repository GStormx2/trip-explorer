package com.example.omega.geobangla2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


public class ResortDescriptionFragment extends Fragment {

    private String imageUrl;
    private String imageName;
    private int myInt;
    private StoredResources str = new StoredResources();
    private ImageView myImage;
    private TextView myText;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imageUrl = str.getStoredImageUrls(StoredResources.getPos());
        imageName = str.getStoredNames(StoredResources.getPos());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_resort_desc, container,false);
        myImage = v.findViewById(R.id.resort_desc_img);
        myText = v.findViewById(R.id.resort_desc_name);

        Glide.with(getContext())
                .asBitmap()
                .load(imageUrl)
                .into(myImage);
        myText.setText(imageName);

        return v;
    }
}
