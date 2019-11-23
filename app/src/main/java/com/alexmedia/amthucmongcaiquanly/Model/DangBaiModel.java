package com.alexmedia.amthucmongcaiquanly.Model;

public class DangBaiModel {
    String id,tench,diachi,thoigian,sodt,tinhtrangship,facebook,create,timecreate,image,danhmuc;
    Double latitude,longitude;

    public DangBaiModel() {
    }

    public DangBaiModel(String id, String tench, String diachi, String thoigian, String tinhtrangship, String facebook, String create, String timecreate, String image, Double latitude, Double longitude,String danhmuc,String sodt) {
        if(image.trim().equals("")){
            image = "No Name";
        }
        this.id = id;
        this.tench = tench;
        this.diachi = diachi;
        this.thoigian = thoigian;
        this.sodt = sodt;
        this.tinhtrangship = tinhtrangship;
        this.facebook = facebook;
        this.create = create;
        this.timecreate = timecreate;
        this.latitude = latitude;
        this.longitude = longitude;
        this.image = image;
        this.danhmuc = danhmuc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTench() {
        return tench;
    }

    public void setTench(String tench) {
        this.tench = tench;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getThoigian() {
        return thoigian;
    }

    public void setThoigian(String thoigian) {
        this.thoigian = thoigian;
    }
    public String getSodt() {
        return sodt;
    }

    public void setSodt(String sodt) {
        this.sodt = sodt;
    }

    public String getTinhtrangship() {
        return tinhtrangship;
    }

    public void setTinhtrangship(String tinhtrangship) {
        this.tinhtrangship = tinhtrangship;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getCreate() {
        return create;
    }

    public void setCreate(String create) {
        this.create = create;
    }

    public String getTimecreate() {
        return timecreate;
    }

    public void setTimecreate(String timecreate) {
        this.timecreate = timecreate;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDanhmuc() {
        return danhmuc;
    }

    public void setDanhmuc(String danhmuc) {
        this.danhmuc = danhmuc;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }


}
