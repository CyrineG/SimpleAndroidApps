package com.example.petbio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView catView;
    private ImageView dogView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        catView= (ImageView) findViewById(R.id.catViewID);
        dogView = (ImageView) findViewById(R.id.dogViewID);

        catView.setOnClickListener(this);
        dogView.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.catViewID:
                Toast.makeText(MainActivity.this, "selected Cat!", Toast.LENGTH_SHORT).show();
                //go to second activity
                Intent catIntent = new Intent(MainActivity.this, BioActivity.class);
                catIntent.putExtra("name", "Boyl");
                catIntent.putExtra("bio", " Boyl is a broody cat with a big sense of pride. \n But Under that cold exterior Boyl is a smart and lovely cat that takes care of his owner.\n" +
                        " Expect lots of various gifts from Boyl ranging from unsightly 'food' he had hunted to flowers and random cuddles <3.");
                startActivity(catIntent);
                break;
            case R.id.dogViewID:
                Toast.makeText(MainActivity.this, "Selected Dog!", Toast.LENGTH_LONG).show();
                //go to second activity
                Intent dogIntent = new Intent(MainActivity.this, BioActivity.class);
                dogIntent.putExtra("name", "Samantha");
                dogIntent.putExtra("bio", " Samantha is a very energetic doggo with a big curiosity. \n Discipline migh be in order but with Samantha you'll be sure to never get bored!\n"+
                        " A few treats a day and lots of love can go a long way <3.");
                startActivity(dogIntent);
                break;
        }
    }
}
