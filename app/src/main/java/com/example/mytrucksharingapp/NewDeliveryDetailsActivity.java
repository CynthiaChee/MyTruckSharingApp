package com.example.mytrucksharingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import java.util.Calendar;

public class NewDeliveryDetailsActivity extends AppCompatActivity {

    //Initializing variables
    EditText otherGoodsEdit, otherVehicleEdit, weightEdit, widthEdit, lengthEdit, heightEdit;
    Button createOrderButton;
    RadioGroup goodTypeRadioGroup, vehicleTypeRadioGroup;
    RadioButton goodTypeRadioButton, vehicleTypeRadioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_delivery_details);

        //Find view by ID
        otherGoodsEdit = findViewById(R.id.otherGoodsEdit);
        otherVehicleEdit = findViewById(R.id.otherVehicleEdit);
        weightEdit = findViewById(R.id.weightEditText);
        widthEdit = findViewById(R.id.widthEditText);
        lengthEdit = findViewById(R.id.lengthEditText);
        heightEdit = findViewById(R.id.heightEditText);
        goodTypeRadioGroup = findViewById(R.id.goodTypeRadioGroup);
        vehicleTypeRadioGroup = findViewById(R.id.vehicleTypeRadioGroup);
        createOrderButton = findViewById(R.id.createOrderButton);

        DatabaseHelper myDatabaseHelper = new DatabaseHelper(this);

        //Receiving delivery details
        String receiver, time, location, sender;
        Long date;
        sender = getIntent().getStringExtra("sender");
        receiver = getIntent().getStringExtra("receiver");
        time = getIntent().getStringExtra("time");
        location = getIntent().getStringExtra("location");
        date = getIntent().getLongExtra("date", Calendar.getInstance().getTime().getTime());

        //If Create Order button is clicked
        createOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int goodTypeSelectedID, vehicleTypeSelectedID;
                goodTypeSelectedID = goodTypeRadioGroup.getCheckedRadioButtonId();
                vehicleTypeSelectedID = vehicleTypeRadioGroup.getCheckedRadioButtonId();

                //If good type not selected
                if (goodTypeSelectedID == -1) {
                    Toast.makeText(getApplicationContext(), "Good type not selected.", Toast.LENGTH_SHORT).show();

                    //If vehicle type not selected
                } else if (vehicleTypeSelectedID == -1) {
                    Toast.makeText(getApplicationContext(), "Vehicle type not selected.", Toast.LENGTH_SHORT).show();

                    //If any of the dimension fields are empty
                } else if (weightEdit.getText().toString().equals("") || widthEdit.getText().toString().equals("") ||
                    lengthEdit.getText().toString().equals("") || heightEdit.getText().toString().equals("") ) {
                    Toast.makeText(getApplicationContext(), "Please enter all required values.", Toast.LENGTH_SHORT).show();

                } else {
                    goodTypeRadioButton = findViewById(goodTypeSelectedID);
                    vehicleTypeRadioButton = findViewById(vehicleTypeSelectedID);

                    //Parse dimensions to string
                    int weight, width, length, height;
                    weight = Integer.parseInt(weightEdit.getText().toString());
                    width = Integer.parseInt(widthEdit.getText().toString());
                    length = Integer.parseInt(lengthEdit.getText().toString());
                    height = Integer.parseInt(heightEdit.getText().toString());

                    //If other good type is entered, else selected from radio group
                    String goodType;
                    if (goodTypeSelectedID == R.id.otherGoodsButton) {
                        goodType = otherGoodsEdit.getText().toString();
                    } else {
                        goodType = goodTypeRadioButton.getText().toString();
                    }

                    //If other vehicle type is entered, else selected from radio group
                    String vehicleType;
                    if (vehicleTypeSelectedID == R.id.otherVehicleButton) {
                        vehicleType = otherVehicleEdit.getText().toString();
                    } else {
                        vehicleType = vehicleTypeRadioButton.getText().toString();
                    }

                    //Create new delivery instance and initialize it with the variables
                    Delivery newDelivery = new Delivery(sender, receiver, date, time, location, goodType, vehicleType, weight, length, width, height);
                    long insertedDelivery = myDatabaseHelper.addDelivery(newDelivery);
                    if (insertedDelivery > -1) {
                        Toast.makeText(getApplicationContext(), "Order successfully created!", Toast.LENGTH_SHORT).show();
                        Intent resultIntent = new Intent();
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Order not created. Error: " + insertedDelivery, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
