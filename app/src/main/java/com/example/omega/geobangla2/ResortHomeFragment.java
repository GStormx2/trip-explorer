package com.example.omega.geobangla2;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ResortHomeFragment extends Fragment {
    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;
    FirebaseRecyclerOptions<ResortClass> options;
    FirebaseRecyclerAdapter<ResortClass, ResortListRecycler> adapter;
    String path = "resort/" + StoredResources.getClickedDivision();

    TextView hotellist_welcome, hotellist_desc;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container,false);

        hotellist_welcome = v.findViewById(R.id.hotellist_welcome);
        hotellist_desc = v.findViewById(R.id.hotellist_desc);

        String welcome_text = StoredResources.getClickedDivision() + " Hotels and Resorts";
        String welcome_desc = "Showing results for Hotels and Resorts in " + StoredResources.getClickedDivision() + ". Select the place you wish to stay";

        hotellist_welcome.setText(welcome_text);
        hotellist_desc.setText(welcome_desc);

        mRecyclerView = v.findViewById(R.id.recycler_view);
        

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference(path);

        options = new FirebaseRecyclerOptions.Builder<ResortClass>()
                .setQuery(mRef, ResortClass.class)
                .build();
        adapter = new FirebaseRecyclerAdapter<ResortClass, ResortListRecycler>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ResortListRecycler holder, int position, @NonNull ResortClass model) {
                holder.setDetails(getContext(), model.getName(), model.getType(), model.getStars(), model.getImage(), model.getPack1price(), model.getPack1());
                holder.setOnClickListener(new ResortListRecycler.CliclListener() {
                    @Override
                    public void onItemCLick(View view, int position) {
                        StoredResources.setResortPosition(position);
                        AppCompatActivity activity = (AppCompatActivity) getContext();
                        FragmentManager fragmentManager = activity.getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        ResortDescriptionFragment fragment = new ResortDescriptionFragment();
                        fragmentTransaction.replace(R.id.fragment_container, fragment);
                        fragmentTransaction.commit();

                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                });
            }

            @NonNull
            @Override
            public ResortListRecycler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_item_list, viewGroup, false);
                return new ResortListRecycler(view);
            }
        };
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        adapter.startListening();
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(layoutManager);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
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
}
