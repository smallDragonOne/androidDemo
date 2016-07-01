package com.example.apple.sometestdemo.Bean;

/**
 * Created by apple on 16/6/30.
 */
public class RequestBean {

    String jsonDate;
    public RequestBean(String jsonDate){
        this.jsonDate = jsonDate;
    }

    public String getJsonDate() {
        return jsonDate;
    }

    public void setJsonDate(String jsonDate) {
        this.jsonDate = jsonDate;
    }


}
