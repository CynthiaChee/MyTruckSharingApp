package com.example.mytrucksharingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import androidx.annotation.Nullable;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(@Nullable Context context) {
        super(context, "user_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_USER_TABLE = "CREATE TABLE USERS (USERID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, PASSWORD TEXT, IMAGE BLOB, FULL_NAME TEXT, PHONE_NUMBER TEXT);";
        String CREATE_DELIVERY_TABLE = "CREATE TABLE DELIVERIES (DELIVERYID INTEGER PRIMARY KEY AUTOINCREMENT, SENDER TEXT, RECEIVER TEXT, GOOD_TYPE TEXT, VEHICLE_TYPE TEXT, TIME TEXT, LOCATION TEXT, DATE TEXT, WEIGHT INTEGER, LENGTH INTEGER, WIDTH INTEGER, HEIGHT INTEGER);";
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
        sqLiteDatabase.execSQL(CREATE_DELIVERY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String DROP_DELIVERY_TABLE = "DROP TABLE IF EXISTS DELIVERY;";
        sqLiteDatabase.execSQL(DROP_DELIVERY_TABLE);
        String DROP_USER_TABLE = "DROP TABLE IF EXISTS USERS;";
        sqLiteDatabase.execSQL(DROP_USER_TABLE);

        onCreate(sqLiteDatabase);
    }

    public long addUser(User user) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("USERNAME", user.getUserName());
        contentValues.put("PASSWORD", user.getPassWord());
        byte[] accountImageByte = getBitmapAsByteArray(user.getUserPhoto());
        contentValues.put("IMAGE", accountImageByte);
        contentValues.put("FULL_NAME", user.getFullName());
        long row = myDB.insert("USERS", null, contentValues);
        myDB.close();
        return row;
    }

    public long addDelivery(Delivery delivery) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("SENDER", delivery.getSender());
        contentValues.put("RECEIVER", delivery.getReceiver());
        contentValues.put("DATE", delivery.getDate().toString());
        contentValues.put("TIME", delivery.getTime());
        contentValues.put("LOCATION", delivery.getLocation());
        contentValues.put("GOOD_TYPE", delivery.getGoodType());
        contentValues.put("VEHICLE_TYPE", delivery.getVehicleType());
        contentValues.put("WEIGHT", delivery.getWeight());
        contentValues.put("LENGTH", delivery.getLength());
        contentValues.put("WIDTH", delivery.getWidth());
        contentValues.put("HEIGHT", delivery.getHeight());
        long newRow = myDB.insert("DELIVERIES", null, contentValues);
        myDB.close();
        return newRow;
    }

    public boolean getUser(String username, String password) {
        SQLiteDatabase myDB = this.getReadableDatabase();
        Cursor cursor = myDB.query("USERS", new String[]{"USERID"}, "USERNAME=? AND PASSWORD=?", new String[]{username, password}, null, null, null);
        int num_rows = cursor.getCount();
        cursor.close();
        myDB.close();
        return num_rows > 0;
    }

    public List<Delivery> getAllDeliveries() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM DELIVERIES";
        Cursor cursor = db.rawQuery(sql, null);

        List<Delivery> deliveryList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                String sender = cursor.getString(1);
                String receiver = cursor.getString(2);
                Long date = Long.parseLong(cursor.getString(7));
                String time = cursor.getString(5);
                String location = cursor.getString(6);
                String goodType = cursor.getString(3);
                String vehicleType = cursor.getString(4);
                int weight = cursor.getInt(8);
                int length = cursor.getInt(9);
                int width = cursor.getInt(10);
                int height = cursor.getInt(11);

                Delivery delivery = new Delivery(sender, receiver, date, time, location, goodType, vehicleType, weight, length, width, height);
                deliveryList.add(delivery);
                cursor.moveToNext();
            } while(!cursor.isAfterLast());
        }
        cursor.close();
        return deliveryList;
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }
}
