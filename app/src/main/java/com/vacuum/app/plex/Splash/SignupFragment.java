package com.vacuum.app.plex.Splash;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vacuum.app.plex.MainActivity;
import com.vacuum.app.plex.Model.User;
import com.vacuum.app.plex.R;
import com.vacuum.app.plex.Utility.ApiInterface;
import com.vacuum.app.plex.Utility.SingleShotLocationProvider;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;
import static com.vacuum.app.plex.Splash.SplashScreen.MY_PREFS_NAME;

public class SignupFragment extends Fragment implements View.OnClickListener {

    final static String SIGNUP_FRAGMENT_TAG = "SIGNUP_FRAGMENT_TAG";
    private EditText full_name,email,password,phone;
    Button buttonRegister,determine_location,Date_of_Birth;
    Context mContext;
    static final Integer LOCATION = 0x1;
    String age,location,address;
    AnimationDrawable anim;
    LinearLayout background_layout_signup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.signup_layout, container, false);
        mContext = this.getActivity();
        full_name =  view.findViewById(R.id.full_name);
        email =  view.findViewById(R.id.email);
        password =  view.findViewById(R.id.password);
        phone =  view.findViewById(R.id.phone);

        buttonRegister =  view.findViewById(R.id.buttonRegister);
        determine_location =  view.findViewById(R.id.determine_location);
        Date_of_Birth =  view.findViewById(R.id.Date_of_Birth);




        background_layout_signup= view.findViewById(R.id.background_layout_signup);


        anim = (AnimationDrawable) background_layout_signup.getBackground();
        anim.setEnterFadeDuration(4000);
        anim.setExitFadeDuration(4000);


        buttonRegister.setOnClickListener(this);
        determine_location.setOnClickListener(this);
        Date_of_Birth.setOnClickListener(this);


        return view;
    }
    public void showDatePicker() {
        DatePickerDialog cc = new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT,dateSetListener, 1990,
                1, 1);
        cc.show();
    }
    private DatePickerDialog.OnDateSetListener dateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int month, int day) {
                    Toast.makeText(getActivity(), "selected date is " + view.getYear() +
                            " / " + (view.getMonth()+1) +
                            " / " + view.getDayOfMonth(), Toast.LENGTH_SHORT).show();
                }
            };





    private void insertUser() {
        String ROOT_URL = "https://mohamedebrahim.000webhostapp.com/";
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        ApiInterface api = retrofit.create(ApiInterface.class);
        api.registration(
                full_name.getText().toString(),
                email.getText().toString(),
                password.getText().toString(),
                phone.getText().toString(),
                2000,
                age,
                location,
                address
        ).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {


                if(response.isSuccessful()) {
                  User u = response.body();
                            SharedPreferences.Editor editor = mContext.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                            editor.putString("full_name",full_name.getText().toString());
                            editor.putString("email",email.getText().toString());
                            editor.putString("password",password.getText().toString());
                            editor.putString("phone",phone.getText().toString());
                            editor.putInt("points",u.getPoints());
                            editor.putString("age",age);
                            editor.putString("loation",location);
                            editor.putString("address",address);
                            editor.apply();
                            skipSplash();
                            Toast.makeText(mContext,"Registered Successfully", Toast.LENGTH_SHORT).show();

                        }




            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(mContext,"unable to register", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void validateFields() {
        if (full_name.getText().length() == 0) {
            full_name.setError("Empty Field");
        }else if (email.getText().length() == 0) {
            email.setError("Empty Field");
        }
        else if (phone.getText().length() == 0){
            phone.setError("Empty Field");
        }else {
            insertUser();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonRegister:
                validateFields();
                break;
            case R.id.determine_location:
                askForPermission(Manifest.permission.ACCESS_FINE_LOCATION, LOCATION);
                break;
            case R.id.Date_of_Birth:
                showDatePicker();
                break;
        }
    }




    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(mContext, permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(), permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(this.getActivity(), new String[]{permission}, requestCode);

            } else {
                ActivityCompat.requestPermissions(this.getActivity(), new String[]{permission}, requestCode);
            }
        } else {
            getGPS();
        }
    }
    public void getGPS() {
        // when you need location
        // if inside activity context = this;
        SingleShotLocationProvider.requestSingleUpdate(mContext,
                new SingleShotLocationProvider.LocationCallback() {
                    @Override public void onNewLocationAvailable(SingleShotLocationProvider.GPSCoordinates location1) {
                        Log.e("TAG Location", "my location is " +  location1.latitude+"//////"+ location1.longitude);
                        try{
                            Geocoder geo = new Geocoder(mContext, Locale.getDefault());
                            List<Address> addresses = geo.getFromLocation(location1.latitude, location1.longitude, 1);
                            if (addresses.isEmpty()) {
                            }
                            else {
                                if (addresses.size() > 0) {
                                    Log.e("TAG",addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() +", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName());
                                    Toast.makeText(mContext, "Location Done", Toast.LENGTH_SHORT).show();
                                    location = location1.latitude+","+ location1.longitude;
                                    address = addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() +", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName();
                                    age = "1/8/1996";
                                }
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }




    private void skipSplash()
    {
        Intent i = new Intent(getActivity(), MainActivity.class);
        startActivity(i);
        getActivity().finish();
    }



    @Override
    public void onResume() {
        super.onResume();
        if (anim != null && !anim.isRunning())
            anim.start();
    }

    //Stopping animation:- stop the animation on onPause.
    @Override
    public void onPause() {
        super.onPause();
        if (anim != null && anim.isRunning())
            anim.stop();
    }

}