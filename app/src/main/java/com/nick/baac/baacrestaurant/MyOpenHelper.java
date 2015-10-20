package com.nick.baac.baacrestaurant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by BAAC on 10/19/2015.
 */
public class MyOpenHelper extends SQLiteOpenHelper{

    // Explicit ประกาศตัวแปร
    private static final String DATABASE_NAME = "BAAC.db";  //final ไม่สามารถแก้ไขได้
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_USER_TABLE = "create table userTable (_id integer primary key, User text, Password text, Name text);";
    private static final String CRATE_FOOD_TABLE = "create table foodTable (_id integer primary key, Food text, Source Text, Price Text);";


    public MyOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }  //Constructor

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //manage Table
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
        sqLiteDatabase.execSQL(CRATE_FOOD_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
} // Main Class คลาสหลัก
