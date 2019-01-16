package com.example.omega.geobangla2;

import android.app.DownloadManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.support.v7.widget.helper.ItemTouchUIUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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

    String user_id;

    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;
    //FirebaseRecyclerOptions<BookingClass> options;
    //FirebaseRecyclerAdapter<BookingClass, BookingListRecycler> adapter;
    BookingListRecycler adapter;

    Users user = new Users();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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


        mRecyclerView = v.findViewById(R.id.profile_recyclerview);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        user_id = firebaseUser.getUid();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        String path = "bookings/" + user_id;
        System.out.println("THIS IS PATH:" + path);
        mRef = mFirebaseDatabase.getReference(path);

        setUpRecyclerView();

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadUserDetails();
        if(adapter != null){
            adapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(adapter != null){
            adapter.stopListening();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(adapter != null){
            adapter.startListening();
        }
    }

    private void setUpRecyclerView(){
        FirebaseRecyclerOptions<BookingClass> options = new FirebaseRecyclerOptions.Builder<BookingClass>()
                .setQuery(mRef, BookingClass.class)
                .build();
        adapter = new BookingListRecycler(options);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        adapter.startListening();
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(layoutManager);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                adapter.deleteItem(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(mRecyclerView);
    }
    private void loadUserDetails(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        user_id = firebaseUser.getUid();
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("users");
        Query query = mRef.orderByChild("uid").equalTo(user_id);

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
    }
}
