package com.caloriecounterapp.calcounter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Data.DatabaseHandler;
import Model.Food;

public class MainActivity extends AppCompatActivity {
    private EditText foodName, foodCalories;
    private Button btnSubmit;
    private DatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        foodName =(EditText) findViewById(R.id.foodNameID);
        foodCalories = (EditText) findViewById(R.id.foodCaloriesID);
        btnSubmit = (Button) findViewById(R.id.btnSubmitID);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataToDB();
            }
        });

    }

    private void saveDataToDB() {
        db = new DatabaseHandler(this);
        Food food =new Food();
        String name = foodName.getText().toString().trim();
        String calString = foodCalories.getText().toString().trim();
        if (calString.isEmpty() || name.isEmpty()){
            Toast.makeText(this,"Please enter fields", Toast.LENGTH_LONG).show();
        } else{
            int cal = Integer.parseInt(calString);
            food.setName(name);
            food.setCalories(cal);
            db.addFood(food);
            db.close();

            //clear form
            foodName.setText("");
            foodCalories.setText("");

            //takes users to list
            startActivity( new Intent(MainActivity.this, ListActivity.class) );

        }
    }
}
