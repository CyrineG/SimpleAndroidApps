package com.example.tryme;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private View appView;
    private Button btnTryMe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appView = (View) findViewById(R.id.windowViewId);
        btnTryMe= (Button) findViewById(R.id.btnTryMe);

        final int  colors[] = new int[]{Color.BLUE,Color.GRAY, Color.RED, Color.GREEN,
        Color.MAGENTA, Color.YELLOW, Color.CYAN};

        appView.setBackgroundColor(Color.RED);
        btnTryMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                int randomColor = random.nextInt(colors.length);
                Log.d("Random", String.valueOf(randomColor));
                appView.setBackgroundColor(colors[randomColor]);
            }
        });

    }
}
