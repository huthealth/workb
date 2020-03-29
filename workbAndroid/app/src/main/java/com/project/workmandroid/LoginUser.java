package com.project.workmandroid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginUser {
    @SerializedName("userid")
    public String userid;
    @SerializedName("passwd")
    public String passwd;

    @SerializedName("result")
    @Expose
    private String result;

    @SerializedName("auth")
    @Expose
    private int auth;

    public LoginUser(String email, String password) {
        this.userid = email;
        this.passwd = password;
    }

    public String getResult() {
        return result;
    }

    public String getUserid() { return userid; }

    public int getAuth() { return auth; }


    public void setResult(String result) {
        this.result = result;
    }
}
