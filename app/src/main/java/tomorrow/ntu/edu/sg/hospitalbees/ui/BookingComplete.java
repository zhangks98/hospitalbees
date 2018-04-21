package tomorrow.ntu.edu.sg.hospitalbees.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tomorrow.ntu.edu.sg.hospitalbees.BuildConfig;
import tomorrow.ntu.edu.sg.hospitalbees.HBApp;
import tomorrow.ntu.edu.sg.hospitalbees.R;

/**
 * The class for booking that is completed
 */
public class BookingComplete extends AppCompatActivity {

    private static final String TAG = "BookingComplete";
    private static final String serverUrl = BuildConfig.SERVER_URL;
    @Inject
    OkHttpClient mHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_complete);
        ((HBApp) getApplication()).getNetComponent().inject(this);
        uploadFCMToken();
    }

    /**
     * Method that returns to home page by pressing the home button.
     *
     * @param view the home button view
     */
    public void homeButton(View view) {
        this.finish();

    }

    /**
     * My queue.
     *
     * @param view the view
     */
    public void MyQueue(View view) {
        startActivity(new Intent(this, MyQueue.class));
        this.finish();
    }

    private void uploadFCMToken() {
        final String token = FirebaseInstanceId.getInstance().getToken();
        SharedPreferences mUserPreference = getSharedPreferences(getString(R.string.pref_user), Context.MODE_PRIVATE);
        String phoneNumber = mUserPreference.getString(getString(R.string.pref_user_phone_number_key), null);
        if (phoneNumber != null) {
            Uri updateToken = Uri.parse(serverUrl).buildUpon()
                    .appendPath("api")
                    .appendPath("user")
                    .appendPath(phoneNumber)
                    .appendPath("fcmToken")
                    .appendPath(token)
                    .build();
            Log.d(TAG, "serverUrl " + updateToken.toString());
            RequestBody reqbody = RequestBody.create(null, new byte[0]);
            Request request = new Request.Builder().url(updateToken.toString()).put(reqbody).build();
            mHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(TAG, "Failed to push the Token in the database");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.v(TAG, "Token Uploaded " + token);
                }
            });
        }
    }

}
