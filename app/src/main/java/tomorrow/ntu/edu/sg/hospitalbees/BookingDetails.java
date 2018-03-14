package tomorrow.ntu.edu.sg.hospitalbees;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class BookingDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);
    }

    public void confirmBookingButton(View view) {
        startActivity(new Intent(this, BookingComplete.class));

    }

    public void cancelBookingButton(View view) {
        startActivity(new Intent(this, HomePage.class));
    }
}
