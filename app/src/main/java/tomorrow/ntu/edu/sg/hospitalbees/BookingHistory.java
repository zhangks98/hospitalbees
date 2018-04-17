package tomorrow.ntu.edu.sg.hospitalbees;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import tomorrow.ntu.edu.sg.hospitalbees.adaptors.BookingHistoryAdapter;

public class BookingHistory extends AppCompatActivity {
    RecyclerView recentMonthRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);

        recentMonthRV = (RecyclerView) findViewById(R.id.recyclerview);
        recentMonthRV.setLayoutManager(new LinearLayoutManager(this));
        recentMonthRV.setAdapter(new BookingHistoryAdapter(this));
    }


    public void backButton(View view) {
        startActivity(new Intent(this, HomePage.class));
    }
}
