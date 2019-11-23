package com.alexmedia.amthucmongcaiquanly.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alexmedia.amthucmongcaiquanly.MainActivity;
import com.alexmedia.amthucmongcaiquanly.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterEmail extends AppCompatActivity {
    FirebaseAuth mfault;
    EditText edtEmailReg,edtPassReg;
    Button btnReg,btnReturn;
    Animation floatin,dowml;
    TextView spal;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_email);
        mfault = FirebaseAuth.getInstance();
        floatin = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.float_in);
        dowml = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.xfa);
        edtEmailReg = findViewById(R.id.edtUserSignUp);
        edtPassReg = findViewById(R.id.edtPasswordSignUp);
        btnReg = findViewById(R.id.btnRegister1);
        btnReturn = findViewById(R.id.btnReturn);
        spal = findViewById(R.id.textView2);
        spal.startAnimation(dowml);
        edtEmailReg.startAnimation(floatin);
        edtPassReg.startAnimation(floatin);
        btnReg.startAnimation(floatin);
        btnReturn.startAnimation(floatin);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new ProgressDialog(RegisterEmail.this);
                dialog.setMessage("Xin vui lòng chờ đăng ký");
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                String email = edtEmailReg.getText().toString().trim();
                String password = edtPassReg.getText().toString().trim();
                if (TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(), "Yêu cầu nhập địa chỉ rõ ràng", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(), "Yêu cầu nhập Mật Khẩu", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 6){
                    Toast.makeText(getApplicationContext(), "Mật khẩu quá ngắn yêu cầu nhập trên 6", Toast.LENGTH_SHORT).show();
                    return;
                }
                mfault.createUserWithEmailAndPassword(email,password).addOnCompleteListener(RegisterEmail.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(RegisterEmail.this, "Tạo Tài khoản Email là" + task.toString(), Toast.LENGTH_SHORT).show();
                        if (!task.isSuccessful()){
                            Toast.makeText(RegisterEmail.this, "Lỗi khi đăng ký (có tài khoản rồi !!!)", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            startActivity(new Intent(RegisterEmail.this, MainActivity.class));
                            finish();
                        }
                    }
                });
            }
        });
    }
}
