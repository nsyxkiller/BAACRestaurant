package com.nick.baac.baacrestaurant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

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
        String[] strFood = obFoodTABLE.readAllData(0);
        String[] strSource = obFoodTABLE.readAllData(1);
        String[] strPrice = obFoodTABLE.readAllData(2);

        MyAdapter objMyAdapter = new MyAdapter((OrderActivity.this), strSource, strFood, strPrice);
        foodListView.setAdapter(objMyAdapter);
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
