package com.alexmedia.amthucmongcaiquanly;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class AdapterIntroCuaHang extends RecyclerView.Adapter<AdapterIntroCuaHang.ViewHolder> {

    Context context;
    List<ModelImageCuaHANG> modelInfoCuaHangList;

    public AdapterIntroCuaHang(Context context, List<ModelImageCuaHANG> modelInfoCuaHangList) {
        this.context = context;
        this.modelInfoCuaHangList = modelInfoCuaHangList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_imagecuahang,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(modelInfoCuaHangList.get(position).getImage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return modelInfoCuaHangList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageInfo);
        }
    }
}
