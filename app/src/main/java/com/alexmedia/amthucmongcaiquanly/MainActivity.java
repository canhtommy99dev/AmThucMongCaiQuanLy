package com.alexmedia.amthucmongcaiquanly;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView accountFacebook;
    Button btnLog2;
    ImageView btnLogout,btnThemCuaHang,btnKiemTraApp;
    ListView lvDsCuaHang;
    List<DangBaiModel> baiModelList;
    FirebaseAuth auth;
    DatabaseReference databaseReference;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    AdapterCuaHang adapterCuaHang;
    ProgressBar loadingimage;
    public static final String ID ="id";
    public static final String TENCH = "tench";
    public static final String ADDRESS = "diachi";
    public static final String TIMEOPENEND = "time";
    public static final String SODIENTHOAI = "thoigian";
    public static final String SHIPTINHTRANG = "tinhtrangship";
    public static final String FACEBOOK_CH = "facebook";
    public static final String DANHMUC = "tench";
    public static final String CREATE_BY = "create";
    public static final String IMAGE = "image";

    private Uri imgUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        accountFacebook = findViewById(R.id.txtAccountFacebook);
        btnLog2= findViewById(R.id.imgLogout34);
        btnThemCuaHang = findViewById(R.id.imgThemCuaHang);
        btnKiemTraApp = findViewById(R.id.imgKiemTra);
        btnKiemTraApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MapKiemTra.class));
            }
        });
        loadingimage = findViewById(R.id.pro_clice);
        accountFacebook.setText("Tài Khoản:" + user.getEmail());
        btnLog2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                startActivity(new Intent(getApplicationContext(),DangNhapFB.class));
                finish();
            }
        });
        btnThemCuaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),DangBaiCuaHang.class));
            }
        });
        lvDsCuaHang = findViewById(R.id.listviewdscuahang);
        baiModelList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("CuaHang/DanhSachCuaHang");
        final StorageReference storageRef = storage.getReferenceFromUrl("gs://amthucmongcai.appspot.com/CuaHang/DanhSachCuaHang");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                baiModelList.clear();
                for (DataSnapshot list:dataSnapshot.getChildren()){
                    DangBaiModel dangBaiModel = list.getValue(DangBaiModel.class);
                    baiModelList.add(dangBaiModel);
                    adapterCuaHang = new AdapterCuaHang(getApplicationContext(),R.layout.adapterquanly,baiModelList);
                    lvDsCuaHang.setAdapter(adapterCuaHang);
                    adapterCuaHang.notifyDataSetChanged();
                    loadingimage.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        lvDsCuaHang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DangBaiModel baiModel = baiModelList.get(i);
                Intent intent = new Intent(getApplicationContext(),AboutStoreMC.class);
                intent.putExtra(ID,baiModel.getId());
                intent.putExtra(TENCH, baiModel.getTench());
                intent.putExtra(ADDRESS, baiModel.getDiachi());
                intent.putExtra(TIMEOPENEND, baiModel.getThoigian());
                intent.putExtra(SODIENTHOAI, baiModel.getSodt());
                intent.putExtra(SHIPTINHTRANG, baiModel.getTinhtrangship());
                intent.putExtra(FACEBOOK_CH, baiModel.getFacebook());
                intent.putExtra(DANHMUC, baiModel.getDanhmuc());
                intent.putExtra(CREATE_BY, baiModel.getCreate());
                intent.putExtra(IMAGE, baiModel.getImage());
                startActivity(intent);
            }
        });
        lvDsCuaHang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DangBaiModel dangBaiModel = baiModelList.get(position);
                Intent intent = new Intent(getApplicationContext(),DangCHMongCai.class);
                intent.putExtra(ID,dangBaiModel.getId());
                intent.putExtra(TENCH, dangBaiModel.getTench());
                intent.putExtra(ADDRESS, dangBaiModel.getDiachi());
                intent.putExtra(TIMEOPENEND, dangBaiModel.getThoigian());
                intent.putExtra(SODIENTHOAI, dangBaiModel.getSodt());
                intent.putExtra(SHIPTINHTRANG, dangBaiModel.getTinhtrangship());
                intent.putExtra(FACEBOOK_CH, dangBaiModel.getFacebook());
                intent.putExtra(DANHMUC, dangBaiModel.getDanhmuc());
                intent.putExtra(CREATE_BY, dangBaiModel.getCreate());
                intent.putExtra(IMAGE, dangBaiModel.getImage());
                startActivity(intent);
                return false;
            }
        });
    }
}
