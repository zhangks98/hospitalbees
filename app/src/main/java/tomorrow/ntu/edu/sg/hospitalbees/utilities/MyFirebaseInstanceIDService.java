/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tomorrow.ntu.edu.sg.hospitalbees.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

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


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";
    private static final String serverUrl = BuildConfig.SERVER_URL;

    @Inject
    OkHttpClient mHttpClient;

    @Override
    public void onCreate() {
        super.onCreate();
        ((HBApp) getApplication()).getNetComponent().inject(this);
    }


    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }
    // [END refresh_token]

<<<<<<< HEAD

    private void sendRegistrationToServer(String token) {
=======
    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(final String token) {
>>>>>>> ac4d3cdbf51cc83a78444d066e30cc0d62686338
        // TODO: Implement this method to send token to your app server.
        SharedPreferences mUserPreference = getSharedPreferences(getString(R.string.pref_user), Context.MODE_PRIVATE);
        String phoneNumber = mUserPreference.getString(getString(R.string.pref_user_phone_number_key), null);
        if (phoneNumber != null) {
            Uri updateToken = Uri.parse(serverUrl).buildUpon()
                    .appendPath("api")
                    .appendPath(phoneNumber)
                    .appendPath(token)
                    .build();
            Request request = new Request.Builder().url(updateToken.toString()).put(null).build();
            mHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(TAG, "Failed to update the Token in the database");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.v(TAG, "Token Updated " + token);
                }
            });
        }
    }
}
