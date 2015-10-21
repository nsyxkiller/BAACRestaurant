package com.nick.baac.baacrestaurant;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    //Explicit
    private UserTABLE obUserTABLE;
    private foodTABLE obFoodTABLE;
    private EditText userEditText, passwordEditText;
    private String userString, passwordString;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind Widget
        bindWidget();


        // Create & Connect Database
        createAndConnect();

        // Tester Add New Value
        //testerAdd();

        //Delete All SQLite
        deleteAllSQLite();

        //Synchronize JSON to SQLite
        synJSONtoSQLite();

    } // Main Method

    private void bindWidget() {
        userEditText = (EditText) findViewById(R.id.editText);
        passwordEditText = (EditText) findViewById(R.id.editText2);
    }

    public void clickLogin(View view) {

        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        if (userString.equals("") || passwordString.equals("")) {

            //Have Space
            errorDialog("Have Space", "Please Fill All Every Blank");

        } else {

            //No Space
            checkUser();


        }

    }

    private void checkUser() {
        try {

            String[] strMyResult = obUserTABLE.searchUser(userString);
            if (passwordString.equals(strMyResult[2])) {
                Toast.makeText(MainActivity.this, "Welcom " + strMyResult[3],
                        Toast.LENGTH_LONG).show();


                //Intent to OrderActivity
                Intent objIntent = new Intent(MainActivity.this, OrderActivity.class);
                objIntent.putExtra("Name", strMyResult[3]);
                startActivity(objIntent);
                finish();

            } else {
                errorDialog("Password False", "Please Try again Password False");
            }

        } catch (Exception e) {
            errorDialog("No This User", "No " + userString + " on my Database");
        }
        }

    private void errorDialog(String strTitle, String strMessage) {

        AlertDialog.Builder objBuilder = new AlertDialog.Builder(this);
        objBuilder.setIcon(R.drawable.danger);
        objBuilder.setTitle(strTitle);
        objBuilder.setMessage(strMessage);
        objBuilder.setCancelable(false);
        objBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        objBuilder.show();

    }


    private void synJSONtoSQLite() {

        //0. Change Policy
        StrictMode.ThreadPolicy myPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(myPolicy);

        int inTimes = 0;
        while (inTimes <= 1) {
            // น้อยกว่า 1 เพราะว่ามี 2 Table เริ่มที่ 0 - 1
            InputStream objInputStream = null;
            String strJSON = null;
            String strUserURL = "http://swiftcodingthai.com/baac/php_get_data_master.php";
            String strFoodURL = "http://swiftcodingthai.com/baac/php_get_food.php";
            HttpPost objHttpPost;

            //1. Create Input Stream
            try {

                HttpClient objHttpClient = new DefaultHttpClient();

                switch (inTimes) {
                    case 0:
                        objHttpPost = new HttpPost(strUserURL);
                        break;
                    case 1:
                        objHttpPost = new HttpPost(strFoodURL);
                        break;
                    default: // บังคับกรณีไม่เท่ากับ 1 และ 0
                        objHttpPost = new HttpPost(strUserURL);
                } //switch
                //ต้องมี response Entity input stream
                HttpResponse objHttpResponse = objHttpClient.execute(objHttpPost);
                HttpEntity objHttpEntity = objHttpResponse.getEntity();
                objInputStream = objHttpEntity.getContent();

            } catch (Exception e) { //เอาค่าที่ error ไปเก็บไว้ที่ e และโปรแกรมดำเนินการต่อ
                Log.d("baac", "InputStream ==> " + e.toString());
            }

            //2. Create JSON String
            try {

                // คำนวน Streaming ก่อนโหลด พร้อมเข้ารหัส UTF-8
                BufferedReader objBufferedReader = new BufferedReader(new InputStreamReader(objInputStream, "UTF-8"));
                //เอา String ย่อยมาผนวกเป็นตัวเดียว
                StringBuilder objStringBuilder = new StringBuilder();
                // ตัวแปรรับสิ่งที่ตัด
                String strLine = null;

                //strLine เท่ากับสิ่งที่ตัด และไม่เท่ากับ null
                while ((strLine = objBufferedReader.readLine()) != null) {

                    objStringBuilder.append(strLine);

                } //while
                objInputStream.close();
                strJSON = objStringBuilder.toString();

            } catch (Exception e) {
                Log.d(("baac"), "StrJSON ==> " + e.toString());
            }
            //3. Update SQLite
            try {
                JSONArray objJsonArray = new JSONArray(strJSON);
                for (int i = 0; i < objJsonArray.length(); i++) {

                    JSONObject object = objJsonArray.getJSONObject(i);

                    switch (inTimes) {
                        case 0:

                            // For userTABLE
                            String strUser = object.getString("User");
                            String strPassword = object.getString("Password");
                            String strName = object.getString("Name");
                            obUserTABLE.addNewUser(strUser,strPassword,strName);

                            break;
                        case 1:

                            // For foodTABLE
                            String strFood = object.getString("Food");
                            String strSource = object.getString("Source");
                            String strPrice = object.getString("Price");
                            obFoodTABLE.addNewFood(strFood, strSource, strPrice);

                            break;
                    }//switch
                } // for
            } catch (Exception e) {
                Log.d("baac", "Update ==> " + e.toString());
            }

            inTimes += 1; //เพิ่มค่าทีละ 1
        } //while
    }// syJSONtoSQLite

    private void deleteAllSQLite() {
        //Mode private อนุญาติให้ลบไม่ให้ดรอปตาราง
        SQLiteDatabase objSqLiteDatabase = openOrCreateDatabase("BAAC.db", MODE_PRIVATE, null);
        objSqLiteDatabase.delete("userTABLE", null, null);
        objSqLiteDatabase.delete("foodTABLE", null, null);
    }

    private void testerAdd() {
        obUserTABLE.addNewUser("testUser", "testPassword", "ทดสอบชื่อภาษาไทย");
        obFoodTABLE.addNewFood("ชื่ออาหาร", "testSource","123");
    }

    private void createAndConnect() {
        obUserTABLE = new UserTABLE(this);
        obFoodTABLE = new foodTABLE(this);
    }
} // Main Class
