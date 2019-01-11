package com.example.omega.geobangla2;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TestActivity extends AppCompatActivity {

    String user_id;

    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;
    FirebaseRecyclerOptions<BookingClass> options;
    FirebaseRecyclerAdapter<BookingClass, BookingListRecycler> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testview);

        mRecyclerView = findViewById(R.id.test_view);

        //mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        user_id = firebaseUser.getUid();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        String path = "bookings/" + user_id;
        System.out.println("THIS IS PATH:" + path);
        mRef = mFirebaseDatabase.getReference(path);

        options = new FirebaseRecyclerOptions.Builder<BookingClass>()
                .setQuery(mRef, BookingClass.class)
                .build();
        adapter = new FirebaseRecyclerAdapter<BookingClass, BookingListRecycler>(options) {
            @Override
            protected void onBindViewHolder(@NonNull BookingListRecycler holder, int position, @NonNull BookingClass model) {
                holder.setDetails(model.getName(), model.getTag(), model.getStars(), model.getBedType(), model.getRoomCount(), model.getCheckInDate(), model.getCheckOutDate(), model.getTotalPrice(), model.getUid());
            }

            @NonNull
            @Override
            public BookingListRecycler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.booking_item_list, viewGroup, false);
                return new BookingListRecycler(view);
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
}
