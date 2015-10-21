package com.nick.baac.baacrestaurant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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

    public String[] readAllData(int inType) {

        String[] strResult = null;
        Cursor objCursor = reaSqLiteDatabase.query(FOOD_TABLE,
                new String[]{COLUMN_ID_FOOD, COLUMN_FOOD, COLUMN_SOURCE, COLUMN_PRICE},
                null, null, null, null, null);
        if (objCursor != null) {

            objCursor.moveToFirst();
            strResult = new String[objCursor.getCount()];

            for (int i = 0; i < objCursor.getCount(); i++) {
                switch (inType) {
                    case 0:
                        strResult[i] = objCursor.getString(objCursor.getColumnIndex(COLUMN_FOOD));
                        break;
                    case 1:
                        strResult[i] = objCursor.getString(objCursor.getColumnIndex(COLUMN_SOURCE));
                        break;
                    case 2:
                        strResult[i] = objCursor.getString(objCursor.getColumnIndex(COLUMN_PRICE));
                        break;
                    default:
                        break;
                } //switch

                objCursor.moveToNext();

            } //for

        } //if

        objCursor.close();
        return strResult;
    }


    public long addNewFood(String strFood, String strSource, String strPrice) {
        ContentValues obContentValues = new ContentValues();
        obContentValues.put(COLUMN_FOOD, strFood);
        obContentValues.put(COLUMN_SOURCE, strSource);
        obContentValues.put(COLUMN_PRICE, strPrice);

        return writSqLiteDatabase.insert(FOOD_TABLE, null, obContentValues);
    }
} // Main Class
