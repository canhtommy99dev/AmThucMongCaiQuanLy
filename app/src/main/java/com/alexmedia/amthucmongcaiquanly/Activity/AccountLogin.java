package com.alexmedia.amthucmongcaiquanly.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alexmedia.amthucmongcaiquanly.DangNhapFB;
import com.alexmedia.amthucmongcaiquanly.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccountLogin extends AppCompatActivity {

    ImageView btnClickBack,btnSignOut;
    EditText edtChangePassword;
    TextView accountemail;
    Button btnUpdate;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_login);
        btnClickBack = findViewById(R.id.imgBack2111);
        btnSignOut = findViewById(R.id.signout11);
        edtChangePassword = findViewById(R.id.edtChangePassword);
        accountemail = findViewById(R.id.txtAccount1222);
        btnUpdate = findViewById(R.id.btnChangePassword);
        auth =FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        accountemail.setText("Tài Khoản:"+user.getEmail());
        btnClickBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(getApplicationContext(), DangNhapFB.class));
                finish();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePass(v);
            }
        });
    }
    public void ChangePass(View v){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null){
            firebaseUser.updatePassword(edtChangePassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(AccountLogin.this, "Mật khẩu của bạn đã thay đổi...!!!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(AccountLogin.this, "Mật khẩu không thể thay đổi được...!!!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}
