package com.alexmedia.amthucmongcaiquanly.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentResolver;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alexmedia.amthucmongcaiquanly.Model.ModelDangBaiDuLich;
import com.alexmedia.amthucmongcaiquanly.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class ThemDuLich extends AppCompatActivity {

    ImageView btnClickBack,btnMamual,clickImage;
    EditText edtTenDuLich,edtStreetView,edtYoutube,edtBaiViet;
    String key_id,tendulich,streetview,youtube,baiviet,image66;
    DatabaseReference dataDuLich;
    StorageReference anhdulieu1;
    private static final int CHOOSE_IMAGE = 1;
    private Uri imgUri;
    Button btnUpLoad,btnDongLai;
    ProgressBar prog1;
    StorageTask mUploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_du_lich);
        anhdulieu1 = FirebaseStorage.getInstance().getReference("CuaHang/DanhSachCuaHang");
        dataDuLich = FirebaseDatabase.getInstance().getReference("DuLich");
        btnClickBack = findViewById(R.id.btnBack155);
        btnClickBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnMamual = findViewById(R.id.androidMamual);
        edtTenDuLich = findViewById(R.id.edtTenDuLich);
        edtStreetView = findViewById(R.id.edtLinkStreetView);
        edtYoutube= findViewById(R.id.edtlinkyoutube);
        edtBaiViet= findViewById(R.id.edtGioiThieu);
        btnUpLoad = findViewById(R.id.buttonThemDuLich);
        clickImage = findViewById(R.id.imgScreen);
        prog1 = findViewById(R.id.upLoadImage1);
        clickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFile();
            }
        });
        btnUpLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(ThemDuLich.this, "Đang up để xử lý", Toast.LENGTH_SHORT).show();
                } else {
                    uploadImage();
                }
            }
        });
        btnMamual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog1();
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
                clickImage.setImageBitmap(bitmap);
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
    private void uploadImage() {
        if (imgUri != null ){
            final StorageReference fileres = anhdulieu1.child(System.currentTimeMillis()+"."+getFileExtension(imgUri));
            fileres.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            prog1.setProgress(0);
                        }
                    },500);
                    Toast.makeText(ThemDuLich.this, "Đã xử lý thành công", Toast.LENGTH_SHORT).show();
                    tendulich = edtTenDuLich.getText().toString();
                    streetview = edtStreetView.getText().toString();
                    youtube = edtYoutube.getText().toString();
                    baiviet = edtBaiViet.getText().toString();
                    image66 = fileres.toString();
                    taskSnapshot.getUploadSessionUri().toString();
                    if (tendulich.isEmpty() || streetview.isEmpty() || youtube.isEmpty()|| baiviet.isEmpty()){
                        Toast.makeText(ThemDuLich.this, "Nhập đúng theo yêu cầu vào", Toast.LENGTH_SHORT).show();
                    }else {
                        fileres.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                ModelDangBaiDuLich dangBaiDuLich = new ModelDangBaiDuLich(
                                        key_id,tendulich,streetview,youtube,baiviet,image66
                                );
                                dangBaiDuLich.setImagedulich(uri.toString());
                                key_id = dataDuLich.push().getKey();
                                dangBaiDuLich.setId(key_id);
                                dataDuLich.child(key_id).setValue(dangBaiDuLich);
                                finish();
                            }
                        });
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ThemDuLich.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    double process = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    prog1.setProgress((int) process);
                    createDialog();
                }
            });
        }else {
            Toast.makeText(this, "Không file để up & nhập đầy đủ", Toast.LENGTH_SHORT).show();
        }
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
    }
    public void createDialog1() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dialog_hdsdung, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(alertLayout);
        alert.setCancelable(false);
        btnDongLai = alertLayout.findViewById(R.id.donglaidialog);
        final AlertDialog dialog = alert.create();
        dialog.show();
        btnDongLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
