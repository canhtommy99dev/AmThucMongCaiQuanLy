package com.alexmedia.amthucmongcaiquanly;

public class ModelDangBaiDuLich {
    String id,namedulich,linkgoogle,linkyoutube,baidang,imagedulich;

    public ModelDangBaiDuLich() {
    }

    public ModelDangBaiDuLich(String id, String namedulich, String linkgoogle, String linkyoutube, String baidang, String imagedulich) {
        if(imagedulich.trim().equals("")){
            imagedulich = "No Name";
        }
        this.id = id;
        this.namedulich = namedulich;
        this.linkgoogle = linkgoogle;
        this.linkyoutube = linkyoutube;
        this.baidang = baidang;
        this.imagedulich = imagedulich;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamedulich() {
        return namedulich;
    }

    public void setNamedulich(String namedulich) {
        this.namedulich = namedulich;
    }

    public String getLinkgoogle() {
        return linkgoogle;
    }

    public void setLinkgoogle(String linkgoogle) {
        this.linkgoogle = linkgoogle;
    }

    public String getLinkyoutube() {
        return linkyoutube;
    }

    public void setLinkyoutube(String linkyoutube) {
        this.linkyoutube = linkyoutube;
    }

    public String getBaidang() {
        return baidang;
    }

    public void setBaidang(String baidang) {
        this.baidang = baidang;
    }

    public String getImagedulich() {
        return imagedulich;
    }

    public void setImagedulich(String imagedulich) {
        this.imagedulich = imagedulich;
    }
}
