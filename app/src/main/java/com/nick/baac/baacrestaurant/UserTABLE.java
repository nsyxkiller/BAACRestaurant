package com.nick.baac.baacrestaurant;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by BAAC on 10/19/2015.
 */
public class UserTABLE {

    //Explicit
    private MyOpenHelper obMyOpenHelper;
    private SQLiteDatabase writeSQSqLiteDatabase, readSqLiteDatabase;

    public static final String USER_TABLE = "userTABLE";
    public static final String COLUMN_ID_USER = "_id";
    public static final String COLUMN_USER = "User";
    public static final String COLUMN_PASSWORD = "Password";
    public static final String COLUMN_NAME = "Name";


    public UserTABLE(Context context) {
        obMyOpenHelper = new MyOpenHelper(context);
        writeSQSqLiteDatabase = obMyOpenHelper.getWritableDatabase();
        readSqLiteDatabase = obMyOpenHelper.getReadableDatabase();

    } // Constructor

    public long addNewUser(String strUser, String strPassword, String strName) {

        ContentValues objContentValues = new ContentValues();
        objContentValues.put(COLUMN_USER, strUser);
        objContentValues.put(COLUMN_PASSWORD, strPassword);
        objContentValues.put(COLUMN_NAME, strName);

        return writeSQSqLiteDatabase.insert(USER_TABLE, null, objContentValues);
    }

} // Main Class
