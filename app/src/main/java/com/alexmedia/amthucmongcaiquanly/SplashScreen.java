package com.alexmedia.amthucmongcaiquanly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.DigitalClock;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;

public class SplashScreen extends AppCompatActivity {
    ImageView anhintro;
    TextView ngaythangnam;
    Animation aniFade;
    DigitalClock digitalClock;
    Calendar lich;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        anhintro = findViewById(R.id.imgLogo);
        ngaythangnam = findViewById(R.id.txtLich);
        digitalClock = findViewById(R.id.simpleDigitalClock);
        aniFade  = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.float_in);
        anhintro.startAnimation(aniFade);
        digitalClock.startAnimation(aniFade);
        ngaythangnam.startAnimation(aniFade);
        lich = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(lich.getTime());
        ngaythangnam.setText(currentDate);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),DangNhapFB.class));
                finish();
            }
        },5000);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
    }
}
