package com.example.backgroundmusicplayer;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity{
    private Button btnPlayMusic;
    private LinearLayout linearLayout;
    private Button btnStop;
    private Button btnBackward;
    private Button btnForward;
    private TextView tvTotalDuration;
    private TextView tvCurrentDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListener();
    }

    private void initView(){
        btnPlayMusic = findViewById(R.id.btn_play_music);
        linearLayout = findViewById(R.id.linearlayout);
        btnStop = findViewById(R.id.btn_stop_music);
        btnBackward = findViewById(R.id.btn_backward);
        btnForward = findViewById(R.id.btn_forward);
        if (BackgroundSoundPlayer.isPlaying()) {
            btnPlayMusic.setText("Pause");
            linearLayout.setVisibility(View.VISIBLE);
        }
    }

    private void initListener(){
        btnPlayMusic.setOnClickListener(v -> PlayBackgroundMuscic());
        btnStop.setOnClickListener(v -> stopBackgroundMusic());
        btnBackward.setOnClickListener(v -> BackgroundSoundPlayer.toBackward());
        btnForward.setOnClickListener(v -> BackgroundSoundPlayer.toForward());
    }

    public void PlayBackgroundMuscic() {
        if(!BackgroundSoundPlayer.isPlaying()) {
            Intent intent = new Intent(MainActivity.this, BackgroundSoundPlayer.class);
            startService(intent);
            btnPlayMusic.setText("Pause");
            linearLayout.setVisibility(View.VISIBLE);
        } else if (btnPlayMusic.getText() == "Pause"){
            btnPlayMusic.setText("Play");
            BackgroundSoundPlayer.pauseSong();
        } else {
            btnPlayMusic.setText("Pause");
            BackgroundSoundPlayer.continueSong();
        }
    }

    private void stopBackgroundMusic() {
        Intent intent = new Intent(MainActivity.this, BackgroundSoundPlayer.class);
        stopService(intent);
        linearLayout.setVisibility(View.GONE);
        btnPlayMusic.setText("Play");
    }
}