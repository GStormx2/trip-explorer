package com.example.omega.geobangla2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class FavoriteFragment extends Fragment {

    TextView favlist_favdesc;
    RecyclerView favlist_recyclerview;
    String user_id;

    FirebaseRecyclerOptions<ResortClass> options;
    FirebaseRecyclerAdapter<ResortClass, FavoriteListRecycler> adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container,false);

        favlist_favdesc = view.findViewById(R.id.favlist_favdesc);
        favlist_recyclerview = view.findViewById(R.id.favlist_recyclerview);

        String favlistTitle = " Your #Favourite in #" + StoredResources.getClickedDivision();
        favlist_favdesc.setText(favlistTitle);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        user_id = firebaseUser.getUid();

        String path = "favorite/" + user_id + "/" + StoredResources.getClickedDivision();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(path);

        options = new FirebaseRecyclerOptions.Builder<ResortClass>()
                .setQuery(databaseReference, ResortClass.class)
                .build();
        adapter = new FirebaseRecyclerAdapter<ResortClass, FavoriteListRecycler>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FavoriteListRecycler holder, int position, @NonNull ResortClass model) {
                holder.setDetails(model.getName(), model.getType(), model.getStars(), model.getPack1price(), model.getPack1());
                holder.setOnClickListener(new FavoriteListRecycler.CliclListener() {
                    @Override
                    public void onItemCLick(View view, int position) {
                        DataSnapshot dataSnapshot = getSnapshots().getSnapshot(position);
                        ResortClass resortClass = dataSnapshot.getValue(ResortClass.class);
                        int temp_int = Integer.parseInt(resortClass.getId());
                        StoredResources.setResortPosition(temp_int - 1);

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
            public FavoriteListRecycler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.favorite_list_layout, viewGroup, false);
                return new FavoriteListRecycler(view);
            }
        };
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        adapter.startListening();
        favlist_recyclerview.setAdapter(adapter);
        favlist_recyclerview.setLayoutManager(layoutManager);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                adapter.getSnapshots().getSnapshot(viewHolder.getAdapterPosition()).getRef().removeValue();

            }
        }).attachToRecyclerView(favlist_recyclerview);

        return view;
    }
}
