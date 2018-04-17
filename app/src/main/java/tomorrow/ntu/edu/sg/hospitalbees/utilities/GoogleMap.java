package tomorrow.ntu.edu.sg.hospitalbees.utilities;

import android.os.AsyncTask;
import android.telecom.Call;

import com.google.android.gms.common.api.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import tomorrow.ntu.edu.sg.hospitalbees.models.Hospital;

public final class GoogleMap {
    private final OkHttpClient client = new OkHttpClient();

    public void run(String origin, String[] destination) throws Exception {
        String destinations = "";
        for(int i=0; i < destination.length - 1; i++){
            destinations = destinations + destination[i]+"|";
        }
        destinations = destinations + destination[destination.length-1] ;
        System.out.println(destinations);
        Request request = new Request.Builder()
                .url("https://maps.googleapis.com/maps/api/distancematrix/json?" +
                        "origins=" + origin + "&destinations=" +
                        destinations + "&" +
                        "key=AIzaSyBzkZbcWIZvz1s6MX2rl_uMoqYXfQoFOkg")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(okhttp3.Call call, IOException e) {
                    e.printStackTrace();
            }

            @Override public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                    Headers responseHeaders = response.headers();
                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }
                    String jsonData = responseBody.string();
                }
            }
        });
    }

    public static void main(String... args) throws Exception {
        /*Hospital test_Hospital_One = new Hospital(1, "Ng Teng Fong General Hospital");
        Hospital test_Hospital_Two = new Hospital(2, "NTU Fullerton Health");
        test_Hospital_One.setQueueLength(2);
        test_Hospital_One.setTotalETA(15);
        test_Hospital_One.setTravelTime(13);
        test_Hospital_Two.setQueueLength(7);
        test_Hospital_Two.setTotalETA(16);
        test_Hospital_Two.setTravelTime(14);
        ArrayList<Hospital> hospitalList= new ArrayList<Hospital>();
        hospitalList.add(test_Hospital_One);
        hospitalList.add(test_Hospital_Two);
        Collections.sort(hospitalList);
        for(int i=0; i< hospitalList.size(); i++){
            System.out.println(hospitalList.get(i).getName());

        }
        */
        //String fakelocation[] = {"Ng Teng Fong General Hospital", "NTU Fullerton Health"};
        //new GoogleMap().run("NTU Singapore", fakelocation);
    }
}