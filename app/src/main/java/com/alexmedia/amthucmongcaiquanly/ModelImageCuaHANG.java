package com.alexmedia.amthucmongcaiquanly;

public class ModelImageCuaHANG {
    String id,imagegoc;

    public ModelImageCuaHANG() {
    }

    public ModelImageCuaHANG(String cid, String imagegoc) {
        this.id = cid;
        this.imagegoc = imagegoc;
    }

    public String getid() {
        return id;
    }

    public void setId(String cid) {
        this.id = id;
    }

    public String getImagegoc() {
        return imagegoc;
    }

    public void setImagegoc(String imagegoc) {
        this.imagegoc = imagegoc;
    }
}
