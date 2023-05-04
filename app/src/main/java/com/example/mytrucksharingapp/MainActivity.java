package com.example.mytrucksharingapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Initializing variables
    EditText myUserName, myPassword;
    Button loginButton, signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find view by ID
        myUserName = findViewById(R.id.userNameEdit);
        myPassword = findViewById(R.id.passwordEdit);
        loginButton = findViewById(R.id.loginButton);
        signupButton = findViewById(R.id.signupButton);

        DatabaseHelper myDatabaseHelper = new DatabaseHelper(this);

        //If Login button clicked
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uName = myUserName.getText().toString();
                String pWord = myPassword.getText().toString();
                boolean getUserData = myDatabaseHelper.getUser(uName, pWord);
                if(getUserData == true){
                    Toast.makeText(getApplicationContext(), "Login successful.", Toast.LENGTH_SHORT).show();
                    Intent loginIntent = new Intent(MainActivity.this, HomeActivity.class);
                    loginIntent.putExtra("user", uName);
                    startActivity(loginIntent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Login failed. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //If Signup button clicked
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signupIntent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(signupIntent);
            }
        });
    }
}
