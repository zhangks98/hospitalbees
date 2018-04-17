package tomorrow.ntu.edu.sg.hospitalbees.models;

import android.support.annotation.NonNull;

public class Hospital implements Comparable{
    private final int id;
    private final String name;
    private int queueLength;
    private int travelTime;
    private int totalETA;

    public Hospital (int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQueueLength() {
        return queueLength;
    }

    public void setQueueLength(int queueLength) {
        this.queueLength = queueLength;
    }

    public int getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(int travelTime) {
        this.travelTime = travelTime;
    }

    public int getTotalETA() {
        return totalETA;
    }

    public void setTotalETA(int totalETA) {
        this.totalETA = totalETA;
    }

    @Override
    public int compareTo(Object compareHospital) {
        int compareETA=((Hospital)compareHospital).getTotalETA();
        /* For Ascending order*/
        return this.totalETA-compareETA;
    }
}

