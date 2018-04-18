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
    Hospital hosp[] = new Hospital[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_duration);
        hosp[0] = new Hospital(1001, "Ng Teng Fong Hospital", 1.335082, 103.745198 );
        hosp[1] = new Hospital(1002, "NTU Fullerton Health", 1.345690, 103.682677);
        hosp[2] = new Hospital(1003, "Jurong Polyclinic", 1.350060, 103.730598);
        int no_of_hospital = 3;
        String str_to[] = new String[no_of_hospital];
        String all_hosp_latlng = "";
        for (int i = 0; i< no_of_hospital; i++) {
            str_to[i] = Double.toString(hosp[i].getLat()) + "," + Double.toString(hosp[i].getLng());
            all_hosp_latlng = all_hosp_latlng + str_to[i];
            if (i == no_of_hospital-1) {
                break;
            }
            else{
                all_hosp_latlng = all_hosp_latlng + "|";
            }
        }
        str_from = currentlocation.replaceAll(" ", "%20");
        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + all_hosp_latlng + "&destinations=" + currentlocation + "&key=AIzaSyBzkZbcWIZvz1s6MX2rl_uMoqYXfQoFOkg";
//        new GeoTask(LocationDuration.this).execute(url);

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
