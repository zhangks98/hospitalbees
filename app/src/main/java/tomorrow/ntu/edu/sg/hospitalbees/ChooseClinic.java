package tomorrow.ntu.edu.sg.hospitalbees;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

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

public class ChooseClinic extends AppCompatActivity implements ClinicAdapter.ClinicAdapterOnClickHandler{


    private RecyclerView mClinicRecylerView;
    private ClinicAdapter mClinicAdapter;
    private ProgressBar mLoadClinicProgressBar;

    private static final String serverUrl = BuildConfig.SERVER_URL;
    private static final String TAG = "ChooseClinic";

    @Inject
    OkHttpClient mHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_clinic);
        ((HBApp)getApplication()).getNetComponent().inject(this);
        mClinicRecylerView =  findViewById(R.id.clinic_recycler_view);
        mLoadClinicProgressBar = findViewById(R.id.pb_load_hospitals);
        mClinicAdapter = new ClinicAdapter(this);
        mClinicRecylerView.setHasFixedSize(true);
        mClinicRecylerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        mClinicRecylerView.setAdapter(mClinicAdapter);
        mLoadClinicProgressBar.setVisibility(View.VISIBLE);
        getOpenedHospitals();

    }

    @Override
    public void onHospitalItemClick(Hospital chosenHospital) {
        Intent startBookingDetailsIntent = new Intent(this,BookingDetails.class);
        startBookingDetailsIntent
                .putExtra(getString(R.string.intent_extra_hospital_id_key), chosenHospital.getId())
                .putExtra(getString(R.string.intent_extra_hospital_travel_time_key), chosenHospital.getTravelTime());
        startActivity(startBookingDetailsIntent);
    }

    private void getOpenedHospitals() {
        Uri queryUri = Uri.parse(serverUrl).buildUpon()
                .appendPath("api")
                .appendPath("hospital")
                .build();
        Request request = new Request.Builder().url(queryUri.toString()).get().build();

        mHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG,"failed to fetch opened hospitals from HB");
                e.printStackTrace();
                mLoadClinicProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()) {
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
                            Hospital hospitalPOJO = new Hospital(id,name,lat,lng);
                            hospitalPOJO.setQueueLength(queueLength);
                            hospitals[i] = hospitalPOJO;
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mLoadClinicProgressBar.setVisibility(View.INVISIBLE);
                                mClinicAdapter.setHospitalList(hospitals);
                            }
                        });
                    } catch (JSONException e) {
                        mLoadClinicProgressBar.setVisibility(View.INVISIBLE);
                        Log.e(TAG,"failed to parse json response for opened hospitals");
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
