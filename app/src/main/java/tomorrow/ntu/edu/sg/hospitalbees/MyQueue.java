package tomorrow.ntu.edu.sg.hospitalbees;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MyQueue extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_queue);
    }

    public void backButton(View view) {
        startActivity(new Intent(this, HomePage.class));

    }

    public void detailedButton(View view) {
        startActivity(new Intent(this, MyQueueDetailed.class));
    }
}
