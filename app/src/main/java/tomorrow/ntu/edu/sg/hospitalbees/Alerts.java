package tomorrow.ntu.edu.sg.hospitalbees;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
/**
 * The class for showing Alerts.
 */
public class Alerts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts);
    }

    /**
     * method that returns to homepage by pressing the thanks button
     *
     * @param view the thanks button view
     */
    public void thanksButton(View view) {
        startActivity(new Intent(this, HomePage.class));

    }

}
