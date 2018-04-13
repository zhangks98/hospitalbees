package tomorrow.ntu.edu.sg.hospitalbees;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import static tomorrow.ntu.edu.sg.hospitalbees.MyQueue.shareddata;
import static tomorrow.ntu.edu.sg.hospitalbees.SplashActivity.logincheck;

public class HomePage extends AppCompatActivity implements View.OnClickListener {

    TextView homePageWording;
    CardView homePageCard;
    SharedPreferences set, check;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        set = getSharedPreferences(shareddata, 0);
        boolean returned = set.getBoolean(shareddata, false);
        check = getSharedPreferences(logincheck, 0);
        boolean checked = check.getBoolean(logincheck, false);



        homePageWording = (TextView) findViewById(R.id.homepageWordings);
        homePageCard = (CardView) findViewById(R.id.homepage_card_view);

        homePageCard.setOnClickListener(this);

        if (returned) {
            homePageWording.setVisibility(View.GONE);
            homePageCard.setVisibility(View.VISIBLE);
        }



    }

    @Override
    public void onClick(View v) {
        if (v.getId()== R.id.homepage_card_view){
            homePageWording.setVisibility(View.VISIBLE);
            homePageCard.setVisibility(View.GONE);

            SharedPreferences.Editor editor = set.edit();
            editor.putBoolean(shareddata, false);
            editor.apply();


        }
    }


    public void bookAppointmentButton(View view) {
        startActivity(new Intent(this, ClinicList.class));
    }

    public void viewBookingHistoryButton(View view) {
        startActivity(new Intent(this, BookingHistory.class));
    }

    public void viewMyQueueButton(View view) {
        startActivity(new Intent(this, MyQueue.class));

    }

    public void viewFAQButton(View view) {
        startActivity(new Intent(this, FAQ.class));
    }


    public void viewAlertsButton(View view) {
        startActivity(new Intent(this, Alerts.class));
    }

    public void logOutButton(View view) {
        SharedPreferences.Editor editor = check.edit();
        editor.putBoolean(logincheck, false);
        editor.apply();
        startActivity(new Intent(this, LoginActivity.class));


    }
}
