package tomorrow.ntu.edu.sg.hospitalbees;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;

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
    private static final String mapsApiUrl = "https://maps.googleapis.com/maps/api/distancematrix/json";
    private static final String TAG = "ChooseClinic";

    private static final int REQUEST_LOCATION = 1;
    GoogleMap mGoogleMap;
    LocationManager lm;
    private double myLat, myLong;

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
                        /*final Hospital[] hospitals = new Hospital[jsonArray.length()];

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
                        }*/

                        final Hospital[] hospitals = new Hospital[3];
                        hospitals[0] = new Hospital(1001, "Ng Teng Fong Hospital", 1.335082, 103.745198 );
                        hospitals[1] = new Hospital(1002, "NTU Fullerton Health", 1.345690, 103.682677);
                        hospitals[2] = new Hospital(1003, "Jurong Polyclinic", 1.350060, 103.730598);
                        sortHospitalByTravelTime(hospitals);


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

    protected void fetchHospitalTravelTime (final Hospital[] hospitals, String origins, String destinations) {
        Uri queryUri = Uri.parse(mapsApiUrl).buildUpon()
                .appendQueryParameter("origins", origins)
                .appendQueryParameter("destinations", destinations)
                .appendQueryParameter("key", "AIzaSyBzkZbcWIZvz1s6MX2rl_uMoqYXfQoFOkg")
                .build();
        Log.d(TAG,queryUri.toString());
        Request request = new Request.Builder().get().url(queryUri.toString()).build();

        mHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonString = response.body().string();
                    try {
                        JSONObject root = new JSONObject(jsonString);
                        JSONArray array_rows = root.getJSONArray("rows");
                        Log.d("JSON", "array_rows:" + array_rows);
                        JSONObject object_rows = array_rows.getJSONObject(0);
                        Log.d("JSON", "object_rows:" + object_rows);
                        JSONArray array_elements = object_rows.getJSONArray("elements");
                        Log.d("JSON", "array_elements:" + array_elements);
                        for (int i = 0; i < array_elements.length(); i++) {
                            JSONObject object_elements = array_elements.getJSONObject(i);
                            Log.d("JSON", "object_elements:" + object_elements);
                            JSONObject object_duration = object_elements.getJSONObject("duration");
                            Log.d("JSON", "object_duration:" + object_duration);
                            hospitals[i].setTravelTime(object_duration.getInt("value")/60);
                        }
                        Arrays.sort(hospitals);
                        for (int j=0; j<hospitals.length;j++) {
                            Log.d("ARRAYRESULTS", "arrayRESULT:" + hospitals[j].getTotalETA());
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
                        Log.d("error","error3");
                    }
                }
            }
        });
    }

    private void sortHospitalByTravelTime(Hospital[] hospitals){
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        getLocation();

//        String currentlocation = Double.toString(myLat) + "," + Double.toString(myLong);
        String currentlocation = "1.346474, 103.681518";
        String str_to[] = new String[hospitals.length];
        String all_hosp_latlng = "";
        for (int i = 0; i< hospitals.length; i++) {
            str_to[i] = Double.toString(hospitals[i].getLat()) + "," + Double.toString(hospitals[i].getLng());
            all_hosp_latlng = all_hosp_latlng + str_to[i];
            if (i == hospitals.length-1) {
                break;
            }
            else{
                all_hosp_latlng = all_hosp_latlng + "|";
            }
        }
        fetchHospitalTravelTime(hospitals, currentlocation, all_hosp_latlng);






    }
    private void getLocation(){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
        else{
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                myLat = location.getLatitude();
                myLong = location.getLongitude();
            }
        }
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
