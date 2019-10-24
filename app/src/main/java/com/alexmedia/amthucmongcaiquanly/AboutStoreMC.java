package com.alexmedia.amthucmongcaiquanly;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class AboutStoreMC extends AppCompatActivity {

    String tench,diachi,timeopen,sodt,fb,createby,danhmuc,ship,image;
    String idc,image3;
    TextView danhmuc1,create1,diachifix,opentime1,sdt1,facebook1,tinhtrangship1;
    ImageView imgCH,imgHienThiUpload,imgThemAnhToanBo;
    Button themhinhanh;
    Context context;
    Intent intent;
    DatabaseReference databaseReference;
    FloatingActionButton fab;
    private Uri imgUri;
    private static final int CHOOSE_IMAGE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_store_mc);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        intent = getIntent();
        tench = intent.getStringExtra(MainActivity.TENCH);
        diachi = intent.getStringExtra(MainActivity.ADDRESS);
        timeopen = intent.getStringExtra(MainActivity.TIMEOPENEND);
        sodt = intent.getStringExtra(MainActivity.SODIENTHOAI);
        fb = intent.getStringExtra(MainActivity.FACEBOOK_CH);
        createby = intent.getStringExtra(MainActivity.CREATE_BY);
        danhmuc = intent.getStringExtra(MainActivity.DANHMUC);
        ship = intent.getStringExtra(MainActivity.SHIPTINHTRANG);
        image = intent.getStringExtra(MainActivity.IMAGE);
        databaseReference = FirebaseDatabase.getInstance()
                .getReference("CuaHang/DanhSachCuaHang").child(intent.getStringExtra(MainActivity.ID));
        imgCH = findViewById(R.id.iv_detail);
        create1 = findViewById(R.id.txtCreateBy1);
        danhmuc1 = findViewById(R.id.txtDanhMuc2);
        opentime1 = findViewById(R.id.txttimeOpenEnd2);
        sdt1 = findViewById(R.id.txtSDTMC);
        facebook1 = findViewById(R.id.txfFacebook);
        tinhtrangship1 = findViewById(R.id.txtTinhTrangShipDo);
        diachifix = findViewById(R.id.txtDiachi1);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AboutStoreMC.this, "ccc", Toast.LENGTH_SHORT).show();
            }
        });
        setTitle("Thông tin cửa hàng");
        diachifix.setText("Địa Chỉ" + diachi);
        opentime1.setText("Time:" + timeopen);
        sdt1.setText("SĐT:"+sodt);
        facebook1.setText("Fanpage: "+fb);
        create1.setText("Mail Create: "+createby);
        danhmuc1.setText("Danh Mục: "+danhmuc);
        tinhtrangship1.setText("Tình Trạng Giao Hàng: " + ship);
        Picasso.with(context).load(image).into(imgCH);
        imgHienThiUpload = findViewById(R.id.imgHienThiCacAnh);
        themhinhanh = findViewById(R.id.btnChooseImage);
        imgThemAnhToanBo = findViewById(R.id.imgUpload2);
        themhinhanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFile();
            }
        });
    }
    private void ShowFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, CHOOSE_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
                imgHienThiUpload.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
