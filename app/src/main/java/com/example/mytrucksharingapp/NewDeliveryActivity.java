package com.example.mytrucksharingapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class NewDeliveryActivity extends AppCompatActivity {

    //Initializing variables
    EditText receiverEdit, timeEdit, locationEdit;
    CalendarView myCalendar;
    Button nextButton;
    Long date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_delivery);

        //Find view by ID
        receiverEdit = findViewById(R.id.receiverNameEdit);
        timeEdit = findViewById(R.id.pickupTimeEdit);
        locationEdit = findViewById(R.id.pickupLocationEdit);
        myCalendar = findViewById(R.id.pickupCalendarView);
        nextButton = findViewById(R.id.deliveryNextButton);

        String sender = getIntent().getStringExtra("sender");
        date = myCalendar.getDate();

        //Set calendar
        myCalendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String currentYear = String.valueOf(year);
            String currentMonth = String.valueOf(month+1);
            String currentDay = String.valueOf(dayOfMonth);
            String dateString = currentDay + "/" + currentMonth + "/" + currentYear;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyy", Locale.ENGLISH);

            try {
                date = simpleDateFormat.parse(dateString).getTime();
            } catch (ParseException exception) {
                exception.printStackTrace();
            }
        });

        //If next button is clicked
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (receiverEdit.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Receiver name required.", Toast.LENGTH_SHORT).show();
                } else if (timeEdit.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Pickup time required.", Toast.LENGTH_SHORT).show();
                } else if (locationEdit.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Pickup location required.", Toast.LENGTH_SHORT).show();
                } else {
                    String receiver = receiverEdit.getText().toString();
                    String time = timeEdit.getText().toString();
                    String location = locationEdit.getText().toString();

                    Intent newDeliveryIntent = new Intent(NewDeliveryActivity.this, NewDeliveryDetailsActivity.class);
                    newDeliveryIntent.putExtra("receiver", receiver);
                    newDeliveryIntent.putExtra("time", time);
                    newDeliveryIntent.putExtra("location", location);
                    newDeliveryIntent.putExtra("date", date);
                    newDeliveryIntent.putExtra("sender", sender);
                    startActivityForResult(newDeliveryIntent, 1);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK && requestCode==1) {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        }
    }

}
