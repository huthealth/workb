package com.project.workmandroid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttendOffwork {
    @SerializedName("userid")
    public String userid;
    @SerializedName("year")
    public String year;
    @SerializedName("month")
    public String month;
    @SerializedName("day")
    public String day;
    @SerializedName("hour")
    public String hour;
    @SerializedName("min")
    public String min;
    @SerializedName("sec")
    public String sec;


    @SerializedName("result")
    @Expose
    private String result;

    public AttendOffwork(String email, String year, String month, String day, String hour, String min,  String sec) {
        this.userid = email;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.min = min;
        this.sec = sec;

    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
