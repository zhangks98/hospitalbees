package tomorrow.ntu.edu.sg.hospitalbees;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import tomorrow.ntu.edu.sg.hospitalbees.models.Hospital;

public class LocationDuration extends AppCompatActivity implements GeoTask.Geo {
    String currentlocation = "NTU HALL 9";
    public static String travellist = "TravelList";
    String str_from;
    public String minlist = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_duration);
        Hospital hosp1 = new Hospital(1001, "Ng Teng Fong Hospital");
        Hospital hosp2 = new Hospital(1002, "NTU Fullerton Health");
        Hospital hosp3 = new Hospital(1003, "Jurong Polyclinic");
        int no_of_hospital = 3;
        String str_to[] = new String[no_of_hospital];
        str_to[0] = hosp1.getName();
        str_to[1] = hosp2.getName();
        str_to[2] = hosp3.getName();
        str_from = currentlocation.replaceAll(" ", "%20");
        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + str_to[0] + "|" + str_to[1] + "|" + str_to[2] + "&destinations=" + currentlocation + "&key=AIzaSyBzkZbcWIZvz1s6MX2rl_uMoqYXfQoFOkg";
        new GeoTask(LocationDuration.this).execute(url);

        }



    @Override
    public void setDouble(String result) {
        String res[]=result.split(",");
        int min;
        for (int i = 0; i< res.length ; i++) {
            min = Integer.parseInt(res[i]) / 60;
            minlist = minlist + Integer.toString(min) + ",";
        }

        PreferenceManager.getDefaultSharedPreferences(this).edit().putString(travellist, minlist).apply();
        Log.d("Test minlist", minlist);

    }

    public void moveToChooseClinicButton(View view) {
        startActivity(new Intent(this, ChooseClinic.class));

    }
}
