package tomorrow.ntu.edu.sg.hospitalbees;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * The class for booking that is completed
 */
public class BookingComplete extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_complete);
    }

    /**
     * Method that returns to home page by pressing the home button.
     *
     * @param view the home button view
     */
    public void homeButton(View view) {
        this.finish();

    }

    /**
     * My queue.
     *
     * @param view the view
     */
    public void MyQueue(View view) {
        startActivity(new Intent(this, MyQueue.class));
        this.finish();
    }
}
