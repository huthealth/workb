package com.project.workmandroid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class getContract {
    @SerializedName("company")
    @Expose
    public String company;
    @SerializedName("employeeName")
    @Expose
    public String employeeName;
    @SerializedName("employerName")
    @Expose
    public String employerName;
    @SerializedName("employeePhoneNumber")
    @Expose
    public String employeePhoneNumber;
    @SerializedName("employerPhoneNumber")
    @Expose
    public String employerPhoneNumber;
    @SerializedName("year")
    @Expose
    public String year;
    @SerializedName("month")
    @Expose
    public String month;
    @SerializedName("day")
    @Expose
    public String day;
    @SerializedName("wage")
    @Expose
    public int wage;
    @SerializedName("workday")
    @Expose
    public int workday;
    @SerializedName("workHour")
    @Expose
    public int workHour;
    @SerializedName("period")
    @Expose
    public int period;

    @SerializedName("result")
    @Expose
    private String result;

    public getContract(String company, String employeeName, String employerName, String employeePhoneNumber, String employerPhoneNumber, String year, String month, String day, int wage, int workday, int workHour, int period ) {
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

    public String getCompany() {
        return company;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getEmployerName() {
        return employerName;
    }

    public String getEmployeePhoneNumber() {
        return employeePhoneNumber;
    }

    public String getEmployerPhoneNumber() {
        return employerPhoneNumber;
    }

    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public String getDay() {
        return day;
    }

    public int getWage() {
        return wage;
    }

    public int getWorkday() {
        return workday;
    }

    public int getWorkHour() {
        return workHour;
    }

    public int getPeriod() {
        return period;
    }

    public String getResult() {
        return result;
    }
}
