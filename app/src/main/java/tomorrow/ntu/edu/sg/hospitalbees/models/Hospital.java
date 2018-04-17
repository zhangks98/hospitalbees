package tomorrow.ntu.edu.sg.hospitalbees.models;

public class Hospital {
    private final int id;
    private final String name;
    private final double lat;
    private final double lng;
    private int queueLength;
    private int travelTime;
    private int totalETA;

    public Hospital(int id, String name, double lat, double lng) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
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
}
