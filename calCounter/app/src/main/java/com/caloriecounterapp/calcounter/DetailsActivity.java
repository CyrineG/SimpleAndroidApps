package com.caloriecounterapp.calcounter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import Model.Food;

public class DetailsActivity extends AppCompatActivity {
    private TextView foodName, foodCal, foodDate;
    private Button btnShare;
    private int foodID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        foodName = (TextView) findViewById(R.id.DtlfoodNameID);
        foodCal = (TextView) findViewById(R.id.DtlcalorieCountID);
        foodDate = (TextView) findViewById(R.id.DtldateAddedID);
        btnShare = (Button) findViewById(R.id.DtlBtnShare);

        Food food = (Food) getIntent().getSerializableExtra("foodObj");

        foodName.setText(food.getName());
        foodCal.setText(String.valueOf(food.getCalories()));
        foodDate.setText(food.getDateAdded());
        foodID = food.getId();

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });
    }



    public void share(){
        StringBuilder dataString = new StringBuilder();
        String name = foodName.getText().toString();
        String cal = foodCal.getText().toString();
        String date = foodDate.getText().toString();

        dataString.append("Food: "+ name +"\n");
        dataString.append("Calories: "+ cal +"\n");
        dataString.append("Date added: "+ date +"\n");

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_SUBJECT, "my caloric intake");
        intent.putExtra(Intent.EXTRA_EMAIL,new String[]{"syrynegamoudi@gmail.com"});
        intent.putExtra(Intent.EXTRA_TEXT, dataString.toString());

        try {
            startActivity(Intent.createChooser( intent,"sending email..."));
        } catch (ActivityNotFoundException e){
            Toast.makeText(getApplicationContext(),"please install app client before sending.", Toast.LENGTH_LONG).show();
        }

    }
}
