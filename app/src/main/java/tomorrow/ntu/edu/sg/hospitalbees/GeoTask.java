package tomorrow.ntu.edu.sg.hospitalbees;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import tomorrow.ntu.edu.sg.hospitalbees.models.Hospital;


public class GeoTask{
    ProgressDialog pd;
    Context mContext;
    Geo geo1;
    OkHttpClient mHttpClient = new OkHttpClient();

    final String queryUrl = "https://maps.googleapis.com/maps/api/distancematrix/json";


    protected void sortHospitalsByETA(final Hospital[] hospitals, String origins, String destinations) {
        Uri queryUri = Uri.parse(queryUrl).buildUpon()
                .appendQueryParameter("origins", origins)
                .appendQueryParameter("destinations", destinations)
                .appendQueryParameter("key", "AIzaSyBzkZbcWIZvz1s6MX2rl_uMoqYXfQoFOkg")
                .build();
        Request request = new Request.Builder().get().url(queryUri.toString()).build();

        mHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    String jsonString = responseBody.string();
                    try {
                        JSONObject root = new JSONObject(jsonString);
                        JSONArray array_rows = root.getJSONArray("rows");
                        Log.d("JSON", "array_rows:" + array_rows);
                        for (int i = 0; i < array_rows.length(); i++) {
                            JSONObject object_rows = array_rows.getJSONObject(i);
                            Log.d("JSON", "object_rows:" + object_rows);
                            JSONArray array_elements = object_rows.getJSONArray("elements");
                            Log.d("JSON", "array_elements:" + array_elements);
                            JSONObject object_elements = array_elements.getJSONObject(0);
                            Log.d("JSON", "object_elements:" + object_elements);
                            JSONObject object_duration = object_elements.getJSONObject("duration");

                            Log.d("JSON", "object_duration:" + object_duration);
                            hospitals[i].setTravelTime(object_duration.getInt("value"));
                        }
                    } catch (JSONException e) {
                        Log.d("error","error3");
                    }
                }
            }
        });
    }

//    protected void onPreExecute() {
//        super.onPreExecute();
//        pd=new ProgressDialog(mContext);
//        pd.setMessage("Loading");
//        pd.setCancelable(false);
//        pd.show();
//    }
//
//    protected void onPostExecute(String aDouble) {
//        super.onPostExecute(aDouble);
//        if(aDouble!=null)
//        {
//            geo1.setDouble(aDouble);
//            pd.dismiss();
//        }
//        else
//            Toast.makeText(mContext, "Error4!Please Try Again with proper values", Toast.LENGTH_SHORT).show();
//    }
//
//
//    protected String doInBackground(String... params) {
//        try {
//            URL url=new URL(params[0]);
//            HttpURLConnection con= (HttpURLConnection) url.openConnection();
//            con.setRequestMethod("GET");
//            con.connect();
//            int statuscode=con.getResponseCode();
//            if(statuscode==HttpURLConnection.HTTP_OK)
//            {
//                BufferedReader br=new BufferedReader(new InputStreamReader(con.getInputStream()));
//                StringBuilder sb=new StringBuilder();
//                String line=br.readLine();
//                while(line!=null)
//                {
//                    sb.append(line);
//                    line=br.readLine();
//                }
//                String total_object_duration = "";
//                String json=sb.toString();
//                Log.d("JSON",json);
//                JSONObject root=new JSONObject(json);
//                JSONArray array_rows=root.getJSONArray("rows");
//                Log.d("JSON","array_rows:"+array_rows);
//                for (int i = 0; i < array_rows.length() ; i++) {
//                    JSONObject object_rows = array_rows.getJSONObject(i);
//                    Log.d("JSON", "object_rows:" + object_rows);
//                    JSONArray array_elements = object_rows.getJSONArray("elements");
//                    Log.d("JSON", "array_elements:" + array_elements);
//                    JSONObject object_elements = array_elements.getJSONObject(0);
//                    Log.d("JSON", "object_elements:" + object_elements);
//                    JSONObject object_duration = object_elements.getJSONObject("duration");
//                    JSONObject object_distance = object_elements.getJSONObject("distance");
//
//                    Log.d("JSON", "object_duration:" + object_duration);
//                    total_object_duration = total_object_duration + object_duration.getString("value") + ",";
//                }
//                Log.d("JSON", "total_object_duration:" + total_object_duration);
//                return total_object_duration;
//
//            }
//        } catch (MalformedURLException e) {
//            Log.d("error", "error1");
//        } catch (IOException e) {
//            Log.d("error", "error2");
//        } catch (JSONException e) {
//            Log.d("error","error3");
//        }
//
//
//        return null;
//    }
    interface Geo{
        public void setDouble(String min);
    }

}

