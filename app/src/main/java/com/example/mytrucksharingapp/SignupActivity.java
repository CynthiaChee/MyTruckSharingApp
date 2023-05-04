package com.example.mytrucksharingapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;

public class SignupActivity extends AppCompatActivity {

    //Initializing variables
    Button addPhotoButton, createAccountButton;
    ImageButton addPhotoImageButton;
    EditText signupFullName, signupUserName, signupPassword, confirmPassword, signupPhoneNumber;
    Uri selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Find view by ID
        addPhotoButton = findViewById(R.id.addPhotoButton);
        addPhotoImageButton = findViewById(R.id.addPhotoField);
        signupFullName = findViewById(R.id.signupFullNameEdit);
        signupUserName = findViewById(R.id.signupUserNameEdit);
        signupPassword = findViewById(R.id.signupPasswordEdit);
        confirmPassword = findViewById(R.id.signupConfirmPasswordEdit);
        signupPhoneNumber = findViewById(R.id.signupPhoneEdit);
        selectedImage = Uri.parse("src/main/res/drawable/resource_default.jpg");
        createAccountButton = findViewById(R.id.signupCreateAccountButton);

        DatabaseHelper myDatabaseHelper = new DatabaseHelper(this);

        addPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(photoIntent, 3);
            }
        });

        addPhotoImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(photoIntent, 3);
            }
        });

        createAccountButton.setOnClickListener(view -> {
            String fullName, uName, pWord, confPWord, phoneNo;
            Bitmap uPhoto;
            fullName = signupFullName.getText().toString();
            uName = signupUserName.getText().toString();
            pWord = signupPassword.getText().toString();
            confPWord = confirmPassword.getText().toString();
            phoneNo = signupPhoneNumber.getText().toString();

            try {
                uPhoto = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            } catch (IOException exception) {
                exception.printStackTrace();
                uPhoto = BitmapFactory.decodeResource(this.getResources(), R.drawable.addphoto);
            }

            if(fullName.equals("") || uName.equals("") || pWord.equals("") || confPWord.equals("") || phoneNo.equals("")){
                Toast.makeText(this, "Not all fields are filled.", Toast.LENGTH_SHORT).show();
            } else if (!confPWord.equals(pWord)){
                Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
            } else{
                User newUser = new User(fullName, uName, uPhoto, pWord, phoneNo);
                long result = myDatabaseHelper.addUser(newUser);
                if (result > -1) {
                    Toast.makeText(this, "New user created!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignupActivity.this, MainActivity.class));
                } else {
                    Toast.makeText(this, "Error: " + result, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            selectedImage = data.getData();
            addPhotoImageButton.setImageURI(selectedImage);
        }
    }
}
