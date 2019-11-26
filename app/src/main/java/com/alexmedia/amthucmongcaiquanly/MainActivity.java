package com.alexmedia.amthucmongcaiquanly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.alexmedia.amthucmongcaiquanly.Activity.AboutStoreMC;
import com.alexmedia.amthucmongcaiquanly.Activity.AccountLogin;
import com.alexmedia.amthucmongcaiquanly.Activity.DangBaiCuaHang;
import com.alexmedia.amthucmongcaiquanly.Activity.MapTestMC;
import com.alexmedia.amthucmongcaiquanly.Activity.MenuTongHop;
import com.alexmedia.amthucmongcaiquanly.Adapter.AdapterCuaHang;
import com.alexmedia.amthucmongcaiquanly.Model.DangBaiModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView accountFacebook;
    Button btnLog2;
    ImageView btnPlus, btnThemCuaHang, btnKiemTraApp;
    ListView lvDsCuaHang;
    List<DangBaiModel> baiModelList;
    FirebaseAuth auth;
    DatabaseReference databaseReference,imagecuahang,datacomment;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    AdapterCuaHang adapterCuaHang;
    ProgressBar loadingimage;
    public static final String ID = "id";
    public static final String TENCH = "tench";
    public static final String ADDRESS = "diachi";
    public static final String TIMEOPENEND = "time";
    public static final String SODIENTHOAI = "thoigian";
    public static final String SHIPTINHTRANG = "tinhtrangship";
    public static final String FACEBOOK_CH = "facebook";
    public static final String DANHMUC = "tench";
    public static final String CREATE_BY = "create";
    public static final String IMAGE = "image";
    public static final String DATE_CREATE = "timecreate";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    private static final int CHOOSE_IMAGE = 1;
    String amPm;
    private Uri imgUri;
    Context context;
    Intent intent;
    Calendar clc;
    int curHour, curMinute;
    TimePickerDialog timedialog;
    DatePickerDialog pdialog;
    Double lat1,long12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = getIntent();
        auth = FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        accountFacebook = findViewById(R.id.txtAccountFacebook);
        btnThemCuaHang = findViewById(R.id.imgThemCuaHang);
        btnKiemTraApp = findViewById(R.id.imgKiemTra);
        btnKiemTraApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MapTestMC.class));
            }
        });
        btnPlus = findViewById(R.id.clickplus);
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MenuTongHop.class));
            }
        });
        loadingimage = findViewById(R.id.pro_clice);
        accountFacebook.setText("Tài Khoản:" + user.getEmail());
        accountFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AccountLogin.class));
            }
        });
        btnThemCuaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DangBaiCuaHang.class));
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
                for (DataSnapshot list : dataSnapshot.getChildren()) {
                    DangBaiModel dangBaiModel = list.getValue(DangBaiModel.class);
                    baiModelList.add(dangBaiModel);
                    adapterCuaHang = new AdapterCuaHang(getApplicationContext(), R.layout.adapterquanly, baiModelList);
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
                Intent intent = new Intent(getApplicationContext(), AboutStoreMC.class);
                intent.putExtra(ID, baiModel.getId());
                intent.putExtra(TENCH, baiModel.getTench());
                intent.putExtra(ADDRESS, baiModel.getDiachi());
                intent.putExtra(TIMEOPENEND, baiModel.getThoigian());
                intent.putExtra(SODIENTHOAI, baiModel.getSodt());
                intent.putExtra(SHIPTINHTRANG, baiModel.getTinhtrangship());
                intent.putExtra(FACEBOOK_CH, baiModel.getFacebook());
                intent.putExtra(DANHMUC, baiModel.getDanhmuc());
                intent.putExtra(CREATE_BY, baiModel.getCreate());
                intent.putExtra(IMAGE, baiModel.getImage());
                intent.putExtra(DATE_CREATE,baiModel.getTimecreate());
                intent.putExtra(LATITUDE,baiModel.getLatitude());
                intent.putExtra(LONGITUDE,baiModel.getLongitude());
                startActivity(intent);
            }
        });
        lvDsCuaHang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DangBaiModel model = baiModelList.get(position);
                showUpdateDeleteDialog(model.getId(), model.getTench(), model.getDiachi(), model.getThoigian(), model.getSodt(), model.getTinhtrangship(),
                        model.getDanhmuc(), model.getFacebook(), model.getTimecreate(), model.getImage(), model.getLatitude(), model.getLongitude(), model.getCreate());
                intent.putExtra(ID, model.getId());
                intent.putExtra(TENCH, model.getTench());
                intent.putExtra(ADDRESS, model.getDiachi());
                intent.putExtra(TIMEOPENEND, model.getThoigian());
                intent.putExtra(SODIENTHOAI, model.getSodt());
                intent.putExtra(SHIPTINHTRANG, model.getTinhtrangship());
                intent.putExtra(FACEBOOK_CH, model.getFacebook());
                intent.putExtra(DANHMUC, model.getDanhmuc());
                intent.putExtra(CREATE_BY,model.getCreate());
                intent.putExtra(IMAGE, model.getImage());
                intent.putExtra(DATE_CREATE,model.getTimecreate());
                intent.putExtra(LATITUDE,model.getLatitude());
                intent.putExtra(LONGITUDE,model.getLongitude());
                return false;
            }
        });
    }

    private void showUpdateDeleteDialog(final String id, final String cuahang, final String diachi,
                                        final String tongthoigian,
                                        final String sodt, final String shipdoan, final String doansang, final String facebook,
                                        final String ngaydang, final String image, final Double latitude, final Double longitude, final String accountnguoidang) {
        AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialogfixbai, null);
        dialogbuilder.setView(dialogView);
        final EditText edtTenCh = dialogView.findViewById(R.id.edtTenCuaHang12);
        final EditText edtDiaChi = dialogView.findViewById(R.id.edtDiachi12);
        final EditText edtSodt = dialogView.findViewById(R.id.edtSoDienThoai12);
        final EditText edtThoiGianMo = dialogView.findViewById(R.id.edtTimeOpen12);
        final EditText edtThoiGianDong = dialogView.findViewById(R.id.edtTimeClose12);
        final Spinner spnTinhTrang = dialogView.findViewById(R.id.spnChonShip12);
        final Spinner spnDanhMuc = dialogView.findViewById(R.id.spnChonDanhMuc12);
        final EditText edtfacebookch = dialogView.findViewById(R.id.edtFacebook12);
        final EditText edtngaydang = dialogView.findViewById(R.id.edtNgayDang12);
        final EditText edtLatitude = dialogView.findViewById(R.id.edtlatitude12);
        final EditText edtLongitude = dialogView.findViewById(R.id.edtlongitude12);
        final Button updateID = dialogView.findViewById(R.id.buttonUpdateArtist);
        final Button deleteID = dialogView.findViewById(R.id.buttonDeleteArtist);
        final TextView img = dialogView.findViewById(R.id.tcc);
        img.setText("");
        ArrayAdapter<CharSequence> charSequenceArrayAdapter = ArrayAdapter.createFromResource(this, R.array.shipdo, android.R.layout.select_dialog_item);
        charSequenceArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTinhTrang.setAdapter(charSequenceArrayAdapter);
        ArrayAdapter<CharSequence> charSequenceArrayAdapter2 = ArrayAdapter.createFromResource(this, R.array.danhmuc, android.R.layout.select_dialog_item);
        charSequenceArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnDanhMuc.setAdapter(charSequenceArrayAdapter2);
        Double lat1 = latitude;
        Double long1 = longitude;
        dialogbuilder.setTitle("Sửa Chữa Cửa Hàng");
        edtTenCh.setText(cuahang);
        edtDiaChi.setText(diachi);
        edtSodt.setText(sodt);
        edtfacebookch.setText(facebook);
        edtngaydang.setText(ngaydang);
        edtLatitude.setText(String.valueOf(lat1));
        edtLongitude.setText(String.valueOf(long1));
        final AlertDialog b = dialogbuilder.create();
        edtThoiGianMo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clc = Calendar.getInstance();
                curHour = clc.get(Calendar.HOUR_OF_DAY);
                curMinute = clc.get(Calendar.MINUTE);
                timedialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
        edtThoiGianDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clc = Calendar.getInstance();
                curHour = clc.get(Calendar.HOUR_OF_DAY);
                curMinute = clc.get(Calendar.MINUTE);
                timedialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
        edtThoiGianDong.setInputType(InputType.TYPE_NULL);
        edtThoiGianMo.setInputType(InputType.TYPE_NULL);
        edtngaydang.setInputType(InputType.TYPE_NULL);
        edtngaydang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                pdialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                edtngaydang.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                pdialog.show();
            }
        });
        b.show();
        updateID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tench = edtTenCh.getText().toString();
                String diachi = edtDiaChi.getText().toString();
                String thoigianmo = edtThoiGianMo.getText().toString();
                String thoigiandong = edtThoiGianDong.getText().toString();
                String tongthoigian = thoigianmo + " - " +thoigiandong;
                String sodt  = edtSodt.getText().toString();
                String spnChonTinhTrangShip = spnTinhTrang.getSelectedItem().toString();
                String spnDanhMuc1 = spnDanhMuc.getSelectedItem().toString();
                String facebook = edtfacebookch.getText().toString();
                String ngaydang = edtngaydang.getText().toString();
                double latitude = Double.parseDouble(edtLatitude.getText().toString());
                double longitude = Double.parseDouble(edtLongitude.getText().toString());
                String accountnguoidang = accountFacebook.getText().toString();
                if (tench.isEmpty() || diachi.isEmpty() || thoigianmo.isEmpty() || thoigiandong.isEmpty() || sodt.isEmpty() || facebook.isEmpty()||ngaydang.isEmpty()){
                    Toast.makeText(MainActivity.this, "Yêu cầu nhập đầy đủ", Toast.LENGTH_SHORT).show();
                }
                else {
                    updateArtist(id,tench,diachi,tongthoigian,sodt,spnChonTinhTrangShip,spnDanhMuc1,facebook,ngaydang,image,latitude,longitude,accountnguoidang);
                    b.dismiss();
                }
            }
        });
        deleteID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteArtist(id);
                b.dismiss();
            }
        });
    }
    private boolean updateArtist(String id,String cuahang,String diachi, String tongthoigian, String sodt,String shipdoan,String doansang,String facebook,
                                 String ngaydang,String image,double latitude,double longitude,String accountnguoidang) {
        //getting the specified artist reference
        databaseReference = FirebaseDatabase.getInstance().getReference("CuaHang/DanhSachCuaHang").child(id);

        //updating artist
        DangBaiModel artist = new DangBaiModel(id, cuahang, diachi,tongthoigian,shipdoan,facebook,accountnguoidang,ngaydang,image,latitude,longitude,doansang,sodt);
        databaseReference.setValue(artist);
        Toast.makeText(getApplicationContext(), "Đã thay đổi cửa hàng", Toast.LENGTH_LONG).show();
        return true;
    }
    private boolean deleteArtist(String id) {
        //getting the specified artist reference
        databaseReference = FirebaseDatabase.getInstance().getReference("CuaHang/DanhSachCuaHang").child(id);
        imagecuahang = FirebaseDatabase.getInstance().getReference("ImageAlbum").child(id);
        datacomment = FirebaseDatabase.getInstance().getReference("CommentBaiViet").child(id);
        //removing artist
        databaseReference.removeValue();
        imagecuahang.removeValue();
        datacomment.removeValue();
        Toast.makeText(getApplicationContext(), "Đã xóa thành công", Toast.LENGTH_LONG).show();
        return true;
    }
}
