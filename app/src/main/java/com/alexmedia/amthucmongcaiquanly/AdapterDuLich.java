package com.alexmedia.amthucmongcaiquanly;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.List;

public class AdapterDuLich extends ArrayAdapter<ModelDangBaiDuLich> {
    Activity activity;
    List<ModelDangBaiDuLich> modelDangBaiDuLiches;
    public AdapterDuLich(Activity activity,List<ModelDangBaiDuLich> modelDangBaiDuLiches) {
        super(activity,R.layout.adapter_dulich,modelDangBaiDuLiches);
        this.activity = activity;
        this.modelDangBaiDuLiches = modelDangBaiDuLiches;
    }
    @Override
    public int getCount() {
        return modelDangBaiDuLiches.size();
    }

    @Nullable
    @Override
    public ModelDangBaiDuLich getItem(int position) {
        return modelDangBaiDuLiches.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View listItems = inflater.inflate(R.layout.adapter_dulich, null, true);
        TextView tenDiaChi = listItems.findViewById(R.id.txtTenMC);
        ImageView imgAnhMc = listItems.findViewById(R.id.imgCuaHangDoLen1);
        ModelDangBaiDuLich listDanhSach = modelDangBaiDuLiches.get(position);
        tenDiaChi.setText(listDanhSach.getNamedulich());
        Glide.with(activity).load(listDanhSach.imagedulich).centerCrop().into(imgAnhMc);
        Animation animation = AnimationUtils.loadAnimation(activity,R.anim.zoom_in);
        listItems.startAnimation(animation);
        return listItems;
    }
}
