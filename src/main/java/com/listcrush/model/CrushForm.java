package com.listcrush.model;

import org.springframework.web.multipart.MultipartFile;

public class CrushForm {
    private int id;
    private String name;
    private int old;
    private String address;
    private String facebook;
    private MultipartFile img;

    public CrushForm() {
    }

    public CrushForm(int id, String name, int old, String address, String facebook, MultipartFile img) {
        this.id = id;
        this.name = name;
        this.old = old;
        this.address = address;
        this.facebook = facebook;
        this.img = img;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOld() {
        return old;
    }

    public void setOld(int old) {
        this.old = old;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public MultipartFile getImg() {
        return img;
    }

    public void setImg(MultipartFile img) {
        this.img = img;
    }
}
