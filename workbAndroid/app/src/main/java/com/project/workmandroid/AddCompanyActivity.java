package com.project.workmandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A login screen that offers login via email/password.
 */
public class AddCompanyActivity extends AppCompatActivity {
    private UserLoginTask mAuthTask = null;
    private EditText companyAddress;
    private EditText companyName;
    private View mLoginFormView;
    private Button btn_addCompany;
    // UI references.
    private View mProgressView;


    private static  final String TAG = "UseRetrofit";
    public static final String BASE_URL = "http://192.168.43.240:8080/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company);
        // Set up the login form.
        this.InitializeView();
        this.SetListener();
    }

    public void InitializeView() {
        companyAddress = findViewById(R.id.company_address);
        companyName = findViewById(R.id.company_name);
        btn_addCompany = findViewById(R.id.btn_add_company);
    }

    public void SetListener() {
        btn_addCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddCompanyActivity.this, EmployerMainActivity.class);
                finish();
                startActivity(intent);
                attemptAddCompany();
            }
        });

        mProgressView = findViewById(R.id.login_progress);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptAddCompany() {
        if (mAuthTask != null) {
            Intent intent = new Intent(AddCompanyActivity.this, EmployerMainActivity.class);
            finish();
            startActivity(intent);
        }

        // Reset errors.
        companyName.setError(null);
        companyAddress.setError(null);

        // Store values at the time of the login attempt.
        String companyname = companyName.getText().toString();
        String address = companyAddress.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {

            //Here a logging interceptor is created
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            //The logging interceptor will be added to the http client
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(logging);

            //use cookie
            SharedPreferences sf = getSharedPreferences("sFile",MODE_PRIVATE);
            String userid = sf.getString("userid","nop");

            //The Retrofit builder will have the client attached, in order to get connection logs
            Retrofit retrofit = new Retrofit.Builder()
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build();
           ServiceApi service = retrofit.create(ServiceApi.class);

            Call<CompanyAdd> call = service.post_company(userid, companyname, address);

            call.enqueue(new Callback<CompanyAdd>() {
                @Override
                public void onResponse(Call<CompanyAdd> call, Response<CompanyAdd> response) {
                    if(response.isSuccessful()){
                        response.body();
                        String result = response.body().getResult();
                        String check = "1";
                        if(result.equals(check)) {
                            Toast.makeText(AddCompanyActivity.this,"회사 생성이 완료 되었습니다.",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(AddCompanyActivity.this, EmployerMainActivity.class);
                            finish();
                            startActivity(intent);
                            Log.i("result", result);
                            Log.e(TAG, "Success");
                        }
                        else {
                            Toast.makeText(AddCompanyActivity.this,"생성 실패.",Toast.LENGTH_LONG).show();
                            Log.i("result", result);
                            Log.e(TAG, "Fail");
                        }
                    }
                }

                @Override
                public void onFailure(Call<CompanyAdd> call, Throwable t) {
                    // handle execution failures like no internet connectivity
                    Toast.makeText(AddCompanyActivity.this,"생성 실패.",Toast.LENGTH_LONG).show();
                    Log.e(TAG,"Fail");
                }
            });


        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mName;
        private final String mAddress;

        UserLoginTask(String name, String address) {
            mName = name;
            mAddress = address;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                Intent intent = new Intent(AddCompanyActivity.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }


}
