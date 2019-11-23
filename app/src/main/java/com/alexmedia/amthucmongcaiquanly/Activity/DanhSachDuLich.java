package com.alexmedia.amthucmongcaiquanly.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alexmedia.amthucmongcaiquanly.Adapter.AdapterDuLich;
import com.alexmedia.amthucmongcaiquanly.Model.ModelDangBaiDuLich;
import com.alexmedia.amthucmongcaiquanly.R;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DanhSachDuLich extends AppCompatActivity {
    ImageView btnQuayLai,imaInfo,androidmamual;
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
    EditText edtTenChinh,edtStreetview,edtYoutube,edtNoiDung;
    Button btnUpload,btnDelete,btnCancel,btnDongLai;

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
        androidmamual = findViewById(R.id.androidMamual1);
        androidmamual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog1();
            }
        });
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
                intent.putExtra(ID,modelDangBaiDuLich.getId());
                intent.putExtra(TENDULICH,modelDangBaiDuLich.getNamedulich());
                intent.putExtra(STREETVIEW,modelDangBaiDuLich.getLinkgoogle());
                intent.putExtra(YOUTUBELINK,modelDangBaiDuLich.getLinkyoutube());
                intent.putExtra(BAIVIETGIOITHIEU,modelDangBaiDuLich.getBaidang());
                intent.putExtra(IMAGEDULICH,modelDangBaiDuLich.getImagedulich());
                startActivity(intent);
            }
        });
        lvDuLich.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ModelDangBaiDuLich modelDangBaiDuLich = arrayModel.get(position);
                showFixDuLich(modelDangBaiDuLich.getId(),modelDangBaiDuLich.getNamedulich(),modelDangBaiDuLich.getLinkgoogle(),modelDangBaiDuLich.getLinkyoutube()
                        ,modelDangBaiDuLich.getBaidang(),modelDangBaiDuLich.getImagedulich());
                intent.putExtra(ID,modelDangBaiDuLich.getId());
                intent.putExtra(TENDULICH,modelDangBaiDuLich.getNamedulich());
                intent.putExtra(STREETVIEW,modelDangBaiDuLich.getLinkgoogle());
                intent.putExtra(YOUTUBELINK,modelDangBaiDuLich.getLinkyoutube());
                intent.putExtra(BAIVIETGIOITHIEU,modelDangBaiDuLich.getBaidang());
                intent.putExtra(IMAGEDULICH,modelDangBaiDuLich.getImagedulich());
                return false;
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
    private void showFixDuLich(final String id,final String namedulich,final String streetview,
                               final String youtube,final String noidung,final String image){
        AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_fixsuabao, null);
        dialogbuilder.setView(dialogView);
        final AlertDialog b = dialogbuilder.create();
        b.show();
        imaInfo = dialogView.findViewById(R.id.imgScreen1);
        edtTenChinh = dialogView.findViewById(R.id.edtTenDuLich1);
        edtStreetview = dialogView.findViewById(R.id.edtLinkStreetView1);
        edtYoutube = dialogView.findViewById(R.id.edtlinkyoutube1);
        edtNoiDung =  dialogView.findViewById(R.id.edtGioiThieu1);
        Glide.with(this).load(image).into(imaInfo);
        edtTenChinh.setText(namedulich);
        edtStreetview.setText(streetview);
        edtYoutube.setText(youtube);
        edtNoiDung.setText(noidung);
        btnUpload = dialogView.findViewById(R.id.buttonUploadBaiDang);
        btnDelete = dialogView.findViewById(R.id.buttonXoaDuLich);
        btnCancel = dialogView.findViewById(R.id.buttonthethoi);

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namedulich = edtTenChinh.getText().toString();
                String streetview = edtStreetview.getText().toString();
                String youtube = edtYoutube.getText().toString();
                String noidung = edtNoiDung.getText().toString();
                if (namedulich.isEmpty() || streetview.isEmpty() || youtube.isEmpty() || noidung.isEmpty()){

                    Toast.makeText(DanhSachDuLich.this, "Nhập đầy đủ vào", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(DanhSachDuLich.this, "Cập Nhật Thành Công", Toast.LENGTH_SHORT).show();
                    updateArtist(id,namedulich,streetview,youtube,noidung,image);
                    b.dismiss();
                }
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteArtist(id);
                b.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.dismiss();
            }
        });
    }
    private boolean updateArtist(String id,String namedulich,String streetview, String youtube, String noidung,String image) {
        //getting the specified artist reference
        dataDuLich1 = FirebaseDatabase.getInstance().getReference("DuLich").child(id);

        //updating artist
        ModelDangBaiDuLich modelDangBaiDuLich = new ModelDangBaiDuLich(id,namedulich,streetview,youtube,noidung,image);
        dataDuLich1.setValue(modelDangBaiDuLich);
        Toast.makeText(getApplicationContext(), "Đã thay đổi thành công", Toast.LENGTH_LONG).show();
        return true;
    }
    private boolean deleteArtist(String id) {
        //getting the specified artist reference
        dataDuLich1 = FirebaseDatabase.getInstance().getReference("DuLich").child(id);

        //removing artist
        dataDuLich1.removeValue();
        Toast.makeText(getApplicationContext(), "Đã xóa thành công", Toast.LENGTH_LONG).show();

        return true;
    }
    public void createDialog1() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dialog_hdsdunglist, null);
        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(this);
        alert.setView(alertLayout);
        alert.setCancelable(false);
        btnDongLai = alertLayout.findViewById(R.id.donglaidialog1);
        final android.app.AlertDialog dialog = alert.create();
        dialog.show();
        btnDongLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
