package com.alexmedia.amthucmongcaiquanly;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class DangBaiCuaHang extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    FirebaseAuth auth;
    TextView dangbai, account,txtLat,txtLong;
    DatabaseReference dataQuanly;
    StorageReference anhdulieu;
    FirebaseStorage storage;
    StorageTask mUploadTask;
    EditText edtTenCH, edtDiaChi, edtThoiGianMo, edtThoiGianDong, edtfacebookch, edtngaydang, latiture1, longitude1, edtSdt;
    String key_id, tenCh, DiaChi, thoigianmo, thoigiandong, sodt, tongthoigian, facebookcuahang, nguoidang, shiphoatdong, ngaydangbai, spnDanhMuc1, amPm,image,latiture, longitude;
    Double lang1,long1;
    Button chonanh,uplen;
    DatePickerDialog pdialog;
    ImageView quaylai, themvao, imgHienThi;
    Spinner spnShip, spnDanhMuc;
    private static final int CHOOSE_IMAGE = 1;
    private Uri imgUri;
    ProgressBar pgmc;
    Calendar clc;
    int curHour, curMinute;
    TimePickerDialog timedialog;
    ////demxLocation
    Context context = this;
    private FusedLocationProviderClient mFusedLocationClient;
    private static final String TAG = "MyFirebaseService";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_bai_cua_hang);
        pgmc = findViewById(R.id.upLoadImage);
        dataQuanly = FirebaseDatabase.getInstance().getReference("CuaHang/DanhSachCuaHang");
        anhdulieu = FirebaseStorage.getInstance().getReference("CuaHang/DanhSachCuaHang");
        auth = FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        edtSdt = findViewById(R.id.edtSoDienThoai);
        account = findViewById(R.id.txtAccountemail);
        account.setText(user.getEmail());
        quaylai = findViewById(R.id.thoiquaylai);
        edtTenCH = findViewById(R.id.edtTenCuaHang);
        edtDiaChi = findViewById(R.id.edtDiachi);
        edtThoiGianMo = findViewById(R.id.edtTimeOpen);
        edtThoiGianDong = findViewById(R.id.edtTimeClose);
        edtngaydang = findViewById(R.id.edtNgayDang);
        latiture1 = findViewById(R.id.edtlatitude);
        longitude1 = findViewById(R.id.edtlongitude);
        chonanh = findViewById(R.id.btnChonHinhAnh);
        edtTenCH = findViewById(R.id.edtTenCuaHang);
        imgHienThi = findViewById(R.id.imgHienThi);
        edtfacebookch = findViewById(R.id.edtFacebook);
        quaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        chonanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowFile();
            }
        });
        spnShip = findViewById(R.id.spnChonShip);
        ArrayAdapter<CharSequence> arrayAdapter1 = ArrayAdapter.createFromResource(this, R.array.shipdo, android.R.layout.select_dialog_item);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnShip.setAdapter(arrayAdapter1);
        spnShip.setOnItemSelectedListener(this);
        edtngaydang.setInputType(InputType.TYPE_NULL);
        edtngaydang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                pdialog = new DatePickerDialog(DangBaiCuaHang.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                edtngaydang.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                pdialog.show();
            }
        });
        spnDanhMuc = findViewById(R.id.spnChonDanhMuc);
        ArrayAdapter<CharSequence> arrayAdapter2 = ArrayAdapter.createFromResource(this, R.array.danhmuc, android.R.layout.select_dialog_item);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnDanhMuc.setAdapter(arrayAdapter2);
        spnDanhMuc.setOnItemSelectedListener(this);
        themvao = findViewById(R.id.themvaocuahang);
        themvao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(DangBaiCuaHang.this, "Đang up để xử lý", Toast.LENGTH_SHORT).show();
                }
                else{
                    uploadImage();
                }
            }
        });
        edtThoiGianMo.setInputType(InputType.TYPE_NULL);
        edtThoiGianMo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clc = Calendar.getInstance();
                curHour = clc.get(Calendar.HOUR_OF_DAY);
                curMinute = clc.get(Calendar.MINUTE);
                timedialog = new TimePickerDialog(DangBaiCuaHang.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        if (i >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        edtThoiGianMo.setText(String.format("%2d:%2d", i, i1) + amPm);
                    }
                }, curHour, curMinute, false);
                timedialog.show();
            }
        });
        edtThoiGianDong.setInputType(InputType.TYPE_NULL);
        edtThoiGianDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clc = Calendar.getInstance();
                curHour = clc.get(Calendar.HOUR_OF_DAY);
                curMinute = clc.get(Calendar.MINUTE);
                timedialog = new TimePickerDialog(DangBaiCuaHang.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        if (i >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        edtThoiGianDong.setText(String.format("%2d:%2d", i, i1) + amPm);
                    }
                }, curHour, curMinute, false);
                timedialog.show();
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
                imgHienThi.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage() {
        if (imgUri != null) {
            final StorageReference fileRes = anhdulieu.child(System.currentTimeMillis() + "." + getFileExtension((imgUri)));
            fileRes.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pgmc.setProgress(0);
                            createDialog();
                        }
                    }, 500);
                    Toast.makeText(DangBaiCuaHang.this, "Đã Up ảnh lên thành công", Toast.LENGTH_SHORT).show();
                    tenCh = edtTenCH.getText().toString();
                    DiaChi = edtDiaChi.getText().toString();
                    ///--> thoi gian mo va dong
                    thoigianmo = edtThoiGianMo.getText().toString();
                    thoigiandong = edtThoiGianDong.getText().toString();
                    ///--> tong thoi gian
                    tongthoigian = thoigianmo + "---" + thoigiandong;
                    sodt = edtSdt.getText().toString();
                    shiphoatdong = spnShip.getSelectedItem().toString();
                    image = fileRes.toString();
                    spnDanhMuc1 = spnDanhMuc.getSelectedItem().toString();
                    facebookcuahang = edtfacebookch.getText().toString();
                    ngaydangbai = edtngaydang.getText().toString();
                    latiture = latiture1.getText().toString();
                    longitude = longitude1.getText().toString();
                    lang1 = Double.valueOf(latiture);
                    long1 =  Double.valueOf(longitude);
//                    latiture = Double.parseDouble(latiture1.getText().toString());
//                    longitude = Double.parseDouble(longitude1.getText().toString());
                    nguoidang = account.getText().toString();
                    taskSnapshot.getUploadSessionUri().toString();

                    if (tenCh.isEmpty() || DiaChi.isEmpty() || thoigianmo.isEmpty() || thoigiandong.isEmpty() ||sodt.isEmpty()||
                    shiphoatdong.isEmpty() || facebookcuahang.isEmpty() || ngaydangbai.isEmpty() || latiture.isEmpty() || longitude.isEmpty()){
                        Toast.makeText(DangBaiCuaHang.this, "Nhập cái vẫn còn trống ...!!!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        fileRes.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                DangBaiModel dangBaiModel = new DangBaiModel(
                                        key_id,tenCh,DiaChi,tongthoigian,shiphoatdong,facebookcuahang,nguoidang,ngaydangbai,image,lang1,long1,spnDanhMuc1,sodt
                                );
                                dangBaiModel.setImage(uri.toString());
                                key_id = dataQuanly.push().getKey();
                                dangBaiModel.setId(key_id);
                                dataQuanly.child(key_id).setValue(dangBaiModel);
                                finish();
                            }
                        });
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(DangBaiCuaHang.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    double process = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    pgmc.setProgress((int) process);
                    createDialog();
                }
            });
        } else {
            Toast.makeText(this, "Không file để up & nhập đầy đủ", Toast.LENGTH_SHORT).show();
        }
    }
    ///loadapp
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
