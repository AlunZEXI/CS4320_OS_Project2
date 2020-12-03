package com.example.test2;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.widget.ImageView;
import android.widget.Toast;

import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Random;

public class MyService extends Service {

    MediaPlayer myPlayer;
    String songs[] = {"777 - Joji","Flexing So Hard - Higher Brothers","Lover Boy 88 - Higher Brothers","Masiwei - Masiwei vs ChineseRap","Midsummer Madness - 88rising,Higher Brothers,Rich Brian","Mr. Bentley - KnowKnow,Higher Brothers","Papillon - Jackson Wang","R&B All Night - KnowKnow,Higher Brothers","Rich Brian - Love In My Pocket","Tequila Sunrise (feat. AUGUST 08 & GoldLink) - 88rising,Jackson Wang,Higher Brothers"};
    int uris[] = {R.raw.s1,R.raw.s2,R.raw.s3,R.raw.s4,R.raw.s5,R.raw.s6,R.raw.s7,R.raw.s8,R.raw.s9,R.raw.s10};
    Random rand = new Random();
    int goingOn;
    int maxVolume = 100;
    float log1=(float)(1 - Math.log(maxVolume-40)/Math.log(maxVolume));

    public class MyServiceBinder extends Binder{
        public MyService getService(){
            return MyService.this;
        }
    }
    IBinder mBinder = new MyServiceBinder();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public String sendData(){
        return songs[goingOn];
    }
    public int sendNumber(){
        return goingOn;
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "Service Created", Toast.LENGTH_LONG).show();
        goingOn = rand.nextInt(10);
        int song = uris[goingOn];
        myPlayer = MediaPlayer.create(this,song);
        myPlayer.setLooping(false); // Set looping

    }
    @Override
    public void onStart(Intent intent, int startid) {
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        myPlayer.start();
    }
    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_LONG).show();
        myPlayer.stop();
    }
}