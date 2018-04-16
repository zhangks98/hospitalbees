package tomorrow.ntu.edu.sg.hospitalbees;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HomePage extends AppCompatActivity implements View.OnClickListener {

    private TextView homePageWording;
    private CardView homePageCard;
    private SharedPreferences mUserPreferences;
    private SharedPreferences mBookingPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        this.mUserPreferences = getSharedPreferences(getString(R.string.pref_user), Context.MODE_PRIVATE);
        this.mBookingPreferences = getSharedPreferences(getString(R.string.pref_booking), Context.MODE_PRIVATE);
        FirebaseMessaging.getInstance().subscribeToTopic("news");

//        set = getSharedPreferences(shareddata, 0);
//        boolean returned = set.getBoolean(shareddata, false);

        homePageWording = (TextView) findViewById(R.id.homepageWordings);
//        homePageCard = (CardView) findViewById(R.id.homepage_card_view);
//        homePageCard.setOnClickListener(this);
//
//        if (returned) {
//            homePageWording.setVisibility(View.GONE);
//            homePageCard.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId()== R.id.homepage_card_view){
            homePageWording.setVisibility(View.VISIBLE);
            homePageCard.setVisibility(View.GONE);

//            SharedPreferences.Editor editor = set.edit();
//            editor.putBoolean(shareddata, false);
//            editor.apply();

        }
    }


    public void bookAppointmentButton(View view) {
        startActivity(new Intent(this, LocationDuration.class));
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
        SharedPreferences.Editor editor = mUserPreferences.edit();
        editor.putBoolean(getString(R.string.pref_user_is_logged_in_key), false).apply();
        mBookingPreferences.edit().clear().apply();
        startActivity(new Intent(this, LoginActivity.class));
        this.finish();

    }
}
