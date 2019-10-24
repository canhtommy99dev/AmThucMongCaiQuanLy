package com.alexmedia.amthucmongcaiquanly;

public class ModelImageCuaHANG {
    String cid,imagegoc;

    public ModelImageCuaHANG() {
    }

    public ModelImageCuaHANG(String cid, String imagegoc) {
        this.cid = cid;
        this.imagegoc = imagegoc;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getImagegoc() {
        return imagegoc;
    }

    public void setImagegoc(String imagegoc) {
        this.imagegoc = imagegoc;
    }
}
