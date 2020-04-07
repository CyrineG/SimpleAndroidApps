package com.example.mediaplayerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private Button btnPlay;
    private SeekBar mSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = new MediaPlayer();
        mediaPlayer= mediaPlayer.create(getApplicationContext(), R.raw.candygame1);
        btnPlay = (Button) findViewById(R.id.btnPlayID);
        mSeekBar = (SeekBar) findViewById(R.id.mSeekBarID);
        mSeekBar.setMax(mediaPlayer.getDuration());

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser){
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                btnPlay.setText("Play");
                int duration = mp.getDuration();
                String strDuration = String.valueOf(duration/1000);
                Toast.makeText(getApplicationContext(), "Duration : "+ strDuration+ "sec"  , Toast.LENGTH_SHORT).show();
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()){
                    pauseMusic();
                }
                else startMusic();
            }
        });
    }

    public void pauseMusic(){
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            btnPlay.setText("Play");
        }
    }

    public void startMusic(){
        if (mediaPlayer != null){
            btnPlay.setText("Pause");
            mediaPlayer.start();

        }
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer= null;
        }
        super.onDestroy();

    }
}
