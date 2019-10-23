package com.alexmedia.amthucmongcaiquanly;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterCuaHang extends BaseAdapter {

    Context mContext;
    int myLayout;
    List<DangBaiModel> arraydangbai;

    public AdapterCuaHang(Context mContext, int myLayout, List<DangBaiModel> arraydangbai) {
        this.mContext = mContext;
        this.myLayout = myLayout;
        this.arraydangbai = arraydangbai;
    }

    @Override
    public int getCount() {
        return arraydangbai.size();
    }

    @Override
    public Object getItem(int i) {
        return arraydangbai.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder{
        ImageView imgCh;
        TextView txttench,txtdiachi,txtthoigian,txtsdt,txtShip,txtfb,txtnguoidang,txttimecreate;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowview = view;
        ViewHolder viewHolder = new ViewHolder();
        if (rowview == null){
            rowview = inflater.inflate(myLayout,null);
            viewHolder.txttench = rowview.findViewById(R.id.txtTenCH);
            viewHolder.txtdiachi = rowview.findViewById(R.id.txtDiaChi);
            viewHolder.txtthoigian = rowview.findViewById(R.id.txttimeOpenEnd);
            viewHolder.txtsdt = rowview.findViewById(R.id.txtPhoneNumber);
            viewHolder.txtShip = rowview.findViewById(R.id.txtShip);
            viewHolder.txtfb = rowview.findViewById(R.id.txtFacebookCH);
            viewHolder.txtnguoidang = rowview.findViewById(R.id.txtcreateby);
            viewHolder.txttimecreate = rowview.findViewById(R.id.timeUpdate);
            viewHolder.imgCh = rowview.findViewById(R.id.imgNhaHang);
            rowview.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) rowview.getTag();
        }
        viewHolder.txttench.setText(arraydangbai.get(i).tench);
        viewHolder.txtdiachi.setText("Địa chỉ :"+arraydangbai.get(i).diachi);
        viewHolder.txtthoigian.setText(" -- "+arraydangbai.get(i).thoigian);
        viewHolder.txtsdt.setText(" -- " + arraydangbai.get(i).sodt);
        viewHolder.txtShip.setText("-- Ship đồ ăn :" + arraydangbai.get(i).tinhtrangship);
        viewHolder.txtfb.setText("Facebook Cửa Hàng :"+arraydangbai.get(i).facebook);
        viewHolder.txtnguoidang.setText("Create By:"+arraydangbai.get(i).create);
        viewHolder.txttimecreate.setText(arraydangbai.get(i).timecreate);
        Picasso.with(mContext).load(arraydangbai.get(i).image).into(viewHolder.imgCh);
        return rowview;
    }
}
