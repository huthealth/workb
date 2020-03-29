package com.project.workmandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.project.workmandroid.CheckContractActivity;
import com.project.workmandroid.R;
import com.project.workmandroid.SignatureActivity;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EmployerMainActivity extends AppCompatActivity {
    private Button mSign;
    private Button mCheck;
    private static  final String TAG = "UseRetrofit";
    public static final String BASE_URL = "http://192.168.43.240:8080/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_main);

        Button mSign = findViewById(R.id.mSign);
        Button mCheck = findViewById(R.id.mCheck);

        //use cookie
        SharedPreferences sf = getSharedPreferences("sFile",MODE_PRIVATE);
        String getUserid = sf.getString("userid","no");
        Log.e("main",getUserid);

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

        Call<ResponseCompany> call = service.get_company(getUserid);

        call.enqueue(new Callback<ResponseCompany>() {
            @Override
            public void onResponse(Call<ResponseCompany> call, Response<ResponseCompany> response) {
                if(response.isSuccessful()){
                    response.body();
                    String result = response.body().getResult();
                    String check = "1";
                    if(result.equals(check)) {
                        String companyname = response.body().getCompanyname();
                        // Login Cookie
                        SharedPreferences sf2 = getSharedPreferences("sFile",MODE_PRIVATE);

                        String checkUse = sf2.getString("company","");
                        if(checkUse == "") {
                            SharedPreferences.Editor editor = sf2.edit();
                            editor.putString("company", companyname);
                            editor.commit();
                        }else{
                            SharedPreferences.Editor editor = sf2.edit();
                            editor.remove("company");
                            editor.putString("company", companyname);
                            editor.commit();
                        }
                        Log.i("result", result);
                        Log.e(TAG, "Success");
                        Log.e("company",companyname);
                    }
                    else {
                        Log.i("result", result);
                        Log.e(TAG, "Fail");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseCompany> call, Throwable t) {
                Log.e(TAG, "Fail");
            }
        });



        mSign.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmployerMainActivity.this, AddEmployeeActivity.class);
                startActivity(intent);
            }
        });

        mCheck.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmployerMainActivity.this, FindEmployeeContract.class);
                startActivity(intent);
            }
        });
    }
}