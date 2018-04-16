package tomorrow.ntu.edu.sg.hospitalbees;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LocationDuration extends AppCompatActivity implements GeoTask.Geo {
    String currentlocation = "NTU HALL 9";
    String fakelocation[] = {"Ng Teng Fong Hospital", "NTU Fullerton Health"};
    public static String travellist = "TravelList";
    public static String traveltransfer = "TravelString";



    String str_from,str_to;
    public String minlist = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_duration);
        str_from = currentlocation.replaceAll(" ", "%20");
        str_to =fakelocation[0].replaceAll(" ", "%20");
        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + str_from + "&destinations=" + str_to + "&mode=driving&language=fr-FR&avoid=tolls&key=AIzaSyBzkZbcWIZvz1s6MX2rl_uMoqYXfQoFOkg";
        new GeoTask(LocationDuration.this).execute(url);
        str_to =fakelocation[1].replaceAll(" ", "%20");
        url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + str_from + "&destinations=" + str_to + "&mode=driving&language=fr-FR&avoid=tolls&key=AIzaSyBzkZbcWIZvz1s6MX2rl_uMoqYXfQoFOkg";
        new GeoTask(LocationDuration.this).execute(url);
        String temp = PreferenceManager.getDefaultSharedPreferences(this).getString(traveltransfer, "QueueNotFound");

        PreferenceManager.getDefaultSharedPreferences(this).edit().putString(travellist, temp).apply();


        initialize();

        startActivity(new Intent(this, ChooseClinic.class));

        }



    @Override
    public void setDouble(String result) {
        String res[]=result.split(",");
        int min=Integer.parseInt(res[0])/60;
        int dist=Integer.parseInt(res[1])/1000;
        minlist = minlist + Integer.toString(min);
        minlist = minlist + ",";
        PreferenceManager.getDefaultSharedPreferences(this).edit().putString(traveltransfer, minlist).apply();




    }
    public void initialize()
    {

    }
}
