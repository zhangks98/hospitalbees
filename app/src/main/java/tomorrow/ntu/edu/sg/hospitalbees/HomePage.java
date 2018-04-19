package tomorrow.ntu.edu.sg.hospitalbees;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * The class for showing the home page.
 */
public class HomePage extends AppCompatActivity {

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

        homePageWording = (TextView) findViewById(R.id.homepageWordings);
//        Log.d("FCM",FirebaseInstanceId.getInstance().getToken());
    }


    /**
     * Method that allows User to book appointment by clicking the book appointment button
     *
     * @param view the book appointment button view
     */
    public void bookAppointmentButton(View view) {
        startActivity(new Intent(this, ChooseClinic.class));
    }

    /**
     * Method that allows User to view the booking history by clicking the view history button.
     *
     * @param view the view booking history button view
     */
    public void viewBookingHistoryButton(View view) {
        startActivity(new Intent(this, BookingHistory.class));
    }

    /**
     * Method that allows User to view the his current queue by clicking the view my queue button.
     *
     * @param view the view my queue button view
     */
    public void viewMyQueueButton(View view) {
        startActivity(new Intent(this, MyQueue.class));

    }

    /**
     * Method that allows User to view FAQ by clicking the view FAQ button.
     *
     * @param view the view FAQ button view
     */
    public void viewFAQButton(View view) {
        startActivity(new Intent(this, FAQ.class));
    }


    /**
     * Method that allows User to view alerts by clicking the view alerts button.
     *
     * @param view the view alerts button view
     */
    public void viewAlertsButton(View view) {
        startActivity(new Intent(this, Alerts.class));
    }

    /**
     * Method that allows User to log out by clicking log out menu option.
     *
     */
    private void logOut () {
        SharedPreferences.Editor editor = mUserPreferences.edit();
        editor.putBoolean(getString(R.string.pref_user_is_logged_in_key), false).apply();
        mBookingPreferences.edit().clear().apply();
        startActivity(new Intent(this, LoginActivity.class));
        this.finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_logout) {
            logOut();
        }
        return super.onOptionsItemSelected(item);
    }
}
