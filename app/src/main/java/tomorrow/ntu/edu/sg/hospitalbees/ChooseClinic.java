package tomorrow.ntu.edu.sg.hospitalbees;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static tomorrow.ntu.edu.sg.hospitalbees.ClinicAdapter.clinicdetails;

public class ChooseClinic extends AppCompatActivity implements OnMapReadyCallback {

    static final int REQUEST_LOCATION = 1;
    GoogleMap mGoogleMap;
    String clinicChoiceString;
    LocationManager lm;


    String clinics[] = {"Ng Teng Fong Hospital", "Fullerton Health"};
    double lats[] = {1.3340363, 1.344278};
    double lngs[] = {103.7429231, 103.6815601};
    double latti;
    double longi;
    LatLng clinic;
    int i;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_clinic);

        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        getLocation();
        initMap();



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
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
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

        startActivity(new Intent(this, BookingDetails.class));
    }
}
