package tomorrow.ntu.edu.sg.hospitalbees.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
import tomorrow.ntu.edu.sg.hospitalbees.BuildConfig;
import tomorrow.ntu.edu.sg.hospitalbees.HBApp;
import tomorrow.ntu.edu.sg.hospitalbees.R;

/**
 * The class for showing Alerts.
 */
public class Alerts extends AppCompatActivity {

    private static final String TAG = "Alerts";
    private static final String serverUrl = BuildConfig.SERVER_URL;

    private String mAlertsResponse;
    private String dName;
    private int dNum;
    private TextView mDiseaseName, mNumOfCases;


    @Inject
    OkHttpClient mHttpClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts);
        ((HBApp) getApplication()).getNetComponent().inject(this);
        mDiseaseName = findViewById(R.id.diseaseName);
        mNumOfCases = findViewById(R.id.numOfCases);
    }

    public void knowMore(View view) {
        startActivity(new Intent(this, AlertsDetail.class));

//        if(mAlertsResponse != null && !TextUtils.isEmpty(mAlertsResponse)) {
//            Intent intent = new Intent(this, AlertsDetail.class);
//            intent.putExtra(getString(R.string.intent_extra_alerts_response_key), mAlertsResponse);
//
////            startActivity(new Intent(this, AlertsDetail.class));
//        }
    }



    private void fetchAlerts() {
        Uri queryUri = Uri.parse(serverUrl).buildUpon()
                .appendPath("api")
                .appendPath("alerts")
                .build();

        Request request = new Request.Builder().url(queryUri.toString()).get().build();
        mHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()) {
                    final String body = response.body().string();
                    mAlertsResponse = body;
                    try {
                        JSONObject obj = new JSONObject(body);
                        JSONArray diseaseArr = obj.getJSONArray("diseases");
                        for(int i=0; i < diseaseArr.length(); i++){
                            JSONObject diseaseDetails = diseaseArr.getJSONObject(i);
                            dName = diseaseDetails.getString("Disease_Name");
                            dNum = diseaseDetails.getInt("No_of_cases");
                            mDiseaseName.setText(dName);
                            mNumOfCases.setText(dNum);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

    }

}
