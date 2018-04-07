package tomorrow.ntu.edu.sg.hospitalbees;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class ChooseClinic extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mGoogleMap;
    LocationManager locationManager;

    String clinics[] = {"Ng Teng Fong Hospital", "Fullerton Health"};
    double lats[] = {1.3340363, 1.344278};
    double lngs[] = {103.7429231, 103.6815601};

    Spinner sp;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_clinic);
        initMap();

        sp = (Spinner) findViewById(R.id.spinner);
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, clinics);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void initMap() {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
//        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
//            Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
//
//            onLocationChanged(location);
//        }


    }


    public void homePageButton(View view) {

        startActivity(new Intent(this, HomePage.class));
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        goToLocation(1.3340363,103.7429231, 12);
    }

    private void goToLocation(double lat, double lng, float zoom) {
        for (int i = 0; i<clinics.length ; i++) {
            LatLng clinic = new LatLng(lats[i], lngs[i]);
            mGoogleMap.addMarker(new MarkerOptions().position(clinic).title(clinics[i]));
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(clinic, zoom);
            mGoogleMap.moveCamera(update);
        }
    }

    public void confirmBookingButton(View view) {
        startActivity(new Intent(this, BookingDetails.class));
    }
}
