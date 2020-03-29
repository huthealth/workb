package com.project.workmandroid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompanyAdd {
    @SerializedName("userid")
    public String userid;
    @SerializedName("companyname")
    public String companyname;
    @SerializedName("address")
    public String address;

    @SerializedName("result")
    @Expose
    private String result;

    public CompanyAdd(String email, String companyname, String address) {
        this.userid = email;
        this.companyname = companyname;
        this.address = address;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
