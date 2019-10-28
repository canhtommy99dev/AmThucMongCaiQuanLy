package com.alexmedia.amthucmongcaiquanly;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
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
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AboutStoreMC extends AppCompatActivity implements RecycleViewAdapter.ItemListener{

    String tench,diachi,timeopen,sodt,fb,createby,danhmuc,ship,image;
    String id,image3;
    TextView danhmuc1,create1,diachifix,opentime1,sdt1,facebook1,tinhtrangship1;
    ImageView imgCH,imgHienThiUpload,imgThemAnhToanBo;
    Button themhinhanh;
    Context context;
    Intent intent;
    DatabaseReference databaseReference;
    FloatingActionButton fab;
    StorageReference anhmc;
    private Uri imgUri;
    private static final int CHOOSE_IMAGE = 1;
    ProgressBar pgmc;
    ProgressDialog dialog;
    List<ModelImageCuaHANG> mc1;
    AdapterImageCuaHang adapterImageCuaHang;
    StorageTask mUploadTasks;
    RecyclerView recycleViewAdapter;
    ArrayList arrayList;
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
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AboutStoreMC.this, "ccc", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.setTitle("Xin vui lòng chờ upload");
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
        recycleViewAdapter = findViewById(R.id.recyclerView);
        arrayList = new ArrayList();
        arrayList.add(new DataModel("Item 1", R.drawable.ic_android_black_24dp, "#09A9FF"));
        arrayList.add(new DataModel("Item 2", R.drawable.ic_android_black_24dp, "#3E51B1"));
        arrayList.add(new DataModel("Item 3", R.drawable.ic_android_black_24dp, "#673BB7"));
        arrayList.add(new DataModel("Item 4", R.drawable.ic_android_black_24dp, "#4BAA50"));
        arrayList.add(new DataModel("Item 5", R.drawable.ic_android_black_24dp, "#F94336"));
        arrayList.add(new DataModel("Item 6", R.drawable.ic_android_black_24dp, "#0A9B88"));
        GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recycleViewAdapter.setLayoutManager(manager);
        AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(this, 500);
        recycleViewAdapter.setLayoutManager(layoutManager);
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
                    mc1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            ModelImageCuaHANG modelImageCuaHANG = new ModelImageCuaHANG(id,image3);
                            modelImageCuaHANG.setImagegoc(uri.toString());
                            modelImageCuaHANG.setId(id);
                            id = databaseReference.push().getKey();
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
                }
            });
        }else {
            Toast.makeText(this, "Not file update image", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(DataModel item) {
        Toast.makeText(getApplicationContext(), item.text + " is clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
