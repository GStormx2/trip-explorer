package com.example.omega.geobangla2;

import android.support.v7.app.ActionBar;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BookingActivity extends AppCompatActivity {

    TextView booking_placename;
    EditText booking_name;
    EditText booking_email;
    EditText booking_phone;
    RadioButton booking_pack1;
    RadioButton booking_pack2;
    TextView booking_price_bed;
    Button booking_subtractionbutton;
    Button booking_additionbutton;
    TextView booking_guestcount;
    TextView booking_guests_price;
    TextView booking_checkindate;
    TextView booking_checkoutdate;
    TextView booking_day_price;
    TextView booking_total_price;
    Button booking_confirmbutton;
    Button booking_calculatebutton;
    int bed_price, room_price, total_price, day_price;
    long day_count;
    int flag = 1;
    String checkinDate, checkoutDate;
    String user_id;
    String selected_radioButton;
    DatePickerDialog.OnDateSetListener onDateSetListener1;
    DatePickerDialog.OnDateSetListener onDateSetListener2;

    Users user = new Users();

    FirebaseUser firebaseUser;
    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        booking_placename = findViewById(R.id.booking_placename);
        booking_price_bed = findViewById(R.id.booking_price_bed);
        booking_guests_price = findViewById(R.id.booking_guests_price);
        booking_checkindate = findViewById(R.id.booking_checkindate);
        booking_checkoutdate = findViewById(R.id.booking_checkoutdate);
        booking_day_price = findViewById(R.id.booking_day_price);
        booking_total_price = findViewById(R.id.booking_total_price);
        booking_guestcount = findViewById(R.id.booking_guestcount);

        booking_name = findViewById(R.id.booking_name);
        booking_email = findViewById(R.id.booking_email);
        booking_phone = findViewById(R.id.booking_phone);

        booking_pack1 = findViewById(R.id.booking_pack1);
        booking_pack2 = findViewById(R.id.booking_pack2);

        booking_subtractionbutton = findViewById(R.id.booking_subtractingbutton);
        booking_additionbutton = findViewById(R.id.booking_additionbutton);
        booking_confirmbutton = findViewById(R.id.booking_confirmbutton);
        booking_calculatebutton = findViewById(R.id.booking_calculatebutton);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Booking Page");

        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        booking_placename.setText(StoredResources.getResortName());



        getUser();

        booking_subtractionbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int guest = Integer.parseInt(booking_guestcount.getText().toString());

                if(guest == 0){
                    booking_guestcount.setText(String.valueOf(guest));
                    int temp_price = (int)Math.ceil(guest/2.0);
                    room_price = temp_price * bed_price;
                    String temp = "Tk. " + String.valueOf(room_price);
                    booking_guests_price.setText(temp);
                    flag = 1;
                }
                else{
                    guest = guest - 1;
                    booking_guestcount.setText(String.valueOf(guest));
                    int temp_price = (int)Math.ceil(guest/2.0);
                    room_price = temp_price * bed_price;
                    String temp = "Tk. " + String.valueOf(room_price);
                    booking_guests_price.setText(temp);
                    flag = 1;
                }
            }
        });
        booking_additionbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int guest = Integer.parseInt(booking_guestcount.getText().toString());
                guest = guest + 1;
                booking_guestcount.setText(String.valueOf(guest));
                int temp_price = (int)Math.ceil(guest/2.0);
                room_price = temp_price * bed_price;
                String temp = "Tk. " + String.valueOf(room_price);
                booking_guests_price.setText(temp);
                flag = 1;
            }
        });

        booking_checkindate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        BookingActivity.this,
                        android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth,
                        onDateSetListener1,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.show();
            }
        });

        onDateSetListener1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                checkinDate = date;
                booking_checkindate.setText(date);

                if(checkoutDate != null){
                    String strCheckin = checkinDate;
                    String strCheckout = checkoutDate;

                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    Date d1 = null, d2 = null;
                    try{
                        d1 = formatter.parse(strCheckin);
                        d2 = formatter.parse(strCheckout);
                    } catch(ParseException e){
                        e.printStackTrace();
                    }

                    Calendar cal1 = Calendar.getInstance();
                    Calendar cal2 = Calendar.getInstance();
                    cal1.setTime(d1);
                    cal2.setTime(d2);

                    long diff = cal2.getTimeInMillis() - cal1.getTimeInMillis();
                    day_count = diff / (24*60*60*1000);

                    if(day_count <= 0){
                        Toast.makeText(BookingActivity.this, "Please Select Proper Dates!", Toast.LENGTH_LONG).show();
                        String temp = "Tk. 0";
                        booking_day_price.setText(temp);
                        flag = 1;
                    }
                    else{
                        day_price = (int) day_count * StoredResources.getPricePerDay();
                        String temp_string = "Tk. " + day_price;
                        booking_day_price.setText(temp_string);
                        flag = 1;
                    }
                }
            }
        };

        booking_checkoutdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        BookingActivity.this,
                        android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth,
                        onDateSetListener2,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.show();
            }
        });

        onDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                checkoutDate = date;
                booking_checkoutdate.setText(date);

                if(checkinDate != null){
                    String strCheckin = checkinDate;
                    String strCheckout = checkoutDate;

                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    Date d1 = null, d2 = null;
                    try{
                        d1 = formatter.parse(strCheckin);
                        d2 = formatter.parse(strCheckout);
                    } catch(ParseException e){
                        e.printStackTrace();
                    }

                    Calendar cal1 = Calendar.getInstance();
                    Calendar cal2 = Calendar.getInstance();
                    cal1.setTime(d1);
                    cal2.setTime(d2);

                    long diff = cal2.getTimeInMillis() - cal1.getTimeInMillis();
                    day_count = diff / (24*60*60*1000);

                    if(day_count <= 0){
                        Toast.makeText(BookingActivity.this, "Please Select Proper Dates!", Toast.LENGTH_LONG).show();
                        String temp = "Tk. 0";
                        booking_day_price.setText(temp);
                        flag = 1;
                    }
                    else{
                        day_price = (int) day_count * StoredResources.getPricePerDay();
                        String temp_string = "Tk. " + day_price;
                        booking_day_price.setText(temp_string);
                        flag = 1;
                    }
                }
            }
        };

        booking_calculatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateTotalPrice();
                flag = 0;
            }
        });

        booking_confirmbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == 1){
                    Toast.makeText(BookingActivity.this, "Press Calculate Button First!", Toast.LENGTH_LONG).show();
                }
                else{
                    BookingClass bookingClass = new BookingClass(
                            StoredResources.getResortName(),
                            StoredResources.getResortTag(),
                            StoredResources.getResortStars(),
                            selected_radioButton,
                            booking_guestcount.getText().toString(),
                            checkinDate,
                            checkoutDate,
                            String.valueOf(total_price),
                            user_id
                    );
                    mRef = FirebaseDatabase.getInstance().getReference("bookings");
                    mRef.push().setValue(bookingClass);


                    finish();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void calculateTotalPrice(){
        if(bed_price == 0 || room_price == 0 || day_price == 0 ){
            Toast.makeText(BookingActivity.this, "Please Check Fields again!", Toast.LENGTH_LONG).show();
        }
        else{
            total_price = bed_price + room_price + day_price;
            total_price =  total_price + (int)Math.ceil(0.15 * total_price);
            String temp = "Tk. " + String.valueOf(total_price);
            booking_total_price.setText(temp);
        }
    }

    public void getUser(){
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        user_id = firebaseUser.getUid();
        mRef = FirebaseDatabase.getInstance().getReference("users");
        Query query = mRef.orderByChild("Uid").equalTo(user_id);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapShot: dataSnapshot.getChildren()){
                        user = snapShot.getValue(Users.class);
                    }
                }

                booking_name.setText(user.getName());
                booking_email.setText(user.getEmail());
                booking_phone.setText(user.getPhone());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void onRadioButtonCLick(View view){
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()){
            case R.id.booking_pack1:
                if(checked){
                    selected_radioButton = booking_pack1.getText().toString();
                    bed_price = StoredResources.getPackOne();
                    String temp = "Tk. " + String.valueOf(bed_price);
                    booking_price_bed.setText(temp);
                    flag = 1;
                }
                break;
            case R.id.booking_pack2:
                if(checked){
                    selected_radioButton = booking_pack2.getText().toString();
                    bed_price = StoredResources.getPackTwo();
                    String temp = "Tk. " + String.valueOf(bed_price);
                    booking_price_bed.setText(temp);
                    flag = 1;
                }
        }
    }


}
