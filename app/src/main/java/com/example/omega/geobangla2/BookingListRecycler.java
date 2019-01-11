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

class BookingListRecycler extends RecyclerView.ViewHolder {

    View view;
    public BookingListRecycler(@NonNull View itemView) {
        super(itemView);

        view = itemView;

    }

    public void setDetails(String Name, String Tag, String Stars, String BedType, String RoomCount, String CheckInDate, String CheckOutDate, String TotalPrice, String Uid) {
        TextView bookinglist_name;
        TextView bookinglist_tag;
        RatingBar bookinglist_rating;
        TextView bookinglist_roomtype;
        TextView bookinglist_guestcount;
        TextView bookinglist_checkin;
        TextView bookinglist_checkout;
        TextView bookinglist_price;
        bookinglist_checkin = view.findViewById(R.id.bookinglist_checkin);
        bookinglist_name = view.findViewById(R.id.bookinglist_name);
        bookinglist_tag = view.findViewById(R.id.bookinglist_tag);
        bookinglist_roomtype = view.findViewById(R.id.bookinglist_roomtype);
        bookinglist_guestcount = view.findViewById(R.id.bookinglist_guestcount);
        bookinglist_checkout = view.findViewById(R.id.bookinglist_checkout);
        bookinglist_price = view.findViewById(R.id.bookinglist_price);

        bookinglist_rating = view.findViewById(R.id.bookinglist_rating);

        bookinglist_checkin.setText(CheckInDate);
        bookinglist_name.setText(Name);
        bookinglist_tag.setText(Tag);
        bookinglist_rating.setNumStars(Integer.parseInt(Stars));
        bookinglist_rating.setRating(Integer.parseInt(Stars));
        bookinglist_roomtype.setText(BedType);
        bookinglist_guestcount.setText(RoomCount);
        bookinglist_checkout.setText(CheckOutDate);
        bookinglist_price.setText(TotalPrice);
    }
}

