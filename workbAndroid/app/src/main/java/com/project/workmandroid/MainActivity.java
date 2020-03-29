package com.project.workmandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.MenuItem;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    SalaryFragment salaryFragment;
    ContractFragment contractFragment;
    AttendanceFragment attendanceFragment;

    private static  final String TAG = "UseRetrofit";
    public static final String BASE_URL = "http://192.168.43.240:8080/";
    public String employeePhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contractFragment = new ContractFragment();
        salaryFragment = new SalaryFragment();
        attendanceFragment = new AttendanceFragment();

        setTitle("근로계약서");
        FragmentManager fragmentManager0 = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction0 = fragmentManager0.beginTransaction();
        fragmentTransaction0.replace(R.id.fragment_container, contractFragment);
        fragmentTransaction0.commit();

        //use cookie
        SharedPreferences sf2 = getSharedPreferences("sFile",MODE_PRIVATE);
        String getUserid = sf2.getString("userid","nop");
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

        Call<getUser> call = service.get_user(getUserid);

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

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_contract:
                        setTitle("근로계약서");
                        FragmentManager fragmentManager1 = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
                        fragmentTransaction1.replace(R.id.fragment_container, contractFragment);
                        fragmentTransaction1.commit();
                        return true;
                    case R.id.navigation_attendance:
                        setTitle("출퇴근");
                        FragmentManager fragmentManager2 = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                        fragmentTransaction2.replace(R.id.fragment_container, attendanceFragment);
                        fragmentTransaction2.commit();
                        return true;
                    case R.id.navigation_salary:
                        setTitle("급여");
                        FragmentManager fragmentManager3 = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction3 = fragmentManager3.beginTransaction();
                        fragmentTransaction3.replace(R.id.fragment_container, salaryFragment);
                        fragmentTransaction3.commit();
                        return true;
                }
                return false;
            }
        });


    }
}
