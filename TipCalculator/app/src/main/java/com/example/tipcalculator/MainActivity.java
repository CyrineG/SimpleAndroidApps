package com.example.tipcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText billAmount;
    private Button btnCalcTip;
    private SeekBar tipPourcentageBar;
    private TextView pourcentage;
    private TextView rsltTip;
    private TextView totalBill;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        billAmount = (EditText) findViewById(R.id.billAmountID);
        btnCalcTip = (Button) findViewById(R.id.btnCalcTip);
        rsltTip = (TextView) findViewById(R.id.rsltTip);
        totalBill = (TextView) findViewById(R.id.totalBill);
        tipPourcentageBar = (SeekBar) findViewById(R.id.tipPoucentageID);
        pourcentage = (TextView) findViewById(R.id.tipPercentageShowID);
        pourcentage.setText("0%");
        rsltTip.setText("0");
        tipPourcentageBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pourcentage.setText(tipPourcentageBar.getProgress()+"%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });

        btnCalcTip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double tipValue = Double.parseDouble(billAmount.getText().toString()) * tipPourcentageBar.getProgress()/100;
                double totalBillValue = Double.parseDouble(billAmount.getText().toString()) + tipValue;
                rsltTip.setText(String.format("Tip : %.2f", tipValue));
                totalBill.setText(String.format("Total Bill : %.2f",totalBillValue));
            }
        });
    }
}
