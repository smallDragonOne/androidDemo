package com.example.apple.sometestdemo.Bean;


/**
 * Created by apple on 16/6/24.
 */
public class mainBean {

    private String title;
    private int image;
    public mainBean(){

    }

    public mainBean(int image , String title){
        this.image = image;
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
