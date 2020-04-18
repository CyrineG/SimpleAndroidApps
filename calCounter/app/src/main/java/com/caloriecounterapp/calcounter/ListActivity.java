package com.caloriecounterapp.calcounter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import Data.CustomListViewAdapter;
import Data.DatabaseHandler;
import Model.Food;
import Util.Utils;

public class ListActivity extends AppCompatActivity {
    private DatabaseHandler db;
    private ArrayList<Food> dbFood = new ArrayList<>();
    private CustomListViewAdapter foodAdapter;
    private ListView listView;
    private Food myFood;
    private TextView totalCals, totalFoods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = (ListView) findViewById(R.id.listID);
        totalCals = (TextView) findViewById(R.id.totCaloriesID);
        totalFoods = (TextView) findViewById(R.id.totFoodsID);
        
        refreshData();


    }

    private void refreshData() {
        dbFood.clear();
        db = new DatabaseHandler(getApplicationContext());
        ArrayList<Food> foodFromDB = db.getAllFood();
        int calsValue = db.getSumCalories();
        int foodCount = db.getFoodCount();
        String formattedCalsValue = Utils.numberFormat(calsValue);
        String formattedFoodCount = Utils.numberFormat(foodCount);

        totalCals.setText(formattedCalsValue);
        totalFoods.setText(formattedFoodCount);

        for (int i =0; i< foodFromDB.size(); i++){
            String name = foodFromDB.get(i).getName();
            int cal = foodFromDB.get(i).getCalories();
            String dateAdded = foodFromDB.get(i).getDateAdded();
            int id = foodFromDB.get(i).getId();

            Log.d("added Food", String.valueOf(id));

            myFood= new Food(id,name,cal,dateAdded);

            dbFood.add(myFood);
        }

        db.close();

        //set Adapter
        foodAdapter = new CustomListViewAdapter(this, R.layout.list_row,dbFood);
        listView.setAdapter(foodAdapter);
        foodAdapter.notifyDataSetChanged();
    }
}
