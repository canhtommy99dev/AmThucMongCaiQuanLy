package com.alexmedia.amthucmongcaiquanly.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alexmedia.amthucmongcaiquanly.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {

    Button SendMail,Return;
    EditText edtUser;
    FirebaseAuth auth;
    ProgressDialog dialog;
    Animation animation1;
    TextView flu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        auth = FirebaseAuth.getInstance();
        SendMail = findViewById(R.id.btnSend);
        Return = findViewById(R.id.btnReturn1);
        edtUser = findViewById(R.id.edtUserSendEmail);
        SendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtUser.getText().toString().trim();

                if (email.isEmpty()) {
                    Toast.makeText(getApplication(), "Yêu cầu nhập Email để gửi để xác nhận...!!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (email.length() < 6){
                    Toast.makeText(ResetPassword.this, "Không nhập quá nhỏ 6 ký tự email", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ResetPassword.this, "Hệ thống sẽ gửi email cho bạn để xác thực...!!!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ResetPassword.this, "Lỗi khi gửi email...!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        Return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        animation1 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.float_in);
        edtUser.startAnimation(animation1);
        Return.startAnimation(animation1);
        SendMail.startAnimation(animation1);
        flu = findViewById(R.id.textView2);
        flu.startAnimation(animation1);
    }
}
