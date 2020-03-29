package com.project.workmandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddEmployeeActivity extends AppCompatActivity {

    private EditText mEmailView;
    private EditText mPayView;
    private EditText mTermView;
    private EditText mTimeView;
    private EditText mDayView;
    private Button mBtton;


    public String company;
    public String employeeName;
    public String employerName;
    public String employeePhoneNumber;
    public String employerPhoneNumber;


    public String employeeid ;
    public int wage;
    public int period;
    public int workday;
    public int workHour;

    public String year;
    public String month;
    public String day;

    private static  final String TAG = "UseRetrofit";
    public static final String BASE_URL = "http://192.168.43.240:8080/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        mEmailView=(EditText)findViewById(R.id.email);
        mPayView=(EditText)findViewById(R.id.pay);
        mTermView=(EditText)findViewById(R.id.term);
        mTimeView=(EditText)findViewById(R.id.time);
        mDayView=(EditText) findViewById(R.id.day);
        mBtton=(Button) findViewById(R.id.btn_add_employee);


        mBtton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                employeeid = mEmailView.getText().toString();
                wage = Integer.parseInt(mPayView.getText().toString());
                period = Integer.parseInt(mTermView.getText().toString());
                workday= Integer.parseInt(mDayView.getText().toString());
                workHour = Integer.parseInt(mTimeView.getText().toString());

                //time
                Date from = new Date();
                SimpleDateFormat tfyear = new SimpleDateFormat("yyyy");
                SimpleDateFormat tfmonth = new SimpleDateFormat("MM");
                SimpleDateFormat tfday = new SimpleDateFormat("dd");

                year = tfyear.format(from);
                month = tfmonth.format(from);
                day = tfday.format(from);

                //use cookie
                SharedPreferences sf = getSharedPreferences("sFile",MODE_PRIVATE);
                company = sf.getString("company","nop");
                Log.e("AddEmployee",company);

                //use cookie
                SharedPreferences sf2 = getSharedPreferences("sFile",MODE_PRIVATE);
                String employerid = sf2.getString("userid","nop");
                Log.e("employerid",employerid);

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

                Call<getUser> call = service.get_user(employerid);

                call.enqueue(new Callback<getUser>() {
                    @Override
                    public void onResponse(Call<getUser> call, Response<getUser> response) {
                        if(response.isSuccessful()){
                            response.body();
                            String result = response.body().getResult();
                            String check = "1";
                            if(result.equals(check)) {
                                Log.i("result", result);
                                Log.e(TAG, "Success");

                                employerName = response.body().getName();
                                employerPhoneNumber = response.body().getPhone();
                                Log.e("employername",employerName);
                                Log.e("employerPhone",employerPhoneNumber);
                            }
                            else {
                                Log.i("result", result);
                                Log.e(TAG, "Fail");
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<getUser> call, Throwable t) {
                        Log.e(TAG, "Fail");
                    }
                });


                ServiceApi service2 = retrofit.create(ServiceApi.class);

                Call<getUser> call2 = service2.get_user(employeeid);

                call2.enqueue(new Callback<getUser>() {
                    @Override
                    public void onResponse(Call<getUser> call2, Response<getUser> response) {
                        if(response.isSuccessful()){
                            response.body();
                            String result = response.body().getResult();
                            String check = "1";
                            if(result.equals(check)) {
                                Log.i("result", result);
                                Log.e(TAG, "Success");

                                employeeName = response.body().getName();
                                employeePhoneNumber = response.body().getPhone();
                                Log.e("employeename",employeeName);
                                Log.e("employeePhone",employeePhoneNumber);
                            }
                            else {
                                Log.i("result", result);
                                Log.e(TAG, "Fail");
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<getUser> call, Throwable t) {
                        Log.e(TAG, "Fail");
                    }
                });

                ServiceApi service3 = retrofit.create(ServiceApi.class);

                Call<ContractAdd> call3 = service3.post_contract(company, employeeName,employerName,employeePhoneNumber,employerPhoneNumber,year,month,day,wage,workday,workHour,period);

                call3.enqueue(new Callback<ContractAdd>() {
                    @Override
                    public void onResponse(Call<ContractAdd> call2, Response<ContractAdd> response) {
                        if(response.isSuccessful()){
                            response.body();
                            String result = response.body().getResult();
                            String check = "1";
                            if(result.equals(check)) {
                                Log.i("result", result);
                                Log.e(TAG, "Success");

                            }
                            else {
                                Log.i("result", result);
                                Log.e(TAG, "Fail");
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<ContractAdd> call, Throwable t) {
                        Log.e(TAG, "Fail");
                    }
                });





            }



        });


    }

}
