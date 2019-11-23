package com.alexmedia.amthucmongcaiquanly.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alexmedia.amthucmongcaiquanly.Adapter.AdapterIntroCuaHang;
import com.alexmedia.amthucmongcaiquanly.R;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

public class ImageFullScreen extends AppCompatActivity {

    Intent intent;
    String id,image88;
    PhotoView imageView1l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_full_screen);
        intent = getIntent();
        id = intent.getStringExtra(AdapterIntroCuaHang.ID);
        image88 = intent.getStringExtra(AdapterIntroCuaHang.IMAGE66);
        imageView1l = findViewById(R.id.photomc);
        Glide.with(this).load(image88).into(imageView1l);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() !=null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setTitle("");
    }
}
