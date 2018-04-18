package tomorrow.ntu.edu.sg.hospitalbees;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import tomorrow.ntu.edu.sg.hospitalbees.adaptors.BookingHistoryAdapter;

public class BookingHistory extends AppCompatActivity {
    RecyclerView recentMonthRV;

    @Inject
    OkHttpClient mOkHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);
        ((HBApp) getApplication()).getNetComponent().inject(this);

        recentMonthRV = findViewById(R.id.recyclerview);
        recentMonthRV.setLayoutManager(new LinearLayoutManager(this));
        recentMonthRV.setAdapter(new BookingHistoryAdapter());
    }
}
