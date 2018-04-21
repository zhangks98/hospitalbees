package tomorrow.ntu.edu.sg.hospitalbees;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AlertsDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts_detail);

        Intent alertIntent = getIntent();
        if (alertIntent.hasExtra(getString(R.string.intent_extra_alerts_response_key))) {
         //TODO JSON parsing



        }
    }
}
