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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ResortDescriptionFragment extends Fragment {


    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;
    private String Address, Amenities, Description, Email, Image, Map, Name, Pack1, Pack1price, Pack2, Pack2price, Phone,
            Priceperday, Stars, Type;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String path = "resort/" + StoredResources.getClickedDivision() + "/01";
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference(path);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Address = dataSnapshot.child("Address").getValue().toString();
                Amenities = dataSnapshot.child("Amenities").getValue().toString();
                Description = dataSnapshot.child("Description").getValue().toString();
                Email = dataSnapshot.child("Email").getValue().toString();
                Image = dataSnapshot.child("Image").getValue().toString();
                Map = dataSnapshot.child("Map").getValue().toString();
                Name = dataSnapshot.child("Name").getValue().toString();
                Pack1 = dataSnapshot.child("Pack1").getValue().toString();
                Pack1price = dataSnapshot.child("Pack1price").getValue().toString();
                Pack2 = dataSnapshot.child("Pack2").getValue().toString();
                Pack2price = dataSnapshot.child("Pac2price").getValue().toString();
                Phone = dataSnapshot.child("Phone").getValue().toString();
                Priceperday = dataSnapshot.child("Priceperday").getValue().toString();
                Stars = dataSnapshot.child("Stars").getValue().toString();
                Type = dataSnapshot.child("Type").getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_resort_desc, container,false);


        return v;
    }
}
