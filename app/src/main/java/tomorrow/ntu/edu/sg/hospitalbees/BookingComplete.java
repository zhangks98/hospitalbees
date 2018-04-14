package tomorrow.ntu.edu.sg.hospitalbees;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class BookingComplete extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_complete);
    }

    public void homeButton(View view) {
        startActivity(new Intent(this, HomePage.class));

    }

    public void MyQueue(View view) {
        startActivity(new Intent(this, MyQueue.class));
    }
}
