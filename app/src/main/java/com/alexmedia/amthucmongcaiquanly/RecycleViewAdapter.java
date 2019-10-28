package com.alexmedia.amthucmongcaiquanly;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecycleViewAdapter extends RecyclerView.Adapter {

    ArrayList mValues;
    Context mContext;
    protected ItemListener mListener;

    public RecycleViewAdapter(ArrayList mValues, Context mContext, ItemListener mListener) {
        this.mValues = mValues;
        this.mContext = mContext;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_imagecuahang,parent,false);
        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
    public interface ItemListener {
        void onItemClick(DataModel item);
    }
    public class ViewHoder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView textView;
        public ImageView imageView;
        public RelativeLayout relativeLayout;
        DataModel item;

        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textView = itemView.findViewById(R.id.textView1);
            imageView = itemView.findViewById(R.id.imageView);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
        }

        public void setData(DataModel model){
            this.item = item;
            textView.setText(item.text);
            imageView.setImageResource(item.drawable);
            relativeLayout.setBackgroundColor(Color.parseColor(item.color));
        }
        @Override
        public void onClick(View v) {
            if (mListener != null){
                mListener.onItemClick(item);
            }
        }
    }
}
