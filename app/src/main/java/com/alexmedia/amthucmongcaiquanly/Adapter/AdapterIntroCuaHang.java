package com.alexmedia.amthucmongcaiquanly.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexmedia.amthucmongcaiquanly.Activity.ImageFullScreen;
import com.alexmedia.amthucmongcaiquanly.Model.ModelImageCuaHANG;
import com.alexmedia.amthucmongcaiquanly.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class AdapterIntroCuaHang extends RecyclerView.Adapter<AdapterIntroCuaHang.ViewHolder> {

    Context context;
    List<ModelImageCuaHANG> modelInfoCuaHangList;
    public static final String ID = "id";
    public static final String IMAGE66 = "Image";


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
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Glide.with(context).load(modelInfoCuaHangList.get(position).getImage()).into(holder.image);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, modelInfoCuaHangList.get(position).id, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ImageFullScreen.class);
                intent.putExtra(ID,modelInfoCuaHangList.get(position).getId());
                intent.putExtra(IMAGE66,modelInfoCuaHangList.get(position).getImage());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelInfoCuaHangList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageInfo);
        }
    }
}
