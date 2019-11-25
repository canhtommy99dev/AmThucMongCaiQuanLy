package com.alexmedia.amthucmongcaiquanly.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alexmedia.amthucmongcaiquanly.Adapter.AdapterIntroCuaHang;
import com.alexmedia.amthucmongcaiquanly.MainActivity;
import com.alexmedia.amthucmongcaiquanly.Model.ModelImageCuaHANG;
import com.alexmedia.amthucmongcaiquanly.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AboutStoreMC extends AppCompatActivity {

    String tench,diachi,timeopen,sodt,fb,createby,danhmuc,ship,image;
    String id,image3;
    TextView danhmuc1,create1,diachifix,opentime1,sdt1,facebook1,tinhtrangship1;
    ImageView imgCH,imgHienThiUpload,imgThemAnhToanBo;
    Button themhinhanh;
    Context context;
    Intent intent;
    DatabaseReference databaseReference;
    StorageReference anhmc;
    private Uri imgUri;
    private static final int CHOOSE_IMAGE = 1;
    ProgressBar pgmc;
    ProgressDialog dialog;
    List<ModelImageCuaHANG> mc1;
    StorageTask mUploadTasks;
    RecyclerView listdodulieu;
    AdapterIntroCuaHang adapterIntroCuaHang;
    public static final String ID = "ID";
    public static final String IMAGEFULL = "image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_store_mc);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mc1 = new ArrayList<>();
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
        pgmc = findViewById(R.id.upLoadImag1e);
        tench = intent.getStringExtra(MainActivity.TENCH);
        diachi = intent.getStringExtra(MainActivity.ADDRESS);
        timeopen = intent.getStringExtra(MainActivity.TIMEOPENEND);
        sodt = intent.getStringExtra(MainActivity.SODIENTHOAI);
        fb = intent.getStringExtra(MainActivity.FACEBOOK_CH);
        createby = intent.getStringExtra(MainActivity.CREATE_BY);
        danhmuc = intent.getStringExtra(MainActivity.DANHMUC);
        ship = intent.getStringExtra(MainActivity.SHIPTINHTRANG);
        image = intent.getStringExtra(MainActivity.IMAGE);
        databaseReference = FirebaseDatabase.getInstance().getReference("ImageAlbum").child(intent.getStringExtra(MainActivity.ID));
        anhmc = FirebaseStorage.getInstance().getReference("CuaHang/DanhSachCuaHangc");
        imgCH = findViewById(R.id.iv_detail);
        dialog = new ProgressDialog(AboutStoreMC.this);
        create1 = findViewById(R.id.txtCreateBy1);
        danhmuc1 = findViewById(R.id.txtDanhMuc2);
        opentime1 = findViewById(R.id.txttimeOpenEnd2);
        sdt1 = findViewById(R.id.txtSDTMC);
        facebook1 = findViewById(R.id.txfFacebook);
        tinhtrangship1 = findViewById(R.id.txtTinhTrangShipDo);
        diachifix = findViewById(R.id.txtDiachi1);
        dialog.setTitle("Xin vui lòng chờ upload");
        setTitle("Thông tin cửa hàng");
        diachifix.setText("Địa Chỉ" + diachi);
        opentime1.setText("Time:" + timeopen);
        sdt1.setText("SĐT:"+sodt);
        facebook1.setText("Fanpage: "+fb);
        create1.setText("Mail Create: "+createby);
        danhmuc1.setText("Danh Mục: "+danhmuc);
        tinhtrangship1.setText("Tình Trạng Giao Hàng: " + ship);
        Glide.with(this).load(image).into(imgCH);
        imgHienThiUpload = findViewById(R.id.imgHienThiCacAnh);
        themhinhanh = findViewById(R.id.btnChooseImage);
        imgThemAnhToanBo = findViewById(R.id.imgUpload2);
        themhinhanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFile();
            }
        });
        imgThemAnhToanBo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTasks != null && mUploadTasks.isInProgress()){
                    Toast.makeText(AboutStoreMC.this, "Đang xử lý quá trình", Toast.LENGTH_SHORT).show();
                }else {
                    UploadImage();
                }
            }
        });
        listdodulieu = findViewById(R.id.lvDanhSachChon);
        adapterIntroCuaHang = new AdapterIntroCuaHang(AboutStoreMC.this,mc1);
        listdodulieu.setAdapter(adapterIntroCuaHang);
        listdodulieu.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
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
    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    private void UploadImage(){
        if (imgUri != null){
            final StorageReference mc1 = anhmc.child(System.currentTimeMillis()+"."+ getFileExtension((imgUri)));
            mc1.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pgmc.setProgress(0);
                        }
                    },500);
                    Toast.makeText(AboutStoreMC.this, "Updated Complete", Toast.LENGTH_SHORT).show();
                    taskSnapshot.getUploadSessionUri().toString();
                    image3 = mc1.toString();
                    mc1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            ModelImageCuaHANG modelImageCuaHANG = new ModelImageCuaHANG(id,image3);
                            modelImageCuaHANG.setImage(uri.toString());
                            id = databaseReference.push().getKey();
                            modelImageCuaHANG.setId(id);
                            databaseReference.child(id).setValue(modelImageCuaHANG);
                        }
                    });

                }
                //atimeover
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AboutStoreMC.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    double process = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    pgmc.setProgress((int) process);
                    createDialog();
                }
            });
        }else {
            Toast.makeText(this, "Not file update image", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mc1.clear();
                for (DataSnapshot postsnap:dataSnapshot.getChildren()){
                    ModelImageCuaHANG modelImageCuaHANG = postsnap.getValue(ModelImageCuaHANG.class);
                    mc1.add(modelImageCuaHANG);
                    adapterIntroCuaHang.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void createDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dialog_loadingup, null);
        final android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(this);
        alert.setView(alertLayout);
        alert.setCancelable(false);
        final AlertDialog dialog = alert.create();
        dialog.show();
        final Handler handler  = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        };

        alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                handler.removeCallbacks(runnable);
            }
        });

        handler.postDelayed(runnable, 3500);
    }
}
