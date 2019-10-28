package com.alexmedia.amthucmongcaiquanly;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterImageCuaHang extends BaseAdapter {

    Context mcontext;
    int myLayout1;
    List<ModelImageCuaHANG> modelImageCuaHANGS;
    private OnItemClickListener mListenr;

    public AdapterImageCuaHang(Context mcontext, int myLayout1, List<ModelImageCuaHANG> modelImageCuaHANGS) {
        this.mcontext = mcontext;
        this.myLayout1 = myLayout1;
        this.modelImageCuaHANGS = modelImageCuaHANGS;
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

    private class ViewHolder implements View.OnClickListener{
        ImageView chimaage;

        @Override
        public void onClick(View v) {
            if (mListenr != null){

            }
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowview = convertView;
        ViewHolder viewHolder = new ViewHolder();
        if (rowview == null){
            rowview = inflater.inflate(myLayout1,null);
            viewHolder.chimaage = rowview.findViewById(R.id.photoView);
        }else {
            viewHolder = (ViewHolder) rowview.getTag();
        }
        Picasso.with(mcontext).load(modelImageCuaHANGS.get(position).imagegoc).into(viewHolder.chimaage);
        return rowview;
    }
    public interface OnItemClickListener{
        void onItemClick(int postion);
        void onWhatEverClick(int postion);
        void onDeleteClick(int postion);
    }
    public void setOnClickListener(OnItemClickListener listener){
        mListenr = listener;
    }
}
