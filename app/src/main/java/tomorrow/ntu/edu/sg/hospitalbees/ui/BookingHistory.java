package tomorrow.ntu.edu.sg.hospitalbees.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import tomorrow.ntu.edu.sg.hospitalbees.BuildConfig;
import tomorrow.ntu.edu.sg.hospitalbees.HBApp;
import tomorrow.ntu.edu.sg.hospitalbees.R;
import tomorrow.ntu.edu.sg.hospitalbees.adaptors.BookingHistoryAdapter;
import tomorrow.ntu.edu.sg.hospitalbees.models.Booking;

/**
 * The class for showing the booking history
 */
public class BookingHistory extends AppCompatActivity {
    private static final String serverUrl = BuildConfig.SERVER_URL;
    private static final String TAG = "BookingHistory";

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView recentMonthRV;
    private BookingHistoryAdapter mBookingAdapter;
    private SharedPreferences mUserPreferences;

    @Inject
    OkHttpClient mHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);
        ((HBApp) getApplication()).getNetComponent().inject(this);

        mUserPreferences = getSharedPreferences(getString(R.string.pref_user), Context.MODE_PRIVATE);
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_booking_history);
        recentMonthRV = findViewById(R.id.recyclerview);
        recentMonthRV.setLayoutManager(new LinearLayoutManager(this));
        mBookingAdapter = new BookingHistoryAdapter();
        recentMonthRV.setAdapter(mBookingAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchHistory();
            }
        });
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorAccent);
        mSwipeRefreshLayout.setRefreshing(true);
        fetchHistory();
    }

    private void fetchHistory() {
        String phoneNumber = mUserPreferences.getString(getString(R.string.pref_user_phone_number_key), null);
        if (phoneNumber != null) {
            Uri queryUri = Uri.parse(serverUrl).buildUpon()
                    .appendPath("api")
                    .appendPath("user")
                    .appendPath(phoneNumber)
                    .appendPath("history")
                    .build();
            final Request request = new Request.Builder().url(queryUri.toString()).get().build();
            mHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(TAG, "Fail to fetching booking history");
                    Log.e(TAG, e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        final String body = response.body().string();
                        try {
                            JSONArray bookingsJsonArray = new JSONArray(body);
                            final Booking[] bookings = new Booking[bookingsJsonArray.length()];
                            for (int i = bookings.length - 1; i >= 0; i--) {
                                JSONObject bookingJsonObject = bookingsJsonArray.getJSONObject(bookings.length - i - 1);
                                String tid = bookingJsonObject.getString("Booking_TID");
                                String status = bookingJsonObject.getString("Booking_BookingStatus");
                                String hospitalName = bookingJsonObject.getString("Hospital_Name");
                                bookings[i] = new Booking(tid, status, hospitalName);
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mBookingAdapter.setBookingHistoryList(bookings);
                                    mSwipeRefreshLayout.setRefreshing(false);
                                }
                            });
                        } catch (JSONException e) {
                            Log.e(TAG, "Error parsing json history");
                            Log.e(TAG, e.getMessage());
                        }
                    }
                }
            });
        }
    }
}
