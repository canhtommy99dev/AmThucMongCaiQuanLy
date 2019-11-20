package com.alexmedia.amthucmongcaiquanly;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;

public class InfomationDuLich extends AppCompatActivity {

    private WebView wv,wwyoutube;
    FloatingActionButton btnInfoCho;
    ImageView btnBack,imageHienThi;
    ProgressBar ptobar,ptomc;
    String streetview,youtube,gioithieu,tengioithieu,image;
    Intent intent;
    TextView textViewgioithieu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infomation_du_lich);
        intent = getIntent();
        btnBack = findViewById(R.id.btnBack12322);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        streetview = intent.getStringExtra(DanhSachDuLich.STREETVIEW);
        youtube = intent.getStringExtra(DanhSachDuLich.YOUTUBELINK);
        gioithieu = intent.getStringExtra(DanhSachDuLich.BAIVIETGIOITHIEU);
        ptobar = findViewById(R.id.prologapplication);
        tengioithieu = intent.getStringExtra(DanhSachDuLich.TENDULICH);
        image = intent.getStringExtra(DanhSachDuLich.IMAGEDULICH);
        wv = findViewById(R.id.webView1);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.loadDataWithBaseURL("", streetview , "text/html",  "UTF-8", "");
        wv.setWebChromeClient(new WebChromeClient(){
            public void onProgressChanged(WebView view, int progress) {
                if(progress < 100 && ptobar.getVisibility() == ProgressBar.GONE){
                    ptobar.setVisibility(ProgressBar.VISIBLE);
                }

                ptobar.setProgress(progress);
                if(progress == 100) {
                    ptobar.setVisibility(ProgressBar.GONE);
                }
            }
        });
        btnInfoCho = findViewById(R.id.fab);
        btnInfoCho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog();
            }
        });
    }
    public void createDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dialog_chomongcai, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(tengioithieu);
        alert.setView(alertLayout);
        alert.setCancelable(false);
        wwyoutube = alertLayout.findViewById(R.id.youtubePlayerView);
        ptomc = alertLayout.findViewById(R.id.probarandroid);
        imageHienThi = alertLayout.findViewById(R.id.imageCeater);
        Glide.with(this).load(image).into(imageHienThi);
        textViewgioithieu = alertLayout.findViewById(R.id.txtGioiThieuDulich);
        textViewgioithieu.setText(gioithieu);
        wwyoutube.getSettings().setJavaScriptEnabled(true);
        wwyoutube.loadDataWithBaseURL("",youtube,"text/html","UTF-8","");
        wwyoutube.setWebChromeClient(new WebChromeClient(){
            public void onProgressChanged(WebView view, int progress) {
                if(progress < 100 && ptomc.getVisibility() == ProgressBar.GONE){
                    ptomc.setVisibility(ProgressBar.VISIBLE);
                }

                ptobar.setProgress(progress);
                if(progress == 100) {
                    ptomc.setVisibility(ProgressBar.GONE);
                }
            }
        });
        alert.setPositiveButton("Đóng Lại", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // code for matching password
                dialog.dismiss();
            }
        });

        AlertDialog dialog = alert.create();
        dialog.show();
    }
}
