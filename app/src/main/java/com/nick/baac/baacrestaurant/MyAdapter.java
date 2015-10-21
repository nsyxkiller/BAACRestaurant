package com.nick.baac.baacrestaurant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by BAAC on 10/21/2015.
 */
public class MyAdapter extends BaseAdapter{

    //Explicit
    private Context objContext;
    private String[] sourceStrings, foodStrings, priceStrings;

    public MyAdapter(Context objContext, String[] sourceStrings, String[] foodStrings, String[] priceStrings) {
        this.objContext = objContext;
        this.sourceStrings = sourceStrings;
        this.foodStrings = foodStrings;
        this.priceStrings = priceStrings;
    }

    @Override
    public int getCount() {
        return foodStrings.length; // นับ array
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //เปิด Service Adapter
        LayoutInflater objLayoutInflater = (LayoutInflater) objContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //รับ data ทั้งหมด 50 record
        View objView1 = objLayoutInflater.inflate(R.layout.food_listview, viewGroup, false);

        // For show Food
        TextView foodTextView = (TextView) objView1.findViewById(R.id.txtShowFood);
        foodTextView.setText(foodStrings[i]);

        // For show price
        TextView priceTextView = (TextView) objView1.findViewById(R.id.txtShowPrice);
        priceTextView.setText(priceStrings[i]);

        //for Icon
        ImageView IconImageView = (ImageView) objView1.findViewById(R.id.imvicon);
        Picasso.with(objContext).
                load(sourceStrings[i]).
                resize(120, 120).
                into(IconImageView);


        return objView1;
    }
} // Main Class
