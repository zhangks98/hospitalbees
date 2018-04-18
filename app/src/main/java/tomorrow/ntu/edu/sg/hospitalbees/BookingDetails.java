package tomorrow.ntu.edu.sg.hospitalbees;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tomorrow.ntu.edu.sg.hospitalbees.models.Hospital;


public class BookingDetails extends AppCompatActivity implements OnMapReadyCallback {

    private static final int REQUEST_LOCATION = 1;
    private static final String TAG = "BookingDetails";
    private static final String serverUrl = BuildConfig.SERVER_URL;

    GoogleMap mGoogleMap;
    LocationManager lm;

    private SharedPreferences mUserPreferences, mBookingPreferences;
    private Hospital mChosenHospital;

    private ConstraintLayout mBookingDetailsLayout;
    private LinearLayout mBookingMessageLayout;
    private TextView mUserPhoneNumberTextView, mHospitalNameTextView, mBookingTimeTextView, mETATextView, mBookingMessageTextView;

    private String mUserPhoneNumber;
    private double myLat, myLong;

    @Inject
    OkHttpClient mHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);
        ((HBApp) getApplication()).getNetComponent().inject(this);

        // Get user preferences for user info
        mUserPreferences = getSharedPreferences(getString(R.string.pref_user), Context.MODE_PRIVATE);

        // Get booking preference if user successfully made a booking
        mBookingPreferences = getSharedPreferences(getString(R.string.pref_booking), Context.MODE_PRIVATE);

        // Get the Intent that start the BookingDetails Activity (ChooseClinic Activity)
        Intent chooseClinicIntent = getIntent();

        mBookingDetailsLayout = findViewById(R.id.layout_booking_details);
        mUserPhoneNumberTextView = findViewById(R.id.tv_user_phone_number_value);
        mHospitalNameTextView = findViewById(R.id.tv_chosen_clinic_name);
        mBookingTimeTextView = findViewById(R.id.tv_booking_time);
        mETATextView = findViewById(R.id.tv_chosen_clinic_eta);

        mBookingMessageLayout = findViewById(R.id.layout_booking_message);
        mBookingMessageTextView = findViewById(R.id.tv_booking_details_message);

        mUserPhoneNumber = mUserPreferences.getString(getString(R.string.pref_user_phone_number_key), null);

        // Get the Hospital that the user chooses in the ChooseClinic Activity
        if (chooseClinicIntent.hasExtra(getString(R.string.intent_extra_chosen_hospital_key)) && mUserPhoneNumber != null) {
            showBookingDetails();
            mChosenHospital = chooseClinicIntent.getParcelableExtra(getString(R.string.intent_extra_chosen_hospital_key));
            mUserPhoneNumberTextView.setText(mUserPhoneNumber);
            mHospitalNameTextView.setText(mChosenHospital.getName());
            mETATextView.setText(getString(R.string.eta_value, mChosenHospital.getTotalETA()));

            DateFormat formatter = SimpleDateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
            mBookingTimeTextView.setText(formatter.format(new Date()));
            
            lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            getLocation();
            initMap();
        } else {
            showErrorMessage();
        }

    }

    private void getLocation(){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
        else{
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                myLat = location.getLatitude();
                myLong = location.getLongitude();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_LOCATION:
                getLocation();
                break;
        }
    }


    private void initMap() {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFrag);
        mapFragment.getMapAsync(this);

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mGoogleMap.setMyLocationEnabled(true);
        }

        goToLocation(myLat, myLong);

    }

    private void goToLocation(double myLat, double myLng) {
        double hospitalLat = mChosenHospital.getLat();
        double hospitalLng = mChosenHospital.getLng();
        LatLng me = new LatLng(myLat,myLng);
        LatLng hospital = new LatLng(hospitalLat, hospitalLng);
        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(me).include(hospital).build();
        mGoogleMap.addMarker(new MarkerOptions().position(hospital).title(mChosenHospital.getName()));
        CameraUpdate update = CameraUpdateFactory.newLatLngBounds(bounds,150);
        mGoogleMap.animateCamera(update);


    }

    private void submitBooking() {
        Uri submitUri = Uri.parse(serverUrl).buildUpon()
                .appendPath("api")
                .appendPath("booking")
                .build();
        RequestBody requestBody = new FormBody.Builder()
                .add("phoneNumber", mUserPhoneNumber)
                .add("hospitalID",String.valueOf(mChosenHospital.getId()))
                .add("eta", String.valueOf(mChosenHospital.getTotalETA()))
                .build();
        Request request = new Request.Builder().url(submitUri.toString())
                .post(requestBody).build();

        mHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "Error creating new Booking");
                Log.e(TAG, e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showErrorAlert();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == HttpURLConnection.HTTP_CREATED) {
                    String body = response.body().string();
                    try {
                        JSONObject bookingResponse = new JSONObject(body);
                        String tid = bookingResponse.getString("tid");
                        if (tid != null && !TextUtils.isEmpty(tid)) {
                            mBookingPreferences.edit().putString(getString(R.string.pref_booking_tid_key), tid).apply();
                            startActivity(new Intent(BookingDetails.this, BookingComplete.class));
                            BookingDetails.this.finish();
                        }
                    } catch (JSONException e) {
                        Log.e(TAG, "failed to parse json response for opened hospitals");
                        Log.e(TAG,e.getMessage());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showErrorAlert();
                            }
                        });
                    }
                } else if (response.code() == HttpURLConnection.HTTP_CONFLICT) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showDuplicateBookingAlert();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showErrorAlert();
                        }
                    });
                }

            }

            void showErrorAlert() {
                AlertDialog.Builder builder = new AlertDialog.Builder(BookingDetails.this);
                builder.setMessage(R.string.submit_booking_error)
                        .setPositiveButton(R.string.retry_label, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(BookingDetails.this, ChooseClinic.class));
                                BookingDetails.this.finish();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }

            void showDuplicateBookingAlert() {
                AlertDialog.Builder builder = new AlertDialog.Builder(BookingDetails.this);
                builder.setMessage(R.string.duplicate_booking_error)
                        .setPositiveButton(R.string.view_my_queue, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(BookingDetails.this, MyQueue.class));
                                BookingDetails.this.finish();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,ChooseClinic.class));
        this.finish();
    }

    private void showBookingDetails() {
        mBookingMessageLayout.setVisibility(View.INVISIBLE);
        mBookingDetailsLayout.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mBookingDetailsLayout.setVisibility(View.INVISIBLE);
        mBookingMessageLayout.setVisibility(View.VISIBLE);
        mBookingMessageTextView.setText(R.string.booking_details_error);
    }

    private void showSubmitingBookingMessage () {
        mBookingDetailsLayout.setVisibility(View.INVISIBLE);
        mBookingMessageLayout.setVisibility(View.VISIBLE);
        mBookingMessageTextView.setText(R.string.submit_booking_message);

    }


    public void confirmBookingButton(View view) {
        showSubmitingBookingMessage();
        submitBooking();
    }

    public void cancelBookingButton(View view) {
        startActivity(new Intent(this, ChooseClinic.class));
        this.finish();
    }
}
