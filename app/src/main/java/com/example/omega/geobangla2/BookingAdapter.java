package com.example.omega.geobangla2;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class BookingAdapter extends FirebaseRecyclerAdapter<BookingClass, BookingAdapter.BookingHolder> {


    public BookingAdapter(@NonNull FirebaseRecyclerOptions<BookingClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull BookingHolder holder, int position, @NonNull BookingClass model) {
        holder.bookinglist_checkin.setText(model.getCheckInDate());
        holder.bookinglist_checkout.setText(model.getCheckOutDate());
        holder.bookinglist_guestcount.setText(model.getRoomCount());
        holder.bookinglist_name.setText(model.getName());
        holder.bookinglist_price.setText(model.getTotalPrice());
        holder.bookinglist_rating.setNumStars(Integer.parseInt(model.getStars()));
        holder.bookinglist_rating.setRating(Integer.parseInt(model.getStars()));
        holder.bookinglist_tag.setText(model.getTag());
    }

    @NonNull
    @Override
    public BookingHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.booking_item_list, viewGroup, false);
        return new BookingHolder(v);
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

            bookinglist_checkin = itemView.findViewById(R.id.bookinglist_checkin);
            bookinglist_name = itemView.findViewById(R.id.bookinglist_name);
            bookinglist_tag = itemView.findViewById(R.id.bookinglist_tag);
            bookinglist_roomtype = itemView.findViewById(R.id.bookinglist_roomtype);
            bookinglist_guestcount = itemView.findViewById(R.id.bookinglist_guestcount);
            bookinglist_checkout = itemView.findViewById(R.id.bookinglist_checkout);
            bookinglist_price = itemView.findViewById(R.id.bookinglist_price);

            bookinglist_rating = itemView.findViewById(R.id.bookinglist_rating);

        }
    }
}
