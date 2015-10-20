package com.nick.baac.baacrestaurant;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by BAAC on 10/19/2015.
 */
public class foodTABLE {

    // Explicit
    private MyOpenHelper obMyOpenHelper;
    private SQLiteDatabase writSqLiteDatabase, reaSqLiteDatabase;

    public static final String FOOD_TABLE = "foodTABLE";
    public static final String COLUMN_ID_FOOD = "_id";
    public static final String COLUMN_FOOD = "Food";
    public static final String COLUMN_SOURCE = "Source";
    public static final String COLUMN_PRICE = "Price";

    public foodTABLE(Context context) {

        obMyOpenHelper = new MyOpenHelper(context);
        writSqLiteDatabase = obMyOpenHelper.getWritableDatabase();
        reaSqLiteDatabase = obMyOpenHelper.getReadableDatabase();

    }// Constructor

    public long addNewFood(String strFood, String strSource, String strPrice) {
        ContentValues obContentValues = new ContentValues();
        obContentValues.put(COLUMN_FOOD, strFood);
        obContentValues.put(COLUMN_SOURCE, strSource);
        obContentValues.put(COLUMN_PRICE, strPrice);

        return writSqLiteDatabase.insert(FOOD_TABLE, null, obContentValues);
    }
} // Main Class
