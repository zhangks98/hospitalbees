package tomorrow.ntu.edu.sg.hospitalbees.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class Hospital implements Parcelable, Comparable<Hospital>{
    private final int id;
    private final String name;
    private final double lat;
    private final double lng;
    private int queueLength;
    private int travelTime;
    private int totalETA;

    private static final int AVG_Q_WAITING_TIME = 10;

    public Hospital(int id, String name, double lat, double lng) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }

    protected Hospital(Parcel in) {
        id = in.readInt();
        name = in.readString();
        lat = in.readDouble();
        lng = in.readDouble();
        queueLength = in.readInt();
        travelTime = in.readInt();
        totalETA = in.readInt();
    }

    public static final Creator<Hospital> CREATOR = new Creator<Hospital>() {
        @Override
        public Hospital createFromParcel(Parcel in) {
            return new Hospital(in);
        }

        @Override
        public Hospital[] newArray(int size) {
            return new Hospital[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeDouble(lat);
        dest.writeDouble(lng);
        dest.writeInt(queueLength);
        dest.writeInt(travelTime);
        dest.writeInt(totalETA);
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
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
        this.totalETA = Math.max(this.travelTime, this.queueLength * AVG_Q_WAITING_TIME);
    }

    public int getTotalETA() {
        return totalETA;
    }

    public void setTotalETA(int totalETA) {
        this.totalETA = totalETA;
    }

    @Override
    public int compareTo(@NonNull Hospital compareHospital) {
        int compareETA = ((Hospital) compareHospital).getTotalETA();
        return this.totalETA - compareETA;
    }
}
