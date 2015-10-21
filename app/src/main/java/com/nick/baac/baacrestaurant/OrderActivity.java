package com.nick.baac.baacrestaurant;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.StrictMode;
import android.preference.DialogPreference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {
    //Explicit
    private TextView officerTextview;
    private Spinner deskSpinner;
    private ListView foodListView;
    private String officeString, deskString, foodString, itemString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        //Bind Widget
        bindWidget();

        // show officer
        showOfficer();

        //Create Spinner
        createSpinner();

        //Create ListView
        createListView();

    } //onCreate

    private void createListView() {
        foodTABLE obFoodTABLE = new foodTABLE(this);
        final String[] strFood = obFoodTABLE.readAllData(0);
        String[] strSource = obFoodTABLE.readAllData(1);
        String[] strPrice = obFoodTABLE.readAllData(2);

        MyAdapter objMyAdapter = new MyAdapter((OrderActivity.this), strSource, strFood, strPrice);
        foodListView.setAdapter(objMyAdapter);

        foodListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                foodString = strFood[i];
                chooseItem(strFood[i]);
            }
        });

    }

    private void chooseItem(String strFood) {
        CharSequence[] objCharSequences = {"1 Set","2 Set", "3 Set", "4 Set", "5 Set"};
        AlertDialog.Builder objBuilder = new AlertDialog.Builder(this);
        objBuilder.setTitle(strFood);
        objBuilder.setSingleChoiceItems(objCharSequences, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                itemString = Integer.toString(i + 1);
                uploadToMySQL();
                dialogInterface.dismiss();
            }
        });
        objBuilder.show();
    }

    private void uploadToMySQL() {
        AlertDialog.Builder objBuilder = new AlertDialog.Builder(this);
        objBuilder.setTitle("Officer ==> " + officeString);
        objBuilder.setMessage("Food = " + foodString + "\n"
                + "item = " + itemString + "\n"
                + "Desk = " + deskString);
        objBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        objBuilder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                postNewOrder();
                dialogInterface.dismiss();
            }
        });
        objBuilder.show();
    }

    private void postNewOrder() {

        StrictMode.ThreadPolicy myPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(myPolicy);

        try {

            ArrayList<NameValuePair> objNameValuePairs = new ArrayList<NameValuePair>();
            objNameValuePairs.add(new BasicNameValuePair("isAdd", "true"));
            objNameValuePairs.add(new BasicNameValuePair("Officer", officeString));
            objNameValuePairs.add(new BasicNameValuePair("Desk", deskString));
            objNameValuePairs.add(new BasicNameValuePair("Food", foodString));
            objNameValuePairs.add(new BasicNameValuePair("Item", itemString));

            HttpClient objHttpClient = new DefaultHttpClient();
            HttpPost objHttpPost = new HttpPost("http://swiftcodingthai.com/baac/php_add_data_restaurant.php");
            objHttpPost.setEntity(new UrlEncodedFormEntity(objNameValuePairs, "UTF-8"));
            objHttpClient.execute(objHttpPost);

            Toast.makeText(OrderActivity.this, "Update Order Successful", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(OrderActivity.this, "Cannot Order to MySQL", Toast.LENGTH_LONG).show();
        }
    }

    private void createSpinner() {
        final String[] strDesk = new String[10];
        strDesk[0] = "A1";
        strDesk[1] = "A2";
        strDesk[2] = "A3";
        strDesk[3] = "A4";
        strDesk[4] = "A5";
        strDesk[5] = "A6";
        strDesk[6] = "A7";
        strDesk[7] = "A8";
        strDesk[8] = "A9";
        strDesk[9] = "A10";

        final ArrayAdapter<String> deskAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strDesk);
        deskSpinner.setAdapter(deskAdapter);

        //Active When Choose
        deskSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                deskString = strDesk[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                deskString = strDesk[0]; // default ขึ้นตัวแรก A1

            }
        }); //เลือกรายการไปมาได้

    }

    private void showOfficer() {
        officeString = getIntent().getStringExtra("Name");
        officerTextview.setText(officeString);
    }

    private void bindWidget() {
        officerTextview = (TextView) findViewById(R.id.txtShowName);
        deskSpinner = (Spinner) findViewById(R.id.spinner);
        foodListView = (ListView) findViewById(R.id.listView);
    }
} //main class
