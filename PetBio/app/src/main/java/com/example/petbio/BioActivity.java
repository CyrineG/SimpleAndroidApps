package com.example.petbio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class BioActivity extends AppCompatActivity {
    private ImageView imgView;
    private TextView name;
    private TextView bio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio);
        imgView = (ImageView) findViewById(R.id.imageViewID);
        name = (TextView) findViewById(R.id.nameID);
        bio = (TextView) findViewById(R.id.bioID);

        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        if (data != null){
            String strName = data.getString("name");
            String strBio = data.getString("bio");

            setUp(strName,strBio);
        }

    }

    private void setUp(String strName, String strBio){
        switch(strName){
            case "Boyl":
                imgView.setImageDrawable(getResources().getDrawable(R.drawable.cat_icon));
                break;
            case "Samantha":
                imgView.setImageDrawable(getResources().getDrawable(R.drawable.dog_icon));
                break;
        }

        name.setText(strName);
        bio.setText(strBio);
    }
}
