package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button buttonStart, buttonStop,buttonNext, buttonBind, buttonGet, buttonUnbind;
    TextView textResult, textTest;
    int imgs[] = {R.raw.i1,R.raw.i2,R.raw.i3,R.raw.i4,R.raw.i5,R.raw.i6,R.raw.i7,R.raw.i8,R.raw.i9,R.raw.i10};
    boolean isServiceBound = false;
    MyService myService;
    Intent serviceIntent;
    ImageView imageIcon;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyService.MyServiceBinder m = (MyService.MyServiceBinder)service;
            myService = m.getService();
            isServiceBound = true;
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            isServiceBound = false;
        }
    };

    public void bindService(){
        serviceIntent = new Intent(this,MyService.class);
        bindService(serviceIntent,serviceConnection, Context.BIND_AUTO_CREATE);
        if(isServiceBound){
            textResult.setText("Service is onBind.");
        }
    }

    public void unbindService(){
        if(isServiceBound){
            unbindService(serviceConnection);
            isServiceBound = false;
            textResult.setText("Service is not onBind.");
            imageIcon.setVisibility(View.INVISIBLE);
        }
    }

    public void getData() {
        if(isServiceBound){
            textResult.setText(myService.sendData());
            imageIcon.setVisibility(View.VISIBLE);
                int imgNumber = myService.sendNumber();
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),imgs[imgNumber]);
                imageIcon.setImageBitmap(bitmap);
        }
        else if(!isServiceBound){
            textResult.setText("Not Bound");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStart = findViewById(R.id.buttonStart);
        buttonStop = findViewById(R.id.buttonStop);
        //buttonNext =  findViewById(R.id.buttonNext);
        buttonBind = findViewById(R.id.buttonBind);
        buttonGet =  findViewById(R.id.buttonGet);
        buttonUnbind = findViewById(R.id.buttonUnbind);
        textResult = findViewById(R.id.textResult);
        textTest = findViewById(R.id.textTest);
        imageIcon = findViewById(R.id.ImageIcon);

        buttonStart.setOnClickListener(this);
        buttonStop.setOnClickListener(this);
        //buttonNext.setOnClickListener(this);
        buttonGet.setOnClickListener(this);
        buttonBind.setOnClickListener(this);
        buttonUnbind.setOnClickListener(this);


    }
    public void onClick(View src) {
        switch (src.getId()) {
            case R.id.buttonStart:
                startService(new Intent(this, MyService.class));
                textTest.setText("Music Going on.");
                break;
            case R.id.buttonStop:
                stopService(new Intent(this, MyService.class));
                textTest.setText("Music NOT Going on.");
                break;
            //case R.id.buttonNext:
                //Intent intent=new Intent(this,NextPage.class);
                //startActivity(intent);
                //break;
            case R.id.buttonBind:
                bindService();
                Toast.makeText(this, "onBind", Toast.LENGTH_LONG).show();
                break;
            case R.id.buttonGet:
                getData();

                break;
            case R.id.buttonUnbind:
                unbindService();
                Toast.makeText(this, "NOT onBind", Toast.LENGTH_LONG).show();
                break;
        }
    }
}