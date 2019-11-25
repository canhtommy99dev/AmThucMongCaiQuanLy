package com.alexmedia.amthucmongcaiquanly.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexmedia.amthucmongcaiquanly.Model.DangBaiModel;
import com.alexmedia.amthucmongcaiquanly.Model.Infofrommap;
import com.alexmedia.amthucmongcaiquanly.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class AdapterMap implements GoogleMap.InfoWindowAdapter {

    private Activity context;

    public AdapterMap(Activity ctx){
        context = ctx;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = context.getLayoutInflater()
                .inflate(R.layout.map_custom_infowindow, null);

        TextView tencuahang = view.findViewById(R.id.nameCuaHang);
        TextView diachi = view.findViewById(R.id.diachicuahang);
        tencuahang.setText(marker.getTitle());
        diachi.setText(marker.getSnippet());
        return view;
    }
}
