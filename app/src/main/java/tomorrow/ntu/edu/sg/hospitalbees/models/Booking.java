package tomorrow.ntu.edu.sg.hospitalbees.models;

import java.util.Date;

import tomorrow.ntu.edu.sg.hospitalbees.utilities.TIDParser;


public class Booking {
    private final String tid;
    private final String status;
    private final String hospitalName;


    public Booking(String tid, String status, String hospitalName) {
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


    public Date getBookingTime() {
        return TIDParser.getBookingDate(tid);
    }
}
