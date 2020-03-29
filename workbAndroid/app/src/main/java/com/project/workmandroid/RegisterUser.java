package com.project.workmandroid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterUser {

    @SerializedName("userid")
    public String userid;
    @SerializedName("passwd")
    public String passwd;
    @SerializedName("phone")
    public String phone;
    @SerializedName("name")
    public String name;
    @SerializedName("auth")
    public Integer auth;

    @SerializedName("result")
    @Expose
    private String result;

    public RegisterUser(String email, String password, String name, String phone, Integer auth) {
        this.userid = email;
        this.passwd = password;
        this.phone = phone;
        this.name = name;
        this.auth = auth;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}
