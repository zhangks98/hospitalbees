package tomorrow.ntu.edu.sg.hospitalbees;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
    }


    public void bookAppointmentButton(View view) {
        startActivity(new Intent(this, ChooseClinic.class));
    }

    public void viewBookingHistoryButton(View view) {
        startActivity(new Intent(this, BookingHistory.class));
    }

    public void viewMyQueueButton(View view) {

    }

    public void viewFAQButton(View view) {
        startActivity(new Intent(this, FAQ.class));
    }


    public void viewAlertsButton(View view) {
        startActivity(new Intent(this, Alerts.class));
    }
}
