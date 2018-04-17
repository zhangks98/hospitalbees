package tomorrow.ntu.edu.sg.hospitalbees.models;

import tomorrow.ntu.edu.sg.hospitalbees.utilities.TIDParser;

public class BookingHistory {
    private final String tid;
    private final String status;
    private final String hospitalName;

    public BookingHistory(String tid, String status, String hospitalName) {
        this.tid = tid;
        this.status = status;
        this.hospitalName = hospitalName;
    }

    public String getStatus() {
        return status;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public String getBookingTime() {
        return TIDParser.
    }
}
