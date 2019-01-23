package com.example.omega.geobangla2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PaymentActivity extends AppCompatActivity {

    TextView payment_placename;
    TextView payment_totalprice;
    Spinner payment_namespinner;
    EditText payment_cardnumber;
    EditText payment_expdate;
    EditText payment_cvv;
    Button payment_bookbutton;

    String user_id;

    RecyclerView cardRecyclerView;
    CardListRecycler cardAdapter;
    ArrayAdapter<CharSequence> spinnerAdapter;

    CardClass cardClass = new CardClass();
    BookingClass bookingClass = new BookingClass();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Intent intent = getIntent();
        bookingClass = intent.getParcelableExtra("booking information");

        payment_placename = findViewById(R.id.payment_placename);
        payment_totalprice = findViewById(R.id.payment_totalprice);
        payment_namespinner = findViewById(R.id.payment_namespinner);
        payment_cardnumber = findViewById(R.id.payment_cardnumber);
        payment_expdate = findViewById(R.id.payment_expdate);
        payment_cvv = findViewById(R.id.payment_cvv);
        payment_bookbutton = findViewById(R.id.payment_bookbutton);
        cardRecyclerView = findViewById(R.id.payment_cardrecyclerview);

        payment_placename.setText(bookingClass.getName());
        payment_totalprice.setText(bookingClass.getTotalPrice());

        spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.types, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        payment_namespinner.setAdapter(spinnerAdapter);
        payment_namespinner.setSelection(spinnerAdapter.getPosition("Visa"));

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        user_id = firebaseUser.getUid();

        setUpCardRecyclerView();

        payment_bookbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cardNumber = payment_cardnumber.toString().trim();
                String expDate = payment_expdate.toString().trim();
                String cvv = payment_cvv.toString().trim();

                if(cardNumber.isEmpty()){
                    payment_cardnumber.setError("Card Number Required");
                    payment_cardnumber.requestFocus();
                    return;
                }
                if(expDate.isEmpty()){
                    payment_expdate.setError("Expiry Date Required");
                    payment_expdate.requestFocus();
                    return;
                }
                if(cvv.isEmpty()){
                    payment_cvv.setError("CVV required");
                    payment_cvv.requestFocus();
                    return;
                }
                DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("bookings/" + user_id);
                mRef.push().setValue(bookingClass);
                Toast.makeText(PaymentActivity.this, "Booking Successful!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }

    private void setUpCardRecyclerView(){
        String tempPath = "cards/" + user_id;
        DatabaseReference cardRef = FirebaseDatabase.getInstance().getReference(tempPath);
        FirebaseRecyclerOptions<CardClass> options = new FirebaseRecyclerOptions.Builder<CardClass>()
                .setQuery(cardRef, CardClass.class)
                .build();
        cardAdapter = new CardListRecycler(options);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PaymentActivity.this, LinearLayoutManager.HORIZONTAL, false);
        cardAdapter.startListening();
        cardRecyclerView.setAdapter(cardAdapter);
        cardRecyclerView.setLayoutManager(layoutManager);

        cardAdapter.setOnItemClickListener(new CardListRecycler.OnItemClickListener() {
            @Override
            public void onItemClick(DataSnapshot dataSnapshot, int position) {
                cardClass = dataSnapshot.getValue(CardClass.class);
                payment_cardnumber.setText(cardClass.getCardNumber());
                payment_expdate.setText(cardClass.getExpiryDate());
                payment_cvv.setText(cardClass.getCcv());
                payment_namespinner.setSelection(spinnerAdapter.getPosition(cardClass.getType()));
            }

            @Override
            public void onItemLongClick(DataSnapshot dataSnapshot, int position) {

            }
        });

    }
}
