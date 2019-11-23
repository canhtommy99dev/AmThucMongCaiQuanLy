package com.alexmedia.amthucmongcaiquanly.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.InputType;
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

import com.alexmedia.amthucmongcaiquanly.MainActivity;
import com.alexmedia.amthucmongcaiquanly.Model.DangBaiModel;
import com.alexmedia.amthucmongcaiquanly.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;

public class DangCHMongCai extends AppCompatActivity implements AdapterView.OnItemClickListener {
    FirebaseAuth auth;
    TextView dangbai, account,txtLat,txtLong;
    DatabaseReference dataQuanly;
    StorageReference anhdulieu;
    FirebaseStorage storage;
    StorageTask mUploadTask;
    EditText edtTenCH1, edtDiaChi1, edtThoiGianMo1, edtThoiGianDong1, edtfacebookch1, edtngaydang1, latiture2, longitude2, edtSdt1;
    String id,tenCh, DiaChi, thoigianmo, thoigiandong, sodt, tongthoigian, facebookcuahang, nguoidang, shiphoatdong, ngaydangbai, spnDanhMuc, amPm,image;
    Double latiture, longitude;
    Button btnupdate,btnDelete,btnthemanh;
    DatePickerDialog pdialog;
    ImageView imgBack, imgHienThi1;
    Spinner spnShip1, spnDanhMuc1;
    private static final int CHOOSE_IMAGE = 1;
    private Uri imgUri;
    ProgressBar pgmc;
    Calendar clc;
    int curHour, curMinute;
    TimePickerDialog timedialog;
    Context context;
    Intent intent;
    ////demxLocation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_chmong_cai);
        //themhamfirebase
        intent = getIntent();
        dataQuanly = FirebaseDatabase.getInstance().getReference("CuaHang/DanhSachCuaHang").child(intent.getStringExtra(MainActivity.ID));
        anhdulieu = FirebaseStorage.getInstance().getReference("CuaHang/DanhSachCuaHang");
        auth = FirebaseAuth.getInstance();
        //nhap layout
        imgBack  = findViewById(R.id.imgBack1);
        account = findViewById(R.id.txtAccountGet);
        edtTenCH1 = findViewById(R.id.edtTenCuaHang1);
        edtDiaChi1 = findViewById(R.id.edtDiachi1);
        edtThoiGianMo1 = findViewById(R.id.edtTimeOpen1);
        edtThoiGianDong1 = findViewById(R.id.edtTimeClose1);
        edtSdt1 = findViewById(R.id.edtSoDienThoai1);
        spnShip1 = findViewById(R.id.spnChonShip1);
        spnDanhMuc1 = findViewById(R.id.spnChonDanhMuc1);
        edtfacebookch1 = findViewById(R.id.edtFacebook1);
        edtngaydang1 = findViewById(R.id.edtNgayDang1);
        btnthemanh = findViewById(R.id.btnChonHinhAnh1);
        imgHienThi1 = findViewById(R.id.imgHienThi12);
        latiture2 = findViewById(R.id.edtlatitude1);
        longitude2 = findViewById(R.id.edtlongitude1);
        btnupdate = findViewById(R.id.buttonUpdateArtist);
        btnDelete = findViewById(R.id.buttonDeleteArtist);
        //sét sửa chữa firebase
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        account.setText("Tài Khoản:" + user.getEmail());
        //--->>>set string
        sodt = intent.getStringExtra(MainActivity.SODIENTHOAI);
        facebookcuahang = intent.getStringExtra(MainActivity.FACEBOOK_CH);
        DiaChi = intent.getStringExtra(MainActivity.ADDRESS);
        tenCh = intent.getStringExtra(MainActivity.TENCH);
        //settitle return
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //settext-->> from activity
        edtDiaChi1.setText(DiaChi);
        edtSdt1.setText(sodt);
        edtfacebookch1.setText(facebookcuahang);
        ///---> from spinner
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this,R.array.danhmuc,android.R.layout.select_dialog_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnDanhMuc1.setAdapter(arrayAdapter);
        ArrayAdapter<CharSequence> arrayAdapter1 = ArrayAdapter.createFromResource(this,R.array.shipdo,android.R.layout.select_dialog_item);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnShip1.setAdapter(arrayAdapter1);
        //spin set dieu khien
        edtThoiGianMo1.setInputType(InputType.TYPE_NULL);
        edtThoiGianMo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clc = Calendar.getInstance();
                curHour = clc.get(Calendar.HOUR_OF_DAY);
                curMinute = clc.get(Calendar.MINUTE);
                timedialog = new TimePickerDialog(DangCHMongCai.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        if (i >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        edtThoiGianMo1.setText(String.format("%2d:%2d", i, i1) + amPm);
                    }
                }, curHour, curMinute, false);
                timedialog.show();
            }
        });
        edtThoiGianDong1.setInputType(InputType.TYPE_NULL);
        edtThoiGianDong1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clc = Calendar.getInstance();
                curHour = clc.get(Calendar.HOUR_OF_DAY);
                curMinute = clc.get(Calendar.MINUTE);
                timedialog = new TimePickerDialog(DangCHMongCai.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        if (i >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        edtThoiGianDong1.setText(String.format("%2d:%2d", i, i1) + amPm);
                    }
                }, curHour, curMinute, false);
                timedialog.show();
            }
        });
        btnthemanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFile();
            }
        });
        edtngaydang1.setInputType(InputType.TYPE_NULL);
        edtngaydang1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                pdialog = new DatePickerDialog(DangCHMongCai.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                edtngaydang1.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                pdialog.show();
            }
        });
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
                finish();
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
        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null){
            imgUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imgUri);
                imgHienThi1.setImageBitmap(bitmap);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    private boolean deleteArtist(String id) {
        Toast.makeText(getApplicationContext(), "Đã xóa thành công", Toast.LENGTH_LONG).show();
        return true;
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
                        }
                    }, 500);
                    Toast.makeText(DangCHMongCai.this, "Đã Up ảnh lên thành công", Toast.LENGTH_SHORT).show();
                    tenCh = edtTenCH1.getText().toString();
                    DiaChi = edtDiaChi1.getText().toString();
                    ///--> thoi gian mo va dong
                    thoigianmo = edtThoiGianMo1.getText().toString();
                    thoigiandong = edtThoiGianDong1.getText().toString();
                    ///--> tong thoi gian
                    tongthoigian = thoigianmo + "---" + thoigiandong;
                    sodt = edtSdt1.getText().toString();
                    shiphoatdong = spnShip1.getSelectedItem().toString();
                    image = fileRes.toString();
                    spnDanhMuc = spnDanhMuc1.getSelectedItem().toString();
                    facebookcuahang = edtfacebookch1.getText().toString();
                    ngaydangbai = edtngaydang1.getText().toString();
                    latiture = Double.parseDouble(latiture2.getText().toString());
                    longitude = Double.parseDouble(longitude2.getText().toString());
                    nguoidang = account.getText().toString();
                    taskSnapshot.getUploadSessionUri().toString();

                    fileRes.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            DangBaiModel dangBaiModel = new DangBaiModel(
                                    id,tenCh,DiaChi,tongthoigian,shiphoatdong,facebookcuahang,nguoidang,ngaydangbai,image,latiture,longitude,spnDanhMuc,sodt
                            );
                            dangBaiModel.setImage(uri.toString());
                            dangBaiModel.setId(id);
                            dataQuanly.setValue(dangBaiModel);
                            finish();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(DangCHMongCai.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    double process = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    pgmc.setProgress((int) process);
                }
            });
        } else {
            Toast.makeText(this, "Không file để up", Toast.LENGTH_SHORT).show();
        }
    }
}