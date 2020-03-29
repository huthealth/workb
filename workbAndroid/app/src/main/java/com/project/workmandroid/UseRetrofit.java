package com.project.workmandroid;

import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UseRetrofit {
    private static  final String TAG = "UseRetrofit";
    public static final String BASE_URL = "http://172.16.101.119:8080/";

    public static int checkvalue =-1;
    public void RegisterPost(String userid, String passwd, String name, String phone, Integer auth){

        //Here a logging interceptor is created
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        //The logging interceptor will be added to the http client
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        //The Retrofit builder will have the client attached, in order to get connection logs
        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        ServiceApi service = retrofit.create(ServiceApi.class);

        Call<RegisterUser> call = service.post_register(userid, passwd, name, phone, auth);
        call.enqueue(new Callback<RegisterUser>() {
            @Override
            public void onResponse(Call<RegisterUser> call, Response<RegisterUser> response) {
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
            public void onFailure(Call<RegisterUser> call, Throwable t) {
                // handle execution failures like no internet connectivity
                Log.e(TAG,"Fail");
            }
        });
    }

    public void LoginPost(String userid, String passwd){

        //Here a logging interceptor is created
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        //The logging interceptor will be added to the http client
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        //The Retrofit builder will have the client attached, in order to get connection logs
        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        ServiceApi service = retrofit.create(ServiceApi.class);

        Call<LoginUser> call = service.post_Login(userid, passwd);

        call.enqueue(new Callback<LoginUser>() {
            @Override
            public void onResponse(Call<LoginUser> call, Response<LoginUser> response) {
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
            public void onFailure(Call<LoginUser> call, Throwable t) {
                // handle execution failures like no internet connectivity
                Log.e(TAG,"Fail");

            }
        });


    }

}
