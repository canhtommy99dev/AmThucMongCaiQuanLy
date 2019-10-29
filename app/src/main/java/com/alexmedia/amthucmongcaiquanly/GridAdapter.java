package com.alexmedia.amthucmongcaiquanly;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class GridAdapter extends BaseAdapter {

    Context mcontext;
    int mLayout;
    List<ModelImageCuaHANG> arrayCH;

    public GridAdapter(Context context, int mLayout, List<ModelImageCuaHANG> arrayCH) {
        this.mcontext = context;
        this.mLayout = mLayout;
        this.arrayCH = arrayCH;
    }
    private class ViewHolder{
        ImageView imgCh1;
    }

    @Override
    public int getCount() {
        return arrayCH.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayCH.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowview = convertView;
        ViewHolder viewHolder = new ViewHolder();
        if (rowview == null){
            rowview = inflater.inflate(mLayout,null);
            viewHolder.imgCh1 = rowview.findViewById(R.id.photoView1);
        }else {
            viewHolder = (ViewHolder) rowview.getTag();
        }
        return rowview;
    }
}
