package com.example.backgroundmusicplayer;

import static com.example.backgroundmusicplayer.MyApplication.CHANNEL_ID;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class BackgroundSoundPlayer extends Service {
    static private MediaPlayer mediaPlayer;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.guardian_tales_ost);
        mediaPlayer.setLooping(true); // lặp lại
    }
    public int onStartCommand(Intent intent, int flags, int startId) {
        sendNotification();
        mediaPlayer.start();
        return startId;
    }

    private void sendNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat
                .Builder(this, CHANNEL_ID)
                .setContentTitle("Guardian tale OST")
                .setContentText("Music is playing")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        mediaPlayer = null;
        super.onDestroy();
    }

    static public boolean isPlaying(){
        if (mediaPlayer == null) {
            return false;
        }
        return true;
    }

    static public int getSongDuration(){ return mediaPlayer.getDuration(); }

    static public int getSongCurrentDuration(){
        return mediaPlayer.getCurrentPosition();
    }

    static public void seekToDuration(int position){
        mediaPlayer.seekTo(position);
    }

    static public void pauseSong(){
        mediaPlayer.pause();
    }

    static public void continueSong(){
        mediaPlayer.start();
    }

    static public void toForward(){
        int currentPosition = mediaPlayer.getCurrentPosition();
        if(currentPosition + 5000 <= mediaPlayer.getDuration()){
            mediaPlayer.seekTo(currentPosition + 5000);
        }else{
            mediaPlayer.seekTo(mediaPlayer.getDuration());
        }
    }

    static public void toBackward(){
        int currentPosition = mediaPlayer.getCurrentPosition();
        if(currentPosition - 5000 >= 0){
            mediaPlayer.seekTo(currentPosition - 5000);
        }else{
            mediaPlayer.seekTo(0);
        }
    }
}