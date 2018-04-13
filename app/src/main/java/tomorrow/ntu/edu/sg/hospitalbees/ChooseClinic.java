package tomorrow.ntu.edu.sg.hospitalbees;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.preference.PreferenceManager;
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

import static tomorrow.ntu.edu.sg.hospitalbees.ClinicAdapter.clinicdetails;

public class ChooseClinic extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mGoogleMap;
    String clinicChoiceString;



    String clinics[] = {"Ng Teng Fong Hospital", "Fullerton Health"};
    double lats[] = {1.3340363, 1.344278};
    double lngs[] = {103.7429231, 103.6815601};
    LatLng clinic;
    int i;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_clinic);
        initMap();



    }

    private void initMap() {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);



    }


    public void homePageButton(View view) {

        startActivity(new Intent(this, HomePage.class));
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mGoogleMap.setMyLocationEnabled(true);
        }
        LocationManager lm = (LocationManager)getSystemService(this.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        goToLocation(latitude, longitude, 12);

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
