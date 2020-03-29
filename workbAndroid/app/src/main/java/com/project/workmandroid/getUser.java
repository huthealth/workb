package com.project.workmandroid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class getUser {
    @SerializedName("result")
    @Expose
    private String result;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("phone")
    @Expose
    private String phone;

    public String getResult() {
        return result;
    }

    public String getName() { return name; }

    public String getPhone() { return phone; }


}
