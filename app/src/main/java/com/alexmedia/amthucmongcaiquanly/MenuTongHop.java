package com.alexmedia.amthucmongcaiquanly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MenuTongHop extends AppCompatActivity {

    ImageView btnClickBack;
    Button them,danhsach,about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_tong_hop);
        btnClickBack = findViewById(R.id.btnBack111333);
        btnClickBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        them = findViewById(R.id.btnthem);
        danhsach = findViewById(R.id.danhSachDuLich);
        about = findViewById(R.id.btnAboutCreaet);
        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ThemDuLich.class));
            }
        });
        danhsach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DanhSachDuLich.class));
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AboutApplication.class));
            }
        });
    }
}
