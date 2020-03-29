package com.project.workmandroid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContractAdd {
    @SerializedName("company")
    public String company;
    @SerializedName("employeeName")
    public String employeeName;
    @SerializedName("employerName")
    public String employerName;
    @SerializedName("employeePhoneNumber")
    public String employeePhoneNumber;
    @SerializedName("employerPhoneNumber")
    public String employerPhoneNumber;
    @SerializedName("year")
    public String year;
    @SerializedName("month")
    public String month;
    @SerializedName("day")
    public String day;
    @SerializedName("wage")
    public int wage;
    @SerializedName("workday")
    public int workday;
    @SerializedName("workHour")
    public int workHour;
    @SerializedName("period")
    public int period;


    @SerializedName("result")
    @Expose
    private String result;

    public ContractAdd(String company, String employeeName, String employerName, String employeePhoneNumber, String employerPhoneNumber, String year, String month, String day, int wage, int workday, int workHour, int period ) {
        this.company = company;
        this.employeeName = employeeName;
        this.employerName = employerName;
        this.employeePhoneNumber =  employeePhoneNumber;
        this.employerPhoneNumber = employerPhoneNumber;
        this.year = year;
        this.month = month;
        this.day = day;
        this.wage = wage;
        this.workday= workday;
        this.workHour= workHour;
        this.period= period;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
