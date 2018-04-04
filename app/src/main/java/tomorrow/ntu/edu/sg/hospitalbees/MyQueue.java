package tomorrow.ntu.edu.sg.hospitalbees;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MyQueue extends AppCompatActivity implements View.OnClickListener {

    TextView queueNumberText, queueNumber1Text, waitingTimeText, timingText, reminder1Text, queueText, reminderText;
    ImageView QRCode;

    public static String shareddata = "MySharedData";
    SharedPreferences set;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_queue);

        set = getSharedPreferences(shareddata, 0);
        boolean returned = set.getBoolean(shareddata, false);


        queueNumberText = (TextView) findViewById(R.id.QueueNumberText);
        queueNumber1Text = (TextView) findViewById(R.id.queuenumber1Text);
        waitingTimeText = (TextView) findViewById(R.id.WaitingTimeText);
        timingText = (TextView) findViewById(R.id.Timingtext);
        reminder1Text = (TextView) findViewById(R.id.Reminder1Text);
        queueText = (TextView) findViewById(R.id.QueueText);
        reminderText = (TextView) findViewById(R.id.ReminderText);
        QRCode = (ImageView) findViewById(R.id.QRImage);

        QRCode.setOnClickListener(this);





        if (returned) {

            queueText.setVisibility(View.GONE);
            QRCode.setVisibility(View.GONE);
            reminderText.setVisibility(View.GONE);
            queueNumberText.setVisibility(View.VISIBLE);
            queueNumber1Text.setVisibility(View.VISIBLE);
            waitingTimeText.setVisibility(View.VISIBLE);
            timingText.setVisibility(View.VISIBLE);
            reminder1Text.setVisibility(View.VISIBLE);
        }
        else {
            queueText.setVisibility(View.VISIBLE);
            QRCode.setVisibility(View.VISIBLE);
            reminderText.setVisibility(View.VISIBLE);
            queueNumberText.setVisibility(View.GONE);
            queueNumber1Text.setVisibility(View.GONE);
            waitingTimeText.setVisibility(View.GONE);
            timingText.setVisibility(View.GONE);
            reminder1Text.setVisibility(View.GONE);
        }
    }
    public void backButton(View view) {


        startActivity(new Intent(this, HomePage.class));

    }

    @Override
    public void onClick(View v) {
        if (v.getId()== R.id.QRImage){
            queueText.setVisibility(View.GONE);
            QRCode.setVisibility(View.GONE);
            reminderText.setVisibility(View.GONE);
            queueNumberText.setVisibility(View.VISIBLE);
            queueNumber1Text.setVisibility(View.VISIBLE);
            waitingTimeText.setVisibility(View.VISIBLE);
            timingText.setVisibility(View.VISIBLE);
            reminder1Text.setVisibility(View.VISIBLE);
            SharedPreferences.Editor editor = set.edit();
            editor.putBoolean(shareddata, true);
            editor.apply();


        }
    }

}
