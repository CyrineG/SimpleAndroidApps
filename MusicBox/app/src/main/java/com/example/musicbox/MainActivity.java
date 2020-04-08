package com.example.musicbox;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements OnClickListener{
    private SeekBar mSeekBar;
    private TextView currentTime, totalTime;
    private Button btnPlay, btnBeginning, btnEnding;
    private MediaPlayer mediaPlayer;
    private Thread thread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpUI();
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser) {
                    mediaPlayer.seekTo(progress);
                }

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                int currentPos = mediaPlayer.getCurrentPosition();
                int duration = mediaPlayer.getDuration();
                currentTime.setText(simpleDateFormat.format(new Date(currentPos)));
                totalTime.setText(simpleDateFormat.format(new Date(duration-currentPos)));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void setUpUI(){
        mSeekBar = (SeekBar) findViewById(R.id.mSeekBarID);
        currentTime = (TextView) findViewById(R.id.currentTimeID);
        totalTime = (TextView) findViewById(R.id.totalTimeID);
        btnBeginning = (Button) findViewById(R.id.btnBeginningID);
        btnPlay = (Button) findViewById(R.id.btnPlayID);
        btnEnding = (Button) findViewById(R.id.btnEndingID);
        mediaPlayer = new MediaPlayer().create(getApplicationContext(), R.raw.montee_vanilla_vip);

        btnPlay.setOnClickListener(this);
        btnBeginning.setOnClickListener(this);
        btnEnding.setOnClickListener(this);

        mSeekBar.setMax(mediaPlayer.getDuration());
        currentTime.setText("00:00");
        totalTime.setText(new SimpleDateFormat("mm:ss").format(new Date(mediaPlayer.getDuration())));



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnPlayID:
                if (mediaPlayer.isPlaying() && mediaPlayer!=null){
                    btnPlay.setBackgroundResource(android.R.drawable.ic_media_play);
                    mediaPlayer.pause();
                }
                else if(mediaPlayer!=null){
                    btnPlay.setBackgroundResource(android.R.drawable.ic_media_pause);
                    mediaPlayer.start();
                    updateThread();
                }
                break;

            case R.id.btnBeginningID:
                mediaPlayer.seekTo(0);
                break;

            case R.id.btnEndingID:
                mediaPlayer.seekTo(mediaPlayer.getDuration()-1000);
            break;

        }


        }

        public void updateThread(){
            thread = new Thread(){
                @Override
                public void run(){
                    try {
                        while(mediaPlayer !=null && mediaPlayer.isPlaying()) {
                            thread.sleep(50);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    int position = mediaPlayer.getCurrentPosition();
                                    int maxPosition = mediaPlayer.getDuration() - position;
                                    mSeekBar.setMax(maxPosition);
                                    mSeekBar.setProgress(position);
                                    currentTime.setText(String.valueOf(new java.text.SimpleDateFormat("mm:ss")
                                            .format(new Date(position))));
                                    totalTime.setText(String.valueOf(new java.text.SimpleDateFormat("mm:ss")
                                            .format(new Date(maxPosition))));
                                }
                            });
                        }

                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
        }

    @Override
    protected void onDestroy() {
        if(mediaPlayer!=null) {
            mediaPlayer.pause();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        thread.interrupt();
        thread = null;
        super.onDestroy();
    }

    }

