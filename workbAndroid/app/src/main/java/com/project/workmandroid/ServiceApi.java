package com.project.workmandroid;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ServiceApi {

    @FormUrlEncoded
    @POST("api/users")
    Call<RegisterUser> post_register(
            @Field("userid") String userid,
            @Field("passwd") String passwd,
            @Field("name") String name,
            @Field("phone")String phone,
            @Field("auth")Integer auth);

    @FormUrlEncoded
    @POST("api/users/login")
    Call<LoginUser> post_Login(
            @Field("userid") String userid,
            @Field("passwd") String passwd);

    @FormUrlEncoded
    @POST("api/attend")
    Call<AttendOffwork> post_Attend(
            @Field("userid") String userid,
            @Field("year") String year,
            @Field("month") String month,
            @Field("day")String day,
            @Field("hour")String hour,
            @Field("min")String min,
            @Field("sec")String sec
    );

    @FormUrlEncoded
    @POST("api/offwork")
    Call<AttendOffwork> post_Offwork(
            @Field("userid") String userid,
            @Field("year") String year,
            @Field("month") String month,
            @Field("day")String day,
            @Field("hour")String hour,
            @Field("min")String min,
            @Field("sec")String sec
    );

    @FormUrlEncoded
    @POST("api/company")
    Call<CompanyAdd> post_company(
            @Field("userid") String userid,
            @Field("companyname") String companyname,
            @Field("address") String address);


    @GET("api/company/{userid}")
    Call<ResponseCompany> get_company(
            @Path("userid") String userid
    );

    @FormUrlEncoded
    @POST("api/createPTJContract")
    Call<ContractAdd> post_contract(
            @Field("company") String company,
            @Field("employeeName") String employeeName,
            @Field("employerName") String employerName,
            @Field("employeePhoneNumber") String employeePhoneNumber,
            @Field("employerPhoneNumber") String employerPhoneNumber,
            @Field("year") String year,
            @Field("month") String month,
            @Field("day") String day,
            @Field("wage") int wage,
            @Field("workday") int workday,
            @Field("workHour") int workHour,
            @Field("period") int period);

    @GET("api/users/{userid}")
    Call<getUser> get_user(
            @Path("userid") String userid
    );

    @GET("api/getPendingPTJContract/{phoneNumber}")
    Call<getContract> get_contract(
            @Path("phoneNumber") String phoneNumber
    );


}
