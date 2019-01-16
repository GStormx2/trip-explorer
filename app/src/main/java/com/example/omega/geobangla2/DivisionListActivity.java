package com.example.omega.geobangla2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DivisionListActivity extends AppCompatActivity {
    private static final String TAG = "DivisionListActivity";

    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;
    FirebaseRecyclerOptions<DivisionClass> options;
    FirebaseRecyclerAdapter<DivisionClass, DivListRecycler> adapter;
    ArrayList<ResortClass> resortClasses = new ArrayList<>();

    TextView divlist_welcome, divlist_desc;
    String user_id;
    Users user = new Users();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_div_list_test);
        //Action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Divisions");
        //

        divlist_welcome = findViewById(R.id.divlist_welcome);
        divlist_desc = findViewById(R.id.divlist_desc);
        loadUserDetails();

        mRecyclerView = findViewById(R.id.divlist_recylerview);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference("divisions");

        options = new FirebaseRecyclerOptions.Builder<DivisionClass>()
                .setQuery(mRef, DivisionClass.class)
                .build();
        adapter = new FirebaseRecyclerAdapter<DivisionClass, DivListRecycler>(options) {
            @Override
            protected void onBindViewHolder(@NonNull DivListRecycler holder, int position, @NonNull DivisionClass model) {
                holder.setDetails(getApplicationContext(), model.getName(), model.getDescription(), model.getImage());
                holder.setOnClickListener(new DivListRecycler.ClickListener() {
                    @Override
                    public void onItemCLick(View view, int position) {
                        ImageView mDivImage = view.findViewById(R.id.divison_img);
                        TextView mDivName = view.findViewById(R.id.division_name);
                        TextView mDivDescription = view.findViewById(R.id.division_desc);

                        String divName = mDivName.getText().toString();
                        StoredResources.setClickedDivision(divName);
                        Intent intent = new Intent(view.getContext(), LandingPage.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onItemLongClock(View view, int position) {

                    }
                });
            }

            @NonNull
            @Override
            public DivListRecycler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.divlist_layout, viewGroup, false);
                return new DivListRecycler(view);
            }
        };
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        adapter.startListening();
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(layoutManager);

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
                String temp = "Welcome, " + user.getName();
                divlist_welcome.setText(temp);
                divlist_desc.setText("Choose a division from the list down below to show division wise Hotels and Resort results");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(adapter != null){
            adapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(adapter != null){
            adapter.stopListening();
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(adapter != null){
            adapter.startListening();
        }
    }
}
