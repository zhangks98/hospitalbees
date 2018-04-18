package tomorrow.ntu.edu.sg.hospitalbees.models;

public class Hospital {
    private final int id;
    private final String name;
    private int queueLength;
    private int travelTime;
    private int totalETA;
    private double lattitude;
    private double longitude;

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

    public double getLattitude() {
        return lattitude;
    }
    public void setLattitude( double lattitude) {
        this.lattitude = lattitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude( double longitude) {
        this.longitude = longitude;
    }
}
