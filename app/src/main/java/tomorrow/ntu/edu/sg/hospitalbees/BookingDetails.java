package tomorrow.ntu.edu.sg.hospitalbees;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static tomorrow.ntu.edu.sg.hospitalbees.ClinicAdapter.clinicdetails;
import static tomorrow.ntu.edu.sg.hospitalbees.ClinicAdapter.queuetime;
import static tomorrow.ntu.edu.sg.hospitalbees.ClinicAdapter.traveldetails;

//import static tomorrow.ntu.edu.sg.hospitalbees.ClinicAdapter.clinicdetails;
//import static tomorrow.ntu.edu.sg.hospitalbees.LoginActivity.logindetails;

public class BookingDetails extends AppCompatActivity implements OnMapReadyCallback {

    String loginNameString;
    String clinicChoiceString;
    String timeString;
    String waitString;
    private SharedPreferences mUserPreferences;

    static final int REQUEST_LOCATION = 1;
    GoogleMap mGoogleMap;
    LocationManager lm;


    String clinics[] = {"Ng Teng Fong Hospital", "NTU Fullerton Health"};
    double lats[] = {1.3340363, 1.344278};
    double lngs[] = {103.7429231, 103.6815601};
    double latti;
    double longi;
    LatLng clinic;
    int i;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        getLocation();
        initMap();

        mUserPreferences = getSharedPreferences(getString(R.string.pref_user), Context.MODE_PRIVATE);
        loginNameString = mUserPreferences.getString(getString(R.string.pref_user_phone_number_key), null);
        TextView username = (TextView) findViewById(R.id.editUserName);
        username.setText(loginNameString);
        clinicChoiceString = PreferenceManager.getDefaultSharedPreferences(this).getString(clinicdetails, "ClinicNotFound");
        TextView clinic = (TextView) findViewById(R.id.clinicName);
        clinic.setText(clinicChoiceString);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        timeString = format.format(calendar.getTime());
        TextView time = (TextView) findViewById(R.id.bookingTime);
        time.setText(timeString);
        waitString = PreferenceManager.getDefaultSharedPreferences(this).getString(queuetime, "QueueNotFound");
        TextView wait = (TextView) findViewById(R.id.estimatedWaitingTime);
        wait.setText(waitString + " minutes");

    }
    void getLocation(){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
        else{
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                latti = location.getLatitude();
                longi = location.getLongitude();
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

        goToLocation(latti, longi, 12);

    }

    private void goToLocation(double lat, double lng, float zoom) {
        clinicChoiceString = PreferenceManager.getDefaultSharedPreferences(this).getString(clinicdetails, "ClinicNotFound");
        for (i = 0; i< clinics.length; i++) {
            if (clinicChoiceString.equals(clinics[i])) {
                clinic = new LatLng(lats[i], lngs[i]);
                break;
            }
        }
        lat = (lat + lats[i])/2;
        lng = (lng + lngs[i])/2;
        LatLng show = new LatLng(lat,lng);
        mGoogleMap.addMarker(new MarkerOptions().position(clinic).title(clinicChoiceString));
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(show, zoom);
        mGoogleMap.moveCamera(update);


    }


    public void confirmBookingButton(View view) {
        startActivity(new Intent(this, BookingComplete.class));

    }

    public void cancelBookingButton(View view) {
        startActivity(new Intent(this, HomePage.class));
    }
}
