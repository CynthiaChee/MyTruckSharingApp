package com.example.mytrucksharingapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageButton;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements DeliveryAdapter.OnDeliveryClickListener {

    //Initializing variables
    RecyclerView myRecyclerView;
    RecyclerView.LayoutManager myLayoutManager;
    DeliveryAdapter deliveryAdapter;
    ImageButton addNewDelivery;
    String currentUser;
    List<Delivery> deliveryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        DatabaseHelper myDatabaseHelper = new DatabaseHelper(this);
        currentUser = getIntent().getStringExtra("user");

        //Showing list of current deliveries
        myRecyclerView = findViewById(R.id.deliveriesRecyclerView);
        deliveryList = myDatabaseHelper.getAllDeliveries();
        showDeliveries(deliveryList);

        //If add new delivery button clicked
        addNewDelivery = findViewById(R.id.addDeliveryButton);
        addNewDelivery.setOnClickListener(view -> {
            Intent newDeliveryIntent = new Intent(HomeActivity.this, NewDeliveryActivity.class);
            newDeliveryIntent.putExtra("sender", currentUser);
            startActivityForResult(newDeliveryIntent, 1);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK && requestCode==1) {
            DatabaseHelper myDatabaseHelper = new DatabaseHelper(this);
            deliveryList = myDatabaseHelper.getAllDeliveries();
            showDeliveries(deliveryList);
        }
    }

    //Adds a new order
    @Override
    public void onDeliveryClick(int position) {
        Intent orderIntent = new Intent(HomeActivity.this, OrderDetails.class);
        orderIntent.putExtra("sender", deliveryList.get(position).getSender());
        orderIntent.putExtra("receiver", deliveryList.get(position).getReceiver());
        orderIntent.putExtra("date", deliveryList.get(position).getDate());
        orderIntent.putExtra("time", deliveryList.get(position).getTime());
        orderIntent.putExtra("location",deliveryList.get(position).getLocation());
        orderIntent.putExtra("goodType", deliveryList.get(position).getGoodType());
        orderIntent.putExtra("weight", deliveryList.get(position).getWeight());
        orderIntent.putExtra("width", deliveryList.get(position).getWidth());
        orderIntent.putExtra("length", deliveryList.get(position).getLength());
        orderIntent.putExtra("height", deliveryList.get(position).getHeight());
        startActivity(orderIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemSelected = item.getItemId();

        //If Home menu option selected, show all deliveries
        if (itemSelected == R.id.homeMenu) {
            DatabaseHelper myDatabaseHelper = new DatabaseHelper(this);
            myRecyclerView = findViewById(R.id.deliveriesRecyclerView);
            deliveryList = myDatabaseHelper.getAllDeliveries();
            showDeliveries(deliveryList);

        } else if (itemSelected == R.id.myOrdersMenu) {
            //else if My Orders menu option selected, show user's deliveries
            currentUser = getIntent().getStringExtra("user");
            DatabaseHelper myDatabaseHelper = new DatabaseHelper(this);
            deliveryList = myDatabaseHelper.getAllDeliveries();
            List<Delivery> myDeliveryList = new ArrayList<>();
            for (Delivery delivery:deliveryList) {
                if (delivery.getSender().equals(currentUser)) {
                    myDeliveryList.add(delivery);
                }
            }
            showDeliveries(myDeliveryList);
        }
        return super.onOptionsItemSelected(item);
    }

    public void showDeliveries(List<Delivery> list){
        deliveryAdapter = new DeliveryAdapter( HomeActivity.this, list, this);
        myRecyclerView.setAdapter(deliveryAdapter);
        myLayoutManager = new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(myLayoutManager);
    }
}
