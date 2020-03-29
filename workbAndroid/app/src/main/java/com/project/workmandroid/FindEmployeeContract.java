package com.project.workmandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FindEmployeeContract extends AppCompatActivity {

    private EditText mEmailView;
    private Button mBtton;

    public String employeeid;
    public String employeePhoneNumber;

    private static  final String TAG = "UseRetrofit";
    public static final String BASE_URL = "http://192.168.43.240:8080/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_employee_contract);
        mEmailView=(EditText)findViewById(R.id.email);
        mBtton=(Button) findViewById(R.id.btn_find_employee_contract);

        mBtton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                employeeid = mEmailView.getText().toString();

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

                Call<getUser> call = service.get_user(employeeid);

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
                                employeePhoneNumber = response.body().getPhone();
                                Log.e("employerPhone",employeePhoneNumber);

                                // Login Cookie
                                SharedPreferences sf = getSharedPreferences("sFile",MODE_PRIVATE);
                                String checkUse = sf.getString("employeePhoneNumber","");
                                if(checkUse == "") {
                                    SharedPreferences.Editor editor = sf.edit();
                                    editor.putString("employeePhoneNumber", employeePhoneNumber);
                                    editor.commit();
                                }else{
                                    SharedPreferences.Editor editor = sf.edit();
                                    editor.remove("employPhoneNumber");
                                    editor.putString("employeePhoneNumber", employeePhoneNumber);
                                    editor.commit();
                                }

                                Intent intent = new Intent(FindEmployeeContract.this, CheckContractActivity.class);
                                finish();
                                startActivity(intent);

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

            }

        });



    }
}
