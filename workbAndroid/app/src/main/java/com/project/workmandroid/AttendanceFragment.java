package com.project.workmandroid;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AttendanceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AttendanceFragment extends Fragment {
    MainActivity activity;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private static  final String TAG = "UseRetrofit";
    public static final String BASE_URL = "http://192.168.43.240:8080/";


    public AttendanceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AttendanceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AttendanceFragment newInstance(String param1, String param2) {

        AttendanceFragment fragment = new AttendanceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_attendance, container, false);

        SharedPreferences sf= this.getActivity().getSharedPreferences("sFile", Context.MODE_PRIVATE);
        final String getUserid = sf.getString("userid","nop");
        Log.e("attendfragment",getUserid);

        //time
        Date from = new Date();
        SimpleDateFormat tfyear = new SimpleDateFormat("yyyy");
        SimpleDateFormat tfmonth = new SimpleDateFormat("MM");
        SimpleDateFormat tfday = new SimpleDateFormat("dd");
        SimpleDateFormat tfhour = new SimpleDateFormat("HH");
        SimpleDateFormat tfmin = new SimpleDateFormat("mm");
        SimpleDateFormat tfsec = new SimpleDateFormat("ss");

        final String year = tfyear.format(from);
        final String month = tfmonth.format(from);
        final String day = tfday.format(from);
        final String hour = tfhour.format(from);
        final String min = tfmin.format(from);
        final String sec = tfsec.format(from);


        Button btn_sign = rootView.findViewById(R.id.button_hello);
        btn_sign.setOnClickListener(new ViewGroup.OnClickListener() {
            @Override
            public void onClick(View v){

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

                Call<AttendOffwork> call = service.post_Attend(getUserid, year,month,day,hour,min,sec);

                call.enqueue(new Callback<AttendOffwork>() {
                    Activity root = getActivity();
                    @Override
                    public void onResponse(Call<AttendOffwork> call, Response<AttendOffwork> response) {

                        if(response.isSuccessful()){
                            response.body();
                            String result = response.body().getResult();
                            String check = "1";
                            if(result.equals(check)) {
                                Log.i("result", result);
                                Log.e(TAG, "Success");
                                Toast.makeText(root, "출근 완료.", Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(root, "출근 실패", Toast.LENGTH_LONG).show();
                                Log.i("result", result);
                                Log.e(TAG, "Fail");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AttendOffwork> call, Throwable t) {
                        // handle execution failures like no internet connectivity
                        Toast.makeText(root, "출근 실패", Toast.LENGTH_LONG).show();
                        Log.e(TAG,"Fail");
                    }
                });




            }
        });


        Button btn_check = rootView.findViewById(R.id.button_bye);
        btn_check.setOnClickListener(new ViewGroup.OnClickListener() {
            @Override
            public void onClick(View v){
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
                final ServiceApi service = retrofit.create(ServiceApi.class);

                Call<AttendOffwork> call = service.post_Offwork(getUserid, year,month,day,hour,min,sec);

                call.enqueue(new Callback<AttendOffwork>() {
                    Activity root = getActivity();
                    @Override
                    public void onResponse(Call<AttendOffwork> call, Response<AttendOffwork> response) {

                        if(response.isSuccessful()){
                            response.body();
                            String result = response.body().getResult();
                            String check = "1";
                            if(result.equals(check)) {
                                Log.i("result", result);
                                Log.e(TAG, "Success");
                                Toast.makeText(root, "퇴근 완료.", Toast.LENGTH_LONG).show();

                            }
                            else {
                                Toast.makeText(root, "퇴근 실패", Toast.LENGTH_LONG).show();
                                Log.i("result", result);
                                Log.e(TAG, "Fail");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AttendOffwork> call, Throwable t) {
                        // handle execution failures like no internet connectivity
                        Toast.makeText(root, "퇴근 실패", Toast.LENGTH_LONG).show();
                        Log.e(TAG,"Fail");
                    }
                });



            }
        });



        return rootView;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
