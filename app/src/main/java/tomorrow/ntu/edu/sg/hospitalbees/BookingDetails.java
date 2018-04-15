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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

//import static tomorrow.ntu.edu.sg.hospitalbees.ClinicAdapter.clinicdetails;
//import static tomorrow.ntu.edu.sg.hospitalbees.LoginActivity.logindetails;

public class BookingDetails extends AppCompatActivity {

    String loginNameString;
    String clinicChoiceString;
    String timeString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

//        loginNameString = PreferenceManager.getDefaultSharedPreferences(this).getString(logindetails, "NameNotFound");
//        TextView username = (TextView) findViewById(R.id.editUserName);
//        username.setText(loginNameString);
//        clinicChoiceString = PreferenceManager.getDefaultSharedPreferences(this).getString(clinicdetails, "ClinicNotFound");
//        TextView clinic = (TextView) findViewById(R.id.clinicName);
//        clinic.setText(clinicChoiceString);
//        Calendar calendar = Calendar.getInstance();
//        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
//        timeString = format.format(calendar.getTime());
//        TextView time = (TextView) findViewById(R.id.bookingTime);
//        time.setText(timeString);

    }


    public void confirmBookingButton(View view) {
        startActivity(new Intent(this, BookingComplete.class));

    }

    public void cancelBookingButton(View view) {
        startActivity(new Intent(this, HomePage.class));
    }
}
