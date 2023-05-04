package com.example.mytrucksharingapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class OrderDetails extends AppCompatActivity {

    //Initializing variables
    TextView senderText, pickupTimeText, receiverText, locationText, weightText, goodTypeText, widthText, heightText, lengthText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        //Find view by ID
        senderText = findViewById(R.id.senderTextView);
        pickupTimeText = findViewById(R.id.orderPickupTimeText);
        receiverText = findViewById(R.id.orderReceiverText);
        locationText = findViewById(R.id.orderPickupLocation);
        weightText = findViewById(R.id.goodWeightTextView);
        goodTypeText = findViewById(R.id.goodTypeTextView);
        widthText = findViewById(R.id.goodWidthTextView);
        heightText = findViewById(R.id.goodHeightTextView);
        lengthText = findViewById(R.id.goodLengthTextView);

        //Getting delivery values
        String sender = "Sender: " + getIntent().getStringExtra("sender");
        String receiver = "Receiver: " + getIntent().getStringExtra("receiver");
        String location = "Pickup location: " + getIntent().getStringExtra("location");

        //Pickup date and time
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        String pickupDateTime = "Pickup time: " + getIntent().getStringExtra("time") + ", "
            + simpleDateFormat.format(getIntent().getLongExtra("date", 0)).toString();;

        //Getting goods values
        String goodWeight = "Weight: \n" + getIntent().getIntExtra("weight", 0);
        String goodType = "Good type: \n" + getIntent().getStringExtra("goodType");
        String goodWidth = "Width: \n" + getIntent().getIntExtra("width", 0);
        String goodHeight = "Height: \n" + getIntent().getIntExtra("height", 0);
        String goodLength = "Length: \n" + getIntent().getIntExtra("length", 0);

        //Setting values to text views
        senderText.setText(sender);
        pickupTimeText.setText(pickupDateTime);
        receiverText.setText(receiver);
        locationText.setText(location);
        weightText.setText(goodWeight);
        goodTypeText.setText(goodType);
        widthText.setText(goodWidth);
        heightText.setText(goodHeight);
        lengthText.setText(goodLength);

    }
}
