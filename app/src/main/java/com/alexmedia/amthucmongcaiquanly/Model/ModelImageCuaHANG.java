package com.alexmedia.amthucmongcaiquanly.Model;

public class ModelImageCuaHANG {
    String id,image;

    public ModelImageCuaHANG() {
    }

    public ModelImageCuaHANG(String id, String image) {
        if (image.trim().equals("")){
            image = "No name";
        }
        this.id = id;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
