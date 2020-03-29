package com.project.workmandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CheckContractActivity extends AppCompatActivity {

    public String company;
    public String employeeName;
    public String employerName;
    public String employeePhoneNumber;
    public String employerPhoneNumber;
    public String year;
    public String month;
    public String day;
    public int wage;
    public int workday;
    public int workHour;
    public int period;

    private static  final String TAG = "UseRetrofit";
    public static final String BASE_URL = "http://192.168.43.240:8080/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_contract);

        // Login Cookie
        SharedPreferences sf = getSharedPreferences("sFile",MODE_PRIVATE);
        String phoneNumber= sf.getString("employeePhoneNumber","");
        Log.e("CheckContractPhone",phoneNumber);


        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        ServiceApi service = retrofit.create(ServiceApi.class);

        Call<getContract> call = service.get_contract(phoneNumber);

        call.enqueue(new Callback<getContract>() {
            @Override
            public void onResponse(Call<getContract> call, Response<getContract> response) {
                if(response.isSuccessful()){
                    response.body();
                    String result = response.body().getResult();
                    String check = "1";
                    if(result.equals(check)) {

                        Log.i("result", result);
                        Log.e(TAG, "Success");

                        company = response.body().getCompany();
                        employeeName=response.body().getEmployeeName();
                        employerName=response.body().getEmployerName();
                        employeePhoneNumber=response.body().getEmployeePhoneNumber();
                        employerPhoneNumber=response.body().getEmployerPhoneNumber();
                        year=response.body().getYear();
                        month=response.body().getMonth();
                        day=response.body().getDay();
                        wage=response.body().getWage();
                        workday=response.body().getWorkday();
                        workHour=response.body().getWorkHour();
                        period=response.body().getPeriod();

                        Log.e("yeah",company);
                    }
                    else {

                        Log.e(TAG, "Fail");
                    }
                }
            }

            @Override
            public void onFailure(Call<getContract> call, Throwable t) {
                Log.e(TAG, "Fail");
            }
        });

    }
}
