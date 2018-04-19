package tomorrow.ntu.edu.sg.hospitalbees;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * The class for splash activity which is the loading screen (blue screen) in between pages
 */
public class SplashActivity extends AppCompatActivity {

    private static final int REQUEST_LOCATION = 1;
    LocationManager lm;
    private double myLat, myLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        getLocation();
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);


        SharedPreferences user = getSharedPreferences(getString(R.string.pref_user), Context.MODE_PRIVATE);
        if (user.getBoolean(getString(R.string.pref_user_is_logged_in_key), getResources().getBoolean(R.bool.pref_is_logged_in_default))) {
            startActivity(new Intent(this, HomePage.class));
        }
        else {
            startActivity(new Intent(this, LoginActivity.class));
        }
        this.finish();
    }
    private void getLocation(){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
        else{
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                myLat = location.getLatitude();
                myLng = location.getLongitude();
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
}
