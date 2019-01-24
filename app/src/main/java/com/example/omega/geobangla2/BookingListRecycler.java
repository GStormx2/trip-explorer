package com.example.omega.geobangla2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

class BookingListRecycler extends FirebaseRecyclerAdapter<BookingClass, BookingListRecycler.BookingHolder> {


    public BookingListRecycler(@NonNull FirebaseRecyclerOptions<BookingClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull BookingHolder holder, int position, @NonNull BookingClass model) {
        holder.bookinglist_checkin.setText(model.getCheckInDate());
        holder.bookinglist_name.setText(model.getName());

        holder.bookinglist_tag.setText(model.getTag());
        if(model.getTag().equals("Hotel")){
            holder.bookinglist_tag.setBackgroundResource(R.drawable.text_rounded);
        }
        else{
            holder.bookinglist_tag.setBackgroundResource(R.drawable.text_rounded_green);
        }

        holder.bookinglist_tag.setText(model.getTag());
        holder.bookinglist_rating.setNumStars(Integer.parseInt(model.getStars()));
        holder.bookinglist_rating.setRating(Integer.parseInt(model.getStars()));
        holder.bookinglist_roomtype.setText(model.getBedType());
        holder.bookinglist_guestcount.setText(model.getRoomCount());
        holder.bookinglist_checkout.setText(model.getCheckOutDate());
        holder.bookinglist_price.setText(model.getTotalPrice());
    }

    @NonNull
    @Override
    public BookingHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.booking_item_list, viewGroup, false);
        return new BookingHolder(v);
    }
    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getRef().removeValue();

    }

    class BookingHolder extends RecyclerView.ViewHolder{
        TextView bookinglist_name;
        TextView bookinglist_tag;
        RatingBar bookinglist_rating;
        TextView bookinglist_roomtype;
        TextView bookinglist_guestcount;
        TextView bookinglist_checkin;
        TextView bookinglist_checkout;
        TextView bookinglist_price;

        public BookingHolder(@NonNull View itemView) {
            super(itemView);
            View view = itemView;
            bookinglist_checkin = view.findViewById(R.id.bookinglist_checkin);
            bookinglist_name = view.findViewById(R.id.bookinglist_name);
            bookinglist_tag = view.findViewById(R.id.bookinglist_tag);
            bookinglist_roomtype = view.findViewById(R.id.bookinglist_roomtype);
            bookinglist_guestcount = view.findViewById(R.id.bookinglist_guestcount);
            bookinglist_checkout = view.findViewById(R.id.bookinglist_checkout);
            bookinglist_price = view.findViewById(R.id.bookinglist_price);

            bookinglist_rating = view.findViewById(R.id.bookinglist_rating);
        }
    }

}

