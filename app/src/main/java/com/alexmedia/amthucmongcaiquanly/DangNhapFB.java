package com.alexmedia.amthucmongcaiquanly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alexmedia.amthucmongcaiquanly.Activity.RegisterEmail;
import com.alexmedia.amthucmongcaiquanly.Activity.ResetPassword;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DangNhapFB extends AppCompatActivity {
    Animation aniFad,Zdo;
    ImageView imgIntro,imClick;
    FirebaseAuth firebaseAuth;
    EditText emailE,passE;
    TextView clickreg,clickForget;
    ProgressDialog dialog;
    CheckBox mCheckRemember;
    SharedPreferences mPrefs;
    private static final String PREFS_NAME = "PrefsFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_facebook);
        if (checkNetwork()){

        }else if (!checkNetwork())
        {
            createDialog();
        }
        imgIntro = findViewById(R.id.imgLogo2);
        aniFad = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_in);
        Zdo = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.float_in);
        imgIntro.startAnimation(aniFad);
        mPrefs = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        emailE = findViewById(R.id.edtEmail);
        passE = findViewById(R.id.edtPassword);
        mCheckRemember = findViewById(R.id.chbxmmm1);
        firebaseAuth = FirebaseAuth.getInstance();
        imClick = findViewById(R.id.imgClickLogin);
        imClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new ProgressDialog(DangNhapFB.this);
                dialog.setMessage("Xin vui lòng chờ đăng nhập");
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                final String email = emailE.getText().toString();
                final String password = passE.getText().toString();
                if (TextUtils.isEmpty(email)){
                    Toast.makeText(DangNhapFB.this, "Yêu cầu nhập account email đã đăng ký ...!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(DangNhapFB.this, "Yêu cầu nhập password đã đăng ký...!!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(DangNhapFB.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()){
                            if (password.length() < 6){
                                passE.setError("Mật khẩu không quá ít 6 ký tự...!!!");
                                dialog.dismiss();
                            }else {
                                Toast.makeText(DangNhapFB.this, "Lỗi đăng nhập,yêu cầu kiểm tra lại email và mật khẩu khi đăng ký...!!!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }else {
                            if (mCheckRemember.isChecked()){
                                Boolean booleanChecked = mCheckRemember.isChecked();
                                SharedPreferences.Editor editor = mPrefs.edit();
                                editor.putString("pref_name",emailE.getText().toString());
                                editor.putString("pref_pass",passE.getText().toString());
                                editor.putBoolean("pref_check",booleanChecked);
                                editor.apply();
                                Toast.makeText(DangNhapFB.this, "Lưu lại tài khoản mật khẩu", Toast.LENGTH_SHORT).show();
                            }else {
                                mPrefs.edit().clear().apply();
                            }
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }
                        emailE.getText().clear();
                        passE.getText().clear();
                    }
                });
            }
        });
        clickreg = findViewById(R.id.txtClickReg);
        clickreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterEmail.class));
            }
        });
        clickForget = findViewById(R.id.txtForget);
        clickForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ResetPassword.class));
            }
        });
        emailE.startAnimation(Zdo);
        passE.startAnimation(Zdo);
        imClick.startAnimation(Zdo);
        clickForget.startAnimation(Zdo);
        clickreg.startAnimation(Zdo);
        getPrferencesData();
    }
    private boolean checkNetwork(){
        boolean wifiConnect = false;
        boolean mobileConnected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connectivityManager.getActiveNetworkInfo();
        if (activeInfo != null && activeInfo.isConnected()){
            wifiConnect = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
            mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            if (wifiConnect){
                wifiConnect = true;
                Toast.makeText(this, "Kết nối mạng wifi", Toast.LENGTH_SHORT).show();
            }else if (mobileConnected){
                mobileConnected = true;
                Toast.makeText(this, "Kết nối mạng 3G/4G", Toast.LENGTH_SHORT).show();
            }
        }
        return wifiConnect||mobileConnected;
    }
    public void createDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dialogerror, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(alertLayout);
        alert.setCancelable(false);
        Button btnExitConnect = alertLayout.findViewById(R.id.btnExit);
        btnExitConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }
    private void getPrferencesData(){
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        if (sharedPreferences.contains("pref_name")){
            String u = sharedPreferences.getString("pref_name","not_found");
            emailE.setText(u.toString());
        }
        if (sharedPreferences.contains("pref_pass")){
            String p = sharedPreferences.getString("pref_pass","not_found");
            passE.setText(p.toString());
        }
        if (sharedPreferences.contains("pref_check")){
            Boolean b = sharedPreferences.getBoolean("pref_check",false);
            mCheckRemember.setChecked(b);
        }
    }
}
