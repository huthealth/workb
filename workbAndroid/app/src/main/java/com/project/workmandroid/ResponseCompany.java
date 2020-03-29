package com.project.workmandroid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseCompany {
    @SerializedName("userid")
    public String userid;

    @SerializedName("result")
    @Expose
    private String result;

    @SerializedName("companyname")
    @Expose
    private String companyname;

    @SerializedName("address")
    @Expose
    private String address;

    public ResponseCompany(String userid) {
        this.userid = userid;

    }

    public String getResult() {
        return result;
    }

    public String getUserid() { return userid; }

    public String getCompanyname() { return companyname; }

    public String getAddress() { return address; }


    public void setResult(String result) {
        this.result = result;
    }
}
