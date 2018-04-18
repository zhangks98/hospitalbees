package tomorrow.ntu.edu.sg.hospitalbees;

import android.content.Context;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import tomorrow.ntu.edu.sg.hospitalbees.models.Hospital;

public class TestHospital {

    Hospital hospital1 = new Hospital(1, "Ng Teng Fong General Hospital", 1.334031, 103.745112);
    Hospital hospital2 = new Hospital(2, "Fullerton Health NTU", 1.345503, 103.682685);
    Hospital hospital3 = new Hospital(3, "Jurong Polyclinic Singapore", 1.349792, 103.730619);
    Hospital hospital4 = new Hospital(4, "Sengkang Polyclinic", 1.349797, 103.730629);
    Hospital[] hospitalList = new Hospital[]{hospital1, hospital2, hospital3, hospital4};

    protected void getTravelTimeandSort(String origin){
            final OkHttpClient client = new OkHttpClient();
            String destinations = "";
            for(int i=0; i < hospitalList.length - 1; i++){
                destinations = destinations + hospitalList[i].getName()+"|";
            }
            System.out.println(hospitalList.length);
            destinations = destinations + hospitalList[hospitalList.length-1].getName();

            Request request = new Request.Builder()
                        .url("https://maps.googleapis.com/maps/api/distancematrix/json?" +
                                "origins=" + origin + "&destinations=" +
                                destinations + "&" +
                                "key=AIzaSyBzkZbcWIZvz1s6MX2rl_uMoqYXfQoFOkg")
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override public void onFailure(okhttp3.Call call, IOException e) {
                        e.printStackTrace();
                        System.out.println("Failed");
                    }

                    @Override public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                        try (ResponseBody responseBody = response.body()) {
                            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                            System.out.println("Failed");

                            Headers responseHeaders = response.headers();
                            for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                                System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                            }
                            String jsonData = responseBody.string();
                            System.out.println(jsonData);
                            hospitalList[0].setTotalETA(30);
                            hospitalList[1].setTotalETA(20);
                            hospitalList[2].setTotalETA(10);
                            hospitalList[3].setTotalETA(5);
                            for(int i=0; i<4; i++){
                                System.out.println(hospitalList[i].getTotalETA());
                            }
                        }
                    }
                });
            }
    }





