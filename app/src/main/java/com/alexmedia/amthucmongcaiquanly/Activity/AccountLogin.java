package com.alexmedia.amthucmongcaiquanly.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.alexmedia.amthucmongcaiquanly.R;
import com.google.firebase.auth.FirebaseAuth;

public class AccountLogin extends AppCompatActivity {

    ImageView btnClickBack,btnSignOut;
    EditText edtChangePassword;
    Button btnUpdate;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_login);
    }
}
