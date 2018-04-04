package tomorrow.ntu.edu.sg.hospitalbees;

import android.content.Intent;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_queue);

        boolean set = false;

        queueNumberText = (TextView) findViewById(R.id.QueueNumberText);
        queueNumber1Text = (TextView) findViewById(R.id.queuenumber1Text);
        waitingTimeText = (TextView) findViewById(R.id.WaitingTimeText);
        timingText = (TextView) findViewById(R.id.Timingtext);
        reminder1Text = (TextView) findViewById(R.id.Reminder1Text);
        queueText = (TextView) findViewById(R.id.QueueText);
        reminderText = (TextView) findViewById(R.id.ReminderText);

        QRCode = (ImageView) findViewById(R.id.QRImage);

        QRCode.setOnClickListener(this);
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

        }
    }
}
