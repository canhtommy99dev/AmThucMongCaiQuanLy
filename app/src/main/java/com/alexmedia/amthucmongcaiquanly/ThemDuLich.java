package com.alexmedia.amthucmongcaiquanly;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

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
    private static final String TAG = "MyFirebaseService";
    Button btnUpLoad;
    ProgressBar prog1;
    StorageTask mUploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_du_lich);
        anhdulieu1 = FirebaseStorage.getInstance().getReference("CuaHang/DanhSachCuaHang");
        dataDuLich = FirebaseDatabase.getInstance().getReference("DuLich");
        btnClickBack = findViewById(R.id.btnBack155);
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
        if (imgUri != null){
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
        }
    }
    public void createDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dialog_loadingup, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(alertLayout);
        alert.setCancelable(false);
        AlertDialog dialog = alert.create();
        dialog.show();
    }
}
