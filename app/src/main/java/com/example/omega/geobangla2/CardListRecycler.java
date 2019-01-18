package com.example.omega.geobangla2;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;

public class CardListRecycler extends FirebaseRecyclerAdapter<CardClass, CardListRecycler.CardHolder> {
    private OnItemClickListener listener;
    //private OnItemLongClickListener mlistener;

    public CardListRecycler(@NonNull FirebaseRecyclerOptions<CardClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull CardHolder holder, int position, @NonNull CardClass model) {
        switch (model.getType()){
            case "Visa":
                holder.card_image.setImageResource(R.drawable.visa);
                break;
            case "Master Card":
                holder.card_image.setImageResource(R.drawable.mastercard);
                break;
            case "PayPal":
                holder.card_image.setImageResource(R.drawable.paypal);
                break;
            case "American Express":
                holder.card_image.setImageResource(R.drawable.amex);
                break;
        }
        holder.card_name.setText(model.getName());
    }

    @NonNull
    @Override
    public CardHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_list_layout, viewGroup, false);
        return new CardHolder(v);
    }
    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getRef().removeValue();

    }

    public class CardHolder extends RecyclerView.ViewHolder{

        ImageView card_image;
        TextView card_name;

        public CardHolder(@NonNull View itemView) {
            super(itemView);
            card_image = itemView.findViewById(R.id.card_image);
            card_name = itemView.findViewById(R.id.card_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && listener != null){
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && listener != null){
                        listener.onItemLongClick(getSnapshots().getSnapshot(position), position);
                    }
                    return false;
                }
            });
        }
    }
    public interface OnItemClickListener{
        void onItemClick(DataSnapshot dataSnapshot, int position);
        void onItemLongClick(DataSnapshot dataSnapshot, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

}
