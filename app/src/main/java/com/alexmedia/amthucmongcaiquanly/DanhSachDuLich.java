package com.alexmedia.amthucmongcaiquanly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DanhSachDuLich extends AppCompatActivity {
    ImageView btnQuayLai;
    ListView lvDuLich;
    DatabaseReference dataDuLich1;
    AdapterDuLich adapterDuLich;
    ProgressBar progxuly1;
    List<ModelDangBaiDuLich> arrayModel;
    public static final String ID = "id";
    public static final String TENDULICH = "tendulich";
    public static final String STREETVIEW = "linkstreetview";
    public static final String YOUTUBELINK = "youtubelink";
    public static final String BAIVIETGIOITHIEU = "baivietgioithieu";
    public static final String IMAGEDULICH = "imagedulich";
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_du_lich);
        btnQuayLai = findViewById(R.id.btnBack139);
        btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        arrayModel = new ArrayList<>();
        lvDuLich =  findViewById(R.id.listviewDS);
        dataDuLich1 = FirebaseDatabase.getInstance().getReference("DuLich");
        progxuly1 = findViewById(R.id.proload);
        adapterDuLich = new AdapterDuLich(DanhSachDuLich.this,arrayModel);
        lvDuLich.setAdapter(adapterDuLich);
        dataDuLich1.addValueEventListener(valueEventListener);
        intent = getIntent();
        lvDuLich.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ModelDangBaiDuLich modelDangBaiDuLich = arrayModel.get(position);
                Intent intent = new Intent(getApplicationContext(),InfomationDuLich.class);
                intent.putExtra(ID,modelDangBaiDuLich.id);
                intent.putExtra(TENDULICH,modelDangBaiDuLich.namedulich);
                intent.putExtra(STREETVIEW,modelDangBaiDuLich.linkgoogle);
                intent.putExtra(YOUTUBELINK,modelDangBaiDuLich.linkyoutube);
                intent.putExtra(BAIVIETGIOITHIEU,modelDangBaiDuLich.baidang);
                intent.putExtra(IMAGEDULICH,modelDangBaiDuLich.imagedulich);
                startActivity(intent);
            }
        });
    }
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            arrayModel.clear();
            for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                ModelDangBaiDuLich modelDangBaiDuLich = dataSnapshot1.getValue(ModelDangBaiDuLich.class);
                arrayModel.add(modelDangBaiDuLich);
                adapterDuLich.notifyDataSetChanged();
                progxuly1.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(DanhSachDuLich.this, "Danh Sách trống", Toast.LENGTH_SHORT).show();
        }
    };
}
