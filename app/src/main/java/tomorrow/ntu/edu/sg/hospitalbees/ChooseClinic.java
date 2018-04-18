package tomorrow.ntu.edu.sg.hospitalbees;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import tomorrow.ntu.edu.sg.hospitalbees.adaptors.ClinicAdapter;
import tomorrow.ntu.edu.sg.hospitalbees.models.Hospital;

/**
 * The class for Choosing clinic.
 */
public class ChooseClinic extends AppCompatActivity implements ClinicAdapter.ClinicAdapterOnClickHandler {


    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mClinicRecylerView;
    private ClinicAdapter mClinicAdapter;
    private TextView mErrorMessageTextView, mNoClinicTextView;

    private static final String serverUrl = BuildConfig.SERVER_URL;
    private static final String TAG = "ChooseClinic";

    @Inject
    OkHttpClient mHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_clinic);
        ((HBApp) getApplication()).getNetComponent().inject(this);

        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_hospitals);
        mClinicRecylerView = findViewById(R.id.clinic_recycler_view);
        mClinicAdapter = new ClinicAdapter(this);
        mClinicRecylerView.setHasFixedSize(true);
        mClinicRecylerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mClinicRecylerView.setAdapter(mClinicAdapter);

        mErrorMessageTextView = findViewById(R.id.tv_error_fetch_clinic);
        mNoClinicTextView = findViewById(R.id.tv_no_clinic);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getOpenedHospitals();
            }
        });
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_dark,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        getOpenedHospitals();

    }

    @Override
    public void onHospitalItemClick(Hospital chosenHospital) {
        Intent startBookingDetailsIntent = new Intent(this, BookingDetails.class);
        startBookingDetailsIntent.putExtra(getString(R.string.intent_extra_chosen_hospital_key), chosenHospital);
        startActivity(startBookingDetailsIntent);
        this.finish();
    }

    private void getOpenedHospitals() {
        mSwipeRefreshLayout.setRefreshing(true);
        Uri queryUri = Uri.parse(serverUrl).buildUpon()
                .appendPath("api")
                .appendPath("hospital")
                .build();
        Request request = new Request.Builder().url(queryUri.toString()).get().build();

        mHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "failed to fetch opened hospitals from HB");
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                        showErrorMessage();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        final String body = response.body().string();
                        JSONArray jsonArray = new JSONArray(body);
                        final Hospital[] hospitals = new Hospital[jsonArray.length()];
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject hospitalObject = jsonArray.getJSONObject(i);
                            int id = hospitalObject.getInt("id");
                            String name = hospitalObject.getString("name");
                            double lat = hospitalObject.getDouble("lat");
                            double lng = hospitalObject.getDouble("lng");
                            int queueLength = hospitalObject.getInt("queueLength");
                            Hospital hospitalPOJO = new Hospital(id, name, lat, lng);
                            hospitalPOJO.setQueueLength(queueLength);
                            hospitals[i] = hospitalPOJO;
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mSwipeRefreshLayout.setRefreshing(false);
                                if (hospitals.length == 0) {
                                    showNoClinic();
                                } else {
                                    mClinicAdapter.setHospitalList(hospitals);
                                    showClinicList();
                                }
                            }
                        });
                    } catch (JSONException e) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        showErrorMessage();
                        Log.e(TAG, "failed to parse json response for opened hospitals");
                        Log.e(TAG,e.getMessage());
                    }
                }
            }
        });
    }

    private void showErrorMessage() {
        mErrorMessageTextView.setVisibility(View.VISIBLE);
        mNoClinicTextView.setVisibility(View.INVISIBLE);
    }

    private void showNoClinic() {
        mErrorMessageTextView.setVisibility(View.INVISIBLE);
        mNoClinicTextView.setVisibility(View.VISIBLE);
    }

    private void showClinicList() {
        mErrorMessageTextView.setVisibility(View.INVISIBLE);
        mNoClinicTextView.setVisibility(View.INVISIBLE);
    }

}
