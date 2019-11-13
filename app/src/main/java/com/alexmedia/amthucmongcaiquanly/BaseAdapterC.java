package com.alexmedia.amthucmongcaiquanly;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

public class BaseAdapterC extends BaseAdapter {

    Activity context;
    List<ModelImageCuaHANG> modelImageCuaHANGS;
    int mLayout;

    public BaseAdapterC(Activity context, List<ModelImageCuaHANG> modelImageCuaHANGS, int mLayout) {
        this.context = context;
        this.modelImageCuaHANGS = modelImageCuaHANGS;
        this.mLayout = mLayout;
    }

    @Override
    public int getCount() {
        return modelImageCuaHANGS.size();
    }

    @Override
    public Object getItem(int position) {
        return modelImageCuaHANGS.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        ImageView mc;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowview = convertView;
        ViewHolder viewHolder = new ViewHolder();
        if (rowview == null){
            rowview = inflater.inflate(mLayout,null);
            viewHolder.mc = rowview.findViewById(R.id.anhchuamc);
        }else {
            viewHolder = (ViewHolder) rowview.getTag();
        }
        Glide.with(context).load(modelImageCuaHANGS.get(position).image).into(viewHolder.mc);
        return rowview;
    }
}
