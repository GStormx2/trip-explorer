package com.example.omega.geobangla2;

import android.app.DownloadManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class ProfileFragment extends Fragment {

    ImageView profile_cover;
    ImageView profile_picture;
    TextView profile_name;
    TextView profile_email;
    TextView profile_phone;

    Users user = new Users();

    public void onCreate(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container,false);

        profile_cover = v.findViewById(R.id.profile_cover);
        profile_picture = v.findViewById(R.id.profile_picture);
        profile_name = v.findViewById(R.id.profile_name);
        profile_email = v.findViewById(R.id.profile_email);
        profile_phone = v.findViewById(R.id.profile_phone);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String user_id = firebaseUser.getUid();
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("users");
        Query query = mRef.orderByChild("Uid").equalTo(user_id);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapShot: dataSnapshot.getChildren()){
                        user = snapShot.getValue(Users.class);
                    }
                }
                Glide.with(getContext())
                        .asBitmap()
                        .load(user.getCoverPic())
                        .into(profile_cover);
                Glide.with(getContext())
                        .asBitmap()
                        .load(user.getProfilePic())
                        .into(profile_picture);
                profile_name.setText(user.getName());
                profile_email.setText(user.getEmail());
                profile_phone.setText(user.getPhone());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Toast.makeText(getActivity(), " USER ID: " + user_id, Toast.LENGTH_LONG).show();

        return v;
    }
}
