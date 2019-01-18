package com.example.omega.geobangla2;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CardActivity extends AppCompatActivity {
    Spinner card_namespinner;
    EditText card_customname;
    EditText card_cardnumber;
    EditText card_month;
    EditText card_year;
    EditText card_ccv;

    Button card_button;

    String selectedType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Create New Card");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        card_cardnumber = findViewById(R.id.card_cardnumber);
        card_customname = findViewById(R.id.card_customname);
        card_month = findViewById(R.id.card_month);
        card_year = findViewById(R.id.card_year);
        card_ccv = findViewById(R.id.card_ccv);
        card_button = findViewById(R.id.card_savebutton);

        card_namespinner = findViewById(R.id.card_namespinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.types, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        card_namespinner.setAdapter(spinnerAdapter);

        card_namespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedType = adapterView.getItemAtPosition(i).toString();
                //Toast.makeText(adapterView.getContext(), selectedType, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        card_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCardInformation();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void getCardInformation(){
        String customName = card_customname.getText().toString().trim();
        String cardNumber = card_cardnumber.getText().toString().trim();
        String cardMonth = card_month.getText().toString().trim();
        String cardYear = card_year.getText().toString().trim();
        String cardCCV = card_ccv.getText().toString().trim();

        if(customName.isEmpty()){
            card_customname.setError("Card Name required");
            card_customname.requestFocus();
            return;
        }
        if(cardNumber.isEmpty()){
            card_cardnumber.setError("Card Number required");
            card_cardnumber.requestFocus();
            return;
        }
        if(cardMonth.isEmpty()){
            card_month.setError("Expiry Month required");
            card_month.requestFocus();
            return;
        }
        if(cardYear.isEmpty()){
            card_year.setError("Expiry Year required");
            card_year.requestFocus();
            return;
        }
        if(cardCCV.isEmpty()){
            card_ccv.setError("Card CCV required");
            card_ccv.requestFocus();
            return;
        }

        String expiryDate = cardMonth + "/" + cardYear;
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String user_id = firebaseUser.getUid();

        CardClass cardClass = new CardClass(selectedType, customName, cardNumber, expiryDate, cardCCV, user_id);
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("cards/" + user_id);
        mRef.push().setValue(cardClass);

        Toast.makeText(CardActivity.this, "Card Added Succesfully!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
