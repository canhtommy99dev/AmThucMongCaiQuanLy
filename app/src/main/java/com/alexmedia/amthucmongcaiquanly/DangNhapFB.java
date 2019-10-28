package com.alexmedia.amthucmongcaiquanly;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DangNhapFB extends AppCompatActivity {
    Animation aniFad,Zdo;
    ImageView imgIntro,imClick;
    FirebaseAuth firebaseAuth;
    EditText emailE,passE;
    TextView clickreg,clickForget;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_facebook);
        imgIntro = findViewById(R.id.imgLogo2);
        aniFad = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_in);
        Zdo = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.float_in);
        imgIntro.startAnimation(aniFad);
        emailE = findViewById(R.id.edtEmail);
        passE = findViewById(R.id.edtPassword);
        emailE.setText("mongcai@gmail.com");
        passE.setText("mongcai123");
        firebaseAuth = FirebaseAuth.getInstance();
        imClick = findViewById(R.id.imgClickLogin);
        imClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new ProgressDialog(DangNhapFB.this);
                dialog.setMessage("Xin vui lòng chờ đăng nhập");
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                String email = emailE.getText().toString();
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
                            }else {
                                Toast.makeText(DangNhapFB.this, "Lỗi đăng nhập,yêu cầu kiểm tra lại email và mật khẩu khi đăng ký...!!!", Toast.LENGTH_SHORT).show();
                            }
                        }else {
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
                startActivity(new Intent(getApplicationContext(),RegisterEmail.class));
            }
        });
        clickForget = findViewById(R.id.txtForget);
        clickForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ResetPassword.class));
            }
        });
        emailE.startAnimation(Zdo);
        passE.startAnimation(Zdo);
        imClick.startAnimation(Zdo);
        clickForget.startAnimation(Zdo);
        clickreg.startAnimation(Zdo);
    }
}
