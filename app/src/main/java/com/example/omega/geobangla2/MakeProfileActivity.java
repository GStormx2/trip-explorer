package com.example.omega.geobangla2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class MakeProfileActivity extends AppCompatActivity {
    private static final String TAG2 = "MakeProfileActivity";
    ImageView makeprofile_propic;
    ImageView makeprofile_coverpic;
    EditText makeprofile_name;
    EditText makeprofile_phone;
    EditText makeprofile_email;
    ProgressBar progress_bar_makeprofile;

    Button makeprofile_button;

    Uri uri_cover;
    Uri uri_profile;

    String user_id;
    String profile_image_url;
    String cover_image_url;

    Users user = new Users();

    static final int CHOOSE_COVER = 101;
    static final int CHOOSE_PROFILE = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_profile);

        FirebaseApp.initializeApp(this);
        makeprofile_coverpic = findViewById(R.id.makeprofile_coverpic);
        makeprofile_propic = findViewById(R.id.makeprofile_propic);
        makeprofile_name = findViewById(R.id.makeprofile_name);
        makeprofile_phone = findViewById(R.id.makeprofile_phone);
        makeprofile_button = findViewById(R.id.makeprofile_button);
        makeprofile_email = findViewById(R.id.makeprofile_email);
        progress_bar_makeprofile = findViewById(R.id.progress_bar_makeprofile);

        loadUserDetails();

        makeprofile_coverpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageChooser();
            }
        });
        makeprofile_propic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageChooserProfile();
            }
        });
        makeprofile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG2, "onClick: Clicked on the button");
                uploadCoverImage();
                Log.d(TAG2, "onClick: Uploading complete");

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2 && resultCode == RESULT_OK && data != null && data.getData() != null){
            uri_cover = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri_cover);
                makeprofile_coverpic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            uri_profile = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri_profile);
                makeprofile_propic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadCoverImage() {
        String filename = "cover/" + System.currentTimeMillis() + ".jpg";
        if(uri_cover != null){
            progress_bar_makeprofile.setVisibility(View.VISIBLE);
            final StorageReference coverImageRef = FirebaseStorage.getInstance().getReference(filename);
            UploadTask uploadTask = (UploadTask) coverImageRef.putFile(uri_cover)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                            while(!urlTask.isSuccessful());
                            Uri downloadUrl = urlTask.getResult();
                            Log.d(TAG2, "onSuccess: firebase download Url" + downloadUrl.toString());
                            cover_image_url = downloadUrl.toString();
                            uploadProfileImage();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progress_bar_makeprofile.setVisibility(View.GONE);
                            Toast.makeText(MakeProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        //saveUserInformation();
    }
    private void uploadProfileImage(){
        String filename1 = "profile/" + System.currentTimeMillis() + ".jpg";
        if(uri_profile != null){
            final StorageReference profileImageRef = FirebaseStorage.getInstance().getReference(filename1);
            UploadTask uploadTask = (UploadTask) profileImageRef.putFile(uri_profile)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                            while(!urlTask.isSuccessful());
                            Uri downloadUrl = urlTask.getResult();
                            Log.d(TAG2, "onSuccess: firebase download Url" + downloadUrl.toString());
                            profile_image_url = downloadUrl.toString();
                            saveUserInformation();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progress_bar_makeprofile.setVisibility(View.GONE);
                            Toast.makeText(MakeProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }


    }

    private void saveUserInformation(){
        String displayName, displayEmail, displayPhone;
        displayName = makeprofile_name.getText().toString();
        displayEmail = makeprofile_email.getText().toString();
        displayPhone = makeprofile_phone.getText().toString();

        if(displayEmail.isEmpty()){
            makeprofile_email.setError("Email must be same as signup!");
            makeprofile_email.requestFocus();
            return;
        }
        if(displayName.isEmpty()){
            makeprofile_name.setError("Name is required");
            makeprofile_name.requestFocus();
            return;
        }
        if(displayPhone.isEmpty()){
            makeprofile_phone.setError("Phone is required");
            makeprofile_phone.requestFocus();
            return;
        }
        System.out.println(cover_image_url);
        System.out.println(profile_image_url);

        Users newUser = new Users(cover_image_url, displayEmail, displayName, displayPhone, profile_image_url, user_id);
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("users/");
        mRef.child(user_id).setValue(newUser);
        progress_bar_makeprofile.setVisibility(View.GONE);
        Intent intent = new Intent(MakeProfileActivity.this, DivisionListActivity.class);
        startActivity(intent);

        finish();

    }

    private void loadUserDetails(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        user_id = firebaseUser.getUid();
        makeprofile_email.setText(firebaseUser.getEmail());

    }
    private void showImageChooserProfile(){
        Intent intent1 = new Intent();
        intent1.setType("image/*");
        intent1.setAction(intent1.ACTION_GET_CONTENT);
        startActivityForResult(intent1.createChooser(intent1, "Select Profile Picture"), 1);
    }

    private void showImageChooser(){
        Intent intent2 = new Intent();
        intent2.setType("image/*");
        intent2.setAction(intent2.ACTION_GET_CONTENT);
        startActivityForResult(intent2.createChooser(intent2, "Select Profile Cover"), 2);
    }
}
