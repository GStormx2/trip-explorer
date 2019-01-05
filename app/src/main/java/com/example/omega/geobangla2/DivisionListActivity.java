package com.example.omega.geobangla2;

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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DivisionListActivity extends AppCompatActivity {
    private static final String TAG = "DivisionListActivity";

    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;
    FirebaseRecyclerOptions<DivisionClass> options;
    FirebaseRecyclerAdapter<DivisionClass, DivListRecycler> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_div_list_test);
        //Action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Divisions");
        //
        mRecyclerView = findViewById(R.id.divlist_recylerview);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference("divisions");

        options = new FirebaseRecyclerOptions.Builder<DivisionClass>()
                .setQuery(mRef, DivisionClass.class)
                .build();
        adapter = new FirebaseRecyclerAdapter<DivisionClass, DivListRecycler>(options) {
            @Override
            protected void onBindViewHolder(@NonNull DivListRecycler holder, int position, @NonNull DivisionClass model) {
                holder.setDetails(getApplicationContext(), model.getName(), model.getDescription(), model.getImage());
            }

            @NonNull
            @Override
            public DivListRecycler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.divlist_layout, viewGroup, false);
                return new DivListRecycler(view);
            }
        };
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        adapter.startListening();
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(linearLayoutManager);

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
            adapter.startListening();
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
