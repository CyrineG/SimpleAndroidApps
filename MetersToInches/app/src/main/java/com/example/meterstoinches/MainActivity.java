package com.example.meterstoinches;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button btnConvert;
    private EditText valMeters;
    private TextView valInches;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnConvert = (Button) findViewById(R.id.btnConvert);
        valMeters = (EditText) findViewById(R.id.valMeters);
        valInches = (TextView) findViewById(R.id.valInches);

        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(valMeters.getText().toString().isEmpty()==true){
                    valInches.setText(String.format("no value to convert!"));
                    valInches.setText(R.string.error_msg);
                    valInches.setTextColor(Color.RED);
                    valInches.setVisibility(View.VISIBLE);

                } else{
                    Double meters;
                    Double inches;
                    meters = Double.parseDouble(valMeters.getText().toString());
                    inches = meters * 39.37;
                    valInches.setText(String.format("%.2f",inches));
                    valInches.setTextColor(Color.DKGRAY);
                    valInches.setVisibility(View.VISIBLE);
                }
            }
        });

    }
}
